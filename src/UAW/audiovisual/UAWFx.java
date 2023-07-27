package UAW.audiovisual;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Vec2;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;
import static mindustry.graphics.Drawf.light;

public class UAWFx {
	// region Static
	public static final Effect none = new Effect(0, 0f, e -> {
	}),

	breakBlockPhlog = new Effect(12, e -> {
		color(UAWPal.phlogistonMid);
		stroke(3f - e.fin() * 2f);
		Lines.square(e.x, e.y, tilesize / 2f * e.rotation + e.fin() * 3f);

		randLenVectors(e.id, 3 + (int) (e.rotation * 3), e.rotation * 2f + (tilesize * e.rotation) * e.finpow(), (x, y) -> {
			Fill.square(e.x + x, e.y + y, 1f + e.fout() * (3f + e.rotation));
		});
	}),


	// region Shooting
	shootSmoke = new Effect(30f, e -> {
		Draw.color(e.color, Color.lightGray, Color.gray, e.fin());

		Angles.randLenVectors(e.id, 9, e.finpow() * 23f, e.rotation, 20f, (x, y) -> {
			Fill.circle(e.x + x, e.y + y, e.fout() * 2.4f + 0.2f);
		});
	}),

	shootHugeColor = new Effect(20, e -> {
		color(e.color, Color.gray, e.fin());
		float w = 18.5f * e.fout();
		Drawf.tri(e.x, e.y, w, 52 * e.fout(), e.rotation);
		Drawf.tri(e.x, e.y, w, 5.4f * e.fout(), e.rotation + 180f);
	}),

	// endregion Shooting

	// region Torpedoes
	torpedoRippleHit = new Effect(40f, 100f, e -> {
		color(e.color);
		Lines.stroke(e.fout() * 1.4f);
		float circleRad = 4f + e.finpow() * 55f;
		Lines.circle(e.x, e.y, circleRad);
		reset();
	}).layer(Layer.debris),

	torpedoRippleTrail = new Effect(30, e -> {
		e.lifetime = 30f * e.rotation;
		Draw.color(Tmp.c1.set(e.color));
		Lines.stroke(e.fout() * 1.2f);
		Lines.circle(e.x, e.y, 3 + e.finpow() * 12f);
	}).layer(Layer.debris),

	torpedoCruiseTrail = new Effect(17f, e -> {
		Draw.color(e.color, Color.valueOf("f5f5f5"), e.fin());
		Angles.randLenVectors(e.id, 16, 2f + e.fin() * 6f, (x, y) -> {
			Draw.alpha(0.15f);
			Fill.circle(e.x + x, e.y + y, 0.5f + e.fslope() * 1.5f);
			Draw.reset();
		});
		Draw.reset();
	}).layer(Layer.debris),

	torpedoTrailFade = new Effect(400f, 400, e -> {
		if (!(e.data instanceof Trail trail)) return;
		e.lifetime = trail.length * 1.4f;
		if (!state.isPaused()) {
			trail.shorten();
		}
		trail.drawCap(e.color, e.rotation);
		trail.draw(e.color, e.rotation);
	}).layer(Layer.scorch - 0.1f),

	// endregion Torpedoes

	// region Hit

	hitBulletBigColor = new Effect(13, e -> {
		Draw.color(Color.white, e.color, e.fin());
		Lines.stroke(0.5f + e.fout() * 1.5f);
		Angles.randLenVectors(e.id, 8, e.finpow() * 30f, e.rotation, 50f, (x, y) -> {
			float ang = Mathf.angle(x, y);
			lineAngle(e.x + x, e.y + y, ang, e.fout() * 4 + 1.5f);
		});
	}),

	/** Based on Fx.fireHit */
	cryoHit = new Effect(38f, e -> {
		Draw.color(UAWPal.cryoFront, UAWPal.cryoBack, e.fin());
		Angles.randLenVectors(e.id, 5, 2f + e.fin() * 10f, (x, y) -> Fill.circle(e.x + x, e.y + y, 0.2f + e.fout() * 1.6f));
		Draw.color();
	}),

	/** Based on Fx.fireHit */
	plastHit = new Effect(38f, e -> {
		color(Pal.plastaniumFront, Pal.plastaniumBack, e.fin());
		randLenVectors(e.id, 5, 2f + e.fin() * 10f, (x, y) ->
			Fill.circle(e.x + x, e.y + y, 0.2f + e.fout() * 1.6f));
		color();
	}),

	/** Based on Fx.fireHit */
	surgeHit = new Effect(38f, e -> {
		color(UAWPal.surgeFront, UAWPal.surgeBack, e.fin());
		randLenVectors(e.id, 5, 2f + e.fin() * 10f, (x, y) ->
			Fill.circle(e.x + x, e.y + y, 0.2f + e.fout() * 1.6f));
		color();
	}),

