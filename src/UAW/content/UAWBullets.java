package UAW.content;

import UAW.entities.bullet.*;
import UAW.graphics.*;
import arc.graphics.Color;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.Pal;

import static mindustry.Vars.tilesize;
import static mindustry.content.Bullets.*;

public class UAWBullets implements ContentList {
	public static BulletType
		smallCopper, smallDense, smallPiercing, smallIncendiary, smallCryo,
		mediumPiercing, mediumStandard, mediumSurge, mediumIncendiary, mediumCryo,
		heavyCopper, heavyDense, heavyHoming, heavyThorium, heavySurge, heavyPiercing, heavyIncendiary, heavyCryo,
		buckshotLead, buckshotIncend, buckshotCryo,
		buckshotMedium, buckshotMediumIncend, buckshotMediumCryo, buckshotMediumPiercing, buckshotMediumFrag,
		mineBasic, mineIncend, mineCryo, mineOil, mineEMP, mineSpore,
		canisterBasic, canisterIncend, canisterCryo, canisterOil, canisterEMP, canisterSpore, canisterNuke;

	@Override
	public void load() {
		smallCopper = new BasicBulletType(4f, 9) {{
			width = 7f;
			height = 9f;
			lifetime = 60f;
			shootEffect = Fx.shootSmall;
			smokeEffect = Fx.shootSmallSmoke;
			ammoMultiplier = 2;
		}};
		smallDense = new BasicBulletType(5.5f, 18) {{
			width = 9f;
			height = 12f;
			reloadMultiplier = 0.6f;
			ammoMultiplier = 4;
			lifetime = 60f;
		}};
		smallIncendiary = new BasicBulletType(4.2f, 16) {{
			width = 10f;
			height = 12f;
			frontColor = Pal.lightishOrange;
			backColor = Pal.lightOrange;
			status = StatusEffects.burning;
			hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.fireHit);
			ammoMultiplier = 5;
			splashDamage = 10f;
			splashDamageRadius = 22f;
			makeFire = true;
			lifetime = 60f;
		}};
		smallCryo = new BasicBulletType(4.2f, 16) {{
			width = 10f;
			height = 12f;
			frontColor = UAWPal.cryoFront;
			backColor = UAWPal.cryoBack;
			status = StatusEffects.freezing;
			hitEffect = new MultiEffect(Fx.hitBulletSmall, UAWFxS.cryoHit);
			ammoMultiplier = 5;
			splashDamage = 10f;
			splashDamageRadius = 22f;
			lifetime = 60f;
		}};
		smallPiercing = new TrailBulletType(10f, 10f) {{
			height = 15f;
			width = 5f;
			armorIgnoreScl = 0.25f;
			shieldDamageMultiplier = 1.5f;
			shootEffect = Fx.shootBig;
			smokeEffect = Fx.shootBigSmoke;
			ammoMultiplier = 3;
		}};

