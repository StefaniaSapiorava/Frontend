package controller;

import Database.AbonentDAO;
import Database.AccountDAO;
import Database.AbonentServiceDAO;
import Database.ServiceDAO;
import exceptions.ConnectionPoolExceptions;
import Database.ConnectionPool;
import model.Abonent;
import model.Service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TelephoneStationController {
    private final AbonentDAO abonentDAO;
    private final AccountDAO accountDAO;
    private final ServiceDAO serviceDAO;
    private final AbonentServiceDAO abonentServiceDAO;
    private final Scanner scanner;

    public TelephoneStationController() {
        abonentDAO = new AbonentDAO();
        accountDAO = new AccountDAO();
        serviceDAO = new ServiceDAO();
        abonentServiceDAO = new AbonentServiceDAO();
        scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;

        while (running) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Добавить абонента");
            System.out.println("2. Получить всех абонентов");
            System.out.println("3. Добавить услугу");
            System.out.println("4. Получить все услуги");
            System.out.println("5. Добавить аккаунт");
            System.out.println("6. Получить баланс абонента");
            //System.out.println("7. Добавить связь абонента с услугой");
            System.out.println("0. Выход");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addAbonent();
                    break;
                case 2:
                    getAllAbonents();
                    break;
                case 3:
                    addService();
                    break;
                case 4:
                    getAllServices();
                    break;
                case 5:
                    addAccount();
                    break;
                case 6:
                    getAbonentBalance();
                    break;
                case 7:
                    addAbonentService();
                    break;
                case 0:
                    running = false;
                    System.out.println("Выход из программы...");
                    break;
                default:
                    System.out.println("Неверный выбор, пожалуйста, попробуйте снова.");
            }
        }

        scanner.close(); // Close scanner when done
    }

    private void addAbonent() {
        Abonent abonent = new Abonent();
        System.out.print("Введите ID абонента: ");
        abonent.setId(scanner.nextLong());
        System.out.print("Введите имя: ");
        abonent.setName(scanner.next());
        System.out.print("Введите телефон: ");
        abonent.setPhone(scanner.next());
        System.out.print("Введите email: ");
        abonent.setEmail(scanner.next());
        System.out.print("Статус блокировки (0 - не заблокирован, 1 - заблокирован): ");
        abonent.setIsBlocked(scanner.nextInt());

        try {
            abonentDAO.addAbonent(abonent);
            System.out.println("Абонент добавлен!");
        } catch (ConnectionPoolExceptions e) {
            System.err.println("Ошибка при добавлении абонента: " + e.getMessage());
            // Вы можете также логировать исключение, если у вас есть настроенный логгер
        } catch (Exception e) {
            System.err.println("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }
    
    private void getAllAbonents() {
        try {
            List<Abonent> abonents = abonentDAO.getAllAbonents(); // Fetch all abonents from the database
            System.out.println("Список абонентов:");
            for (Abonent abonent : abonents) {
                System.out.println(abonent); // Assuming the Abonent class has a proper toString method
            }
        } catch (ConnectionPoolExceptions e) {
            System.err.println("Ошибка при получении списка абонентов: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
        }
    }

    private void addService() {
        Service service = new Service();
        try {
            System.out.print("Введите ID услуги: ");
            service.setId(scanner.nextLong());
            System.out.print("Введите название услуги: ");
            service.setName(scanner.next());
            System.out.print("Введите описание услуги: ");
            service.setDescription(scanner.next());
            System.out.print("Введите стоимость услуги: ");
            service.setCost(BigDecimal.valueOf(scanner.nextDouble()));
            
            // Attempt to add the service
            serviceDAO.addService(service);
            System.out.println("Услуга добавлена!");
            
        } catch (ConnectionPoolExceptions e) {
            System.err.println("Ошибка при добавлении услуги: " + e.getMessage());
            // Log the error if necessary
        } catch (Exception e) {
            System.err.println("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }


    private void getAllServices() {
        try {
            List<Service> services = serviceDAO.getAllServices(); // This may throw a ConnectionPoolException
            System.out.println("Список услуг:");
            for (Service s : services) {
                System.out.println(s);
            }
        } catch (ConnectionPoolExceptions e) {
            System.err.println("Ошибка при получении списка услуг: " + e.getMessage());
            // Optionally log the error or provide additional feedback to the user
        } catch (Exception e) {
            System.err.println("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }


    private void addAccount() {
        try {
            System.out.print("Введите ID абонента для аккаунта: ");
            int abonentId = scanner.nextInt();
            System.out.print("Введите начальный баланс: ");
            double balance = scanner.nextDouble();
            accountDAO.addAccount(abonentId, balance);
            System.out.println("Аккаунт добавлен!");
        } catch (ConnectionPoolExceptions e) {
            System.err.println("Ошибка при добавлении аккаунта: " + e.getMessage());
            // Optional: Log the error or rethrow it as a different exception
        } catch (InputMismatchException e) {
            System.err.println("Ошибка ввода. Пожалуйста, убедитесь, что вы вводите корректные данные.");
            // Clear the scanner buffer to avoid infinite loop on wrong input
            scanner.nextLine();
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
        }
    }
    
    private void getAbonentBalance() {
        try {
            System.out.print("Введите ID абонента для получения баланса: ");
            int abonentId = scanner.nextInt();
            double currentBalance = accountDAO.getBalance(abonentId);
            System.out.println("Баланс абонента: " + currentBalance);
        } catch (ConnectionPoolExceptions e) {
            System.err.println("Ошибка при получении баланса: " + e.getMessage());
            // Optional: Log the error or take additional actions
        } catch (InputMismatchException e) {
            System.err.println("Ошибка ввода. Пожалуйста, введите корректный ID абонента.");
            // Clear the scanner buffer to avoid infinite loop on wrong input
            scanner.nextLine();
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
        }
    }


    private void addAbonentService() {
        try {
            System.out.print("Введите ID абонента: ");
            int abonentId = scanner.nextInt();
            System.out.print("Введите ID услуги: ");
            int serviceId = scanner.nextInt();
            abonentServiceDAO.addAbonentService(abonentId, serviceId);
            System.out.println("Связь абонента с услугой добавлена!");
        } catch (ConnectionPoolExceptions e) {
            System.err.println("Ошибка при добавлении связи абонента с услугой: " + e.getMessage());
            // Optional: Log the error or take additional actions
        } catch (InputMismatchException e) {
            System.err.println("Ошибка ввода. Пожалуйста, введите корректные ID.");
            scanner.nextLine(); // Clear the buffer to avoid infinite loop on wrong input
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
        }
    }

}
