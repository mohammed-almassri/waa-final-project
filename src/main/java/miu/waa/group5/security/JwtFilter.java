package miu.waa.group5.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import miu.waa.group5.util.JWTUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/api/admins/login",
            "/api/owners/login",
            "/api/owners/signup",
            "/api/customers/login",
            "/api/customers/signup",
//            "/api/customers/properties",
            "/actuator/health",
            "/api/media/upload",
            "/"
    );



    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        //TODO: awful code
        if ("/".equals(path) || EXCLUDED_PATHS.stream().filter(p->!p.equals("/")).anyMatch(path::startsWith)) {
            filterChain.doFilter(request, response); // Skip JWT validation for these paths
            return;
        }

        if ("/api/customers/properties".equals(path)) {
            var token = extractTokenFromRequest(request);
            if (token != null) {
                if (jwtUtil.validateToken(token)) {
                    SecurityContextHolder.getContext().setAuthentication(jwtUtil.getAuthentication(token));
                } else {
                    throw new JwtException("Invalid JWT token");
                }
            }
            filterChain.doFilter(request, response);
            return;
        }

        var token = extractTokenFromRequest(request);

        if (token != null && jwtUtil.validateToken(token)) {
            SecurityContextHolder.getContext().setAuthentication(jwtUtil.getAuthentication(token));
        }
        else{
            throw new JwtException("Invalid JWT token");
        }

        filterChain.doFilter(request, response);
    }



    /**
     * Helper method
     *
     * @param request
     * @return
     */
    public String extractTokenFromRequest(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            var token = authorizationHeader.substring(7);
            return token;
        }
        return null;
    }
}
