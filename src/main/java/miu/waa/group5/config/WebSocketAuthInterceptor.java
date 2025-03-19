package miu.waa.group5.config;

import miu.waa.group5.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final JWTUtil jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSocketAuthInterceptor(JWTUtil jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            // Extract token from headers or query parameters
            String token = extractToken(accessor);

            if (token != null && jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Create authentication object
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Set authentication in header
                accessor.setUser(authentication);
            }
        }
        return message;
    }

    private String extractToken(StompHeaderAccessor accessor) {
        // Try to get from headers first
        String authorization = accessor.getFirstNativeHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }

        // If not in headers, try from query params
        String query = accessor.getFirstNativeHeader(StompHeaderAccessor.NATIVE_HEADERS);
        if (query == null) {
            // Try to get from the session attributes
            Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
            if (sessionAttributes != null) {
                String token = (String) sessionAttributes.get("token");
                if (token != null) {
                    return token;
                }
            }
        }

        return null;
    }
}