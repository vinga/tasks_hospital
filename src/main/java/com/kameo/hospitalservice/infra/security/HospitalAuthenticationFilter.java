package com.kameo.hospitalservice.infra.security;

import com.kameo.hospitalservice.staff.domain.StaffMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This filter takes uuid from Authorization header and creates from it an Authentication
 * For real application uuid should be replaced with other authorization method (oauth2, jwt)
 */
@Component
@RequiredArgsConstructor
public class HospitalAuthenticationFilter extends OncePerRequestFilter {
    private final IdToPrincipalResolver idToPrincipalResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Optional.ofNullable(request.getHeader("Authorization"))
                .map(header -> header.toLowerCase().replace("bearer ", ""))
                .flatMap(xAuth -> idToPrincipalResolver.findById(UUID.fromString(xAuth))
                        .map(staffMember -> new StaffAuthentication(staffMember, xAuth)))
                .ifPresentOrElse(auth ->
                                SecurityContextHolder.getContext().setAuthentication(auth),
                        SecurityContextHolder::clearContext);

        filterChain.doFilter(request, response);
    }


    private static class StaffAuthentication extends AbstractAuthenticationToken {
        private final StaffMember principal;
        private final Object credentials;

        public StaffAuthentication(StaffMember principal, Object credentials) {
            super(List.of());
            this.principal = principal;
            this.credentials = credentials;
            setAuthenticated(true);
        }

        @Override
        public Object getPrincipal() {
            return principal;
        }

        @Override
        public Object getCredentials() {
            return credentials;
        }
    }

}
