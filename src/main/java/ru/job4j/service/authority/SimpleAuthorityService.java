package ru.job4j.service.authority;

import org.springframework.stereotype.Service;
import ru.job4j.models.Authority;
import ru.job4j.repository.authority.AuthorityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleAuthorityService implements AuthorityService {
    private final AuthorityRepository authorityRepository;

    public SimpleAuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }

    @Override
    public void update(Authority authority) {
        authorityRepository.save(authority);
    }

    @Override
    public Optional<Authority> findById(int id) {
        return authorityRepository.findById(id);
    }

    @Override
    public Authority findByAuthority(String authority) {
        return authorityRepository.findByAuthority(authority);
    }

    @Override
    public List<Authority> findAll() {
        List<Authority> authorities = new ArrayList<>();
        authorityRepository.findAll().forEach(authorities::add);
        return authorities;
    }

    @Override
    public void delete(int id) {
        authorityRepository.deleteById(id);
    }
}
