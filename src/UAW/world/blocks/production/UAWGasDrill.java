package UAW.world.blocks.production;

import arc.graphics.Blending;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Time;
import gas.world.blocks.production.GasDrill;
import mindustry.graphics.Drawf;

import static arc.math.Mathf.rand;

public class UAWGasDrill extends GasDrill {
	public int particles = 35;
	public float particleLife = -1f, particleSpreadRadius = 7f, particleLength = 3f;

	public UAWGasDrill(String name) {
		super(name);
	}

	@Override
	public void init(){
		if (particleLife < 0) particleLife = (size * 2) * 10;
	}

	public class UAWGasDrillBuild extends GasDrillBuild {

		public void drawParticles() {
			float base = (Time.time / particleLife);
			rand.setSeed(id);
			for (int i = 0; i < particles; i++) {
				float fin = (rand.random(1f) + base) % 1f, fout = 1f - fin;
				float angle = rand.random(360f);
				float len = particleSpreadRadius * Interp.pow2Out.apply(fin);
				Draw.color(dominantItem.color);
				Fill.circle(x + Angles.trnsx(angle, len), y + Angles.trnsy(angle, len), particleLength * fout * warmup);
			}
			Draw.blend();
			Draw.reset();
		}

		@Override
		public void draw() {
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
			if (warmup > 0) drawParticles();
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
