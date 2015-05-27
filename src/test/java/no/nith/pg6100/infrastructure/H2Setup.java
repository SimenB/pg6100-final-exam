package no.nith.pg6100.infrastructure;

import org.h2.tools.Server;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class H2Setup implements TestRule {
    private Server server;
    private EntityManagerFactory factory;
    private EntityManager entityManager;

    @Override
    public Statement apply(final Statement statement, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                startDb();
                try {
                    statement.evaluate();
                } finally {
                    showdownDb();
                }
            }
        };
    }

    private void startDb() throws Exception {
        server = Server.createTcpServer();
        server.start();
        factory = Persistence.createEntityManagerFactory("pg6100-exam-beksim10");
        entityManager = factory.createEntityManager();
    }

    private void showdownDb() throws Exception {
        entityManager.close();
        factory.close();
        server.shutdown();
    }

    public EntityManager entityManager() {
        return entityManager;
    }
}
