package UAW.graphics;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.entities.Effect;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.state;

public class UAWFxS {
	public static final Effect none = new Effect(0, 0f, e -> {
	}),
	// region Shooting
	crossShoot = new Effect(15f, 90f, e -> {
		color(Pal.orangeSpark, Pal.lightOrange, e.fin());
		stroke(e.fout() * 4f);
		for (int i = 0; i < 4; i++) {
			Drawf.tri(e.x, e.y, 6f, 80f * e.fout(), i * 90);
		}
		color();
		for (int i = 0; i < 4; i++) {
			Drawf.tri(e.x, e.y, 3f, 30f * e.fout(), i * 90);
		}
	}),

	shootWaterFlame = new Effect(33f, 80f, e -> {
		color(UAWPal.waterFront, UAWPal.waterMiddle, Color.gray, e.fin());

		randLenVectors(e.id, 12, e.finpow() * 70f, e.rotation, 10f, (x, y) ->
			Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.6f));
	}),

	shootCryoFlame = new Effect(33f, 80f, e -> {
		color(UAWPal.cryoMiddle, UAWPal.cryoBack, Color.gray, e.fin());

		randLenVectors(e.id, 12, e.finpow() * 70f, e.rotation, 10f, (x, y) ->
			Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.6f));
	}),

	shootSurgeFlame = new Effect(33f, 80f, e -> {
		color(UAWPal.surgeFront, UAWPal.surgeBack, Color.gray, e.fin());

		randLenVectors(e.id, 12, e.finpow() * 70f, e.rotation, 10f, (x, y) ->
			Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.6f));
	}),

	shootPlastFlame = new Effect(33f, 80f, e -> {
		color(Pal.plastaniumFront, Pal.plastaniumBack, Color.gray, e.fin());

		randLenVectors(e.id, 12, e.finpow() * 70f, e.rotation, 10f, (x, y) ->
			Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.6f));
	}),

	shootSporeFlame = new Effect(33f, 80f, e -> {
		color(UAWPal.sporeFront, UAWPal.sporeBack, Color.gray, e.fin());

		randLenVectors(e.id, 12, e.finpow() * 70f, e.rotation, 10f, (x, y) ->
			Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.6f));
	}),

	shootPyraFlame = new Effect(35f, 80f, e -> {
		color(Pal.darkPyraFlame, Pal.lightPyraFlame, Color.gray, e.fin());

		randLenVectors(e.id, 13, e.finpow() * 70f, e.rotation, 10f, (x, y) ->
			Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.6f));
	}),

	muzzleBreakShootSmoke = new Effect(38f, e -> {
		int amount = 12;
		float length = 40f;
		float range = 30f;
		color(Pal.lightOrange, Color.lightGray, Color.gray, e.fin());
		randLenVectors(e.id, amount, e.finpow() * length, e.rotation + 90, range, (x, y) ->
			Fill.circle(e.x + x, e.y + y, e.fout() * 2.4f + 0.2f));
		color(Pal.lightOrange, Color.lightGray, Color.gray, e.fin());
		randLenVectors(e.id, amount, e.finpow() * length, e.rotation - 90, range, (x, y) ->
			Fill.circle(e.x + x, e.y + y, e.fout() * 2.4f + 0.2f));
	}),
	// endregion Shooting
	// region Hit
	torpedoRippleHit = new Effect(40f, 100f, e -> {
		color(UAWPal.waterMiddle);
		stroke(e.fout() * 1.4f);
		float circleRad = 4f + e.finpow() * 55f;
		Lines.circle(e.x, e.y, circleRad);
	}).layer(Layer.debris),

	bulletImpactHit = new Effect(38f, e -> {
		color(e.color, e.fin());
		randLenVectors(e.id, 4, 2f + e.fin() * 10f, (x, y) -> Fill.circle(e.x + x, e.y + y, 0.2f + e.fout() * 1.6f));
		color();
	}),

	cryoHit = new Effect(38f, e -> {
		color(UAWPal.cryoFront, UAWPal.cryoBack, e.fin());
		randLenVectors(e.id, 4, 2f + e.fin() * 10f, (x, y) -> Fill.circle(e.x + x, e.y + y, 0.2f + e.fout() * 1.6f));
		color();
	}),

	plastHit = new Effect(38f, e -> {
		color(Pal.plastaniumFront, Pal.plastaniumBack, e.fin());
		randLenVectors(e.id, 4, 2f + e.fin() * 10f, (x, y) ->
			Fill.circle(e.x + x, e.y + y, 0.2f + e.fout() * 1.6f));
		color();
	}),

	surgeHit = new Effect(38f, e -> {
		color(UAWPal.surgeFront, UAWPal.surgeBack, e.fin());
		randLenVectors(e.id, 4, 2f + e.fin() * 10f, (x, y) ->
			Fill.circle(e.x + x, e.y + y, 0.2f + e.fout() * 1.6f));
		color();
	}),

	/** Fx.hitBulletBig but with lancer pallete */
	hitLaserSpark = new Effect(13, e -> {
		color(Color.white, Pal.lancerLaser, e.fin());
		stroke(0.5f + e.fout() * 1.5f);

		randLenVectors(e.id, 8, e.finpow() * 30f, e.rotation, 50f, (x, y) -> {
			float ang = Mathf.angle(x, y);
			lineAngle(e.x + x, e.y + y, ang, e.fout() * 4 + 1.5f);
		});
	}),
	// endregion Hit
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
			e.x + trnsx(lr, len) + Mathf.randomSeedRange(e.id + i + 7, 3f * e.fin()),
			e.y + trnsy(lr, len) + Mathf.randomSeedRange(e.id + i + 8, 3f * e.fin()),
			5.5f, 12f,
			rot + e.fin() * 50f * i
		);
	}).layer(Layer.bullet),
	// endregion Casings
	// region Trails
	pyraSmokeTrail = new Effect(33f, 80f, e -> {
		color(Pal.lightPyraFlame, Color.lightGray, Color.valueOf("ddcece"), e.fin() * e.fin());

		randLenVectors(e.id, 8, 2f + e.finpow() * 36f, e.rotation + 180, 17f, (x, y) ->
			Fill.circle(e.x + x, e.y + y, 0.45f + e.fout() * 2f));
	}),

	pyraSmokeTrailUnder = new Effect(33f, 80f, e -> {
		color(Pal.lightPyraFlame, Color.lightGray, Color.valueOf("ddcece"), e.fin() * e.fin());

		randLenVectors(e.id, 8, 2f + e.finpow() * 36f, e.rotation + 180, 17f, (x, y) ->
			Fill.circle(e.x + x, e.y + y, 0.45f + e.fout() * 2f));
	}).layer(Layer.flyingUnitLow - 1.05f),

	cryoSmokeTrail = new Effect(33f, 80f, e -> {
		color(UAWPal.cryoMiddle, Color.lightGray, Color.valueOf("ddcece"), e.fin() * e.fin());

		randLenVectors(e.id, 8, 2f + e.finpow() * 36f, e.rotation + 180, 17f, (x, y) ->
			Fill.circle(e.x + x, e.y + y, 0.45f + e.fout() * 2f));
	}),

	cryoSmokeTrailUnder = new Effect(33f, 80f, e -> {
		color(UAWPal.cryoMiddle, Color.lightGray, Color.valueOf("ddcece"), e.fin() * e.fin());

		randLenVectors(e.id, 8, 2f + e.finpow() * 36f, e.rotation + 180, 17f, (x, y) ->
			Fill.circle(e.x + x, e.y + y, 0.45f + e.fout() * 2f));
	}).layer(Layer.flyingUnitLow - 1.05f),

	torpedoRippleTrail = new Effect(180, e -> {
		e.lifetime = 30f * e.rotation;
		color(Tmp.c1.set(e.color).mul(1.5f));
		stroke(e.fout() * 1.2f);
		Lines.circle(e.x, e.y, 3 + e.finpow() * 12f);
	}).layer(Layer.scorch - 0.1f),

	torpedoCruiseTrail = new Effect(25f, e -> {
		color(Color.valueOf("d0d0d0"), Color.valueOf("e8e8e8"), Color.valueOf("f5f5f5"), e.fout());
		randLenVectors(e.id, 16, 2f + e.fin() * 7f, (x, y) ->
			Fill.circle(e.x + x, e.y + y, 0.5f + e.fslope() * 1.5f));
	}).layer(Layer.debris + 0.1f),

	torpedoTrailFade = new Effect(400f, e -> {
		if (!(e.data instanceof Trail trail)) return;
		e.lifetime = trail.length * 1.4f;

		if (!state.isPaused()) {
			trail.shorten();
		}
		trail.drawCap(e.color, e.rotation);
		trail.draw(e.color, e.rotation);
	}).layer(Layer.scorch - 0.1f);

	// endregion Trails
}
