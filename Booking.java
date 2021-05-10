package TheProject;

import java.util.*;

public class Booking {
   private static int bookingNumber =1;
   private static final HashMap<Integer, Ticket> bookings = new HashMap<>();
   private static final Queue<Passenger> rac = new LinkedList<>();
   private static final Queue<Passenger> waitingList = new LinkedList<>();

   // 1. VIEW AVAILABLE TICKETS
    public static void displayAvailableBerth () {

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("%%%%%%%%%%%%%% AVAILABLE TICKETS %%%%%%%%%%%%%%%%%%\n");
            int totalTicketsAvailable = Berth.getLowerBerth()+ Berth.getUpperBerth() + Berth.getSideBerth();
            System.out.println("Tickets Available: ");
            System.out.println("Lower Berth: "+ Berth.getLowerBerth());
            System.out.println("Upper Berth: " + Berth.getUpperBerth());
            System.out.println("Side Berth: "+ Berth.getSideBerth());
            System.out.println("Total tickets available: "+ totalTicketsAvailable+ "\n");
            System.out.println("RAC: " + (Berth.getRac() - rac.size()) ); // reduce the size from the Limit (Since List size is dynamic)
            System.out.println("Waiting List: " + (Berth.getWaitingList() - waitingList.size()) ); //  reduce the size from the Limit (Since List size is dynamic)
            System.out.println("\n---------------------------\n\n\n");

            isRunning = UI.backButton(isRunning);
        }
    }

    // 2. VIEW BOOK TICKETS
    public static void viewBookedTickets () {
        //   List<Ticket> bookedTicketList = new ArrayList<>();
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n%%%%%%%%%%%%%% BOOKED TICKETS %%%%%%%%%%%%%%%%%%\n");
            System.out.println(bookings);
            System.out.println("\n======================================================\n\n");

            isRunning = UI.backButton(isRunning);
        }
    }

    // 3. BOOK TICKET
    public void bookTicket () {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n%%%%%%%%%%%%%% BOOK A TICKET %%%%%%%%%%%%%%%%%%\n");

            Passenger passenger = createPassenger();
            // When all berths are available - tickets are booked as per passengers choice
            if ( Berth.getSideBerth() > 0 && Berth.getUpperBerth() > 0 && Berth.getLowerBerth() > 0 ) {
                allBerthsAvailable(passenger);
            }
            // If at least one berth is available
            else if (Berth.getLowerBerth() > 0 || Berth.getUpperBerth() > 0 || Berth.getSideBerth() > 0)
            {
                atLeastOneBerth(passenger);
            }
            else {
                // Ask the users if they want to book a RAC or join the waitList and adds them.
                // Lets the user know that the tickets are sold out and also if the waiting list is full.
                confirmedTicketsFull(passenger);
            }
            //BACK BUTTON
            isRunning = UI.backButton(isRunning);
        }
    }

    // 4. CANCEL TICKET:
    public void cancelTicket () {
        // It books a ticket only when there is an entry in the RAC and
        // moves the passengers from waiting list to RAC only when there is an entry for waiting list.
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n%%%%%%%%%%%%%% TICKET CANCELLATION %%%%%%%%%%%%%%%%%%\n");

            // Display bookedTickets and then ask the user to enter teh bookingID which needs to be cancelled.
            Booking.viewBookedTickets();
            System.out.println("\n=============================================================\n\n");

            System.out.println("Enter the booking number that you'd like to cancel");
            Scanner cancelTicketScan = new Scanner(System.in);
            int bookingNumber = cancelTicketScan.nextInt();


            // gets the cancelled berthLocation to assign it to the RAC
            Passenger cancelledPassenger = bookings.get(bookingNumber).getPassenger();
            bookings.remove(bookingNumber);

            // When there is a RAC ticket, it needs to be booked and removed from the waiting list.
            if(rac.size() > 0) {
                Passenger racPassenger = rac.poll();
                racToBooking(racPassenger, cancelledPassenger);
                // If waiting list has queue, add them to the RAC list.
                if (waitingList.size() > 0) {
                    rac.add(waitingList.poll());
                }
                else {
                    // Subtracting the booked count from the respective berth when the ticket is cancelled.
                    if (cancelledPassenger.getTicket().getBerthLocation() == Passenger.berthType.LOWER) {
                        Berth.setLowerBerth(Berth.getLowerBerth() - 1);
                    } else if (cancelledPassenger.getTicket().getBerthLocation() == Passenger.berthType.UPPER) {
                        Berth.setUpperBerth(Berth.getUpperBerth() - 1);
                    } else {
                        Berth.setSideBerth(Berth.getSideBerth() - 1);
                    }
                }
            }
            System.out.println("Booking number "+  cancelledPassenger.getTicket().getBookingID() +" has been cancelled");
            isRunning = UI.backButton(isRunning);
        }

    }


    // Creating a Passenger
    // Creating a Passenger >> Adding Children >> Creating Children
    private Passenger createPassenger () {
        Passenger passenger = new Passenger();
        Scanner scanPassenger = new Scanner(System.in);
        System.out.print("Enter your name: ");
        passenger.setName(scanPassenger.nextLine());
        System.out.print("Enter your age: ");
        passenger.setAge(scanPassenger.nextInt());

        System.out.println("Enter your gender selection \n1.Male \n2.Female");
        int genderSelection = scanPassenger.nextInt();

        // An ordinary if else statement inside the method to select the passengers gender;
        if (genderSelection == 1) { passenger.setGender(Passenger.gender.MALE); }
        else if (genderSelection == 2) { passenger.setGender(Passenger.gender.FEMALE);  }

        System.out.println("What type of berth do you prefer? (Enter the number of your choice)");
        System.out.println("1.Lower Berth \n2.Upper Berth \n3.Side Berth");
        int berthSelection = scanPassenger.nextInt();

        if (berthSelection == 1) { passenger.setBerthPreference(Passenger.berthType.LOWER); }
        else if (berthSelection == 2) { passenger.setBerthPreference(Passenger.berthType.UPPER); }
        else if (berthSelection == 3) { passenger.setBerthPreference(Passenger.berthType.SIDE); }

        System.out.println("Are you bringing any children along with you? \n1. Yes \n2. No");
        int childSelection = scanPassenger.nextInt();

        if (childSelection == 1) {
            passenger.createAndAddChild();
        }
        return passenger;
    }


