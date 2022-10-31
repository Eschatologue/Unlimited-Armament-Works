package UAW.entities.effects;

import UAW.audiovisual.UAWPal;
import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.math.Rand;
import arc.math.geom.Vec2;
import mindustry.entities.Effect;

import static arc.graphics.g2d.Draw.color;
import static arc.math.Angles.randLenVectors;

public class ShootSmokeEffect extends Effect {
	public static final Rand rand = new Rand();
	public static final Vec2 v = new Vec2();

	public Color color = UAWPal.graphiteFront;
	public float life = 18, baseSize = 0.2f, smokeSize = 2.4f, spreadRange = 23, spreadCone = 20;
	public int amount = 9;

	public boolean split = false;
	public float splitAngle = 85;

	public int shape = 1;

	public ShootSmokeEffect() {
		lifetime = life;

		renderer = e -> {
			color(color, Color.lightGray, Color.gray, e.fin());

			randLenVectors(e.id, amount, e.finpow() * spreadRange, !split ? e.rotation : e.rotation + splitAngle, spreadCone, (x, y) -> {
				Fill.circle(e.x + x, e.y + y, e.fout() * smokeSize + baseSize);

				switch (shape) {
					case 1 -> Fill.circle(e.x + x, e.y + y, e.fout() * smokeSize + baseSize);
					case 2 -> Fill.square(e.x + x, e.y + y, e.fout() * smokeSize + baseSize, 45);
				}


			});
			if (split) {
				randLenVectors(e.id, amount, e.finpow() * spreadRange, e.rotation - splitAngle, spreadCone, (x, y) -> {
					Fill.circle(e.x + x, e.y + y, e.fout() * smokeSize + baseSize);
				});
			}
		};
	}

	@Override
	public void init() {
		clip = Math.max(clip, Math.max(baseSize, smokeSize) + Math.max(baseSize, smokeSize));
	}
}

