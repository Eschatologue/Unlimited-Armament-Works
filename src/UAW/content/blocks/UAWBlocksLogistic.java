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
	pressurizedConduit, platedPressurizedConduit, pressurizedLiquidRouter, pressurizedLiquidJunction, pressurizedLiquidBridge,

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
//				UAWItems.compositeAlloy, 2,
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

		pressurizedConduit = new PressurizedConduit("pressurized-conduit") {{
			requirements(Category.liquid, with(
				Items.titanium, 3,
				Items.metaglass, 2,
				Items.plastanium, 3
			));
			health = 500;
			baseExplosiveness = 8f;
		}};
		platedPressurizedConduit = new PlatedPressurizedConduit("plated-pressurized-conduit") {{
			requirements(Category.liquid, with(
				Items.titanium, 3,
				Items.metaglass, 2,
				Items.plastanium, 3,
				Items.thorium, 2
			));
			health = 750;
			baseExplosiveness = 8f;
		}};
		pressurizedLiquidRouter = new LiquidRouter("pressurized-liquid-router") {{
			requirements(Category.liquid, with(
				UAWItems.compositeAlloy, 3,
				Items.plastanium, 2,
				Items.metaglass, 2
			));
			health = 500;
			baseExplosiveness = 8f;
			liquidCapacity = 60f;
			liquidPressure = 2f;
			placeableLiquid = true;

			squareSprite = false;

			liquidPadding = 4 * px;
		}};
		pressurizedLiquidJunction = new LiquidJunction("pressurized-liquid-junction") {{
			requirements(Category.liquid, with(
				UAWItems.compositeAlloy, 3,
				Items.plastanium, 2,
				Items.metaglass, 2
			));
			health = 500;
			liquidCapacity = 60f;
			liquidPressure = 1.5f;
			placeableLiquid = true;
			baseExplosiveness = 8f;

			squareSprite = false;
		}};
		pressurizedLiquidBridge = new DirectionLiquidBridge("pressurized-liquid-bridge") {{
			requirements(Category.liquid, with(
				UAWItems.compositeAlloy, 3,
				Items.plastanium, 2,
				Items.metaglass, 2
			));
			health = 500;
			liquidCapacity = 60f;
			liquidPressure = 1.5f;
			range = 6;
			baseExplosiveness = 8f;

			squareSprite = false;

			((PressurizedConduit)pressurizedConduit).rotBridgeReplacement = this;
		}};


	}
}

