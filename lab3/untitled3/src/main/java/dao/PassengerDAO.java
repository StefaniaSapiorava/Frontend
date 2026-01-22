package dao;

import exceptions.ConnectionPoolException;
import flights.Passenger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class PassengerDAO {
    private static final Logger logger = LogManager.getLogger(PassengerDAO.class);
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AirlineSystemPU");

    // Получить пассажира по id
    public Passenger getPassengerById(int id) throws ConnectionPoolException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Passenger passenger;
        try {
            passenger = entityManager.find(Passenger.class, id);
        } catch (Exception e) {
            logger.error("Ошибка при получении пассажира по id", e);
            throw new ConnectionPoolException("Ошибка при получении пассажира по id", e);
        } finally {
            entityManager.close();
        }
        return passenger;
    }

    // Получить пассажира по имени
    public Passenger getPassengerByName(String name) throws ConnectionPoolException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Passenger passenger;
        try {
            TypedQuery<Passenger> query = entityManager.createQuery("SELECT p FROM Passenger p WHERE p.name = :name", Passenger.class);
            query.setParameter("name", name);
            passenger = query.getResultList().stream().findFirst().orElse(null);
        } catch (Exception e) {
            logger.error("Ошибка при получении пассажира по имени", e);
            throw new ConnectionPoolException("Ошибка при получении пассажира по имени", e);
        } finally {
            entityManager.close();
        }
        return passenger;
    }

    // Добавить пассажира
    public void addPassenger(Passenger passenger) throws ConnectionPoolException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(passenger);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Ошибка при добавлении пассажира", e);
            transaction.rollback();
            throw new ConnectionPoolException("Ошибка при добавлении пассажира", e);
        } finally {
            entityManager.close();
        }
    }
}
