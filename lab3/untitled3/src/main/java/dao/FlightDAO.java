package dao;

import exceptions.ConnectionPoolException;
import flights.Flight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityTransaction;

import java.net.URL;
import java.util.List;

public class FlightDAO {
    private static final Logger logger = LogManager.getLogger(FlightDAO.class);
    private static final EntityManagerFactory entityManagerFactory;

    static {
        try {
            // Проверяем наличие persistence.xml в пути META-INF
            URL resource = FlightDAO.class.getClassLoader().getResource("META-INF/persistence.xml");
            if (resource == null) {
                System.out.println("persistence.xml not found");
            } else {
                System.out.println("persistence.xml found at: " + resource.getPath());
            }

            System.out.println("Attempting to initialize EntityManagerFactory...");
            logger.info("Initializing EntityManagerFactory with persistence unit name 'AirlineSystemPU'");

            entityManagerFactory = Persistence.createEntityManagerFactory("AirlineSystemPU");
            logger.info("EntityManagerFactory created successfully.");
        } catch (Exception e) {
            logger.error("Failed to create EntityManagerFactory", e);
            throw new ExceptionInInitializerError("EntityManagerFactory initialization failed");
        }
    }

    private EntityManager getEntityManager() throws ConnectionPoolException {
        if (entityManagerFactory == null) {
            logger.error("EntityManagerFactory not initialized. Check persistence.xml configuration.");
            throw new ConnectionPoolException("EntityManagerFactory not initialized.");
        }
        return entityManagerFactory.createEntityManager();
    }

    // Получить все рейсы
    public List<Flight> getAllFlights() throws ConnectionPoolException {
        EntityManager entityManager = getEntityManager();
        List<Flight> flights;
        try {
            logger.info("Attempting to retrieve all flights from the database.");
            TypedQuery<Flight> query = entityManager.createQuery("SELECT f FROM Flight f", Flight.class);
            flights = query.getResultList();
        } catch (Exception e) {
            logger.error("Ошибка при получении рейсов", e);
            throw new ConnectionPoolException("Ошибка при получении рейсов", e);
        } finally {
            entityManager.close();
        }
        return flights;
    }

    // Получить рейс по номеру
    public Flight getFlightByNumber(String flightNumber) throws ConnectionPoolException {
        EntityManager entityManager = getEntityManager();
        Flight flight;
        try {
            logger.info("Attempting to retrieve flight by number: " + flightNumber);
            TypedQuery<Flight> query = entityManager.createQuery("SELECT f FROM Flight f WHERE f.flightNumber = :flightNumber", Flight.class);
            query.setParameter("flightNumber", flightNumber);
            flight = query.getSingleResult();
        } catch (Exception e) {
            logger.error("Ошибка при получении рейса по номеру", e);
            throw new ConnectionPoolException("Ошибка при получении рейса по номеру", e);
        } finally {
            entityManager.close();
        }
        return flight;
    }

    // Добавить новый рейс
    public void addFlight(Flight flight) throws ConnectionPoolException {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            logger.info("Attempting to add a new flight: " + flight);
            entityManager.persist(flight);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Ошибка при добавлении рейса!", e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new ConnectionPoolException("Ошибка при добавлении рейса!", e);
        } finally {
            entityManager.close();
        }
    }

    // Обновить количество свободных мест на рейс
    public void updateAvailableSeats(String flightNumber, int newSeats) throws ConnectionPoolException {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            logger.info("Attempting to update available seats for flight number: " + flightNumber);
            Flight flight = entityManager.find(Flight.class, flightNumber);
            if (flight != null) {
                flight.setAvailableSeats(newSeats);
                entityManager.merge(flight);
            }
            transaction.commit();
        } catch (Exception e) {
            logger.error("Ошибка при обновлении свободных мест для рейса " + flightNumber, e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new ConnectionPoolException("Ошибка при обновлении свободных мест для рейса " + flightNumber, e);
        } finally {
            entityManager.close();
        }
    }

    // Удалить рейс по номеру
    public void deleteFlightByNumber(String flightNumber) throws ConnectionPoolException {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            logger.info("Attempting to delete flight with number: " + flightNumber);
            Flight flight = entityManager.find(Flight.class, flightNumber);
            if (flight != null) {
                entityManager.remove(flight);
            }
            transaction.commit();
        } catch (Exception e) {
            logger.error("Ошибка при удалении рейса " + flightNumber, e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new ConnectionPoolException("Ошибка при удалении рейса " + flightNumber, e);
        } finally {
            entityManager.close();
        }
    }
}
