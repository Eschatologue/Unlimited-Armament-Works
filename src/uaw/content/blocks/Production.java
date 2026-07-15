package uaw.content.blocks;

import arc.graphics.Color;
import dev.jojofr.multicrafter.MultiCrafterBlock;
import dev.jojofr.multicrafter.type.DrawRecipe;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.RadialEffect;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.*;
import uaw.audiovisual.UAWPal;
import uaw.audiovisual.fx.ProductionFx;
import uaw.audiovisual.fx.ResourcingFx;
import uaw.content.UAWItems;
import uaw.content.UAWLiquids;
import uaw.content.UAWRecipes;
import uaw.utils.BlockDef;
import uaw.world.blocks.production.BoostGenericCrafter;
import uaw.world.blocks.production.ConversionDrill;
import uaw.world.draw.UAWDrawFlame;

import static mindustry.type.ItemStack.with;
import static uaw.Vars.px;
import static uaw.Vars.tick;
import static uaw.utils.Calc.liquidsPerSec;

public class Production {

    public static Block placeholder,

    // Resourcing
    hydroThumper,
    // Production I
    refiner_carbon, refiner_oil,
    // Production II
    mixer_chemicalSynth, pyrolyticExtractor,
    // Production II - TiSiC
    sinteringFurnace,
    // Production III - Stoutsteel
    isostaticCrucible;

    public static void load() {

        // region Resourcing
        hydroThumper = new ConversionDrill("hydro-thumper") {{
            requirements(Category.production, with(
                    Items.copper, 125,
                    Items.lead, 50,
                    Items.graphite, 50
            ));

            BlockDef.general(this, 3);

            drillTime = 3 * tick;
            drillEffect = new MultiEffect(
                    Fx.mineImpact,
                    ResourcingFx.thumpImpactWave,
                    ResourcingFx.thumpParticles
            );
            consumeLiquid(Liquids.water, liquidsPerSec(15));
            itemCapacity = 50;
            addConversion(Blocks.oreCoal, UAWItems.anthracite);
        }};
        // endregion Resourcing

        // Production - Graphite
        // region Production I
        refiner_carbon = new MultiCrafterBlock("refiner-carbon") {{
            requirements(Category.crafting, with(Items.graphite, 100));
            BlockDef.general(this, 2);
            drawer = new DrawRecipe();
            recipes.addAll(
                    UAWRecipes.anthraciteSynth,
                    UAWRecipes.graphiteSynth,
                    UAWRecipes.hvyAnthraciteSynth
            );
            drawer = new DrawRecipe();
            UAWRecipes.anthraciteSynth.drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-base"),
                    new DrawRegion("-rec-1")
            );
            UAWRecipes.graphiteSynth.drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-base"),
                    new DrawRegion("-rec-2"),
                    new UAWDrawFlame() {{
                        topSuffix = "-rec-2-top";
                    }}
            );

        }};

        refiner_oil = new MultiCrafterBlock("refiner-oil") {{
            requirements(Category.crafting, with(Items.graphite, 150));
            BlockDef.general(this, 3);
            recipes.addAll(
                    UAWRecipes.oilProcessing_basic,
                    UAWRecipes.oilProcessing_adv,
                    UAWRecipes.coalLiquefaction,
                    UAWRecipes.sporePress
            );
            drawer = new DrawRecipe();

            UAWRecipes.oilProcessing_basic.drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-rec-1")
            );
            UAWRecipes.oilProcessing_basic.updateEffect = ProductionFx.chimneySmoke(UAWPal.refinerySmokeSulph);
            UAWRecipes.oilProcessing_basic.updateEffectSpread = 0.4f;
            UAWRecipes.oilProcessing_basic.updateEffectChance = 16;

            UAWRecipes.oilProcessing_adv.drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-rec-2")
            );
            UAWRecipes.oilProcessing_adv.updateEffect = ProductionFx.chimneySmoke(UAWPal.refinerySmokeSulph);
            UAWRecipes.oilProcessing_adv.updateEffectSpread = 0.2f;
            UAWRecipes.oilProcessing_adv.updateEffectChance = 16;
        }};
        // endregion

        // region Production II
        mixer_chemicalSynth = new MultiCrafterBlock("mixer-chemical-synth") {{
            requirements(Category.crafting, with(Items.graphite, 150, Items.metaglass, 50));
            BlockDef.general(this, 3);
            recipes.addAll(
                    UAWRecipes.sulphur,
                    UAWRecipes.sulphuricAcid,
                    UAWRecipes.sulphuricPyrite,
                    UAWRecipes.sulphuricBlast,
                    UAWRecipes.plastanium,
                    UAWRecipes.ketonite
            );
            drawer = new DrawRecipe();
        }};

        // endregion

        // region Legacy

