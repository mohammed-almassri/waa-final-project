package miu.waa.group5.Aspect;

import miu.waa.group5.repository.UserRepository;
import miu.waa.group5.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Aspect
@Component
@Configuration
public class OwnerInactiveCheckAspect {

    @Autowired
    private UserService userService;

    @Around("within(miu.waa.group5.controller.*)")
    public Object checkOwnerStatus(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            var user = userService.findByEmail(username);
            if (user != null && "OWNER".equals(user.getRole()) && !user.isActive()) {
                throw new DisabledException( "Owner account is inactive.");
            }
        }

        return joinPoint.proceed();
    }
}