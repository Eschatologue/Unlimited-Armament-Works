package UAW.world.blocks.gas;

import UAW.content.UAWGas;
import arc.Core;
import arc.math.Mathf;
import gas.GasStack;
import mindustry.content.Liquids;
import mindustry.graphics.Pal;
import mindustry.type.ItemStack;
import mindustry.ui.Bar;
import mindustry.world.meta.Stat;

import static UAW.Vars.tick;

public class LiquidBoiler extends GasCrafter {
	public float waterAmount = 30f;
	public float steamMultiplier = 1.5f;

	public LiquidBoiler(String name) {
		super(name);
		warmupSpeed = 0.01f;
		craftTime = 2 * tick;
	}

	@Override
	public void init() {
		super.init();
		hasItems = true;
		hasLiquids = true;
		hasGasses = true;
		outputsGas = true;
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
		bars.add("heat", (LiquidBoilerBuild entity) ->
			new Bar(() ->
				Core.bundle.format("bar.heat", (int) (entity.warmup)),
				() -> Pal.lightOrange,
				entity::warmupProgress));
	}

	public class LiquidBoilerBuild extends GasCrafterBuild {

		public float warmupProgress() {
			return warmup;
		}

		@Override
		public void updateTile() {
			if (consValid()) {
				progress += getProgressIncrease(craftTime);
				totalProgress += delta();
				warmup = Mathf.approachDelta(warmup, 1f, warmupSpeed);
				if (Mathf.chanceDelta(updateEffectChance)) {
					updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
				}
			} else {
				warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
			}

			if (progress >= 1f) {
				consume();

				if (outputItems != null) {
					for (ItemStack output : outputItems) {
						for (int i = 0; i < output.amount; i++) {
							offload(output.item);
						}
					}
				}

				if (outputLiquid != null) {
					handleLiquid(this, outputLiquid.liquid, outputLiquid.amount);
				}

				if (outputGas != null && warmupProgress() >= 0.85f) {
					handleGas(this, outputGas.gas, outputGas.amount);
				}

				craftEffect.at(x, y);
				progress %= 1f;
			}

			if (outputItems != null && timer(timerDump, dumpTime / timeScale)) {
				for (ItemStack output : outputItems) {
					dump(output.item);
				}
			}

			if (outputLiquid != null && outputLiquid.liquid != null && hasLiquids) {
				dumpLiquid(outputLiquid.liquid);
			}

			if (outputGas != null && outputGas.gas != null && hasGasses) {
				dumpGas(outputGas.gas);
			}
		}
	}
}

