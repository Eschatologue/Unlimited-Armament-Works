package UAW.content.blocks;

import UAW.graphics.UAWFxD;
import UAW.world.blocks.gas.LiquidBoiler;
import UAW.world.blocks.power.WarmUpGenerator;
import gas.GasStack;
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

		// Gasses
		steamKettle = new LiquidBoiler("steam-kettle") {{
			requirements(Category.power, with(
				Items.copper, 12,
				Items.lead, 12
			));
			size = 1;
			squareSprite = false;
			warmupSpeed = 0.01f;
			liquidAmount = 15f;
			gasEffect = UAWFxD.steamCloud(1.5f);
			gasEffectRnd = 0;
			gasEffectWarmupMult = 0.10f;
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
			gasEffect = UAWFxD.steamCloud(3.5f);
			gasEffectWarmupMult = 0.2f;
			gasEffectRnd = 0.2f;
			liquidAmount = 36;
			consumes.items(new ItemStack(
				Items.coal, 4
			));
			consumes.liquid(liquidInput, liquidAmount / craftTime);
			outputGas = new GasStack(gasResult, liquidAmount * conversionMultiplier);
		}};
		pressureBoiler = new LiquidBoiler("pressure-boiler") {{
			requirements(Category.power, with(
				Items.copper, 150,
				Items.lead, 125,
				Items.titanium, 55,
				Items.graphite, 50
			));
			size = 4;
			squareSprite = false;
			itemCapacity = 30;
			warmupSpeed = 0.0015f;
			gasEffect = UAWFxD.steamCloud(8f);
			gasEffectWarmupMult = 0.15f;
			gasEffectRnd = 0.05f;
			liquidAmount = 180;
			consumes.items(with(
				Items.coal, 4,
				Items.pyratite, 2
			));
			consumes.liquid(liquidInput, liquidAmount / craftTime);
			outputGas = new GasStack(gasResult, liquidAmount * conversionMultiplier);
		}};

	}
}
