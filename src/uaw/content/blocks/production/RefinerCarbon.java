package uaw.content.blocks.production;

import dev.jojofr.multicrafter.MultiCrafterBlock;
import dev.jojofr.multicrafter.type.DrawRecipe;
import dev.jojofr.multicrafter.type.Recipe;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.draw.DrawGlowRegion;
import mindustry.world.draw.DrawLiquidTile;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;
import uaw.audiovisual.UAWPal;
import uaw.audiovisual.fx.CombatFx;
import uaw.content.UAWItems;
import uaw.content.UAWLiquids;
import uaw.type.UAWRecipe;
import uaw.utils.BlockDef;
import uaw.world.draw.UAWDrawFlame;

import static mindustry.type.ItemStack.with;
import static uaw.Vars.px;
import static uaw.Vars.tick;
import static uaw.utils.Calc.liqPerSec;
import static uaw.utils.Calc.recipe;

public class RefinerCarbon {

    public static Block b;
    public static Recipe anthracite, graphite, graphiteMk2, graphFrOil;

    public static void load() {
        loadRecipes();
        b = new MultiCrafterBlock("refiner-carbon") {{
            requirements(Category.crafting, with(Items.graphite, 50));
            BlockDef.general(this, 2);
            recipes.addAll(anthracite, graphite, graphiteMk2, graphFrOil);
            drawer = new DrawRecipe();
        }};
    }

    public static void loadRecipes() {
        anthracite = new UAWRecipe(recipe + UAWItems.anthracite.name,
                in -> in.withItems(Items.coal, 2),
                out -> out.withItems(UAWItems.anthracite, 1, UAWItems.sulphur, 1)
        ) {{
            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke, Fx.formsmoke);
            updateEffect = Fx.fireHit;
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-base"),
                    new DrawGlowRegion() {{
                        color = Pal.slagOrange;
                    }},
                    new DrawRegion("-solid"),
                    new UAWDrawFlame() {{
                        topSuffix = "-solid-top";
                    }}
            );
        }}.isAlwaysUnlocked();

        graphite = new UAWRecipe(recipe + Items.graphite.name,
                in -> in.withItems(UAWItems.anthracite, 1),
                out -> out.withItems(Items.graphite, 3)
        ) {{
            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke, Fx.formsmoke);
            updateEffect = Fx.fireHit;
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-base"),
                    new DrawGlowRegion() {{
                        color = Pal.slagOrange;
                    }},
                    new DrawRegion("-solid"),
                    new UAWDrawFlame() {{
                        topSuffix = "-solid-top";
                    }}
            );
        }}.isAlwaysUnlocked();

        graphiteMk2 = new UAWRecipe(recipe + Items.graphite.name + UAWLiquids.steam.name,
                in -> in.withItems(UAWItems.anthracite, 1).withLiquids(UAWLiquids.steam, liqPerSec(15)),
                out -> out.withItems(Items.graphite, 6)
        ) {{
            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, CombatFx.hitBullet(UAWPal.waterMid));
            updateEffect = CombatFx.hitBullet(UAWPal.steam);
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(UAWLiquids.steam, 16 * px),
                    new DrawRegion("-base"),
                    new DrawGlowRegion() {{
                        color = Pal.slagOrange;
                    }},
                    new DrawRegion("-fluid")
            );
        }};

        graphFrOil = new UAWRecipe(recipe + Items.graphite.name + Liquids.oil.name,
                in -> in.withLiquids(UAWLiquids.refOil, liqPerSec(12)),
                out -> out.withItems(Items.graphite, 4)
        ) {{
            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke);
            updateEffect = new MultiEffect(Fx.fireHit, Fx.steam);
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(UAWLiquids.refOil, 16 * px),
                    new DrawRegion("-base"),
                    new DrawGlowRegion() {{
                        color = Pal.slagOrange;
                    }},
                    new DrawRegion("-fluid")
            );
        }};

    }
}