	// endregion Hit

	// region Status
	statusEffectCircle = new Effect(35f, e -> {
		color(e.color);

		randLenVectors(e.id, 3, 2f + e.fin() * 7f, (x, y) -> {
			Fill.circle(e.x + x, e.y + y, 0.2f + e.fslope() * 1.5f);
		});
	}),

	statusEffectSquare = new Effect(35f, e -> {
		color(e.color);

		randLenVectors(e.id, 3, 2f + e.fin() * 7f, (x, y) -> {
			Fill.square(e.x + x, e.y + y, 0.2f + e.fslope() * 1.5f, 45f);
		});
	}),
	// endregion Status

	// region Casings
	casing1Double = new Effect(32f, e -> {
		color(Pal.lightOrange, Color.lightGray, Pal.lightishGray, e.fin());
		alpha(e.fout(0.5f));
		float rot = Math.abs(e.rotation) + 90f;
		for (int i : Mathf.signs) {
			float len = (2f + e.finpow() * 10f) * i;
			float lr = rot + e.fin() * 20f * i;
			rect(Core.atlas.find("casing"),
				e.x + trnsx(lr, len) + Mathf.randomSeedRange(e.id + i + 7, 3f * e.fin()),
				e.y + trnsy(lr, len) + Mathf.randomSeedRange(e.id + i + 8, 3f * e.fin()),
				1f, 2f, rot + e.fin() * 50f * i
			);
		}

	}).layer(Layer.bullet),

	casing2Long = new Effect(36f, e -> {
		color(Pal.lightOrange, Color.lightGray, Pal.lightishGray, e.fin());
		alpha(e.fout(0.5f));
		float rot = Math.abs(e.rotation) + 90f;
		int i = -Mathf.sign(e.rotation);
		float len = (2f + e.finpow() * 10f) * i;
		float lr = rot + e.fin() * 20f * i;
		rect(Core.atlas.find("casing"),
			e.x + trnsx(lr, len) + Mathf.randomSeedRange(e.id + i + 7, 3f * e.fin()),
			e.y + trnsy(lr, len) + Mathf.randomSeedRange(e.id + i + 8, 3f * e.fin()),
			2.5f, 5f, rot + e.fin() * 50f * i
		);
	}).layer(Layer.bullet),

	casing3Long = new Effect(55f, e -> {
		color(Pal.lightOrange, Color.lightGray, Pal.lightishGray, e.fin());
		alpha(e.fout(0.5f));
		float rot = Math.abs(e.rotation) + 90f;
		int i = -Mathf.sign(e.rotation);
		float len = (2f + e.finpow() * 10f) * i;
		float lr = rot + e.fin() * 20f * i;
		rect(Core.atlas.find("casing"),
			e.x + trnsx(lr, len) + Mathf.randomSeedRange(e.id + i + 7, 3f * e.fin()),
			e.y + trnsy(lr, len) + Mathf.randomSeedRange(e.id + i + 8, 3f * e.fin()),
			3f, 6.5f, rot + e.fin() * 50f * i
		);
	}).layer(Layer.bullet),

	casing4Long = new Effect(45f, e -> {
		color(Pal.lightOrange, Pal.lightishGray, Pal.lightishGray, e.fin());
		alpha(e.fout(0.5f));
		float rot = Math.abs(e.rotation) + 90f;
		int i = -Mathf.sign(e.rotation);
		float len = (4f + e.finpow() * 9f) * i;
		float lr = rot + Mathf.randomSeedRange(e.id + i + 6, 20f * e.fin()) * i;

		rect(Core.atlas.find("casing"),
			e.x + trnsx(lr, len) + Mathf.randomSeedRange(e.id + i + 7, 3f * e.fin()),
			e.y + trnsy(lr, len) + Mathf.randomSeedRange(e.id + i + 8, 3f * e.fin()),
			3f, 8f,
			rot + e.fin() * 50f * i
		);
	}).layer(Layer.bullet),

	casing5 = new Effect(65f, e -> {
		color(Pal.lightOrange, Pal.lightishGray, Pal.lightishGray, e.fin());
		alpha(e.fout(0.5f));
		float rot = Math.abs(e.rotation) + 90f;
		int i = -Mathf.sign(e.rotation);
		float len = (4f + e.finpow() * 9f) * i;
		float lr = rot + Mathf.randomSeedRange(e.id + i + 6, 20f * e.fin()) * i;

		rect(Core.atlas.find("casing"),
			e.x + trnsx(lr, len) + Mathf.randomSeedRange(e.id + i + 7, 3f * e.fin()),
			e.y + trnsy(lr, len) + Mathf.randomSeedRange(e.id + i + 8, 3f * e.fin()),
			3.5f, 8f,
			rot + e.fin() * 50f * i
		);
	}).layer(Layer.bullet),

