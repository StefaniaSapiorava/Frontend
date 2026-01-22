package flights;

import jakarta.persistence.*;

@Entity
@Table(name = "passenger")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passengerid")
    private int passengerId;

    @Column(name = "name", nullable = false)
    private String name;

    public Passenger() {}

    public Passenger(String name) {
        this.name = name;
    }

    public Passenger(int passengerId, String name) {
        this.passengerId = passengerId;
        this.name = name;
    }

    // Геттеры и сеттеры

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
