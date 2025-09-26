package ru.job4j.service.accidenttype;

import org.springframework.stereotype.Service;
import ru.job4j.models.AccidentType;
import ru.job4j.repository.accidenttype.AccidentTypeRepository;
import java.util.List;
import java.util.Optional;

@Service
public class MemoryAccidentTypeService implements AccidentTypeService {
    private final AccidentTypeRepository accidentTypeRepository;

    public MemoryAccidentTypeService(AccidentTypeRepository accidentTypeRepository) {
        this.accidentTypeRepository = accidentTypeRepository;
    }

    @Override
    public boolean save(AccidentType accidentType) {
        validateAccidentType(accidentType);
        return accidentTypeRepository.save(accidentType);
    }

    @Override
    public void update(AccidentType accidentType) {
        validateAccidentType(accidentType);
        accidentTypeRepository.update(accidentType);
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be greater than 0");
        }
        return accidentTypeRepository.findById(id);
    }

    @Override
    public List<AccidentType> findAll() {
        return accidentTypeRepository.findAll();
    }

    @Override
    public void delete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be greater than 0");
        }
        accidentTypeRepository.delete(id);
    }

    private void validateAccidentType(AccidentType accidentType) {
        if (accidentType == null) {
            throw new IllegalArgumentException("Accident type cannot be null");
        }
        if (accidentType.getName() == null || accidentType.getName().isBlank()) {
            throw new IllegalArgumentException("Accident type name cannot be null or empty");
        }
    }
}
