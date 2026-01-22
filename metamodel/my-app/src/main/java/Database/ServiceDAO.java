package Database;

import exceptions.ConnectionPoolExceptions;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.Service;
import model.Service_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ServiceDAO {
    private static final Logger logger = LogManager.getLogger(ServiceDAO.class);
    private static final EntityManagerFactory entityManagerFactory;

    static {
        try {
            // Initialize EntityManagerFactory
            entityManagerFactory = Persistence.createEntityManagerFactory("YourPersistenceUnitName");
            logger.info("EntityManagerFactory created successfully.");
        } catch (Exception e) {
            logger.error("Failed to create EntityManagerFactory", e);
            throw new ExceptionInInitializerError("EntityManagerFactory initialization failed");
        }
    }

    private EntityManager getEntityManager() throws ConnectionPoolExceptions {
        if (entityManagerFactory == null) {
            logger.error("EntityManagerFactory not initialized.");
            throw new ConnectionPoolExceptions("EntityManagerFactory not initialized.");
        }
        return entityManagerFactory.createEntityManager();
    }

    // Method to add a service
    public void addService(Service service) throws ConnectionPoolExceptions {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(service);
            transaction.commit();
            logger.info("Service added: ID = " + service.getId() + ", Name = " + service.getName()); // Log successful addition
        } catch (Exception e) {
            logger.error("Error while adding service: ID = " + service.getId() + ", Name = " + service.getName(), e);
            if (transaction.isActive()) {
                transaction.rollback(); 
            }
            throw new ConnectionPoolExceptions("Error while adding service.", e);
        } finally {
            entityManager.close(); 
        }
    }

    public List<Service> getAllServices() throws ConnectionPoolExceptions {
        EntityManager entityManager = getEntityManager();
        List<Service> services;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Service> criteriaQuery = criteriaBuilder.createQuery(Service.class);
            Root<Service> root = criteriaQuery.from(Service.class);
            criteriaQuery.select(root);
            TypedQuery<Service> query = entityManager.createQuery(criteriaQuery);
            services = query.getResultList();

            logger.info("Retrieved all services using Criteria API: " + services.size() + " services found.");
        } catch (Exception e) {
            logger.error("Error while retrieving services using Criteria API", e);
            throw new ConnectionPoolExceptions("Error while retrieving services.", e);
        } finally {
            entityManager.close();
        }
        return services;
    }
}
