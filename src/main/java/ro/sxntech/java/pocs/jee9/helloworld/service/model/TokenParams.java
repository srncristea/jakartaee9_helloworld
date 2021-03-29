package ro.sxntech.java.pocs.jee9.helloworld.service.model;

import lombok.RequiredArgsConstructor;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RequiredArgsConstructor
public class TokenParams {

    private final String username;
    private final String password;
    private final String grantType;
    private final String clientId;
    private final String clientSecret;


    public String bodyAsUrlEncoded() {
        final var sb = new StringBuilder();
        if (Objects.nonNull(password)) {
            sb.append("password").append("=").append(URLEncoder.encode(password, StandardCharsets.UTF_8));
        }
        if (Objects.nonNull(username)) {
            sb.append("&").append("username").append("=").append(URLEncoder.encode(username, StandardCharsets.UTF_8));
        }
        if (Objects.nonNull(grantType)) {
            sb.append("&").append("grant_type").append("=").append(URLEncoder.encode(grantType, StandardCharsets.UTF_8));
        }
        if (Objects.nonNull(clientId)) {
            sb.append("&").append("client_id").append("=").append(URLEncoder.encode(clientId, StandardCharsets.UTF_8));
        }
        if (Objects.nonNull(clientSecret)) {
            sb.append("&").append("client_secret").append("=").append(URLEncoder.encode(clientSecret, StandardCharsets.UTF_8));
        }

        return sb.toString();
    }
}