		mediumStandard = new BasicBulletType(8, 20) {{
			pierceCap = 2;
			height = 25;
			width = 12;
			knockback = 1.2f;
			hitEffect = Fx.hitBulletBig;
			smokeEffect = Fx.shootBigSmoke2;
			shootEffect = Fx.shootBig2;
			trailChance = 0.4f;
			trailColor = Color.lightGray;

			status = StatusEffects.slow;
		}};
		mediumPiercing = new TrailBulletType(12, 22) {{
			height = 20;
			width = 5;
			pierce = true;
			reloadMultiplier = 1.2f;
			armorIgnoreScl = 0.5f;
			hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.generatespark);
			despawnEffect = Fx.hitBulletBig;
			smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.fireSmoke);
			shootEffect = new MultiEffect(Fx.shootBig2, Fx.hitBulletBig);
		}};
		mediumSurge = new BasicBulletType(8, 15) {{
			height = 25;
			width = 8;
			hitEffect = despawnEffect = new MultiEffect(Fx.hitBulletBig, Fx.lightning);
			smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.fireSmoke);
			shootEffect = new MultiEffect(Fx.shootBig, Fx.sparkShoot);
			status = StatusEffects.electrified;
			trailChance = 0.4f;
			trailColor = Color.lightGray;

			lightning = 3;
			lightningAngle = 360;
			lightningDamage = 1.5f;
			lightningLength = 12;
		}};
		mediumIncendiary = new BasicBulletType(7, 15) {{
			height = 25;
			width = 10;
			knockback = 1.2f;
			hitEffect = Fx.hitBulletBig;
			smokeEffect = Fx.shootBigSmoke2;
			shootEffect = new MultiEffect(Fx.shootPyraFlame, Fx.shootBig2);
			hitEffect = despawnEffect = new MultiEffect(Fx.hitBulletBig, Fx.fireHit);
			frontColor = UAWPal.incendFront;
			backColor = UAWPal.incendBack;
			status = StatusEffects.burning;
			makeFire = true;
			trailChance = 0.4f;
			trailColor = Color.lightGray;
		}};
		mediumCryo = new BasicBulletType(7, 15) {{
			height = 25;
			width = 10;
			knockback = 1.2f;
			hitEffect = Fx.hitBulletBig;
			despawnEffect = Fx.freezing;
			smokeEffect = Fx.shootBigSmoke2;
			shootEffect = new MultiEffect(UAWFxS.shootCryoFlame, Fx.shootBig2);
			hitEffect = despawnEffect = new MultiEffect(Fx.hitBulletBig, UAWFxS.cryoHit);
			frontColor = UAWPal.cryoFront;
			backColor = UAWPal.cryoBack;
			status = StatusEffects.freezing;
			trailChance = 0.4f;
			trailColor = Color.lightGray;
		}};

		heavyCopper = new TrailBulletType(10f, 55f) {{
			hitSize = 5;
			height = 30f;
			width = 10f;
			shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame);
			smokeEffect = Fx.shootBigSmoke;
			hitEffect = Fx.hitBulletBig;
			despawnHit = true;
			ammoMultiplier = 2;
			pierceCap = 2;
		}};
		heavyDense = new TrailBulletType(8f, 90) {{
			hitSize = 5;
			height = 25f;
			width = 15f;
			shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame);
			hitEffect = Fx.hitBulletBig;
			despawnHit = true;
			smokeEffect = Fx.shootBigSmoke;
			reloadMultiplier = 0.5f;
			ammoMultiplier = 2;
			knockback = 1.2f;
		}};
		heavyHoming = new TrailBulletType(10f, 55f) {{
			hitSize = 5;
			height = 30f;
			width = 10f;
			homingPower = 0.16f;
			shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame);
			smokeEffect = Fx.shootBigSmoke;
			hitEffect = Fx.hitBulletBig;
			despawnHit = true;
			ammoMultiplier = 2;
			pierceCap = 2;
		}};
		heavyThorium = new TrailBulletType(8f, 80) {{
			hitSize = 5;
			height = 30f;
			width = 16f;
			shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame);
			smokeEffect = Fx.shootBigSmoke;
			hitEffect = Fx.hitBulletBig;
			despawnHit = true;
			pierceCap = 2;
			pierceBuilding = true;
			knockback = 0.7f;
		}};
		heavySurge = new TrailBulletType(10f, 25f) {{
			hitSize = 6f;
			height = 22f;
			width = 10f;
			shootEffect = new MultiEffect(Fx.shootBig2, Fx.hitBulletBig, UAWFxS.shootSurgeFlame);
			armorIgnoreScl = 0.6f;
			smokeEffect = Fx.shootBigSmoke;
			ammoMultiplier = 2;
		}};
		heavyPiercing = new TrailBulletType(11f, 45f) {{
			hitSize = 6f;
			height = 20f;
			width = 8f;
			shootEffect = new MultiEffect(Fx.shootBig2, Fx.hitBulletBig, UAWFxS.shootSurgeFlame);
			hitEffect = Fx.hitBulletBig;
			despawnHit = true;
			armorIgnoreScl = 0.6f;
			smokeEffect = Fx.shootBigSmoke;
			ammoMultiplier = 2;
			pierce = true;
		}};

		buckshotLead = new BuckshotBulletType(4.5f, 10f) {{
			pierceCap = 3;
			lifetime = 30f;
			knockback = 6f;
			despawnHit = true;
			hitEffect = Fx.flakExplosion;
			shootEffect = UAWFxD.instShoot(32, Pal.bulletYellowBack);
			smokeEffect = Fx.shootSmallSmoke;
			ammoMultiplier = 3;
		}};
		buckshotIncend = new BuckshotBulletType(4.5f, 8f) {{
			height = width = 12f;
			pierceCap = 3;
			lifetime = 30f;
			knockback = 4f;
			shootEffect = UAWFxD.instShoot(32, Pal.lightishOrange);
			smokeEffect = Fx.shootSmallSmoke;
			hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.fireHit, Fx.fireSmoke);
			frontColor = Pal.lightishOrange;
			backColor = Pal.lightOrange;
			status = StatusEffects.burning;
		}};
		buckshotCryo = new BuckshotBulletType(4.5f, 8f) {{
			height = width = 12f;
			pierceCap = 3;
			lifetime = 30f;
			knockback = 4f;
			shootEffect = UAWFxD.instShoot(32, Color.valueOf("87CEEB"));
			smokeEffect = Fx.shootSmallSmoke;
			hitEffect = new MultiEffect(Fx.hitBulletSmall, UAWFxS.cryoHit, Fx.fireSmoke);
			frontColor = UAWPal.cryoFront;
			backColor = UAWPal.cryoBack;
			status = StatusEffects.freezing;
		}};

		buckshotMedium = new BuckshotBulletType(5f, 12f) {{
			knockback = 4f;
			despawnEffect = hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.burning, Fx.coalSmeltsmoke);
			shieldDamageMultiplier = 2.5f;
		}};
		buckshotMediumIncend = new BuckshotBulletType(5f, 8f) {{
			shootEffect = Fx.shootPyraFlame;
			smokeEffect = Fx.shootBigSmoke2;
			frontColor = Pal.lightishOrange;
			backColor = Pal.lightOrange;
			despawnEffect = hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.burning, Fx.fireHit);
			status = StatusEffects.burning;
			shieldDamageMultiplier = 1.4f;
		}};
		buckshotMediumCryo = new BuckshotBulletType(5f, 8f) {{
			shootEffect = UAWFxS.shootCryoFlame;
			smokeEffect = Fx.shootBigSmoke2;
			frontColor = UAWPal.cryoFront;
			backColor = UAWPal.cryoMiddle;
			despawnEffect = hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.freezing, UAWFxS.cryoHit);
			status = StatusEffects.freezing;
			shieldDamageMultiplier = 1.4f;
		}};
		buckshotMediumPiercing = new BuckshotBulletType(6f, 10f) {{
			height = width = 15;
			shrinkX = shrinkY = 0.5f;
			splashDamageRadius = 1.6f * tilesize;
			splashDamage = damage / 1.8f;
			pierceCap = 2;
			knockback = 2;
			trailLength = 0;
			trailInterval = 4.5f;
			trailColor = Color.lightGray;
			despawnEffect = shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame);
			smokeEffect = Fx.shootBigSmoke2;
			hitEffect = Fx.hitBulletBig;
			armorIgnoreScl = 0.4f;
		}};
		buckshotMediumFrag = new BuckshotBulletType(5f, 8f) {{
			splashDamageRadius = 1.8f * tilesize;
			splashDamage = damage / 2;
			fragBullets = 6;
			fragBullet = fragGlass;
		}};

		mineBasic = new MineBulletType(100, 8 * tilesize, 60 * 60) {{
			hitEffect = UAWFxD.dynamicExplosion(8 * tilesize);
			frontColor = Pal.bulletYellow;
			backColor = Pal.bulletYellowBack;
			status = StatusEffects.blasted;
			fragBullets = 16;
			fragBullet = flakGlassFrag;
		}};
