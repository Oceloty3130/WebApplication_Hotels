package Reservation;

import Reservation.Reservation;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@ComponentScan
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}

