package ro.fortech.academy.hotelmanagementapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fortech.academy.hotelmanagementapplication.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
