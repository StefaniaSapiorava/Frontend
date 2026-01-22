package Entity;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import Interface.Client;

public class Account implements Comparator<Account>, Comparable<Account> {
	private int id;
    private String accountNumber;
    private double balance;
    private Client client;

	public Account() {}
    public Account(int id, String accountNumber, double balance, Client client) {
    	this.setId(id);
        this.setAccountNumber(accountNumber);
        this.setBalance(balance);
        this.setClient(client);
    }
    
	public String getAccountNumber() { return accountNumber; }
	public double getBalance() { return balance; }
	
	public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
	public void setBalance(double balance) { this.balance = balance; }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	static public Optional<Account> findEditionById(List<Account> accounts, String accountNumber) {
        return accounts.stream().filter(account -> account.getAccountNumber() == accountNumber).findFirst();
    }

	@Override
	public int compare(Account first, Account second) {
		
		return first.getAccountNumber().compareTo(second.getAccountNumber());
	}

	@Override
	public int compareTo(Account other) {
		return this.getAccountNumber().compareTo(other.getAccountNumber());
	}
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}

}
