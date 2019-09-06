package io.pgsantos.toggles.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class RequestLogAspect {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restcontroller() {}

    @Pointcut("!@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    protected void allMappingsExceptDelete() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    protected void deleteOnly() {
    }

    @Pointcut("execution(public * *(..))")
    protected void loggingPublicOperation() {
    }

    @Around("restcontroller() && allMappingsExceptDelete() && loggingPublicOperation() && args(.., @RequestBody body)")
    public Object logAllControllerMethods(ProceedingJoinPoint joinPoint, Object body) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();

        Object result;

        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            log.info(
                    "<[Method: {}], [URI: {}], [Payload: {}]>",
                    request.getMethod(),
                    request.getRequestURI(),
                    body);
        }

        log.info("<[Result: {}]>", result != null ? result.toString() : "");

        return result;
    }

    @Before("restcontroller() && deleteOnly() && loggingPublicOperation() && args(..)")
    public void logDeleteMappings(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();

        if(request!=null) {
            log.info(
                    "<[Method: {}], [URI: {}]>",
                    request.getMethod(),
                    request.getRequestURI());
        }
    }
}