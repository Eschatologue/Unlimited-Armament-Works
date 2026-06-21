package uaw.content.blocks;

import arc.graphics.Color;
import dev.jojofr.multicrafter.MultiCrafterBlock;
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

import static mindustry.type.ItemStack.with;
import static uaw.Vars.px;
import static uaw.Vars.tick;
import static uaw.utils.Calc.liquidUnit;

public class Production {

    public static Block placeholder,

    // Resourcing
    hydroThumper, acidThumper,
    // Production - Anthracite
    attritionMill, calcinator,
    // Production - Sulphur
    oxidationKiln, chemicalSaturator,
    // Production - Phlogiston
    pyrolyticExtractor,
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
            consumeLiquid(Liquids.water, liquidUnit(15));
            itemCapacity = 50;
            addConversion(Blocks.oreCoal, UAWItems.anthracite);
        }};
        // endregion Resourcing

        // Production - Anthracite
        attritionMill = new GenericCrafter("attrition-mill") {{
            requirements(Category.crafting, with(Items.graphite, 60));

            BlockDef.general(this, 2);

            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke);
            updateEffect = Fx.pulverize;

            craftTime = 2 * tick;
            consumeItems(with(Items.graphite, 2, Items.sand, 2));
            outputItems = with(UAWItems.anthracite, 2);

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawArcSmelt() {{
                        circleStroke = 1;
                        particles = 25;
                        particleLife = 30;
                    }},
                    new DrawRegion("-rot1", -4, true),
                    new DrawRegion("-rot2", 4, true),
                    new DrawDefault());
        }};

        // Production - Graphite
        calcinator = new MultiCrafterBlock("calcinator") {{
            requirements(Category.crafting, with(Items.graphite, 150, Items.copper, 50));
            BlockDef.general(this, 2);
            itemCapacity = 50;

            recipes.addAll(UAWRecipes.Anthracite.fromCoal, UAWRecipes.Graphite.fromAnthracite);
        }};

        // Production - Sulphur
        oxidationKiln = new GenericCrafter("oxidation-kiln") {{
            requirements(Category.crafting, with(Items.copper, 60, Items.graphite, 25));

            BlockDef.general(this, 2);

            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke);
            updateEffect = Fx.pulverizeSmall;

            craftTime = 2 * tick;
            consumeItems(with(Items.lead, 2, Items.coal, 1));
            outputItems = with(UAWItems.sulphur, 2);
            outputLiquids = LiquidStack.with(Liquids.slag, liquidUnit(3));

            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffc099")));
        }};
        chemicalSaturator = new GenericCrafter("chemical-saturator") {{
            requirements(Category.crafting, with(Items.graphite, 50, Items.metaglass, 25));
            BlockDef.general(this, 3);

            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke);

            craftTime = 2 * tick;
            consumeItems(with(UAWItems.sulphur, 2));
            consumeLiquids(LiquidStack.with(Liquids.water, liquidUnit(12)));
            outputLiquids = LiquidStack.with(UAWLiquids.sulphuricAcid, liquidUnit(12));
            itemCapacity = 50;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(Liquids.water, 8 * px) {{
                        alpha = 0.8f;
                    }},
                    new DrawLiquidTile(UAWLiquids.sulphuricAcid, 8 * px) {{
                        alpha = 1.2f;
                    }},
                    new DrawRegion("-rot", 3),
                    new DrawDefault());
        }};

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
            consumeLiquids(LiquidStack.with(UAWLiquids.sulphuricAcid, liquidUnit(12))).boost();
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
                    Liquids.oil, liquidUnit(18),
                    UAWLiquids.sulphuricAcid, liquidUnit(12)
            ));
            outputLiquid = new LiquidStack(UAWLiquids.phlogiston, liquidUnit(12));

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
            consumeLiquid(UAWLiquids.phlogiston, liquidUnit(12));
            consumeLiquid(UAWLiquids.sulphuricAcid, liquidUnit(6)).boost();
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
