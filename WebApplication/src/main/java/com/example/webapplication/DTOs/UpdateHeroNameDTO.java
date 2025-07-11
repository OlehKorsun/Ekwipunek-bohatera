package com.example.webapplication.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateHeroNameDTO {
    @NotBlank(message = "Name cannot be empty")
    private String name;
}