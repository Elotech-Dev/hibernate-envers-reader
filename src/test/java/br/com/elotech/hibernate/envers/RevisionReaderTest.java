package br.com.elotech.hibernate.envers;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import br.com.elotech.domain.City;
import br.com.elotech.domain.Person;
import br.com.elotech.hibernate.envers.RevisionReader;
import br.com.elotech.hibernate.envers.RevisionRecord;

public class RevisionReaderTest {

	private static EntityManagerFactory instance;

	private EntityManager em;

	private RevisionReader revisionReader;

	public RevisionReaderTest() {
		if (instance == null) {
			instance = Persistence.createEntityManagerFactory("persistenceUnit");
		}
	}

	@Before
	public void setup() {
		em = instance.createEntityManager();
		revisionReader = new RevisionReader(em);
	}

	private void createFixtures(Long id) {

		em.getTransaction().begin();

		City city = new City();

		city.setId(id);
		city.setDescricao("test");

		em.persist(city);

		Person person = new Person();
		person.setId(id);
		person.setName("Dunha");
		person.setCity(city);

		em.persist(person);
		em.getTransaction().commit();
	}

	@Test
	public void findHistory() {

		createFixtures(1000L);

		List<RevisionRecord<Person>> history = revisionReader.findHistory(Person.class, 1000L);

		assertEquals("list size", 1, history.size());

		RevisionRecord<Person> personRevision = history.get(0);

		assertNotNull("not null entity", personRevision.getEntity());
		
		assertEquals(1000L, personRevision.getEntity().getId().longValue());

		assertTrue("removed proxy", personRevision.getEntity().getCity().getClass().equals(City.class));

	}

	@Test
	public void findDeletes() {

		createFixtures(1001L);
		
		em.getTransaction().begin();
		em.remove(em.find(Person.class, 1001L));
		em.getTransaction().commit();

		List<RevisionRecord<Person>> history = revisionReader.findDeletes(Person.class);

		assertEquals("list size", 1, history.size());
		
		RevisionRecord<Person> personRevision = history.get(0);

		assertNotNull("not null entity", personRevision.getEntity());
		
		assertEquals(1001L, personRevision.getEntity().getId().longValue());
	


	}

}
