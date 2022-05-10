package UAW.content.blocks;

import UAW.content.*;
import UAW.graphics.UAWPal;
import UAW.world.blocks.power.LiquidBoiler;
import UAW.world.blocks.power.*;
import UAW.world.drawer.DrawBoilerSmoke;
import mindustry.content.Items;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.consumers.ConsumeItemFlammable;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;

import static mindustry.type.ItemStack.with;

/** Contains Power Blocks or Blocks that produces Power */
public class UAWBlocksPower {
	public static Block placeholder,

	steamTurbine, advancedSteamTurbine,

	steamKettle, coalBoiler, pressureBoiler, geothermalBoiler, solarBoiler;

	public static void load() {

		// Steam to Power
//		steamTurbine = new SimplePowerGenerator("steam-turbine") {{
//			requirements(Category.power, with(
//				Items.copper, 65,
//				Items.graphite, 40,
//				Items.lead, 55,
//				Items.silicon, 15
//			));
//			size = 2;
//			squareSprite = false;
//			powerProduction = 6f;
//			liquidCapacity = 120f;
//			consumeLiquid(UAWLiquids.steam, 0.5f);
//		}};
//		advancedSteamTurbine = new GasGenerator("advanced-steam-turbine") {{
//			requirements(Category.power, with(
//				Items.copper, 90,
//				Items.titanium, 60,
//				Items.lead, 150,
//				Items.silicon, 90,
//				Items.metaglass, 45
//			));
//			size = 4;
//			rotatorSpeed = 7f;
//			warmupSpeed = 0.0005f;
//			squareSprite = false;
//			powerProduction = 20f;
//			liquidCapacity = 320f;
//			steamBottom = false;
//			steamIntensityMult = 0.8f;
//			steamParticleSize = 6f;
//			consumes.add(new ConsumeGas(UAWLiquids.steam, 1.8f).optional(false, false));
//		}};

		// Steam Production
		steamKettle = new LiquidBoiler("steam-kettle") {{
			requirements(Category.power, with(
				Items.copper, 12,
				Items.lead, 12
			));
			size = 1;
			squareSprite = false;
			warmupSpeed = 0.01f;
			liquidAmount = 7.5f;
			drawer = new DrawMulti(
				new DrawParticles() {{
					reverse = true;
					color = UAWPal.steamFront;
					alpha = 0.6f;
					particleSize = 4f;
					particles = 10;
					particleRad = 12f;
					particleLife = 140f;
				}},
				new DrawHeatRegion("-heat")
			);

			consumeItems(new ItemStack(
				Items.coal, 1
			));
			consumeLiquid(liquidInput, liquidAmount / craftTime);
			outputLiquid = new LiquidStack(gasResult, liquidAmount * conversionMultiplier);
		}};
		coalBoiler = new LiquidBoiler("coal-boiler") {{
			requirements(Category.power, with(
				Items.copper, 35,
				Items.graphite, 25,
				Items.lead, 30
			));
			size = 2;
			squareSprite = false;
			warmupSpeed = 0.002f;
			liquidAmount = 36;
			consume(new ConsumeItemFlammable());
			drawer = new DrawMulti(
				new DrawBoilerSmoke() {{
					particles = 35;
					lifetime = 160f;
					size = 3f;
					spreadRadius = 5f;
				}},
				new DrawHeatRegion("-heat"),
				new DrawDefault()
			);
			consumeLiquid(liquidInput, liquidAmount / craftTime);
			outputLiquid = new LiquidStack(gasResult, liquidAmount * conversionMultiplier);
		}};
		pressureBoiler = new LiquidBoiler("pressure-boiler") {{
			requirements(Category.power, with(
				Items.copper, 150,
				Items.lead, 125,
				Items.titanium, 65,
				Items.graphite, 50
			));
			size = 4;
			squareSprite = false;
			itemCapacity = 30;
			warmupSpeed = 0.0015f;
			liquidAmount = 90;
			consumeItems(with(
				UAWItems.anthracite, 2
			));
			drawer = new DrawMulti(
				new DrawBoilerSmoke() {{
					particles = 45;
					lifetime = 180f;
					size = 5f;
					spreadRadius = 12f;
				}},
				new DrawHeatRegion("-heat"),
				new DrawDefault()
			);
			consumeLiquid(liquidInput, liquidAmount / craftTime);
			outputLiquid = new LiquidStack(gasResult, liquidAmount * conversionMultiplier);
		}};

		geothermalBoiler = new LiquidBoiler("geothermal-boiler") {{
			requirements(Category.power, with(
				Items.copper, 60,
				Items.graphite, 45,
				Items.lead, 45,
				Items.silicon, 30,
				Items.metaglass, 30
			));
			size = 2;
			squareSprite = false;
			warmupSpeed = 0.0025f;
			liquidAmount = 25f;
			drawer = new DrawMulti(
				new DrawBoilerSmoke() {{
					particles = 30;
					lifetime = 160f;
					size = 3f;
					spreadRadius = 5f;
				}},
				new DrawHeatRegion("-heat"),
				new DrawDefault()
			);
			consumeLiquid(liquidInput, liquidAmount / craftTime);
			outputLiquid = new LiquidStack(gasResult, liquidAmount * conversionMultiplier);

			attribute = Attribute.heat;
			floating = true;
			baseEfficiency = 0f;
			boostScale = 0.5f;
			maxBoost = 2f;
		}};

//		solarBoiler = new LiquidBoiler("solar-boiler") {{
//			requirements(Category.power, with(
//				Items.copper, 160,
//				Items.lead, 90,
//				Items.silicon, 120,
//				Items.metaglass, 120,
//				Items.phaseFabric, 15
//			));
//			size = 2;
//			squareSprite = false;
//			warmupSpeed = 0.0025f;
//			liquidAmount = 15f;
//			consumeLiquid(liquidInput, liquidAmount / craftTime);
//			outputGas = new GasStack(gasResult, liquidAmount * conversionMultiplier);
//
//			attribute = Attribute.light;
//			baseEfficiency = 1f;
//			boostScale = 0.25f;
//			maxBoost = 1f;
//		}};

	}
}
