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

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.processingUtils = new ProcessingUtils(processingEnv);
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
        // TODO process annotations
    }


}