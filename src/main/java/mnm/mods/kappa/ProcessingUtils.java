package mnm.mods.kappa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Contains methods for validating annotation usage.
 *
 * @author Matthew Messinger
 */
public final class ProcessingUtils {

    private Messager messager;
    private Types typeUtils;
    private Elements elementUtils;

    public ProcessingUtils(ProcessingEnvironment processingEnv) {
        this.messager = processingEnv.getMessager();
        this.typeUtils = processingEnv.getTypeUtils();
        this.elementUtils = processingEnv.getElementUtils();
    }

    /**
     * Ensures an element's owner is annotated. If it is not, prints a compile
     * warning to that element.
     *
     * @param element The element
     * @param annotation The annotation
     */
    public void ensureOwnerAnnotation(Element element, TypeElement annotation) {
        if (!hasAnnotation(element.getEnclosingElement(), annotation.asType())) {
            String typeName = getElementTypeName(element);
            String anno = annotation.getSimpleName().toString();
            Messages.MISSING_ANNOTATION.printMessage(messager, typeName, "@" + anno, element);
        }
    }

    /**
     * Tests if an {@link Element} has a {@link Modifier} and if it should
     * according to {@code req}. Prints a compile error if it doesn't match the
     * requirements.
     *
     * @param element The element to test
     * @param modifier The modifier to test for
     * @param req True to require, false to restrict
     */
    public void ensureModifier(Element element, Modifier modifier, boolean req) {
        boolean has = element.getModifiers().contains(modifier);
        boolean error = req ? !has : has;
        if (error) {
            Messages message = null;
            switch (modifier) {
            case ABSTRACT:
                message = req ? Messages.NON_ABSTRACT : Messages.ABSTRACT;
                break;
            case FINAL:
                message = req ? Messages.NON_FINAL : Messages.FINAL;
                break;
            case STATIC:
                message = req ? Messages.NON_STATIC : Messages.STATIC;
                break;
            case PUBLIC:
                message = req ? Messages.NON_PUBLIC : null;
                break;
            default:

            }
            if (message != null) {
                String typeName = getElementTypeName(element);
                message.printMessage(messager, typeName, "", element);
            }
        }
    }

    /**
     * Ensures that a {@link TypeElement} has a public constructor with the
     * given arguments. Prints a compile error if it does not.
     *
     * @param type The type to test
     * @param args The arguments of the constructor
     * @see ProcessingUtils#ensureConstructor(TypeElement, AnnotationMirror,
     *      TypeMirror...)
     */
    public void ensureConstructor(TypeElement type, TypeMirror... args) {
        ensureConstructor(type, null, args);
    }

    /**
     * Ensures that a {@link TypeElement} has a public constructor with the
     * given arguments. Prints a compile error if it does not.
     *
     * @param type The type to test
     * @param annotation The annotation associated with it
     * @param args The arguments of the constructor
     */
    public void ensureConstructor(TypeElement type, AnnotationMirror annotation,
            TypeMirror... args) {
        for (Element element : type.getEnclosedElements()) {
            if (element.getKind().equals(ElementKind.CONSTRUCTOR)) {
                if (signatureMatches(((ExecutableElement) element).getParameters(), args)) {
                    // ensure public
                    ensureModifier(element, Modifier.PUBLIC, true);
                    return;
                }
            }
        }
        String prefix = getElementTypeName(type);
        String suffix = generateParameters(args);
        if (annotation == null) {
            Messages.MISSING_CONSTRUCTOR.printMessage(messager, prefix, suffix, type);
        } else {
            Messages.MISSING_CONSTRUCTOR.printMessage(messager, prefix, suffix, type, annotation);
        }
    }

    /**
     * Ensures that an {@link ExecutableElement} (method or constructor) has the
     * given parameters. If the parameters are assignable, it passes. Prints a
     * compile error if it does not.
     *
     * @param method The method to test
     * @param params The parameters
     */
    public void ensureParameters(ExecutableElement method, TypeMirror... params) {
        List<? extends VariableElement> list = method.getParameters();
        if (list.size() == params.length) {
            if (signatureMatches(list, params)) {
                return;
            }
        }
        String prefix = getElementTypeName(method);
        String suffix = generateParameters(params);
        Messages.INVALID_PARAMETERS.printMessage(messager, prefix, suffix, method);
    }

    /**
     * Ensures that a {@link ExecutableElement} (method) has the given return
     * type. Must be exact. Prints a compile error if it is not.
     *
     * @param method The method to check
     * @param retVal The return value
     */
    public void ensureReturn(ExecutableElement method, TypeMirror retVal) {
        if (!method.getReturnType().equals(retVal)) {
            String prefix = getElementTypeName(method);
            String suffix = retVal.toString();
            Messages.INVALID_RETURN.printMessage(messager, prefix, suffix, method);
        }
    }

