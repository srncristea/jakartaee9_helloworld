package ro.sxntech.java.pocs.jee9.helloworld.dao;

//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.sxntech.java.pocs.jee9.helloworld.service.JakartaEE9_Exception;
import ro.sxntech.java.pocs.jee9.helloworld.service.OpenIDProvider;
import ro.sxntech.java.pocs.jee9.helloworld.service.model.OpenIDUser;
import ro.sxntech.java.pocs.jee9.helloworld.service.model.Token;
import ro.sxntech.java.pocs.jee9.helloworld.service.model.TokenParams;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

import static java.net.http.HttpClient.Version.HTTP_1_1;

@Slf4j
//@ApplicationScoped
//@RequiredArgsConstructor(onConstructor = @__(@Inject))
@RequiredArgsConstructor
public class KeyCloakOpenID implements OpenIDProvider {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String AUTHORIZATION = "Authorization";
    private static final String WWW_FORM_URL_ENCODED = "application/x-www-form-urlencoded";
    private static final String APPLICATION_JSON = "application/json";

    private final String masterTokenUrl;
    private final String accessTokenUrl;
    private final String createUserUrl;

    @Override
    public Token masterRealmToken(TokenParams tokenParams) {
        try {
            final var requestBody = tokenParams.bodyAsUrlEncoded();
            final var httpClient = httpClientBuilder().build();
            final var httpRequest = PostRequestBuilder(masterTokenUrl, Map.of(CONTENT_TYPE, WWW_FORM_URL_ENCODED), requestBody)
                    .build();
            log.info("Execute request: {}", httpRequest);
            final var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            return handleResponse(httpResponse, Token.class);
        } catch (Exception x) {
            throw new JakartaEE9_Exception(x);
        }
    }

    @Override
    public Token accessToken(TokenParams tokenParams) {
        try {
            final var requestBody = tokenParams.bodyAsUrlEncoded();
            final var httpClient = httpClientBuilder().build();
            final var httpRequest = PostRequestBuilder(accessTokenUrl, Map.of(CONTENT_TYPE, WWW_FORM_URL_ENCODED), requestBody)
                    .build();
            final var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            return handleResponse(httpResponse, Token.class);
        } catch (Exception x) {
            throw new JakartaEE9_Exception(x);
        }
    }

    @Override
    public void createUser(OpenIDUser openIDUser, final String masterAccessToken) {
        try {
            final var requestBody = serialize(openIDUser);
            final var httpClient = httpClientBuilder().build();
            final var headers = Map.of(CONTENT_TYPE, APPLICATION_JSON, AUTHORIZATION, "Bearer " + masterAccessToken);
            final var httpRequest = PostRequestBuilder(createUserUrl, headers, requestBody)
                    .build();
            final var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            final var status = httpResponse.statusCode();
            if (201 == status) {
                final var response = httpResponse.body();
                log.debug("Create user response, status: {} response: {}", status, response);
            } else {
                throw new JakartaEE9_Exception("Create user response status code: " + status);
            }
        } catch (Exception x) {
            throw new JakartaEE9_Exception(x);
        }
    }

    private HttpClient.Builder httpClientBuilder() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .version(HTTP_1_1);
    }

    /**
     * private HttpRequest.Builder GetRequestBuilder(final String url, final Map<String, String> headers) {
     * final var httpRequestBuilder = HttpRequest
     * .newBuilder(URI.create(url))
     * .GET();
     * setHeaders(httpRequestBuilder, headers);
     * <p>
     * return httpRequestBuilder;
     * }
     */

    private HttpRequest.Builder PostRequestBuilder(final String url, final Map<String, String> headers, final String body) {
        final var httpRequestBuilder = HttpRequest
                .newBuilder(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(body));
        setHeaders(httpRequestBuilder, headers);

        return httpRequestBuilder;
    }

    public void setHeaders(final HttpRequest.Builder builder, final Map<String, String> headers) {
        headers.forEach(builder::setHeader);
    }

    private <T> String serialize(T t) {
        try {
            final var jsonb = JsonbBuilder.newBuilder().build();
            final var str = jsonb.toJson(t, t.getClass());
            log.trace("Serialize class: {} -> {}", t.getClass().getSimpleName(), str);

            return str;
        } catch (Exception x) {
            throw new JakartaEE9_Exception(x);
        }
    }

    private <T> T handleResponse(final HttpResponse<String> httpResponse, final Class<T> tClass) {
        log.trace("Handle Response");
        final var status = httpResponse.statusCode();
        final var content = httpResponse.body();
        log.debug("HttResponse.Status: {} Response: {}", status, content);

        return deserialize(content, tClass);
    }

    private <T> T deserialize(final String content, Class<T> tClass) {
        try {
            return JsonbBuilder.newBuilder().build().fromJson(content, tClass);
        } catch (Exception x) {
            throw new JakartaEE9_Exception(x);
        }
    }
}
