package mnm.mods.kappa.fap;

import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.NoType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;

import mnm.mods.kappa.ProcessingUtils;

/**
 * Annotation processor for dealing with MinecraftForge's many annotations. Not
 * affiliated with Forge. Will cause an error if any of the supported
 * annotations' requirements are not met.<br>
 * <h3>Supported annotations:</h3>
 * <ul>
 * <li><b>@Mod</b>: Checks that the class applied to is public, non-abstract,
 * and has a no parameter public constructor. If it is an inner class, checks
 * that it is static.
 * <li><b>@EventHandler</b>: Checks that the method is public, non-static,
 * returns {@code void}, takes one parameter assignable to {@code FMLEvent}, and
 * its owner is annotated with {@code @Mod}.</li>
 * <li><b>@Instance</b>: Checks that field is public, static, non-final, of the
 * correct type, and that its owner is annotated with {@code @Mod}.</li>
 * <li><b>@Metadata</b>: Checks that the field is public, non-static, non-final,
 * and is of type {@code ModMetadata}.</li>
 * <li><b>@InstanceFactory</b>: Checks for empty parameters and returns
 * containing class, that it's in a {@code @Mod} annotated class, and that there
 * are no other {@code @InstanceFactory} methods in the class.</li>
 * <li><b>@SidedProxy</b>: Checks that a field is public, static, non-final,
 * that the specified classes are assignable to the type, each proxy class has a
 * no parameter public constructor, and its owner is annotated with {@code @Mod}
 * .</li>
 * <li><b>@SubscribeEvent</b>: Checks that the method is public, non-static,
 * returns {@code void}, and takes one parameter extending {@code Event}.</li>
 * <li><b>@Cancelable</b>: Checks that the type applied to is an {@code Event}.</li>
 * <li><b>@HasResult</b>: Checks that the type applied to is an {@code Event}.</li>
 * <li><b>@NetworkCheckHandler</b>: Checks that the method applied to returns
 * {@code boolean} and takes parameters {@code String} and {@code Side}</li>
 * </ul>
 *
 * @author Matthew Messinger
 */
public abstract class ForgeProcessor extends AbstractProcessor {

    private static final String MOD = "Mod";
    private static final String EVENT_HANDLER = "EventHandler";
    private static final String INSTANCE = "Instance";
    private static final String METADATA = "Metadata";
    private static final String INSTANCE_FACTORY = "InstanceFactory";
    private static final String SIDED_PROXY = "SidedProxy";
    private static final String SUBSCRIBE_EVENT = "SubscribeEvent";
    private static final String CANCELABLE = "Cancelable";
    private static final String HAS_RESULT = "HasResult";
    private static final String NETWORK_CHECK_HANDLER = "NetworkCheckHandler";

    private final String fmlPackage;

    private ProcessingUtils processingUtils;

    // object types
    private TypeElement modType;
    private TypeElement instanceType;
    private TypeElement sidedProxyType;
    private TypeElement instanceFactoryType;

    private TypeElement fmlEventType;
    private TypeElement eventType;
    private TypeElement modMetadataType;
    private TypeElement sideType;
    private TypeElement stringType;

    // other types
    private NoType voidType;
    private PrimitiveType booleanType;

