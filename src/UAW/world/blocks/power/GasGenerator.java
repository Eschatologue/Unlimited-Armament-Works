package UAW.world.blocks.power;

import UAW.graphics.UAWPal;
import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Time;
import gas.world.blocks.power.GasPowerGenerator;
import mindustry.graphics.Drawf;

public class GasGenerator extends GasPowerGenerator {
	protected static final Rand rand = new Rand();
	public TextureRegion rotatorRegion1, rotatorRegion2, topRegion;

	public float warmupSpeed = 0.001f;
	public float rotatorSpeed = 12f;

	public int steamParticleCount = 25;
	public float steamParticleLifetime = 60f;
	public float steamParticleSpreadRadius = 7f;
	public float steamParticleSize = 3f;

	public GasGenerator(String name) {
		super(name);
	}

	@Override
	public void load() {
		super.load();
		rotatorRegion1 = Core.atlas.find(name + "-rotator-1");
		rotatorRegion2 = Core.atlas.find(name + "-rotator-2");
		topRegion = Core.atlas.find(name + "-top");
	}

	public class GasGeneratorBuild extends GasPowerGenerator.GasGeneratorBuild {
		float warmup;
		float totalTime;

		@Override
		public void draw() {
			super.draw();
			drawSteam();
			if (rotatorRegion1.found()) Drawf.spinSprite(rotatorRegion1, x, y, rotatorSpeed * warmup);
			if (rotatorRegion2.found()) Drawf.spinSprite(rotatorRegion2, x, y, rotatorSpeed * warmup);
			if (topRegion.found()) Draw.rect(topRegion, x, y);
		}

		public void drawSteam() {
			float base = (Time.time / steamParticleLifetime);
			rand.setSeed(id);
			for (int i = 0; i < steamParticleCount; i++) {
				float fin = (rand.random(1f) + base) % 1f, fout = 1f - fin;
				float angle = rand.random(360f);
				float len = steamParticleSpreadRadius * Interp.pow2Out.apply(fin);
				Draw.color(UAWPal.steamFront);
				Draw.alpha(0.45f);
				Fill.circle(x + Angles.trnsx(angle, len), y + Angles.trnsy(angle, len), steamParticleSize * fout * warmup);
			}
			Draw.blend();
			Draw.reset();
		}

		@Override
		public void updateTile() {
			super.updateTile();
			if (consValid()) {
				warmup = Mathf.lerpDelta(warmup, 1f, warmupSpeed);
				totalTime += delta();
			} else warmup = Mathf.lerpDelta(warmup, 0f, warmupSpeed);

			productionEfficiency = warmup;
		}
	}
}
