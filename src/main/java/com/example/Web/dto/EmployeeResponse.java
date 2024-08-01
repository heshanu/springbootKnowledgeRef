package com.example.Web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmployeeResponse {
    private String empName;
    private String empAddress;
    private String empTelePhone;
}
