package no.nith.pg6100;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

@Singleton
@Startup
public class Config implements Serializable {
    private Properties properties;

    @PostConstruct
    public void init() {
        properties = new Properties();
        InputStream input = null;

        try {
            input = Config.class.getClassLoader().getResourceAsStream("config.properties");

            properties.load(input);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getCallerId() {
        return properties.getProperty("callerId");
    }
}
