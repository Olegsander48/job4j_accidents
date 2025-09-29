package ru.job4j.repository.accidenttype;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.models.AccidentType;

public interface JpaAccidentTypeRepository extends CrudRepository<AccidentType, Integer> {
}
