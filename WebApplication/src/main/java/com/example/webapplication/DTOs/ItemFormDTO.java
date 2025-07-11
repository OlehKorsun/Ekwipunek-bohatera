package com.example.webapplication.DTOs;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class ItemFormDTO {
    private String name;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "0.01", message = "Waga musi być większa od zera")
    private Double weight;

    @NotNull(message = "Wybierz typ przedmiotu")
    private Integer typeId;

    @NotNull(message = "Wybierz bohatera")
    private Integer heroId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public Integer getTypeId() { return typeId; }
    public void setTypeId(Integer typeId) { this.typeId = typeId; }

    public Integer getHeroId() { return heroId; }
    public void setHeroId(Integer heroId) { this.heroId = heroId; }
}