    /**
     * Sets the package that FML is located in. {@code cpw.mods} for 1.7,
     * {@code net.minecraftforge} for 1.8.
     *
     * @param fmlPackage The package FML is located in.
     */
    protected ForgeProcessor(String fmlPackage) {
        this.fmlPackage = fmlPackage;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        Elements elementUtils = processingEnv.getElementUtils();
        this.processingUtils = new ProcessingUtils(processingEnv);

        final String common = fmlPackage + ".fml.common";
        this.modType = elementUtils.getTypeElement(common + ".Mod");
        this.instanceType = elementUtils.getTypeElement(common + ".Mod.Instance");
        this.sidedProxyType = elementUtils.getTypeElement(common + ".SidedProxy");
        this.instanceFactoryType = elementUtils.getTypeElement(common + ".Mod.InstanceFactoryType");

        this.modMetadataType = elementUtils.getTypeElement(common + ".ModMetadata");
        this.fmlEventType = elementUtils.getTypeElement(common + ".event.FMLEvent");
        this.eventType = elementUtils.getTypeElement(common + ".eventhandler.Event");
        this.sideType = elementUtils.getTypeElement(fmlPackage + ".fml.relauncher.Side");

        this.voidType = processingEnv.getTypeUtils().getNoType(TypeKind.VOID);
        this.stringType = elementUtils.getTypeElement("java.lang.String");
        this.booleanType = processingEnv.getTypeUtils().getPrimitiveType(TypeKind.BOOLEAN);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // Iterate through registered annotations
        for (TypeElement annotation : annotations) {
            process(annotation, roundEnv.getElementsAnnotatedWith(annotation));
        }
        return true;
    }

    private void process(TypeElement annotation, Set<? extends Element> elements) {
        for (Element element : elements) {
            switch (annotation.getSimpleName().toString()) {
            case MOD:
                checkMod((TypeElement) element);
                break;
            case EVENT_HANDLER:
                checkEventHandler((ExecutableElement) element);
                break;
            case INSTANCE:
                checkInstance((VariableElement) element);
                break;
            case METADATA:
                checkMetadata((VariableElement) element);
                break;
            case INSTANCE_FACTORY:
                checkInstanceFactory((ExecutableElement) element);
                break;
            case SIDED_PROXY:
                checkSidedProxy((VariableElement) element);
                break;
            case SUBSCRIBE_EVENT:
                checkSubscribeEvent((ExecutableElement) element);
                break;
            case CANCELABLE:
                checkCancelable((TypeElement) element);
                break;
            case HAS_RESULT:
                checkHasResult((TypeElement) element);
                break;
            case NETWORK_CHECK_HANDLER:
                checkNetworkCheckHandler((ExecutableElement) element);
                break;
            }
        }
    }

    private void checkMod(TypeElement mod) {
        // check public
        processingUtils.ensureModifier(mod, Modifier.PUBLIC, true);
        // check non-abstract
        processingUtils.ensureModifier(mod, Modifier.ABSTRACT, false);
        // check static if nested
        if (mod.getNestingKind().isNested()) {
            processingUtils.ensureModifier(mod, Modifier.STATIC, true);
        }
        // check for no argument constructor
        processingUtils.ensureConstructor(mod);
    }

    private void checkEventHandler(ExecutableElement eventHandler) {
        // check owner
        processingUtils.ensureOwnerAnnotation(eventHandler, modType);
        // check if public
        processingUtils.ensureModifier(eventHandler, Modifier.PUBLIC, true);
        // check if static
        processingUtils.ensureModifier(eventHandler, Modifier.STATIC, false);
        // check parameters
        processingUtils.ensureParameters(eventHandler, fmlEventType.asType());
        // check return
        processingUtils.ensureReturn(eventHandler, this.voidType);
    }

    private void checkInstance(VariableElement instance) {
        processingUtils.ensureOwnerAnnotation(instance, modType);
        // check public
        processingUtils.ensureModifier(instance, Modifier.PUBLIC, true);
        // check static
        processingUtils.ensureModifier(instance, Modifier.STATIC, true);
        // check non-final
        processingUtils.ensureModifier(instance, Modifier.FINAL, false);

        // check type
        AnnotationMirror instanceAnno = processingUtils.getAnnotation(instance,
                instanceType.asType());
        Map<String, AnnotationValue> values = processingUtils.getAnnotationValues(instanceAnno);
        String value = values.get("value").getValue().toString();
        if (value.isEmpty()) {
            TypeElement type = (TypeElement) instance.getEnclosingElement();
            processingUtils.ensureAssignable(instance, type);
            processingUtils.ensureSame(instance, type);
        }
    }

