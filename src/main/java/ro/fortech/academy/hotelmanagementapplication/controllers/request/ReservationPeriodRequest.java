package ro.fortech.academy.hotelmanagementapplication.controllers.request;

import java.time.LocalDate;

public class ReservationPeriodRequest {

    private LocalDate dateOfCheckIn;

    private LocalDate dateOfCheckOut;

    public ReservationPeriodRequest() {
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
}
