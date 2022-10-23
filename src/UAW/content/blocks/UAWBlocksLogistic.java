package UAW.content.blocks;

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
	pressurizedLiquidRouter, pressurizedLiquidJunction, pressurizedLiquidBridge, pressurizedConduit, platedPressurizedConduit,

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

		pressurizedLiquidRouter = new LiquidRouter("pressurized-liquid-router") {{
			requirements(Category.liquid, with(
				UAWItems.stoutsteel, 3,
				Items.plastanium, 4,
				Items.metaglass, 4
			));
			health = 625;
			baseExplosiveness = 8f;
			liquidCapacity = 60f;
			liquidPressure = 2f;
			placeableLiquid = true;

			squareSprite = false;

			liquidPadding = 4 * px;
		}};
		pressurizedLiquidJunction = new LiquidJunction("pressurized-liquid-junction") {{
			requirements(Category.liquid, with(
				UAWItems.stoutsteel, 3,
				Items.plastanium, 4,
				Items.metaglass, 4
			));
			health = 620;
			liquidCapacity = 60f;
			liquidPressure = 1.5f;
			placeableLiquid = true;
			baseExplosiveness = 8f;

			squareSprite = false;
		}};
		pressurizedLiquidBridge = new DirectionLiquidBridge("pressurized-liquid-bridge") {{
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
		}};
		pressurizedConduit = new PrzConduit("pressurized-conduit") {{
			requirements(Category.liquid, with(
				Items.titanium, 3,
				Items.metaglass, 2,
				Items.plastanium, 3
			));
			health = 550;
			baseExplosiveness = 8f;
		}};
		platedPressurizedConduit = new PrzPlatedConduit("plated-pressurized-conduit") {{
			requirements(Category.liquid, with(
				Items.titanium, 3,
				Items.metaglass, 2,
				Items.plastanium, 3,
				UAWItems.stoutsteel, 2
			));
			health = 850;
			baseExplosiveness = 8f;
		}};

	}
}

