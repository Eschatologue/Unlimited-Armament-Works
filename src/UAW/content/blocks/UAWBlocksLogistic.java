package UAW.content.blocks;

import UAW.content.UAWItems;
import UAW.world.blocks.gas.UAWGasConduit;
import UAW.world.blocks.liquid.*;
import gas.world.blocks.gas.*;
import gas.world.blocks.sandbox.*;
import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.liquid.*;
import mindustry.world.meta.BuildVisibility;

import static mindustry.type.ItemStack.with;

/** Contains anything relating to resource transportation and storage */
public class UAWBlocksLogistic implements ContentList {
	public static Block placeholder,
	// Items

	// Liquid
	pressurizedConduit, platedPressurizedConduit, pressurizedLiquidRouter, pressurizedLiquidJunction, pressurizedLiquidBridge,
		liquidCistern,

	// Gas
	gasSource, gasVoid,
		copperGasPipe, copperGasJunction, copperGasRouter, copperGasBridge,
		compositeGasPipe, platedGasPipe, compositeGasJunction, compositeGasRouter, compositeGasBridge,

	// Payload
	heavyDutyPayloadConveyor;

	@Override
	public void load() {
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
				UAWItems.titaniumCarbide, 3,
				Items.plastanium, 2,
				Items.metaglass, 2
			));
			health = 500;
			baseExplosiveness = 8f;
			liquidCapacity = 60f;
			liquidPressure = 2f;
			placeableLiquid = true;
		}};
		pressurizedLiquidJunction = new LiquidJunction("pressurized-liquid-junction") {{
			requirements(Category.liquid, with(
				UAWItems.titaniumCarbide, 3,
				Items.plastanium, 2,
				Items.metaglass, 2
			));
			health = 500;
			liquidCapacity = 60f;
			liquidPressure = 1.5f;
			placeableLiquid = true;
			baseExplosiveness = 8f;
		}};
		pressurizedLiquidBridge = new LiquidBridge("pressurized-liquid-bridge") {{
			requirements(Category.liquid, with(
				UAWItems.titaniumCarbide, 3,
				Items.plastanium, 2,
				Items.metaglass, 2
			));
			health = 500;
			liquidCapacity = 60f;
			liquidPressure = 1.5f;
			range = 6;
			arrowPeriod = 0.9f;
			arrowTimeScl = 2.75f;
			baseExplosiveness = 8f;
		}};

		liquidCistern = new LiquidRouter("liquid-cistern") {{
			requirements(Category.liquid, with(
				Items.titanium, 90,
				Items.plastanium, 50,
				Items.metaglass, 45,
				Items.silicon, 45
			));
			size = 3;
			squareSprite = false;
			liquidCapacity = 3600f;
			health = 900;
			buildCostMultiplier = 1.5f;
		}};

		// Gasses
		gasSource = new GasSource("gas-source") {{
			requirements(Category.liquid, BuildVisibility.sandboxOnly, with());
			alwaysUnlocked = true;
		}};
		gasVoid = new GasVoid("gas-void") {{
			requirements(Category.liquid, BuildVisibility.sandboxOnly, with());
			alwaysUnlocked = true;
		}};

		copperGasPipe = new UAWGasConduit("copper-gas-pipe") {{
			requirements(Category.liquid, with(
				Items.metaglass, 1,
				Items.copper, 2
			));
			size = 1;
			gasCapacity = 30f;
			health = 45;
			junctionReplacement = UAWBlocksLogistic.copperGasJunction;
			bridgeReplacement = UAWBlocksLogistic.copperGasBridge;
			squareSprite = false;
		}};
		copperGasJunction = new GasJunction("copper-gas-junction") {{
			requirements(Category.liquid, with(
				Items.metaglass, 2,
				Items.copper, 6
			));
			size = 1;
			gasCapacity = 30f;
			hasLiquids = false;
			health = 65;
		}};
		copperGasRouter = new GasRouter("copper-gas-router") {{
			requirements(Category.liquid, with(
				Items.metaglass, 2,
				Items.copper, 6
			));
			size = 1;
			gasCapacity = 60f;
			health = 65;
		}};
		copperGasBridge = new GasBridge("copper-gas-bridge") {{
			requirements(Category.liquid, with(
				Items.metaglass, 8,
				Items.copper, 8
			));
			size = 1;
			health = 75;
			gasCapacity = 30f;
			hasPower = false;
			consumesPower = false;
			range = 6;
		}};

		compositeGasPipe = new UAWGasConduit("composite-gas-pipe") {{
			requirements(Category.liquid, with(
				Items.metaglass, 2,
				Items.titanium, 4,
				Items.silicon, 3
			));
			size = 1;
			gasCapacity = 60f;
			health = 365;
			junctionReplacement = UAWBlocksLogistic.compositeGasJunction;
//			bridgeReplacement = UAWBlocksLogistic.compositeGasBridge;
			squareSprite = false;
			leaks = false;
		}};
		platedGasPipe = new GasArmoredConduit("plated-gas-pipe") {{
			requirements(Category.liquid, with(
				Items.metaglass, 2,
				Items.titanium, 4,
				Items.silicon, 3
			));
			size = 1;
			gasCapacity = 60f;
			health = 365;
			junctionReplacement = UAWBlocksLogistic.compositeGasJunction;
			bridgeReplacement = UAWBlocksLogistic.compositeGasBridge;
			squareSprite = false;
		}};
		compositeGasJunction = new GasJunction("composite-gas-junction") {{
			requirements(Category.liquid, with(
				Items.metaglass, 2,
				Items.titanium, 4,
				Items.silicon, 3
			));
			size = 1;
			gasCapacity = 60f;
			hasLiquids = false;
			health = 320;
		}};
		compositeGasRouter = new GasRouter("composite-gas-router") {{
			requirements(Category.liquid, with(
				Items.metaglass, 3,
				Items.titanium, 6,
				Items.silicon, 5
			));
			size = 1;
			gasCapacity = 120f;
			health = 380;
		}};
//		compositeGasBridge = new GasBridge("composite-gas-bridge") {{
//			requirements(Category.liquid, with(
//				Items.metaglass, 8,
//				Items.titanium, 8,
//				Items.silicon, 5
//			));
//			size = 1;
//			health = 350;
//			range = 8;
//		}};
	}
}
