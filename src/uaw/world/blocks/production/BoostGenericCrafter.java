package uaw.world.blocks.production;

import arc.Core;
import arc.math.Mathf;
import arc.util.Strings;
import mindustry.type.ItemStack;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

/**
 * GenericCrafter where consumeLiquid().boost() actually does something
 */
public class BoostGenericCrafter extends GenericCrafter {
    /**
     * Craft speed multiplier at full boost
     */
    public float boostCraftSpeedMult = 1f;
    /**
     * Output item multiplier at full boost
     */
    public float boostYieldMult = 1f;

    public BoostGenericCrafter(String name) {
        super(name);
    }

    /**
     * Shows one decimal place only when the value has a fractional part. 2.0 → "2", 1.5 → "1.5"
     */
    private static String formatNum(float value) {
        return value == (int) value ? String.valueOf((int) value) : Strings.fixed(value, 1);
    }

    @Override
    public void setStats() {
        super.setStats();

        if (boostCraftSpeedMult != 1f) {
            stats.remove(Stat.productionTime);
            stats.add(Stat.productionTime, table -> {
                float base = craftTime / 60f;
                float boosted = craftTime / boostCraftSpeedMult / 60f;
                // trim() guards against versions where localized() includes a leading space
                String unit = " " + StatUnit.seconds.localized().trim();

                table.add(Core.bundle.format("stat.uaw-boost-craft-speed",
                        formatNum(base) + unit,
                        formatNum(boosted) + unit,
                        formatNum(boostCraftSpeedMult)
                )).left();
            });
        }

        if (boostYieldMult != 1f) {
            stats.add(Stat.output, table -> {
                table.row();
                table.add(Core.bundle.format("stat.uaw-boost-yield", formatNum(boostYieldMult))).left();
            });
        }
    }

    public class BoostGenericCrafterBuild extends GenericCrafterBuild {

        /**
         * optionalEfficiency is 0 with no boost liquid, 1 when fully supplied
         * Lerping between 1 and the multiplier means no boost = normal speed
         */
        @Override
        public float getProgressIncrease(float baseTime) {
            float base = super.getProgressIncrease(baseTime);
            if (boostCraftSpeedMult == 1f) return base;
            return base * Mathf.lerp(1f, boostCraftSpeedMult, optionalEfficiency);
        }

        @Override
        public void craft() {
            consume();

            if (outputItems != null) {
                for (ItemStack output : outputItems) {
                    // Scale item count by the yield boost, using chance to resolve fractions
                    // e.g. 1 item x 1.5x boost = 1 item always, 50% chance of a second
                    float total = output.amount * Mathf.lerp(1f, boostYieldMult, optionalEfficiency);
                    int whole = (int) total;
                    float remainder = total - whole;

                    for (int i = 0; i < whole; i++) offload(output.item);
                    if (Mathf.chance(remainder)) offload(output.item);
                }
            }

            if (outputLiquid != null) {
                handleLiquid(this, outputLiquid.liquid, outputLiquid.amount);
            }

            craftEffect.at(x, y);
            progress %= 1f;
            totalProgress++;
        }
    }
}