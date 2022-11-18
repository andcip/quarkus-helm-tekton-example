package org.andcip;

import org.andcip.entity.Client;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Priority(1)
@Alternative
@ApplicationScoped
public class TestClient extends Client {

    @Inject
    EntityManager entityManager;

    @PostConstruct
    public void init() {
        entityManager.persist(new Client(1L,"Test", "test@email.com"));
    }

}
