package com.example.webapplication.Controller;

import com.example.webapplication.Model.Hero;
import com.example.webapplication.Model.Item;
import com.example.webapplication.Service.HeroService;
import com.example.webapplication.Service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class HeroController {
    private final HeroService heroService;
    private final ItemService itemService;

    // ok
    @GetMapping("/heroes")
    public List<Hero> getHeroes() {
        return heroService.getAllHeroesSorted();
    }

    // ok
    @GetMapping("/hero/{id}")
    public Hero getHero(@PathVariable int id) {
        return heroService.getHeroWithItems(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Hero with ID " + id + " not found"
                ));
    }

    // ok
    @PostMapping("/hero")
    public Hero createHero(@RequestParam String name, @RequestParam Double weightLimit) {
        try {
            return heroService.createHero(name, weightLimit);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create hero: " + e.getMessage());
        }
    }

    // ok
    @DeleteMapping("/hero/{id}")
    public void deleteHero(@PathVariable int id) {
        boolean deleted = heroService.deleteHero(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hero with ID " + id + " not found or already deleted");
        }
    }

    @PostMapping("/item")
    public Item createItem(@RequestParam Double weight, @RequestParam Integer typeId) {
        return itemService.createItem(weight, typeId);
    }

    @PostMapping("/addItem")
    public boolean addItemToInventory(@RequestParam int itemId, @RequestParam int inventoryId) {
        return itemService.addItemToInventory(itemId, inventoryId);
    }

    @GetMapping("/items")
    public List<Item> getItems() {
        return itemService.getAllItems();
    }

    // ok
    @PutMapping("/hero/{id}/name")
    public Hero updateHeroName(@PathVariable int id, @RequestParam String newName) {
        return heroService.updateHeroName(id, newName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hero with ID " + id + " not found"));
    }
}
