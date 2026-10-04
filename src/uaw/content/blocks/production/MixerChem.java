package uaw.content.blocks.production;

import dev.jojofr.multicrafter.MultiCrafterBlock;
import dev.jojofr.multicrafter.type.DrawRecipe;
import dev.jojofr.multicrafter.type.Recipe;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawPistons;
import mindustry.world.draw.DrawRegion;
import uaw.content.UAWItems;
import uaw.content.UAWLiquids;
import uaw.type.UAWRecipe;
import uaw.utils.BlockDef;

import static mindustry.type.ItemStack.with;
import static uaw.Vars.px;
import static uaw.Vars.tick;
import static uaw.utils.Calc.liqPerSec;
import static uaw.utils.Calc.recipe;
import static uaw.utils.RecipeDef.io;

public class MixerChem {
    public static Block b;
    public static Recipe sulphuricPyrite, sulphuricBlast, cryoSolid;

    public static String baseBot = "-bot", baseMid = "-mid", baseTop = "-top";

    public static void load() {
        loadRecipes();
        b = new MultiCrafterBlock("mixer-chem") {{
            requirements(Category.crafting, with(Items.graphite, 150, Items.metaglass, 50));
            BlockDef.general(this, 3);
            addRecipe(sulphuricPyrite, sulphuricBlast, cryoSolid);
            ambientSound = Sounds.loopMachineSpin;
            autoSelectRecipe = true;
            drawer = new DrawRecipe();
        }};
    }

    public static void loadRecipes() {

        sulphuricPyrite = new UAWRecipe(recipe + Items.pyratite.name,
                in -> in.withItems(Items.coal, 2, UAWItems.sulphur, 2),
                out -> out.withItems(Items.pyratite, 2)
        ) {{
            drawer = new DrawMulti(
                    new DrawRegion(baseBot),
                    new DrawPistons() {{
                        suffix = "-piston";
                        sideOffset = 1f;
                        sinMag = 5f * px;
                        sinScl = 6f;
                    }},
                    new DrawRegion(baseTop)
            );
        }};

        sulphuricBlast = new UAWRecipe(recipe + Items.blastCompound.name,
                in -> in.withItems(Items.pyratite, 2, UAWItems.sulphur, 2),
                out -> out.withItems(Items.blastCompound, 1)
        ) {{
            drawer = new DrawMulti(
                    new DrawRegion(baseBot),
                    new DrawPistons() {{
                        suffix = "-piston";
                        sideOffset = 1f;
                        sinMag = 5f * px;
                        sinScl = 6f;
                    }},
                    new DrawRegion(baseTop)
            );
        }};

        cryoSolid = new UAWRecipe(recipe + UAWItems.cryoSolid.name,
                in -> in.withLiquids(Liquids.cryofluid, liqPerSec(15), UAWLiquids.refOil, liqPerSec(10)),
                out -> out.withItems(UAWItems.cryoSolid, 1)
        ) {{
            drawer = new DrawMulti(
                    new DrawRegion(baseBot),
                    new DrawPistons() {{
                        suffix = "-piston";
                        sideOffset = 1f;
                        sinMag = 5f * px;
                        sinScl = 6f;
                    }},
                    new DrawRegion(baseTop)
            );
        }};
    }
}