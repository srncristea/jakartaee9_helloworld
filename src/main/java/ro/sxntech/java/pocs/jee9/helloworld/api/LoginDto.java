package ro.sxntech.java.pocs.jee9.helloworld.api;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDto {

    private String username, password;
}
