package UAW.graphics;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Vec2;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.randLenVectors;

public class UAWFxD {
	private static final Rand rand = new Rand();
	private static final Vec2 v = new Vec2();

	public static Effect instShoot(float size, Color color) {
		return new Effect(24.0F, size * 8, (e) -> {
			e.scaled(12.0F, (b) -> {
				Draw.color(Color.white, color, b.fin());
				Lines.stroke(b.fout() * 3.0F + 0.4F);
				Lines.circle(b.x, b.y, b.fin() * size);
			});
			Draw.color(color);

			for (int i : Mathf.signs) {
				Drawf.tri(e.x, e.y, size / 2.3f * e.fout(), size, e.rotation + 90 * i);
				Drawf.tri(e.x, e.y, size / 3.6f * e.fout(), size, e.rotation + 20 * i);
			}
		});
	}

	public static Effect railShoot(float size, Color color) {
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

	public static Effect railTrail(float width, Color color) {
		return new Effect(width * 1.8f, e -> {
			color(color);
			for (int i : Mathf.signs) {
				Drawf.tri(e.x, e.y, width * e.fout(), (width * 2.4f), e.rotation + 90 + 90f * i);
			}
			Drawf.light(e.x, e.y, (width * 6) * e.fout(), Pal.orangeSpark, 0.5f);
		});
	}

	public static Effect railHit(float size, Color color) {
		return new Effect(20f, 200f, e -> {
			color(color);
			for (int i : Mathf.signs) {
				Drawf.tri(e.x, e.y, size * e.fout(), size * 6, e.rotation + 140f * i);
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

	public static Effect statusFieldApply(float size, Color frontColor, Color backColor) {
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

	/** Explosion in an X pattern */
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

	/**
	 * Vanilla dynamicExplosion
	 *
	 * @param size
	 * 	How big is the explosion
	 */
	public static Effect dynamicExplosion(float size) {
		return new Effect(size * 10, 500f, b -> {
			float intensity = size / 19;
			float baseLifetime = 26f + intensity * 15f;
			b.lifetime = 43f + intensity * 35f;

			color(Color.gray);
			alpha(0.9f);
			for (int i = 0; i < 4; i++) {
				rand.setSeed(b.id * 2L + i);
				float lenScl = rand.random(0.4f, 1f);
				int fi = i;
				b.scaled(b.lifetime * lenScl, e ->
					randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (3f * intensity), 14f * intensity, (x, y, in, out) -> {
						float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
						Fill.circle(e.x + x, e.y + y, fout * ((2f + intensity) * 1.8f));
					}));
			}

			b.scaled((baseLifetime / 1.5f), e -> {
				e.scaled(5 + intensity * 2.5f, i -> {
					stroke((3.1f + intensity / 5f) * i.fout());
					Lines.circle(e.x, e.y, (3f + i.fin() * 14f) * intensity);
					Drawf.light(e.x, e.y, i.fin() * 14f * 2f * intensity, Color.white, 0.9f * e.fout());
				});

				color(Pal.lighterOrange, Pal.lightOrange, Color.gray, e.fin());
				stroke((1.7f * e.fout()) * (1f + (intensity - 1f) / 2f));

				Draw.z(Layer.effect + 0.001f);
				randLenVectors(e.id + 1, e.finpow() + 0.001f, (int) (6 * intensity), 35f * intensity, (x, y, in, out) -> {
					lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + out * 4 * (3f + intensity));
					Drawf.light(e.x + x, e.y + y, (out * 4 * (3f + intensity)) * 3.5f, Draw.getColor(), 0.8f);
				});
			});
		});
	}

	/** Dynamic Explosion with circle spark instead of lines, used with effects */
	public static Effect dynamicExplosion2(float size, Color frontColor, Color backColor) {
		return new Effect(size * 10, 500f, b -> {
			float intensity = size / 19;
			float baseLifetime = 26f + intensity * 15f;
			b.lifetime = 43f + intensity * 35f;

			color(Color.gray);
			alpha(0.9f);
			for (int i = 0; i < 4; i++) {
				rand.setSeed(b.id * 2L + i);
				float lenScl = rand.random(0.4f, 1f);
				int fi = i;
				b.scaled(b.lifetime * lenScl, e ->
					randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (3f * intensity), 14f * intensity, (x, y, in, out) -> {
						float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
						Fill.circle(e.x + x, e.y + y, fout * ((2f + intensity) * 1.8f));
					}));
			}

			b.scaled((baseLifetime / 1.5f), e -> {
				e.scaled(5 + intensity * 2.5f, i -> {
					Draw.color(frontColor);
					stroke((3f + intensity / 4f) * i.fout());
					Lines.circle(e.x, e.y, (3f + i.fin() * 14f) * intensity);
					Drawf.light(e.x, e.y, i.fin() * 14f * 2f * intensity, Color.white, 0.9f * e.fout());
				});

				Draw.z(Layer.bullet + 0.001f);
				randLenVectors(e.id + 1, e.finpow() + 0.001f, (int) (4 * intensity), 20f * intensity, (x, y, in, out) -> {
					float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
					Draw.color(frontColor);
					Fill.circle(e.x + x, e.y + y, fout * ((2f + intensity) * 1.3f));
				});

				stroke((1.7f * e.fout()) * (1f + (intensity - 1f) / 2f));
				Draw.z(Layer.effect + 0.001f);
				randLenVectors(e.id + 1, e.finpow() + 0.001f, (int) (6 * intensity), 35f * intensity, (x, y, in, out) -> {
					color(frontColor, backColor, Color.gray, e.fin());
					lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + out * 4 * (3f + intensity));
					Drawf.light(e.x + x, e.y + y, (out * 4 * (3f + intensity)) * 3.5f, Draw.getColor(), 0.8f);
				});
			});
		});
	}

	/** Navanax EMP Blast */
	public static Effect empBlast(float size, int point, Color frontColor) {
		float length = (size * 1.7f) / 2;
		float width = (size / 13.3f) / 2;
		return new Effect(50f, 100f, e -> {
			e.scaled(7f, b -> {
				color(frontColor, b.fout());
				Fill.circle(e.x, e.y, size);
			});

			color(frontColor);
			stroke(e.fout() * 3f);
			Lines.circle(e.x, e.y, size);

			float offset = Mathf.randomSeed(e.id, 360f);
			for (int i = 0; i < point; i++) {
				float angle = i * 360f / point + offset;
				Drawf.tri(
					e.x + Angles.trnsx(angle, size),
					e.y + Angles.trnsy(angle, size),
					80 / 13.33f,
					size / 1.6f * e.fout(), angle
				);
			}

			for (int i = 0; i < 4; i++) {
				Drawf.tri(e.x, e.y,
					width * 2,
					length * 1.5f * e.fout(),
					i * e.rotation
				);
			}

			Fill.circle(e.x, e.y, 12f * e.fout());
			color();
			Fill.circle(e.x, e.y, 6f * e.fout());
			Drawf.light(e.x, e.y, size * 1.6f, frontColor, e.fout());
			Fx.chainLightning.at(e.x, e.y, frontColor);
		});
	}

	/** Used with repeating aftershock */
	public static Effect circleSplash(float size, float lifetime, Color lightColor, Color darkColor, Color splashColor) {
		return new Effect(lifetime, size * 2f, e -> {
			color(lightColor, darkColor, e.fin());
			stroke(e.fout() * 4f);
			Lines.circle(e.x, e.y, size + e.fin() * 3f);
			Fill.light(e.x, e.y, circleVertices(size / 2), size, Color.white.cpy().a(0f), Tmp.c4.set(splashColor).a(e.fout()));
		});
	}

	/**
	 * Based on Fx.BlastExplosion
	 *
	 * @param frontColor
	 * 	The lighter color | Default : Pal.MissileYellow
	 * @param backColor
	 * 	The darker color | Default : Pal.MissileYellowBack
	 */
	public static Effect thermalExplosion(Color frontColor, Color backColor) {
		return new Effect(23, e -> {
			color(frontColor);

			e.scaled(6, i -> {
				stroke(3f * i.fout());
				Lines.circle(e.x, e.y, 3f + i.fin() * 15f);
			});

			color(Color.gray);

			randLenVectors(e.id, 5, 2f + 23f * e.finpow(), (x, y) ->
				Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 0.5f));

			color(backColor);
			stroke(e.fout());

			randLenVectors(e.id + 1, 4, 1f + 23f * e.finpow(), (x, y) ->
				lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 0.7f + e.fout() * 3f));

			Drawf.light(e.x, e.y, 45f, backColor, 0.8f * e.fout());
		});
	}

