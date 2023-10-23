package ro.fortech.academy.hotelmanagementapplication.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number_of_beds")
    private Integer numberOfBeds;

    @Column(name = "price_per_night")
    private Double roomPricePerNight;

    @Column(name = "is_visible")
    private Boolean isVisible;

    public Room() {
    }

    public Room(Long id, Integer numberOfBeds, Double roomPricePerNight, Boolean isVisible) {
        this.id = id;
        this.numberOfBeds = numberOfBeds;
        this.roomPricePerNight = roomPricePerNight;
        this.isVisible = isVisible;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(Integer numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public Double getRoomPricePerNight() {
        return roomPricePerNight;
    }

    public void setRoomPricePerNight(Double roomPricePerNight) {
        this.roomPricePerNight = roomPricePerNight;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }
}
