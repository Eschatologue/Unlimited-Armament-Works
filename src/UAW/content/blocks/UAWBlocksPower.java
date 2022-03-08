package UAW.content.blocks;

import UAW.content.*;
import UAW.world.blocks.gas.LiquidBoiler;
import UAW.world.blocks.power.*;
import UAW.world.drawer.GasDrawEverything;
import gas.GasStack;
import gas.world.consumers.ConsumeGas;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.gen.Sounds;
import mindustry.type.*;
import mindustry.world.Block;

import static mindustry.type.ItemStack.with;

/** Contains Power Blocks or Blocks that produces Power */
public class UAWBlocksPower implements ContentList {
	public static Block placeholder,

	petroleumGenerator,
		steamWheel, steamTurbine, advancedSteamTurbine,
		steamKettle, coalBoiler, pressureBoiler, geothermalKettle, solarBoiler;

	@Override
	public void load() {
		petroleumGenerator = new WarmUpGenerator("petroleum-generator") {{
			requirements(Category.power, with(
				Items.copper, 200,
				Items.lead, 200,
				Items.titanium, 150,
				Items.plastanium, 100,
				Items.silicon, 120,
				Items.metaglass, 120
			));
			size = 4;
			health = 300 * size;
			hasLiquids = true;
			hasItems = false;
			liquidCapacity = 1200f;
			ambientSound = Sounds.machine;
			ambientSoundVolume = 0.05f;

			powerProduction = 45f;
			consumes.liquid(Liquids.oil, 3f);
		}};

		// Steam to Power
		steamTurbine = new GasGenerator("steam-turbine") {{
			requirements(Category.power, with(
				Items.copper, 65,
				Items.graphite, 40,
				Items.lead, 55,
				Items.silicon, 15
			));
			size = 2;
			powerProduction = 5.5f;
			gasCapacity = 60f;
			consumes.add(new ConsumeGas(UAWGas.steam, 0.5f).optional(false, false));
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
			liquidAmount = 15f;
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
			liquidAmount = 45;
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
			liquidAmount = 135;
			consumes.items(with(
				UAWItems.anthracite, 2
			));
			drawer = new GasDrawEverything() {{
				drawSteam = true;
				steamParticleCount = 45;
				steamParticleLifetime = 180f;
				steamParticleSize = 6f;
				steamParticleSpreadRadius = 12f;
			}};
			consumes.liquid(liquidInput, liquidAmount / craftTime);
			outputGas = new GasStack(gasResult, liquidAmount * conversionMultiplier);
		}};

	}
}
