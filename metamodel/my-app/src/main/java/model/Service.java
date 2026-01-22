package model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Service.class)
@Entity
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private long id;     

    @Column(name = "name", nullable = false)
    private String name;             

    @Column(name = "description", nullable = true)
    private String description;       

    @Column(name = "cost", nullable = false)
    private BigDecimal cost;         

    public Service() {}

    public Service(String name, String description, BigDecimal cost) {
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    public Service(long id, String name, String description, BigDecimal cost) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }
}
