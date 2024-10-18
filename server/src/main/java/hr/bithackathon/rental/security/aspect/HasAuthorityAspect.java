package hr.bithackathon.rental.security.aspect;

import hr.bithackathon.rental.security.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HasAuthorityAspect {

    @Before("@annotation(hasAuthorityAnnotation)")
    public void checkValue(JoinPoint joinPoint, HasAuthority hasAuthorityAnnotation) throws Exception {
        String[] allowedAuthorities = hasAuthorityAnnotation.value();
        if (!SecurityUtils.hasCurrentUserAnyOfAuthorities(allowedAuthorities)) throw new AccessDeniedException(
                HasAuthorityAspect.class.getName() +
                "Invalid authority" +
                "User not authorized to access this resource"
        );
    }
}
