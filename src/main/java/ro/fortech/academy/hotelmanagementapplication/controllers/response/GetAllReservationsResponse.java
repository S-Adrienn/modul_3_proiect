package ro.fortech.academy.hotelmanagementapplication.controllers.response;

import ro.fortech.academy.hotelmanagementapplication.entities.Reservation;

import java.util.ArrayList;
import java.util.List;

public class GetAllReservationsResponse {
    private final List<Reservation> reservations = new ArrayList<>();
    public GetAllReservationsResponse() {
    }
    public GetAllReservationsResponse(List<Reservation> reservations){
        this.reservations.addAll(reservations);
    }
    public List<Reservation> getReservations() {
        return reservations;
    }
}
