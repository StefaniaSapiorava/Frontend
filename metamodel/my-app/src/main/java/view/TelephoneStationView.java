package view;

import model.Abonent;
import model.Service;
import java.util.List;
import java.util.Scanner;

public class TelephoneStationView {
    private final Scanner scanner;

    public TelephoneStationView() {
        scanner = new Scanner(System.in);
    }

    // Method to display the main menu
    public int showMainMenu() {
        System.out.println("\nЧто вы хотите сделать?");
        System.out.println("1. Добавить абонента");
        System.out.println("2. Получить список всех абонентов");
        System.out.println("3. Добавить услугу");
        System.out.println("4. Получить список всех услуг");
        System.out.println("5. Добавить аккаунт");
        System.out.println("6. Получить баланс абонента");
        System.out.println("7. Добавить услугу для абонента");
        System.out.println("8. Выйти");

        System.out.print("Введите номер действия: ");
        String choiceStr = scanner.nextLine();
        int choice = -1;
        try {
            choice = Integer.parseInt(choiceStr);
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод. Пожалуйста, введите число.");
        }
        return choice;
    }

    // Method to display a message
    public void showMessage(String message) {
        System.out.println(message);
    }

    // Methods to prompt for user input
    public long promptAbonentId() {
        System.out.print("Введите ID абонента: ");
        return Long.parseLong(scanner.nextLine());
    }

    public String promptAbonentName() {
        System.out.print("Введите имя абонента: ");
        return scanner.nextLine();
    }

    public String promptAbonentPhone() {
        System.out.print("Введите телефон абонента: ");
        return scanner.nextLine();
    }

    public String promptAbonentEmail() {
        System.out.print("Введите email абонента: ");
        return scanner.nextLine();
    }

    public int promptIsBlockedStatus() {
        System.out.print("Статус блокировки (0 - не заблокирован, 1 - заблокирован): ");
        return Integer.parseInt(scanner.nextLine());
    }

    public long promptServiceId() {
        System.out.print("Введите ID услуги: ");
        return Long.parseLong(scanner.nextLine());
    }

    public String promptServiceName() {
        System.out.print("Введите название услуги: ");
        return scanner.nextLine();
    }

    public String promptServiceDescription() {
        System.out.print("Введите описание услуги: ");
        return scanner.nextLine();
    }

    public double promptServiceCost() {
        System.out.print("Введите стоимость услуги: ");
        return Double.parseDouble(scanner.nextLine());
    }

    public double promptAccountBalance() {
        System.out.print("Введите начальный баланс: ");
        return Double.parseDouble(scanner.nextLine());
    }

    // Method to display abonent details
    public void displayAbonentDetails(Abonent abonent) {
        System.out.println("Абонент ID: " + abonent.getId());
        System.out.println("Имя: " + abonent.getName());
        System.out.println("Телефон: " + abonent.getPhone());
        System.out.println("Email: " + abonent.getEmail());
        System.out.println("Заблокирован: " + (abonent.isBlocked() ? "Да" : "Нет"));
    }

    // Method to display a list of abonents
    public void displayAbonents(List<Abonent> abonents) {
        System.out.println("Список абонентов:");
        for (Abonent abonent : abonents) {
            displayAbonentDetails(abonent);
            System.out.println();
        }
    }

    // Method to display service details
    public void displayServiceDetails(Service service) {
        System.out.println("Услуга ID: " + service.getId());
        System.out.println("Название: " + service.getName());
        System.out.println("Описание: " + service.getDescription());
        System.out.println("Стоимость: " + service.getCost());
    }

    // Method to display a list of services
    public void displayServices(List<Service> services) {
        System.out.println("Список услуг:");
        for (Service service : services) {
            displayServiceDetails(service);
            System.out.println();
        }
    }

    // Method to prompt for additional inputs if needed
    public int promptAbonentIdForService() {
        System.out.print("Введите ID абонента для добавления услуги: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public int promptServiceIdForAbonent() {
        System.out.print("Введите ID услуги для абонента: ");
        return Integer.parseInt(scanner.nextLine());
    }
}
