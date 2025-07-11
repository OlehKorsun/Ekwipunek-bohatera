package com.example.webapplication.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "inventory")
@ToString(exclude = "inventory")
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idHero;

    @Column(nullable = false, length = 50)
    private String name;

    @OneToOne(mappedBy = "hero", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Inventory inventory;
}
