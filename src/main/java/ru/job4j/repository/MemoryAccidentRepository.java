package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.models.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemoryAccidentRepository implements AccidentRepository {
    Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    AtomicInteger accidentId = new AtomicInteger(1);

    @Override
    public boolean save(Accident accident) {
        boolean result = accidents.put(accidentId.get(), accident) != null;
        accident.setId(accidentId.getAndIncrement());
        return result;
    }

    @Override
    public void update(Accident accident) {
        accidents.put(accident.getId(), accident);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
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
