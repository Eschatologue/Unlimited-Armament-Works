package UAW.world.consumers;

import UAW.world.blocks.production.FilterGenericCrafter;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.world.consumers.ConsumeItemFlammable;

public class ConsumeItemFuelFlammable extends ConsumeItemFlammable {

	@Override
	public void update(Building build) {
		super.update(build);
		var item = getConsumed(build);
		if (build instanceof FilterGenericCrafter.FilterGenericCrafterBuild filterGenericCrafterBuild) {
			if (item != null) {
				filterGenericCrafterBuild.useItemFuel = true;
				filterGenericCrafterBuild.itemEfficiency = item.flammability;
			}
			filterGenericCrafterBuild.itemColor = item != null ? item.color : Pal.bar;
		}
	}
}

