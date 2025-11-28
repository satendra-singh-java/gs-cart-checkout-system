package com.pratap.gscs.service;

import com.pratap.gscs.model.Item;
import com.pratap.gscs.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing items.
 */
@Service
@AllArgsConstructor
public class ItemService {
    private final ItemRepository repo;

    public List<Item> getAllItems(){
        return repo.findAll();
    }
    public Optional<Item> findByName(String name){
        return repo.findByName(name.toLowerCase());
    }
}