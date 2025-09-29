package ru.job4j.service.accident;

import org.springframework.stereotype.Service;
import ru.job4j.models.Accident;
import ru.job4j.repository.accident.AccidentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SimpleAccidentService implements AccidentService {
    private final AccidentRepository accidentRepository;

    public SimpleAccidentService(AccidentRepository accidentRepository) {
        this.accidentRepository = accidentRepository;
    }

    @Override
    public boolean save(Accident accident) {
        validateAccident(accident);
        return accidentRepository.save(accident);
    }

    @Override
    public void update(Accident accident) {
        validateAccident(accident);
        accidentRepository.update(accident);
    }

    @Override
    public Optional<Accident> findById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Accident id must be greater than 0");
        }
        return accidentRepository.findById(id);
    }

    @Override
    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    @Override
    public void delete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Accident id must be greater than 0");
        }
        accidentRepository.delete(id);
    }

    private void validateAccident(Accident accident) {
        if (accident == null) {
            throw new IllegalArgumentException("Accident cannot be null");
        }
        if (accident.getName() == null || accident.getName().isBlank()) {
            throw new IllegalArgumentException("Accident name cannot be null or empty");
        }
        if (accident.getDescription() == null || accident.getDescription().isBlank()) {
            throw new IllegalArgumentException("Accident text cannot be null or empty");
        }
        if (accident.getAddress() == null || accident.getAddress().isBlank()) {
            throw new IllegalArgumentException("Accident address cannot be null or empty");
        }
    }
}
