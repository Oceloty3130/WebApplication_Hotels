package com.example.webapplication_hotels;

import Reservation.RequestReservation;
import Reservation.Reservation;
import Reservation.ServiceReservation;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Route("")
public class MainView extends VerticalLayout {

    private final HotelService hotelService;
    private final ServiceReservation serviceReservation;
    private final TextField latitudeField = new TextField("Latitude");
    private final TextField longitudeField = new TextField("Longitude");
    private final NumberField radiusField = new NumberField("Radius (km)");

    private final Grid<Hotel> hotelGrid = new Grid<>(Hotel.class);
    private final Grid<Rooms> roomGrid = new Grid<>(Rooms.class);

    private Hotel selectedHotel;

    @Autowired
    public MainView(HotelService hotelService, ServiceReservation serviceReservation) {
        this.hotelService = hotelService;
        this.serviceReservation = serviceReservation;

        hotelGrid.setColumns("name", "latitude", "longitude");

        Button searchButton = new Button("Search", event -> searchHotels());
        add(latitudeField, longitudeField, radiusField, searchButton, hotelGrid, roomGrid);

        TextField userIdField = new TextField("User ID");
        TextField roomIdField = new TextField("Room ID");
        Button reserveButton = new Button("Reserve", event -> reserveRoom(userIdField.getValue(), roomIdField.getValue()));
        add(userIdField, roomIdField, reserveButton);

        hotelGrid.asSingleSelect().addValueChangeListener(event -> {
            selectedHotel = event.getValue();
            if (selectedHotel != null) {
                loadRoomsForSelectedHotel();
            }
        });
    }

    private void searchHotels() {
        try {
            double lat = Double.parseDouble(latitudeField.getValue());
            double lon = Double.parseDouble(longitudeField.getValue());
            double radius = radiusField.getValue();

            List<Hotel> hotels = hotelService.getHotelRepository().findAll().stream()
                    .filter(hotel -> isWithinRadius(lat, lon, hotel.getLatitude(), hotel.getLongitude(), radius))
                    .collect(Collectors.toList());

            hotelGrid.setItems(hotels);
            roomGrid.setItems();
        } catch (NumberFormatException e) {
            Notification.show("Invalid input! Please enter valid latitude, longitude, and radius.");
        }
    }

    private boolean isWithinRadius(double userLat, double userLong, double hotelLat, double hotelLong, double radius) {
        double earthRadius = 6371;
        double dLat = Math.toRadians(hotelLat - userLat);
        double dLong = Math.toRadians(hotelLong - userLong);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(hotelLat)) *
                        Math.sin(dLong / 2) * Math.sin(dLong / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        return distance <= radius;
    }

    private void loadRoomsForSelectedHotel() {
        Hotel hotel = hotelService.getHotelRepository().findByIdWithRooms(selectedHotel.getId());
        List<Rooms> rooms = hotel.getRooms();
        if (rooms.isEmpty()) {
            Notification.show("No rooms available for the selected hotel.");
        } else {
            roomGrid.setItems(rooms);
        }
    }

    private void reserveRoom(String userId, String roomId) {
        if (selectedHotel == null) {
            Notification.show("Please select a hotel to reserve.");
            return;
        }

        try {
            Long userIdLong = Long.parseLong(userId);
            Long roomIdLong = Long.parseLong(roomId);
            LocalDateTime checkIn = LocalDateTime.now();
            LocalDateTime checkOut = checkIn.plusDays(1);

            RequestReservation request = new RequestReservation();
            request.setUserId(userIdLong);
            request.setRoomId(roomIdLong);
            request.setCheckIn(checkIn);
            request.setCheckOut(checkOut);

            Reservation reservation = serviceReservation.bookRoom(request);

            Notification.show("Room reserved successfully with ID: " + reservation.getId());
        } catch (NumberFormatException e) {
            Notification.show("Invalid input! Please enter valid user ID and room ID.");
        } catch (RuntimeException e) {
            Notification.show(e.getMessage());
        }
    }
}
