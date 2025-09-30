package ru.job4j.service.authority;

import ru.job4j.models.Authority;
import java.util.List;
import java.util.Optional;

public interface AuthorityService {
    Authority save(Authority authority);

    void update(Authority authority);

    Optional<Authority> findById(int id);

    Authority findByAuthority(String authority);

    List<Authority> findAll();

    void delete(int id);
}
