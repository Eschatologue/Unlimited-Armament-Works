package uaw.audiovisual;

import arc.graphics.Color;
import mindustry.graphics.Pal;

public class UAWPal {

    // --- General ---
    public static final Color heat = new Color(1f, 0.22f, 0.22f, 0.8f);
    public static final Color missileSmoke = Color.grays(0.3f).cpy().lerp(Pal.bulletYellow, 0.5f).cpy().a(0.4f);

    // --- Ammo types ---
    public static final Color redAmmoFront = Pal.redLight;
    public static final Color redAmmoBack = Color.valueOf("ea8878");

    public static final Color cryoAmmoFront = Color.valueOf("c0ecff");
    public static final Color cryoAmmoBack = Color.valueOf("6586b0");

    public static final Color incendAmmoFront = Pal.lightishOrange;
    public static final Color incendAmmoBack = Pal.lightOrange;

    public static final Color titaniumAmmoFront = Color.valueOf("A9D8FF");
    public static final Color titaniumAmmoBack = Color.valueOf("809CEB");

    // --- Effects ---
    public static final Color sporeFront = Color.valueOf("9e78dc");
    public static final Color sporeMiddle = Color.valueOf("7457ce");
    public static final Color sporeBack = Color.valueOf("5541b1");

    public static final Color healFront = Color.valueOf("84f491");
    public static final Color healMiddle = Color.valueOf("73d188");
    public static final Color healBack = Color.valueOf("62ae7f");

    public static final Color surgeFront = Color.valueOf("f3e979");
    public static final Color surgeMid = Color.valueOf("e8d174");
    public static final Color surgeBack = Color.valueOf("d99f6b");

    // --- Materials ---
    public static final Color sulphurFront = Color.valueOf("ffcd66");
    public static final Color sulphurBack = Color.valueOf("e28654");

    public static final Color coalFront = Color.valueOf("404040");
    public static final Color coalBack = Color.valueOf("2a2a2a");

    public static final Color ketoniteFront = Color.valueOf("ffe4ca");
    public static final Color ketoniteMid = Color.valueOf("f7cba4");
    public static final Color ketoniteBack = Color.valueOf("e0b28d");

    public static final Color stoutsteelFront = Color.valueOf("828b98");
    public static final Color stoutsteelMid = Color.valueOf("636a78");
    public static final Color stoutsteelBack = Color.valueOf("444858");

    public static final Color refOilFront = Color.valueOf("70e6e2");
    public static final Color refOilMid = Color.valueOf("5ec1bf");
    public static final Color refOilBack = Color.valueOf("408b99");

    public static final Color phlogiston = Color.valueOf("00aea2");
    public static final Color phlogistonFront = Color.valueOf("82e9de");
    public static final Color phlogistonMid = Color.valueOf("4db6ac");
    public static final Color phlogistonBack = Color.valueOf("00867d");

    public static final Color waterFront = Color.valueOf("8aa3f4");
    public static final Color waterMid = Color.valueOf("6974c4");
    public static final Color waterBack = Color.valueOf("5757c1");

    // --- Draw glows ---
    public static final Color drawGlowGold = Color.valueOf("fcba03");
    public static final Color drawGlowPink = Color.valueOf("ff6060ff");
    public static final Color drawGlowOrange = Color.valueOf("ff9b59");

    // --- Emissions ---
    public static final Color refinerySmokeSulph = Color.grays(0.38f).lerp(sulphurBack, 0.125f);
    public static final Color refinerySmokeFire = Color.grays(0.38f).lerp(redAmmoFront, 0.15f);
}