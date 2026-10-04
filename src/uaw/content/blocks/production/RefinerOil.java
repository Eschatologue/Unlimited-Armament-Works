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
import uaw.type.UAWRecipe;
import uaw.utils.BlockDef;

import static mindustry.type.ItemStack.with;
import static uaw.Vars.px;
import static uaw.Vars.tick;
import static uaw.utils.Calc.liqPerSec;
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
        oilProcessingBasic = new UAWRecipe(recipe + "basic-oil",
                in -> in.withLiquids(Liquids.oil, liqPerSec(60)),
                out -> out.withLiquids(UAWLiquids.refOil, liqPerSec(30))
        ) {{
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-middle"),
                    new DrawRegion("-refinery")
            );
            updateEffect = ProductionFx.chimneySmoke(UAWPal.refinerySmokeSulph);
            updateEffectSpread = 0.4f;
            updateEffectChance = 16;
        }}.isAlwaysUnlocked();

        oilProcessingAdvanced = new UAWRecipe(recipe + "adv-oil",
                in -> in.withLiquids(Liquids.oil, liqPerSec(60), UAWLiquids.steam, liqPerSec(60)),
                out -> out.withLiquids(UAWLiquids.refOil, liqPerSec(60))
        ) {{
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-middle"),
                    new DrawRegion("-refinery")
            );
            updateEffect = ProductionFx.chimneySmoke(UAWPal.refinerySmokeSulph);
            updateEffectSpread = 0.2f;
            updateEffectChance = 16;
        }};

        coalLiquefaction = new UAWRecipe(recipe + "coal-liquefaction",
                in -> in.withItems(Items.coal, 15).withLiquids(UAWLiquids.steam, liqPerSec(60)),
                out -> out.withLiquids(UAWLiquids.refOil, liqPerSec(30))
        ) {{
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(UAWLiquids.refOil, 12 * px) {{
                        alpha = 4;
                    }},
                    new DrawRegion("-middle"),
                    new DrawRegion("-coalliq-rot", 8, true),
                    new DrawRegion("-coalliq")
            );
        }};

//        plastanium = new Recipe(recipe + "plastanium",
//                io(its(Items.titanium, 2), liq(UAWLiquids.refOil, 5)),
//                io(its(Items.plastanium, 2)),
//                2 * tick
//        );
//
//        ketonite = new Recipe(recipe + "ketonite",
//                io(its(UAWItems.anthracite, 5, Items.titanium, 5), liq(UAWLiquids.refOil, 30)),
//                io(its(UAWItems.ketonite, 1), liq(Liquids.slag, 5)),
//                5 * tick
//        );

    }
}