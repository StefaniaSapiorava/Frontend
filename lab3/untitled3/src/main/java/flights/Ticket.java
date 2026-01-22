package flights;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticketid")
    private int ticketId;

    @ManyToOne
    @JoinColumn(name = "flightnumber", nullable = false)
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "passengerid", nullable = false)
    private Passenger passenger;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "baggageid", referencedColumnName = "baggageid")
    private Baggage baggage;

    @Column(name = "price", nullable = false)
    private double price;

    public Ticket() {}

    public Ticket(Flight flight, Passenger passenger, Baggage baggage) {
        this.flight = flight;
        this.passenger = passenger;
        this.baggage = baggage;
        this.price = calculatePrice();
    }

    // Геттеры и сеттеры

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Baggage getBaggage() {
        return baggage;
    }

    public void setBaggage(Baggage baggage) {
        this.baggage = baggage;
    }

    public double getPrice() {
        return price;
    }

    // Дополнительные методы

    private double calculatePrice() {
        double totalPrice = flight.getAdjustedPrice();
        totalPrice += baggage.calculateBaggageCost();
        return totalPrice;
    }
}
