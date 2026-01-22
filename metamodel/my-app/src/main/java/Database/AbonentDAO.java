package Database;

import exceptions.ConnectionPoolExceptions;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.Abonent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityTransaction;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AbonentDAO {
    private static final Logger logger = LogManager.getLogger(AbonentDAO.class);
    private static final EntityManagerFactory entityManagerFactory;

    static {
        try {
            URL resource = AbonentDAO.class.getClassLoader().getResource("META-INF/persistence.xml");
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
            e.printStackTrace();
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

    // Method to add a new abonent
    public void addAbonent(Abonent abonent) throws ConnectionPoolExceptions {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            logger.info("Attempting to add a new abonent: " + abonent);
            entityManager.persist(abonent);
            transaction.commit();
            logger.info("Abonent added successfully.");
        } catch (Exception e) {
            logger.error("Error while adding abonent: " + abonent, e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new ConnectionPoolExceptions("Error while adding abonent", e);
        } finally {
            entityManager.close();
        }
    }

    // Method to get all abonents
    public List<Abonent> getAllAbonents() throws ConnectionPoolExceptions {
        EntityManager entityManager = getEntityManager();
        List<Abonent> abonents = new ArrayList<>();
        try {
            logger.info("Attempting to retrieve all abonents from the database.");
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Abonent> criteriaQuery = criteriaBuilder.createQuery(Abonent.class);
            Root<Abonent> root = criteriaQuery.from(Abonent.class);
            criteriaQuery.select(root);

            TypedQuery<Abonent> query = entityManager.createQuery(criteriaQuery);
            abonents = query.getResultList();
            logger.info("Retrieved " + abonents.size() + " abonents.");
        } catch (Exception e) {
            logger.error("Error while retrieving abonents", e);
            throw new ConnectionPoolExceptions("Error while retrieving abonents", e);
        } finally {
            entityManager.close();
        }
        return abonents;
    }
}
