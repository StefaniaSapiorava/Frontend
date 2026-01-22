package Database;

import exceptions.ConnectionPoolExceptions;
import model.AbonentService;
import model.AbonentService_;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityTransaction;

import java.net.URL;
import java.time.LocalDateTime;

public class AbonentServiceDAO {
    private static final Logger logger = LogManager.getLogger(AbonentServiceDAO.class);
    private static final EntityManagerFactory entityManagerFactory;

    static {
        try {
            // Check for the presence of persistence.xml in META-INF
            URL resource = AbonentServiceDAO.class.getClassLoader().getResource("META-INF/persistence.xml");
            if (resource == null) {
                System.out.println("persistence.xml not found");
            } else {
                System.out.println("persistence.xml found at: " + resource.getPath());
            }

            System.out.println("Attempting to initialize EntityManagerFactory...");
            logger.info("Initializing EntityManagerFactory with persistence unit name 'YourPersistenceUnitName'");

            entityManagerFactory = Persistence.createEntityManagerFactory("YourPersistenceUnitName");
            logger.info("EntityManagerFactory created successfully.");
        } catch (Exception e) {
            logger.error("Failed to create EntityManagerFactory", e);
            throw new ExceptionInInitializerError("EntityManagerFactory initialization failed");
        }
    }

    private EntityManager getEntityManager() throws ConnectionPoolExceptions {
        if (entityManagerFactory == null) {
            logger.error("EntityManagerFactory not initialized. Check persistence.xml configuration.");
            throw new ConnectionPoolExceptions("EntityManagerFactory not initialized.");
        }
        return entityManagerFactory.createEntityManager();
    }

    public void addAbonentService(long abonentId, long serviceId) throws ConnectionPoolExceptions {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            AbonentService abonentService = new AbonentService();
            abonentService.setAbonentId(abonentId); // Set the abonent ID
            abonentService.setId(serviceId); // Set the service ID
            abonentService.setDateAdded(LocalDateTime.now()); // Set the current date and time

            logger.info("Attempting to add abonent-service relationship: Abonent ID = " + abonentId + ", Service ID = " + serviceId);
            entityManager.persist(abonentService); // Persist the relationship
            transaction.commit();

            logger.info("AbonentService added: Abonent ID = " + abonentId + ", Service ID = " + serviceId); // Log successful addition
        } catch (Exception e) {
            logger.error("Error while adding abonent-service relationship: Abonent ID = " + abonentId + ", Service ID = " + serviceId, e);
            if (transaction.isActive()) {
                transaction.rollback(); 
            }
            throw new ConnectionPoolExceptions("Error while adding abonent-service relationship.", e);
        } finally {
            entityManager.close(); 
        }
    }
}
