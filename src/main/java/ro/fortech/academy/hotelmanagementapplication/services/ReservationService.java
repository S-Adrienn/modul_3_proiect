package ro.fortech.academy.hotelmanagementapplication.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.fortech.academy.hotelmanagementapplication.controllers.request.ReservationPeriodRequest;
import ro.fortech.academy.hotelmanagementapplication.controllers.request.ReservationRequest;
import ro.fortech.academy.hotelmanagementapplication.entities.Reservation;
import ro.fortech.academy.hotelmanagementapplication.entities.Room;
import ro.fortech.academy.hotelmanagementapplication.repositories.ReservationRepository;
import ro.fortech.academy.hotelmanagementapplication.repositories.RoomRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    private final RoomRepository roomRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    public void addReservation(ReservationRequest requestBody) {
        Reservation newReservation = new Reservation();
        validateCheckInAndCheckOutDate(requestBody);

        newReservation.setDateOfCheckIn(requestBody.getDateOfCheckIn());
        newReservation.setDateOfCheckOut(requestBody.getDateOfCheckOut());
        newReservation.setGuestName(requestBody.getGuestName());
        newReservation.setPhoneNumber(requestBody.getPhoneNumber());

        //ezt kulon metodusba
        String totalPriceString = requestBody.getTotalPrice();
        Double totalPrice = Double.parseDouble(totalPriceString);
        newReservation.setTotalPrice(totalPrice);

        newReservation.setDeleted(false);

        //ez is kulon
        String roomIdString = requestBody.getRoomId();
        Long roomId = Long.parseLong(roomIdString);
        newReservation.setRoomId(roomId);

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
        isValidReservationUpdate(id, requestBody);

        reservation.setDateOfCheckIn(requestBody.getDateOfCheckIn());
        reservation.setDateOfCheckOut(requestBody.getDateOfCheckOut());
        reservation.setGuestName(requestBody.getGuestName());
        reservation.setPhoneNumber(requestBody.getPhoneNumber());

        Double totalPrice = Double.parseDouble(requestBody.getTotalPrice());
        reservation.setTotalPrice(totalPrice);

        Long roomId = Long.parseLong(requestBody.getRoomId());
        reservation.setRoomId(roomId);

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

    //overload validateCheckInAndCheckOutDate
    private void validateCheckInAndCheckOutDate(ReservationPeriodRequest requestBody) {
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

    private List<Reservation> findExistentReservation(Long reservationId, ReservationRequest requestBody) {
        Long roomId = Long.parseLong(requestBody.getRoomId());

        List<Reservation> reservations = reservationRepository
                .findAll().stream().filter(reservation -> reservation.getDeleted().equals(false)).toList();

        List<Reservation> reservationsByRoomId = reservations
                .stream().filter(reservation -> reservation.getRoomId().equals(roomId)).toList();

        List<Reservation> searchedReservations = reservationsByRoomId
                .stream().filter(reservation -> !reservation.getId().equals(reservationId)).toList();

        if (searchedReservations.isEmpty()) {
            return Collections.emptyList();
        }
        return searchedReservations;
    }

    private boolean isValidReservationPeriod(List<Reservation> existentReservationList, ReservationRequest newReservationRequest) {
        for (Reservation existentReservation : existentReservationList) {
            if (existentReservation.getDateOfCheckIn().isBefore(newReservationRequest.getDateOfCheckOut())
                    && existentReservation.getDateOfCheckOut().isAfter(newReservationRequest.getDateOfCheckIn())) {
                throw new IllegalArgumentException("This room is not available for the selected period. " +
                        "If you still want to make a reservation for this date, please delete your current reservation " +
                        "and create a new one in one of our available rooms.");
            }
        }
        return true;
    }

    private boolean isValidReservationUpdate(Long id, ReservationRequest request) {
        List<Reservation> existentReservationList = findExistentReservation(id, request);
        if (!existentReservationList.isEmpty()) {
            return isValidReservationPeriod(existentReservationList, request);
        }
        return true;
    }

    public List<Room> findFreePeriodForReservation(ReservationPeriodRequest requestBody) {
        validateCheckInAndCheckOutDate(requestBody);
        List<Room> availableRooms = roomRepository.findAll();
        List<Reservation> reservationsInPeriod = getAllReservationsInConflict(requestBody);

        for (Reservation reservation : reservationsInPeriod) {
            availableRooms.removeIf(room -> room.getId().equals(reservation.getRoomId()));
        }
        if (availableRooms.isEmpty()) {
            throw new IllegalArgumentException("There are no available rooms for this period. Please choose another date.");
        }
        return availableRooms;
    }

    private List<Reservation> getAllReservationsInConflict(ReservationPeriodRequest requestBody) {
        List<Reservation> reservations = reservationRepository.findAll();
        List<Reservation> reservationsInConflict = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getDateOfCheckIn().isBefore(requestBody.getDateOfCheckOut())
                    && reservation.getDateOfCheckOut().isAfter(requestBody.getDateOfCheckIn())) {
                reservationsInConflict.add(reservation);
            }
        }
        return reservationsInConflict;
    }

    public List<Reservation> getReservationsByGuestName(String guestName) {
        List<Reservation> existentReservations = reservationRepository.findAll().stream().filter(reservation -> reservation.getDeleted().equals(false)).toList();
        List<Reservation> guestsReservations = existentReservations.stream().filter(reservation -> reservation.getGuestName().equals(guestName)).toList();
        if (guestsReservations.isEmpty()) {
            throw new IllegalArgumentException("No reservation exists with this name.");
        }
        return guestsReservations;
    }
}
