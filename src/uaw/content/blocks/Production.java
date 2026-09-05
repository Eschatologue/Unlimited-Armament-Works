package uaw.content.blocks;

import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.RadialEffect;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.draw.*;
import uaw.audiovisual.UAWPal;
import uaw.audiovisual.fx.ProductionFx;
import uaw.audiovisual.fx.ResourcingFx;
import uaw.content.UAWItems;
import uaw.content.UAWLiquids;
import uaw.content.blocks.production.*;
import uaw.utils.BlockDef;
import uaw.world.blocks.production.BoostGenericCrafter;
import uaw.world.blocks.production.ConversionDrill;

import static mindustry.type.ItemStack.with;
import static uaw.Vars.px;
import static uaw.Vars.tick;
import static uaw.utils.Calc.liquidsPerSec;

public class Production {

    public static Block placeholder,

    // Resourcing
    hydroThumper,
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

        // region Production I
        RefinerCarbon.load();
        RefinerOil.load();
        MixerChem.load();
        MixerReactor.load();
        FurnaceAdv.load();
        // Production I endregion

        // endregion

        // TODO Turn these into multicrafter

        // Production - TiSiC

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
