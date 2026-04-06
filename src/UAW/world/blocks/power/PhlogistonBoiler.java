package UAW.world.blocks.power;

import UAW.audiovisual.UAWFx;
import UAW.world.blocks.production.filters.FilterGenericCrafter;
import UAW.world.consumers.*;
import mindustry.content.Fx;
import mindustry.world.consumers.ConsumeItemExplode;
import mindustry.world.meta.Stat;

import static UAW.Vars.tick;

public class PhlogistonBoiler extends FilterGenericCrafter {

	public PhlogistonBoiler(String name) {
		super(name);
		warmupSpeed = 0.0025f;
		craftTime = 2f * tick;
		hasItems = true;
		hasLiquids = true;
		updateEffect = Fx.steam;
		breakEffect = UAWFx.breakBlockPhlog;
		consume(new ConsumeItemFuelFlammable());
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.remove(Stat.productionTime);
	}

}
