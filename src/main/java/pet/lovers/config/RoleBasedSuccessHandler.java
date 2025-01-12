package pet.lovers.config;

import jakarta.servlet.ServletException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RoleBasedSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Redirect based on role
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            getRedirectStrategy().sendRedirect(request, response, "/admin/dashboard");
        }
//        else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SHELTER"))) {
//            getRedirectStrategy().sendRedirect(request, response, "/shelter/dashboard");
//        }else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VET"))) {
//            getRedirectStrategy().sendRedirect(request, response, "/vet/dashboard");
//        }else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADOPTER"))) {
//            getRedirectStrategy().sendRedirect(request, response, "/adopter/dashboard");
//        }
        else {
            getRedirectStrategy().sendRedirect(request, response, "/");         }
    }
}
