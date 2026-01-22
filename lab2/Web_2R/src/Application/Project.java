package Application;


import Control.*;
import Entity.*;
import Interface.*;
import Data.*;


/*
 //Initialisation Accounts.Client
 
 		Bank bank = Bank.getInstance();
		bank.addClient(new Client(1, "John Batist", "+8(201)93-239-231"));
		bank.addClient(new Client(2, "Petrarco Dermua", "+9(037)82-957-304"));
		bank.addClient(new Client(3, "Federicko Alienni", "+2(038)94-382-058"));
		bank.addClient(new Client(4, "Hanns Zymer", "+4(945)83-054-234"));
		
		 		
 		
  //Initialisation Accounts.Account
   
 		Bank bank = Bank.getInstance();
		bank.addAccount(new Account(1, "BS0249LD-29194125", 2000, new Client(1, "John Batist", "+8(201)93-239-231")));
		bank.addAccount(new Account(2, "BT0222WD-48192461", 1000, new Client(2, "Petrarco Dermua", "+9(037)82-957-304")));
		bank.addAccount(new Account(3, "BS0212DT-12314525", 4000, new Client(3, "Federicko Alienni", "+2(038)94-382-058")));
		bank.addAccount(new Account(4, "BS0292LO-29325245", 3000, new Client(4, "Hanns Zymer", "+4(945)83-054-234")));

 */

public class Project {

	public static void main(String[] args) throws Exception {
		
 		Bank bank = Bank.getInstance();
 		Administrator administrator = new Administrator();

 		// Вернуть в изначальное состояние для симуляции
		bank.addAccount(new Account(2, "BT0222WD-48192461", 4000, new Client(2, "Petrarco Dermua", "+9(037)82-957-304")));
		new DAOPayment().deletePayment(5);
		/*bank.recieveAdministratorPayment(new Payment(6,
										new Account(4, "BS0292LO-29325245", 3000, new Client(4, "Hanns Zymer", "+4(945)83-054-234")), 
										new Account(3, "BS0212DT-12314525", 4000, new Client(3, "Federicko Alienni", "+2(038)94-382-058")),
										3500));*/
		// Вернуть в изначальное состояние для симуляции
		
 		bank.getClient(2).closeAccount("BT0222WD-48192461");
 		bank.getClient(3).makePayment(5, "BS0292LO-29325245", 3500); 	
 		bank.getClient(3).viewAccountBalance(new Account(3, "BS0212DT-12314525", 4000, new Client(3, "Federicko Alienni", "+2(038)94-382-058")));
 		administrator.reviewPayment(bank.getPayment(5));
 		bank.getClient(3).viewAccountBalance(new Account(3, "BS0212DT-12314525", 4000, new Client(3, "Federicko Alienni", "+2(038)94-382-058")));
 		administrator.viewClientAccounts(bank.getClient(1));
 		administrator.viewClientPayments(bank.getClient(3));
 		
 		
 		
		
	}

}
