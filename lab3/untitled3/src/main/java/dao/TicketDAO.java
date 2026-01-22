package dao;

import exceptions.ConnectionPoolException;
import flights.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class TicketDAO {
    private static final Logger logger = LogManager.getLogger(TicketDAO.class);
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AirlineSystemPU");

    // Метод для добавления билета
    public void addTicket(Ticket ticket) throws ConnectionPoolException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(ticket);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Ошибка при добавлении билета", e);
            transaction.rollback();
            throw new ConnectionPoolException("Ошибка при добавлении билета", e);
        } finally {
            entityManager.close();
        }
    }

    // Метод для получения количества билетов для указанного рейса
    public int getTicketCountByFlight(String flightNumber) throws ConnectionPoolException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        int count;
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(t) FROM Ticket t WHERE t.flight.flightNumber = :flightNumber", Long.class);
            query.setParameter("flightNumber", flightNumber);
            count = query.getSingleResult().intValue();
        } catch (Exception e) {
            logger.error("Ошибка при получении количества билетов для рейса " + flightNumber, e);
            throw new ConnectionPoolException("Ошибка при получении количества билетов для рейса " + flightNumber, e);
        } finally {
            entityManager.close();
        }
        return count;
    }
}
