package ru.job4j.service.accident;

import ru.job4j.models.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentService {
    boolean save(Accident accident);

    void update(Accident accident);

    Optional<Accident> findById(int id);

    List<Accident> findAll();

    void delete(int id);
}
