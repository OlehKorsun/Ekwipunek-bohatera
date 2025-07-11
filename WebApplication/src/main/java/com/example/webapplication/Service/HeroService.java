package com.example.webapplication.Service;

import com.example.webapplication.Model.Hero;
import com.example.webapplication.Model.Inventory;
import com.example.webapplication.Repository.HeroRepository;
import com.example.webapplication.Repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeroService {
    private final HeroRepository heroRepo;
    private final InventoryRepository inventoryRepo;

    public Hero createHero(String name, Double weightLimit) {
        Hero hero = new Hero();
        hero.setName(name);
        Hero savedHero = heroRepo.save(hero);

        Inventory inv = new Inventory();
        inv.setHero(savedHero);
        inv.setWeightLimit(weightLimit);
        inventoryRepo.save(inv);

        savedHero.setInventory(inv);
        return heroRepo.save(savedHero);
    }

    public List<Hero> getAllHeroesSorted() {
        return heroRepo.findAll(Sort.by("name"));
    }

    public Optional<Hero> getHeroWithItems(int id) {
        return heroRepo.findById(id);
    }

    public boolean deleteHero(int id) {
        if (heroRepo.existsById(id)) {
            heroRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Hero> updateHeroName(int id, String newName) {
        Optional<Hero> heroOpt = heroRepo.findById(id);
        heroOpt.ifPresent(hero -> {
            hero.setName(newName);
            heroRepo.save(hero);
        });
        return heroOpt;
    }

    public Hero findById(int id) {
        return heroRepo.findById(id).orElse(null);
    }

}