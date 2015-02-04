package mnm.mods.kappa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Added to a meta annotation to ensure its target.
 *
 * @author Matthew Messinger
 */
@EnsureTarget(ElementType.ANNOTATION_TYPE)
@Target(ElementType.ANNOTATION_TYPE)
public @interface EnsureTarget {
    /**
     * Any of the targets that the annotation should have.
     *
     * @return The targets
     */
    ElementType[] value();
}