    private void checkMetadata(VariableElement element) {
        // check modifiers
        processingUtils.ensureModifier(element, Modifier.PUBLIC, true);
        processingUtils.ensureModifier(element, Modifier.STATIC, false);
        processingUtils.ensureModifier(element, Modifier.FINAL, false);
        // check type
        processingUtils.ensureAssignable(element, this.modMetadataType);
    }

    private void checkInstanceFactory(ExecutableElement element) {
        processingUtils.ensureOwnerAnnotation(element, this.modType);
        processingUtils.ensureModifier(element, Modifier.PUBLIC, true);
        processingUtils.ensureModifier(element, Modifier.STATIC, true);
        processingUtils.ensureParameters(element);
        processingUtils.ensureReturn(element, element.getEnclosingElement().asType());
        processingUtils.ensureNumberOfElements(element, this.instanceFactoryType, 1, 1);
    }

    private void checkSidedProxy(VariableElement sidedProxy) {
        processingUtils.ensureOwnerAnnotation(sidedProxy, modType);
        // check public
        processingUtils.ensureModifier(sidedProxy, Modifier.PUBLIC, true);
        // check static
        processingUtils.ensureModifier(sidedProxy, Modifier.STATIC, true);
        // check non-final
        processingUtils.ensureModifier(sidedProxy, Modifier.FINAL, false);

        AnnotationMirror sidedProxyAnno = processingUtils.getAnnotation(sidedProxy,
                sidedProxyType.asType());
        Map<String, AnnotationValue> values = processingUtils.getAnnotationValues(sidedProxyAnno);

        processingUtils.ensureClassExists(sidedProxy, sidedProxyAnno, values.get("clientSide"));
        processingUtils.ensureClassExists(sidedProxy, sidedProxyAnno, values.get("serverSide"));

        String client = values.get("clientSide").getValue().toString();
        String server = values.get("serverSide").getValue().toString();
        if (!client.isEmpty() && !server.isEmpty()) {
            TypeElement clientType = this.processingEnv.getElementUtils().getTypeElement(client);
            TypeElement serverType = this.processingEnv.getElementUtils().getTypeElement(server);
            // check if classes exist
            if (clientType != null && serverType != null) {
                // check types
                processingUtils.ensureAssignable(sidedProxy, clientType);
                processingUtils.ensureAssignable(sidedProxy, serverType);
                // check constructors
                processingUtils.ensureConstructor(clientType, sidedProxyAnno);
                processingUtils.ensureConstructor(serverType, sidedProxyAnno);
            }
        }
    }

    private void checkSubscribeEvent(ExecutableElement subscribeEvent) {
        // check public
        processingUtils.ensureModifier(subscribeEvent, Modifier.PUBLIC, true);
        // check non-static
        processingUtils.ensureModifier(subscribeEvent, Modifier.STATIC, false);
        // check non-final
        processingUtils.ensureModifier(subscribeEvent, Modifier.FINAL, false);
        // check parameter an Event
        processingUtils.ensureParameters(subscribeEvent, this.eventType.asType());

    }

    private void checkCancelable(TypeElement cancelable) {
        // check extends Event
        processingUtils.ensureInstanceof(cancelable, eventType);
    }

    private void checkHasResult(TypeElement hasResult) {
        // check extends Event
        processingUtils.ensureInstanceof(hasResult, eventType);
    }

    private void checkNetworkCheckHandler(ExecutableElement networkCheckHandler) {
        // check return boolean
        processingUtils.ensureReturn(networkCheckHandler, booleanType);
        // check params String and Side
        processingUtils.ensureParameters(networkCheckHandler, stringType.asType(),
                sideType.asType());
        // check public
        processingUtils.ensureModifier(networkCheckHandler, Modifier.PUBLIC, true);
    }

}
