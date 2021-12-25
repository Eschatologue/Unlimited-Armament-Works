package UAW.world.blocks.liquid;

import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.blocks.distribution.Router;
import mindustry.world.blocks.liquid.LiquidRouter;

public class PressurizedRouter extends LiquidRouter {
	public PressurizedRouter(String name) {
		super(name);
		outputsPower = consumesPower = hasPower = true;
		consumes.power(0.125f);
	}

	public class PressurizedRouterBuild extends LiquidRouterBuild {
		@Override
		public void updateTile() {
			if (liquids.total() > 0.01f && power.status > 0.99f) {
				dumpLiquid(liquids.current());
			}
		}

		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			return (liquids.current() == liquid || liquids.currentAmount() < 0.2f);
		}
	}
}
