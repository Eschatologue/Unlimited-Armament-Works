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
            sporePress,
            sulphuricAcid, sulphur, sulphuricPyrite, sulphuricBlast, plastanium, ketonite;

    public static void load() {

    }
}