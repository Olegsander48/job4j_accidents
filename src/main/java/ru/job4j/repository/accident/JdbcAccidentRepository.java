package ru.job4j.repository.accident;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.models.Accident;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class JdbcAccidentRepository implements AccidentRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccidentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean save(Accident accident) {
        return jdbcTemplate.update("INSERT INTO accidents(name, description, address, accident_type_id) VALUES (?, ?, ?, ?)",
                accident.getName(),
                accident.getDescription(),
                accident.getAddress(),
                accident.getAccidentType().getId()) > 0;
    }

    @Override
    public void update(Accident accident) {
        jdbcTemplate.update("UPDATE accidents "
                + "SET name = (?), description = (?), address = (?), accident_type_id = (?) WHERE id = (?)",
                accident.getName(), accident.getDescription(), accident.getAccidentType().getId(), accident.getId());
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM accidents a WHERE a.id = (?)",
                (rs, rowNum) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setAddress(rs.getString("address"));
                    accident.setDescription(rs.getString("description"));
                    return accident;
                }, id));
    }

    @Override
    public List<Accident> findAll() {
        return jdbcTemplate.query("SELECT * FROM accidents",
                (rs, rowNum) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setAddress(rs.getString("address"));
                    accident.setDescription(rs.getString("description"));
                    return accident;
                });
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM accidents WHERE id = (?)", id);
    }
}
