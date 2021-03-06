package UAW.content.blocks;

import UAW.content.UAWItems;
import UAW.world.blocks.gas.*;
import UAW.world.blocks.liquid.*;
import arc.graphics.Color;
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
		gasBridge, gasJunction, gasRouter,
		gasPipe, reinforcedGasPipe, platedGasPipe,

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

		gasJunction = new GasJunction("gas-junction") {{
			requirements(Category.liquid, with(
				Items.metaglass, 2,
				Items.copper, 6
			));
			size = 1;
			gasCapacity = 50f;
			hasLiquids = false;
			health = 65;
		}};
		gasRouter = new GasRouter("gas-router") {{
			requirements(Category.liquid, with(
				Items.metaglass, 2,
				Items.copper, 6
			));
			size = 1;
			gasCapacity = 50f;
			health = 65;
		}};
		gasBridge = new GasBridge("gas-bridge") {{
			requirements(Category.liquid, with(
				Items.metaglass, 8,
				Items.copper, 8
			));
			size = 1;
			health = 75;
			gasCapacity = 60f;
			outputsGas = true;
			hasGasses = true;
			range = 6;
		}};

		gasPipe = new GasPipe("gas-pipe") {{
			requirements(Category.liquid, with(
				Items.metaglass, 1,
				Items.copper, 2
			));
			size = 1;
			botColor = Color.valueOf("2c2d38");
			gasCapacity = 7.5f;
			health = 45;
			junctionReplacement = UAWBlocksLogistic.gasJunction;
			bridgeReplacement = UAWBlocksLogistic.gasBridge;
			squareSprite = false;
		}};
		reinforcedGasPipe = new GasPipe("reinforced-gas-pipe") {{
			requirements(Category.liquid, with(
				Items.metaglass, 2,
				Items.titanium, 4,
				Items.silicon, 3
			));
			size = 1;
			botColor = Color.valueOf("2c2d38");
			gasCapacity = 15f;
			health = 150;
			squareSprite = false;
		}};
		platedGasPipe = new PlatedGasPipe("plated-gas-pipe") {{
			requirements(Category.liquid, with(
				Items.metaglass, 2,
				Items.titanium, 4,
				Items.silicon, 3
			));
			size = 1;
			botColor = Color.valueOf("2c2d38");
			gasCapacity = 30f;
			health = 300;
			squareSprite = false;
		}};
	}
}
