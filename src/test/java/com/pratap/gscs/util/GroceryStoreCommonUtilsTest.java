package com.pratap.gscs.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GroceryStoreCommonUtilsTest {
    @InjectMocks
    private GroceryStoreCommonUtils groceryStoreCommonUtils;

    @Test
    void parseInput() {
        String input = "3 banana";
        Map<String,Integer> result = groceryStoreCommonUtils.parseInput(input);
        assertEquals(1, result.size());
        assertEquals(3, result.get("banana"));
    }

    @Test
    void round() {
        double d = 1.5888889;
        double result = groceryStoreCommonUtils.round(d);
        assertEquals(1.59,result);
    }

    @Test
    void capitalize() {
        String s = "banana";
        String result = groceryStoreCommonUtils.capitalize(s);
        assertEquals("Banana",result);
    }
}