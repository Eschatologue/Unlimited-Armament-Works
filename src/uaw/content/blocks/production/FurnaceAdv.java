package uaw.content.blocks.production;

import dev.jojofr.multicrafter.MultiCrafterBlock;
import dev.jojofr.multicrafter.type.DrawRecipe;
import dev.jojofr.multicrafter.type.Recipe;
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
import uaw.type.UAWRecipe;
import uaw.utils.BlockDef;

import static mindustry.type.ItemStack.with;
import static uaw.utils.Calc.recipe;

public class FurnaceAdv {
    public static Block b;
    public static Recipe silicon, tisic, thoriumEnr;

    public static void load() {
        loadRecipes();
        b = new MultiCrafterBlock("furnace-adv") {{
            requirements(Category.crafting, with(Items.graphite, 300, Items.silicon, 250));
            BlockDef.general(this, 3);
            recipes.addAll(silicon, tisic, thoriumEnr);
            ambientSound = Sounds.loopSmelter;
            drawer = new DrawRecipe();
        }};
    }

    public static void loadRecipes() {
        silicon = new UAWRecipe(recipe + Items.silicon.name,
                in -> in.withItems(Items.graphite, 2, Items.sand, 6),
                out -> out.withItems(Items.silicon, 6)
        ) {{
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawArcSmelt() {{
                        particles = 30;
                    }},
                    new DrawRegion("-middle"),
                    new DrawGlowRegion() {{
                        suffix = "-glow";
                        color = Pal.slagOrange.lerp(Items.silicon.color, 0.5f);
                    }},
                    new DrawRegion("-top")
            );
        }};
        tisic = new UAWRecipe(recipe + UAWItems.tisic.name,
                in -> in.withItems(Items.titanium, 3, Items.silicon, 1, Items.graphite, 2),
                out -> out.withItems(UAWItems.tisic, 1)
        ) {{
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawArcSmelt() {{
                        particles = 30;
                    }},
                    new DrawRegion("-middle"),
                    new DrawGlowRegion() {{
                        suffix = "-glow";
                        color = Pal.slagOrange;
                    }},
                    new DrawRegion("-top")
            );
        }};
        thoriumEnr = new UAWRecipe(recipe + UAWItems.thoriumEnr.name,
                in -> in.withItems(Items.thorium, 2, Items.graphite, 2),
                out -> out.withItems(UAWItems.thoriumEnr, 2)
        ) {{
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawArcSmelt() {{
                        particles = 30;
                    }},
                    new DrawRegion("-middle"),
                    new DrawGlowRegion() {{
                        suffix = "-glow";
                        color = Pal.slagOrange;
                    }},
                    new DrawRegion("-top")
            );
        }};
//        stoutsteel = new Recipe(recipe + UAWItems.stoutsteel.name,
//                io(its(UAWItems.tisic, 2, Items.phaseFabric, 1), liq(UAWLiquids.phlogiston, 12)),
//                io(its(UAWItems.stoutsteel, 1)),
//                2 * tick
//        ) {{
//            String rcpId = rcp.next();
//        }};

    }
}
