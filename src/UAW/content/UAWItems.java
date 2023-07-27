package UAW.content;

import UAW.audiovisual.UAWPal;
import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.type.Item;

public class UAWItems {
	public static Item placeholder,
	// Processed
	cryogel,
	// Metal
	stoutsteel,
	// Crystal
	anthracite, phlogistonCrystal,
	// Natural
	sulphur;

	public static void load() {
		cryogel = new Item("item-cryogel", UAWPal.cryoMiddle) {{
			flammability = -10f;
			explosiveness = 0f;
		}};
		stoutsteel = new Item("item-stoutsteel", UAWPal.stoutSteelMiddle) {{
			cost = 2.5f;
		}};

		sulphur = new Item("item-sulphur", Color.valueOf("e28654")) {{
		}};
		anthracite = new Item("item-anthracite", Color.valueOf("272727")) {{
			flammability = Items.coal.flammability * 1.8f;
			explosiveness = Items.coal.explosiveness / 2;
			hardness = 4;
		}};
	}

//	public class Munitions {
//		public static Item placeholder,
//
//		// Empties
//		emptyBulletCasing, emptyMineCanister,
//
//		// Bullets
//		standardBullet, incendiaryBullet, cryoBullet, armourPiercingBullet, spreadShotBullet, explosiveBullet, arcBullet,
//
//		// Mine Canister
//		standardMineCanister, incendiaryMineCanister, cryoMineCanister, antiAirMineCanister, sporeMineCanister, empMineCanister, nukeMineCanister;
//
//		public static void load() {
//			emptyBulletCasing = new Item("item-bullet-empty", Color.valueOf("989aa4"));
//			emptyMineCanister = new Item("item-mine-empty", Color.valueOf("989aa4"));
//
//			standardBullet = new Item("item-bullet-basic", Color.valueOf("eab678"));
//			incendiaryBullet = new Item("item-bullet-incendiary", Color.valueOf("e48c57"));
//			cryoMineCanister = new Item("item-bullet-cryo", UAWPal.cryoMiddle);
//			armourPiercingBullet = new Item("item-bullet-ap", Color.valueOf("ebeef5"));
//			spreadShotBullet = new Item("item-bullet-spreadshot", Color.valueOf("646469"));
//			explosiveBullet = new Item("item-bullet-explosive", Color.valueOf("e46b58"));
//			arcBullet = new Item("item-bullet-arc", Pal.lancerLaser);
//
//			standardMineCanister = new Item("item-mine-basic", Color.valueOf("eab678"));
//			incendiaryMineCanister = new Item("item-mine-incendiary", Color.valueOf("e48c57"));
//			cryoMineCanister = new Item("item-mine-cryo", UAWPal.cryoMiddle);
//			antiAirMineCanister = new Item("item-mine-aa", Color.valueOf("cbd97f"));
//			sporeMineCanister = new Item("item-mine-spore", Color.valueOf("7457ce"));
//			empMineCanister = new Item("item-mine-emp", Color.valueOf("6974c4"));
//			nukeMineCanister = new Item("item-mine-nuke", Color.valueOf("706f74"));
//		}
//	}
}