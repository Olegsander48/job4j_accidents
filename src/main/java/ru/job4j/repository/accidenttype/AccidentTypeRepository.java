package ru.job4j.repository.accidenttype;

import ru.job4j.models.AccidentType;
import java.util.List;
import java.util.Optional;

public interface AccidentTypeRepository {
    boolean save(AccidentType accidentType);

    void update(AccidentType accidentType);

    Optional<AccidentType> findById(int id);

    List<AccidentType> findAll();

    void delete(int id);
}
