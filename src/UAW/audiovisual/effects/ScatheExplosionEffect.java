package UAW.audiovisual.effects;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Tmp;
import mindustry.entities.Effect;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;

public class ScatheExplosionEffect extends Effect {
	public static final Rand rand = new Rand();

	public Color color = Pal.missileYellow, color1 = Pal.missileYellowBack;
	public float size = 60, life = 60f;
	public float strokeMin = 0.5f, stroke = 5f;
	public float slashAmount = -1, slashWidth = 40f, slashLength = 30f;

	@Override
	public void init() {
		super.init();
		lifetime = life;
		clip = size * 1.5f;
	}

	@Override
	public void render(EffectContainer e) {
		explosion(e);
		light(e);
	}

	public void explosion(EffectContainer e) {
		color(color);
		stroke(strokeMin + e.fout() * stroke);
		float circleRad = 6f + e.finpow() * size;
		Lines.circle(e.x, e.y, circleRad);

		rand.setSeed(e.id);
		for (int i = 0; i < (slashAmount > 0 ? slashAmount : ((int) (size * 0.26f))); i++) {
			float angle = rand.random(360f);
			float lenRand = rand.random(0.5f, 1f);
			Tmp.v1.trns(angle, circleRad);

			for (int s : Mathf.signs) {
				Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * slashWidth, e.fout() * slashLength * lenRand + 6f, angle + 90f + s * 90f);
			}
		}
	}

	public void light(EffectContainer e) {
		float circleRad = 6f + e.finpow() * size;
		Draw.z(Layer.debris);
		Fill.light(e.x, e.y, Lines.circleVertices(circleRad / 2), circleRad, Color.white.cpy().a(0f), Tmp.c4.set(color).a(e.fout()));
		Draw.reset();
		Drawf.light(e.x, e.y, circleRad * 1.6f, color1, e.fout());
	}
}
