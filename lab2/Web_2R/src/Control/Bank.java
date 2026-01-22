package Control;

import java.util.List;

import Data.*;
import Entity.*;
import Interface.*;

public class Bank {
	
    private static Bank instance;
    private Bank() {}
    public static synchronized Bank getInstance() {
        if (instance == null) { instance = new Bank(); }
        return instance;
    }
    
    public void addPayment(Payment payment) throws Exception { new DAOPayment().addPayment(payment); }
    public void addClient(Client client) throws Exception { new DAOClient().addClient(client); }
    public void addAccount(Account account) throws Exception { new DAOAccount().addAccount(account); }
    public void addCard(Card card) throws Exception { new DAOCard().addCard(card); }
    
    public Payment getPayment(int id) throws Exception { return new DAOPayment().getPayment(id); } 
    public Client getClient(int id) throws Exception { return new DAOClient().getClient(id); } 
    public Account getAccount(int id) throws Exception { return new DAOAccount().getAccount(id); } 
    public Account getAccount(String accountNumber) throws Exception { return new DAOAccount().getAccount(accountNumber); }
    public Card getCard(int id) throws Exception { return new DAOCard().getCard(id); } 
    
    public void recieveClientPayment(int id, int senderClientId, String recieverNumber, double amount) throws Exception {
    	this.addPayment(new Payment(id, this.getAccount(senderClientId), this.getAccount(recieverNumber), amount));
    }
    
    public void recieveAdministratorPayment(Payment payment) throws Exception {
    	Account sender = new DAOAccount().getAccount(payment.getSender().getId());
    	sender.setBalance(sender.getBalance()-payment.getAmount());
    	new DAOAccount().updateAccount(sender);
    	Account reciever = new DAOAccount().getAccount(payment.getReciever().getId());
    	reciever.setBalance(reciever.getBalance()+payment.getAmount());
    	new DAOAccount().updateAccount(reciever);
    }
    
    public void removeClientAccount(String accountNumber) throws Exception {
    	if (this.getAccount(accountNumber).getBalance() > 0) {
    		new DAOAccount().deleteAccount(this.getAccount(accountNumber).getId());
    	}
    }
    
    public void declinePayment(Payment payment) throws Exception {
    	new DAOPayment().deletePayment(payment);
    }
    
    public List<Account> viewClientAccounts(Client client) throws Exception {
		return new DAOAccount().getClientAccounts(client.getId());
    }
    
    public List<Payment> viewClientPayments(Client client) throws Exception {
    	return new DAOPayment().getClientPayments(client.getId());
    	
    }
   
    public double viewAccountBalance(Account account) throws Exception {
    	return new DAOAccount().getAccount(account.getAccountNumber()).getBalance();
    }
}
