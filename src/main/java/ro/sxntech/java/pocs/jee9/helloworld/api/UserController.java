package ro.sxntech.java.pocs.jee9.helloworld.api;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Email;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import lombok.extern.slf4j.Slf4j;
import ro.sxntech.java.pocs.jee9.helloworld.service.*;
import ro.sxntech.java.pocs.jee9.helloworld.service.model.TokenParams;

import java.util.stream.Collectors;


@Slf4j
//@AllArgsConstructor(onConstructor = @__(@Inject))
//@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@RequestScoped
@Path("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserAssembler userAssembler;
    private final UserService userService;


    @Inject
    public UserController(UserRepository userRepository,
                          UserAssembler userAssembler,
                          UserService userService) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
        this.userService = userService;
    }

    public UserController() {
        this(null, null, null);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(@Context UriInfo uriInfo, @Context SecurityContext context, UserDto dto) {
        log.info("Create user: {}", dto);
        try {
            final var user = userAssembler.toEntity(dto);
            final var savedUSer = userService.create(user);
            final var uri = uriInfo.getBaseUriBuilder().build(savedUSer.getId());

            return Response.created(uri).build();
        } catch (Exception x) {
            throw new JakartaEE9_Exception("create new user error", x);
        }
    }

    @POST
    @Path("/authenticate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(LoginDto loginDto) {
        try {
            final var token = userService.authenticateUser(loginDto.getUsername(), loginDto.getPassword());
            return Response.ok(userAssembler.toDto(token)).build();
        } catch (Exception x) {
            throw new JakartaEE9_Exception("login user error", x);
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            final var users = userRepository.findAll();
            final var dtos = users.stream().map(userAssembler::toDto).collect(Collectors.toList());

            return Response.ok(dtos).build();
        } catch (Exception x) {
            throw new JakartaEE9_Exception("find all users error", x);
        }
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBy(@Email @PathParam("email") String email) {
        try {
            final var user = userRepository.findBy(email);
            return Response.ok(userAssembler.toDto(user)).build();
        } catch (Exception x) {
            throw new JakartaEE9_Exception("find user error", x);
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id, UserDto dto) {
        try {
            final var user = userAssembler.toEntity(dto);
            final var updatedUser = userRepository.update(user);
            return Response.ok(updatedUser).build();
        } catch (Exception x) {
            throw new JakartaEE9_Exception("update user error", x);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) {
        try {
            userRepository.delete(id);
            return Response.ok().build();
        } catch (Exception x) {
            throw new JakartaEE9_Exception("delete user error", x);
        }
    }
}
