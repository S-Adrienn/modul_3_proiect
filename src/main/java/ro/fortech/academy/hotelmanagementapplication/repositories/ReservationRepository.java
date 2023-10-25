package ro.fortech.academy.hotelmanagementapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fortech.academy.hotelmanagementapplication.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
