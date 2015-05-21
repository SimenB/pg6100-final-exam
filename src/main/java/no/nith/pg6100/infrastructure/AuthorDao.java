package no.nith.pg6100.infrastructure;

import no.nith.pg6100.entity.Author;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class AuthorDao {
    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }

    public List<Author> findAll() {
        TypedQuery<Author> query = em.createNamedQuery("Author.findAll", Author.class);
        return query.getResultList();
    }

    public void persist(Author author) {
        em.persist(author);
    }

    public void remove(Author author) {
        if (em.contains(author)) {
            em.remove(author);
        } else {
            em.remove(em.merge(author));
        }
    }
}
