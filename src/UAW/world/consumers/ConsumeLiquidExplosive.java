package UAW.world.consumers;

import mindustry.gen.Building;
import mindustry.world.consumers.*;

public class ConsumeLiquidExplosive extends ConsumeLiquidFilter {
	public float minExplosiveness;

	public ConsumeLiquidExplosive(float minExplosiveness, float amount){
		this.amount = amount;
		this.minExplosiveness = minExplosiveness;
		this.filter = liquid -> liquid.explosiveness >= this.minExplosiveness;
	}

	public ConsumeLiquidExplosive(float amount){
		this(0.2f, amount);
	}

	public ConsumeLiquidExplosive(){
		this(0.2f);
	}

	@Override
	public float efficiencyMultiplier(Building build){
		var liq = getConsumed(build);
		return liq == null ? 0f : liq.explosiveness;
	}
}
