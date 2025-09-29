package ru.job4j.repository.accident;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.models.Accident;

public interface JpaAccidentRepository extends CrudRepository<Accident, Integer> {
}
