package no.nith.pg6100.infrastructure;

import no.nith.pg6100.entity.Book;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class BookDao {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Book> findAll() {
        return entityManager
            .createNamedQuery("Book.findAll", Book.class)
            .getResultList();
    }

    public List<Book> findAllInGenre(String genre) {
        return entityManager
            .createNamedQuery("Book.findAllInGenre", Book.class)
            .setParameter("genre", genre)
            .getResultList();
    }

    public List<Book> findAllByAuthor(String author) {
        return entityManager
            .createNamedQuery("Book.findAllByAuthor", Book.class)
            .setParameter("author", author)
            .getResultList();
    }

    public Book findById(int id) {
        return entityManager.find(Book.class, id);
    }

    public void persist(Book book) {
        entityManager.persist(book);
    }

    public void remove(Book book) {
        if (entityManager.contains(book)) {
            entityManager.remove(book);
        } else {
            entityManager.remove(entityManager.merge(book));
        }
    }
}
