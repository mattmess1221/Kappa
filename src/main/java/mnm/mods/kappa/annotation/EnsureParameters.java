package mnm.mods.kappa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Added onto a method annotation to give it rules for parameters. Parameters
 * are listed in order and use their qualifying name. A no parameter method will
 * take an empty array.
 * <p>
 * The following example will require 2 parameters of {@code String} and
 * {@code boolean}.
 * </p>
 *
 * <pre>
 * &#064;EnsureParameters({ &quot;java.lang.String&quot;, &quot;boolean&quot; })
 * &#064;Target(ElementType.ANNOTATION_TYPE)
 * public @interface Example {}
 * </pre>
 *
 * @author Matthew Messinger
 */
@EnsureTarget({ ElementType.METHOD, ElementType.CONSTRUCTOR })
@Target(ElementType.ANNOTATION_TYPE)
public @interface EnsureParameters {
    /**
     * The parameters that the method or constructor should have.
     *
     * @return The values
     */
    String[] values();
}
