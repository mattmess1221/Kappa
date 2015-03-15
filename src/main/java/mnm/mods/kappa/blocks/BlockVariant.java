package mnm.mods.kappa.blocks;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Creates a variants entry in the blockstates as well as a model. Everything
 * has a default so you can change what you need.
 */
@Target({})
@Retention(RetentionPolicy.SOURCE)
public @interface BlockVariant {

    /**
     * The variant name.
     * <p>
     * Defaults to {@code normal}.
     */
    String variant() default "normal";

    /**
     * The name of the model. Usually the block name. If empty, gets set to the
     * block name.
     * <p>
     * Defaults to {@code ""}.
     */
    String modelName() default "";

    /**
     * The texture names for the model arguments. An empty array will be
     * replaced with the block name.
     * <p>
     * Defaults to an empty array.
     *
     * @see ModelType#arguments
     */
    String[] textures() default {};

    /**
     * This varient's model name.
     * <p>
     * Defaults to {@link ModelType#CUBE_ALL}.
     */
    ModelType modelType() default ModelType.CUBE_ALL;

    /**
     * Determines whether a block model should be created. Set this to false to
     * not create a model file for this variant in case you want a special one.
     * <p>
     * Use {@link #createItem()} for the item model.
     * <p>
     * Defaults to {@code true}
     */
    boolean createModel() default true;

    /**
     * Determines whether an item model should be created. Set this to false to
     * not create a model file for this variant in case you want a special one.
     * <p>
     * Use {@link #createModel()} for the block model.
     * <p>
     * Defaults to {@code true}
     */
    boolean createItem() default true;
}
