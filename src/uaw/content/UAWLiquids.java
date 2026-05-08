package uaw.content;

import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.Liquid;
import uaw.audiovisual.UAWPal;

public class UAWLiquids {

    public static Liquid sulphuricAcid, phlogiston;

    public static void load() {
        sulphuricAcid = new Liquid("sulphuric-acid", UAWPal.sulphurFront) {{
                temperature = 0.5f;
                coolant = false;
            }
        };
        phlogiston = new Liquid("phlogiston", UAWPal.phlogistonFront) {{
            flammability = UAWItems.anthracite.flammability * 2f;
            explosiveness = UAWItems.anthracite.explosiveness * 2f;
            temperature = 2;
            boilPoint = -1;
            gasColor = UAWPal.phlogistonFront.cpy().a(0.4f);

            gas = true;
            gasColor = color;
            barColor = UAWPal.phlogistonMid;

            coolant = false;
        }};
    }
}
