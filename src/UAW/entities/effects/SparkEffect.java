package UAW.entities.effects;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.*;
import static arc.math.Angles.randLenVectors;

public class SparkEffect extends Effect {
	public Color sparkColor = Pal.bulletYellow, lerpColor = Color.lightGray;
	public float alpha = 1f;
	public float size = 3, life = 90, spreadBase = 5, spreadRad = 22;
	public int amount = 5;

	public boolean circle = false, square = true;

	public SparkEffect() {
		clip = spreadRad * 5;
		lifetime = life;

		renderer = e -> {
			alpha(alpha);
			color(sparkColor, lerpColor, e.fin());
			randLenVectors(e.id, amount > 0 ? amount : (int) (size * 1.6f), spreadBase + e.finpow() * spreadRad, (x, y) -> {
				if (circle) Fill.circle(e.x + x, e.y + y, e.fout() * size);
				if (square) Fill.square(e.x + x, e.y + y, e.fout() * size, 45);
			});
		};

	}
}
