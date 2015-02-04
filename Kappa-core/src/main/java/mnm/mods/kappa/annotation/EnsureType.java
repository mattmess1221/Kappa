package mnm.mods.kappa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Added to an annotation to restrict what type it can go on.
 *
 * @author Matthew Messinger
 */
@EnsureTarget(ElementType.TYPE)
@Target(ElementType.ANNOTATION_TYPE)
public @interface EnsureType {
    /**
     * The fully qualified name of the type
     *
     * @return The type name
     */
    String value();
}
