package uaw.utils;

import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.type.LiquidStack;

import static uaw.utils.Calc.liquidsPerSec;

/**
 * Shorthand builders for {@link ItemStack} and {@link LiquidStack} arrays
 */
public class StackDef {

    /**
     * Shorthand for {@link ItemStack#with}.
     */
    public static ItemStack[] its(Object... items) {
        return ItemStack.with(items);
    }

    /**
     * Shorthand for {@link LiquidStack#with}.
     *
     * <p>Each amount is treated as a per-second figure and converted to the per-tick figure Mindustry liquid
     * modules expect, via {@link Calc#liquidsPerSec(float)}
     * Call sites no longer need to wrap amounts in {@code liquidsPerSec()}</p>
     */
    public static LiquidStack[] liq(Object... liquids) {
        int pairs = liquids.length / 2;
        LiquidStack[] result = new LiquidStack[pairs];

        for (int i = 0; i < pairs; i++) {
            Liquid liquid = (Liquid) liquids[i * 2];
            float amount = ((Number) liquids[i * 2 + 1]).floatValue();
            result[i] = new LiquidStack(liquid, liquidsPerSec(amount));
        }

        return result;
    }
}