package UAW.world.blocks.gas;

import UAW.content.UAWGas;
import arc.Core;
import gas.GasStack;
import gas.type.Gas;
import mindustry.content.Liquids;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.world.meta.Stat;

import static UAW.Vars.tick;

/** Boils crafter to convert liquid to gas based on conversion ratio */
public class LiquidBoiler extends GasCrafter {
	/** The amount of liquid unit it consumes */
	public float liquidAmount = 30f;
	/** Liquid to gas conversion ratio */
	public float conversionMultiplier = 1.5f;
	/** Block inventory capacity multipler */
	public float capacityMultiplier = 1.5f;

	public Liquid liquidInput = Liquids.water;
	public Gas gasResult = UAWGas.steam;

	public LiquidBoiler(String name) {
		super(name);
		warmupSpeed = 0.01f;
		craftTime = 2 * tick;
		hasItems = true;
		hasLiquids = true;
		hasGasses = true;
		outputsGas = true;
		consumes.liquid(liquidInput, liquidAmount / craftTime);
		outputGas = new GasStack(gasResult, liquidAmount * conversionMultiplier);
	}

	@Override
	public void init() {
		super.init();
		gasCapacity = liquidAmount * conversionMultiplier * capacityMultiplier;
		liquidCapacity = liquidAmount * capacityMultiplier;
	}


	@Override
	public void setStats() {
		super.setStats();
		stats.remove(Stat.productionTime);
	}

	@Override
	public void setBars() {
		super.setBars();
		bars.add("heat", (LiquidBoilerBuild entity) ->
			new Bar(() ->
				Core.bundle.format("bar.heat", (int) (entity.warmup)),
				() -> Pal.lightOrange,
				entity::warmupProgress
			));
//		bars.add("liquid", entity -> new Bar(() -> Core.bundle.format("bar.liquid", entity.items.total()), () -> Pal.items, () -> entity.liquids.currentAmount() / liquidCapacity));
	}

	public class LiquidBoilerBuild extends GasCrafterBuild {

		public float warmupProgress() {
			return warmup;
		}

		@Override
		public float getProgressIncrease(float base) {
			return super.getProgressIncrease(base) * warmupProgress();
		}


	}
}

