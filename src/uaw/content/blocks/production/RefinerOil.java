package uaw.content.blocks.production;

import dev.jojofr.multicrafter.MultiCrafterBlock;
import dev.jojofr.multicrafter.type.DrawRecipe;
import dev.jojofr.multicrafter.type.Recipe;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.draw.DrawLiquidTile;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;
import uaw.audiovisual.UAWPal;
import uaw.audiovisual.fx.ProductionFx;
import uaw.content.UAWItems;
import uaw.content.UAWLiquids;
import uaw.utils.BlockDef;

import static mindustry.type.ItemStack.with;
import static uaw.Vars.px;
import static uaw.Vars.tick;
import static uaw.utils.Calc.recipe;
import static uaw.utils.RecipeDef.io;
import static uaw.utils.StackDef.its;
import static uaw.utils.StackDef.liq;

public class RefinerOil {
    public static Block block;
    // Ref Oil
    public static Recipe oilProcessingBasic, oilProcessingAdvanced, coalLiquefaction;
    // Plastics
    public static Recipe plastanium, ketonite;

    public static void load() {
        loadRecipes();
        block = new MultiCrafterBlock("refiner-oil") {{
            requirements(Category.crafting, with(Items.graphite, 150));
            BlockDef.general(this, 3);
            recipes.addAll(oilProcessingBasic, oilProcessingAdvanced, coalLiquefaction);
            drawer = new DrawRecipe();
        }};
    }

    public static void loadRecipes() {
        oilProcessingBasic = new Recipe(recipe + "basic-oil",
                io(liq(Liquids.oil, 30)),
                io(liq(UAWLiquids.refOil, 15)),
                2 * tick
        ) {{
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-middle"),
                    new DrawRegion("-rcp-1")
            );
            updateEffect = ProductionFx.chimneySmoke(UAWPal.refinerySmokeSulph);
            updateEffectSpread = 0.4f;
            updateEffectChance = 16;
        }}.isAlwaysUnlocked();

        oilProcessingAdvanced = new Recipe(recipe + "adv-oil",
                io(liq(Liquids.oil, 30, Liquids.water, 15)),
                io(liq(UAWLiquids.refOil, 30)),
                2 * tick
        ) {{
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(UAWLiquids.refOil, 12 * px) {{
                        alpha = 4;
                    }},
                    new DrawRegion("-middle"),
                    new DrawRegion("-rcp-2")
            );
            updateEffect = ProductionFx.chimneySmoke(UAWPal.refinerySmokeSulph);
            updateEffectSpread = 0.2f;
            updateEffectChance = 16;
        }};

        coalLiquefaction = new Recipe(recipe + "coal-liquefaction",
                io(its(Items.coal, 6), liq(Liquids.water, 12)),
                io(liq(UAWLiquids.refOil, 6)),
                2 * tick
        ) {{
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(UAWLiquids.refOil, 12 * px) {{
                        alpha = 4;
                    }},
                    new DrawRegion("-middle"),
                    new DrawRegion("-rcp-3-rot", 8, true),
                    new DrawRegion("-rcp-3")
            );
        }};

//        sporePress = new Recipe(recipe + "spore-liquefaction",
//                io(its(Items.sporePod, 1, Items.sand, 2)),
//                io(liq(UAWLiquids.refOil, 9)),
//                2 * tick
//        ) {{
//            drawer = new DrawMulti(
//                    new DrawRegion("-bottom"),
//                    new DrawLiquidTile(UAWLiquids.refOil, 12 * px) {{
//                        alpha = 4;
//                    }},
//                    new DrawRegion("-middle"),
//                    new DrawRegion("-rcp-4")
//            );
//        }};

        plastanium = new Recipe(recipe + "plastanium",
                io(its(Items.titanium, 2), liq(UAWLiquids.refOil, 5)),
                io(its(Items.plastanium, 2)),
                2 * tick
        );

        ketonite = new Recipe(recipe + "ketonite",
                io(its(UAWItems.anthracite, 5, Items.titanium, 5), liq(UAWLiquids.refOil, 30)),
                io(its(UAWItems.ketonite, 1), liq(Liquids.slag, 5)),
                5 * tick
        );

    }
}