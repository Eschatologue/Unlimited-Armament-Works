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
            // Raw
            anthracite, sulphur,
    // Alloys
    tisic, stoutsteel,
    // Metals
    mythril,
    // Polymers
    ketonite,
    // Compounds
    thoriumEnr, hvyAnthracite, cryoSolid,
    // Munitions - WIP
    ammoBall, ammoAP, ammoAPI, ammoAPC, ammoHE, ammoACG, ammoFRAG, ammoARC,
    // Shells - WIP
    shellGP, shellAP, shellHE, shellHEVT, shellHEI, shellHEC, shellPHL;

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
        thoriumEnr = new Item("thorium-enr", Pal.thoriumAmmoBack) {{
            explosiveness = Items.thorium.explosiveness;
            radioactivity = Items.thorium.radioactivity * 1.5f;
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

        cryoSolid = new Item("cryo-solid", UAWPal.cryoAmmoFront) {{
            flammability = -10f;
            explosiveness = 0f;
        }};
        munitionItem();
        serpuloItems.addAll(
                sulphur, anthracite, cryoSolid, tisic, ketonite, stoutsteel, mythril
        );
    }

    public static void munitionItem() {
        ammoBall = ammoItem("ammo-ball", Pal.copperAmmoFront);
        ammoAP = ammoItem("ammo-ap", UAWPal.titaniumAmmoFront);
        ammoAPI = ammoItem("ammo-ap-i", UAWPal.incendAmmoFront);
        ammoAPC = ammoItem("ammo-ap-c", UAWPal.cryoAmmoFront);
        ammoHE = ammoItem("ammo-he", Items.blastCompound.color);
        ammoACG = ammoItem("ammo-acg", Items.silicon.color);
        ammoFRAG = ammoItem("ammo-frag", Items.metaglass.color);
        ammoARC = ammoItem("ammo-arc", Items.surgeAlloy.color);

        shellGP = ammoItem("shell-gp", Pal.graphiteAmmoBack);
        shellAP = ammoItem("shell-ap", UAWPal.titaniumAmmoFront);
        shellHE = ammoItem("shell-he", Items.blastCompound.color);
        shellHEVT = ammoItem("shell-he-vt", Pal.plastaniumFront);
        shellHEI = ammoItem("shell-he-i", UAWPal.incendAmmoFront);
        shellHEC = ammoItem("shell-he-c", UAWPal.cryoAmmoFront);
        shellPHL = ammoItem("shell-phl", UAWPal.phlogistonMid);
    }

    /**
     * Builds a munition item with the shared placeholder stats
     */
    private static Item ammoItem(String name, Color color) {
        return new Item(name, color) {{
            explosiveness = 1.5f;
            buildable = false;
            databaseCategory = "munition";
        }};
    }
}