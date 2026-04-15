package uaw.content;

import arc.struct.Seq;
import mindustry.graphics.Pal;
import uaw.audiovisual.UAWPal;
import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.type.Item;

public class UAWItems {
    public static Item
            sulphur, anthracite, cryogel, stoutsteel;

    public static final Seq<Item> serpuloItems = new Seq<>(), erekirItems = new Seq<>(), erekirOnlyItems = new Seq<>();

    public static void load() {
        sulphur = new Item("sulphur", Pal.bulletYellow) {{
            flammability = Items.coal.flammability * 1.25f;
            explosiveness = Items.coal.explosiveness / 4;
            buildable = false;
        }};
        anthracite = new Item("anthracite", Color.valueOf("272727")) {{
            flammability = Items.coal.flammability * 2;
            explosiveness = Items.coal.explosiveness / 2;
            hardness = Items.coal.hardness * 2;
            buildable = false;
        }};
        cryogel = new Item("cryogel", Pal.bulletYellow) {{
            flammability = -10f;
            explosiveness = 0f;
            buildable = false;
        }};

        stoutsteel = new Item("stoutsteel", UAWPal.stoutSteelMiddle) {{
            cost = 1;
            hardness = Items.tungsten.hardness;
        }};

        serpuloItems.addAll(
               sulphur, anthracite, cryogel, stoutsteel
        );

    }
}