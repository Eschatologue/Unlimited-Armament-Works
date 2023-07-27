package UAW.world.consumers;

import UAW.world.blocks.power.FilterLiquidGenerator;
import arc.math.Mathf;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;

public class ConsumeLiquidFuelExplosive extends ConsumeLiquidExplosive {
	public float efficiencyMult = 1;

	public ConsumeLiquidFuelExplosive(float minExplosiveness, float amount) {
		super(minExplosiveness, amount);
	}

	public ConsumeLiquidFuelExplosive(float amount) {
		super(0.2f, amount);
	}

	public ConsumeLiquidFuelExplosive() {
		super(0.2f);
	}

	@Override
	public void update(Building build) {
		super.update(build);
		Liquid liq = getConsumed(build);
		if (build instanceof FilterLiquidGenerator.FilterLiquidGeneratorBuild filterLiquidGeneratorBuild) {
			if (liq != null) {
				filterLiquidGeneratorBuild.liquidEfficiency = efficiencyMult == 1 ? liq.explosiveness : Mathf.ceil(liq.explosiveness * efficiencyMult);
			}
			filterLiquidGeneratorBuild.liquidColor = liq != null ? liq.color : Pal.bar;
		}

	}
}
