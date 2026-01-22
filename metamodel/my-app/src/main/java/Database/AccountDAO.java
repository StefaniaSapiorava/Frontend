package Database;

import exceptions.ConnectionPoolExceptions;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.Account;
import model.Account_;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.net.URL;

public class AccountDAO {
    private static final Logger logger = LogManager.getLogger(AccountDAO.class);
    private static final EntityManagerFactory entityManagerFactory;

    static {
        try {
            URL resource = AccountDAO.class.getClassLoader().getResource("META-INF/persistence.xml");
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

    // Method to add an account
    public void addAccount(long abonentId, double balance) throws ConnectionPoolExceptions {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Account account = new Account();
            account.setAbonentId(abonentId);
            account.setBalance(balance);

            logger.info("Attempting to add account: Abonent ID = " + abonentId + ", Balance = " + balance);
            entityManager.persist(account); 
            transaction.commit();

            logger.info("Account added: Abonent ID = " + abonentId + ", Balance = " + balance); // Log successful addition
        } catch (Exception e) {
            logger.error("Error while adding account: Abonent ID = " + abonentId + ", Balance = " + balance, e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new ConnectionPoolExceptions("Error while adding account.", e);
        } finally {
            entityManager.close(); 
        }
    }

    // Method to get the balance of an abonent
    public double getBalance(long abonentId) throws ConnectionPoolExceptions {
        EntityManager entityManager = getEntityManager();
        double balance = 0.0;
        try {
            logger.info("Attempting to retrieve balance for Abonent ID = " + abonentId);
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
            Root<Account> root = criteriaQuery.from(Account.class);

            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Account_.abonentId), abonentId));

            TypedQuery<Account> query = entityManager.createQuery(criteriaQuery);
            Account account = query.getSingleResult();
            if (account != null) {
                balance = account.getBalance();
                logger.info("Balance retrieved: Abonent ID = " + abonentId + ", Balance = " + balance);
            } else {
                logger.warn("No account found for Abonent ID = " + abonentId);
            }
        } catch (Exception e) {
            logger.error("Error while retrieving balance: Abonent ID = " + abonentId, e);
            throw new ConnectionPoolExceptions("Error while retrieving balance.", e);
        } finally {
            entityManager.close();
        }
        return balance;
    }
}
