package no.nith.pg6100.infrastructure;

import no.nith.pg6100.entity.Person;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class PersonDao {
    private EntityManager em;

    @Inject
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }

    public List<Person> findAll() {
        TypedQuery<Person> query = em.createNamedQuery("Person.findAll", Person.class);
        return query.getResultList();
    }
}
