package UAW.audiovisual.effects;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.math.Rand;
import arc.math.geom.Vec2;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.color;
import static arc.math.Angles.randLenVectors;

public class ShootSmokeEffect extends Effect {
	public static final Rand rand = new Rand();
	public static final Vec2 v = new Vec2();

	public Color color = Pal.lightishOrange;
	public float life = 18, baseSize = 0.2f, smokeSize = 2.4f, spreadRange = 23, spreadCone = 20;
	public int amount = 9;

	public boolean split = false;
	public float splitAngle = 85;

	public int shapeVariant = 1;

	@Override
	public void render(EffectContainer e) {
		color(color, Color.lightGray, Color.gray, e.fin());

		randLenVectors(e.id, amount, e.finpow() * spreadRange, !split ? e.rotation : e.rotation + splitAngle, spreadCone, (x, y) -> {
			Fill.circle(e.x + x, e.y + y, e.fout() * smokeSize + baseSize);

			switch (shapeVariant) {
				case 1 -> Fill.circle(e.x + x, e.y + y, e.fout() * smokeSize + baseSize);
				case 2 -> Fill.square(e.x + x, e.y + y, e.fout() * smokeSize + baseSize, 45);
			}

		});

		if (split) {
			randLenVectors(e.id, amount, e.finpow() * spreadRange, e.rotation - splitAngle, spreadCone, (x, y) -> {
				Fill.circle(e.x + x, e.y + y, e.fout() * smokeSize + baseSize);

				switch (shapeVariant) {
					case 1 -> Fill.circle(e.x + x, e.y + y, e.fout() * smokeSize + baseSize);
					case 2 -> Fill.square(e.x + x, e.y + y, e.fout() * smokeSize + baseSize, 45);
				}

			});
		}
	}

	@Override
	public void init() {
		lifetime = life;
		clip = Math.max(clip, Math.max(baseSize, smokeSize) + Math.max(baseSize, smokeSize));
	}
}

