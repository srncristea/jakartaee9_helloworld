# jakartaee9_helloworld: wildfly(runtime)

## Description
- proof of concept that cover JakartaEE 9 specifications, as runtime use [Wildfly(Preview)](>=23.0.0.Final) and [OpenLiberty], java 11, postrgesql, KeyCloak and maven.


## build and run
```dockerfile
docker-compose down -v && docker-compose up --build
```

## necessary configurations

### add postgresql support
```markdown
 1. new directory: ${wildfly_home}/modules/system/layers/base/org/postgresql
 2. new file: module.xml
 3. copy postgresql driver: postgresql-42.2.19.jar
```

module.xml content:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.0" name="org.postgresql">
    <resources>
        <resource-root path="postgresql-42.2.19.jar" />
    </resources>
    <dependencies>
        <module name="javax.api" />
        <module name="javax.transaction.api" />
    </dependencies>
</module>
```
```markdown
 4. add postgresql db configurations, into: ${wildfly_home}/standalone/configuration/standalone.xml
```
standalone.xml:
```xml
        <subsystem xmlns="urn:jboss:domain:datasources:6.0">
            <datasources>
                <datasource jndi-name="java:jboss/datasources/ExampleDS" pool-name="ExampleDS" enabled="true" use-java-context="true" statistics-enabled="${wildfly.datasources.statistics-enabled:${wildfly.statistics-enabled:false}}">
                    <connection-url>jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE</connection-url>
                    <driver>h2</driver>
                    <security>
                        <user-name>sa</user-name>
                        <password>sa</password>
                    </security>
                </datasource>
                <!-- <datasource jndi-name="java:jboss/datasources/demods" pool-name="demods-pool" enabled="true" use-java-context="true">
                    <connection-url>jdbc:postgresql://localhost:5432/demodb</connection-url>
                    <driver>postgresql</driver>
                    <security>
                        <user-name>postgres</user-name>
                        <password>admin</password>
                    </security>
                </datasource> -->
                <drivers>
                    <driver name="h2" module="com.h2database.h2">
                        <xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
                    </driver>
                    <driver name="postgresql" module="org.postgresql">
                        <!-- for xa datasource -->
                        <xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
                        <!-- for non-xa datasource -->
                        <driver-class>org.postgresql.Driver</driver-class>
                    </driver>
                </drivers>
            </datasources>
        </subsystem>
        <subsystem xmlns="urn:jboss:domain:keycloak:1.1">
            <secure-deployment name="hello_world.war">
                <realm>public</realm>
                <auth-server-url>http://localhost:8084/auth/</auth-server-url>
                <public-client>true</public-client>
                <ssl-required>external</ssl-required>
                <resource>JakartaEE-jwt-client</resource>
            </secure-deployment>
        </subsystem>    
```

### db connection
```
 - run docker ps -a, to see the list of containers, find postresql container id and run docker -exec docker-container-id /bin/sh
 - after you are connected into container run psql database(mso_core_services_0) user(3kgh0m3)
