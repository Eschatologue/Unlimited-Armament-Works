package UAW.world.blocks.power;

import mindustry.world.blocks.power.ThermalGenerator;
import mindustry.world.meta.Stat;

public class AttributeLiquidBoiler extends ThermalGenerator {

	public AttributeLiquidBoiler(String name) {
		super(name);
		hasItems = true;
		hasLiquids = true;
		outputsLiquid = true;
		liquidCapacity = 15f * size;
		hasPower = false;
		powerProduction = 0f;
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.remove(Stat.basePowerGeneration);
		stats.remove(Stat.productionTime);
	}
}
