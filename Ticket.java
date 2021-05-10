package TheProject;

public class Ticket {
    enum confirmationStatus {
        CONFIRMED, PENDING_CONFIRMATION
    }
    private int bookingID;
    private confirmationStatus confirmationStatus;
    private Passenger.berthType berthLocation;
    private Passenger passenger;
    private long berthID;

    public int getBookingID() { return bookingID; }

    public void setBookingID(int bookingID) { this.bookingID = bookingID; }

    public Passenger.berthType getBerthLocation() {
        return berthLocation;
    }

    public void setBerthLocation(Passenger.berthType berthLocation) {
        this.berthLocation = berthLocation;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Ticket.confirmationStatus getConfirmationStatus() {
        return confirmationStatus;
    }

    public void setConfirmationStatus(Ticket.confirmationStatus confirmationStatus) { this.confirmationStatus = confirmationStatus; }

    public long getBerthID() {
        return berthID;
    }

    public void setBerthID(long berthID) {
        this.berthID = berthID;
    }

}
