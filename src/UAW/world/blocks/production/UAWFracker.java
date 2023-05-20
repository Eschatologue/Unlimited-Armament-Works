package UAW.world.blocks.production;

import UAW.content.UAWLiquids;
import arc.Core;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import mindustry.graphics.*;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.world.blocks.liquid.LiquidBlock;
import mindustry.world.blocks.production.Fracker;

import static UAW.Vars.px;

public class UAWFracker extends Fracker {

	public TextureRegion bottomRegion;

	public float liquidPadding = 15 * px;
	public Liquid drawnLiquid = UAWLiquids.phlogiston;

	public UAWFracker(String name) {
		super(name);
	}

	@Override
	public void load() {
		super.load();
		bottomRegion = Core.atlas.find(name + ("-bottom"));
	}

	public class BurstFrackerBuild extends FrackerBuild {

		@Override
		public void draw() {
			Draw.rect(bottomRegion, x, y);
			LiquidBlock.drawTiledFrames(size, x, y, liquidPadding, drawnLiquid, liquids.get(result) / block.liquidCapacity * 1);
			Draw.rect(region, x, y);
			Draw.z(Layer.blockCracks);
			super.drawCracks();
			Draw.z(Layer.blockAfterCracks);

			Drawf.spinSprite(rotatorRegion, x, y, pumpTime * rotateSpeed);
			Draw.rect(topRegion, x, y);
		}

	}
}
