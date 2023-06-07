package UAW.audiovisual;

import arc.graphics.Color;
import mindustry.graphics.Pal;

public class UAWPal {
	// front = light, back = dark
	public static Color placeHolder,

	heat = new Color(1f, 0.22f, 0.22f, 0.8f),
	missileSmoke = Color.grays(0.3f).cpy().lerp(Pal.bulletYellow, 0.5f).cpy().a(0.4f),

	cryoFront = Color.valueOf("c0ecff"),
		cryoMiddle = Color.valueOf("87ceeb"),
		cryoBack = Color.valueOf("6586b0"),

	incendFront = Color.valueOf("f8ad42"),
		incendBack = Color.valueOf("f68021"),

	titaniumFront = Color.valueOf("a4b8fa"),
		titaniumMiddle = Color.valueOf("919fe7"),
		titaniumBack = Color.valueOf("7575c8"),

	graphiteFront = Color.valueOf("95abd9"),
		graphiteMiddle = Color.valueOf("6b77a1"),
		graphiteBack = Color.valueOf("626f9b"),

	sporeFront = Color.valueOf("9e78dc"),
		sporeMiddle = Color.valueOf("7457ce"),
		sporeBack = Color.valueOf("5541b1"),

	healFront = Color.valueOf("84f491"),
		healMiddle = Color.valueOf("73d188"),
		healBack = Color.valueOf("62ae7f"),

	surgeFront = Color.valueOf("f3e979"),
		surgeMiddle = Color.valueOf("e8d174"),
		surgeBack = Color.valueOf("d99f6b"),

	stoutsteelFront = Color.valueOf("828b98"),
		stoutSteelMiddle = Color.valueOf("636a78"),
		stoutsteelBack = Color.valueOf("444858"),

	phlogiston = Color.valueOf("00aea2"),
		phlogistonFront = Color.valueOf("82e9de"),
		phlogistonMid = Color.valueOf("4db6ac"),
		phlogistonBack = Color.valueOf("00867d"),

	coalFront = Color.valueOf("404040"),
		coalBack = Color.valueOf("2a2a2a"),

	waterFront = Color.valueOf("8aa3f4"),
		waterMiddle = Color.valueOf("6974c4"),
		waterBack = Color.valueOf("5757c1"),

	steamFront = Color.valueOf("ececec"),
		steamMid = Color.valueOf("DCDDE4"),
		steamBack = Color.valueOf("CCCEDB"),

	drawGlowGold = Color.valueOf("fcba03"),
		drawGlowPink = Color.valueOf("ff6060ff"),
		drawGlowOrange = Color.valueOf("ff9b59")

			// end
			;
}
