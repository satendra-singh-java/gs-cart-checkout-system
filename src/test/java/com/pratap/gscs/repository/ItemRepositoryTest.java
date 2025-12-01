package com.pratap.gscs.repository;

import com.pratap.gscs.config.ItemLoader;
import com.pratap.gscs.model.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemRepositoryTest {

    @InjectMocks
    private ItemRepository itemRepository;
    @Mock
    private ItemLoader itemLoader;

    @BeforeAll
    public static void init(){
        ItemRepository.ITEM_RECORDS.clear();
        ItemRepository.ITEM_RECORDS.add(new Item("banana", 10.0));
    }

    @Test
    void findAll() {
        List<Item> result = itemRepository.findAll();
        assertNotNull(result);
        assertEquals(1,result.size());
    }

    @Test
    void findByName() {
        Optional<Item> result = itemRepository.findByName("banana");
        assertTrue(result.isPresent());
        assertEquals("banana",result.get().getName());
        assertEquals(10.0,result.get().getPrice());
    }
}