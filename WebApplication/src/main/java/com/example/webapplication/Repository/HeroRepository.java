package com.example.webapplication.Repository;

import com.example.webapplication.Model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<Hero, Integer> {}