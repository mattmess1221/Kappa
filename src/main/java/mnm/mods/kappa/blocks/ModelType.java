package mnm.mods.kappa.blocks;

public enum ModelType {

    /**
     * buttons
     * <p>
     * Arguments: {@code texture}
     */
    BUTTON("texture"),
    /**
     * Column on it's side<br>
     * log, quartz pillar, hay
     * <p>
     * Arguments: {@code end, side}
     */
    COLUMN_SIDE("end", "side"),
    /**
     * Used to give each side a different texture. Used by mushroom blocks.
     * <p>
     * Arguments: {@code particle, down, up, north, east, south, west}
     */
    CUBE("particle", "down", "up", "north", "east", "south", "west"),
    /**
     * Most normal cube blocks with 1 texture on all sides.
     * <p>
     * Arguments: {@code all}
     */
    CUBE_ALL("all"),
    /**
     * Uses different textures for the top and bottom.<br>
     * tnt, grass, mycelium
     * <p>
     * Arguments: {@code bottom, top, side}
     */
    CUBE_BOTTOM_TOP("bottom", "top", "side"),
    /**
     * Uses a different texture for the top and bottom (ends)<br>
     * log, quartz pillar, bookshelf, hay
     * <p>
     * Arguments: {@code end, side}
     */
    CUBE_COLUMN("end", "side"),
    /**
     * A mirrored version of {@link #CUBE}
     * <p>
     * Arguments: {@code down, up, north, south, west, east}
     */
    CUBE_MIRROED("down", "up", "north", "south", "west", "east"),
    /**
     * A mirrored version of {@link #CUBE_ALL}
     * <p>
     * Arguments: {@code all}
     */
    CUBE_MIRRORED_ALL("all"),
    /**
     * Uses a different texture for the top.<br>
     * jukebox
     * <p>
     * Arguments: {@code top, side}
     */
    CUBE_TOP("top", "side"),
    /**
     * flower, mushroom
     * <p>
     * ARguments: {@code cross}
     */
    CROSS("cross"),
    /**
     * wheat, potatoes, carrots
     * <p>
     * Arguments: {@code crop}
     */
    CROP("crop"),
    /**
     * carpet
     * <p>
     * Arguments: {@code particle, wool}
     */
    CARPET("particle", "wool"),
    /**
     * door bottom Arguments:
     * <p>
     * {@code bottom, top}
     */
    DOOR_BOTTOM("bottom", "top"),
    /**
     * door bottom right-hinge
     * <p>
     * Arguments: {@code bottom, top}
     */
    DOOR_BOTTOM_RH("bottom", "top"),
    /**
     * door top
     * <p>
     * Arguments: {@code bottom, top}
     */
    DOOR_TOP("bottom", "top"),
    /**
     * door top right-hinge
     * <p>
     * Arguments: {@code bottom, top}
     */
    DOOR_TOP_RH("bottom", "top"),
    /**
     * the bottom half of slabs
     * <p>
     * Arguments: {@code bottom, top, side}
     */
    HALF_SLAB("bottom", "top", "side"),
    /**
     * leaves, leaves2
     * <p>
     * Arguments: {@code all}
     */
    LEAVES("all"),
    /**
     * Pumpkins, droppers, dispensers, furnace
     * <p>
     * Arguments: {@code top, front, side}
     */
    ORIENTABLE("top", "front", "side"),
    /**
     * droppers, dispensers
     * <p>
     * Arguments: {@code front, side}
     */
    ORIENTABLE_VERTICAL("front", "side"),
    /**
     * stairs
     * <p>
     * Arguments: {@code bottom, top, side}
     */
    STAIRS("bottom", "top", "side"),
    /**
     * tallgrass, sugarcane
     * <p>
     * Arguments: {@code cross}
     */
    TALLGRASS("cross"),
    /**
     * slabs
     * <p>
     * Arguments: {@code bottom, top, side}
     */
    UPPER_SLAB("bottom", "top", "side");

    public String[] arguments;

    private ModelType(String... models) {
        this.arguments = models;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}