package UAW.audiovisual.effects;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.*;
import static arc.math.Angles.randLenVectors;

public class SparkEffect extends Effect {
	public Color color = Pal.bulletYellow, color1 = Color.lightGray;
	public float life = 90;
	public float size = 3, spreadBase = 5, spreadRad = 22;
	public int amount = 5;

	public int shapeVariant;

	public int impactVariant;

	@Override
	public void init() {
		clip = spreadRad * 5;
		lifetime = life;
	}

	@Override
	public void render(EffectContainer e) {
		color(color, color1, e.fin());

		randLenVectors(e.id, amount > 0 ? amount : (int) (size * 1.6f), spreadBase + e.finpow() * spreadRad, (x, y) -> {
			switch (shapeVariant) {
				default -> Fill.square(e.x + x, e.y + y, e.fout() * size, 45);
				case 1 -> Fill.circle(e.x + x, e.y + y, e.fout() * size);
				case 2 -> Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * size);
			}
		});
	}

}
