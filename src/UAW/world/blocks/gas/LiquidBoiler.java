package UAW.world.blocks.gas;

import UAW.content.UAWGas;
import UAW.graphics.UAWFxS;
import arc.Core;
import arc.math.Mathf;
import gas.GasStack;
import gas.type.Gas;
import mindustry.content.Liquids;
import mindustry.graphics.Pal;
import mindustry.type.*;
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
		this.hasItems = true;
		this.hasLiquids = true;
		this.hasGasses = true;
		this.outputsGas = true;
		this.gasCapacity = liquidAmount * conversionMultiplier * capacityMultiplier;
		this.liquidCapacity = liquidAmount * capacityMultiplier;
		this.consumes.liquid(liquidInput, liquidAmount / craftTime);
		this.outputGas = new GasStack(gasResult, liquidAmount * conversionMultiplier);
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

//		@Override
//		public void updateTile() {
//			if (consValid()) {
//				progress += getProgressIncrease(craftTime);
//				totalProgress += delta();
//				warmup = Mathf.approachDelta(warmup, 1f, warmupSpeed);
//				if (warmup >= 0.001) {
//					if (Mathf.chance(warmup * steamEffectMult)) {
//						if (steamEffect == UAWFxS.steamSmoke) {
//							steamEffect.at(x + Mathf.range(size / 3.5f * 4f), y + Mathf.range(size / 3.5f * 4f), steamSize / 10, steamColor);
//						} else steamEffect.at(x + Mathf.range(size / 3.5f * 4f), y + Mathf.range(size / 3.5f * 4f));
//					}
//				}
//			} else {
//				warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
//			}
//			if (progress >= 1f) {
//				consume();
//				if (outputItems != null) {
//					for (ItemStack output : outputItems) {
//						for (int i = 0; i < output.amount; i++) {
//							offload(output.item);
//						}
//					}
//				}
//				if (outputLiquid != null) {
//					handleLiquid(this, outputLiquid.liquid, outputLiquid.amount);
//				}
//				if (outputGas != null && warmupProgress() >= 0.85f && consValid()) {
//					handleGas(this, outputGas.gas, outputGas.amount);
//				}
//				craftEffect.at(x, y);
//				progress %= 1f;
//			}
//			if (outputItems != null && timer(timerDump, dumpTime / timeScale)) {
//				for (ItemStack output : outputItems) {
//					dump(output.item);
//				}
//			}
//
//			if (outputLiquid != null && outputLiquid.liquid != null && hasLiquids) {
//				dumpLiquid(outputLiquid.liquid);
//			}
//
//			if (outputGas != null && outputGas.gas != null && hasGasses) {
//				dumpGas(outputGas.gas);
//			}
//		}
	}
}

