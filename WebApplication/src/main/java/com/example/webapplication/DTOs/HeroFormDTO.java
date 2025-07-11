package com.example.webapplication.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HeroFormDTO {
    @NotNull
    private String name;

    @Min(value = 0, message = "Limit wagi musi być większy lub równy 0")
    private Double weightLimit;
}