package uz.greenwhite.sampledashboard.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import uz.greenwhite.sampledashboard.model.UserContext;

import java.util.Map;

@Service
@Slf4j
public class UserService {
    @Value("${app.keycloak.company-id-attribute:companyId}")
    private String companyIdAttribute;
    @Value("${app.keycloak.user-id-attribute:userId}")
    private String userIdAttribute;
    @Value("${app.keycloak.name-attribute:fullName}")
    private String nameAttribute;

    public UserContext getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            return extractUserContext(oidcUser);
        }

        throw new RuntimeException("Unsupported authentication type");
    }

    private UserContext extractUserContext(OidcUser oidcUser) {
        UserContext userContext = new UserContext();
        Map<String, Object> claims = oidcUser.getClaims();

        // Basic user info
        userContext.setSub(oidcUser.getSubject());
        userContext.setUsername(oidcUser.getPreferredUsername());

        // Extract userId from custom attribute
        Object companyIdObj = claims.get(companyIdAttribute);
        if (companyIdObj != null) {
            userContext.setCompanyId(Long.parseLong(companyIdObj.toString()));
        }

        // Extract userId from custom attribute
        Object userIdObj = claims.get(userIdAttribute);
        if (userIdObj != null) {
            userContext.setUserId(Long.parseLong(userIdObj.toString()));
        }

        // Extract userId from custom attribute
        Object nameObj = claims.get(nameAttribute);
        if (nameObj != null) {
            userContext.setName(nameObj.toString());
        }

        log.debug("Extracted user context: username={}, companyId={}, userId={}, name={}",
                userContext.getUsername(), userContext.getCompanyId(), userContext.getUserId(), userContext.getName());

        return userContext;
    }
}