package uaw.content;

import mindustry.content.Liquids;
import mindustry.type.Liquid;
import uaw.audiovisual.UAWPal;

public class UAWLiquids {

    public static Liquid phlogiston;

    public static void load() {
        phlogiston = new Liquid("phlogiston", UAWPal.phlogistonMid) {{
            flammability = Liquids.oil.flammability * 2f;
            explosiveness = Liquids.oil.explosiveness * 2f;
            heatCapacity = Liquids.oil.heatCapacity * 0.5f;
            temperature = 2;
            boilPoint = -1;
            gasColor = UAWPal.phlogistonFront.cpy().a(0.4f);

            gas = true;
            gasColor = color;
            barColor = UAWPal.phlogistonBack;

            coolant = false;
        }};
    }
}
