package com.springproject.hellodoc.repositories;

import com.springproject.hellodoc.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}