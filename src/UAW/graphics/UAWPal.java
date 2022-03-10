package UAW.graphics;

import arc.graphics.Color;
import mindustry.graphics.Pal;

public class UAWPal {
	// front = light, back = dark
	public static Color placeHolder,

	cryoFront = Color.valueOf("c0ecff"),
		cryoMiddle = Color.valueOf("87ceeb"),
		cryoBack = Color.valueOf("6586b0"),
		cryoBackBloom = new Color(UAWPal.cryoBack).lerp(Color.white, 0.15f),

	incendFront = Color.valueOf("ffffa3"),
		incendBack = Color.valueOf("ffcd66"),
		darkPyraBloom = new Color(Pal.darkPyraFlame).lerp(Color.white, 0.15f),

	titaniumBlueFront = Color.valueOf("a4b8fa"),
		titaniumBlueMiddle = Color.valueOf("919fe7"),
		titaniumBlueBack = Color.valueOf("7575c8"),

	waterFront = Color.valueOf("8aa3f4"),
		waterMiddle = Color.valueOf("6974c4"),
		waterBack = Color.valueOf("5757c1"),
		waterBloom = new Color(UAWPal.waterMiddle).lerp(Color.white, 0.15f),

	healFront = Color.valueOf("84f491"),
		healMiddle = Color.valueOf("73d188"),
		healBack = Color.valueOf("62ae7f"),

	sporeFront = Color.valueOf("9e78dc"),
		sporeMiddle = Color.valueOf("7457ce"),
		sporeBack = Color.valueOf("5541b1"),
		sapBackBloom = new Color(Pal.sapBulletBack).lerp(Color.white, 0.15f),

	surgeFront = Color.valueOf("f3e979"),
		surgeMiddle = Color.valueOf("e8d174"),
		surgeBack = Color.valueOf("d99f6b"),
		surgeBackBloom = new Color(UAWPal.surgeBack).lerp(Color.white, 0.15f),

	plastBackBloom = new Color(Pal.plastaniumBack).lerp(Color.white, 0.15f),
		plastDarker = Color.valueOf("84a147"),

	coalFront = Color.valueOf("404040"),
		coalBack = Color.valueOf("2a2a2a"),
		coalBackBloom = new Color(coalBack).lerp(Color.white, 0.15f),

	steamFront = Color.valueOf("ececec"),
		steamBloom = new Color(Color.valueOf("#c6c6c6").lerp(steamFront, 0.5f))

			// end
			;
}
