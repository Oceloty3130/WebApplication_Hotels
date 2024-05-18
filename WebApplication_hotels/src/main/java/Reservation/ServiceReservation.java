package Reservation;

import org.springframework.stereotype.Service;

@Service
public class ServiceReservation {

    public Reservation bookRoom(RequestReservation request) {
        // Implement the booking logic here and return a Reservation object
        // For simplicity, we're just creating a dummy Reservation object
        Reservation reservation = new Reservation();
        reservation.setId(1L); // This should be dynamically generated in a real application
        reservation.setUserId(request.getUserId());
        reservation.setRoomId(request.getRoomId());
        reservation.setCheckIn(request.getCheckIn());
        reservation.setCheckOut(request.getCheckOut());
        return reservation;
    }
}
