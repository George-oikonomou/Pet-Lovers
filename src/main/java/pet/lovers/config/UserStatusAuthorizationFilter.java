package pet.lovers.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pet.lovers.entities.User;
import pet.lovers.entities.UserStatus;
import pet.lovers.repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class UserStatusAuthorizationFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;

    public UserStatusAuthorizationFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        boolean isShelterPath = (path.startsWith("/shelter/") || path.startsWith("/adoption-requests/shelter")) && !path.startsWith("/shelter/dashboard");
        boolean isVetPath = (path.startsWith("/vet/") || path.startsWith("/appointments/vet")) && !path.startsWith("/vet/dashboard");
        boolean isAdopterPath = (path.startsWith("/adopter/") || path.startsWith("/adoption-requests/adopter")) && !(path.startsWith("/adopter/dashboard"));

        if (isShelterPath || isVetPath || isAdopterPath) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

                if (!UserStatus.APPROVED.equals(user.getUserStatus())) {
                    request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpServletResponse.SC_FORBIDDEN);

                    if (isShelterPath) {
                        request.getRequestDispatcher("/error?error=ShelterAwaitingApproval").forward(request, response);
                    } else if (isVetPath) {
                        request.getRequestDispatcher("/error?error=VetAwaitingApproval").forward(request, response);
                    } else {
                        request.getRequestDispatcher("/error?error=AdopterAwaitingApproval").forward(request, response);
                    }
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