//        attritionMill = new GenericCrafter("attrition-mill") {{
//            requirements(Category.crafting, with(Items.graphite, 60));
//
//            BlockDef.general(this, 2);
//
//            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke);
//            updateEffect = Fx.pulverize;
//
//            craftTime = 2 * tick;
//            consumeItems(with(Items.graphite, 2, Items.sand, 2));
//            outputItems = with(UAWItems.anthracite, 2);
//
//            drawer = new DrawMulti(
//                    new DrawRegion("-bottom"),
//                    new DrawArcSmelt() {{
//                        circleStroke = 1;
//                        particles = 25;
//                        particleLife = 30;
//                    }},
//                    new DrawRegion("-rot1", -4, true),
//                    new DrawRegion("-rot2", 4, true),
//                    new DrawDefault());
//        }};

//        oxidationKiln = new GenericCrafter("oxidation-kiln") {{
//            requirements(Category.crafting, with(Items.copper, 60, Items.graphite, 25));
//
//            BlockDef.general(this, 2);
//
//            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke);
//            updateEffect = Fx.pulverizeSmall;
//
//            craftTime = 2 * tick;
//            consumeItems(with(Items.lead, 2, Items.coal, 1));
//            outputItems = with(UAWItems.sulphur, 2);
//            outputLiquids = LiquidStack.with(Liquids.slag, liquidUnit(3));
//
//            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffc099")));
//        }};

        // endregion


        // TODO Turn these into multicrafter

        // Production - TiSiC
        sinteringFurnace = new BoostGenericCrafter("sintering-furnace") {{
            requirements(Category.crafting, with(
                    Items.titanium, 100,
                    Items.graphite, 50,
                    Items.silicon, 25
            ));

            BlockDef.general(this, 3);

            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke, Fx.vaporSmall.wrap(UAWItems.tisic.color));
            updateEffect = new MultiEffect(Fx.melting, Fx.burning, Fx.fireSmoke);

            craftTime = 2 * tick;
            consumeItems(with(Items.titanium, 3, Items.silicon, 1, Items.graphite, 2));
            consumeLiquids(LiquidStack.with(UAWLiquids.sulphuricAcid, liquidsPerSec(12))).boost();
            outputItems = with(UAWItems.tisic, 1);

            boostYieldMult = 1.5f;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawArcSmelt() {{
                        particles = 45;
                    }},
                    new DrawDefault()
            );
        }};

        // Production - Phlogiston
        pyrolyticExtractor = new GenericCrafter("pyrolytic-extractor") {{
            requirements(Category.crafting, with(
                    Items.titanium, 150,
                    Items.graphite, 100,
                    Items.silicon, 50,
                    Items.metaglass, 50
            ));

            BlockDef.general(this, 3);

            craftEffect =
                    new RadialEffect(
                            new MultiEffect(
                                    ProductionFx.crucibleSmoke(145, UAWPal.phlogistonFront),
                                    ProductionFx.crucibleSmoke(130, UAWPal.phlogistonMid),
                                    ProductionFx.crucibleSmoke(115, Pal.lightishGray)
                            ), 4, 90, 6) {{
                        rotationOffset = 45;
                    }};

            craftTime = 2 * tick;
            consumeItems(with(UAWItems.anthracite, 4));
            consumeLiquids(LiquidStack.with(
                    Liquids.oil, liquidsPerSec(18),
                    UAWLiquids.sulphuricAcid, liquidsPerSec(12)
            ));
            outputLiquid = new LiquidStack(UAWLiquids.phlogiston, liquidsPerSec(12));

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(UAWLiquids.phlogiston, 20 * px) {{
                        alpha = 1.2f;
                    }},
                    new DrawRegion("-middle"),
                    new DrawLiquidTile(UAWLiquids.sulphuricAcid, 32 * px) {{
                        alpha = 0.8f;
                    }},
                    new DrawDefault()
            );
        }};

        // Production - Stoutsteel Alloy
        isostaticCrucible = new BoostGenericCrafter("isostatic-crucible") {{
            requirements(Category.crafting, with(
                    Items.graphite, 300,
                    Items.silicon, 150,
                    UAWItems.tisic, 100
            ));

            BlockDef.general(this, 3);

            craftEffect =
                    new RadialEffect(
                            new MultiEffect(
                                    ProductionFx.crucibleSmoke(160, UAWPal.phlogistonFront),
                                    ProductionFx.crucibleSmoke(145, UAWPal.phlogistonMid),
                                    ProductionFx.crucibleSmoke(130, Pal.lightishGray)
                            ), 4, 90, 6) {{
                        rotationOffset = 0;
                    }};

            craftTime = 10 * tick;
            consumeItems(with(
                    UAWItems.tisic, 5,
                    Items.phaseFabric, 10
            ));
            consumeLiquid(UAWLiquids.phlogiston, liquidsPerSec(12));
            consumeLiquid(UAWLiquids.sulphuricAcid, liquidsPerSec(6)).boost();
            outputItems = with(UAWItems.stoutsteel, 1);

            boostCraftSpeedMult = 0.5f;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(UAWLiquids.phlogiston, 12 * px),
                    new DrawRegion("-middle"),
                    new DrawRegion("-rot", -3, true),
                    new DrawRegion("-top"),
                    new DrawFrames() {{
                        frames = 14;
                        interval = 0.125f * tick;
                        sine = false;
                    }}
            );
        }};
    }
}
