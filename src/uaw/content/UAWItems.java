package uaw.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import uaw.audiovisual.UAWPal;

public class UAWItems {
    public static final Seq<Item> serpuloItems = new Seq<>(), erekirItems = new Seq<>(), erekirOnlyItems = new Seq<>();
    public static Item
            anthracite, sulphur, hvyAnthracite,
            tisic, stoutsteel,
            ketonite,
            cryogel;

    public static void load() {
        anthracite = new Item("anthracite", Pal.coalBlack) {{
            flammability = Items.coal.flammability * 2;
            explosiveness = Items.coal.explosiveness / 2;
            buildable = false;
        }};
        sulphur = new Item("sulphur", UAWPal.sulphurFront) {{
            flammability = Items.coal.flammability * 0.5f;
            explosiveness = Items.coal.explosiveness / 4;
            buildable = false;
        }};
        hvyAnthracite = new Item("heavy-anthracite", Pal.coalBlack) {{
            flammability = anthracite.flammability * 2.5f;
            explosiveness = anthracite.explosiveness / 2;
            buildable = false;
        }};
        tisic = new Item("tisic", Color.valueOf("768a9a"));
        stoutsteel = new Item("stoutsteel", UAWPal.stoutsteelMid);

        ketonite = new Item("ketonite", UAWPal.ketoniteMid) {{
            flammability = -5f;
        }};

        cryogel = new Item("cryogel", UAWPal.cryoAmmoFront) {{
            flammability = -10f;
            explosiveness = 0f;
        }};

        serpuloItems.addAll(
                sulphur, anthracite, cryogel, tisic, ketonite, stoutsteel
        );
    }
}