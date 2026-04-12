package uaw.content;

import uaw.audiovisual.UAWPal;
import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.type.Item;

public class UAWItems {
    public static Item
            cryogel,
            stoutsteel,
            anthracite;

    public static void load() {
        cryogel = new Item("item-cryogel", UAWPal.cryoMiddle) {{
            flammability = -10f;
            explosiveness = 0f;
        }};
        stoutsteel = new Item("item-stoutsteel", UAWPal.stoutSteelMiddle) {{
            cost = 1;
            hardness = Items.tungsten.hardness;
        }};
        anthracite = new Item("item-anthracite", Color.valueOf("272727")) {{
            explosiveness = Items.coal.explosiveness / 2;
            flammability = Items.coal.flammability * 2;
            hardness = Items.coal.hardness * 2;
            buildable = Items.coal.buildable;
        }};
    }
}