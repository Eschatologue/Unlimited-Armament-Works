package UAW.content.techTree;

import mindustry.content.Items;

import static mindustry.type.ItemStack.with;

public class TechTreeContent {
	public static TechTreeNode placeholder,

	// Title
	uawBegin,

	// SubBranch - blockCategory
	production, crafters, defence, power,

	// SubBranch - turret
	turret, turretMG, turretSG, turretART, turretMSL

		// others
		;

	public static void load() {
		uawBegin = new TechTreeNode("start");

		turret = new TechTreeNode("turret"){{
		}};
		turretMG = new TechTreeNode("turret-mg");
		turretSG = new TechTreeNode("turret-sg");
		turretART = new TechTreeNode("turret-art");
		turretMSL = new TechTreeNode("turret-msl");
	}
}
