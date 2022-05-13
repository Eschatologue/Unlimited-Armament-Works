package UAW.world.blocks.production;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Time;
import mindustry.graphics.*;
import mindustry.world.blocks.production.Drill;

public class UAWDrill extends Drill {
	public int particles = -1;
	public float particleLife = -1f, particleSpreadRadius = -1, particleSize = -1;

	public UAWDrill(String name) {
		super(name);
	}

	@Override
	public void init() {
		super.init();
		if (particles < 0) particles = (int) (size * 9.5);
		if (particleLife < 0) particleLife = (size * 2.5f) * 10;
		if (particleSpreadRadius < 0) particleSpreadRadius = size * 2.8f;
		if (particleSize < 0) particleSize = size * 1.4f;
	}

	public class UAWDrillBuild extends DrillBuild {
		/** Without this, it would fuck up other drills */
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
			if (warmup > 0) drawDrillParticles();
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
