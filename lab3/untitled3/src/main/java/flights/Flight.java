package flights;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flight")
@NamedQueries({
        @NamedQuery(name = "Flight.getAllFlights", query = "SELECT f FROM Flight f"),
        @NamedQuery(name = "Flight.getFlightByNumber", query = "SELECT f FROM Flight f WHERE f.flightNumber = :flightNumber")
})
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "flightnumber", nullable = false, unique = true)
    private String flightNumber;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "baseprice", nullable = false)
    private double basePrice;

    @Column(name = "departuredate", nullable = false)
    private LocalDateTime departureDate;

    @Column(name = "availableseats", nullable = false)
    private int availableSeats;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    public Flight() {
        tickets = new ArrayList<>();
    }

    public Flight(String flightNumber, String destination, double basePrice, LocalDateTime departureDate, int availableSeats) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.basePrice = basePrice;
        this.departureDate = departureDate;
        this.availableSeats = availableSeats;
        this.tickets = new ArrayList<>();
    }

    // Getters and setters

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public int getTicketCount() {
        return tickets.size();
    }

    public double getAdjustedPrice() {
        long daysToDeparture = java.time.Duration.between(LocalDateTime.now(), departureDate).toDays();
        if (daysToDeparture < 7) {
            return basePrice * 1.2;
        } else {
            return basePrice;
        }
    }
}
