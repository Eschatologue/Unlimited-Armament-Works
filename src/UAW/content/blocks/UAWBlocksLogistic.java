package UAW.content.blocks;

import UAW.audiovisual.*;
import UAW.content.UAWItems;
import UAW.world.blocks.liquid.*;
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
	stoutsteelLiquidRouter, stoutsteelLiquidJunction, stoutsteelLiquidBridge, stoutsteelConduit, stoutsteelConduitPlated,

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

		stoutsteelLiquidRouter = new LiquidRouter("stoutsteel-liquid-router") {{
			requirements(Category.liquid, with(
				UAWItems.stoutsteel, 3,
				Items.plastanium, 4,
				Items.metaglass, 4
			));
			health = 625;
			baseExplosiveness = 8f;
			liquidPressure = 1.5f;
			placeableLiquid = true;

			liquidCapacity = 45f;
			researchCostMultiplier = 3;
			underBullets = true;
			solid = false;

			squareSprite = false;

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
			liquidCapacity = 45f;
			liquidPressure = 1.5f;
			placeableLiquid = true;
			baseExplosiveness = 8f;

			underBullets = true;
			solid = false;

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
			liquidCapacity = 60f;
			liquidPressure = 1.5f;
			range = 6;
			baseExplosiveness = 8f;

			squareSprite = false;

			breakEffect = UAWFx.breakBlockPhlog;
		}};
		stoutsteelConduit = new PrzConduit("stoutsteel-conduit") {{
			requirements(Category.liquid, with(
				UAWItems.stoutsteel, 2,
				Items.metaglass, 2,
				Items.plastanium, 3
			));
			health = 550;
			baseExplosiveness = 8f;
		}};
		stoutsteelConduitPlated = new PrzPlatedConduit("stoutsteel-conduit-plated") {{
			requirements(Category.liquid, with(
				UAWItems.stoutsteel, 2,
				Items.metaglass, 2,
				Items.plastanium, 3,
				Items.thorium, 2
			));
			health = 850;
			baseExplosiveness = 8f;
		}};

	}
}

