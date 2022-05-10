package UAW.world.blocks.power;

import UAW.graphics.UAWPal;
import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Time;
import mindustry.graphics.Drawf;
import mindustry.world.blocks.power.PowerGenerator;

/** Simple power generator based on consumers instead of item or liquid flammability */
public class SimplePowerGenerator extends PowerGenerator {
	public TextureRegion heatRegionBottom, rotatorRegion1, rotatorRegion2, topRegion, heatRegionTop;
	public Color heatColor = Color.valueOf("ff5512");

	public float warmupSpeed = 0.001f;
	public float rotatorSpeed = 9f;

	public Color smokeColor = UAWPal.steamFront;
	public boolean smokeBottom = true;
	public float smokeIntensityMultiplier = 1;
	public int smokeParticleCount = -1;
	public float smokeParticleLifetime = -1f, smokeParticleSpreadRadius = -1f, smokeParticleSize = -1;

	public SimplePowerGenerator(String name) {
		super(name);
	}


	@Override
	public void load() {
		super.load();
		heatRegionBottom = Core.atlas.find(name + "-heat-bottom");
		rotatorRegion1 = Core.atlas.find(name + "-rotator-1");
		rotatorRegion2 = Core.atlas.find(name + "-rotator-2");
		topRegion = Core.atlas.find(name + "-top");
		heatRegionTop = Core.atlas.find(name + "-heat-top");
	}

	@Override
	public void init() {
		super.init();
		if (smokeParticleCount < 0) smokeParticleCount = (int) ((int) (size * 10.5) * smokeIntensityMultiplier);
		if (smokeParticleLifetime < 0) smokeParticleLifetime = (size * 3) * 10 * smokeIntensityMultiplier;
		if (smokeParticleSpreadRadius < 0) smokeParticleSpreadRadius = size * 3f * smokeIntensityMultiplier;
		if (smokeParticleSize < 0) smokeParticleSize = size * 1.5f * smokeIntensityMultiplier;
	}

	@Override
	public TextureRegion[] icons() {
		return new TextureRegion[]{
			region,
			rotatorRegion1.found() ? rotatorRegion1 : Core.atlas.find("clear"),
			rotatorRegion2.found() ? rotatorRegion2 : Core.atlas.find("clear"),
			topRegion.found() ? topRegion : Core.atlas.find("clear")
		};
	}

	public class SimplePowerGeneratorBuild extends GeneratorBuild {
		float warmup;
		float totalTime;

		@Override
		public void draw() {
			super.draw();
			if (heatRegionBottom.found()) drawHeatBottom();
			if (smokeBottom) drawSteam();
			if (rotatorRegion1.found()) Drawf.spinSprite(rotatorRegion1, x, y, (rotatorSpeed * totalTime));
			if (rotatorRegion2.found()) Drawf.spinSprite(rotatorRegion2, x, y, -((rotatorSpeed * 0.8f) * totalTime));
			if (topRegion.found()) Draw.rect(topRegion, x, y);
			if (heatRegionTop.found()) drawHeatTop();
			if (!smokeBottom) drawSteam();
		}

		public void drawSteam() {
			final Rand rand = new Rand();
			float base = (Time.time / smokeParticleLifetime);
			rand.setSeed(id);
			for (int i = 0; i < smokeParticleCount; i++) {
				float fin = (rand.random(1f) + base) % 1f, fout = 1f - fin;
				float angle = rand.random(360f);
				float len = smokeParticleSpreadRadius * Interp.pow2Out.apply(fin);
				Draw.color(smokeColor);
				Draw.alpha(0.45f);
				Fill.circle(x + Angles.trnsx(angle, len), y + Angles.trnsy(angle, len), smokeParticleSize * fout * warmup);
			}
			Draw.blend();
			Draw.reset();
		}

		public void drawHeatBottom() {
			Draw.color(heatColor);
			Draw.alpha(warmup * 0.6f * (1f - 0.3f + Mathf.absin(Time.time, 3f, 0.3f)));
			Draw.blend(Blending.additive);
			Draw.rect(heatRegionBottom, x, y);
			Draw.blend();
			Draw.color();
			Draw.reset();
		}

		public void drawHeatTop() {
			Draw.color(heatColor);
			Draw.alpha(warmup * 0.6f * (1f - 0.3f + Mathf.absin(Time.time, 3f, 0.3f)));
			Draw.blend(Blending.additive);
			Draw.rect(heatRegionTop, x, y);
			Draw.blend();
			Draw.color();
			Draw.reset();
		}

		@Override
		public void updateTile() {
			super.updateTile();
			if (canConsume()) {
				warmup = Mathf.lerpDelta(warmup, 1f, warmupSpeed);
			} else warmup = Mathf.lerpDelta(warmup, 0f, warmupSpeed);
			totalTime += warmup * edelta();
			productionEfficiency = warmup;
		}
	}
}