//		mineIncend = new MineBulletType(75, 60, 7) {{
//			frontColor = UAWPal.incendFront;
//			backColor = UAWPal.incendBack;
//			hitEffect = despawnEffect = new MultiEffect(UAWFxD.crossBlast(splashDamageRadius, frontColor), Fx.blockExplosionSmoke);
//			fragBullets = 16;
//			fragAngle = 360;
//			status = StatusEffects.burning;
//			fragBullet = heavySlagShot;
//		}};
//		mineCryo = new MineBulletType(75, 60, 7) {{
//			frontColor = UAWPal.cryoFront;
//			backColor = UAWPal.cryoMiddle;
//			hitEffect = despawnEffect = new MultiEffect(UAWFxD.crossBlast(splashDamageRadius, backColor), Fx.blockExplosionSmoke);
//			fragBullets = 16;
//			fragAngle = 360;
//			status = StatusEffects.freezing;
//			fragBullet = heavyCryoShot;
//		}};
//		mineOil = new MineBulletType(75, 60, 7) {{
//			frontColor = Pal.plastaniumFront;
//			backColor = Pal.plastaniumBack;
//			hitEffect = despawnEffect = new MultiEffect(UAWFxD.crossBlast(splashDamageRadius, backColor), Fx.blockExplosionSmoke);
//			fragBullets = 16;
//			fragAngle = 360;
//			status = StatusEffects.tarred;
//			fragBullet = heavyOilShot;
//		}};
//		mineEMP = new MineBulletType(75, 60, 12) {{
//			frontColor = UAWPal.surgeFront;
//			backColor = UAWPal.surgeBack;
//			lightning = 8;
//			lightningDamage = 7;
//			lightningLength = 6;
//			lightningColor = Pal.lancerLaser;
//			hitEffect = despawnEffect = new MultiEffect(UAWFxD.crossBlast(splashDamageRadius, backColor), Fx.hitYellowLaser, Fx.blockExplosionSmoke);
//			explodeRange = 5;
//			status = StatusEffects.electrified;
//		}};
//		mineSpore = new MineBulletType(75, 60, 12) {{
//			frontColor = Pal.spore;
//			backColor = UAWPal.sporeMiddle;
//			status = StatusEffects.sporeSlowed;
//			hitEffect = despawnEffect = new MultiEffect(UAWFxD.crossBlast(splashDamageRadius, backColor), Fx.sporeSlowed, Fx.blockExplosionSmoke);
//		}};

		canisterBasic = new CanisterBulletType(2f, 30, 4, mineBasic) {{
			lifetime =
				ammoMultiplier = 5f;
		}};
		canisterCryo = new CanisterBulletType(2f, 30, 3, mineCryo) {{
			frontColor = UAWPal.cryoFront;
			backColor = UAWPal.cryoMiddle;
			lifetime = 160f;
			ammoMultiplier = 3f;
		}};
		canisterIncend = new CanisterBulletType(2f, 30, 3, mineIncend) {{
			frontColor = UAWPal.incendFront;
			backColor = UAWPal.incendBack;
			shootEffect = Fx.shootPyraFlame;
			lifetime = 160f;
			ammoMultiplier = 3f;
		}};
		canisterOil = new CanisterBulletType(2f, 30, 3, mineOil) {{
			frontColor = Pal.plastaniumFront;
			backColor = Pal.plastaniumBack;
			shootEffect = UAWFxS.shootPlastFlame;
			lifetime = 160f;
			ammoMultiplier = 3f;
		}};
		canisterEMP = new CanisterBulletType(2f, 30, 3, mineEMP) {{
			frontColor = UAWPal.surgeFront;
			backColor = UAWPal.surgeBack;
			shootEffect = UAWFxS.shootSurgeFlame;
			lifetime = 160f;
			ammoMultiplier = 3f;
		}};
		canisterSpore = new CanisterBulletType(2f, 30, 3, mineSpore) {{
			frontColor = Pal.spore;
			backColor = UAWPal.sporeMiddle;
			shootEffect = UAWFxS.shootSporeFlame;
			lifetime = 160f;
			ammoMultiplier = 3f;
		}};

	}
}
