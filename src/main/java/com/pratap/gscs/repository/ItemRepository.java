package com.pratap.gscs.repository;

import com.pratap.gscs.model.Item;
import com.pratap.gscs.config.ItemLoader;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ItemRepository{
    public static final List<Item> ITEM_RECORDS = new ArrayList<>();

    private ItemLoader itemLoader;

    @PostConstruct
    public void initData() {
        List<Item> items = itemLoader.getLoadedItems();
        ITEM_RECORDS.addAll(items);
    }

    public List<Item> findAll() {
        return ITEM_RECORDS;
    }

    public Optional<Item> findByName(String lowerCase) {
        return ITEM_RECORDS.stream()
                .filter(i -> i.getName().equalsIgnoreCase(lowerCase))
                .findFirst();
    }
}