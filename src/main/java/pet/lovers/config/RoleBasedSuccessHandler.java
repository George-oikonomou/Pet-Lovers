package pet.lovers.config;

import jakarta.servlet.ServletException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pet.lovers.entities.Role;
import java.io.IOException;

@Component
public class RoleBasedSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN))) {
            getRedirectStrategy().sendRedirect(request, response, "/admin/dashboard");
        } else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.SHELTER))) {
            getRedirectStrategy().sendRedirect(request, response, "/shelter/dashboard");
        }else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.VET))) {
            getRedirectStrategy().sendRedirect(request, response, "/vet/dashboard");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADOPTER))) {
            getRedirectStrategy().sendRedirect(request, response, "/adopter/dashboard");
        }
    }
}
