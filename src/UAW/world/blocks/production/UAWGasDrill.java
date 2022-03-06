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
		hasGasses = true;
	}

	@Override
	public void init(){
		super.init();
		if (particleLife < 0) particleLife = (size * 2) * 10;
	}

	public class UAWGasDrillBuild extends GasDrillBuild {

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
