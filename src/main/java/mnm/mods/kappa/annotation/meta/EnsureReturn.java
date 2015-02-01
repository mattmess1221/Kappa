package mnm.mods.kappa.annotation.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Added to a method annotation to restrict what a method's return type can be.
 *
 * @author Matthew
 */
@EnsureTarget(ElementType.METHOD)
@Target(ElementType.ANNOTATION_TYPE)
public @interface EnsureReturn {
    /**
     * The fully qualified name of the return type.
     *
     * @return Return type name
     */
    String value();
}
