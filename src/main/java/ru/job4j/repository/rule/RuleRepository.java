package ru.job4j.repository.rule;

import ru.job4j.models.Rule;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RuleRepository {
    boolean save(Rule rule);

    void update(Rule rule);

    Optional<Rule> findById(int id);

    List<Rule> findAll();

    Set<Rule> findRulesByIds(List<Integer> ids);

    void delete(int id);
}
