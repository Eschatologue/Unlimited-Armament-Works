package UAW.world.drawer;

import UAW.audiovisual.UAWPal;
import UAW.world.blocks.production.*;
import arc.Core;
import arc.graphics.g2d.*;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;

public class DrawInstability extends DrawBlock {
	public TextureRegion unstableRegion;

	@Override
	public void draw(Building build) {
		if (build instanceof ExplodingCrafter.ExplodingCrafterBuild explodingCrafterBuild) {
			if (explodingCrafterBuild.isLiquidFull()) {
				Draw.z(Layer.blockAdditive);
				Draw.color(UAWPal.heat, (explodingCrafterBuild.instability - (explodingCrafterBuild.instability * 0.7f)));
				Draw.rect(unstableRegion, explodingCrafterBuild.x, explodingCrafterBuild.y);
				Draw.reset();
			}
		}
		if (build instanceof ExplodingFracker.ExplodingFrackerBuild explodingFrackerBuild) {
			if (explodingFrackerBuild.isLiquidFull()) {
				Draw.z(Layer.blockAdditive);
				Draw.color(UAWPal.heat, (explodingFrackerBuild.instability - (explodingFrackerBuild.instability * 0.7f)));
				Draw.rect(unstableRegion, explodingFrackerBuild.x, explodingFrackerBuild.y);
				Draw.reset();
			}
		}
	}

	@Override
	public void load(Block block) {
		unstableRegion = Core.atlas.find(block.name + "-unstable");
	}
}
