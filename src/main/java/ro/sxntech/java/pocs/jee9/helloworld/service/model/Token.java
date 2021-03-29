package ro.sxntech.java.pocs.jee9.helloworld.service.model;

import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Token {

    @JsonbProperty("access_token")
    private String accessToken;
    @JsonbProperty("expires_in")
    private long expiresIn;
    @JsonbProperty("refresh_expires_in")
    private long refreshExpiresIn;
    @JsonbProperty("refresh_token")
    private String refreshToken;
    @JsonbProperty("token_type")
    private String tokenType;
    @JsonbProperty("not-before-policy")
    private long notBeforePolicy;
    @JsonbProperty("session_state")
    private String sessionState;
    @JsonbProperty("scope")
    private String scope;

}
