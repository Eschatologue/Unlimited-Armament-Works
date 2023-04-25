package UAW.world.blocks.production;

import UAW.world.meta.UAWStat;
import arc.Core;
import arc.graphics.Blending;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.Drill;

import static UAW.Vars.tick;

/**
 * Like drill, but specialised torwards softer materials or materials below its hardnessLimit, anything above that will
 * get exponentially slower
 */
public class Bore extends Drill {
	public float hardnessLimit = 1;
	public float hardnessMultLimit = 0;
	public float hardnessBelowMult = 0.25f;
	public float hardnessUpperMult = 0.5f;

	public int particles = -1;
	public float particleLife = -1f, particleSpreadRadius = -1, particleSize = -1;

	public int drillType = 1;

	public String bonus = "[green]+@%", penalty = "[red]-@%[]";


	public boolean dominantItemBarColor = true;

	public Bore(String name) {
		super(name);

		drawRim = true;
		hasLiquids = false;
		squareSprite = false;

		itemCapacity = (int) (size * 15f);
		ambientSoundVolume = 0.0015f;
		drillEffectChance = 0.5f;
		hardnessDrillMultiplier = 100;
		liquidBoostIntensity = 1;
		updateEffectChance = 0.03f;
		warmupSpeed = 0.002f;

		ambientSound = Sounds.grinding;
	}

	@Override
	public float getDrillTime(Item item) {
		float mult = 1f;
		float tierDiff = (hardnessLimit - item.hardness);
		if (tierDiff > 0) {
			mult -= tierDiff * hardnessBelowMult;
		} else if (tierDiff < 0) {
			mult += Math.abs(tierDiff) * hardnessUpperMult;
		}
		if (hardnessMultLimit > 0) mult += hardnessMultLimit;
		float modifiedDrillTime = drillTime * mult;
		return (modifiedDrillTime + hardnessDrillMultiplier * item.hardness) / drillMultipliers.get(item, 1f);
	}

	@Override
	public void init() {
		super.init();
		particles = particles < 0 ? (int) (size * 9.5) : particles;
		particleLife = particleLife < 0 ? (size * 2.5f) * 10 : particleLife;
		particleSpreadRadius = particleSpreadRadius < 0 ? size * 2.8f : particleSpreadRadius;
		particleSize = particleSize < 0 ? size * 1.4f : particleSize;
	}

	@Override
	public void setBars() {
		super.setBars();
		removeBar("drillspeed");
		addBar("drillspeed", (BoreBuild e) ->
			new Bar(
				() -> Core.bundle.format("bar.drillspeed", Strings.fixed(e.lastDrillSpeed * tick * e.timeScale(), 2)),
				() -> dominantItemBarColor ? e.dominantItem.color : Pal.ammo,
				() -> e.warmup
			));
	}

	@Override
	public void setStats() {
		Object upper = Strings.autoFixed(hardnessUpperMult * 100, 2);
		Object below = Strings.autoFixed(hardnessBelowMult * 100, 2);
		super.setStats();
		stats.add(UAWStat.hardnessTreshold, "[accent]| @ |[]", hardnessLimit);

		switch (drillType) {
			// Bore
			case 1 -> {
				stats.add(UAWStat.hardnessUpperTresh, penalty, upper);
				stats.add(UAWStat.hardnessLowerTresh, bonus, below);
			}
			// Auger
			case 2 -> {
				stats.add(UAWStat.hardnessUpperTresh, bonus, upper);
				stats.add(UAWStat.hardnessLowerTresh, penalty, below);
			}
		}
	}

	public class BoreBuild extends DrillBuild {

		/** Particle drill effects rand */
		protected static final Rand rand = new Rand();

		public void drawDrillParticles() {
			float base = (Time.time / particleLife);
			rand.setSeed(this.id);

			for (int i = 0; i < particles; i++) {
				float fin = (rand.random(1f) + base) % 1f;
				float fout = 1f - fin;
				float angle = rand.random(360f);
				float len = particleSpreadRadius * Interp.pow2Out.apply(fin);

				Draw.alpha(warmup - 0.4f);
				Draw.color(dominantItem.color);
				Fill.circle(x + Angles.trnsx(angle, len), y + Angles.trnsy(angle, len), particleSize * fout * warmup);
			}

			Draw.blend();
			Draw.reset();
		}

		@Override
		public void draw() {
			super.draw();

			Draw.rect(region, x, y);
			super.drawCracks();

			if (drawRim) {
				Draw.color(heatColor);
				float alpha = warmup * 0.6f * (1f - 0.3f + Mathf.absin(Time.time, 3f, 0.3f));
				Draw.alpha(alpha);
				Draw.blend(Blending.additive);
				Draw.rect(rimRegion, x, y);
				Draw.blend();
				Draw.color();
			}

			if (warmup > 0.2f) {
				drawDrillParticles();
			}

			if (drawSpinSprite) {
				Drawf.spinSprite(rotatorRegion, x, y, timeDrilled * rotateSpeed);
			} else {
				Draw.rect(rotatorRegion, x, y, timeDrilled * rotateSpeed);
			}

			Draw.rect(topRegion, x, y);

			if (dominantItem != null && drawMineItem) {
				Draw.color(dominantItem.color);
				Draw.rect(itemRegion, x, y);
				Draw.color();
			}
		}

	}
}
