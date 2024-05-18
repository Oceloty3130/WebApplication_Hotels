package com.example.webapplication_hotels;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Hotel>> typeReference = new TypeReference<List<Hotel>>() {};
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("hotels.json");
        List<Hotel> hotels = mapper.readValue(inputStream, typeReference);
        hotelRepository.saveAll(hotels);
        int a = 1;
    }
}