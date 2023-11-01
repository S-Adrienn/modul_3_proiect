package ro.fortech.academy.hotelmanagementapplication.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.fortech.academy.hotelmanagementapplication.controllers.request.ReservationPeriodRequest;
import ro.fortech.academy.hotelmanagementapplication.controllers.request.ReservationRequest;
import ro.fortech.academy.hotelmanagementapplication.controllers.response.GetAllReservationsResponse;
import ro.fortech.academy.hotelmanagementapplication.entities.Reservation;
import ro.fortech.academy.hotelmanagementapplication.entities.Room;
import ro.fortech.academy.hotelmanagementapplication.services.ReservationService;

import java.util.List;
import java.util.NoSuchElementException;

@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public void createReservation(@Valid @RequestBody ReservationRequest requestBody) {
        reservationService.addReservation(requestBody);
    }

    @PostMapping("/free-period")
    public ResponseEntity<List<Room>> reservationPeriodRequest(@RequestBody ReservationPeriodRequest requestBody) {
        return ResponseEntity.ok(reservationService.findFreePeriodForReservation(requestBody));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        try {
            Reservation responseBody = reservationService.getReservationById(id);
            return ResponseEntity.ok(responseBody);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/guest-reservations/{guestName}")
    public  ResponseEntity<List<Reservation>> getReservationsByGuestName(@PathVariable String guestName) {
        List<Reservation> reservationsByGuestName = reservationService.getReservationsByGuestName(guestName);
        return ResponseEntity.ok(reservationsByGuestName);
    }

    @GetMapping
    public ResponseEntity<GetAllReservationsResponse> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        GetAllReservationsResponse responseBody = new GetAllReservationsResponse(reservations);
        return ResponseEntity.ok(responseBody);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@Valid @PathVariable Long id, @RequestBody ReservationRequest requestBody) {
        try {
            Reservation responseBody = reservationService.updateReservation(id, requestBody);
            return ResponseEntity.ok(responseBody);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservationById(@PathVariable Long id) {
        try {
            Reservation responseBody = reservationService.deleteReservationById(id);
            return ResponseEntity.ok(responseBody);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
