package com.example.webapplication_hotels;

import com.example.webapplication_hotels.Hotel;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.UUID;


@Entity
public class Rooms {
    private static Long nr = Long.valueOf(1);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = nr++;
    private int roomNumber;
    private int type;
    private int price;
    @JsonProperty("isAvailable")
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public int getPrice() {
        return price;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getType() {
        return type;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return ""+roomNumber;
    }
}
