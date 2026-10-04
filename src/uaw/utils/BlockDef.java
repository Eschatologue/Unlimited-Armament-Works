package uaw.utils;

import mindustry.world.Block;

import static uaw.Vars.tick;

public class BlockDef {

    public static void general(Block b, int size) {
        general(b, size, 50, 100);
    }

    /**
     * Used to standardise most UAW Blocks
     */
    public static void general(Block block, int size, int itemCapacity, int liquidCapacity) {
        block.size = size;
        block.buildTime = buildSeconds(size);
        block.itemCapacity = itemCapacity;
        block.liquidCapacity = liquidCapacity;
        block.squareSprite = false;
    }

    public static float buildSeconds(int size) {
        return switch (size) {
            case 1 -> 1f;
            case 2 -> 2f;
            case 3 -> 5f;
            case 4 -> 10f;
            case 5 -> 15f;
            case 6 -> 30f;
            default -> 30f * (size - 5);
        } * tick;
    }
}
