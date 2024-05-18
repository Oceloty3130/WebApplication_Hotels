package com.example.webapplication_hotels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Rooms> getRoomsByHotelId(Long hotelId) {
        return hotelRepository.findByIdWithRooms(hotelId).getRooms();
    }

    public HotelRepository getHotelRepository() {
        return hotelRepository;
    }
}
