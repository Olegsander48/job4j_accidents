package ru.job4j.repository.rule;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.models.Rule;

import java.util.List;
import java.util.Set;

public interface JpaRuleRepository extends CrudRepository<Rule, Integer> {
    Set<Rule> findRulesByIdIn(List<Integer> ids);
}
