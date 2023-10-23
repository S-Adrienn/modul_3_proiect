package ro.fortech.academy.hotelmanagementapplication.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_of_check_in")
    private LocalDate dateOfCheckIn;

    @Column(name = "date_of_check_out")
    private LocalDate dateOfCheckOut;

    @Column(name = "guest_name")
    private String guestName;

    @Column (name = "phone_number")
    private String phoneNumber;

    @Column (name = "total_price")
    private Double totalPrice;

    @Column (name = "room_id")
    private Long roomId;

    public Reservation() {
    }

    public Reservation(Long id, LocalDate dateOfCheckIn, LocalDate dateOfCheckOut, String guestName, String phoneNumber, Double totalPrice, Long roomId) {
        this.id = id;
        this.dateOfCheckIn = dateOfCheckIn;
        this.dateOfCheckOut = dateOfCheckOut;
        this.guestName = guestName;
        this.phoneNumber = phoneNumber;
        this.totalPrice = totalPrice;
        this.roomId = roomId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOfCheckIn() {
        return dateOfCheckIn;
    }

    public void setDateOfCheckIn(LocalDate dateOfCheckIn) {
        this.dateOfCheckIn = dateOfCheckIn;
    }

    public LocalDate getDateOfCheckOut() {
        return dateOfCheckOut;
    }

    public void setDateOfCheckOut(LocalDate dateOfCheckOut) {
        this.dateOfCheckOut = dateOfCheckOut;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}

