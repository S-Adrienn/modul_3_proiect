package ro.fortech.academy.hotelmanagementapplication.controllers.response;

import ro.fortech.academy.hotelmanagementapplication.entities.Room;

import java.util.ArrayList;
import java.util.List;

public class GetAllRoomsResponse {
    private final List<Room> rooms = new ArrayList<>();

    public GetAllRoomsResponse() {
    }

    public GetAllRoomsResponse(List<Room> rooms) {
        this.rooms.addAll(rooms);
    }
    public List<Room> getRooms() {
        return rooms;
    }
}
