package uaw.content.blocks;

import arc.graphics.Color;
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
import uaw.audiovisual.UAWFx;
import uaw.audiovisual.UAWPal;
import uaw.content.UAWItems;
import uaw.content.UAWLiquids;
import uaw.world.blocks.production.ConversionDrill;

import static mindustry.type.ItemStack.with;
import static uaw.Vars.px;
import static uaw.Vars.tick;
import static uaw.utils.Calc.liquidUnit;

public class BlocksProduction {

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

            size = 3;

            drillTime = 3 * tick;
            drillEffect = new MultiEffect(
                    Fx.mineImpact,
                    UAWFx.thumperImpactWave,
                    UAWFx.thumpParticles
            );
            consumeLiquid(Liquids.water, liquidUnit(12));
            addConversion(Blocks.oreCoal, UAWItems.anthracite);
        }};

        // endregion Resourcing

        // Production - Anthracite

        attritionMill = new GenericCrafter("attrition-mill") {{
            requirements(Category.crafting, with(Items.graphite, 60));

            size = 2;
            squareSprite = false;

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

        calcinator = new GenericCrafter("calcinator") {{
            requirements(Category.crafting, with(Items.graphite, 150, Items.copper, 50));

            size = 2;
            squareSprite = false;

            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke, Fx.formsmoke);
            updateEffect = Fx.fireHit;

            craftTime = 2 * tick;
            consumeItems(with(UAWItems.anthracite, 2));
            outputItems = with(Items.graphite, 2, UAWItems.sulphur, 1);
        }};

        // Production - Sulphur

        oxidationKiln = new GenericCrafter("oxidation-kiln") {{
            requirements(Category.crafting, with(Items.copper, 60, Items.graphite, 25));

            size = 2;
            squareSprite = false;

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

            size = 3;
            squareSprite = false;

            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke);

            craftTime = 2 * tick;
            consumeItems(with(UAWItems.sulphur, 2));
            consumeLiquids(LiquidStack.with(Liquids.water, liquidUnit(12)));
            outputLiquids = LiquidStack.with(UAWLiquids.sulphuricAcid, liquidUnit(12));

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

        sinteringFurnace = new GenericCrafter("sintering-furnace") {{
            requirements(Category.crafting, with(
                    Items.titanium, 100,
                    Items.graphite, 50,
                    Items.silicon, 25
            ));

            size = 3;
            squareSprite = false;
            buildTime = 5 * tick;

            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke, Fx.vaporSmall.wrap(UAWItems.tisic.color));
            updateEffect = new MultiEffect(Fx.melting, Fx.burning, Fx.fireSmoke);

            craftTime = 2 * tick;
            consumeItems(with(Items.titanium, 3, Items.silicon, 1, Items.graphite, 2));
            outputItems = with(UAWItems.tisic, 1);

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

            size = 3;
            squareSprite = false;
            buildTime = 5 * tick;

            craftEffect =
                    new RadialEffect(
                            new MultiEffect(
                                    UAWFx.crucibleSmoke(160, UAWPal.phlogistonFront),
                                    UAWFx.crucibleSmoke(145, UAWPal.phlogistonMid),
                                    UAWFx.crucibleSmoke(130, Pal.lightishGray)
                            ), 4, 90, 6) {{
                        rotationOffset = 45;
                    }};

            craftTime = 2 * tick;
            consumeItems(with(UAWItems.anthracite, 2));
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

        isostaticCrucible = new GenericCrafter("isostatic-crucible") {{
            requirements(Category.crafting, with(
                    Items.graphite, 300,
                    Items.silicon, 150,
                    UAWItems.tisic, 100
            ));

            size = 3;
            squareSprite = false;
            buildTime = 5 * tick;

//            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke, Fx.vaporSmall.wrap(UAWItems.tisic.color));
//            updateEffect = new MultiEffect(Fx.melting, Fx.burning, Fx.fireSmoke);

            craftTime = 5 * tick;
            consumeItems(with(UAWItems.tisic, 5));
            consumeLiquids(LiquidStack.with(
                    UAWLiquids.sulphuricAcid, liquidUnit(6),
                    UAWLiquids.phlogiston, liquidUnit(12)
            ));
            outputItems = with(UAWItems.stoutsteel, 1);

            // TODO Drawers
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawArcSmelt() {{
                        particles = 45;
                    }},
                    new DrawDefault()
            );
        }};
    }
}
