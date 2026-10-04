package uaw.content.blocks.production;

import dev.jojofr.multicrafter.MultiCrafterBlock;
import dev.jojofr.multicrafter.type.Recipe;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.draw.DrawGlowRegion;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;
import uaw.content.UAWItems;
import uaw.content.UAWLiquids;
import uaw.type.UAWRecipe;
import uaw.utils.BlockDef;

import static mindustry.type.ItemStack.with;
import static uaw.Vars.tick;
import static uaw.utils.Calc.liqPerSec;
import static uaw.utils.Calc.recipe;

public class Boiler {

    public static Block b;
    public static Recipe steamCoal, steamAnth;

    public static void load() {
        loadRecipes();
        b = new MultiCrafterBlock("boiler") {{
            requirements(Category.crafting, with(Items.graphite, 50));
            BlockDef.general(this, 2);
            recipes.addAll(steamCoal, steamAnth);
            drawer = new DrawMulti(
                    new DrawRegion(),
                    new DrawGlowRegion() {{
                        color = Pal.slagOrange;
                    }}
            );
        }};
    }

    public static void loadRecipes() {
        float baseRate = liqPerSec(30);
        steamCoal = new UAWRecipe(recipe + UAWLiquids.steam.name + Items.coal.name,
                in -> in.withItems(Items.coal, 2).withLiquids(Liquids.water, baseRate),
                out -> out.withLiquids(UAWLiquids.steam, baseRate)
        ) {{
            craftEffect = updateEffect = Fx.steam;
        }}.isAlwaysUnlocked();

        steamAnth = new UAWRecipe(recipe + UAWLiquids.steam.name + UAWItems.anthracite.name,
                in -> in.withItems(UAWItems.anthracite, 2).withLiquids(Liquids.water, baseRate * 2),
                out -> out.withLiquids(UAWLiquids.steam, baseRate * 2)

        ) {{
            craftEffect = updateEffect = Fx.steam;
        }}.isAlwaysUnlocked();
    }
}