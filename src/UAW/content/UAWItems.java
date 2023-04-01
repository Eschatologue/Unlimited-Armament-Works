package UAW.content;

import UAW.audiovisual.UAWPal;
import arc.graphics.Color;
import mindustry.type.Item;

public class UAWItems {
	public static Item placeholder,
	// Processed
	cryogel, dieselCore,
	// Metal
	stoutsteel,
	// Natural
	sulphur, anthracite;

	public static void load() {
		cryogel = new Item("item-cryogel", Color.valueOf("87ceeb")) {{
			flammability = -10f;
			explosiveness = 0f;
		}};
		stoutsteel = new Item("item-stoutsteel", UAWPal.stoutSteelMiddle) {{
			cost = 2.5f;
		}};

		sulphur = new Item("item-sulphur", Color.valueOf("e28654")) {{

		}};
		anthracite = new Item("item-anthracite", Color.valueOf("272727")) {{
			flammability = 1.8f;
			explosiveness = 0.25f;
			hardness = 4;
		}};
	}

	public class Munitions {
		public static Item placeholder,

		// Empties
		emptyBulletCasing, emptyMineCanister,

		// Bullets
		standardBullet, incendiaryBullet, cryoBullet, armourPiercingBullet, spreadShotBullet, explosiveBullet, phaseBullet, arcBullet,

		// Mine Canister
		standardMineCanister, incendiaryMineCanister, cryoMineCanister, antiAirMineCanister, sporeMineCanister, empMineCanister, nukeMineCanister;

		public static void load() {
			emptyMineCanister = new Item("item-mine-empty", Color.valueOf("989aa4"));
			standardMineCanister = new Item("item-mine-basic", Color.valueOf("eab678"));
			incendiaryMineCanister = new Item("item-mine-incendiary", Color.valueOf("e48c57"));
			cryoMineCanister = new Item("item-mine-cryo", Color.valueOf("87ceeb"));
			antiAirMineCanister = new Item("item-mine-aa", Color.valueOf("cbd97f"));
			sporeMineCanister = new Item("item-mine-spore", Color.valueOf("7457ce"));
			empMineCanister = new Item("item-mine-emp", Color.valueOf("6974c4"));
			nukeMineCanister = new Item("item-mine-nuke", Color.valueOf("706f74"));
		}
	}
}