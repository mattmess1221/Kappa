package mnm.mods.kappa;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;

public enum Messages {
    // modifiers
    NON_PUBLIC(Kind.ERROR, " must be public"),
    STATIC(Kind.ERROR, " cannot be static"),
    NON_STATIC(Kind.ERROR, " must be static"),
    FINAL(Kind.ERROR, " cannot be final"),
    NON_FINAL(Kind.ERROR, " must be final"),
    ABSTRACT(Kind.ERROR, " cannot be abstract"),
    NON_ABSTRACT(Kind.ERROR, " must be abstract"),
    // methods
    MISSING_CONSTRUCTOR(Kind.ERROR, " is missing required constructor with parameters "),
    INVALID_PARAMETERS(Kind.ERROR, " has invalid parameters. Must be "),
    INVALID_RETURN(Kind.ERROR, " has an invalid return type. Must be "),
    TOO_MANY(Kind.ERROR, " is invalid because there are too many members annotated with "),
    NOT_ENOUGH(Kind.ERROR, " is invalid because there are not enough members annotated with "),
    // types
    NOT_SUBTYPE(Kind.ERROR, " needs to be a subtype of "),
    NOT_ASSIGNABLE(Kind.ERROR, " needs to be assignable to "),
    // warnings
    NOT_FOUND(Kind.WARNING, " not found"),
    NOT_SAME(Kind.WARNING, " should be of type "),
    MISSING_ANNOTATION(Kind.WARNING, "'s owner should be annotated with ");

    private final Kind kind;
    private final String message;

    private Messages(Kind kind, String message) {
        this.kind = kind;
        this.message = message;
    }

    public void printMessage(Messager messager, String prefix, String suffix, Element element) {
        messager.printMessage(kind, prefix + message + suffix, element);
    }

    public void printMessage(Messager messager, String prefix, String suffix, Element type,
            AnnotationMirror annotation) {
        messager.printMessage(kind, prefix + message + suffix, type, annotation);
    }

    public void printMessage(Messager messager, String prefix, String suffix, Element type,
            AnnotationMirror annotation, AnnotationValue value) {
        messager.printMessage(kind, prefix + message + suffix, type, annotation, value);
    }

}
