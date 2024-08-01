package com.example.Web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class ErrorRes {
    private String errorMessage;
    private String errorCode;
}
