package UAW.world.blocks.production;

import arc.math.Mathf;
import mindustry.content.Liquids;
import mindustry.type.Liquid;
import mindustry.world.blocks.production.Fracker;
import mindustry.world.meta.*;

public class SpecificFracker extends Fracker {
	public Liquid resultLiquid = Liquids.water;

	public SpecificFracker(String name) {
		super(name);
	}

	@Override
	public void setStats() {
		stats.timePeriod = itemUseTime;
		super.setStats();

		stats.add(Stat.productionTime, itemUseTime / 60f, StatUnit.seconds);
	}

	public class SpecificFrackerBuild extends FrackerBuild {
		public float accumulator;

		@Override
		public void updateTile() {
			if (efficiency > 0) {
				if (accumulator >= itemUseTime) {
					consume();
					accumulator -= itemUseTime;
				}

				super.updateTile();
				accumulator += delta() * efficiency;
			} else {
				warmup = Mathf.lerpDelta(warmup, 0f, 0.02f);
				lastPump = 0f;
				dumpLiquid(resultLiquid);
			}
		}
	}
}
