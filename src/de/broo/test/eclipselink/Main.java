package de.broo.test.eclipselink;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import de.broo.test.eclipselink.TODO;
//import org.apache.derby.jdbc.*;
//import org.apache.derby.jdbc.EmbeddedDriver;

public class Main {
	private static final String PERSISTENCE_UNIT_NAME = "todos";
	private static EntityManagerFactory factory;

	public static void main(String[] args) {

		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		// Read the existing entries and write to console
		Query q = em.createQuery("select t from TODO t");
		List<TODO> todoList = q.getResultList();
		for (TODO todo : todoList) {
			System.out.println(todo);
		}
		System.out.println("Size: " + todoList.size());

		// Create new TODO

//		em.getTransaction().begin();
//		TODO todo = new TODO();
//		todo.setSummary("This is a new test");
//		todo.setDescription("This is a new test");
//		em.persist(todo);
//		em.getTransaction().commit();

		em.close();
	}
}