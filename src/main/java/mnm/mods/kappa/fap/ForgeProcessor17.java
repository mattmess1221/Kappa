package mnm.mods.kappa.fap;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

/**
 * Forge annotation processor for Minecraft 1.7.
 */
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({
        "cpw.mods.fml.common.Mod",
        "cpw.mods.fml.common.Mod.EventHandler",
        "cpw.mods.fml.common.Mod.Instance",
        "cpw.mods.fml.common.Mod.Metadata",
        "cpw.mods.fml.common.Mod.InstanceFactory",
        "cpw.mods.fml.common.SidedProxy",
        "cpw.mods.fml.common.eventhandler.Event.HasResult",
        "cpw.mods.fml.common.eventhandler.SubscribeEvent",
        "cpw.mods.fml.common.eventhandler.Cancelable",
        "cpw.mods.fml.common.network.NetworkCheckHandler"
})
public class ForgeProcessor17 extends ForgeProcessor {

    public ForgeProcessor17() {
        super("cpw.mods");
    }
}
