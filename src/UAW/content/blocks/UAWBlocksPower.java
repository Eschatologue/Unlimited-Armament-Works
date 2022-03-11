package UAW.content.blocks;

import UAW.content.*;
import UAW.world.blocks.gas.LiquidBoiler;
import UAW.world.blocks.power.GasGenerator;
import UAW.world.drawer.GasDrawEverything;
import gas.GasStack;
import gas.world.consumers.ConsumeGas;
import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.meta.Attribute;

import static mindustry.type.ItemStack.with;

/** Contains Power Blocks or Blocks that produces Power */
public class UAWBlocksPower implements ContentList {
	public static Block placeholder,

	steamTurbine, advancedSteamTurbine,

	steamKettle, coalBoiler, pressureBoiler, geothermalBoiler, solarBoiler;

	@Override
	public void load() {

		// Steam to Power
		steamTurbine = new GasGenerator("steam-turbine") {{
			requirements(Category.power, with(
				Items.copper, 65,
				Items.graphite, 40,
				Items.lead, 55,
				Items.silicon, 15
			));
			size = 2;
			squareSprite = false;
			powerProduction = 6f;
			gasCapacity = 120f;
			consumes.add(new ConsumeGas(UAWGas.steam, 0.5f).optional(false, false));
		}};
		advancedSteamTurbine = new GasGenerator("advanced-steam-turbine") {{
			requirements(Category.power, with(
				Items.copper, 90,
				Items.titanium, 60,
				Items.lead, 150,
				Items.silicon, 90,
				Items.metaglass, 45
			));
			size = 4;
			rotatorSpeed = 7f;
			warmupSpeed = 0.0005f;
			squareSprite = false;
			powerProduction = 20f;
			gasCapacity = 320f;
			steamBottom = false;
			steamIntensityMult = 0.8f;
			steamParticleSize = 6f;
			consumes.add(new ConsumeGas(UAWGas.steam, 1.8f).optional(false, false));
		}};

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
			drawer = new GasDrawEverything() {{
				drawSteam = true;
				steamParticleCount = 20;
				steamParticleLifetime = 150f;
				steamParticleSize = 1.5f;
				steamParticleSpreadRadius = 2f;
			}};

			consumes.items(new ItemStack(
				Items.coal, 1
			));
			consumes.liquid(liquidInput, liquidAmount / craftTime);
			outputGas = new GasStack(gasResult, liquidAmount * conversionMultiplier);
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
			liquidAmount = 35;
			consumes.items(new ItemStack(
				Items.coal, 3
			));
			drawer = new GasDrawEverything() {{
				drawSteam = true;
				steamParticleCount = 35;
				steamParticleLifetime = 160f;
				steamParticleSize = 3f;
				steamParticleSpreadRadius = 5f;
			}};
			consumes.liquid(liquidInput, liquidAmount / craftTime);
			outputGas = new GasStack(gasResult, liquidAmount * conversionMultiplier);
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
			consumes.items(with(
				UAWItems.anthracite, 2
			));
			drawer = new GasDrawEverything() {{
				drawSteam = true;
				steamParticleCount = 45;
				steamParticleLifetime = 180f;
				steamParticleSize = 5f;
				steamParticleSpreadRadius = 12f;
			}};
			consumes.liquid(liquidInput, liquidAmount / craftTime);
			outputGas = new GasStack(gasResult, liquidAmount * conversionMultiplier);
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
			drawer = new GasDrawEverything() {{
				drawSteam = true;
				steamParticleCount = 35;
				steamParticleLifetime = 160f;
				steamParticleSize = 3f;
				steamParticleSpreadRadius = 5f;
			}};
			consumes.liquid(liquidInput, liquidAmount / craftTime);
			outputGas = new GasStack(gasResult, liquidAmount * conversionMultiplier);

			attribute = Attribute.heat;
			baseEfficiency = 0f;
			boostScale = 0.25f;
			maxBoost = 1.5f;
		}};
		solarBoiler = new LiquidBoiler("solar-boiler") {{
			requirements(Category.power, with(
				Items.copper, 160,
				Items.lead, 90,
				Items.silicon, 120,
				Items.metaglass, 120,
				Items.phaseFabric, 15
			));
			size = 2;
			squareSprite = false;
			warmupSpeed = 0.0025f;
			liquidAmount = 15f;
			drawer = new GasDrawEverything() {{
				drawSteam = true;
				steamParticleCount = 35;
				steamParticleLifetime = 160f;
				steamParticleSize = 3f;
				steamParticleSpreadRadius = 5f;
			}};
			consumes.liquid(liquidInput, liquidAmount / craftTime);
			outputGas = new GasStack(gasResult, liquidAmount * conversionMultiplier);

			attribute = Attribute.light;
			baseEfficiency = 0f;
			boostScale = 0.25f;
			maxBoost = 1f;
		}};

	}
}
