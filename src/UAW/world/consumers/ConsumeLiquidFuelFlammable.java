package UAW.world.consumers;

import UAW.world.blocks.production.FilterGenericCrafter;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;
import mindustry.world.consumers.ConsumeLiquidFlammable;

public class ConsumeLiquidFuelFlammable extends ConsumeLiquidFlammable {

	@Override
	public void update(Building build) {
		super.update(build);
		Liquid liq = getConsumed(build);
		if (build instanceof FilterGenericCrafter.FilterGenericCrafterBuild filterGenericCrafterBuild) {
			if (liq != null) {
				filterGenericCrafterBuild.useLiquidFuel = true;
				filterGenericCrafterBuild.liquidEfficiency = liq.flammability;
			}
			filterGenericCrafterBuild.itemColor = liq != null ? liq.color : Pal.bar;
		}
	}
}
