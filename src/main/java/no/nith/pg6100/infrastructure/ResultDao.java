package no.nith.pg6100.infrastructure;

import no.nith.pg6100.entity.Result;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Stateless
public class ResultDao {
    @Inject
    private EntityManager entityManager;

    public List<Result> findAll() {
        return entityManager
            .createNamedQuery("Result.findAll", Result.class)
            .getResultList();
    }

    public Result findById(final int id) {
        return entityManager.find(Result.class, id);
    }

    public List<Result> findAllByTeam(final int teamId) {
        return entityManager
            .createNamedQuery("Result.findAllByTeam", Result.class)
            .setParameter("team", teamId)
            .getResultList();
    }

    public int getTotalNumberOfResults() {
        return findAll().size();
    }

    public void persist(final Result result) {
        entityManager.persist(result);
    }

    public void remove(final Result result) {
        if (entityManager.contains(result)) {
            entityManager.remove(result);
        } else {
            entityManager.remove(entityManager.merge(result));
        }
    }
}
