package ru.job4j.repository.rule;

import org.springframework.stereotype.Repository;
import ru.job4j.models.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryRuleRepository implements RuleRepository {
    Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    public MemoryRuleRepository() {
        save(new Rule(1, "Статья. 1"));
        save(new Rule(2, "Статья. 2"));
        save(new Rule(3, "Статья. 3"));
    }

    @Override
    public boolean save(Rule rule) {
        return rules.put(rule.getId(), rule) != null;
    }

    @Override
    public void update(Rule rule) {
        rules.put(rule.getId(), rule);
    }

    @Override
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(rules.get(id));
    }

    @Override
    public List<Rule> findAll() {
        return new ArrayList<>(rules.values());
    }

    @Override
    public Set<Rule> findRulesByIds(List<Integer> ids) {
        return ids.stream()
                .map(id -> rules.get(id))
                .collect(Collectors.toSet());
    }

    @Override
    public void delete(int id) {
        rules.remove(id);
    }
}
