package com.springproject.hellodoc.controllers;

import com.springproject.hellodoc.models.Appointment;
import com.springproject.hellodoc.models.Doctor;
import com.springproject.hellodoc.models.Patient;
import com.springproject.hellodoc.repositories.AppointmentRepository;
import com.springproject.hellodoc.repositories.DoctorRepository;
import com.springproject.hellodoc.repositories.PatientRepository;
import com.springproject.hellodoc.services.ChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class ServicesController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Autowired
    private ChatGPTService chatGPTService; 

    @GetMapping("/services")
    public String services(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) Double userLatitude,
            @RequestParam(required = false) Double userLongitude,
            @RequestParam(required = false, defaultValue = "10") int miles,
            Model model) {

        List<Doctor> doctors;

        if (userLatitude != null && userLongitude != null) {
            doctors = doctorRepository.findByLocationAndSpecializationWithinMiles(userLatitude, userLongitude, miles, specialization);
        } else {
            doctors = doctorRepository.findByLocationAndSpecialization(location, specialization);
        }

        model.addAttribute("doctors", doctors);
        model.addAttribute("patients", patientRepository.findAll());
        return "services";
    }

    @PostMapping("/book-appointment")
    public String bookAppointment(
            @RequestParam Long doctorId,
            @RequestParam String patientName,
            @RequestParam int patientAge,
            @RequestParam String patientEmail,
            @RequestParam String patientPhone,
            @RequestParam String problemDescription,
            @RequestParam int appointmentDuration,
            @RequestParam String appointmentDate,
            @RequestParam String appointmentTime,
            Model model,
            RedirectAttributes redirectAttributes) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Patient patient = patientRepository.findByName(patientName).orElseGet(() -> {
            Patient newPatient = new Patient();
            newPatient.setName(patientName);
            newPatient.setAge(patientAge);
            newPatient.setEmail(patientEmail);
            newPatient.setPhone(patientPhone);
            return patientRepository.save(newPatient);
        });

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDate(appointmentDate);
        appointment.setTime(appointmentTime);
        appointment.setDuration(appointmentDuration);

        double totalFee = (Math.ceil(appointmentDuration / 30.0)) * doctor.getFeePer30Min();
        appointment.setTotalFee(totalFee);

        appointmentRepository.save(appointment);

        redirectAttributes.addFlashAttribute("message", "Appointment successfully booked! Total Fee: $" + totalFee);
        return "redirect:/services";
    }

    @PostMapping("/ask-question")
    public String askQuestion(@RequestParam String question, Model model) {
        try {
            // Call ChatGPT API and handle the response
            String answer = chatGPTService.askQuestion(question);
            model.addAttribute("answer", answer);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace(); // This will print the stack trace to the console/log
            model.addAttribute("error", "Failed to get response from ChatGPT. Please try again.");
        }
        return "services";
    }
    

}
