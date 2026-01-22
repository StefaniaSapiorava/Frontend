package by.bsu.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "abonent")
@NamedQuery(name = "Abonent.findAll", query = "SELECT a FROM Abonent a")
public class Abonent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;      

    @Column(name = "name", nullable = false)
    private String name;    

    @Column(name = "phone", nullable = false)
    private String phone;    

    @Column(name = "email", nullable = false)
    private String email;   

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;   

    public Abonent() {}

    public Abonent(String name, String phone, String email, boolean isBlocked) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.isBlocked = isBlocked;
    }

    public Abonent(long id, String name, String phone, String email, boolean isBlocked) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.isBlocked = isBlocked;
    }

    // Getter and setter for ID
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for phone
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Getter and setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and setter for block status
    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }
    
    public void setIsBlocked(int blocked) {
        this.isBlocked = (blocked != 0); // Assuming 0 means not blocked and any non-zero means blocked
    }

    @Override
    public String toString() {
        return "Abonent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", isBlocked=" + isBlocked +
                '}';
    }
}
