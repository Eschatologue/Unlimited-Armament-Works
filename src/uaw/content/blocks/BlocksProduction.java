package uaw.content.blocks;

import arc.graphics.Color;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawFlame;
import mindustry.world.draw.DrawMulti;
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

        testThumper = new ThumperDrill("test-thumper") {{
            requirements(Category.production, BuildVisibility.sandboxOnly, with());

            size = 3;

            drillTime = 6 * tick;
            drillEffect = new MultiEffect(
                    Fx.mineImpact,
                    Fx.hitLiquid.wrap(Pal.water),
                    Fx.mineImpactWave,
                    Fx.drillSteam
            );
            alwaysUnlocked = true;
        }};

        // Resourcing

        hydroThumper = new ConversionDrill("hydro-thumper") {{
            requirements(Category.production, with(
                    Items.copper, 125,
                    Items.lead, 50,
                    Items.graphite, 50
            ));

            size = 3;

            drillTime = 3 * tick;
            tileRequirement = Blocks.oreCoal;
            drilledItem = UAWItems.anthracite;
            drillEffect = new MultiEffect(
                    Fx.mineImpact,
                    Fx.mineImpactWave,
                    Fx.drillSteam
            );
            consumeLiquid(Liquids.water, Calc.liquidUnit(12));
        }};

        // Production - Sulphur

        oxidationKiln = new GenericCrafter("oxidation-kiln") {{
            requirements(Category.crafting, with(Items.copper, 60, Items.graphite, 30));

            size = 2;
            squareSprite = false;

            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke);
            updateEffect = Fx.pulverizeSmall;

            craftTime = 2 * tick;
            consumeItems(with(Items.lead, 2, Items.coal, 1));
            outputItems = with(UAWItems.sulphur, 2);
            outputLiquids = LiquidStack.with(Liquids.slag, Calc.liquidUnit(3));

            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffc099")));
        }};

        // Production - Anthracite

        calcinator = new GenericCrafter("calcinator") {{
            requirements(Category.crafting, with(Items.copper, 120, Items.lead, 50));

            size = 2;
            squareSprite = false;

            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke, Fx.formsmoke);
            updateEffect = Fx.fireHit;

            craftTime = 2 * tick;
            consumeItems(with(Items.coal, 2));
            outputItems = with(UAWItems.anthracite, 1, UAWItems.sulphur, 1);
        }};

        attritionMill = new GenericCrafter("attrition-mill") {{
            requirements(Category.crafting, with(Items.graphite, 50));

            size = 2;
            squareSprite = false;

            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke);
            updateEffect = Fx.pulverize;

            craftTime = 2 * tick;
            consumeItems(with(Items.graphite, 1, Items.sand, 1));
            outputItems = with(UAWItems.anthracite, 2);

            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffc099")));
        }};


    }
}