	casingCanister = new Effect(50f, e -> {
		color(Pal.lightOrange, Pal.lightishGray, Pal.lightishGray, e.fin());
		alpha(e.fout(0.5f));
		float rot = Math.abs(e.rotation) + 90f;
		int i = -Mathf.sign(e.rotation);
		float len = (4f + e.finpow() * 9f) * i;
		float lr = rot + Mathf.randomSeedRange(e.id + i + 6, 20f * e.fin()) * i;

		rect(Core.atlas.find("casing"),
			e.x + trnsx(lr, len) + Mathf.randomSeedRange(e.id + i + 7, 3f * e.fin()),
			e.y + trnsy(lr, len) + Mathf.randomSeedRange(e.id + i + 8, 3f * e.fin()),
			5.4f, 9f,
			rot + e.fin() * 50f * i
		);
	}).layer(Layer.bullet),

	casing6 = new Effect(55f, e -> {
		color(Pal.lightOrange, Pal.lightishGray, Pal.lightishGray, e.fin());
		alpha(e.fout(0.5f));
		float rot = Math.abs(e.rotation) + 90f;
		int i = -Mathf.sign(e.rotation);
		float len = (4f + e.finpow() * 9f) * i;
		float lr = rot + Mathf.randomSeedRange(e.id + i + 6, 20f * e.fin()) * i;

		rect(Core.atlas.find("casing"),
			e.x + trnsx(lr, len) + Mathf.randomSeedRange(e.id + i + 7, 3f * e.fin()),
			e.y + trnsy(lr, len) + Mathf.randomSeedRange(e.id + i + 8, 3f * e.fin()),
			4.5f, 10f,
			rot + e.fin() * 50f * i
		);
	}).layer(Layer.bullet),

	casing7 = new Effect(75f, e -> {
		color(Pal.lightOrange, Pal.lightishGray, Pal.lightishGray, e.fin());
		alpha(e.fout(0.5f));
		float rot = Math.abs(e.rotation) + 90f;
		int i = -Mathf.sign(e.rotation);
		float len = (4f + e.finpow() * 9f) * i;
		float lr = rot + Mathf.randomSeedRange(e.id + i + 6, 20f * e.fin()) * i;

		rect(Core.atlas.find("casing"),
			e.x + trnsx(lr, len) + Mathf.randomSeedRange(e.id + i + 8, 3f * e.fin()),
			e.y + trnsy(lr, len) + Mathf.randomSeedRange(e.id + i + 7, 3f * e.fin()),
			5.5f, 12f,
			rot + e.fin() * 50f * i
		);
	}).layer(Layer.bullet),
	// endregion Casings

	// region Trails

	/**
	 * Vanilla trail fade but with much larger clipsize
	 */
	trailFade = new Effect(400f, 400, e -> {
		if (!(e.data instanceof Trail trail)) return;
		//life is how many frames it takes to fade out the trail
		e.lifetime = trail.length * 1.4f;

		if (!state.isPaused()) {
			trail.shorten();
		}
		trail.drawCap(e.color, e.rotation);
		trail.draw(e.color, e.rotation);
	});

	private static final Rand rand = new Rand();
	private static final Vec2 v = new Vec2();

	// endregion Trails

	//endregion Static

	// region Dynamic

	// region inst & rail

	/** Refer to {@link UAWFx#instShoot(float burstLength, float life, Color color)} */
	public static Effect instShoot(float burstLength) {
		return instShoot(burstLength, Pal.bulletYellowBack);
	}

	/** Refer to {@link UAWFx#instShoot(float burstLength, float life, Color color)} */
	public static Effect instShoot(float burstLength, Color color) {
		return instShoot(burstLength, burstLength * 0.28f, color);
	}

	/**
	 * Based on {@link Fx#instShoot}
	 * @param burstLength
	 * 	[85] The length of the side burst
	 * @param lifetime
	 * 	[24] Adjusts the effect life along with its size etc.
	 * @param color
	 * 	Flame burst color
	 */
	public static Effect instShoot(float burstLength, float lifetime, Color color) {
		float l2 = lifetime * 0.41f;
		float width = burstLength * 0.15f;
		float frontBurstLength = burstLength * 0.58f;
		float lightRad = burstLength * 2.11f;
		return new Effect(lifetime, e -> {
			e.scaled(l2, b -> {
				color(Color.white, color, b.fin());
				stroke(b.fout() * 3f + 0.2f);
				Lines.circle(b.x, b.y, b.fin() * frontBurstLength);
			});

			color(color);

			for (int i : Mathf.signs) {
				Drawf.tri(e.x, e.y, width * e.fout(), burstLength, e.rotation + 90f * i);
				Drawf.tri(e.x, e.y, width * e.fout(), frontBurstLength, e.rotation + 20f * i);
			}

			light(e.x, e.y, lightRad, color, 0.9f * e.fout());
		});
	}

