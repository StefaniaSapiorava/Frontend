package Interface;

import java.util.List;
import java.util.Scanner;

import Control.Bank;
import Entity.Account;
import Entity.Payment;

public class Administrator {

    private String name;
    private String contactInfo;

    public Administrator() {}
    public Administrator(String name, String contactInfo) {
        this.setName(name);
        this.setContactInfo(contactInfo);
    }
    
    public String 	getName() { return name; }
    public String 	getContactInfo() { return contactInfo; }

    public void 	setName(String name) { this.name = name; }
    public void 	setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }


    public void reviewPayment(Payment payment) throws Exception {
        System.out.println("\n - - - - - Administration interface - - - - - ");
        System.out.println("Information about payment:");
        System.out.println("From: " + payment.getReciever().getClient().getName());
        System.out.println("To: " + payment.getSender().getClient().getName());
        System.out.println("Amount: " + payment.getAmount());
        System.out.println("Confirmation (enter \'1\' for yes, otherwise for no): ");
        Scanner input = new Scanner(System.in);
    	int choice = input.nextInt(); 	
    	if (choice == 1) {
    		Bank.getInstance().recieveAdministratorPayment(payment);
    		System.out.println("Success!");
    	} else {
    		Bank.getInstance().declinePayment(payment);
    	}
    	input.close();      
        System.out.println(" - - - - - Administration interface - - - - - ");
    }
    
    public void viewClientAccounts(Client client) throws Exception {
    	System.out.println("\n - - - - - Administration interface - - - - - ");
        System.out.println("Information about accounts:");
        List<Account> Accounts = Bank.getInstance().viewClientAccounts(client);   
        int count = 0;
        for (Account account : Accounts) {
        	count++;
        	System.out.println("Account " + account.getAccountNumber());
        }
        if (count == 0) {
        	System.out.println("No accounts");
        }
        System.out.println(" - - - - - Administration interface - - - - - ");
    }
    
    public void viewClientPayments(Client client) throws Exception {
    	System.out.println("\n - - - - - Administration interface - - - - - ");
        System.out.println("Information about payments:");
        List<Payment> Payments = Bank.getInstance().viewClientPayments(client);   
        int count = 0;
        for (Payment payment : Payments) {
        	count++;
        	System.out.println("Payment to  " + payment.getReciever().getClient().getName() + " on amount of " + payment.getAmount());
        }
        if (count == 0) {
        	System.out.println("No payments");
        } 
        System.out.println(" - - - - - Administration interface - - - - - ");
    }
	
}
