package ro.fortech.academy.hotelmanagementapplication.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //ha van datepicker, akkor nem kell regex?
    @Column(name = "date_of_check_in")
    private LocalDate dateOfCheckIn;

    @Column(name = "date_of_check_out")
    private LocalDate dateOfCheckOut;
    @Pattern(regexp = "^[A-Za-z\\-]+$", message = "Please enter a valid name. Your name should only contain letters and may include hyphens between parts, but it cannot be empty or contain numbers or other characters.")
    @Column(name = "guest_name")
    private String guestName;
    @Pattern(regexp = "^\\+?[0-9]+$", message = "Please enter a valid phone number. The phone number should only contain numbers and may include a leading '+' sign for country code, but no other characters are allowed.")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "room_id")
    private Long roomId;

    public Reservation() {
    }

    public Reservation(Long id, LocalDate dateOfCheckIn, LocalDate dateOfCheckOut, String guestName, String phoneNumber, Double totalPrice, Boolean isDeleted, Long roomId) {
        this.id = id;
        this.dateOfCheckIn = dateOfCheckIn;
        this.dateOfCheckOut = dateOfCheckOut;
        this.guestName = guestName;
        this.phoneNumber = phoneNumber;
        this.totalPrice = totalPrice;
        this.isDeleted = isDeleted;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}

