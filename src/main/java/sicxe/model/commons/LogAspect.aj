package sicxe.model.commons;


import org.apache.log4j.Logger;

/**
 * Created by maciek on 26.10.15.
 */
public aspect LogAspect {
    pointcut publicMethodExecuted(): execution(public * *(..));

    after(): publicMethodExecuted() {
        String clazz = thisJoinPoint.getKind();
        Logger LOG = Logger.getLogger(clazz);
        LOG.debug("Enters on method " + clazz);
        Object[] arguments = thisJoinPoint.getArgs();

        for (int i = 0; i < arguments.length; i++) {
            Object argument = arguments[i];
            if (argument != null) {
                LOG.debug("With argument ofvalue " + argument);
            }
        }
        LOG.debug("Exits on method " + clazz);
    }
}