	/** Refer to {@link UAWFx#railShoot(float, float, Color)} */
	public static Effect railShoot(float burstLength, Color color) {
		return railShoot(burstLength, burstLength * 0.5f, color);
	}

	/**
	 * Based on {@link Fx#railShoot}
	 * @param burstLength
	 * 	[85] Side Burst Length
	 * @param lifetime
	 * 	* 	[24] Effect Lifetime
	 * @param color
	 * 	The effect color
	 */
	public static Effect railShoot(float burstLength, float lifetime, Color color) {
		float l2 = lifetime * 0.41f;
		float width = burstLength * 0.15f;
		float circleRad = burstLength * 0.58f;
		float lightRad = burstLength * 2.11f;
		return new Effect(lifetime, e -> {
			e.scaled(l2, b -> {
				color(Color.white, Color.lightGray, b.fin());
				stroke(b.fout() * 3f + 0.2f);
				Lines.circle(b.x, b.y, b.fin() * circleRad);
			});
			color(color);
			for (int i : Mathf.signs) {
				Drawf.tri(e.x, e.y, width * e.fout(), burstLength, e.rotation + 90f * i);
			}
			light(e.x, e.y, lightRad, color, 0.9f * e.fout());
		});
	}

	/**
	 * Based on Fx.railTrail
	 * @param width
	 * 	How wide is the trail, also adjusts its height, spacing have to be adjusted manually
	 * @param color
	 * 	The color of the trail
	 */
	public static Effect railTrail(float width, Color color) {
		return new Effect(width * 1.8f, e -> {
			color(color);
			for (int i : Mathf.signs) {
				Drawf.tri(e.x, e.y, width * e.fout(), (width * 2.4f), e.rotation + 90 + 90f * i);
			}
			light(e.x, e.y, (width * 6) * e.fout(), color, 0.5f);
		});
	}

	/** Refer to {@link UAWFx#railHit(float, float, Color)} */
	public static Effect railHit(float hitLength, Color color) {
		return railHit(hitLength, hitLength * 0.3f, color);
	}

	/**
	 * Based on {@link Fx#railHit}
	 * @param hitLength
	 * 	[60] Hit length of the effect
	 * @param lifetime
	 * 	[18]
	 */
	public static Effect railHit(float hitLength, float lifetime, Color color) {
		float clipsize = hitLength * 3.33f;
		float width = hitLength * 0.16f;
		return new Effect(lifetime, clipsize, e -> {
			color(color);

			for (int i : Mathf.signs) {
				Drawf.tri(e.x, e.y, width * e.fout(), hitLength, e.rotation + 140f * i);
			}
		});
	}

	/** Refer to {@link UAWFx#instHit(float, float, Color, Color)} */
	public static Effect instHit(float burstLength, Color frontColor, Color backColor) {
		return instHit(burstLength, burstLength * 0.25f, frontColor, backColor);
	}

	/**
	 * Based on {@link Fx#instHit}
	 * @param burstLength
	 * 	[80]
	 * @param lifetime
	 * 	[20]
	 */
	public static Effect instHit(float burstLength, float lifetime, Color frontColor, Color backColor) {
		float clipsize = lifetime * 100f;
		float w2 = burstLength * 0.2875f;
		float otherBurst = burstLength * 0.25f;
		float l2 = lifetime * 0.5f, l3 = lifetime * 0.6f;
		return new Effect(lifetime, clipsize, e -> {
			color(backColor);

			for (int i = 0; i < 2; i++) {
				color(i == 0 ? backColor : frontColor);

				float m = i == 0 ? 1f : 0.5f;

				for (int j = 0; j < 5; j++) {
					float rot = e.rotation + Mathf.randomSeedRange(e.id + j, 50f);
					float w = w2 * e.fout() * m;
					Drawf.tri(e.x, e.y, w, (burstLength + Mathf.randomSeedRange(e.id + j, burstLength / 2)) * m, rot);
					Drawf.tri(e.x, e.y, w, otherBurst * m, rot + 180f);
				}
			}
			float shockRad1 = burstLength * 0.375f;
			e.scaled(l2, c -> {
				float circleThick1 = burstLength * 0.025f;
				color(frontColor);
				stroke(c.fout() * circleThick1 + circleThick1 / 10);
				Lines.circle(e.x, e.y, c.fin() * shockRad1);
			});

			e.scaled(l3, c -> {
				int amount = (int) (burstLength * 0.3125f);
				float range = burstLength * 0.75f;
				color(backColor);
				randLenVectors(e.id, amount, 5f + e.fin() * burstLength, e.rotation, range, (x, y) -> {
					Fill.square(e.x + x, e.y + y, c.fout() * shockRad1 / 10f, 45f);
				});
			});
		});
	}
	// endregion inst & rail

