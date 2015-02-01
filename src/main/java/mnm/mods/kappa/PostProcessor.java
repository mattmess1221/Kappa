package mnm.mods.kappa;

import java.lang.reflect.Field;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import mnm.mods.kappa.annotation.AccessChanged;

/**
 * Killjoy's Annotation Post Processing Application
 *
 * @author Matthew Messinger
 */
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("mnm.mods.kappa.annotation.*")
public class PostProcessor extends AbstractProcessor {

    @SuppressWarnings("unused")
    private ProcessingUtils processingUtils;
    private TypeElement accessChanged;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.processingUtils = new ProcessingUtils(processingEnv);
        this.accessChanged = processingEnv.getElementUtils().getTypeElement(
                "mnm.mods.kappa.annotation.AccessChanged");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                process(annotation, element);
            }
        }
        return true;
    }

    private void process(TypeElement annotation, Element element) {
        if (annotation.equals(accessChanged)) {
            processAccessChanged(element);
        }
    }

    private void processAccessChanged(Element element) {
        AccessChanged accessChanged = element.getAnnotation(AccessChanged.class);
        int oldAccess = accessChanged.value();
        int access = 0;
        for (Modifier mod : element.getModifiers()) {
            try {
                Field field = java.lang.reflect.Modifier.class.getField(mod.name());
                access |= field.getInt(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (access != oldAccess) {
            // TODO Note usages
        }
    }

}