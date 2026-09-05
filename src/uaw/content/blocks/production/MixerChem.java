package uaw.content.blocks.production;

import dev.jojofr.multicrafter.MultiCrafterBlock;
import dev.jojofr.multicrafter.type.DrawRecipe;
import dev.jojofr.multicrafter.type.Recipe;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.draw.DrawLiquidTile;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawPistons;
import mindustry.world.draw.DrawRegion;
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

public class MixerChem {
    public static Block b;
    public static Recipe sulphuricPyrite, sulphuricBlast, cryoSolid;

    public static void load() {
        loadRecipes();
        b = new MultiCrafterBlock("mixer-chem") {{
            requirements(Category.crafting, with(Items.graphite, 150, Items.metaglass, 50));
            BlockDef.general(this, 3);
            recipes.addAll(sulphuricPyrite, sulphuricBlast, cryoSolid);
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

        sulphuricPyrite = new Recipe(recipe + Items.pyratite.name,
                io(its(Items.coal, 2, UAWItems.sulphur, 2)),
                io(its(Items.pyratite, 2)),
                2 * tick
        ) {{
            String rcpId = rcp.next();
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-middle"),
                    new DrawPistons() {{
                        suffix = rcpId + "-piston";
                        sinMag = 5f * px;
                        sinScl = 6f;
                    }},
                    new DrawRegion(rcpId)
            );
        }};

        sulphuricBlast = new Recipe(recipe + Items.blastCompound.name,
                io(its(Items.pyratite, 1, UAWItems.sulphur, 2)),
                io(its(Items.blastCompound, 1)),
                2 * tick
        ) {{
            String rcpId = rcp.next();
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-middle"),
                    new DrawPistons() {{
                        suffix = rcpId + "-piston";
                        sinMag = 5f * px;
                        sinScl = 6f;
                    }},
                    new DrawRegion(rcpId)
            );
        }};

        cryoSolid = new Recipe(recipe + UAWItems.cryoSolid.name,
                io(
                        its(Items.sand, 1),
                        liq(Liquids.cryofluid, 15, UAWLiquids.refOil, 10)),
                io(its(UAWItems.cryoSolid, 1)
                ),
                2 * tick
        ) {{
            String rcpId = rcp.next();
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-middle"),
                    new DrawPistons() {{
                        suffix = rcpId + "-piston";
                        sinMag = 5f * px;
                        sinScl = 6f;
                    }},
                    new DrawRegion(rcpId + "-middle"),
                    new DrawLiquidTile(Liquids.cryofluid, 35 * px),
                    new DrawRegion(rcpId)
            );
        }};
    }
}