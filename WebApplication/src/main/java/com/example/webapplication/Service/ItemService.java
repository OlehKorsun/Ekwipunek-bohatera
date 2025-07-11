package com.example.webapplication.Service;

import com.example.webapplication.DTOs.ItemFormDTO;
import com.example.webapplication.Model.Inventory;
import com.example.webapplication.Model.Item;
import com.example.webapplication.Model.TypeItem;
import com.example.webapplication.Repository.InventoryRepository;
import com.example.webapplication.Repository.ItemRepository;
import com.example.webapplication.Repository.TypeItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepo;
    private final TypeItemRepository typeRepo;
    private final InventoryRepository inventoryRepo;

    public Item createItem(Double weight, Integer typeId) {
        TypeItem type = typeRepo.findById(typeId).orElseThrow();
        Item item = new Item();
        item.setWeight(weight);
        item.setType(type);
        return itemRepo.save(item);
    }


    public Item createItemFromForm(String name, double weight, int typeId, int heroId) {
        TypeItem type = typeRepo.findById(typeId).orElseThrow();
        Inventory inv = inventoryRepo.findAll().stream()
                .filter(i -> i.getHero().getIdHero() == heroId)
                .findFirst()
                .orElseThrow();
        Item item = new Item();
        item.setName(name);
        item.setWeight(weight);
        item.setType(type);

        item.getInventories().add(inv);

        item = itemRepo.save(item);

        inv.getItems().add(item);
        inventoryRepo.save(inv);
        return item;
    }


    public boolean addItemToInventory(int itemId, int inventoryId) {
        Item item = itemRepo.findById(itemId).orElseThrow();
        Inventory inv = inventoryRepo.findById(inventoryId).orElseThrow();

        double currentWeight = inv.getItems().stream().mapToDouble(Item::getWeight).sum();
        if (currentWeight + item.getWeight() <= inv.getWeightLimit()) {
            inv.getItems().add(item);
            inventoryRepo.save(inv);
            return true;
        }
        return false;
    }

    public List<Item> getAllItems() {
        return itemRepo.findAll();
    }


    public boolean tryAddItemToHero(ItemFormDTO form) {
        Item item = new Item();
        item.setName(form.getName());
        item.setWeight(form.getWeight());
        item.setType(typeRepo.findById(form.getTypeId()).orElseThrow());
        item = itemRepo.save(item);

        Inventory inv = inventoryRepo.findAll().stream()
                .filter(i -> i.getHero().getIdHero() == form.getHeroId())
                .findFirst().orElseThrow();

        double totalWeight = inv.getItems().stream().mapToDouble(Item::getWeight).sum();
        if (totalWeight + item.getWeight() > inv.getWeightLimit()) {
            return false;
        }

        inv.getItems().add(item);
        inventoryRepo.save(inv);
        return true;
    }


}