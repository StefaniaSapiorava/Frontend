package controller;

import dao.*;
import flights.*;
import view.AirlineView;

import java.util.ArrayList;
import java.util.List;

public class AirlineController {
    private final FlightDAO flightDAO;
    private final PassengerDAO passengerDAO;
    private final TicketDAO ticketDAO;
    private final BaggageDAO baggageDAO;
    private final AirlineView view;
    private List<Flight> flights;
    private Passenger currentPassenger;

    public AirlineController() {
        flightDAO = new FlightDAO();
        passengerDAO = new PassengerDAO();
        ticketDAO = new TicketDAO();
        baggageDAO = new BaggageDAO();
        view = new AirlineView();
        flights = new ArrayList<>();
        currentPassenger = null;
    }

    public void run() {
        try {
            flights = flightDAO.getAllFlights();
            if (flights.isEmpty()) {
                view.showMessage("В базе данных нет доступных рейсов. Пожалуйста, добавьте новые рейсы.");
            }
        } catch (Exception e) {
            view.showMessage("Ошибка при загрузке рейсов из базы данных.");
            e.printStackTrace();
        }

        view.displayFlights(flights);
        boolean running = true;
        while (running) {
            int choice = view.showMainMenu();
            switch (choice) {
                case 1 -> findFlight();
                case 2 -> buyTicket();
                case 3 -> printTicketCount();
                case 4 -> printBaggageInfo();
                case 5 -> printPriceIncrease();
                case 6 -> addFlight();
                case 7 -> updateSeats();
                case 8 -> {
                    running = false;
                    view.showMessage("Выход из программы.");
                }
                default -> view.showMessage("Неверная команда, попробуйте снова.");
            }
        }
    }

    private void findFlight() {
        String flightNumber = view.promptFlightNumber();
        try {
            Flight flight = flightDAO.getFlightByNumber(flightNumber);
            if (flight != null) {
                view.displayFlightDetails(flight);
            } else {
                view.showMessage("Рейс не найден.");
            }
        } catch (Exception e) {
            view.showMessage("Ошибка при поиске рейса.");
            e.printStackTrace();
        }
    }

    private void buyTicket() {
        String flightNumber = view.promptFlightNumberForTicket();
        try {
            Flight flight = flightDAO.getFlightByNumber(flightNumber);
            if (flight != null) {
                String passengerName = view.promptPassengerName();
                Passenger passenger = passengerDAO.getPassengerByName(passengerName);
                if (passenger == null) {
                    passenger = new Passenger(passengerName);
                    passengerDAO.addPassenger(passenger);
                }
                currentPassenger = passenger;

                double baggageWeight = view.promptBaggageWeight();
                Baggage baggage = new Baggage(baggageWeight);
                baggageDAO.addBaggage(baggage);

                Ticket ticket = new Ticket(flight, currentPassenger, baggage);
                ticketDAO.addTicket(ticket);

                int newAvailableSeats = flight.getAvailableSeats() - 1;
                flight.setAvailableSeats(newAvailableSeats);

                flightDAO.updateAvailableSeats(flight.getFlightNumber(), newAvailableSeats);

                view.showMessage("Билет успешно куплен!");
                view.displayTicketDetails(ticket);
            } else {
                view.showMessage("Рейс с таким номером не найден.");
            }
        } catch (Exception e) {
            view.showMessage("Ошибка при покупке билета.");
            e.printStackTrace();
        }
    }

    private void printTicketCount() {
        String flightNumber = view.promptFlightNumber();
        try {
            Flight flight = flightDAO.getFlightByNumber(flightNumber);
            if (flight != null) {
                int ticketCount = ticketDAO.getTicketCountByFlight(flightNumber);
                view.showTicketCount(flightNumber, ticketCount);
            } else {
                view.showMessage("Рейс не найден.");
            }
        } catch (Exception e) {
            view.showMessage("Ошибка при получении количества билетов.");
            e.printStackTrace();
        }
    }

    private void printBaggageInfo() {
        String flightNumber = view.promptFlightNumber();
        try {
            Flight flight = flightDAO.getFlightByNumber(flightNumber);
            if (flight != null) {
                view.displayBaggageInfo(Baggage.getMaxWeight(), Baggage.getCostPerKg());
            } else {
                view.showMessage("Рейс не найден.");
            }
        } catch (Exception e) {
            view.showMessage("Ошибка при получении информации о багаже.");
            e.printStackTrace();
        }
    }

    private void printPriceIncrease() {
        String flightNumber = view.promptFlightNumber();
        try {
            Flight flight = flightDAO.getFlightByNumber(flightNumber);
            if (flight != null) {
                double originalPrice = flight.getBasePrice();
                double adjustedPrice = flight.getAdjustedPrice();
                double increasePercentage = ((adjustedPrice - originalPrice) / originalPrice) * 100;
                view.displayPriceIncrease(flightNumber, increasePercentage);
            } else {
                view.showMessage("Рейс не найден.");
            }
        } catch (Exception e) {
            view.showMessage("Ошибка при расчете увеличения стоимости.");
            e.printStackTrace();
        }
    }

    private void addFlight() {
        Flight newFlight = view.promptNewFlightDetails();
        try {
            flightDAO.addFlight(newFlight);
            flights.add(newFlight);
            view.showMessage("Рейс успешно добавлен!");
        } catch (Exception e) {
            view.showMessage("Ошибка при добавлении рейса.");
            e.printStackTrace();
        }
    }

    private void updateSeats() {
        String flightNumber = view.promptFlightNumber();
        try {
            Flight flight = flightDAO.getFlightByNumber(flightNumber);
            if (flight != null) {
                int newSeats = view.promptNewSeatCount();
                flight.setAvailableSeats(newSeats);
                flightDAO.updateAvailableSeats(flightNumber, newSeats);
                view.showMessage("Количество свободных мест на рейс " + flightNumber + " обновлено.");
            } else {
                view.showMessage("Рейс не найден.");
            }
        } catch (Exception e) {
            view.showMessage("Ошибка при обновлении свободных мест.");
            e.printStackTrace();
        }
    }
}
