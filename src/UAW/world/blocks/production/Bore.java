package UAW.world.blocks.production;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.ObjectFloatMap;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.Drill;

import static UAW.Vars.tick;

/** Like drill, but can make use of drillMultipliers, has particle effects, and bar colour stuff */
public class Bore extends Drill {
	public int particles = -1;
	public float particleLife = -1f, particleSpreadRadius = -1, particleSize = -1;

	public boolean dominantItemBarColor = true;

	public ObjectFloatMap<Item> drillMultipliers = new ObjectFloatMap<>();

	public Bore(String name) {
		super(name);
		itemCapacity = (int) (size * 15f);
		warmupSpeed = 0.002f;
		hasLiquids = false;
		drawRim = true;
		updateEffectChance = 0.03f;
		ambientSoundVolume = 0.05f;
		squareSprite = false;
	}

	@Override
	public void init() {
		super.init();
		if (particles < 0) particles = (int) (size * 9.5);
		if (particleLife < 0) particleLife = (size * 2.5f) * 10;
		if (particleSpreadRadius < 0) particleSpreadRadius = size * 2.8f;
		if (particleSize < 0) particleSize = size * 1.4f;
	}

	@Override
	public float getDrillTime(Item item) {
		return (drillTime / drillMultipliers.get(item, 1f)) + hardnessDrillMultiplier * item.hardness;
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

	public class BoreBuild extends DrillBuild {

		/** Particle drill effects rand */
		protected static final Rand rand = new Rand();

		public void drawDrillParticles() {
			float base = (Time.time / particleLife);
			rand.setSeed(this.id);
			for (int i = 0; i < particles; i++) {
				float fin = (rand.random(1f) + base) % 1f, fout = 1f - fin;
				float angle = rand.random(360f);
				float len = particleSpreadRadius * Interp.pow2Out.apply(fin);
				Draw.color(dominantItem.color);
				Fill.circle(x + Angles.trnsx(angle, len), y + Angles.trnsy(angle, len), particleSize * fout * warmup);
			}
			Draw.blend();
			Draw.reset();
		}

		@Override
		public void draw() {
			super.draw();
			float s = 0.3f;
			float ts = 0.6f;
			Draw.rect(region, x, y);
			super.drawCracks();
			if (drawRim) {
				Draw.color(heatColor);
				Draw.alpha(warmup * ts * (1f - s + Mathf.absin(Time.time, 3f, s)));
				Draw.blend(Blending.additive);
				Draw.rect(rimRegion, x, y);
				Draw.blend();
				Draw.color();
			}
			if (warmup > 0.2f) drawDrillParticles();
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
