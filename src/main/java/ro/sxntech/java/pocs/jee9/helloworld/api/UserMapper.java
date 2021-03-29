package ro.sxntech.java.pocs.jee9.helloworld.api;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ro.sxntech.java.pocs.jee9.helloworld.service.entity.User;

/**
 * @deprecated : for moment since Jakartaee 9 and mapStrunct are not workin correctly , we use UserAssembler.
 */
@Deprecated

//@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.ERROR)
//@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
//@ApplicationScoped
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    User toEntity(UserDto dto);

    UserDto toDto(User user);
}
