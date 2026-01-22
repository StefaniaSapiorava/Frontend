package Entity;

public class Payment {
	
	private int id;
	private Account sender;
	private Account reciever;
	private double amount;
	
	public Payment() {}
    public Payment(int id, Account sender, Account reciever, double amount) {
    	this.setId(id);
        this.setSender(sender);
        this.setReciever(reciever);
        this.setAmount(amount);
    }	
	

	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Account getSender() {
		return sender;
	}
	public void setSender(Account sender) {
		this.sender = sender;
	}
	public Account getReciever() {
		return reciever;
	}
	public void setReciever(Account reciever) {
		this.reciever = reciever;
	}
}