// Berth Booking Methods
    private void bookLower (Passenger passenger) {
        Ticket ticket = new Ticket();
        ticket.setBookingID(bookingNumber++);
        ticket.setPassenger(passenger);
        ticket.setConfirmationStatus(Ticket.confirmationStatus.CONFIRMED);
        ticket.setBerthLocation(Passenger.berthType.LOWER);
        ticket.setBerthID(Berth.getLowerBerth());
        passenger.setTicket(ticket);
        bookings.put(ticket.getBookingID(),ticket);
        Berth.setLowerBerth(Berth.getLowerBerth()- 1);
        System.out.println( Passenger.berthType.LOWER + " berth booked successfully!");
    }
    private void bookUpper (Passenger passenger) {
        Ticket ticket = new Ticket();
        ticket.setBookingID(bookingNumber++);
        ticket.setPassenger(passenger);
        ticket.setConfirmationStatus(Ticket.confirmationStatus.CONFIRMED);
        ticket.setBerthLocation(Passenger.berthType.UPPER);
        ticket.setBerthID(Berth.getUpperBerth());
        passenger.setTicket(ticket);
        bookings.put(ticket.getBookingID(),ticket);
        Berth.setUpperBerth(Berth.getUpperBerth() - 1);
        System.out.println( Passenger.berthType.UPPER + " berth booked successfully!");
    }
    private void bookSide (Passenger passenger) {
        Ticket ticket = new Ticket();
        ticket.setBookingID(bookingNumber++);
        ticket.setPassenger(passenger);
        ticket.setConfirmationStatus(Ticket.confirmationStatus.CONFIRMED);
        ticket.setBerthLocation(Passenger.berthType.SIDE);
        ticket.setBerthID(Berth.getSideBerth());
        passenger.setTicket(ticket);
        bookings.put(ticket.getBookingID(),ticket);
        Berth.setSideBerth(Berth.getSideBerth() - 1);
        System.out.println( Passenger.berthType.SIDE + " berth booked successfully!");
    }


    // Booking when all the berths are available
    private void allBerthsAvailable (Passenger passenger) {

        if (passenger.getBerthPreference() == Passenger.berthType.LOWER) {
            bookLower(passenger);
        } else if (passenger.getBerthPreference() == Passenger.berthType.UPPER) {
            bookUpper(passenger);
        } else if (passenger.getBerthPreference() == Passenger.berthType.SIDE) {
            bookSide(passenger);
        }
    }

    // Booking when at least one berth is available

    private void atLeastOneBerth (Passenger passenger) {
         // NEED TO CHECK
        // if the passengers preference happens to be the available
        if (passenger.getBerthPreference() == Passenger.berthType.LOWER && Berth.getLowerBerth() >= 1) {
            bookLower(passenger);
        } else if (passenger.getBerthPreference() == Passenger.berthType.UPPER && Berth.getUpperBerth() >= 1){
            bookUpper(passenger);
        } else if (passenger.getBerthPreference() == Passenger.berthType.SIDE && Berth.getSideBerth() >= 1){
            bookSide(passenger);
        }
        // Preference not available
        else if (passenger.getBerthPreference() == Passenger.berthType.LOWER && Berth.getLowerBerth() < 1){
            if (Berth.getUpperBerth() >= 1) { bookUpper(passenger); }
            else if (Berth.getSideBerth() >=1) { bookSide(passenger); }
        } else if (passenger.getBerthPreference() == Passenger.berthType.UPPER && Berth.getUpperBerth() < 1) {
            if (Berth.getLowerBerth() >= 1) { bookLower(passenger); }
            else if (Berth.getSideBerth() >= 1) { bookSide(passenger); }
        } else if (passenger.getBerthPreference() == Passenger.berthType.SIDE && Berth.getSideBerth() < 1){
            if (Berth.getLowerBerth() >= 1){ bookLower(passenger);  }
            else if (Berth.getUpperBerth() >= 1) { bookUpper(passenger); }
        }
    }


    // move to waiting list
    private void addToWaitingList (Passenger passenger) {
            waitingList.add(passenger);
        System.out.println("You've successfully joined the waiting list!");
    }


    // add to RAC
    private void addToRAC (Passenger passenger) {
      Ticket ticket = new Ticket();
      ticket.setBookingID(bookingNumber++);
      ticket.setPassenger(passenger);
      ticket.setConfirmationStatus(Ticket.confirmationStatus.PENDING_CONFIRMATION);
      passenger.setTicket(ticket);
      rac.add(passenger);
        System.out.println("\nBooking has been done for RAC!");
    }


    // When all berths are full
    private void confirmedTicketsFull (Passenger passenger) {
       int userSelection;
        Scanner scan = new Scanner(System.in);
        if (rac.size() < 1 ) {
            System.out.println("The tickets are sold out, would you like to create a booking against RAC?");
            System.out.println("1. Yes\n2. No");
            userSelection = scan.nextInt();
            if (userSelection == 1) { addToRAC(passenger); }
        } else if (waitingList.size() <= 1) {
            System.out.println("The tickets and the RAC is already full, would you like to join the waiting list");
            System.out.println("1. Yes\n2. No");
            userSelection = scan.nextInt();
            if (userSelection == 1) { addToWaitingList(passenger); }
        } else {
            System.out.println("No tickets available!");
        }
    }

    // Allots the cancelled passengers berth position to the RAC person
    private void racToBooking (Passenger racPassenger, Passenger cancelledPassenger) {
        racPassenger.getTicket().setConfirmationStatus(Ticket.confirmationStatus.CONFIRMED);
        racPassenger.getTicket().setBerthLocation(cancelledPassenger.getTicket().getBerthLocation());
        bookings.put(racPassenger.getTicket().getBookingID(), racPassenger.getTicket());
    }

}
