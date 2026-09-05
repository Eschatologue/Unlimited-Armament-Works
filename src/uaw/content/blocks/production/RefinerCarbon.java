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
import uaw.content.UAWItems;
import uaw.utils.BlockDef;
import uaw.world.draw.UAWDrawFlame;

import static mindustry.type.ItemStack.with;
import static uaw.Vars.px;
import static uaw.Vars.tick;
import static uaw.utils.Calc.recipe;
import static uaw.utils.RecipeDef.io;
import static uaw.utils.StackDef.its;
import static uaw.utils.StackDef.liq;

public class RefinerCarbon {

    public static Block b;
    public static Recipe anthracite, graphite, graphiteMk2;

    public static void load() {
        loadRecipes();
        b = new MultiCrafterBlock("refiner-carbon") {{
            requirements(Category.crafting, with(Items.graphite, 50));
            BlockDef.general(this, 2);
            recipes.addAll(anthracite, graphite, graphiteMk2);
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
        anthracite = new Recipe(recipe + UAWItems.anthracite.name,
                io(its(Items.coal, 2)),
                io(its(UAWItems.anthracite, 1, UAWItems.sulphur, 1)),
                2 * tick
        ) {{
            String rcpId = rcp.next();
            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke, Fx.formsmoke);
            updateEffect = Fx.fireHit;
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-base"),
                    new DrawGlowRegion() {{
                        color = Pal.slagOrange;
                    }},
                    new DrawRegion(rcpId)
            );
        }}.isAlwaysUnlocked();

        graphite = new Recipe(recipe + Items.graphite.name,
                io(its(UAWItems.anthracite, 1)),
                io(its(Items.graphite, 3)),
                2 * tick
        ) {{
            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke);
            updateEffect = Fx.fireHit;
            String rcpId = rcp.next();
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-base"),
                    new DrawGlowRegion() {{
                        color = Pal.slagOrange;
                    }},
                    new DrawRegion(rcpId),
                    new UAWDrawFlame() {{
                        topSuffix = rcpId + "-top";
                    }}
            );
        }}.isAlwaysUnlocked();

        graphiteMk2 = new Recipe(recipe + Items.graphite.name + "-mk2",
                io(its(UAWItems.anthracite, 1), liq(Liquids.water, 12)),
                io(its(Items.graphite, 6)),
                2 * tick
        ) {{
            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke);
            updateEffect = new MultiEffect(Fx.fireHit, Fx.steam);
            String rcpId = rcp.next();
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(Liquids.water, 16 * px),
                    new DrawRegion("-base"),
                    new DrawGlowRegion() {{
                        color = Pal.slagOrange;
                    }},
                    new DrawRegion(rcpId)
            );
        }};

    }
}