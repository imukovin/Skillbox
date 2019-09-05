import com.skillbox.airport.Aircraft;
import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;

public class Loader {
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    private final static long TWO_HOURS = 10_800_000;
    static Date d;

    public static void main(String[] args) {

        Airport airport = Airport.getInstance();

        try {
            d = dateFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        airport.getTerminals().stream()
                .forEach(terminal -> terminal.getFlights().stream()
                        .forEach(flight -> {
                            if (flight.getDate().getTime() - d.getTime() <= TWO_HOURS) {
                                System.out.println(flight.getDate() + " - " + flight.getAircraft().toString());
                            }
                        }));

    }
}
