package UAW.world.blocks.production;

import arc.graphics.Blending;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.*;
import gas.world.blocks.production.GasDrill;
import mindustry.graphics.Drawf;

import static arc.math.Angles.randLenVectors;
import static arc.math.Mathf.rand;

public class UAWGasDrill extends GasDrill {

	public int groundDustCount = 20;
	public float groundDustSpread = 3f;
	public float groundDustIntensity = 1f;
	public float groundDustSize = 4f;

	public UAWGasDrill(String name) {
		super(name);
	}

	public class UAWGasDrillBuild extends GasDrillBuild {

		public void drawCrush() {
			rand.setSeed(pos());
			float lifetime = 1f - (Time.time / 60 + rand.random(warmup)) % groundDustIntensity;
			Draw.color(Tmp.c1.set(dominantItem.color).mul(1.1f));
			randLenVectors(0, groundDustCount, 12f * rand.random(groundDustSpread), (x, y) -> {
				Fill.circle(x, y, (1 - lifetime) * groundDustSize);
			});
		}

		@Override
		public void draw() {
			float s = 0.3f;
			float ts = 0.6f;
			Draw.rect(region, x, y);
			super.drawCracks();
			drawCrush();
			if (drawRim) {
				Draw.color(heatColor);
				Draw.alpha(warmup * ts * (1f - s + Mathf.absin(Time.time, 3f, s)));
				Draw.blend(Blending.additive);
				Draw.rect(rimRegion, x, y);
				Draw.blend();
				Draw.color();
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
