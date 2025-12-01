package com.pratap.gscs.util;

import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Common functions will be kept in this. class.
 */
@Component
public class GroceryStoreCommonUtils {
    /**
     * The method takes a string input representing a shopping cart,
     * where each item is represented as "quantity itemName" and items are separated by commas.
     * It parses the input and returns a map with item names as keys (in lowercase)
     * and their total quantities as values.
     *
     * if multiple entries of same item exist, their quantities are summed up.
     *
     * @param input
     * @return
     */
    public Map<String,Integer> parseInput(String input) {
        if (input==null) return Collections.emptyMap();
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(s -> s.split("\\s+", 2))
                .filter(a -> a.length==2)
                .map(a -> new AbstractMap.SimpleEntry<>(a[1].toLowerCase().trim(), Integer.parseInt(a[0])))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum));
    }

    /**
     * The method takes a double value v as input
     * and returns a double value rounded to the nearest hundredth
     * i.e two decimal places.
     *
     * @param v
     * @return
     */
    public double round(double v){
        return Math.round(v*100.0)/100.0;
    }

    /**
     * The method capitalizes the first letter of the input string s.
     * @param s
     * @return
     */
    public String capitalize(String s){
        if (s==null || s.isEmpty()) return s;
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
