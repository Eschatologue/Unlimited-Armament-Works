package uaw.content;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.type.Liquid;
import uaw.audiovisual.UAWPal;

import static mindustry.content.Liquids.oil;
import static mindustry.content.Liquids.water;

public class UAWLiquids {

    public static Liquid steam, sulphuricAcid, refOil, phlogiston;

    public static void load() {
        steam = new Liquid("steam", UAWPal.steam) {{
            temperature = 0.75f;

            gas = true;
            gasColor = color;
            barColor = color;

            coolant = false;
        }};

        sulphuricAcid = new Liquid("sulphuric-acid", UAWPal.acidMid) {{
            viscosity = water.viscosity * 1.5f;
            coolant = false;
        }};

        refOil = new Liquid("ref-oil", UAWPal.refOilMid) {{
            viscosity = oil.viscosity;
            flammability = oil.flammability * 2;
            heatCapacity = oil.heatCapacity;
            barColor = color;
            effect = StatusEffects.tarred;
            boilPoint = 0.65f;
            gasColor = Color.grays(0.4f);
            canStayOn.add(water);
            canStayOn.add(oil);
            coolant = false;
        }};

        phlogiston = new Liquid("phlogiston", UAWPal.phlogistonFront) {{
            flammability = refOil.flammability * 2;
            temperature = 2;
            explosiveness = 3;
            boilPoint = -1;

            gas = true;
            gasColor = UAWPal.phlogistonFront.cpy().a(0.5f);
            barColor = UAWPal.phlogistonMid;

            coolant = false;
        }};
    }
}
