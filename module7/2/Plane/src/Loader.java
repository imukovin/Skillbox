import com.skillbox.airport.Airport;

import java.util.Date;

public class Loader {
    private static final long TWO_HOURS_MS = 2 * 60 * 60 * 1000;

    public static void main(String[] args) {
        Airport airport = Airport.getInstance();
        Date current = new Date();

        System.out.println("Current date: " + current);
        System.out.println("----------");

        airport.getTerminals().stream()
                .flatMap(terminal -> terminal.getFlights().stream())
                .forEach(flight -> {
                    if ((flight.getDate().getTime() - current.getTime() <= TWO_HOURS_MS)
                            && (flight.getDate().getTime() - current.getTime() > 0)) {
                        System.out.println(flight.getDate() + " - " + flight.getAircraft());
                    }
                });
    }
}