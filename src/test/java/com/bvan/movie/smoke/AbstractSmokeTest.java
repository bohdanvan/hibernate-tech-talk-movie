package com.bvan.movie.smoke;

import static org.assertj.core.api.Assertions.assertThat;

import com.bvan.movie.util.TransactionHelper;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

abstract class AbstractSmokeTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private TransactionHelper transactionHelper;

    @Test
    @Order(1)
    @Transactional
    void declarativeTransaction_persist() {
        persist();
    }

    @Test
    @Order(2)
    @Transactional
    void declarativeTransaction_verifyNoEntitiesInPersistentContext() {
        verityNoEntities();
    }

    @Test
    @Order(3)
    void functionalTransaction_persist() {
        transactionHelper.execute(this::persist);
    }

    @Test
    @Order(4)
    void functionalTransaction_verifyNoEntitiesInPersistentContext() {
        transactionHelper.execute(this::verityNoEntities);
    }

    private void persist() {
        entityManager.persist(new Greeting("Hello"));
    }

    private void verityNoEntities() {
        List<Greeting> greetings = entityManager.createQuery("select g from Greeting g", Greeting.class)
                .getResultList();
        assertThat(greetings).isEmpty();
    }
}
