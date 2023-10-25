package ro.fortech.academy.hotelmanagementapplication.controllers.request;


public class RoomRequest {
    private Integer numberOfBeds;
    private Double roomPricePerNight;
    private Boolean isVisible;

    public RoomRequest() {
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
