package uaw.content.blocks;

import arc.graphics.Color;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.BuildVisibility;
import uaw.content.UAWItems;
import uaw.entities.Calc;
import uaw.world.blocks.production.ConversionDrill;
import uaw.world.blocks.production.ThumperDrill;

import static mindustry.type.ItemStack.with;
import static uaw.Vars.tick;

public class BlocksProduction {

    public static Block placeholder,

    testThumper,
    // Resourcing
    hydroThumper, acidThumper,
    // Production - Sulphur
    oxidationKiln, chemicalSaturator,
    // Production - Anthracite
    attritionMill, calcinator,
    // Production - Phlogiston
    pyroliticExtractor,
    // Production II - Titanium Silicon Carbide
    inductionPress,
    // Production III - Stoutsteel
    isostaticCrucible;

    public static void load() {

        hydroThumper = new ConversionDrill("hydro-thumper") {{
            requirements(Category.production, with(
                    Items.copper, 125,
                    Items.lead, 50,
                    Items.graphite, 50
            ));

            size = 3;
            tier = 4;

            arrows = 1;
            baseArrowColor = Color.valueOf("989aa4");

            squareSprite = false;

            drillTime = 3 * tick;
            tileRequirement = Blocks.oreCoal;
            drilledItem = UAWItems.anthracite;
            drillEffect = new MultiEffect(
                    Fx.mineImpact,
                    Fx.hitLiquid.wrap(Pal.water),
                    Fx.mineImpactWave,
                    Fx.drillSteam
            );
            consumeLiquid(Liquids.water, Calc.liquidUnit(12));
        }};

        testThumper = new ThumperDrill("test-thumper") {{
            requirements(Category.production, BuildVisibility.sandboxOnly, with());

            size = 3;
            squareSprite = false;

            drillTime = 6 * tick;
            drillEffect = new MultiEffect(
                    Fx.mineImpact,
                    Fx.hitLiquid.wrap(Pal.water),
                    Fx.mineImpactWave,
                    Fx.drillSteam
            );
            alwaysUnlocked = true;
        }};

        calcinator = new GenericCrafter("calcinator") {{
            requirements(Category.crafting, with(Items.copper, 125, Items.lead, 50));

            size = 2;
            squareSprite = false;
            hasItems = true;
            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke, Fx.fireHit);

            craftTime = 2 * tick;
            consumeItems(with(Items.coal, 2));
            outputItems = with(UAWItems.anthracite, 2, UAWItems.sulphur, 1);
        }};

        oxidationKiln = new GenericCrafter("oxidation-kiln") {{
            requirements(Category.crafting, with(Items.copper, 125, Items.lead, 50));

            size = 2;
            squareSprite = false;
            hasItems = true;
            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke, Fx.fireHit);

            craftTime = 2 * tick;
            consumeItems(with(Items.lead, 2, Items.coal, 1));
            outputItems = with(UAWItems.sulphur, 1);
            outputLiquids = LiquidStack.with(Liquids.slag, Calc.liquidUnit(3));
        }};
    }
}