	/** Used if crafter has explosion effects */
	public static Effect burstSmelt(float size, Color frontColor, Color backColor) {
		return new Effect(35, 100f, e -> {
			float length = e.finpow() * (size * 2.5f);
			float width = e.fout() * (size / 8f);
			color(frontColor, backColor, Color.gray, e.fin());
			stroke(e.fout() * 4f);
			Lines.circle(e.x, e.y, 4f + e.finpow() * size);

			color(backColor);
			for (int i = 0; i < 4; i++) {
				Drawf.tri(e.x, e.y, (width * 2), length, i * 90);
			}

			color(frontColor);
			for (int i = 0; i < 4; i++) {
				Drawf.tri(e.x, e.y, width, length, i * 90);
			}
			randLenVectors(e.id, 12, e.finpow() * e.lifetime * 1.5f, (x, y) -> {
				color(backColor, Color.gray, e.fin());
				Fill.square(e.x + x, e.y + y, (e.fout() * 15f) / 2f, 45);
			});
		});
	}

	/**
	 * Trail used by Cruise Missile
	 *
	 * @param smokeSize
	 * 	how big is the circle smoke effect. Default 0.45.
	 * @param trailColor
	 * 	the color of the trail before it fades into grey
	 */
	public static Effect cruiseMissileTrail(float smokeSize, Color trailColor) {
		return new Effect(30f, 80f, e -> {
			color(trailColor, Color.lightGray, Color.valueOf("ddcece"), e.fin() * e.fin());

			randLenVectors(e.id, 8, 2f + e.finpow() * 36f, e.rotation + 180, 17f, (x, y) ->
				Fill.circle(e.x + x, e.y + y, smokeSize + e.fout() * 2f));
		});
	}

