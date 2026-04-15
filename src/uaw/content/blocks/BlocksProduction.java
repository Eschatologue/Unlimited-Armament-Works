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

            alwaysUnlocked = true;
            size = 3;
            tier = 4;
            squareSprite = false;
            drillTime = 6 * tick;
            drillEffect = new MultiEffect(
                    Fx.mineImpact,
                    Fx.hitLiquid.wrap(Pal.water),
                    Fx.mineImpactWave,
                    Fx.drillSteam
            );
        }};

        oxidationKiln = new GenericCrafter("oxidation-kiln") {{
            //TODO Build Cost
            requirements(Category.crafting, with(Items.copper, 125, Items.lead, 50));

            size = 2;
            squareSprite = false;
            hasItems = true;
            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke, Fx.fireHit);

            consumeItems(with(Items.lead, 4, Items.coal, 2));
            outputItems = with(UAWItems.sulphur, 4);
            outputLiquids = LiquidStack.with(Liquids.slag, Calc.liquidUnit(6));
            craftTime = 2 * tick;
        }};

        calcinator = new GenericCrafter("calcinator") {{
            //TODO Build Cost
            requirements(Category.crafting, with(Items.copper, 125, Items.lead, 50));

            size = 2;
            squareSprite = false;
            hasItems = true;
            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke, Fx.fireHit);

            consumeItems(with(Items.coal, 2));
            outputItems = with(UAWItems.anthracite, 2, UAWItems.sulphur, 1);
            craftTime = 2 * tick;
        }};
    }
}
