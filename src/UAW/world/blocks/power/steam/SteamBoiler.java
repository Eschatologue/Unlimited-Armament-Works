package UAW.world.blocks.power.steam;

import UAW.world.blocks.production.filters.FilterGenericCrafter;
import UAW.world.consumers.ConsumeItemFuelFlammable;
import mindustry.content.Fx;
import mindustry.world.consumers.ConsumeItemExplode;
import mindustry.world.meta.Stat;

import static UAW.Vars.tick;

public class SteamBoiler extends FilterGenericCrafter {

	public SteamBoiler(String name) {
		super(name);
		warmupSpeed = 0.0025f;
		craftTime = 2f * tick;
		hasItems = true;
		hasLiquids = true;
		updateEffect = Fx.steam;
		consume(new ConsumeItemFuelFlammable());
		consume(new ConsumeItemExplode());
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.remove(Stat.productionTime);
	}

}
