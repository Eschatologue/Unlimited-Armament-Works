package UAW.world.blocks.defense;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.gen.Sounds;
import mindustry.logic.Ranged;
import mindustry.world.blocks.defense.MendProjector;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class RejuvenationProjector extends MendProjector {
	public float boostMultiplier = 3f;
	public float boostDuration = 180f;

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

	public class RejuvinationProjectorBuild extends MendBuild implements Ranged {
		float heat;
		float charge = Mathf.random(reload);
		float speedStart = 60f;

		@Override
		public float range() {
			return range;
		}

		@Override
		public void updateTile() {
			boolean canHeal = !checkSuppression();

			smoothEfficiency = Mathf.lerpDelta(smoothEfficiency, efficiency, 0.08f);
			heat = Mathf.lerpDelta(heat, efficiency > 0 && canHeal ? 1f : 0f, 0.08f);
			charge += heat * delta();

			if (optionalEfficiency > 0 && timer(timerUse, useTime) && canHeal) {
				consume();
			}

			if (charge >= reload && canHeal) {
				float realRange = range + phaseHeat * phaseRangeBoost;
				charge = 0f;

				indexer.eachBlock(this, realRange, b -> b.damaged() && !b.isHealSuppressed(), other -> {
					other.heal(other.maxHealth() * (healPercent + phaseHeat * phaseBoost) / 100f * efficiency);
					other.recentlyHealed();
					Fx.healBlockFull.at(other.x, other.y, other.block.size, baseColor, other.block);
					if (other.health() > (other.maxHealth / 100) * speedStart && other.health() < (other.maxHealth / 100) * (speedStart + 5)) {
						other.applyBoost(boostMultiplier, boostDuration);
						Fx.overdriven.at(other.x + Mathf.range(size * 6f), other.y + Mathf.range(size * 6f));
						Sounds.shield.at(other.x, other.y);
					}
				});

			}
		}

		@Override
		public void draw() {
			super.draw();

			float f = 1f - (Time.time / 200f) % 1f;

			Draw.color(baseColor);
			Draw.alpha(heat * Mathf.absin(Time.time, 10f, 1f) * 0.5f);
			Draw.rect(topRegion, x, y);
			Draw.alpha(0.8f);
			Lines.stroke((2f * f + 0.1f) * heat);

			float r = Math.max(0f, Mathf.clamp(2f - f * 2f) * size * tilesize / 2f - f - 0.2f), w = Mathf.clamp(0.5f - f) * size * tilesize;
			Lines.beginLine();
			for (int i = 0; i < 4; i++) {
				Lines.linePoint(x + Geometry.d4(i).x * r + Geometry.d4(i).y * w, y + Geometry.d4(i).y * r - Geometry.d4(i).x * w);
				if (f < 0.5f)
					Lines.linePoint(x + Geometry.d4(i).x * r - Geometry.d4(i).y * w, y + Geometry.d4(i).y * r + Geometry.d4(i).x * w);
			}
			Lines.endLine(true);

			Draw.reset();
		}
	}
}
