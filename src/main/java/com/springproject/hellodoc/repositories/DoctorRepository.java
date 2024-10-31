package com.springproject.hellodoc.repositories;

import com.springproject.hellodoc.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT d FROM Doctor d WHERE d.location = :location AND d.specialization LIKE %:specialization%")
    List<Doctor> findByLocationAndSpecialization(@Param("location") String location, @Param("specialization") String specialization);

    @Query(value = "SELECT * FROM doctor d " +
                   "WHERE (6371 * ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(d.latitude)) * " +
                   "COS(RADIANS(d.longitude) - RADIANS(:longitude)) + " +
                   "SIN(RADIANS(:latitude)) * SIN(RADIANS(d.latitude)))) <= :miles " +
                   "AND d.specialization LIKE %:specialization%", nativeQuery = true)
    List<Doctor> findByLocationAndSpecializationWithinMiles(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("miles") int miles,
            @Param("specialization") String specialization);
}
