package no.nith.pg6100;

import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

@Singleton
public class Config implements Serializable {
    @Inject
    private Logger logger;

    private Properties properties;

    @PostConstruct
    public void init() {
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");

            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getCallerId() {
        return properties.getProperty("callerId");
    }
}
