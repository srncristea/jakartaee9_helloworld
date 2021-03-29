package ro.sxntech.java.pocs.jee9.helloworld.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenDto {

    private final String accessToken, refreshToken;
}
