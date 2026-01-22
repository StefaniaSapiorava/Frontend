package comparators;

import flights.Flight;

import java.util.Comparator;

public class FlightPriceComparator implements Comparator<Flight> {
    @Override
    public int compare(Flight f1, Flight f2) {
        return Double.compare(f1.getAdjustedPrice(), f2.getAdjustedPrice());
    }
}
