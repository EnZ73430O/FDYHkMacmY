// 代码生成时间: 2025-08-26 07:52:17
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.security.spi.runtime.AuthenticationRequestContext;
import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.PicketLinkIdentityStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides user authentication service using Quarkus framework.
 */
@ApplicationScoped
@Priority(1)
public class AuthenticationService implements HttpAuthenticationMechanism {

    @Inject
    PicketLinkIdentityStore identityStore;

    @Override
    public void validateRequest(HttpServletRequest request, HttpServletResponse response,
                                 AuthenticationRequestContext context) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Check if parameters are not null
        if (username == null || password == null) {
            throw new AuthenticationFailedException("Username or password cannot be null");
        }

        // Create credential
        UsernamePasswordCredential credential = new UsernamePasswordCredential(username, new Password(password));

        try {
            // Authenticate user
            SecurityIdentity securityIdentity = identityStore.validate(credential);

            // If user is authenticated, associate with context
            if (securityIdentity != null && securityIdentity.isAnonymous()) {
                throw new AuthenticationFailedException("Failed to authenticate user");
            } else {
                context.notifyContainerAboutLogin(securityIdentity);
            }
        } catch (AuthenticationException e) {
            // Handle authentication failure
            throw new AuthenticationFailedException("Authentication failed", e);
        }
    }

    @Override
    public boolean secureResponse(HttpServletRequest request, HttpServletResponse response,
                                    AuthenticationRequestContext context, AuthenticationRequest authenticationRequest) {
        // No need to secure response in this example
        return false;
    }
}
