package UAW.world.blocks.production;

import UAW.content.UAWLiquids;
import arc.Core;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.Nullable;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.graphics.*;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.world.blocks.liquid.LiquidBlock;
import mindustry.world.blocks.production.Fracker;

import static UAW.Vars.px;

public class ExplodingFracker extends Fracker {
	public Effect unstableEffect = Fx.hitBulletSmall;

	public boolean explodeOnFull = true;
	public float unstableSpeed = 0.0005f;

	public TextureRegion bottomRegion;

	public float liquidPadding = 15 * px;
	public Liquid drawnLiquid = UAWLiquids.phlogiston;

	public ExplodingFracker(String name) {
		super(name);
	}

	@Override
	public void setBars() {
		super.setBars();
		addBar("instability", (ExplodingFrackerBuild entity) ->
			new Bar("bar.instability",
				Pal.sap,
				() -> entity.instability
			));
	}

	@Override
	public void load() {
		super.load();
		bottomRegion = Core.atlas.find(name + ("-bottom"));
	}

	public class ExplodingFrackerBuild extends FrackerBuild {
		public float instability = 0;

		/** Checks whenever this block is full of liquid (Don't take out of context) */
		public boolean isLiquidFull() {
			return (liquids.get(result) >= liquidCapacity - 0.0001f);
		}

		/** Generates effects whenever this block became unstable */
		public void unstableEffect() {
			if (isLiquidFull() && Mathf.chanceDelta(instability)) {
				unstableEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
			}
		}

		/** Destroy itself whenever the instability is full */
		public void selfDestruct() {
			if (isLiquidFull() && explodeOnFull) {
				instability = Mathf.approachDelta(instability, 1, unstableSpeed);
			} else instability = Mathf.approachDelta(instability, 0, unstableSpeed);

			if (instability >= 1) kill();
		}

		@Override
		public void updateTile() {
			super.updateTile();
			selfDestruct();
			unstableEffect();
		}

		@Override
		public void draw() {
			Draw.rect(bottomRegion, x, y);
			LiquidBlock.drawTiledFrames(size, x, y, liquidPadding, drawnLiquid, liquids.get(result) / block.liquidCapacity * 1);
			Draw.reset();
			Draw.rect(region, x, y);
			Draw.z(Layer.blockCracks);
			super.drawCracks();
			Draw.z(Layer.blockAfterCracks);

			Drawf.spinSprite(rotatorRegion, x, y, pumpTime * rotateSpeed);
			Draw.rect(topRegion, x, y);
		}

	}
}
