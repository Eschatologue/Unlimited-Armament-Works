package UAW.content.blocks;

import UAW.audiovisual.UAWFx;
import UAW.content.UAWItems;
import UAW.world.blocks.liquid.*;
import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.DirectionLiquidBridge;
import mindustry.world.blocks.liquid.*;

import static UAW.Vars.px;
import static mindustry.type.ItemStack.with;

/** Contains anything relating to resource transportation and storage */
public class UAWBlocksLogistic {
	public static Block placeholder,

	// Heat
	copperHeatNode, compositeHeatNode,

	// Liquid
	hardenedConduit,
		stoutsteelConduit, stoutsteelConduitPlated, stoutsteelLiquidRouter, stoutsteelLiquidJunction, stoutsteelLiquidBridge,

	// Payload
	heavyDutyPayloadConveyor;

	public static void load() {

		// Heat Nodes
//		copperHeatNode = new HeatConduit("copper-heat-node") {{
//			requirements(Category.power, with(
//				Items.copper, 18,
//				Items.lead, 4
//			));
//			size = 1;
//			health = 50;
//			researchCostMultiplier = 5f;
//		}};
//		compositeHeatNode = new HeatConduit("composite-heat-node") {{
//			requirements(Category.power, with(
//				Items.copper, 10,
//				UAWItems.stoutsteel, 2,
//				Items.graphite, 1
//			));
//			size = 1;
//			health = 160;
//			researchCostMultiplier = 12f;
//			placeableLiquid = true;
//
//			hidePowerStat = true;
//
//			outputsPower = true;
//			consumePowerBuffered(100f);
//		}};

		// Liquid

		hardenedConduit = new PrzPlatedConduit("hardened-conduit") {{
			requirements(Category.liquid, with(
				Items.titanium, 2, Items.metaglass, 2, Items.silicon, 1)
			);
			health = 115;
			armor = 15;
			squareSprite = false;
			liquidCapacity = 20f;
			liquidPressure = 1.05f;
			underBullets = true;
			botColor = Color.valueOf("565656");

			placeableLiquid = false;

			stoutsteel = false;
		}};

		stoutsteelLiquidRouter = new LiquidRouter("stoutsteel-liquid-router") {{
			requirements(Category.liquid, with(
				UAWItems.stoutsteel, 3,
				Items.plastanium, 4,
				Items.metaglass, 4
			));
			health = 625;
			armor = 25;
			baseExplosiveness = 8f;
			liquidPressure = 1.5f;
			placeableLiquid = true;

			liquidCapacity = 45f;
			researchCostMultiplier = 3;
			underBullets = true;
			solid = false;

			squareSprite = false;
			hasPower = true;
			conductivePower = true;
			liquidPadding = 4 * px;

			breakEffect = UAWFx.breakBlockPhlog;
		}};
		stoutsteelLiquidJunction = new LiquidJunction("stoutsteel-liquid-junction") {{
			requirements(Category.liquid, with(
				UAWItems.stoutsteel, 3,
				Items.plastanium, 4,
				Items.metaglass, 4
			));
			health = 620;
			armor = 25;
			liquidCapacity = 45f;
			liquidPressure = 1.5f;
			placeableLiquid = true;
			baseExplosiveness = 8f;

			underBullets = true;
			solid = false;
			hasPower = true;
			conductivePower = true;
			squareSprite = false;

			breakEffect = UAWFx.breakBlockPhlog;

		}};
		stoutsteelLiquidBridge = new DirectionLiquidBridge("stoutsteel-liquid-bridge") {{
			requirements(Category.liquid, with(
				UAWItems.stoutsteel, 3,
				Items.plastanium, 4,
				Items.metaglass, 4
			));
			health = 625;
			armor = 25;
			liquidCapacity = 60f;
			liquidPressure = 1.5f;
			range = 6;
			baseExplosiveness = 8f;

			squareSprite = false;
			hasPower = true;
			conductivePower = true;
			breakEffect = UAWFx.breakBlockPhlog;
		}};
		stoutsteelConduit = new PrzConduit("stoutsteel-conduit") {{
			requirements(Category.liquid, with(
				UAWItems.stoutsteel, 2,
				Items.metaglass, 2,
				Items.silicon, 2
			));
			health = 550;
			armor = 25;
			baseExplosiveness = 8f;
		}};
		stoutsteelConduitPlated = new PrzPlatedConduit("stoutsteel-conduit-plated") {{
			requirements(Category.liquid, with(
				UAWItems.stoutsteel, 2,
				Items.metaglass, 2,
				Items.silicon, 3,
				Items.thorium, 2
			));
			health = 650;
			armor = 35;
			baseExplosiveness = 8f;
		}};

	}
}

