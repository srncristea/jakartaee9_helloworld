package ro.sxntech.java.pocs.jee9.helloworld.infrastructure.properties;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Getter
public class RealmAppProperties {

    @Inject
    @ConfigProperty(name = "keycloak.realm.app.grand_type")
    private String grantType;
    @Inject
    @ConfigProperty(name = "keycloak.realm.app.client_id")
    private String clientId;
    @Inject
    @ConfigProperty(name = "keycloak.realm.app.client_secret")
    private String clientSecret;
}
