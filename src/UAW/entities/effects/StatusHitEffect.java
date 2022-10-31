package UAW.entities.effects;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.color;
import static arc.math.Angles.randLenVectors;

public class StatusHitEffect extends Effect {
	public Color color1 = Pal.bulletYellow;
	@Nullable
	public Color color2;
	public float alpha = 1f;
	public float sizeStart = 0.2f, sizeEnd = 1.5f, life = 35, spreadBase = 2f, spreadRad = 7f;
	public int amount = 3;

	public boolean circle = true, square = false;
	public float particleRotSpeed = 0;

	/** Based on {@link mindustry.content.Fx#freezing} */
	public StatusHitEffect() {
		clip = spreadRad * 5;
		lifetime = life;
		renderer = e -> {
			Draw.alpha(sizeStart > sizeEnd ? alpha * e.fout() : alpha);
			color(color1, color2 == null ? color1 : color2, e.fout());

			randLenVectors(e.id, amount, spreadBase + e.fin() * spreadRad, (x, y) -> {
				if (circle)
					Fill.circle(e.x + x, e.y + y, sizeStart + e.fslope() * sizeEnd);
				if (square)
					Fill.square(e.x + x, e.y + y, sizeStart + e.fslope() * sizeEnd, particleRotSpeed != 0 ? particleRotSpeed * Time.time : 45);
			});
		};
	}
}
