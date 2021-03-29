package ro.sxntech.java.pocs.jee9.helloworld.infrastructure;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import ro.sxntech.java.pocs.jee9.helloworld.dao.KeyCloakOpenID;
import ro.sxntech.java.pocs.jee9.helloworld.service.OpenIDProvider;

@ApplicationScoped
public class AppCDIProducer {

    @Inject
    @ConfigProperty(name = "keycloak.master.token.url")
    private String masterTokenUrl;
    @Inject
    @ConfigProperty(name = "keycloak.user.token.url")
    private String userTokenUrl;
    @Inject
    @ConfigProperty(name = "keycloak.user.create.url")
    private String userCreateUrl;
    @Inject
    @PersistenceContext(unitName = "primary")
    private EntityManager entityManager;

    @Produces
    @Any
    public OpenIDProvider openIDProvider() {
        return new KeyCloakOpenID(masterTokenUrl, userTokenUrl, userCreateUrl);
    }

    @Produces
    @Singleton
    public EntityManager entityManager() {
        return entityManager;
    }
}
