package model;

import jakarta.persistence.*;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Account.class)
@Entity
@Table(name = "account")
public class Account {
    @Id 
    @Column(name = "abonent_id", nullable = false)
    private long abonentId;          

    @Column(name = "balance", nullable = false)
    private double balance;           

    public Account() {}

    public Account(long abonentId, double balance) {
        this.abonentId = abonentId;
        this.balance = balance;
    }

    // Getters and setters
    public long getAbonentId() {
        return abonentId;
    }

    public void setAbonentId(long abonentId) {
        this.abonentId = abonentId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "abonentId=" + abonentId +
                ", balance=" + balance +
                '}';
    }
}
