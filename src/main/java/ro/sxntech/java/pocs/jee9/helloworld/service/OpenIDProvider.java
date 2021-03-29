package ro.sxntech.java.pocs.jee9.helloworld.service;

import ro.sxntech.java.pocs.jee9.helloworld.service.model.OpenIDUser;
import ro.sxntech.java.pocs.jee9.helloworld.service.model.TokenParams;
import ro.sxntech.java.pocs.jee9.helloworld.service.model.Token;

public interface OpenIDProvider {

    Token masterRealmToken(TokenParams tokenParams);

    Token accessToken(TokenParams tokenParams);

    void createUser(OpenIDUser openIDUser, String masterAccessToken);
}