package com.pratap.gscs.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratap.gscs.model.Coupon;
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
 * Configuration class to load coupons from a JSON file into memory at application startup.
 */
@Configuration
@Slf4j
public class CouponLoader {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Getter
    private List<Coupon> loadedCoupons;

    @Value("classpath:coupons.json")
    private Resource resourceFile;

    /**
     * Loads coupons from the specified JSON resource file into the loadedCoupons list.
     * @throws IOException
     */
    @PostConstruct
    public void loadCoupons() throws IOException {
        try (InputStream inputStream = resourceFile.getInputStream()) {
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, Coupon.class);
            loadedCoupons = objectMapper.readValue(inputStream, type);
            //System.out.println("Loaded " + loadedCoupons.size() + " coupons.");
            log.info("Loaded {} coupons from coupons.json.",loadedCoupons.size());
        } catch (IOException e) {
            // We need to handle exception as per requirements.
            throw e;
        }
    }

}