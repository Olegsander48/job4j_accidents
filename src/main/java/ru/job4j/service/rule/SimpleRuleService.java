package ru.job4j.service.rule;

import org.springframework.stereotype.Service;
import ru.job4j.models.Rule;
import ru.job4j.repository.rule.JpaRuleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SimpleRuleService implements RuleService {
    private final JpaRuleRepository ruleRepository;

    public SimpleRuleService(JpaRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public Rule save(Rule rule) {
        validateRule(rule);
        return ruleRepository.save(rule);
    }

    @Override
    public void update(Rule rule) {
        validateRule(rule);
        ruleRepository.save(rule);
    }

    @Override
    public Optional<Rule> findById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Rule id cannot be negative or zero");
        }
        return ruleRepository.findById(id);
    }

    @Override
    public List<Rule> findAll() {
        List<Rule> rules = new ArrayList<>();
        ruleRepository.findAll().forEach(rules::add);
        return rules;
    }

    @Override
    public Set<Rule> findRulesByIds(List<Integer> ids) {
        return ruleRepository.findRulesByIdIn(ids);
    }

    @Override
    public void delete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Rule id cannot be negative or zero");
        }
        ruleRepository.delete(findById(id).get());
    }

    private void validateRule(Rule rule) {
        if (rule == null) {
            throw new IllegalArgumentException("Rule cannot be null");
        }
        if (rule.getName() == null || rule.getName().isBlank()) {
            throw new IllegalArgumentException("Rule name cannot be null or empty");
        }
    }
}