```

## runtime
    - wildfly 23.0.0-Final
    - openJdk 11
### KeyCloak
   -  API Endpoints:
         - http://localhost:8084/auth/realms/jakartaee_9_poc/.well-known/openid-configuration
      
```json
{
  "issuer": "http://localhost:8084/auth/realms/jakartaee_9_poc",
  "authorization_endpoint": "http://localhost:8084/auth/realms/jakartaee_9_poc/protocol/openid-connect/auth",
  "token_endpoint": "http://localhost:8084/auth/realms/jakartaee_9_poc/protocol/openid-connect/token",
  "introspection_endpoint": "http://localhost:8084/auth/realms/jakartaee_9_poc/protocol/openid-connect/token/introspect",
  "userinfo_endpoint": "http://localhost:8084/auth/realms/jakartaee_9_poc/protocol/openid-connect/userinfo",
  "end_session_endpoint": "http://localhost:8084/auth/realms/jakartaee_9_poc/protocol/openid-connect/logout",
  "jwks_uri": "http://localhost:8084/auth/realms/jakartaee_9_poc/protocol/openid-connect/certs",
  "check_session_iframe": "http://localhost:8084/auth/realms/jakartaee_9_poc/protocol/openid-connect/login-status-iframe.html",
  "grant_types_supported": [
    "authorization_code",
    "implicit",
    "refresh_token",
    "password",
    "client_credentials"
  ],
  "response_types_supported": [
    "code",
    "none",
    "id_token",
    "token",
    "id_token token",
    "code id_token",
    "code token",
    "code id_token token"
  ],
  "subject_types_supported": [
    "public",
    "pairwise"
  ],
  "id_token_signing_alg_values_supported": [
    "PS384",
    "ES384",
    "RS384",
    "HS256",
    "HS512",
    "ES256",
    "RS256",
    "HS384",
    "ES512",
    "PS256",
    "PS512",
    "RS512"
  ],
  "id_token_encryption_alg_values_supported": [
    "RSA-OAEP",
    "RSA-OAEP-256",
    "RSA1_5"
  ],
  "id_token_encryption_enc_values_supported": [
    "A256GCM",
    "A192GCM",
    "A128GCM",
    "A128CBC-HS256",
    "A192CBC-HS384",
    "A256CBC-HS512"
  ],
  "userinfo_signing_alg_values_supported": [
    "PS384",
    "ES384",
    "RS384",
    "HS256",
    "HS512",
    "ES256",
    "RS256",
    "HS384",
    "ES512",
    "PS256",
    "PS512",
    "RS512",
    "none"
  ],
  "request_object_signing_alg_values_supported": [
    "PS384",
    "ES384",
    "RS384",
    "HS256",
    "HS512",
    "ES256",
    "RS256",
    "HS384",
    "ES512",
    "PS256",
    "PS512",
    "RS512",
    "none"
  ],
  "response_modes_supported": [
    "query",
    "fragment",
    "form_post"
  ],
  "registration_endpoint": "http://localhost:8084/auth/realms/jakartaee_9_poc/clients-registrations/openid-connect",
  "token_endpoint_auth_methods_supported": [
    "private_key_jwt",
    "client_secret_basic",
    "client_secret_post",
    "tls_client_auth",
    "client_secret_jwt"
  ],
  "token_endpoint_auth_signing_alg_values_supported": [
    "PS384",
    "ES384",
    "RS384",
    "HS256",
    "HS512",
    "ES256",
    "RS256",
    "HS384",
    "ES512",
    "PS256",
    "PS512",
    "RS512"
  ],
  "claims_supported": [
    "aud",
    "sub",
    "iss",
    "auth_time",
    "name",
    "given_name",
    "family_name",
    "preferred_username",
    "email",
    "acr"
  ],
  "claim_types_supported": [
    "normal"
  ],
  "claims_parameter_supported": true,
  "scopes_supported": [
    "openid",
    "address",
    "email",
    "microprofile-jwt",
    "offline_access",
    "phone",
    "profile",
    "roles",
    "web-origins"
  ],
  "request_parameter_supported": true,
  "request_uri_parameter_supported": true,
  "require_request_uri_registration": true,
  "code_challenge_methods_supported": [
    "plain",
    "S256"
  ],
  "tls_client_certificate_bound_access_tokens": true,
  "revocation_endpoint": "http://localhost:8084/auth/realms/jakartaee_9_poc/protocol/openid-connect/revoke",
  "revocation_endpoint_auth_methods_supported": [
    "private_key_jwt",
    "client_secret_basic",
    "client_secret_post",
    "tls_client_auth",
    "client_secret_jwt"
  ],
  "revocation_endpoint_auth_signing_alg_values_supported": [
    "PS384",
    "ES384",
    "RS384",
    "HS256",
    "HS512",
    "ES256",
    "RS256",
    "HS384",
    "ES512",
    "PS256",
    "PS512",
    "RS512"
  ],
  "backchannel_logout_supported": true,
  "backchannel_logout_session_supported": true
}
```
 - api endpoints:
    - http://localhost:8084/auth/realms/jakartaee_9_poc/protocol/openid-connect/token
    - http://localhost:8084/auth/realms/jakartaee_9_poc/.well-known/openid-configuration



# 

## resources
    - https://www.stenusys.com/how_to_setup_postgresql_datasource_with_wildfly/
    - https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CONFIGURE_POSTGRESQL_JBOSS_EAP.adoc
    - https://ultering.com/it4us/?p=3285
    - https://github.com/hantsy/jakartaee9-starter-boilerplate
    - https://access.redhat.com/documentation/en-us/jboss_enterprise_application_platform_continuous_delivery/12/html/development_guide/class_loading_and_modules
    - wildfly
        -   https://docs.wildfly.org/23/WildFly_Elytron_Security.html#Bearer_Token_Authorization
        -   https://www.keycloak.org/docs/latest/getting_started/#securing-a-sample-application
        -   https://github.com/wildfly/quickstart/tree/master/jaxrs-jwt
        -   https://github.com/wildfly-extras/wildfly-jar-maven-plugin
        -   https://github.com/projectatomic/docker-image-examples/tree/master/centos-wildfly-standalone 
        -   https://piotrminkowski.com/2020/12/14/microprofile-java-microservices-on-wildfly/
        -   https://kodnito.com/posts/microprofile-jwt-with-keycloak/