package uaw.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.type.Item;
import uaw.audiovisual.UAWPal;

public class UAWItems {
    public static final Seq<Item> serpuloItems = new Seq<>(), erekirItems = new Seq<>(), erekirOnlyItems = new Seq<>();
    public static Item
            sulphur, anthracite, cryogel, tisic, stoutsteel;

    public static void load() {
        sulphur = new Item("sulphur", UAWPal.sulphurFront) {{
            flammability = Items.coal.flammability * 0.5f;
            explosiveness = Items.coal.explosiveness / 4;
            buildable = false;
        }};
        anthracite = new Item("anthracite", Color.valueOf("272727")) {{
            flammability = Items.coal.flammability * 2;
            explosiveness = Items.coal.explosiveness / 2;
            buildable = false;
        }};
        tisic = new Item("tisic", Color.valueOf("768a9a")) {{
            healthScaling = 1.5f;
            cost = 1.5f;
        }};
        stoutsteel = new Item("stoutsteel", UAWPal.stoutsteelMiddle) {{
            healthScaling = 2f;
            cost = 2;
        }};
        cryogel = new Item("cryogel", UAWPal.cryoAmmoFront) {{
            flammability = -10f;
            explosiveness = 0f;
        }};


        serpuloItems.addAll(
                sulphur, anthracite, cryogel, tisic, stoutsteel
        );

    }
}