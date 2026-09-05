package uaw.content.blocks.production;

import dev.jojofr.multicrafter.MultiCrafterBlock;
import dev.jojofr.multicrafter.type.DrawRecipe;
import dev.jojofr.multicrafter.type.Recipe;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.draw.DrawArcSmelt;
import mindustry.world.draw.DrawGlowRegion;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;
import uaw.content.UAWItems;
import uaw.content.UAWLiquids;
import uaw.utils.BlockDef;

import static mindustry.type.ItemStack.with;
import static uaw.Vars.tick;
import static uaw.utils.Calc.recipe;
import static uaw.utils.RecipeDef.io;
import static uaw.utils.StackDef.its;
import static uaw.utils.StackDef.liq;

public class FurnaceAdv {
    public static Block fa;
    public static Recipe silicon, tisic, stoutsteel;

    public static void load() {
        loadRecipes();
        fa = new MultiCrafterBlock("furnace-adv") {{
            requirements(Category.crafting, with(Items.graphite, 300, Items.titanium, 250));
            BlockDef.general(this, 3);
            recipes.addAll(tisic, stoutsteel);
            ambientSound = Sounds.loopSmelter;
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
        tisic = new Recipe(recipe + UAWItems.tisic.name,
                io(its(Items.titanium, 3, Items.silicon, 1, Items.graphite, 2)),
                io(its(UAWItems.tisic, 1)),

                2 * tick
        ) {{
            String rcpId = rcp.next();
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawArcSmelt() {{
                        particles = 30;
                    }},
                    new DrawRegion("-middle"),
                    new DrawGlowRegion() {{
                        suffix = rcpId + "-glow";
                        color = Pal.slagOrange;
                    }},
                    new DrawRegion(rcpId + "-top")
            );
        }};
        stoutsteel = new Recipe(recipe + UAWItems.stoutsteel.name,
                io(its(UAWItems.tisic, 2, Items.phaseFabric, 1), liq(UAWLiquids.phlogiston, 12)),
                io(its(UAWItems.stoutsteel, 1)),
                2 * tick
        ) {{
            String rcpId = rcp.next();
        }};
        silicon = new Recipe(recipe + "silicon-alt",
                io(its(Items.graphite, 2, Items.sand, 6)),
                io(its(Items.silicon, 6)),
                2 * tick
        ) {{
            String rcpId = rcp.next();
        }};
    }
}
