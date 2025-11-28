package com.pratap.gscs.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratap.gscs.model.Item;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Configuration class to load items from a JSON file into memory at application startup.
 */
@Configuration
@Slf4j
public class ItemLoader {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Getter
    private List<Item> loadedItems;

    @Value("classpath:items.json")
    private Resource resourceFile;

    /**
     * Loads items from the specified JSON resource file into the loadedItems list.
     * @throws IOException
     */
    @PostConstruct
    public void loadItems() throws IOException {
        try (InputStream inputStream = resourceFile.getInputStream()) {
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, Item.class);
            loadedItems = objectMapper.readValue(inputStream, type);
            log.info("Loaded {} items from items.json",loadedItems.size());
        } catch (IOException e) {
            // We need to handle exception as per requirements.
            throw e;
        }
    }

}