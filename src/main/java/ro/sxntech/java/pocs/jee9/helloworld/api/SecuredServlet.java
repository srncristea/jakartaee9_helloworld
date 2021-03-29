package ro.sxntech.java.pocs.jee9.helloworld.api;

import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet("/hello_world/api/*")
@ServletSecurity(
        value = @HttpConstraint(rolesAllowed = {"admin_role"}),
        httpMethodConstraints = {
                @HttpMethodConstraint(
                        value = "GET",
                        rolesAllowed = {"user_role"}),
                @HttpMethodConstraint(
                        value = "POST",
                        rolesAllowed = {"admin_role"})
        })
@BasicAuthenticationMechanismDefinition(
        realmName = "userRealm")
public class SecuredServlet extends HttpServlet {

}
