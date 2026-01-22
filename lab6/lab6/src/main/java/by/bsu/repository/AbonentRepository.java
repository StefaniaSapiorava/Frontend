package by.bsu.repository;

import by.bsu.entities.Abonent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbonentRepository extends CrudRepository<Abonent, Integer> {
    List<Abonent> findAll();
}