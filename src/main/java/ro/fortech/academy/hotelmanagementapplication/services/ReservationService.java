package ro.fortech.academy.hotelmanagementapplication.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.fortech.academy.hotelmanagementapplication.controllers.request.ReservationRequest;
import ro.fortech.academy.hotelmanagementapplication.entities.Reservation;
import ro.fortech.academy.hotelmanagementapplication.repositories.ReservationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void addReservation(ReservationRequest requestBody) {
        Reservation newReservation = new Reservation();
        validateCheckInAndCheckOutDate(requestBody);
        isValidNewReservationRequest(requestBody);

        newReservation.setDateOfCheckIn(requestBody.getDateOfCheckIn());
        newReservation.setDateOfCheckOut(requestBody.getDateOfCheckOut());
        newReservation.setGuestName(requestBody.getGuestName());
        newReservation.setPhoneNumber(requestBody.getPhoneNumber());
        newReservation.setTotalPrice(requestBody.getTotalPrice());
        newReservation.setDeleted(false);
        newReservation.setRoomId(requestBody.getRoomId());
        reservationRepository.save(newReservation);
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElseThrow();
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public Reservation updateReservation(Long id, ReservationRequest requestBody) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();

        reservation.setDateOfCheckIn(requestBody.getDateOfCheckIn());
        reservation.setDateOfCheckOut(requestBody.getDateOfCheckOut());
        reservation.setGuestName(requestBody.getGuestName());
        reservation.setPhoneNumber(requestBody.getPhoneNumber());
        reservation.setTotalPrice(requestBody.getTotalPrice());
        reservation.setRoomId(requestBody.getRoomId());
        return reservation;
    }

    @Transactional
    public Reservation deleteReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        reservation.setDeleted(true);
        return reservation;
    }

    private void validateCheckInAndCheckOutDate(ReservationRequest requestBody) {
        LocalDate today = LocalDate.now();
        LocalDate checkInDate = requestBody.getDateOfCheckIn();
        LocalDate checkOutDate = requestBody.getDateOfCheckOut();

        if (checkInDate.isBefore(today) || checkOutDate.isBefore(today)) {
            throw new IllegalArgumentException("Reservations can only be made for future dates.");
        }
        if (checkOutDate.isBefore(checkInDate)) {
            throw new IllegalArgumentException("The check-out date must be later than the check-in date.");
        }
    }

    private Reservation findExistentReservation(ReservationRequest requestBody) {
        List<Reservation> reservations = reservationRepository
                .findAll().stream().filter(reservation -> reservation.getDeleted().equals(false)).toList();

        Optional<Reservation> searchedReservation = reservations
                .stream().filter(reservation -> reservation.getRoomId().equals(requestBody.getRoomId())).findFirst();
        return searchedReservation.orElse(null);
    }

    private boolean isValidReservation(Reservation existentReservation, ReservationRequest newReservationRequest) {
        if (existentReservation.getDateOfCheckIn().isBefore(newReservationRequest.getDateOfCheckOut())
                && existentReservation.getDateOfCheckOut().isAfter(newReservationRequest.getDateOfCheckIn()))
            throw new IllegalArgumentException("The room is not available for the selected period.");
        else {
            return true;
        }
    }

    private boolean isValidNewReservationRequest(ReservationRequest request) {
        Reservation existentReservation = findExistentReservation(request);
        if (existentReservation != null) {
            return isValidReservation(existentReservation, request);
        }
        return true;
    }

}
