package UAW.audiovisual.effects;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.color;
import static arc.math.Angles.randLenVectors;

public class StatusHitEffect extends Effect {
	public Color color = Pal.bulletYellow;
	@Nullable
	public Color color1;
	public float alpha = 1f;
	public float sizeStart = 0.2f, sizeEnd = 1.5f, life = 35, spreadBase = 2f, spreadRad = 7f;
	public int amount = 3;

	public float particleRotSpeed = 0;

	/**
	 * 1 = circle
	 * <p>
	 * 2 = square
	 */
	public int shapeVariant = 1;

	@Override
	public void init() {
		lifetime = life;
	}

	@Override
	public void render(EffectContainer e) {
		Draw.alpha(sizeStart > sizeEnd ? alpha * e.fout() : alpha);
		color(color, color1 == null ? color : color1, e.fout());

		randLenVectors(e.id, amount, spreadBase + e.fin() * spreadRad, (x, y) -> {
			switch (shapeVariant) {
				case 1 -> Fill.circle(e.x + x, e.y + y, sizeStart + e.fslope() * sizeEnd);
				case 2 -> Fill.square(e.x + x, e.y + y, sizeStart + e.fslope() * sizeEnd, particleRotSpeed != 0 ? particleRotSpeed * Time.time : 45);
			}
		});
		Draw.reset();
	}
}
