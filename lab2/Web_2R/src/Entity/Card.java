package Entity;

import Interface.Client;

public class Card {
	private int id;
    private String cardNumber;
    private Account account;
    private Client client;
    
	public Card() {}
    public Card(int id, String cardNumber, Account account, Client client) {
    	this.setId(id);
        this.setCardNumber(cardNumber);
        this.setAccount(account);
        this.setClient(client);
    }
    
	public String getCardNumber() { return cardNumber; }
	public Account getAccount() { return account; }
	
	public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
	public void setAccount(Account account) { this.account = account; }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}

}
