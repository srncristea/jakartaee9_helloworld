package ro.sxntech.java.pocs.jee9.helloworld.service.model;


import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * {
 *     "firstName":"Ovidiu",
 *     "lastName":"Cristea_04",
 *     "email":"Ovidiu.Cristea_04@test.com",
 *     "enabled":"true",
 *     "username":"Ovidiu.Cristea_04",
 *     "credentials":[{"type":"password","value":"test123","temporary":false}]
 * }
 */

@Builder
@Getter
public class OpenIDUser {

    @JsonbProperty("firstName")
    private final String firstName;
    @JsonbProperty("lastName")
    private final String lastName;
    @JsonbProperty("email")
    private final String email;
    @JsonbProperty("enabled")
    private final boolean enabled;
    @JsonbProperty("username")
    private final String username;
    @JsonbProperty("credentials")
    private final List<Credentials> credentials;
}
