package mnm.mods.kappa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Added to a Constructor, Field, or Method to indicate that it has been added
 * to the codebase and might not be available at runtime.
 *
 * @author Matthew Messinger
 */
@Target({ ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD })
public @interface Added {}
