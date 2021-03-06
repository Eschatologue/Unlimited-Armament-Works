package UAW.world.blocks.power;

import UAW.graphics.UAWPal;
import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Time;
import gas.world.blocks.power.GasPowerGenerator;
import mindustry.graphics.Drawf;

public class GasGenerator extends GasPowerGenerator {
	public TextureRegion heatRegionBottom, rotatorRegion1, rotatorRegion2, topRegion, heatRegionTop;
	public Color heatColor = Color.valueOf("ff5512");

	public float warmupSpeed = 0.001f;
	public float rotatorSpeed = 9f;

	public Color steamColor = UAWPal.steamFront;
	public boolean steamBottom = true;
	public float steamIntensityMult = 1;
	public int steamParticleCount = -1;
	public float steamParticleLifetime = -1f, steamParticleSpreadRadius = -1f, steamParticleSize = -1;

	public GasGenerator(String name) {
		super(name);
		hasGasses = true;
		outputsGas = false;
		hasPower = true;
		outputsPower = true;
		hasItems = false;
		hasLiquids = false;
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
		if (steamParticleCount < 0) steamParticleCount = (int) ((int) (size * 10.5) * steamIntensityMult);
		if (steamParticleLifetime < 0) steamParticleLifetime = (size * 3) * 10 * steamIntensityMult;
		if (steamParticleSpreadRadius < 0) steamParticleSpreadRadius = size * 3f * steamIntensityMult;
		if (steamParticleSize < 0) steamParticleSize = size * 1.5f * steamIntensityMult;
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

	public class GasGeneratorBuild extends GasPowerGenerator.GasGeneratorBuild {
		float warmup;
		float totalTime;

		@Override
		public void draw() {
			super.draw();
			if (heatRegionBottom.found()) drawHeatBottom();
			if (steamBottom) drawSteam();
			if (rotatorRegion1.found()) Drawf.spinSprite(rotatorRegion1, x, y, (rotatorSpeed * totalTime));
			if (rotatorRegion2.found()) Drawf.spinSprite(rotatorRegion2, x, y, -((rotatorSpeed * 0.8f) * totalTime));
			if (topRegion.found()) Draw.rect(topRegion, x, y);
			if (heatRegionTop.found()) drawHeatTop();
			if (!steamBottom) drawSteam();
		}

		public void drawSteam() {
			final Rand rand = new Rand();
			float base = (Time.time / steamParticleLifetime);
			rand.setSeed(id);
			for (int i = 0; i < steamParticleCount; i++) {
				float fin = (rand.random(1f) + base) % 1f, fout = 1f - fin;
				float angle = rand.random(360f);
				float len = steamParticleSpreadRadius * Interp.pow2Out.apply(fin);
				Draw.color(steamColor);
				Draw.alpha(0.45f);
				Fill.circle(x + Angles.trnsx(angle, len), y + Angles.trnsy(angle, len), steamParticleSize * fout * warmup);
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
			if (consValid()) {
				warmup = Mathf.lerpDelta(warmup, 1f, warmupSpeed);
			} else warmup = Mathf.lerpDelta(warmup, 0f, warmupSpeed);
			totalTime += warmup * edelta();
			productionEfficiency = warmup;
		}
	}
}
