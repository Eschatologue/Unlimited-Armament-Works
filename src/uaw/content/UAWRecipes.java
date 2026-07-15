package uaw.content;

import arc.graphics.Color;
import dev.jojofr.multicrafter.type.Recipe;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.effect.MultiEffect;
import uaw.audiovisual.UAWPal;
import uaw.audiovisual.fx.CombatFx;
import uaw.audiovisual.fx.ProductionFx;

import static uaw.Vars.tick;
import static uaw.utils.Calc.recipe;
import static uaw.utils.RecipeDef.io;
import static uaw.utils.StackDef.its;
import static uaw.utils.StackDef.liq;

/**
 * Central registry of all UAW {@link Recipe} definitions
 */
public class UAWRecipes {
    public static Recipe
            // Crafting
            anthraciteSynth, graphiteSynth, hvyAnthraciteSynth,
            oilProcessing_basic, oilProcessing_adv, coalLiquefaction, sporePress,
            sulphuricAcid, sulphur, sulphuricPyrite, sulphuricBlast, plastanium, ketonite;

    public static void load() {
        // region Carbon Processing
        anthraciteSynth = new Recipe(recipe + "anthracite-synth",
                io(its(Items.coal, 2)),
                io(its(UAWItems.anthracite, 1, UAWItems.sulphur, 1)),
                2 * tick
        ) {{
            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke, Fx.formsmoke);
            updateEffect = Fx.fireHit;
        }}.isUnlocked();

        graphiteSynth = new Recipe(recipe + "graphite-synth",
                io(its(UAWItems.anthracite, 1)),
                io(its(Items.graphite, 3)),
                2 * tick
        ) {{
            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke);
            updateEffect = Fx.fireHit;
        }}.isUnlocked();

        hvyAnthraciteSynth = new Recipe(recipe + "heavy-anthracite-synth",
                io(its(UAWItems.anthracite, 5), liq(UAWLiquids.sulphuricAcid, 15)),
                io(its(UAWItems.hvyAnthracite, 1)),
                5 * tick
        ) {{
            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke, Fx.formsmoke);
            updateEffect = Fx.fireHit;
        }};
        // endregion

        // region Oil Processing
        oilProcessing_basic = new Recipe(recipe + "basic-oil",
                io(liq(Liquids.oil, 30)),
                io(liq(UAWLiquids.refOil, 15)),
                2 * tick
        ).isUnlocked();

        oilProcessing_adv = new Recipe(recipe + "adv-oil",
                io(liq(Liquids.oil, 30, Liquids.water, 15)),
                io(liq(UAWLiquids.refOil, 30)),
                5 * tick
        ).isUnlocked();;

        coalLiquefaction = new Recipe(recipe + "coal-liquefaction",
                io(its(Items.coal, 6)),
                io(liq(UAWLiquids.refOil, 5)),
                2 * tick
        ).isUnlocked();

        sporePress = new Recipe(recipe + "spore-press",
                io(its(Items.sporePod, 1)),
                io(liq(UAWLiquids.refOil, 10)),
                2 * tick
        ).isUnlocked();
        // endregion

        // region Chemistry
        sulphur = new Recipe(recipe + "sulphur",
                io(liq(UAWLiquids.refOil, 15, Liquids.water, 30)),
                io(its(UAWItems.sulphur, 3)),
                2 * tick
        ) {{
            updateEffect = CombatFx.hitSparks(16, UAWLiquids.sulphuricAcid.color, 360); // TODO
        }}.isUnlocked();

        sulphuricAcid = new Recipe(recipe + "sulphuric-acid",
                io(its(UAWItems.sulphur, 1), liq(Liquids.water, 30)),
                io(liq(UAWLiquids.sulphuricAcid, 30)),
                2 * tick
        ) {{
            updateEffect = CombatFx.hitSparks(16, UAWLiquids.sulphuricAcid.color, 360); // TODO
        }}.isUnlocked();

        sulphuricPyrite = new Recipe(recipe + "sulphuric-pyratite",
                io(its(Items.coal, 2, UAWItems.sulphur, 1)),
                io(its(Items.pyratite, 1)),
                2 * tick
        );

        sulphuricBlast = new Recipe(recipe + "sulphuric-blast-compound",
                io(its(Items.pyratite, 1, UAWItems.sulphur, 2)),
                io(its(Items.blastCompound, 1)),
                2 * tick
        ).isUnlocked();

        plastanium = new Recipe(recipe + "plastanium",
                io(its(Items.titanium, 2), liq(UAWLiquids.refOil, 5)),
                io(its(Items.plastanium, 3)),
                2 * tick
        );

        ketonite = new Recipe(recipe + "ketonite",
                io(its(UAWItems.anthracite, 5), liq(UAWLiquids.refOil, 25, Liquids.slag, 10)),
                io(its(UAWItems.ketonite, 1)),
                5 * tick
        );
        // endregion
    }
}