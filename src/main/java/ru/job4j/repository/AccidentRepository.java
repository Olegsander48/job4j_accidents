package ru.job4j.repository;

import ru.job4j.models.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentRepository {
    boolean save(Accident accident);

    void update(Accident accident);

    Optional<Accident> findById(int id);

    List<Accident> findAll();

    void delete(int id);
}
