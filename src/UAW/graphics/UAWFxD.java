package UAW.graphics;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Vec2;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.graphics.*;

public class UAWFxD {
	private static final Rand rand = new Rand();
	private static final Vec2 v = new Vec2();
	static float smokeSizeLfMult = 12f;

	/**
	 * Based on Fx.instShoot
	 *
	 * @param size
	 * 	The effect flame "Burst" length and width
	 * @param color
	 * 	Flame burst color
	 */
	public static Effect instShoot(float size, Color color) {
		return new Effect(size / 2.6f, size * 8, (e) -> {
			e.scaled(size / 4.12f, (b) -> {
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

	/**
	 * Based on Fx.railShoot
	 *
	 * @param size
	 * 	How big is the effect, also adjust lifetime
	 * @param color
	 * 	The effect color
	 */
	public static Effect railShoot(float size, Color color) {
		return new Effect(size * 0.6f, size * 8, (e) -> {
			e.scaled(size / 1.5f, (b) -> {
				Draw.color(Color.white, Color.lightGray, b.fin());
				Lines.stroke(b.fout() * 3.2f);
				Lines.circle(b.x, b.y, b.fin() * size * 0.8f);
			});
			Draw.color(color);
			for (int i : Mathf.signs) {
				Drawf.tri(e.x, e.y, (size * 0.30f) * e.fout(), (size * 1.5f), e.rotation + 90f * i);
			}
		});
	}

	/**
	 * Based on Fx.railTrail
	 *
	 * @param width
	 * 	How wide is the trail, also adjusts its height, spacing have to be adjusted manually
	 * @param color
	 * 	The color of the trail
	 */
	public static Effect railTrail(float width, Color color) {
		return new Effect(width * 1.8f, e -> {
			Draw.color(color);
			for (int i : Mathf.signs) {
				Drawf.tri(e.x, e.y, width * e.fout(), (width * 2.4f), e.rotation + 90 + 90f * i);
			}
			Drawf.light(e.x, e.y, (width * 6) * e.fout(), color, 0.5f);
		});
	}

	/**
	 * Based on Fx.railHit
	 *
	 * @param size
	 * 	How large is the hit effect, adjusts the width and length of the flame burst
	 * @param color
	 * 	The color of the effect
	 */
	public static Effect railHit(float size, Color color) {
		return new Effect(20f, 200f, e -> {
			Draw.color(color);
			for (int i : Mathf.signs) {
				Drawf.tri(e.x, e.y, size * e.fout(), size * 6, e.rotation + 140f * i);
			}
		});
	}

	/**
	 * Based on Fx.shootBigSmoke
	 *
	 * @param size
	 * 	How big is the smoke, also adjusts its lifetime | default is 2.6f
	 * @param color
	 * 	the color of the smoke, will lerp to lightGray then gray
	 */
	public static Effect shootBigSmoke(float size, Color color) {
		return new Effect(size * 6.5f, e -> {
			Draw.color(color, Color.lightGray, Color.gray, e.fin());

			Angles.randLenVectors(e.id, (int) (size * 3.6f), e.finpow() * 23f, e.rotation, size * 8, (x, y) ->
				Fill.circle(e.x + x, e.y + y, e.fout() * size));
		});
	}

	/**
	 * Based on Fx.hitBulletBig
	 *
	 * @param color
	 * 	The spark color
	 */
	public static Effect hitBulletBig(Color color) {
		return new Effect(15, e -> {
			Draw.color(Color.white, color, e.fin());
			Lines.stroke(0.5f + e.fout() * 1.5f);

			Angles.randLenVectors(e.id, 9, e.finpow() * 30f, e.rotation, 50f, (x, y) -> {
				float ang = Mathf.angle(x, y);
				Lines.lineAngle(e.x + x, e.y + y, ang, e.fout() * 4 + 1.5f);
			});
		});
	}

	/**
	 * Based on Fx.fireHit, that will scale based on particle radius, don't make it too big, or it will lag
	 *
	 * @param particleRadius
	 * 	How big is the particle | Default is 0.2
	 * @param lightColor
	 * 	The lightest color that will be lerped into darkColor
	 * @param darkColor
	 * 	The darkest color that will be lerped from lightColor
	 */
	public static Effect effectHit(float particleRadius, Color lightColor, Color darkColor) {
		return new Effect(particleRadius * 175, e -> {
			Draw.color(lightColor, darkColor, Color.gray, e.fin());
			Angles.randLenVectors(e.id, (int) (particleRadius * 15), particleRadius * 10 + e.fin() * 10f, (x, y) -> {
				Fill.circle(e.x + x, e.y + y, particleRadius + e.fout() * 1.6f);
			});
			Draw.color();
		});
	}

	/**
	 * Trails for Cruise Missiles
	 *
	 * @param trailColor
	 * 	The color of the trail, will be lerped into darker color
	 * @param layer
	 * 	Where does the effect occurs
	 */
	public static Effect cruiseMissileTrail(Color trailColor, float layer) {
		return new Effect(33f, 80f, e -> {
			Draw.color(trailColor, Color.lightGray, Color.valueOf("ddcece"), e.fin() * e.fin());

			Angles.randLenVectors(e.id, 8, 2f + e.finpow() * 36f, e.rotation + 180, 17f, (x, y) ->
				Fill.circle(e.x + x, e.y + y, 0.45f + e.fout() * 2f));
		}).layer(layer);
	}

	/**
	 * Explosion in an X pattern
	 *
	 * @param size
	 * 	How big is the explosion, also adjust lifetime and clipsize
	 */
	public static Effect crossBlast(float size, Color color) {
		float length = size * 1.7f;
		float width = size / 13.3f;
		return new Effect(size * 0.85f, size * 2, e -> {
			Draw.color(color);
			Lines.stroke(e.fout() * 4f);
			Lines.circle(e.x, e.y, 4f + e.finpow() * size);

			Draw.color(color);
			for (int i = 0; i < 4; i++) {
				Drawf.tri(e.x, e.y, (width * 2), (length * 1.6f) * e.fout(), i * 90 + 45);
			}

			Draw.color();
			for (int i = 0; i < 4; i++) {
				Drawf.tri(e.x, e.y, width, (length / 2.8f) * e.fout(), i * 90 + 45);
			}
		});
	}

	/**
	 * Fx.dynamicExplosion
	 *
	 * @param size
	 * 	How big is the explosion
	 */
	public static Effect dynamicExplosion(float size) {
		return new Effect(size * 10, size * 5, b -> {
			float intensity = size / 19;
			float baseLifetime = 26f + intensity * 15f;
			b.lifetime = 43f + intensity * 35f;

			Draw.color(Color.gray);
			Draw.alpha(0.9f);
			for (int i = 0; i < 4; i++) {
				rand.setSeed(b.id * 2L + i);
				float lenScl = rand.random(0.4f, 1f);
				int fi = i;
				b.scaled(b.lifetime * lenScl, e ->
					Angles.randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (3f * intensity), 14f * intensity, (x, y, in, out) -> {
						float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
						Fill.circle(e.x + x, e.y + y, fout * ((2f + intensity) * 1.8f));
					}));
			}

			b.scaled((baseLifetime / 1.5f), e -> {
				e.scaled(5 + intensity * 2.5f, i -> {
					Lines.stroke((3.1f + intensity / 5f) * i.fout());
					Lines.circle(e.x, e.y, (3f + i.fin() * 14f) * intensity);
					Drawf.light(e.x, e.y, i.fin() * 14f * 2f * intensity, Color.white, 0.9f * e.fout());
				});

				Draw.color(Pal.lighterOrange, Pal.lightOrange, Color.gray, e.fin());
				Lines.stroke((1.7f * e.fout()) * (1f + (intensity - 1f) / 2f));

				Draw.z(Layer.effect + 0.001f);
				Angles.randLenVectors(e.id + 1, e.finpow() + 0.001f, (int) (6 * intensity), 35f * intensity, (x, y, in, out) -> {
					Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + out * 4 * (3f + intensity));
					Drawf.light(e.x + x, e.y + y, (out * 4 * (3f + intensity)) * 3.5f, Draw.getColor(), 0.8f);
				});
			});
		});
	}

	/**
	 * Based on Fx.dymamicExplosion, Lines is replaced with circleSparks
	 *
	 * @param size
	 * 	How big is the explosion, can be based on splashDamageRadius, also adjusts lifetime and clip size
	 */
	public static Effect dynamicExplosion(float size, Color frontColor, Color backColor) {
		return new Effect(size * 10, size * 5, b -> {
			float intensity = size / 19;
			float baseLifetime = 26f + intensity * 15f;
			b.lifetime = 43f + intensity * 35f;

			Draw.color(Color.gray);
			Draw.alpha(0.9f);
			for (int i = 0; i < 4; i++) {
				rand.setSeed(b.id * 2L + i);
				float lenScl = rand.random(0.4f, 1f);
				int fi = i;
				b.scaled(b.lifetime * lenScl, e ->
					Angles.randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (3f * intensity), 14f * intensity, (x, y, in, out) -> {
						float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
						Fill.circle(e.x + x, e.y + y, fout * ((2f + intensity) * 1.8f));
					}));
			}

			b.scaled((baseLifetime / 1.5f), e -> {
				e.scaled(5 + intensity * 2.5f, i -> {
					Draw.color(frontColor);
					Lines.stroke((3f + intensity / 4f) * i.fout());
					Lines.circle(e.x, e.y, (3f + i.fin() * 14f) * intensity);
					Drawf.light(e.x, e.y, i.fin() * 14f * 2f * intensity, Color.white, 0.9f * e.fout());
				});

				Draw.z(Layer.bullet + 0.001f);
				Angles.randLenVectors(e.id + 1, e.finpow() + 0.001f, (int) (4 * intensity), 20f * intensity, (x, y, in, out) -> {
					float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
					Draw.color(frontColor);
					Fill.circle(e.x + x, e.y + y, fout * ((2f + intensity) * 1.3f));
				});

				Lines.stroke((1.7f * e.fout()) * (1f + (intensity - 1f) / 2f));
				Draw.z(Layer.effect + 0.001f);
				Angles.randLenVectors(e.id + 1, e.finpow() + 0.001f, (int) (6 * intensity), 35f * intensity, (x, y, in, out) -> {
					Draw.color(frontColor, backColor, Color.gray, e.fin());
					Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + out * 4 * (3f + intensity));
					Drawf.light(e.x + x, e.y + y, (out * 4 * (3f + intensity)) * 3.5f, Draw.getColor(), 0.8f);
				});
			});
		});
	}

	/** Navanax EMP Blast */
	public static Effect empExplosion(float size, int point, Color frontColor) {
		float length = (size * 1.7f) / 2;
		float width = (size / 13.3f) / 2;
		return new Effect(50f, 100f, e -> {
			e.scaled(7f, b -> {
				Draw.color(frontColor, b.fout());
				Fill.circle(e.x, e.y, size);
			});

			Draw.color(frontColor);
			Lines.stroke(e.fout() * 3f);
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
			Draw.color();
			Fill.circle(e.x, e.y, 6f * e.fout());
			Drawf.light(e.x, e.y, size * 1.6f, frontColor, e.fout());
			Fx.chainLightning.at(e.x, e.y, frontColor);
		});
	}

	/**
	 * Used with repeating aftershocks and statusfieldprojectors
	 *
	 * @param size
	 * 	How big is the affected area
	 * @param lifetime
	 * 	How long does the circle last
	 * @param splashColor
	 * 	the color that appears on the bottom of the affected area
	 * @param pointCount
	 * 	How many circling point does the effect has
	 */
	public static Effect circleSplash(float size, float lifetime, Color lightColor, Color darkColor, Color splashColor, int pointCount) {
		return new Effect(lifetime, size * 2f, e -> {
			Draw.color(lightColor, darkColor, e.fin());
			Lines.stroke(e.fout() * 4f);
			Lines.circle(e.x, e.y, size + e.fout() * 3f - 2f);
			Draw.reset();
			if (pointCount > 0) {
				float offset = Mathf.randomSeed(e.id, 360f);
				for (int i = 0; i < pointCount; i++) {
					float angle = (i * 360f / pointCount + (Time.time * 3)) + (offset + 4);
					float rx = Angles.trnsx(angle, size - 2f), ry = Angles.trnsy(angle, size);
					Draw.color(lightColor, darkColor, e.fin());
					Drawf.tri(
						e.x + rx, e.y + ry, 48f, 28f * e.fout(), angle);
				}
			}
			Draw.z(Layer.debris);
			Fill.light(e.x, e.y, Lines.circleVertices(size / 2), size, Color.white.cpy().a(0f), Tmp.c4.set(splashColor).a(e.fout()));
			Draw.reset();
			Drawf.light(e.x, e.y, size * 1.6f, darkColor, e.fout());
		});
	}

	/**
	 * Based on Fx.blastExplosion
	 *
	 * @param frontColor
	 * 	The lighter color | Default : Pal.MissileYellow
	 * @param backColor
	 * 	The darker color | Default : Pal.MissileYellowBack
	 */
	public static Effect blastExplosion(Color frontColor, Color backColor) {
		return new Effect(23, e -> {
			Draw.color(frontColor);
			e.scaled(6, i -> {
				Lines.stroke(3f * i.fout());
				Lines.circle(e.x, e.y, 3f + i.fin() * 15f);
			});
			Draw.color(Color.gray);
			Angles.randLenVectors(e.id, 5, 2f + 23f * e.finpow(), (x, y) ->
				Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 0.5f));
			Draw.color(backColor);
			Lines.stroke(e.fout());
			Angles.randLenVectors(e.id + 1, 4, 1f + 23f * e.finpow(), (x, y) ->
				Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 0.7f + e.fout() * 3f));

			Drawf.light(e.x, e.y, 45f, backColor, 0.8f * e.fout());
		});
	}

	/**
	 * Based on Fx.massiveExplosion
	 *
	 * @param frontColor
	 * 	The lighter color | Default : Pal.MissileYellow
	 * @param backColor
	 * 	The darker color | Default : Pal.MissileYellowBack
	 */
	public static Effect massiveExplosion(Color frontColor, Color backColor) {
		return new Effect(30, e -> {
			Draw.color(frontColor);
			e.scaled(7, i -> {
				Lines.stroke(3f * i.fout());
				Lines.circle(e.x, e.y, 4f + i.fin() * 30f);
			});
			Draw.color(Color.gray);
			Angles.randLenVectors(e.id, 8, 2f + 30f * e.finpow(), (x, y) -> {
				Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 0.5f);
			});
			Draw.color(backColor);
			Lines.stroke(e.fout());
			Angles.randLenVectors(e.id + 1, 6, 1f + 29f * e.finpow(), (x, y) -> {
				Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 4f);
			});
			Drawf.light(e.x, e.y, 50f, backColor, 0.8f * e.fout());
		});
	}

	/**
	 * Based on Fx.greenBomb
	 *
	 * @param size
	 * 	How big is the radius
	 * @param frontColor
	 * 	color on the outer edge of the burst flame
	 * @param backColor
	 * 	color on the inner part of the burst flame
	 */
	public static Effect burstSmelt(float size, Color frontColor, Color backColor) {
		return new Effect(35, 100f, e -> {
			float length = e.finpow() * (size * 2.5f);
			float width = e.fout() * (size / 8f);
			Draw.color(frontColor, backColor, Color.gray, e.fin());
			Lines.stroke(e.fout() * 4f);
			Lines.circle(e.x, e.y, 4f + e.finpow() * size);

			Draw.color(backColor);
			for (int i = 0; i < 4; i++) {
				Drawf.tri(e.x, e.y, (width * 2), length, i * 90);
			}

			Draw.color(frontColor);
			for (int i = 0; i < 4; i++) {
				Drawf.tri(e.x, e.y, width, length, i * 90);
			}
			Angles.randLenVectors(e.id, 12, e.finpow() * e.lifetime * 1.5f, (x, y) -> {
				Draw.color(backColor, Color.gray, e.fin());
				Fill.square(e.x + x, e.y + y, (e.fout() * 15f) / 2f, 45);
			});
		});
	}

	/**
	 * Based on Fx.impactCloud
	 *
	 * @param color
	 * 	The color of the cloud, use darker color
	 */
	public static Effect impactCloud(Color color) {
		return new Effect(120, 400f, e ->
			Angles.randLenVectors(e.id, 22, e.finpow() * 160f, (x, y) -> {
				float size = e.fout() * 15f;
				Draw.color(color, Color.lightGray, e.fin());
				Fill.circle(e.x + x, e.y + y, size / 2f);
			}));
	}

	/**
	 * Based on Fx.SmokeCloud
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
			Angles.randLenVectors(e.id, e.fin(), (int) (lifetime / 2), lifetime / 2, (x, y, fin, fout) -> {
				Draw.color(color);
				Draw.alpha((0.5f - Math.abs(fin - 0.5f)) * 2f);
				Fill.circle(e.x + x, e.y + y, 0.5f + fout * 4f);
			})).layer(layer);
	}

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
			Draw.color(Tmp.c1.set(color).mul(1.1f));
			Angles.randLenVectors(e.id, (int) (6 * smokeSize), 12f * e.finpow() * smokeSize / 8, (x, y) -> {
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
			Draw.color(Tmp.c1.set(Pal.lightishGray).mul(1.1f));
			Angles.randLenVectors(e.id, (int) (6 * smokeSize), 12f * e.finpow() * smokeSize / 8, (x, y) -> {
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
			Draw.color(Tmp.c1.set(Pal.lightishGray).mul(1.1f));
			Angles.randLenVectors(e.id, (int) (6 * smokeSize), 12f * e.finpow() * smokeSize / 8, (x, y) -> {
				Draw.alpha(0.5f);
				Fill.circle(e.x + x, e.y + y, e.fout() * 3f + 0.1f);
				Draw.reset();
			});
		}).layer(Layer.flyingUnitLow);
	}


}