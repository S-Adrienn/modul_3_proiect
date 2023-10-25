package ro.fortech.academy.hotelmanagementapplication.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.fortech.academy.hotelmanagementapplication.controllers.request.RoomRequest;
import ro.fortech.academy.hotelmanagementapplication.entities.Room;
import ro.fortech.academy.hotelmanagementapplication.repositories.RoomRepository;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void addRoom(Room newRoom) {
        roomRepository.save(newRoom);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Room updateRoom(Long id, RoomRequest requestBody) {
        Room room = roomRepository.findById(id).orElseThrow();
        room.setNumberOfBeds(requestBody.getNumberOfBeds());
        room.setRoomPricePerNight(requestBody.getRoomPricePerNight());
        room.setVisible(requestBody.getVisible());
        return room;
    }

    @Transactional
    public Room disableRoomById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow();
        room.setVisible(false);
        return room;
    }
}
