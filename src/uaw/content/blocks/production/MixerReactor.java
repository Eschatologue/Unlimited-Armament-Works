package uaw.content.blocks.production;

import arc.graphics.Color;
import dev.jojofr.multicrafter.MultiCrafterBlock;
import dev.jojofr.multicrafter.type.DrawRecipe;
import dev.jojofr.multicrafter.type.Recipe;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.draw.*;
import uaw.audiovisual.UAWPal;
import uaw.audiovisual.fx.ProductionFx;
import uaw.content.UAWItems;
import uaw.content.UAWLiquids;
import uaw.type.UAWRecipe;
import uaw.utils.BlockDef;
import uaw.world.draw.UAWDrawFrames;

import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;
import static uaw.Vars.px;
import static uaw.Vars.tick;
import static uaw.utils.Calc.liqPerSec;
import static uaw.utils.Calc.recipe;
import static uaw.utils.RecipeDef.io;

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
            autoSelectRecipe = true;
            drawer = new DrawRecipe();
        }};
    }

    public static void loadRecipes() {
        // RCP 1: HDS
        sulphur = new UAWRecipe(recipe + UAWItems.sulphur.name,
                in -> in.withLiquids(UAWLiquids.refOil, liqPerSec(15), UAWLiquids.steam, liqPerSec(15)),
                out -> out.withItems(UAWItems.sulphur, 3)
        ) {{
            updateEffect = ProductionFx.chimneySmoke(Color.gray);
            updateEffectChance = 10 / 100f;
            craftEffect = new MultiEffect(
                    ProductionFx.sulphurChimneySmoke(),
                    ProductionFx.chimneySmoke(90, 8 * tilesize, Color.gray)
            );
            drawer = new DrawMulti(
                    new DrawRegion(baseBot),
                    new DrawLiquidTile(UAWLiquids.refOil, 20 * px),
                    new DrawParticles() {{
                        color = UAWPal.sulphurMid;
                        alpha = 0.5f;
                        particleSize = 2f;
                        particles = 10;
                        particleRad = 6;
                        particleLife = 180;
                    }},
                    new DrawRegion(baseMid),
                    new UAWDrawFrames() {{
                        suffix = "-part";
                        frames = 9;
                        interval = 0.125f * tick;
                        sine = false;
                    }},
                    new DrawGlowRegion() {{
                        suffix = "-glow";
                        color = Pal.slagOrange;
                    }}
            );
        }}.isAlwaysUnlocked();

        // RCP 2: Sulphuric Acid
        sulphuricAcid = new UAWRecipe(recipe + UAWLiquids.sulphuricAcid.name,
                in -> in
                        .withItems(UAWItems.sulphur, 1)
                        .withLiquids(Liquids.water, liqPerSec(30)),
                out -> out.withLiquids(UAWLiquids.sulphuricAcid, liqPerSec(30)),
                2 * tick
        ) {{
            updateEffect = ProductionFx.chimneySmoke(UAWPal.refinerySmokeSulph);
            updateEffectChance = 5 / 100f;
            drawer = new DrawMulti(
                    new DrawRegion(baseBot),
                    new DrawLiquidTile(UAWLiquids.sulphuricAcid, 20 * px),
                    new DrawParticles() {{
                        color = UAWPal.sulphurMid;
                        alpha = 0.5f;
                        particleSize = 2f;
                        particles = 10;
                        particleRad = 6;
                        particleLife = 180;
                    }},
                    new DrawRegion(baseMid),
                    new UAWDrawFrames() {{
                        suffix = "-part";
                        frames = 9;
                        interval = 0.125f * tick;
                        sine = false;
                    }},
                    new DrawGlowRegion() {{
                        suffix = "-glow";
                        color = Pal.slagOrange;
                    }}
            );
        }}.isAlwaysUnlocked();

        // RCP 3: Phlogiston
        phlog = new UAWRecipe(recipe + "phlog",
                in -> in
                        .withItems(UAWItems.anthracite, 5)
                        .withLiquids(UAWLiquids.refOil, liqPerSec(30), UAWLiquids.sulphuricAcid, liqPerSec(15)),
                out -> out.withLiquids(UAWLiquids.phlogiston, liqPerSec(15)),
                2 * tick
        ) {{
            updateEffect = ProductionFx.chimneySmoke(UAWPal.refinerySmokephlog);
            updateEffectChance = 7.5f / 100f;
            drawer = new DrawMulti(
                    new DrawRegion(baseBot),
                    new DrawLiquidTile(UAWLiquids.phlogiston, 20 * px),
                    new DrawParticles() {{
                        color = UAWPal.phlogistonFront;
                        alpha = 0.6f;
                        particleSize = 4f;
                        particles = 10;
                        particleRad = 10;
                        particleLife = 120;
                    }},
                    new DrawRegion(baseMid),
                    new UAWDrawFrames() {{
                        suffix = "-part";
                        frames = 9;
                        interval = 0.125f * tick;
                        sine = false;
                    }},
                    new DrawGlowRegion() {{
                        suffix = "-glow";
                        color = Pal.slagOrange;
                        alpha = 0.6f;
                    }}
            );
        }}.isAlwaysUnlocked();
    }
}
