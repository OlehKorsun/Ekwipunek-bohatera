package com.example.webapplication.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "hero")
@ToString(exclude = "hero")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idInventory;

    @Column(nullable = false)
    @NotNull(message = "Weight limit is required")
    @Min(value = 0, message = "Weight limit cannot be negative")
    private double weightLimit;

    @OneToOne
    @JoinColumn(name = "hero_idHero", nullable = false)
    @JsonBackReference
    private Hero hero;

    @ManyToMany
    @JoinTable(
            name = "Item_Inventory",
            joinColumns = @JoinColumn(name = "idInventory"),
            inverseJoinColumns = @JoinColumn(name = "idItem")
    )
    @JsonBackReference
    private List<Item> items;
}
