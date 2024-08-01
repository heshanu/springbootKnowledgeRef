package com.example.Web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee")
@NoArgsConstructor
@Data
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private float Id;
    private String empName;
    private String empAddress;

    public EmployeeEntity(String empName, String empAddress, String empTelePhone) {
        this.empName = empName;
        this.empAddress = empAddress;
        this.empTelePhone = empTelePhone;
    }

    private String empTelePhone;
}
