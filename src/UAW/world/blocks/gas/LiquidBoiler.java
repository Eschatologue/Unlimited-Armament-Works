package UAW.world.blocks.gas;

import UAW.content.UAWGas;
import arc.Core;
import gas.GasStack;
import mindustry.content.Liquids;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.meta.Stat;

import static UAW.Vars.tick;

public class LiquidBoiler extends GasCrafter {
	public float waterAmount = 30f;
	public float steamMultiplier = 1.5f;

	public LiquidBoiler(String name) {
		super(name);
		squareSprite = false;
		outputsLiquid = false;
		hasItems = true;
		hasLiquids = true;
		hasGasses = true;
		warmupSpeed = 0.01f;
		craftTime = 2 * tick;
	}

	@Override
	public void init() {
		super.init();
		gasCapacity = waterAmount * steamMultiplier * 1.5f;
		liquidCapacity = waterAmount * 1.5f;
		consumes.liquid(Liquids.water, waterAmount / craftTime);
		outputGas = new GasStack(UAWGas.steam, waterAmount * steamMultiplier);
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.remove(Stat.productionTime);
	}

	@Override
	public void setBars() {
		super.setBars();
		bars.add("warmup", (LiquidBoilerBuild entity) ->
			new Bar(() ->
				Core.bundle.format("bar.warmup", (int) (entity.warmup)),
				() -> Pal.lightOrange,
				entity::warmupProgress));
	}

	public class LiquidBoilerBuild extends GasCrafterBuild {

		public float warmupProgress() {
			return warmup;
		}
	}
}

