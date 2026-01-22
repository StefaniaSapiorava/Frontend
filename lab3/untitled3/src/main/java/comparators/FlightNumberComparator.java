package comparators;

import flights.Flight;

import java.util.Comparator;

public class FlightNumberComparator implements Comparator<Flight> {
    @Override
    public int compare(Flight f1, Flight f2) {
        return f1.getFlightNumber().compareTo(f2.getFlightNumber());
    }
}
