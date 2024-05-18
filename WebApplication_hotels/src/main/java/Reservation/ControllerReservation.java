package Reservation;

import Reservation.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ControllerReservation {
    @Autowired
    private ServiceReservation reservationService;

    @PostMapping("/book")
    public Reservation bookRoom(@RequestBody RequestReservation request) {
        return reservationService.bookRoom(request);
    }


}
