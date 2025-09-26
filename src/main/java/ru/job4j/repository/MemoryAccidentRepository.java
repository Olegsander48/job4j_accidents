package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.models.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryAccidentRepository implements AccidentRepository {
    Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    @Override
    public boolean save(Accident accident) {
        return accidents.put(accident.getId(), accident) != null;
    }

    @Override
    public void update(Accident accident) {
        accidents.put(accident.getId(), accident);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.of(accidents.get(id));
    }

    @Override
    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }

    @Override
    public void delete(int id) {
        accidents.remove(id);
    }
}
