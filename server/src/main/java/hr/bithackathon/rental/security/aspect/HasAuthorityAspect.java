package hr.bithackathon.rental.security.aspect;

import java.util.Arrays;

import hr.bithackathon.rental.security.AuthoritiesConstants;
import hr.bithackathon.rental.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class HasAuthorityAspect {

    @Before("@annotation(hasAuthorityAnnotation)")
    public void checkValue(JoinPoint joinPoint, HasAuthority hasAuthorityAnnotation) throws Exception {
        var allowedAuthorities = hasAuthorityAnnotation.value();
        var allowedAuthoritiesWithAdmin = Arrays.copyOf(allowedAuthorities, allowedAuthorities.length + 1);
        allowedAuthoritiesWithAdmin[allowedAuthorities.length] = AuthoritiesConstants.ADMIN;

        if (!SecurityUtils.hasCurrentUserAnyOfAuthorities(allowedAuthoritiesWithAdmin)) {
            throw new AccessDeniedException(
                HasAuthorityAspect.class.getName() +
                    "Invalid authority" +
                    "User not authorized to access this resource"
            );
        }
    }

}
