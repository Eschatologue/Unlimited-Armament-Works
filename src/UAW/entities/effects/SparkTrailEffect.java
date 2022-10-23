package UAW.entities.effects;

import UAW.audiovisual.UAWPal;
import arc.graphics.Color;
import arc.math.Rand;
import arc.math.geom.Vec2;
import mindustry.entities.Effect;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.*;

public class SparkTrailEffect extends Effect {
	public static final Rand rand = new Rand();
	public static final Vec2 v = new Vec2();

	public Color color = UAWPal.graphiteFront;
	public float life = 13, baseSize = 1.5f, minSize = 2f, maxSize = 7f;
	public int amount = 2;

	public boolean line = true, circle = false, square = false;

	public SparkTrailEffect() {
		lifetime = life;
		renderer = e -> {
			color(Color.white, e.color, e.fin());
			stroke(0.6f + e.fout() * 1.7f);
			rand.setSeed(e.id);

			for (int i = 0; i < amount; i++) {
				float rot = e.rotation + rand.range(15f) + 180f;
				v.trns(rot, rand.random(e.fin() * 27f));
				lineAngle(e.x + v.x, e.y + v.y, rot, e.fout() * rand.random(minSize, maxSize) + baseSize);
			}
		};
	}
}
