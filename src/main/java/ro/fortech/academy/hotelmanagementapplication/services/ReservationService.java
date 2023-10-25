package ro.fortech.academy.hotelmanagementapplication.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.fortech.academy.hotelmanagementapplication.controllers.request.CreateReservationRequest;
import ro.fortech.academy.hotelmanagementapplication.entities.Reservation;
import ro.fortech.academy.hotelmanagementapplication.repositories.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void addReservation(Reservation newReservation) {
        reservationRepository.save(newReservation);
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElseThrow();
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public Reservation updateReservation(Long id, CreateReservationRequest requestBody) {
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
}
