package ro.sxntech.java.pocs.jee9.helloworld.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.sxntech.java.pocs.jee9.helloworld.infrastructure.properties.RealmAppProperties;
import ro.sxntech.java.pocs.jee9.helloworld.infrastructure.properties.RealmMasterProperties;
import ro.sxntech.java.pocs.jee9.helloworld.service.entity.User;
import ro.sxntech.java.pocs.jee9.helloworld.service.model.Credentials;
import ro.sxntech.java.pocs.jee9.helloworld.service.model.OpenIDUser;
import ro.sxntech.java.pocs.jee9.helloworld.service.model.Token;
import ro.sxntech.java.pocs.jee9.helloworld.service.model.TokenParams;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
@NoArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private OpenIDProvider openIDProvider;
    private RealmMasterProperties realmMasterProperties;
    private RealmAppProperties realmAppProperties;

    public Token authenticateUser(final String username, final String password) {
        final var tokenParams = new TokenParams(username, password,
                realmAppProperties.getGrantType(),
                realmAppProperties.getClientId(),
                realmAppProperties.getClientSecret());
        final var token = openIDProvider.accessToken(tokenParams);
        log.debug("Access token: {}", token);

        return token;
    }


    public User create(final User user) {
        try {
            log.debug("Create user: {}", user);
            final var createdUser = userRepository.create(user);
            saveUserIntoOpenIDProvider(createdUser);

            return createdUser;
        } catch (Exception x) {
            throw new JakartaEE9_Exception(x);
        }
    }

    private void saveUserIntoOpenIDProvider(User user) {
        final String mat = getMasterAccessToken();
        final var openIDUser = OpenIDUser.builder()
                .username(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .enabled(true)
                .credentials(List.of(Credentials.builder()
                        .type("password")
                        .value(user.getPassword())
                        .build()))
                .build();
        openIDProvider.createUser(openIDUser, mat);
        log.debug("Save user properly into external OpenIDProvider.");
    }

    private String getMasterAccessToken() {
        final var tokenParams = new TokenParams(
                realmMasterProperties.getUsername(),
                realmMasterProperties.getPassword(),
                realmMasterProperties.getGrantType(),
                realmMasterProperties.getClientId(),
                null);
        final var token = openIDProvider.masterRealmToken(tokenParams);
        final var tokenAccess = token.getAccessToken();
        log.trace("Access token: {}", tokenAccess);

        return tokenAccess;
    }


}
