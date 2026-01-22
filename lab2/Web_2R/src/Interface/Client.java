package Interface;

import Control.*;
import Entity.Account;

public class Client {
	private int 	id;
    private String 	name;
    private String 	contactInfo;
    
    public Client() {}
    public Client(int id, String name, String contactInfo) {
    	this.setId(id);
        this.setName(name);
        this.setContactInfo(contactInfo);
    }

    public int 		getId() { return id; }
    public String 	getName() { return name; }
    public String 	getContactInfo() { return contactInfo; }

	public void 	setId(int id) { this.id = id; }
    public void 	setName(String name) { this.name = name; }
    public void 	setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    public void makePayment(int id, String accountNumber, double value) throws Exception {
    	Bank.getInstance().recieveClientPayment(id, this.getId(), accountNumber, value);
    }
    
    public boolean closeAccount(String accountNumber) throws Exception {
    	Bank.getInstance().removeClientAccount(accountNumber);    	
    	return true;
    }
    
    public void viewAccountBalance(Account account) throws Exception {
    	System.out.println("\n - - - - - - Client interface - - - - - - ");
        System.out.println("Account balance: " + Bank.getInstance().viewAccountBalance(account));
        System.out.println(" - - - - - - Client interface - - - - - - ");
    }

}