	// region shootSmokes

	/** Refer to {@link UAWFx#shootSmoke(float, Color, boolean, float, float)} */
	public static Effect shootSmoke(float lifetime, Color color) {
		return shootSmoke(lifetime, color, false);
	}

	/** Refer to {@link UAWFx#shootSmoke(float, Color, boolean, float, float)} */
	public static Effect shootSmoke(float lifetime, Color color, boolean muzzleBreak) {
		return shootSmoke(lifetime, color, muzzleBreak, 1, 1);
	}

	/**
	 * Modified version of {@link Fx#shootBigSmoke2}
	 * @param lifetime
	 * 	[18] How long does the smoke lasts, also adjusts amount, spreads, and radius.
	 * @param color
	 * 	The color of the beginning of the smoke, will lerp to gray
	 * @param muzzleBreak
	 * 	[False] Whenever to cause 2 instances of the effect and make it spread horizontally
	 */
	public static Effect shootSmoke(float lifetime, Color color, boolean muzzleBreak, float sizeMult, float lifetimeMult) {
		int amount = (int) ((int) lifetime * 0.5f);
		float l = lifetime * 1.27f;
		float rng = lifetime * 1.1f;
		float size = (lifetime * 0.13f) * sizeMult;
		return new Effect(lifetime * lifetimeMult, e -> {
			Draw.color(color, Color.lightGray, Color.gray, e.fin());

			randLenVectors(e.id, amount, e.finpow() * l, e.rotation + (muzzleBreak ? 90 : 0), rng, (x, y) -> {
				Fill.circle(e.x + x, e.y + y, e.fout() * size + (size * 0.011f));
			});
			if (muzzleBreak) {
				randLenVectors(e.id, amount, e.finpow() * l, e.rotation + 270, rng, (x, y) -> {
					Fill.circle(e.x + x, e.y + y, e.fout() * size + (size * 0.011f));
				});
			}
			Draw.reset();
		});
	}

	/** Refer to {@link UAWFx#shootSmokeMissile(float, float, int, Color, float)} */
	public static Effect shootSmokeMissile(Color color) {
		return shootSmokeMissile(9, color);
	}

	/** Refer to {@link UAWFx#shootSmokeMissile(float, float, int, Color, float)} */
	public static Effect shootSmokeMissile(float size, Color color) {
		return shootSmokeMissile(size, color, 180);
	}

	/** Refer to {@link UAWFx#shootSmokeMissile(float, float, int, Color, float)} */
	public static Effect shootSmokeMissile(float size, Color color, float angle) {
		return shootSmokeMissile(size, 35, color, angle);
	}

	/** Refer to {@link UAWFx#shootSmokeMissile(float, float, int, Color, float)} */
	public static Effect shootSmokeMissile(float size, int amount, Color color) {
		return shootSmokeMissile(size, amount, color, 180);
	}

	/** Refer to {@link UAWFx#shootSmokeMissile(float, float, int, Color, float)} */
	public static Effect shootSmokeMissile(float size, int amount, Color color, float angle) {
		return shootSmokeMissile(size * 14.4f, size, amount, color, angle);
	}

	/**
	 * Based on {@link Fx#shootSmokeMissile}
	 * @param lifetime
	 * 	[130]
	 * @param size
	 * 	[9]
	 * @param amount
	 * 	[35]
	 * @param angle
	 * 	[180]
	 */
	public static Effect shootSmokeMissile(float lifetime, float size, int amount, Color color, float angle) {
		float clipsize = lifetime * 2.3f;
		return new Effect(lifetime, clipsize, e -> {
			color(color);
			alpha(0.5f);
			rand.setSeed(e.id);
			for (int i = 0; i < amount; i++) {
				v.trns(e.rotation + angle + rand.range(21f), rand.random(e.finpow() * 90f)).add(rand.range(3f), rand.range(3f));
				e.scaled(e.lifetime * rand.random(0.2f, 1f), b -> {
					Fill.circle(e.x + v.x, e.y + v.y, b.fout() * size + (size * 0.033f));
				});
			}
		});
	}

