package UAW.content.blocks;

import UAW.world.blocks.power.WarmUpGenerator;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.Block;

import static mindustry.type.ItemStack.with;

/** Contains Power Blocks */
public class UAWBlocksPower implements ContentList {
	public static Block placeholder,

	petroleumGenerator;

	@Override
	public void load() {
		petroleumGenerator = new WarmUpGenerator("petroleum-generator") {{
			requirements(Category.power, with(
				Items.copper, 200,
				Items.lead, 200,
				Items.titanium, 150,
				Items.plastanium, 100,
				Items.silicon, 120,
				Items.metaglass, 120
			));
			size = 4;
			health = 300 * size;
			hasLiquids = true;
			hasItems = false;
			liquidCapacity = 1200f;
			ambientSound = Sounds.machine;
			ambientSoundVolume = 0.05f;

			powerProduction = 45f;
			consumes.liquid(Liquids.oil, 3f);
		}};

	}
}
