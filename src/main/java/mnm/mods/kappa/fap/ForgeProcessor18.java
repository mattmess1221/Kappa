package mnm.mods.kappa.fap;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

/**
 * Forge annotation processor for Forge 1.8
 */
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({
        "net.minecraftforge.fml.common.Mod",
        "net.minecraftforge.fml.common.Mod.EventHandler",
        "net.minecraftforge.fml.common.Mod.Instance",
        "net.minecraftforge.fml.common.Mod.Metadata",
        "net.minecraftforge.fml.common.Mod.InstanceFactory",
        "net.minecraftforge.fml.common.SidedProxy",
        "net.minecraftforge.fml.common.eventhandler.SubscribeEvent",
        "net.minecraftforge.fml.common.eventhandler.Cancelable",
        "net.minecraftforge.fml.common.eventhandler.Event.HasResult",
        "net.minecraftforge.fml.common.network.NetworkCheckHandler"
})
public class ForgeProcessor18 extends ForgeProcessor {

    public ForgeProcessor18() {
        super("net.minecraftforge");
    }
}
