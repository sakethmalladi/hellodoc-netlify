package com.springproject.hellodoc.models;

import jakarta.persistence.*;

@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialization;
    private String location;
    private String openingTime;
    private String closingTime;
    private double feePer30Min;
    private double latitude; // New field
    private double longitude; // New field

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime=openingTime;
    }
    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime=closingTime;
    }
    public double getFeePer30Min() {
        return feePer30Min;
    }

    public void setFeePer30Min(double feePer30Min) {
        this.feePer30Min=feePer30Min;
    }
    public void setLongitude(double longitude) {
        this.longitude=longitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLatitude(double latitude) {
        this.latitude=latitude;
    }
    public double getLatitude() {
        return latitude;
    }
}
