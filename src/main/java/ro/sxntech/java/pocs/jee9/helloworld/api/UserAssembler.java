package ro.sxntech.java.pocs.jee9.helloworld.api;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import ro.sxntech.java.pocs.jee9.helloworld.service.model.Token;
import ro.sxntech.java.pocs.jee9.helloworld.service.entity.User;

@Slf4j
//@AllArgsConstructor(onConstructor = @__(@Inject))
//@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@ApplicationScoped
public class UserAssembler {

    User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUserName(dto.getUserName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        return user;
    }

    UserDto toDto(User user) {

        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUserName(user.getUserName());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());

        return userDto;
    }

    TokenDto toDto(Token token){
        return new TokenDto(token.getAccessToken(), token.getRefreshToken());
    }
}
