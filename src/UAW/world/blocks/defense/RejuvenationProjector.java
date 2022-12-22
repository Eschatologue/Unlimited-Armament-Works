package UAW.world.blocks.defense;

import UAW.world.blocks.defense.walls.UAWWall;
import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.world.blocks.defense.MendProjector;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import java.util.Objects;

import static mindustry.Vars.indexer;

public class RejuvenationProjector extends MendProjector {
	public DrawBlock drawer = new DrawDefault();
	public float boostMultiplier = 3f;
	public float boostDuration = 180f;
	public float boostStartPercentage = 60;

	public RejuvenationProjector(String name) {
		super(name);
		baseColor = Color.valueOf("a387ea");
		solid = true;
		update = true;
		group = BlockGroup.projectors;
		hasPower = true;
		hasItems = false;
		hasLiquids = true;
		emitLight = true;
		range = 10 * 8;
		lightRadius = range / 2;
		canOverdrive = false;
		baseExplosiveness = 15;
		researchCostMultiplier = 1.5f;
	}

	@Override
	public void setStats() {
		stats.timePeriod = useTime;
		stats.add(Stat.speedIncrease, "+" + (int) (boostMultiplier * 100f - 100) + "%");
		super.setStats();

		stats.remove(Stat.boostEffect);
	}

	public class RejuvinationProjectorBuild extends MendBuild {
		float heat;
		float charge = Mathf.random(reload);

		@Override
		public void updateTile() {

			smoothEfficiency = Mathf.lerpDelta(smoothEfficiency, efficiency, 0.08f);
			heat = Mathf.lerpDelta(heat, efficiency > 0 ? 1f : 0f, 0.08f);
			charge += heat * delta();

			phaseHeat = Mathf.lerpDelta(phaseHeat, optionalEfficiency, 0.1f);

			if (optionalEfficiency > 0 && timer(timerUse, useTime)) {
				consume();
			}

			if (charge >= reload) {
				float realRange = range + phaseHeat * phaseRangeBoost;
				charge = 0f;

				indexer.eachBlock(this, realRange, Objects::nonNull, other -> {
					if (other instanceof UAWWall.UAWWallBuild wallBuild) {
						wallBuild.armor();
					}
				});
			}
		}

		@Override
		public void draw() {
			drawer.draw(this);
		}
	}
}