    /**
     * Ensures that an element's type is an instance of another.
     *
     * @param t1 The element to check
     * @param t2 The type
     */
    public void ensureInstanceof(TypeElement t1, TypeElement t2) {
        if (!typeUtils.isSubtype(t1.asType(), t2.asType())) {
            String prefix = getElementTypeName(t1);
            String suffix = t2.getSimpleName().toString();
            Messages.NOT_SUBTYPE.printMessage(messager, prefix, suffix, t1);
        }
    }

    /**
     * Ensures that an {@link Element}'s type is assignable to another type.
     * Prints a compile error if it is not.
     *
     * @param element The element to check
     * @param type The type
     * @see ProcessingUtils#ensureSame(Element, TypeElement)
     */
    public void ensureAssignable(VariableElement element, TypeElement type) {
        if (!typeUtils.isAssignable(type.asType(), element.asType())) {
            String prefix = getElementTypeName(element);
            String suffix = type.getSimpleName().toString();
            Messages.NOT_ASSIGNABLE.printMessage(messager, prefix, suffix, element);
        }
    }

    /**
     * Ensures that an {@link Element}'s type is the same as another. Prints a
     * compile warning if it isn't.
     *
     * @param element The element to test
     * @param type The type
     */
    public void ensureSame(Element element, TypeElement type) {
        if (!typeUtils.isSameType(element.asType(), type.asType())) {
            String prefix = getElementTypeName(element);
            String suffix = type.getSimpleName().toString();
            Messages.NOT_SAME.printMessage(messager, prefix, suffix, element);
        }
    }

    /**
     * Ensures that a class name from an {@link AnnotationValue} exists. Prints
     * a warning if it doesn't, as it may be available at runtime.
     *
     * @param element The element
     * @param mirror The annotation
     * @param value The value with the class name string
     */
    public void ensureClassExists(Element element, AnnotationMirror mirror, AnnotationValue value) {
        String name = value.getValue().toString();
        TypeElement type = elementUtils.getTypeElement(name);
        if (type == null) {
            String prefix = "Class " + name;
            Messages.NOT_FOUND.printMessage(messager, prefix, "", element, mirror, value);
        }
    }

    /**
     * Ensures that there are a minimum and maximum number of elements in a
     * class with an annotation.
     *
     * @param element The element
     * @param annotation The annotation
     * @param low The minimum number of elements
     * @param high The maximum number of elements
     */
    public void ensureNumberOfElements(Element element, TypeElement annotation, int low, int high) {
        TypeElement type = (TypeElement) element.getEnclosingElement();
        List<? extends Element> list = type.getEnclosedElements();
        List<Element> has = new ArrayList<>();
        for (Element e : list) {
            if (hasAnnotation(element, annotation.asType())) {
                has.add(e);
            }
        }
        Messages msg = null;
        if (has.size() < low) {
            msg = Messages.NOT_ENOUGH;
        } else if (has.size() > high) {
            msg = Messages.TOO_MANY;
        }
        if (msg != null) {
            msg.printMessage(messager, getElementTypeName(element), annotation.toString(),
                    element);
        }
    }

    /**
     * Creates a map of all values from an annotation.
     *
     * @param annotation The annotation
     * @return The map
     */
    public Map<String, AnnotationValue> getAnnotationValues(AnnotationMirror annotation) {
        Map<? extends ExecutableElement, ? extends AnnotationValue> map = this.elementUtils
                .getElementValuesWithDefaults(annotation);
        Map<String, AnnotationValue> values = new HashMap<>();
        for (Element e : annotation.getAnnotationType().asElement().getEnclosedElements()) {
            String name = e.getSimpleName().toString();
            AnnotationValue value = map.get(e);
            values.put(name, value);
        }
        return values;
    }

    /**
     * Gets the annotation mirror of an element.
     *
     * @param type The type with the annotation
     * @param annotation The annotation type
     * @return The annotation
     */
    public AnnotationMirror getAnnotation(Element type, TypeMirror annotation) {
        for (AnnotationMirror mirror : type.getAnnotationMirrors()) {
            if (mirror.getAnnotationType().equals(annotation)) {
                return mirror;
            }
        }
        return null;
    }

    private boolean signatureMatches(List<? extends VariableElement> params1, TypeMirror[] params2) {
        if (params2.length == params1.size()) {
            for (int i = 0; i < params1.size(); i++) {
                if (!typeUtils.isSubtype(params1.get(i).asType(), params2[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private String generateParameters(TypeMirror[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (TypeMirror type : args) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(type.toString());
        }
        sb.append(")");
        return sb.toString();
    }

    private boolean hasAnnotation(Element element, TypeMirror annotation) {
        return getAnnotation(element, annotation) != null;
    }

    private String getElementTypeName(Element element) {
        ElementKind kind = element.getKind();
        String type = kind.toString().toLowerCase().replace('_', ' ');
        // capitalize first letter
        type = Character.toUpperCase(type.charAt(0)) + type.substring(1);
        String name = element.getSimpleName().toString();
        return type + " " + name;
    }

}
