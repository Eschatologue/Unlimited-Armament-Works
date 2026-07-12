package uaw.content;

import arc.graphics.Color;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.type.Liquid;
import uaw.audiovisual.UAWPal;

import static mindustry.content.Liquids.oil;
import static mindustry.content.Liquids.water;

public class UAWLiquids {

    public static Liquid sulphuricAcid, refOil, phlogiston;

    public static void load() {
        sulphuricAcid = new Liquid("sulphuric-acid", UAWPal.sulphurFront) {{
            temperature = 0.5f;
            coolant = false;
        }};

        refOil = new Liquid("ref-oil", UAWPal.refOilMid) {{
            viscosity = water.viscosity;
            flammability = oil.flammability * 1.5f;
            explosiveness = oil.explosiveness * 1.5f;
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
            flammability = UAWItems.anthracite.flammability * 2f;
            explosiveness = 3;
//            temperature = 2; // Will damage normal conduits
            boilPoint = -1;
            gasColor = UAWPal.phlogistonFront.cpy().a(0.5f);

            gas = true;
            gasColor = color;
            barColor = UAWPal.phlogistonMid;

            coolant = false;
        }};
    }
}
