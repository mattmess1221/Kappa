package mnm.mods.kappa;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("mnm.mods.kappa.annotation.meta.*")
public class MetaProcessor extends AbstractProcessor {

    private static final String MODIFIER = "EnsureModifier";
    private static final String PARAMETERS = "EnsureParameters";
    private static final String RETURN = "EnsureReturn";
    private static final String TARGET = "EnsureTarget";
    private static final String TYPE = "EnsureType";

    @SuppressWarnings("unused")
    private ProcessingUtils processingUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.processingUtils = new ProcessingUtils(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement t : annotations) {
            process(t, roundEnv);
        }
        return true;
    }

    private void process(TypeElement annotation, RoundEnvironment roundEnv) {
        for (Element e : roundEnv.getElementsAnnotatedWith(annotation)) {
            TypeElement t = (TypeElement) e;
            switch (annotation.getSimpleName().toString()) {
            case MODIFIER:
                processModifier(t);
                break;
            case PARAMETERS:
                processParameters(t);
                break;
            case RETURN:
                processReturn(t);
                break;
            case TARGET:
                processTarget(t);
                break;
            case TYPE:
                processType(t);
                break;
            }
        }
    }

    private void processModifier(TypeElement element) {
        // TODO
    }

    private void processParameters(TypeElement element) {
        // TODO
    }

    private void processReturn(TypeElement element) {
        // TODO
    }

    private void processTarget(TypeElement element) {
        // TODO
    }

    private void processType(TypeElement element) {
        // TODO
    }
}