	// endregion shootSmokes

	// region hitEffects

	// region sparkHit

	public static Effect sparkHit(float lifetime, int amount, Color color) {
		return sparkHit(lifetime, amount * 1.25f, amount * 5.125f, amount, 1.3f, 6f, color);
	}

	public static Effect sparkHit(float lifetime, float spreadCone, int amount, Color color) {
		return sparkHit(lifetime, spreadCone, spreadCone * 4, amount, 1.3f, 6f, color);
	}

	/**
	 * Based on {@link Fx#colorSparkBig}
	 * @param lifetime
	 * 	25f
	 * @param spreadCone
	 * 	10f
	 * @param spreadRange
	 * 	41f
	 * @param amount
	 * 	8
	 * @param strokeThick
	 * 	1.3f
	 * @param strokeLength
	 * 	6f
	 */
	public static Effect sparkHit(float lifetime, float spreadCone, float spreadRange, int amount, float strokeThick, float strokeLength, Color color) {
		return new Effect(lifetime, e -> {
			color(Color.white, color, e.fin());
			stroke(e.fout() * strokeThick + 0.7f);

			randLenVectors(e.id, amount, spreadRange * e.fin(), e.rotation, spreadCone, (x, y) -> {
				lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * strokeLength + 0.5f);
			});
		});
	}

	// endregion sparkHit

	// region hitBulletSmall
	public static Effect hitBulletSmall(Color color) {
		return hitBulletSmall(Color.white, color);
	}

	public static Effect hitBulletSmall(float scaling, Color color) {
		return hitBulletSmall(14 * (scaling / 2), 5 * scaling, (int) (4 * scaling), Color.white, color);
	}

	public static Effect hitBulletSmall(Color color1, Color color2) {
		return hitBulletSmall(14, 5, 4, color1, color2);
	}

	/**
	 * {@link Fx#hitBulletSmall}
	 * @param lifetime
	 * 	14
	 * @param circleSize
	 * 	5
	 * @param sparkAmount
	 * 	4
	 */

	public static Effect hitBulletSmall(float lifetime, float circleSize, int sparkAmount, Color color1, Color color2) {
		return new Effect(lifetime, e -> {
			color(color1, color2, e.fin());

			e.scaled(lifetime / 2, s -> {
				stroke(0.5f + s.fout());
				Lines.circle(e.x, e.y, s.fin() * circleSize);
			});

			stroke(0.5f + e.fout());

			randLenVectors(e.id, sparkAmount, e.fin() * 15f, (x, y) -> {
				float ang = Mathf.angle(x, y);
				lineAngle(e.x + x, e.y + y, ang, e.fout() * 3 + 1f);
			});

			Drawf.light(e.x, e.y, circleSize * 4, color2, 0.6f * e.fout());
		});
	}
	// endregion hitBulletSmall

	/**
	 * Based on Fx.fireHit, that will scale based on particle radius, don't make it too big, or it will lag
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

// endregion hitEffects

	/** Based on {@link Fx#missileTrailSmoke} */
	public static Effect missileTrailSmoke(float lifetime, float size, float spread) {
		return new Effect(lifetime, lifetime * 3, b -> {
			float intensity = 2f;

			color(b.color, 0.5f);
			for (int i = 0; i < 4; i++) {
				rand.setSeed(b.id * 2L + i);
				float lenScl = rand.random(0.5f, 1f);
				int fi = i;
				b.scaled(b.lifetime * lenScl, e -> {
					randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (spread / 4), spread, (x, y, in, out) -> {
						float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
						float rad = fout * size;

						Fill.circle(e.x + x, e.y + y, rad);
						light(e.x + x, e.y + y, rad * 2.5f, b.color, 0.5f);
					});
				});
			}
		}).layer(Layer.bullet - 1f);
	}

	/**
	 * Caernarvon shell trail
	 * @param length
	 * 	Size and Thickness is the side trail || Default  is 45
	 */
	public static Effect sideTrail(float length) {
		return new Effect(length * 0.5f, e -> {
			Draw.color(e.color);
			for (int s : Mathf.signs) {
				Drawf.tri(e.x, e.y, length * 0.1f, length * e.fslope(), e.rotation + 135f * s);
			}
		});
	}

	// region Explosions

	/**
	 * {@link mindustry.entities.effect.ExplosionEffect}
	 * @param size
	 * 	How big is the explosion
	 */
	public static Effect dynamicExplosion(float size) {
		return dynamicExplosion(size, Pal.missileYellow.cpy(), Pal.missileYellowBack.cpy());
	}

	/**
	 * Based on Fx.dynamicExplosion
	 * @param size
	 * 	How big is the explosion, can be based on splashDamageRadius, also adjusts life and clip size
	 */
	public static Effect dynamicExplosion(float size, Color frontColor, Color backColor) {
		return new Effect(size * 12, size * 5, b -> {
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

			b.scaled(baseLifetime, e -> {
				e.scaled(5 + intensity * 2.5f, i -> {
					stroke((3.1f + intensity / 5f) * i.fout());
					Lines.circle(e.x, e.y, (3f + i.fin() * 14f) * intensity);
					Drawf.light(e.x, e.y, i.fin() * 14f * 2f * intensity, Color.white, 0.9f * e.fout());
				});

				color(backColor, frontColor, Color.gray, e.fin());
				stroke((1.7f * e.fout()) * (1f + (intensity - 1f) / 2f));

				Draw.z(Layer.effect + 0.001f);
				randLenVectors(e.id + 1, e.finpow() + 0.001f, (int) (9 * intensity), 40f * intensity, (x, y, in, out) -> {
					lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + out * 4 * (3f + intensity));
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
			light(e.x, e.y, size * 1.6f, frontColor, e.fout());
			Fx.chainLightning.at(e.x, e.y, frontColor);
		});
	}

	/**
	 * Based on Fx.blastExplosion
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

			light(e.x, e.y, 45f, backColor, 0.8f * e.fout());
		});
	}

	/**
	 * Based on Fx.massiveExplosion
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
			light(e.x, e.y, 50f, backColor, 0.8f * e.fout());
		});
	}

	/** Refer to {@link UAWFx#crossBomb(float size, Color color)} */
	public static Effect crossBomb(float size, Color color) {
		return crossBomb(size, 90, color);
	}

	/** Refer to {@link UAWFx#crossBomb(float lifetime, float size, float rotation, Color color)} */
	public static Effect crossBomb(float size, float rotation, Color color) {
		return crossBomb(size * 0.58f, size, rotation, color);
	}

	/**
	 * Based on {@link Fx#greenBomb}
	 * @param size
	 * 	[ 68 (17 * tilesize)] How big is the radius, also adjusts life
	 * @param rotation
	 * 	[90]
	 * @param color
	 * 	color of the explosion
	 */
	public static Effect crossBomb(float lifetime, float size, float rotation, Color color) {
		float w1 = size * 0.08f, w2 = w1 * 0.5f;
		float l1 = size * 1.47f, l2 = size * 0.514f;
		float circ = size * 0.95f;
		return new Effect(lifetime, lifetime * 1.45f, e -> {
			color(color);
			stroke(e.fout() * 2f);
			float circleRad = 4f + e.finpow() * circ;
			Lines.circle(e.x, e.y, circleRad);

			color(color);
			for (int i = 0; i < 4; i++) {
				Drawf.tri(e.x, e.y, w1, l1 * e.fout(), i * rotation);
			}

			color();
			for (int i = 0; i < 4; i++) {
				Drawf.tri(e.x, e.y, w2, l2 * e.fout(), i * rotation);
			}

			light(e.x, e.y, circleRad * 1.6f, color, e.fout());
		});
	}
	// endregion Explosions

	// region burstCloud

	/** Refer to {@link UAWFx#burstCloud(float, float, int, float, Color)} */
	public static Effect burstCloud(Color color) {
		return burstCloud(15, color);
	}

	/** Refer to {@link UAWFx#burstCloud(float, float, int, float, Color)} */
	public static Effect burstCloud(float size, Color color) {
		return burstCloud(size, 22, 160, color);
	}

	/** Refer to {@link UAWFx#burstCloud(float, float, int, float, Color)} */
	public static Effect burstCloud(float size, int amount, float spreadRad, Color color) {
		return burstCloud(size, size * 6, amount, spreadRad, color);
	}

	/**
	 * Based on old Fx.impactCloud
	 * @param size
	 * 	[15]
	 * @param lifetime
	 * 	[120]
	 * @param amount
	 * 	[22]
	 * @param spreadRad
	 * 	[160]
	 */
	public static Effect burstCloud(float size, float lifetime, int amount, float spreadRad, Color color) {
		return new Effect(lifetime, 400f, e ->
			randLenVectors(e.id, amount, e.finpow() * spreadRad, (x, y) -> {
				float rad = e.fout() * size;
				alpha(0.8f * e.fout());
				color(color, Color.gray, e.fout());
				Fill.circle(e.x + x, e.y + y, rad);
			}));
	}

	// endregion burstCloud

	public static Effect crucibleSmoke(Color color) {
		return crucibleSmoke(160, color);
	}

	public static Effect crucibleSmoke(float lifetime, Color color) {
		return crucibleSmoke(lifetime, 2, color);
	}

	/**
	 * {@link Fx#surgeCruciSmoke}
	 * @param lifetime
	 * 	How long does the effect lasts | 160
	 * @param particleRad
	 * 	Particle Size | 2
	 * @param color
	 * 	particle color
	 */
	public static Effect crucibleSmoke(float lifetime, float particleRad, Color color) {
		return new Effect(lifetime, e -> {
			color(color);
			alpha(0.45f);

			rand.setSeed(e.id);
			for (int i = 0; i < 3; i++) {
				float len = rand.random(3f, 9f);
				float rot = rand.range(40f) + e.rotation;

				e.scaled(e.lifetime * rand.random(0.3f, 1f), b -> {
					v.trns(rot, len * b.finpow());
					Fill.circle(e.x + v.x, e.y + v.y, particleRad * b.fslope() + (particleRad / 10));
				});
			}
		});
	}


	/**
	 * Steam Cloud used for steam effects on various buildings
	 * @param smokeSize
	 * 	How big is the smoke 'puff' also adjusts the amount of 'puff'
	 * @param color
	 * 	The color of the smoke/puff
	 */
	public static Effect cloudPuff(float smokeSize, Color color) {
		float smokeSizeLfMult = 12f;
		return new Effect(smokeSize * smokeSizeLfMult, smokeSize * smokeSizeLfMult * 2.85f, e -> {
			color(Tmp.c1.set(color).mul(1.1f));
			randLenVectors(e.id, (int) (6 * smokeSize), 12f * e.finpow() * smokeSize / 8, (x, y) -> {
				alpha(0.45f * e.fout());
				Fill.circle(e.x + x, e.y + y, e.fout() * 3f + 0.1f);
				reset();
			});
		}).layer(Layer.blockOver);
	}

	public static Effect mineImpact(float size, Color color) {
		return mineImpact(size, 12, color);
	}

	public static Effect mineImpact(float size, int amount, Color color) {
		return mineImpact(size, size * 30, amount, color);
	}

	/**
	 * {@link Fx#mineImpact}
	 * @param size
	 * 	[3]
	 * @param lifetime
	 * 	[90]
	 * @param amount
	 * 	[12]
	 */
	public static Effect mineImpact(float size, float lifetime, int amount, Color color) {
		return new Effect(lifetime, e -> {
			color(color, Color.lightGray, e.fin());
			randLenVectors(e.id, amount, 5f + e.finpow() * 22f, (x, y) -> {
				Fill.square(e.x + x, e.y + y, e.fout() * size, 45);
			});
		});
	}


	public static Effect mineImpactWave(float waveRad, Color color) {
		return mineImpactWave(waveRad, waveRad * 1.517f, color);
	}

	public static Effect mineImpactWave(float waveRad, float sparkSpreadRad, Color color) {
		return mineImpactWave(waveRad, 17, sparkSpreadRad, color);
	}

	public static Effect mineImpactWave(float waveRad, int spark, float sparkSpreadRad, Color color) {
		return mineImpactWave(waveRad, spark, sparkSpreadRad, waveRad * 1.7f, color);
	}

	/**
	 * {@link Fx#mineImpactWave}
	 * @param waveRad
	 * 	[28]
	 * @param spark
	 * 	[12]
	 * @param sparkSpreadRad
	 * 	[40]
	 * @param lifetime
	 * 	[50]
	 */
	public static Effect mineImpactWave(float waveRad, int spark, float sparkSpreadRad, float lifetime, Color color) {
		float waveRadThickness = waveRad * 0.178f;
		return new Effect(lifetime, e -> {
			color(color);

			stroke(e.fout() * 1.5f);

			randLenVectors(e.id, spark, 4f + e.finpow() * sparkSpreadRad, (x, y) -> {
				lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fout() * 5 + 1f);
			});

			e.scaled(lifetime * 0.6f, b -> {
				Lines.stroke(waveRadThickness * b.fout());
				Lines.circle(e.x, e.y, b.finpow() * waveRad);
			});
		});
	}

	/**
	 * {@link Fx#breakBlock}
	 */
	public static Effect blockBreak(Color color) {
		return new Effect(12, e -> {
			color(color);
			stroke(3f - e.fin() * 2f);
			Lines.square(e.x, e.y, tilesize / 2f * e.rotation + e.fin() * 3f);

			randLenVectors(e.id, 3 + (int) (e.rotation * 3), e.rotation * 2f + (tilesize * e.rotation) * e.finpow(), (x, y) -> {
				Fill.square(e.x + x, e.y + y, 1f + e.fout() * (3f + e.rotation));
			});
		});
	}

	// endregion Dynamic
}

