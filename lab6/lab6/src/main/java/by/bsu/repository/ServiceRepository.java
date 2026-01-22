package by.bsu.repository;

import by.bsu.entities.Service;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Integer> {
    List<Service> findAll();
}