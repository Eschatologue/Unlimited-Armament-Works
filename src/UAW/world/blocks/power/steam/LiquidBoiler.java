package UAW.world.blocks.power.steam;

import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.meta.Stat;

public class LiquidBoiler extends ConsumeGenerator {

	public LiquidBoiler(String name) {
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

	public class LiquidBoilerBuild extends ConsumeGeneratorBuild {

	}
}


