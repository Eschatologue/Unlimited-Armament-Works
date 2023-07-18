package UAW.audiovisual.effects;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Vec2;
import arc.util.*;
import mindustry.entities.Effect;

import static arc.graphics.g2d.Draw.*;

public class SmokePuffEffect extends Effect {
	private static final Rand rand = new Rand();
	private static final Vec2 v = new Vec2();

	@Nullable
	public Color color;

	public float life = 50;
	public float alpha = 0.5f, spreadRad = 24, baseSize = 0.5f, maxSize = 4;
	public float squareRot = 45;
	public int amount = 13;

	public int shapeVariant = 1;

	@Override
	public void init() {
		lifetime = life;
	}

	@Override
	public void render(EffectContainer e) {
		color(color == null ? e.color : color);
		rand.setSeed(e.id);
		for (int i = 0; i < amount; i++) {
			float fin = e.fin() / rand.random(0.5f, 1f);
			float fout = 1f - fin;
			float angle = rand.random(360f), len = rand.random(0.5f, 1f);

			if (fin <= 1f) {
				Tmp.v1.trns(angle, fin * spreadRad * len);

				alpha((alpha - Math.abs(fin - alpha)) * 2f);

				switch (shapeVariant) {
					case 1 -> Fill.circle(e.x + Tmp.v1.x, e.y + Tmp.v1.y, baseSize + fout * maxSize);
					case 2 -> Fill.square(e.x + Tmp.v1.x, e.y + Tmp.v1.y, baseSize + fout * maxSize, squareRot);
					case 3 -> Lines.lineAngle(e.x + Tmp.v1.x, e.y + Tmp.v1.y, Mathf.angle(e.x, e.y), baseSize + e.fout() * maxSize);
				}
			}
		}
		Draw.reset();
	}
}
