package ro.fortech.academy.hotelmanagementapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.fortech.academy.hotelmanagementapplication.controllers.request.RoomRequest;
import ro.fortech.academy.hotelmanagementapplication.controllers.response.GetAllRoomsResponse;
import ro.fortech.academy.hotelmanagementapplication.entities.Room;
import ro.fortech.academy.hotelmanagementapplication.services.RoomService;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public void createRoom(@RequestBody RoomRequest requestBody) {
        Room newRoom = new Room();
        newRoom.setNumberOfBeds(requestBody.getNumberOfBeds());
        newRoom.setRoomPricePerNight(requestBody.getRoomPricePerNight());
        newRoom.setVisible(true);
        roomService.addRoom(newRoom);
    }
    @GetMapping("{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        try {
            Room responseBody = roomService.getRoomById(id);
            return ResponseEntity.ok(responseBody);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
//        GetAllRoomsResponse responseBody = new GetAllRoomsResponse(rooms);
        return ResponseEntity.ok(rooms);
    }
    @PutMapping("{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody RoomRequest requestBody) {
        try {
            Room responseBody = roomService.updateRoom(id, requestBody);
            return ResponseEntity.ok(responseBody);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Room> disableRoomById(@PathVariable Long id) {
        try {
            Room responseBody = roomService.disableRoomById(id);
            return ResponseEntity.ok(responseBody);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
