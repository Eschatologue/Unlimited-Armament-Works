package UAW.world.blocks.production;

import UAW.audiovisual.UAWPal;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.Nullable;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.GenericCrafter;

public class ExplodingCrafter extends GenericCrafter {

	public Effect unstableEffect = Fx.hitBulletSmall;

	public boolean explodeOnFull = true;
	public float unstableSpeed = 0.0025f;

	public @Nullable
	Liquid explosionPuddleLiquid;

	public ExplodingCrafter(String name) {
		super(name);

		ambientSound = Sounds.smelter;
		ambientSoundVolume = 0.06f;
	}

	@Override
	public void setBars() {
		super.setBars();
		addBar("instability", (ExplodingCrafterBuild entity) ->
			new Bar("bar.instability",
				Pal.sap,
				() -> entity.instability
			));
	}

	@Override
	public void init() {
		super.init();
		if (explodeOnFull && outputLiquid != null) {
			explosionPuddleLiquid = explosionPuddleLiquid == null ? outputLiquid.liquid : explosionPuddleLiquid;
		}
	}

	public class ExplodingCrafterBuild extends GenericCrafterBuild {
		public float instability = 0;

		/** Checks whenever this block is full of liquid (Don't take out of context) */
		public boolean isLiquidFull() {
			return (liquids.get(outputLiquid.liquid) >= liquidCapacity - 0.0001f);
		}

		/** Generates effects whenever this block became unstable */
		public void unstableEffect() {
			if (isLiquidFull() && Mathf.chanceDelta(instability)) {
				unstableEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
			}
		}

		/** Destroy itself whenever the instability is full */
		public void selfDestruct() {
			if (isLiquidFull() && (outputLiquid != null && explodeOnFull)) {
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
	}
}
