package mnm.mods.kappa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Added to a member whose access has been changed. Usages of that member will
 * be noted about this when compiled.
 *
 * @author Matthew Messinger
 */
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD })
public @interface AccessChanged {

    /**
     * The old access
     *
     * @return The old value
     */
    int value();
}
