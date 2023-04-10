package UAW.world.blocks.power.steam;

import UAW.world.blocks.production.filters.AttributeFilterCrafter;
import mindustry.world.meta.Stat;

import static UAW.Vars.tick;

public class AttributeSteamBoiler extends AttributeFilterCrafter {
	public AttributeSteamBoiler(String name) {
		super(name);
		warmupSpeed = 0.0025f;
		craftTime = 2f * tick;
		hasItems = false;
		hasLiquids = true;
		squareSprite = false;
		acceptsItems = false;
		minEfficiency = 0.1f;
		baseEfficiency = 0f;
		boostScale = 0.25f;
		maxBoost = 3f;
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.remove(Stat.productionTime);
	}
}
