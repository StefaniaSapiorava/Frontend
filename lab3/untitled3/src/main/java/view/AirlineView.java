package view;

import flights.Flight;
import flights.Ticket;
import java.util.List;
import java.util.Scanner;
import comparators.*;
import java.time.format.DateTimeFormatter;

public class AirlineView {
    private Scanner scanner;

    public AirlineView() {
        scanner = new Scanner(System.in);
    }

    public int showMainMenu() {
        System.out.println("\nЧто вы хотите сделать?");
        System.out.println("1. Найти рейс по номеру");
        System.out.println("2. Купить билет на рейс");
        System.out.println("3. Вывести количество купленных билетов на заданный рейс");
        System.out.println("4. Вывести информацию о багаже на заданный рейс");
        System.out.println("5. Вывести процент увеличения стоимости билета");
        System.out.println("6. Добавить рейс");
        System.out.println("7. Обновить информацию о свободных местах на рейс");
        System.out.println("8. Выйти");

        System.out.print("Введите номер действия: ");
        String choiceStr = scanner.nextLine();
        int choice = -1;
        try {
            choice = Integer.parseInt(choiceStr);
        } catch (NumberFormatException e) {
        }
        return choice;
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public String promptFlightNumber() {
        System.out.print("Введите номер рейса: ");
        return scanner.nextLine();
    }

    public String promptFlightNumberForTicket() {
        System.out.print("Введите номер рейса, на который хотите купить билет: ");
        return scanner.nextLine();
    }

    public double promptBaggageWeight() {
        System.out.print("Укажите вес багажа: ");
        String input = scanner.nextLine();
        return Double.parseDouble(input);
    }

    public boolean promptPriorityService() {
        System.out.print("Добавить приоритетное обслуживание? (да/нет): ");
        String input = scanner.nextLine();
        return input.equalsIgnoreCase("да");
    }

    public void displayFlightDetails(Flight flight) {
        System.out.println("Рейс: " + flight.getFlightNumber());
        System.out.println("Пункт назначения: " + flight.getDestination());
        System.out.println("Дата вылета: " + flight.getDepartureDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        System.out.println("Свободные места: " + flight.getAvailableSeats());
        System.out.println("Цена: " + flight.getAdjustedPrice());
    }

    public void displayTicketDetails(Ticket ticket) {
        System.out.println("Билет на рейс: " + ticket.getFlight().getFlightNumber());
        System.out.println("Пассажир: " + ticket.getPassenger().getName());
        System.out.println("Стоимость билета: " + ticket.getPrice());
    }

    public void showTicketCount(String flightNumber, int ticketCount) {
        System.out.println("Количество проданных билетов на рейс " + flightNumber + ": " + ticketCount);
    }

    public void displayBaggageInfo(double maxWeight, double costPerKg) {
        System.out.println("Максимальный допустимый вес багажа: " + maxWeight + " кг");
        System.out.println("Стоимость провоза багажа: " + costPerKg + " за кг");
    }

    public void displayPriceIncrease(String flightNumber, double increasePercentage) {
        System.out.println("Процент увеличения стоимости билета на рейс " + flightNumber + ": " + increasePercentage + "%");
    }

    public Flight promptNewFlightDetails() {
        System.out.print("Введите номер нового рейса: ");
        String flightNumber = scanner.nextLine();
        System.out.print("Введите пункт назначения: ");
        String destination = scanner.nextLine();
        System.out.print("Введите базовую цену: ");
        double basePrice = Double.parseDouble(scanner.nextLine());
        System.out.print("Введите количество свободных мест: ");
        int availableSeats = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите количество дней до вылета: ");
        int daysToFlight = Integer.parseInt(scanner.nextLine());
        java.time.LocalDateTime departureDate = java.time.LocalDateTime.now().plusDays(daysToFlight);
        Flight flight = new Flight(flightNumber, destination, basePrice, departureDate, availableSeats);
        return flight;
    }

    public int promptNewSeatCount() {
        System.out.print("Введите новое количество свободных мест: ");
        int newSeats = Integer.parseInt(scanner.nextLine());
        return newSeats;
    }

    public String promptPassengerName() {
        System.out.print("Введите ваше имя: ");
        return scanner.nextLine();
    }

    public void displayFlights(List<Flight> flights) {
        System.out.println("Сортировка по цене:");
        flights.sort(new FlightPriceComparator());
        for (Flight flight : flights) {
            System.out.println("Рейс: " + flight.getFlightNumber() + " Цена: " + flight.getAdjustedPrice());
        }

        System.out.println("\nСортировка по номеру рейса:");
        flights.sort(new FlightNumberComparator());
        for (Flight flight : flights) {
            System.out.println("Рейс: " + flight.getFlightNumber() + " Цена: " + flight.getAdjustedPrice());
        }

        System.out.println("\nДетали всех рейсов\n");
        for (Flight flight : flights) {
            displayFlightDetails(flight);
            System.out.println();
        }
    }
}
