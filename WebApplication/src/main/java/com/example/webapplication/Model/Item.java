package com.example.webapplication.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItem;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "typeItem_idType", nullable = false)
    @JsonManagedReference
    private TypeItem type;

    @ManyToMany(mappedBy = "items")
    private List<Inventory> inventories = new ArrayList<>();
}