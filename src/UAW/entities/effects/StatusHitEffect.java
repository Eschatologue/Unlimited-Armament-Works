package UAW.entities.effects;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.color;
import static arc.math.Angles.randLenVectors;

public class StatusHitEffect extends Effect {
	public Color color = Pal.bulletYellow;
	public float alpha = 1f;
	public float sizeMin = 0.2f, sizeMax = 1.5f, life = 35, spreadBase = 2f, spreadRad = 7f;
	public int amount = 3;

	public boolean circle = true, square = false;

	/** Based on {@link mindustry.content.Fx#freezing} */
	public StatusHitEffect() {
		clip = spreadRad * 5;
		lifetime = life;
		renderer = e -> {
			color(color);

			randLenVectors(e.id, amount, spreadBase + e.fin() * spreadRad, (x, y) -> {
				if (circle) Fill.circle(e.x + x, e.y + y, sizeMin + e.fslope() * sizeMax);
				if (square) Fill.square(e.x + x, e.y + y, sizeMin + e.fslope() * sizeMax, 45);
			});
		};
	}
}
