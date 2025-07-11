package com.example.webapplication.Controller;

import com.example.webapplication.DTOs.ItemFormDTO;
import com.example.webapplication.Model.Item;
import com.example.webapplication.Repository.HeroRepository;
import com.example.webapplication.Repository.InventoryRepository;
import com.example.webapplication.Repository.ItemRepository;
import com.example.webapplication.Repository.TypeItemRepository;
import com.example.webapplication.Service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/data/items")
public class ItemController {

    private final ItemRepository itemRepository;
    private final HeroRepository heroRepository;
    private final InventoryRepository inventoryRepository;
    private final TypeItemRepository typeItemRepository;
    private final ItemService itemService;

    public ItemController(ItemRepository itemRepository,
                          HeroRepository heroRepository,
                          InventoryRepository inventoryRepository,
                          TypeItemRepository typeItemRepository,
                          ItemService itemService) {
        this.itemRepository = itemRepository;
        this.heroRepository = heroRepository;
        this.inventoryRepository = inventoryRepository;
        this.typeItemRepository = typeItemRepository;
        this.itemService = itemService; // zainicjalizuj
    }

    @GetMapping("/")
    public String showItemForm(Model model) {
        model.addAttribute("itemForm", new ItemFormDTO());
        model.addAttribute("heroes", heroRepository.findAll());
        return "items";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("types", typeItemRepository.findAll()); // pobierz wszystkie typy z bazy
        return "item-form";
    }

    @PostMapping("/")
    public String createItem(@ModelAttribute ItemFormDTO itemForm) {
        itemService.createItemFromForm(itemForm.getName(), itemForm.getWeight(), itemForm.getTypeId(), itemForm.getHeroId());
        return "redirect:/";
    }
}