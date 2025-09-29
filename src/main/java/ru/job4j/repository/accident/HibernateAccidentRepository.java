package ru.job4j.repository.accident;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.models.Accident;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class HibernateAccidentRepository implements AccidentRepository {
    
    private final SessionFactory sessionFactory;

    public HibernateAccidentRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Accident save(Accident accident) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(accident);
            session.getTransaction().commit();
            return accident;
        }
    }

    @Override
    public void update(Accident accident) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(accident);
            session.getTransaction().commit();
        }
    }

    @Override
    public Optional<Accident> findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Accident accident = session.find(Accident.class, id);
            session.getTransaction().commit();
            return Optional.ofNullable(accident);
        }
    }

    @Override
    public List<Accident> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Accident> accidents = session.createQuery("from Accident", Accident.class).list();
            session.getTransaction().commit();
            return accidents;
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Accident accident = session.find(Accident.class, id);
            if (accident != null) {
                session.remove(accident);
            }
            session.getTransaction().commit();
        }
    }
}
