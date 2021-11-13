package UAW.graphics;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Vec2;
import arc.util.*;
import mindustry.entities.Effect;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.randLenVectors;

public class UAWFxDynamic {
	private static final Rand rand = new Rand();
	private static final Vec2 v = new Vec2();

	public static Effect instShoot(float size, Color color) {
		return new Effect(24.0F, size * 8, (e) -> {
			e.scaled(10.0F, (b) -> {
				Draw.color(Color.white, color, b.fin());
				Lines.stroke(b.fout() * 3.0F + 0.2F);
				Lines.circle(b.x, b.y, b.fin() * size);
			});
			Draw.color(color);

			for (int i : Mathf.signs) {
				Drawf.tri(e.x, e.y, size / 4 * e.fout(), size, e.rotation + 90.0F * i);
				Drawf.tri(e.x, e.y, size / 4 * e.fout(), size, e.rotation + 20.0F * i);
			}
		});
	}

	public static Effect instTrail(Color frontColor, Color backColor) {
		return new Effect(30, e -> {
			for (int i = 0; i < 2; i++) {
				color(i == 0 ? backColor : frontColor);

				float m = i == 0 ? 1f : 0.5f;

				float rot = e.rotation + 180f;
				float w = 15f * e.fout() * m;
				Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot);
				Drawf.tri(e.x, e.y, w, 10f * m, rot + 180f);
			}

			Drawf.light(e.x, e.y, 60f, backColor, 0.6f * e.fout());
		});
	}

	public static Effect instHit(Color frontColor, Color backColor) {
		return new Effect(20f, 200f, e -> {
			color(backColor);
			for (int i = 0; i < 2; i++) {
				color(i == 0 ? backColor : frontColor);

				float m = i == 0 ? 1f : 0.5f;

				for (int j = 0; j < 5; j++) {
					float rot = e.rotation + Mathf.randomSeedRange(e.id + j, 50f);
					float w = 23f * e.fout() * m;
					Drawf.tri(e.x, e.y, w, (80f + Mathf.randomSeedRange(e.id + j, 40f)) * m, rot);
					Drawf.tri(e.x, e.y, w, 20f * m, rot + 180f);
				}
			}
			e.scaled(10f, c -> {
				color(frontColor);
				stroke(c.fout() * 2f + 0.2f);
				Lines.circle(e.x, e.y, c.fin() * 30f);
			});
			e.scaled(12f, c -> {
				color(backColor);
				randLenVectors(e.id, 25, 5f + e.fin() * 80f, e.rotation, 60f, (x, y) -> {
					Fill.square(e.x + x, e.y + y, c.fout() * 3f, 45f);
				});
			});
		});
	}

	public static Effect railShoot(Color color, float size) {
		return new Effect(24.0F, size * 8, (e) -> {
			e.scaled(10.0F, (b) -> {
				Draw.color(Color.white, Color.lightGray, b.fin());
				Lines.stroke(b.fout() * 3.0F + 0.2F);
				Lines.circle(b.x, b.y, b.fin() * size);
			});
			Draw.color(color);
			for (int i : Mathf.signs) {
				Drawf.tri(e.x, e.y, (size * 0.26f) * e.fout(), (size * 1.7f), e.rotation + 90f * i);
			}
		});
	}

	public static Effect railTrail(Color color) {
		return new Effect(16f, e -> {
			color(color);
			for (int i : Mathf.signs) {
				Drawf.tri(e.x, e.y, 10f * e.fout(), 24f, e.rotation + 90 + 90f * i);
			}
			Drawf.light(e.x, e.y, 60f * e.fout(), Pal.orangeSpark, 0.5f);
		});
	}

	public static Effect railHit(Color color) {
		return new Effect(18f, 200f, e -> {
			color(color);
			for (int i : Mathf.signs) {
				Drawf.tri(e.x, e.y, 10f * e.fout(), 60f, e.rotation + 140f * i);
			}
		});
	}

	public static Effect shootMassiveSmoke(float size, float lifetime, Color color) {
		return new Effect(lifetime, e -> {
			color(color, Color.lightGray, Color.gray, e.fin());

			randLenVectors(e.id, 12, e.finpow() * 23f, e.rotation, size * 8, (x, y) ->
				Fill.circle(e.x + x, e.y + y, e.fout() * size));
		});
	}

	public static Effect statusFieldApply(Color frontColor, Color backColor, float size) {
		return new Effect(50, e -> {
			color(frontColor, backColor, e.fin());
			stroke(e.fout() * 5f);
			Lines.circle(e.x, e.y, size + e.fin() * 4f);
			int points = 6;
			float offset = Mathf.randomSeed(e.id, 360f);
			for (int i = 0; i < points; i++) {
				float angle = (i * 360f / points + (Time.time * 3)) + (offset + 4);
				float rx = Angles.trnsx(angle, size), ry = Angles.trnsy(angle, size);
				Drawf.tri(
					e.x + rx, e.y + ry, 48f, 28f * e.fout(), angle);
			}
			Fill.light(e.x, e.y, circleVertices(size / 2), size, Color.white.cpy().a(0f), Tmp.c4.set(backColor).a(e.fout()));
			Drawf.light(e.x, e.y, size * 1.6f, backColor, e.fout());
		});
	}

	public static Effect crossBlast(float size, Color color) {
		float length = size * 1.7f;
		float width = size / 13.3f;
		return new Effect(20f, 100f, e -> {
			color(color);
			stroke(e.fout() * 4f);
			Lines.circle(e.x, e.y, 4f + e.finpow() * size);

			color(color);
			for (int i = 0; i < 4; i++) {
				Drawf.tri(e.x, e.y, (width * 2), (length * 1.5f) * e.fout(), i * 90 + 45);
			}

			color();
			for (int i = 0; i < 4; i++) {
				Drawf.tri(e.x, e.y, width, (length / 2.7f) * e.fout(), i * 90 + 45);
			}
		});
	}

	public static Effect crossBombBlast(float size, Color color) {
		return new Effect(50f, 100, e -> {
			color(color);
			stroke(e.fout() * 2f);
			Lines.circle(e.x, e.y, 4f + e.finpow() * size);

			color(Color.gray);

			randLenVectors(e.id, 15, 1.5f + size * e.finpow(), (x, y) -> Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 0.5f));

			color(color);
			for (int i = 0; i < 4; i++) {
				Drawf.tri(e.x, e.y, size / 12, size * e.fout(), i * 90);
			}

			color();
			for (int i = 0; i < 4; i++) {
				Drawf.tri(e.x, e.y, size / 24, (size / 3) * e.fout(), i * 90);
			}
		});
	}

	public static Effect dynamicExplosion(float size, Color color) {
		return new Effect((size * 10), 500f, b -> {
			float realSize = size / 15;
			float baseLifetime = 26f + realSize * 15f;
			b.lifetime = 39f + realSize * 30f;

			color(color);
			alpha(0.9f);
			for (int i = 0; i < 4; i++) {
				rand.setSeed(b.id * 2L + i);
				float lenScl = rand.random(0.4f, 1f);
				int fi = i;
				b.scaled(b.lifetime * lenScl, e -> {
					randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (3f * realSize), 14f * realSize, (x, y, in, out) -> {
						float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
						Fill.circle(e.x + x, e.y + y, fout * ((2f + realSize) * 1.8f));
					});
				});
			}

			b.scaled(baseLifetime, e -> {
				e.scaled(5 + realSize * 2.5f, i -> {
					stroke((3.1f + realSize / 2) * i.fout());
					Lines.circle(e.x, e.y, (3f + i.fin() * 14f) * realSize);
					Drawf.light(e.x, e.y, i.fin() * 14f * 2f * realSize, Color.white, 0.9f * e.fout());
				});

				color(Pal.lighterOrange, Pal.lightOrange, Color.gray, e.fin());
				stroke((1.7f * e.fout()) * (1f + (realSize - 1f) / 2f));

				Draw.z(Layer.effect + 0.001f);
				randLenVectors(e.id + 1, e.finpow() + 0.001f, (int) (9 * (realSize / 2)), 40f * realSize, (x, y, in, out) -> {
					lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + out * 4 * (3f + realSize));
					Drawf.light(e.x + x, e.y + y, (out * 4 * (3f + realSize)) * 3.5f, Draw.getColor(), 0.8f);
				});
			});
		});
	}

	public static Effect hugeExplosion(float size, Color color) {
		return new Effect(120, 450f, e -> {
			float intensity = size / 19f;
			float smokeSize = e.fout() * size / 6;

			color(Color.gray);
			alpha(0.7f);
			randLenVectors(e.id, 40, e.finpow() * 160f, (x, y) -> {
				color(color);
				Fill.circle(e.x + x, e.y + y, smokeSize / 1.5f);
			});
			randLenVectors(e.id, 35, e.finpow() * e.lifetime, (x, y) -> {
				color(Pal.lighterOrange, Pal.darkishGray, Color.gray, e.fin());
				Fill.circle(e.x + x, e.y + y, smokeSize * 1.5f);
			});
			Draw.color();
			e.scaled(5 + intensity * 2f, i -> {
				stroke((3.1f + intensity / 5f) * i.fout());
				Lines.circle(e.x, e.y, (3f + i.fin() * 14f) * intensity);
				Drawf.light(e.x, e.y, i.fin() * 14f * 2f * intensity, Color.lightGray, 0.3f * e.fout());
			});

			color(Pal.bulletYellowBack, Color.gray, e.fin());
			stroke((2f * e.fout()));

			Draw.z(Layer.effect + 0.001f);
			randLenVectors(e.id + 1, e.finpow() + 0.001f, (int) (8 * intensity), 28f * intensity, (x, y, in, out) -> {
				lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + out * 4 * (4f + intensity));
				Drawf.light(e.x + x, e.y + y, (out * 4 * (5f + intensity)) * 3.5f, Draw.getColor(), 0.8f);
			});
		});
	}

	public static Effect statusHit(float lifetime, Color color) {
		return new Effect(lifetime, e -> {
			color(color);
			randLenVectors(e.id, 6, 1.5f + e.fin() * 2.5f, (x, y) ->
				Fill.circle(e.x + x, e.y + y, e.fout() * 1.5f));
		});
	}

	public static Effect circleSplash(float size, float lifetime, Color lightColor, Color darkColor, Color splashColor) {
		return new Effect(lifetime, e -> {
			color(lightColor, darkColor, e.fin());
			stroke(e.fout() * 4f);
			Lines.circle(e.x, e.y, size + e.fin() * 3f);
			Fill.light(e.x, e.y, circleVertices(size / 2), size, Color.white.cpy().a(0f), Tmp.c4.set(splashColor).a(e.fout()));
		});
	}

	public static Effect thermalExplosion(Color frontColor, Color backColor) {
		return new Effect(22, e -> {
			color(frontColor);

			e.scaled(6, i -> {
				stroke(3f * i.fout());
				Lines.circle(e.x, e.y, 3f + i.fin() * 15f);
			});

			color(Color.lightGray);

			randLenVectors(e.id, 5, 2f + 23f * e.finpow(), (x, y) ->
				Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 0.5f));

			color(backColor);
			stroke(e.fout());

			randLenVectors(e.id + 1, 4, 1f + 23f * e.finpow(), (x, y) ->
				lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 0.7f + e.fout() * 3f));

			Drawf.light(e.x, e.y, 45f, backColor, 0.8f * e.fout());
		});
	}

	public static Effect burstSmelt(float height, float width, float offset, int rotation, Color frontColor, Color backColor) {
		//Inspired, modified and translated to java from 'FlinTyX/DiverseTech'
		return new Effect(24f, e -> {
			e.scaled(10f, b -> {
				color(Color.white, frontColor, b.fin());
				stroke(b.fout() * 3f + 0.2f);
				Lines.circle(b.x, b.y, b.fin() * 50f);
			});

			color(backColor);

			for (int i = 0; i < 4; i++) {
				float angle = (i * 360f / 4);
				Drawf.tri(e.x, e.y, 13f * e.fout(), 85f, angle);
			}

			Drawf.light(e.x, e.y, 180f, Pal.bulletYellowBack, 0.9f * e.fout());
		});
	}

	public static Effect smokeCloud(Color color) {
		return new Effect(80f, e -> {
			color(color);
			randLenVectors(e.id, e.fin(), 12, 15f, (x, y, fin, fout) -> {
				Fill.circle(e.x + x, e.y + y, 6f * fout);
			});
		});
	}

	public static Effect effectCloud(Color color) {
		return new Effect(140, 400f, e -> {
			randLenVectors(e.id, 20, e.finpow() * 160f, (x, y) -> {
				float size = e.fout() * 15f;
				color(color, Color.lightGray, e.fin());
				Fill.circle(e.x + x, e.y + y, size / 2f);
			});
		});
	}
}