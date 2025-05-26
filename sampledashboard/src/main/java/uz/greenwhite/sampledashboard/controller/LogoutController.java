package uz.greenwhite.sampledashboard.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Controller
@Slf4j
public class LogoutController {
    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuerUri;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${server.port:8181}")
    private String serverPort;

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String idToken = null;
        String username = "Unknown";

        // Extract ID token and user info
        if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {
            if (oauth2Token.getPrincipal() instanceof OidcUser oidcUser) {
                username = oidcUser.getPreferredUsername();
                idToken = oidcUser.getIdToken().getTokenValue();
                log.info("User {} initiating logout", username);
            }
        }

        // Perform Spring Security logout first
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, authentication);

        // Clear session
        request.getSession().invalidate();

        // Build Keycloak logout URL
        String logoutUrl = buildKeycloakLogoutUrl(idToken);

        log.info("Redirecting user {} to Keycloak logout URL", username);

        // Redirect to Keycloak logout
        response.sendRedirect(logoutUrl);
    }

    private String buildKeycloakLogoutUrl(String idToken) {
        String baseUrl = "http://localhost:" + serverPort;
        String postLogoutRedirectUri = baseUrl + "/logged-out";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(issuerUri + "/protocol/openid-connect/logout");

        // Add client_id parameter
        builder.queryParam("client_id", clientId);

        // Add post logout redirect URI
        builder.queryParam("post_logout_redirect_uri", postLogoutRedirectUri);

        // Add ID token hint if available
        if (idToken != null) {
            builder.queryParam("id_token_hint", idToken);
        }

        String logoutUrl = builder.toUriString();
        log.debug("Built Keycloak logout URL: {}", logoutUrl);

        return logoutUrl;
    }

    @GetMapping("/logged-out")
    public String loggedOut() {
        log.info("User successfully logged out");
        return "logged-out";
    }
}