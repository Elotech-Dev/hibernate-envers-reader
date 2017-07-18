package br.com.elotech.jpa;

import java.util.List;

import org.junit.Test;

import br.com.elotech.domain.Person;
import br.com.elotech.hibernate.envers.RevisionReader;
import br.com.elotech.hibernate.envers.RevisionRecord;

public class CityAuditTest extends BaseTest {

    @Test
    public void testAuditCity() {
        Person person = new Person();
        person.setName("Dunha");

        em.persist(person);
        em.flush();
        em.getTransaction().commit();

        RevisionReader revisionReader = new RevisionReader(em);
        List<RevisionRecord<Person>> findHistory = revisionReader.findHistory(Person.class, person.getId());
        System.out.println(findHistory);
    }

}
