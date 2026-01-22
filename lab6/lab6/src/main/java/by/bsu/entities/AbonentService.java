package by.bsu.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "abonent_service")
public class AbonentService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private long id;               

    @Column(name = "abonent_id", nullable = false)
    private long abonentId;         

    @Column(name = "date_added", nullable = false)
    private LocalDateTime dateAdded; 

    public AbonentService() {}

    public AbonentService(long abonentId, LocalDateTime dateAdded) {
        this.abonentId = abonentId;
        this.dateAdded = dateAdded;
    }

    public AbonentService(long id, long abonentId, LocalDateTime dateAdded) {
        this.id = id;
        this.abonentId = abonentId;
        this.dateAdded = dateAdded;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAbonentId() {
        return abonentId;
    }

    public void setAbonentId(long abonentId) {
        this.abonentId = abonentId;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "AbonentService{" +
                "id=" + id +
                ", abonentId=" + abonentId +
                ", dateAdded=" + dateAdded +
                '}';
    }
}
