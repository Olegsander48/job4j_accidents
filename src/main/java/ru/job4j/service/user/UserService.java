package ru.job4j.service.user;

import ru.job4j.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    void update(User user);

    Optional<User> findById(int id);

    List<User> findAll();

    void delete(int id);
}
