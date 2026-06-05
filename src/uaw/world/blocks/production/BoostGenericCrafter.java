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

    @Override
    public void setStats() {
        super.setStats();

        if (boostCraftSpeedMult != 1f) {
            stats.remove(Stat.productionTime);
            stats.add(Stat.productionTime, table -> {
                float base = craftTime / 60f;
                float boosted = craftTime * boostCraftSpeedMult / 60f;

                String baseStr = Strings.fixed(base, 1) + StatUnit.seconds;
                String boostedStr = Strings.fixed(boosted, 1) + StatUnit.seconds;
                String multStr = Strings.fixed((boostCraftSpeedMult * 100), 0);

                table.add(Core.bundle.format("stat.uaw-boost-craft-speed", baseStr, boostedStr, multStr)).left();
            });
        }

        if (boostYieldMult != 1f) {
            stats.add(Stat.output, table -> {
                table.row();
                table.add(Core.bundle.format("stat.uaw-boost-yield", Strings.fixed(boostYieldMult, 1))).left();
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