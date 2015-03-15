package mnm.mods.kappa.blocks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Apply to a {@code Block} field to automatically creates a blockstate.json for
 * it in the assets folder. Models will inherit from cube.
 * <p>
 * Example Usage:
 *
 * <pre>
 * &#064;BlockModel(namespace = &quot;modid&quot;, blockname = &quot;custom_block&quot;)
 * Block block;
 * </pre>
 *
 * This will create a generic, cube_all block with files at the following
 * locations:
 * <p>
 * <ul>
 * <li>{@code assets/modid/blockstates/custom_block.json}</li>
 * <li>{@code assets/modid/models/block/custom_block.json}</li>
 * <li>{@code assets/modid/models/item/custom_block.json}</li>
 * </ul>
 */
@Target({ ElementType.FIELD, ElementType.LOCAL_VARIABLE })
@Retention(RetentionPolicy.SOURCE)
public @interface BlockDef {

    /**
     * The desired namespace, usually the modid. Will be the name of the
     * subfolder in the {@code assets} folder.
     * <p>
     * Example: {@code assets/modid/blockstates/custom_block.json} with
     * {@code modid} being the namespace.
     */
    String namespace();

    /**
     * The unlocalized name (or texture name) of the block. Will be used as the
     * name of the json file.
     * <p>
     * Example: {@code assets/modid/blockstates/custom_block.json} with
     * {@code custom_block} being the block name.
     */
    String blockname();

    BlockVariant[] variants() default {};
}
