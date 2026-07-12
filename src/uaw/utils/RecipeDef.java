package uaw.utils;

import dev.jojofr.multicrafter.type.IOEntry;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;

/**
 * Static helper methods for building {@link IOEntry} instances for {@link dev.jojofr.multicrafter.type.Recipe} definitions
 * <p>Collapses {@code new IOEntry().withItems(...).withLiquids(...)} into a single call</p>
 */
public class RecipeDef {

    /**
     * IOEntry from items only.
     */
    public static IOEntry io(ItemStack... items) {
        return new IOEntry().withItems(items);
    }

    /**
     * IOEntry from liquids only.
     */
    public static IOEntry io(LiquidStack... liquids) {
        return new IOEntry().withLiquids(liquids);
    }

    /**
     * IOEntry from both items and liquids.
     */
    public static IOEntry io(ItemStack[] items, LiquidStack[] liquids) {
        return new IOEntry().withItems(items).withLiquids(liquids);
    }
}