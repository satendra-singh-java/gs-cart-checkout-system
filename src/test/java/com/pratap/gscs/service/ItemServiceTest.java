package com.pratap.gscs.service;

import com.pratap.gscs.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
    @Mock
    private ItemRepository repo;
    @InjectMocks
    private ItemService itemService;

    @Test
    void getAllItems() {
        itemService.getAllItems();
        Mockito.verify(repo).findAll();
    }

    @Test
    void findByName() {
        itemService.findByName("banana");
        Mockito.verify(repo).findByName(Mockito.anyString());
    }
}