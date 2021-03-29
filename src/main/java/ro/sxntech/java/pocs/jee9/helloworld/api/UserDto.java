package ro.sxntech.java.pocs.jee9.helloworld.api;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String phone;

}
