package UAW.world.drawer;

import UAW.world.blocks.production.filters.FilterGenericCrafter;
import arc.Core;
import arc.graphics.g2d.*;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;

/** Only works with {@link FilterGenericCrafter}, Texture have to be white */
public class DrawFilterItemCrafterTop extends DrawBlock {
	public TextureRegion itemRegion;

	@Override
	public void draw(Building build) {
		if (build instanceof FilterGenericCrafter.FilterGenericCrafterBuild filterGenericCrafterBuild) {
			if (filterGenericCrafterBuild.itemMultProgress() > 0.001f) {
				Draw.alpha(filterGenericCrafterBuild.warmupProgress());
				Draw.color(filterGenericCrafterBuild.itemColor);
				Draw.rect(itemRegion, build.x, build.y);
				Draw.reset();
			}
		}
	}

	@Override
	public void load(Block block) {
		itemRegion = Core.atlas.find(block.name + "-item");
	}
}
