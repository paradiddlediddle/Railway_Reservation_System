package TheProject;

import java.util.Scanner;

public class UI {

// On the UI, when displaying the booked tickets make sure to create a new arrayList sort them in an order and then display them.
// Since HashMap has constant lookup time but doesn't display it in order.

    public void mainScreen () {

        boolean isRunning = true;

        while (isRunning) {

            System.out.println("\n%%%%%%%%%%%%%% RAILWAY RESERVATION SYSTEM %%%%%%%%%%%%%%%%%%\n");

            System.out.println("1. View available Tickets \n2. View booked tickets");
            System.out.println("3. Book a ticket \n4. Cancel a ticket \n5. Exit\n");
            System.out.print("Enter your selection: ");
            Scanner mainScreenScan = new Scanner(System.in);
            int mainScreenSelection = mainScreenScan.nextInt();
            Booking booking = new Booking();

            switch (mainScreenSelection){
                // To avoid creating the booking object twice for case 3 and case 4, an object is created outside the scope.
                case 1: Booking.displayAvailableBerth(); break;

                case 2: Booking.viewBookedTickets(); break;

                case 3: { booking.bookTicket(); break; }

                case 4: { booking.cancelTicket(); break; }

                case 5: isRunning = false;
            }
        }
    }

    // Instead of going back to the main menu after each function gets executed,
    // this acts as an intermittent pause between two subsequent method executions.

    public static boolean backButton (boolean isRunning ) {
        System.out.println("Hit any key to go back!");
        Scanner userEntryScan = new Scanner(System.in);
        String userEntry = userEntryScan.nextLine();

        if (!userEntry.isEmpty()) {
            isRunning = false;
        }
        return isRunning;
    }







}
