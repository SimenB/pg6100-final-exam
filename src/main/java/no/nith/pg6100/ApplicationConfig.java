package no.nith.pg6100;

import com.wordnik.swagger.jaxrs.config.BeanConfig;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResource;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
import no.nith.pg6100.service.ESportService;
import no.nith.pg6100.util.Constants;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/esports")
public class ApplicationConfig extends Application {

    public ApplicationConfig() {
        final BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setBasePath(Constants.BASE_URL);
        beanConfig.setResourcePackage("no.nith.pg6100.service");
        beanConfig.setScan(true);
    }

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<>();

        resources.add(ESportService.class);

        resources.add(ApiListingResource.class);
        resources.add(ApiDeclarationProvider.class);
        resources.add(ApiListingResourceJSON.class);
        resources.add(ResourceListingProvider.class);

        return resources;
    }
}
