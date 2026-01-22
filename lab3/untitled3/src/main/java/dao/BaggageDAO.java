package dao;

import exceptions.ConnectionPoolException;
import flights.Baggage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class BaggageDAO {
    private static final Logger logger = LogManager.getLogger(BaggageDAO.class);
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AirlineSystemPU");

    public void addBaggage(Baggage baggage) throws ConnectionPoolException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin(); // Начинаем транзакцию
            entityManager.persist(baggage);         // Сохраняем объект багажа
            entityManager.getTransaction().commit(); // Фиксируем транзакцию
        } catch (Exception e) {
            logger.error("Ошибка при добавлении багажа", e);
            entityManager.getTransaction().rollback(); // Откатываем транзакцию при ошибке
            throw new ConnectionPoolException("Ошибка при добавлении багажа", e);
        } finally {
            entityManager.close(); // Закрываем EntityManager
        }
    }
}
