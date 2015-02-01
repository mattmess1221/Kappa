package mnm.mods.kappa.annotation.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import javax.lang.model.element.Modifier;

/**
 * Added onto a annotation to give it rules for modifiers. For adding multiple
 * rules, make the modifiers and requirements be parallel. The following example
 * will require public, require static, and deny final.
 *
 * <pre>
 * &#064;EnsureModifier({
 *         &#064;Rule(modifier = Modifier.PUBLIC, rule = true),
 *         &#064;Rule(modifier = Modifier.STATIC, rule = true),
 *         &#064;Rule(modifier = MODIFIER.FINAL, rule = false) })
 * &#064;Target(ElementType.ANNOTATION_TYPE)
 * public @interface Example {}
 * </pre>
 *
 * @author Matthew Messinger
 */
@Target(ElementType.ANNOTATION_TYPE)
public @interface EnsureModifier {

    /**
     * The rules.
     *
     * @return The rules
     */
    Rule[] value();

    /**
     * A rule for EnsureModifier
     */
    @Target({})
    public @interface Rule {

        /**
         * The modifiers to check
         *
         * @return Modifiers array
         */
        Modifier modifier();

        /**
         * true to require, false to deny.
         *
         * @return Requirements array
         */
        boolean rule();

    }
}
