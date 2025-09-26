package ru.job4j.repository.accidenttype;

import org.springframework.stereotype.Repository;
import ru.job4j.models.AccidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemoryAccidentTypeRepository implements AccidentTypeRepository {
    Map<Integer, AccidentType> accidentTypes = new ConcurrentHashMap<>();
    AtomicInteger accidentTypeId = new AtomicInteger(1);

    public MemoryAccidentTypeRepository() {
        save(new AccidentType(1, "Две машины"));
        save(new AccidentType(2, "Машина и человек"));
        save(new AccidentType(3, "Машина и велосипед"));
    }

    @Override
    public boolean save(AccidentType accidentType) {
        boolean result = accidentTypes.put(accidentTypeId.get(), accidentType) != null;
        accidentType.setId(accidentTypeId.getAndIncrement());
        return result;
    }

    @Override
    public void update(AccidentType accidentType) {
        accidentTypes.put(accidentTypeId.get(), accidentType);
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(accidentTypes.get(id));
    }

    @Override
    public List<AccidentType> findAll() {
        return new ArrayList<>(accidentTypes.values());
    }

    @Override
    public void delete(int id) {
        accidentTypes.remove(id);
    }
}
