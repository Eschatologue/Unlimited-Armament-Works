package UAW.world.consumers;

import UAW.world.blocks.production.filters.FilterGenericCrafter;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.world.consumers.*;

public class ConsumeItemFuelExplosive extends ConsumeItemExplosive {

	@Override
	public void update(Building build) {
		super.update(build);
		var item = getConsumed(build);
		if (build instanceof FilterGenericCrafter.FilterGenericCrafterBuild filterGenericCrafterBuild) {
			if (item != null) {
				filterGenericCrafterBuild.useItemFuel = true;
				filterGenericCrafterBuild.itemEfficiency = item.explosiveness;
			}
			filterGenericCrafterBuild.itemColor = item != null ? item.color : Pal.bar;
		}
	}
}

