package com.alena.jewelryproject.dao;

import com.alena.jewelryproject.model.EmailMessage;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class EmailMessagesDao extends Dao<EmailMessage, Long> {
    @Override
    public Optional<EmailMessage> get(Long id) {
        return executeInsideTransaction(entityManager ->
                Optional.ofNullable(entityManager.find(EmailMessage.class, id)));
    }

    @Override
    public List<EmailMessage> getAll() {
        return executeInsideTransaction(entityManager -> {
            TypedQuery<EmailMessage> query = entityManager.createQuery("SELECT e FROM EmailMessage e", EmailMessage.class);
            return Optional.ofNullable(query.getResultList());
        }).orElse(null);
    }

    @Override
    public void save(EmailMessage emailMessage) {
        executeInsideTransaction(entityManager -> {
            entityManager.persist(emailMessage);
            return Optional.empty();
        });
    }

    @Override
    public void update(EmailMessage emailMessage) {
        executeInsideTransaction(entityManager -> {
            entityManager.merge(emailMessage);
            return Optional.empty();
        });
    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(entityManager -> {
                    EmailMessage promotionalCode = entityManager.find(EmailMessage.class, id);
                    entityManager.remove(promotionalCode);
                    return Optional.empty();
                }
        );
    }
}