	public static Effect effectCloud(Color color) {
		return new Effect(140, 400f, e ->
			randLenVectors(e.id, 22, e.finpow() * 160f, (x, y) -> {
				float size = e.fout() * 15f;
				color(color, Color.lightGray, e.fin());
				Fill.circle(e.x + x, e.y + y, size / 2f);
			}));
	}

	/**
	 * General use smoke cloud
	 * <p> Based on Fx.SmokeCloud </p>
	 *
	 * @param lifetime
	 * 	How long the effect last, also adjusts the amount of smoke balls and its burst length
	 * @param layer
	 * 	In what layer that this effect occurs
	 * @param color
	 * 	What Color is the smoke
	 */
	public static Effect smokeCloud(float lifetime, float layer, Color color) {
		return new Effect(lifetime, lifetime * 2.85f, e ->
			randLenVectors(e.id, e.fin(), (int) (lifetime / 2), lifetime / 2, (x, y, fin, fout) -> {
				color(color);
				alpha((0.5f - Math.abs(fin - 0.5f)) * 2f);
				Fill.circle(e.x + x, e.y + y, 0.5f + fout * 4f);
			})).layer(layer);
	}

	static float smokeSizeLfMult = 11f;

	/**
	 * Steam Cloud used for steam effects on various buildings | 1st Variation
	 *
	 * @param smokeSize
	 * 	How big is the smoke 'puff', based on tilesize, also adjusts the amount of 'puff'
	 * @param layer
	 * 	The layer on where does the effect occurs
	 * @param color
	 * 	The color of the smoke puff
	 */
	public static Effect steamCloud(float smokeSize, float layer, Color color) {
		return new Effect(smokeSize * smokeSizeLfMult, smokeSize * smokeSizeLfMult * 2.85f, e -> {
			color(Tmp.c1.set(color).mul(1.1f));
			randLenVectors(e.id, (int) (6 * smokeSize), 12f * e.finpow() * smokeSize / 8, (x, y) -> {
				Draw.alpha(0.5f);
				Fill.circle(e.x + x, e.y + y, e.fout() * 3f + 0.1f);
				Draw.reset();
			});
		}).layer(layer);
	}

	/**
	 * Steam Cloud used for steam effects on various buildings | 2nd Variation
	 *
	 * @param smokeSize
	 * 	How big is the smoke 'puff', based on tilesize, also adjusts the amount of 'puff'
	 * @param layer
	 * 	The layer on where does the effect occurs
	 */
	public static Effect steamCloud(float smokeSize, float layer) {
		return new Effect(smokeSize * smokeSizeLfMult, smokeSize * smokeSizeLfMult * 2.85f, e -> {
			color(Tmp.c1.set(Pal.lightishGray).mul(1.1f));
			randLenVectors(e.id, (int) (6 * smokeSize), 12f * e.finpow() * smokeSize / 8, (x, y) -> {
				Draw.alpha(0.5f);
				Fill.circle(e.x + x, e.y + y, e.fout() * 3f + 0.1f);
				Draw.reset();
			});
		}).layer(layer);
	}

	/**
	 * Steam Cloud used for steam effects on various buildings | 3rd Variation
	 *
	 * @param smokeSize
	 * 	How big is the smoke 'puff', based on tilesize, also adjusts the amount of 'puff'
	 */
	public static Effect steamCloud(float smokeSize) {
		return new Effect(smokeSize * smokeSizeLfMult, smokeSize * smokeSizeLfMult * 2.85f, e -> {
			color(Tmp.c1.set(Pal.lightishGray).mul(1.1f));
			randLenVectors(e.id, (int) (6 * smokeSize), 12f * e.finpow() * smokeSize / 8, (x, y) -> {
				Draw.alpha(0.5f);
				Fill.circle(e.x + x, e.y + y, e.fout() * 3f + 0.1f);
				Draw.reset();
			});
		}).layer(Layer.effect);
	}
}