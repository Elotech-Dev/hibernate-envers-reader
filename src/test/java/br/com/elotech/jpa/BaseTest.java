package br.com.elotech.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;

public abstract class BaseTest {

    private static EntityManagerFactory instance;

    protected static EntityManager em;

    public BaseTest() {
        if (instance == null) {
            instance = Persistence.createEntityManagerFactory("persistenceUnit");
            em = instance.createEntityManager();
        }
    }

    @Before
    public void setup() {
        em.getTransaction().begin();
    }

}
