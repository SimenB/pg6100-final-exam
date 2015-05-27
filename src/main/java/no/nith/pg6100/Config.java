package no.nith.pg6100;

import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

@Singleton
@Startup
public class Config implements Serializable {
    @Inject
    private Logger logger;

    private Properties properties;

    @PostConstruct
    public void init() {
        properties = new Properties();
        InputStream input = null;

        try {
            input = Config.class.getClassLoader().getResourceAsStream("config.properties");

            properties.load(input);
        } catch (final Exception ex) {
            logger.error("Error caught", ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (final Exception e) {
                    logger.error("Error caught", e);
                }
            }
        }
    }

    public String getCallerId() {
        return properties.getProperty("callerId");
    }
}
