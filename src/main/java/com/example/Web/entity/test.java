package com.example.Web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private float Id;
    private String empName;
    private String empAddress;
    private String empTelePhone;
}
