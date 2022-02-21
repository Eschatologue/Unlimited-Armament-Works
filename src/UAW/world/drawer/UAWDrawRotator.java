package UAW.world.drawer;

import arc.Core;
import arc.graphics.g2d.*;
import mindustry.graphics.Drawf;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.DrawBlock;

public class UAWDrawRotator extends DrawBlock {
	public TextureRegion rotator, rotatorTop;
	public float spinSpeed = 12f;

	@Override
	public void draw(GenericCrafter.GenericCrafterBuild build) {
		Draw.rect(build.block.region, build.x, build.y);
		if (rotator.found()) {
			Drawf.spinSprite(rotator, build.x, build.y, build.warmup * spinSpeed);
		} else {
			Draw.rect(rotator, build.x, build.y, build.warmup * spinSpeed);
		}
		if (rotatorTop.found()) Draw.rect(rotatorTop, build.x, build.y);
	}

	@Override
	public void load(Block block) {
		rotator = Core.atlas.find(block.name + "-rotator");
		rotatorTop = Core.atlas.find(block.name + "-rotator-top");
	}

	@Override
	public TextureRegion[] icons(Block block) {
		if (rotatorTop.found()) {
			return new TextureRegion[]{block.region, rotator, rotatorTop};
		} else return new TextureRegion[]{block.region, rotator};
	}
}
