package com.example.webapplication.View;

import com.example.webapplication.DTOs.HeroFormDTO;
import com.example.webapplication.DTOs.ItemFormDTO;
import com.example.webapplication.Model.Hero;
import com.example.webapplication.Service.HeroService;
import com.example.webapplication.Repository.TypeItemRepository;
import com.example.webapplication.Service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class HeroViewController {

    private final HeroService heroService;
    private final TypeItemRepository typeItemRepository;
    private final ItemService itemService;

    @GetMapping("/")
    public String showHeroes(Model model) {
        model.addAttribute("heroes", heroService.getAllHeroesSorted());
        model.addAttribute("types", typeItemRepository.findAll());
        model.addAttribute("itemForm", new ItemFormDTO());
        model.addAttribute("heroForm", new HeroFormDTO());
        return "heroes";
    }


    @PostMapping("/add-hero")
    public String addHero(@Valid @ModelAttribute("heroForm") HeroFormDTO heroForm,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("heroes", heroService.getAllHeroesSorted());
            model.addAttribute("types", typeItemRepository.findAll());
            model.addAttribute("itemForm", new ItemFormDTO());
            return "heroes";
        }

        heroService.createHero(heroForm.getName(), heroForm.getWeightLimit());
        return "redirect:/";
    }



    @PostMapping("/add-item")
    public String addItem(@Valid @ModelAttribute("itemForm") ItemFormDTO itemForm,
                          BindingResult result,
                          Model model) {

        // Sprawdzam wagę
        if (itemForm.getWeight() == null || itemForm.getWeight() <= 0) {
            result.rejectValue("weight", "item.weight.invalid", "Waga przedmiotu musi być większa od zera");
        }

        if (result.hasErrors()) {
            model.addAttribute("heroes", heroService.getAllHeroesSorted());
            model.addAttribute("types", typeItemRepository.findAll());
            model.addAttribute("heroForm", new HeroFormDTO());
            return "heroes";
        }

        // Pobieram bohatera
        Hero hero = heroService.findById(itemForm.getHeroId());
        if (hero == null) {
            result.rejectValue("heroId", "hero.notfound", "Nie znaleziono bohatera");
            model.addAttribute("heroes", heroService.getAllHeroesSorted());
            model.addAttribute("types", typeItemRepository.findAll());
            model.addAttribute("heroForm", new HeroFormDTO());
            return "heroes";
        }

        // Obliczam aktualną wagę przedmiotów bohatera
        double currentWeight = hero.getInventory().getItems().stream()
                .mapToDouble(item -> item.getWeight())
                .sum();

        // Sprawdzam limit
        if (currentWeight + itemForm.getWeight() > hero.getInventory().getWeightLimit()) {
            result.rejectValue("weight", "inventory.limit.exceeded", "Przedmiot przekracza limit wagi bohatera");
            model.addAttribute("heroes", heroService.getAllHeroesSorted());
            model.addAttribute("types", typeItemRepository.findAll());
            model.addAttribute("heroForm", new HeroFormDTO());
            return "heroes";
        }

        // Tworzę przedmiot
        itemService.createItemFromForm(
                itemForm.getName(),
                itemForm.getWeight(),
                itemForm.getTypeId(),
                itemForm.getHeroId()
        );

        return "redirect:/";
    }


    @PostMapping("/heroes/rename")
    public String renameHero(@RequestParam int heroId, @RequestParam String newName) {
        heroService.updateHeroName(heroId, newName);
        return "redirect:/";
    }

    @PostMapping("/heroes/delete")
    public String deleteHero(@RequestParam int heroId) {
        heroService.deleteHero(heroId);
        return "redirect:/";
    }
}