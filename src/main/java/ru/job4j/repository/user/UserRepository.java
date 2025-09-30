package ru.job4j.repository.user;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
