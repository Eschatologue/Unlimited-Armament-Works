package uaw.content.blocks.production;

import arc.graphics.Color;
import arc.math.Interp;
import dev.jojofr.multicrafter.MultiCrafterBlock;
import dev.jojofr.multicrafter.type.DrawRecipe;
import dev.jojofr.multicrafter.type.Recipe;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.RadialEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.draw.*;
import uaw.audiovisual.UAWPal;
import uaw.audiovisual.fx.ProductionFx;
import uaw.content.UAWItems;
import uaw.content.UAWLiquids;
import uaw.utils.BlockDef;
import uaw.world.draw.UAWDrawRegion;

import static mindustry.type.ItemStack.with;
import static uaw.Vars.px;
import static uaw.Vars.tick;
import static uaw.utils.Calc.recipe;
import static uaw.utils.RecipeDef.io;
import static uaw.utils.StackDef.its;
import static uaw.utils.StackDef.liq;

public class MixerReactor {

    public static Block b;
    public static Recipe sulphur, sulphuricAcid, phlog;

    public static String baseBot = "-bot", baseMid = "-mid";

    public static void load() {
        loadRecipes();
        b = new MultiCrafterBlock("mixer-reactor") {{
            requirements(Category.crafting, with(Items.graphite, 300, UAWItems.tisic, 50));
            BlockDef.general(this, 3);
            recipes.addAll(sulphur, sulphuricAcid, phlog);
            ambientSound = Sounds.loopMachineSpin;
            drawer = new DrawRecipe();
        }};
    }

    public static void loadRecipes() {
        var rcp = new Object() {
            int n = 0;

            String next() {
                return "-rcp-" + (++n);
            }
        };
        // RCP 1: HDS
        sulphur = new Recipe(recipe + UAWItems.sulphur.name,
                io(liq(UAWLiquids.refOil, 15, Liquids.water, 15)),
                io(its(UAWItems.sulphur, 3)),
                2 * tick
        ) {{
            updateEffect = ProductionFx.sulphurChimneySmoke();
            updateEffectSpread = 0.15f;
            updateEffectChance = 16;
            String rcpId = rcp.next();
            drawer = new DrawMulti(
                    new DrawRegion(baseBot),
                    new DrawRegion(baseMid),
                    new DrawRegion(rcpId + "-bot"),
                    new DrawLiquidTile(UAWLiquids.refOil, 18 * px),
                    new DrawRegion(rcpId + "-top"),
                    new DrawParticles() {{
                        color = UAWPal.refinerySmokeSulph;
                        alpha = 0.6f;
                        particleSize = 4f;
                        particles = 10;
                        particleRad = 12f;
                        particleLife = 120;
                    }}
            );
        }}.isAlwaysUnlocked();

        // RCP 2: Sulphuric Acid
        sulphuricAcid = new Recipe(recipe + UAWLiquids.sulphuricAcid.name,
                // Input
                io(its(UAWItems.sulphur, 1), liq(Liquids.water, 30)),
                // Output
                io(liq(UAWLiquids.sulphuricAcid, 30)),
                2 * tick
        ) {{
            String rcpId = rcp.next();
            drawer = new DrawMulti(
                    new DrawRegion(baseBot),
                    new DrawRegion(baseMid),
                    new DrawRegion(rcpId + "-bot"),
                    new DrawLiquidTile(UAWLiquids.sulphuricAcid, 20 * px) {{
                        alpha = 2;
                    }},
                    new DrawGlowRegion() {{
                        suffix = rcpId + "-glow";
                        color = Pal.slagOrange;
                    }},
                    new UAWDrawRegion(rcpId + "-rot", 12 * px, true) {{
                        symmetryPeriod = 180f;
                    }},
                    new DrawParticles() {{
                        color = UAWPal.sulphurMid;
                        alpha = 0.5f;
                        particleSize = 2f;
                        particles = 10;
                        particleRad = 6;
                        particleLife = 180;
                    }},
                    new DrawRegion(rcpId + "-top")
            );
        }}.isAlwaysUnlocked();

        // RCP 3: Phlogiston
        phlog = new Recipe(recipe + "phlog",
                io(its(UAWItems.anthracite, 5), liq(UAWLiquids.refOil, 30, UAWLiquids.sulphuricAcid, 15)),
                io(liq(UAWLiquids.phlogiston, 15)),
                2 * tick
        ) {{
            String rcpId = rcp.next();
            craftEffect =
                    new RadialEffect(
                            new MultiEffect(
                                    ProductionFx.crucibleSmoke(145, UAWPal.phlogistonFront),
                                    ProductionFx.crucibleSmoke(130, UAWPal.phlogistonMid),
                                    ProductionFx.crucibleSmoke(115, Pal.lightishGray)
                            ), 4, 90, 6) {{
                        rotationOffset = 45;
                    }};
            drawer = new DrawMulti(
                    new DrawRegion(baseBot),
                    new DrawLiquidTile(UAWLiquids.sulphuricAcid, 20 * px) {{
                        alpha = 1.2f;
                    }},
                    new DrawRegion(baseMid),

                    new DrawRegion(rcpId + "-middle"),
                    new DrawLiquidTile(UAWLiquids.phlogiston, 32 * px) {{
                        alpha = 0.8f;
                    }},
                    new DrawRegion(rcpId)
            );
        }}.isAlwaysUnlocked();
    }
}
