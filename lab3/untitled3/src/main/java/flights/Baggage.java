package flights;

import jakarta.persistence.*;

@Entity
@Table(name = "baggage")
public class Baggage {
    private static final double MAX_WEIGHT = 20.0;
    private static final double COST_PER_KG = 10.0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "baggageid")
    private int baggageId;

    @Column(name = "weight", nullable = false)
    private double weight;

    public Baggage() {}

    public Baggage(double weight) {
        this.weight = weight;
    }

    public Baggage(int baggageId, double weight) {
        this.baggageId = baggageId;
        this.weight = weight;
    }

    // Геттеры и сеттеры

    public int getBaggageId() {
        return baggageId;
    }

    public void setBaggageId(int baggageId) {
        this.baggageId = baggageId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public static double getMaxWeight() {
        return MAX_WEIGHT;
    }

    public static double getCostPerKg() {
        return COST_PER_KG;
    }

    public double calculateBaggageCost() {
        return weight * COST_PER_KG;
    }
}
