package ro.sxntech.java.pocs.jee9.helloworld.infrastructure.properties;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Getter
public class RealmMasterProperties {

    @Inject
    @ConfigProperty(name = "keycloak.realm.master.username")
    private String username;
    @Inject
    @ConfigProperty(name = "keycloak.realm.master.password")
    private String password;
    @Inject
    @ConfigProperty(name = "keycloak.realm.master.grand_type")
    private String grantType;
    @Inject
    @ConfigProperty(name = "keycloak.realm.master.client_id")
    private String clientId;
}
