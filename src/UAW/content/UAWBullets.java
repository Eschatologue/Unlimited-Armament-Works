package UAW.content;

import UAW.entities.bullet.*;
import UAW.graphics.*;
import arc.graphics.Color;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;

import static mindustry.Vars.tilesize;
import static mindustry.content.Bullets.*;

public class UAWBullets implements ContentList {
	public static BulletType placeholder,

	// Standard / Basic
	smallCopper, smallDense, smallPiercing, smallIncendiary, smallCryo,
		mediumPiercing, mediumStandard, mediumSurge, mediumIncendiary, mediumCryo,
		heavyCopper, heavyDense, heavyHoming, heavyThorium, heavySurge, heavyPiercing, heavyIncendiary, heavyCryo,

	// Buckshots
	buckshotLead, buckshotIncend, buckshotCryo,
		buckshotMedium, buckshotMediumIncend, buckshotMediumCryo, buckshotMediumPiercing, buckshotMediumFrag,
		buckshotLarge, buckshotLargeIncend, buckshotLargeCryo, buckshotLargePlast, buckshotLargeFrag, buckshotLargeEMP,

	// Mines
	mineBasic, mineIncend, mineCryo, mineAA, mineEMP, mineSpore,

	// Mine Canister
	canisterBasic, canisterIncend, canisterCryo, canisterEMP, canisterSpore, canisterNuke;

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
			shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame, UAWFxS.muzzleBreakShootSmoke);
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
			shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame, UAWFxS.muzzleBreakShootSmoke);
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
			shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame, UAWFxS.muzzleBreakShootSmoke);
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
			shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame, UAWFxS.muzzleBreakShootSmoke);
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
			shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame, UAWFxS.muzzleBreakShootSmoke);
			armorIgnoreScl = 0.6f;
			smokeEffect = Fx.shootBigSmoke;
			ammoMultiplier = 2;
		}};
		heavyPiercing = new TrailBulletType(11f, 45f) {{
			hitSize = 6f;
			height = 20f;
			width = 8f;
			shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame, UAWFxS.muzzleBreakShootSmoke);
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

		buckshotMedium = new BuckshotBulletType(7f, 12f) {{
			knockback = 4f;
			despawnEffect = hitEffect = new MultiEffect(Fx.burning, Fx.coalSmeltsmoke);
			shieldDamageMultiplier = 2.5f;
		}};
		buckshotMediumIncend = new BuckshotBulletType(7f, 9f) {{
			shootEffect = Fx.shootPyraFlame;
			smokeEffect = Fx.shootBigSmoke2;
			frontColor = Pal.lightishOrange;
			backColor = Pal.lightOrange;
			despawnEffect = hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.burning, Fx.fireHit);
			status = StatusEffects.burning;
			shieldDamageMultiplier = 1.4f;
		}};
		buckshotMediumCryo = new BuckshotBulletType(7f, 9f) {{
			shootEffect = UAWFxS.shootCryoFlame;
			smokeEffect = Fx.shootBigSmoke2;
			frontColor = UAWPal.cryoFront;
			backColor = UAWPal.cryoMiddle;
			despawnEffect = hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.freezing, UAWFxS.cryoHit);
			status = StatusEffects.freezing;
			shieldDamageMultiplier = 1.4f;
		}};
		buckshotMediumPiercing = new BuckshotBulletType(8f, 12f) {{
			height = width = 15;
			shrinkX = shrinkY = 0.5f;
			splashDamageRadius = 1.6f * tilesize;
			splashDamage = damage / 1.8f;
			pierceCap = 2;
			knockback = 2;
			trailLength = 0;
			trailInterval = 4.5f;
			trailColor = Color.lightGray;
			despawnEffect = shootEffect = new MultiEffect(Fx.sparkShoot);
			smokeEffect = Fx.shootBigSmoke2;
			hitEffect = Fx.hitBulletBig;
			armorIgnoreScl = 0.4f;
		}};
		buckshotMediumFrag = new BuckshotBulletType(7f, 9f) {{
			splashDamageRadius = 1.8f * tilesize;
			splashDamage = damage / 2;
			fragBullets = 6;
			fragBullet = fragGlassFrag;
		}};

		buckshotLargeIncend = new BuckshotBulletType(6.5f, 30f) {{
			height = width = 30;
			pierceCap = 3;
			frontColor = Pal.lightishOrange;
			backColor = Pal.lightOrange;
			smokeEffect = new MultiEffect(Fx.smokeCloud, Fx.shootBigSmoke2);
			hitEffect = new MultiEffect(Fx.blastExplosion, Fx.fireHit, Fx.blastsmoke);
			shootEffect = new MultiEffect(UAWFxD.instShoot(64, backColor), Fx.shootPyraFlame);
			despawnEffect = UAWFxD.thermalExplosion(frontColor, backColor);
			status = StatusEffects.burning;
			fragBullets = 8;
			fragBullet = heavySlagShot;
			fragVelocityMin = 0.3f;
			fragVelocityMax = fragVelocityMin * 1.2f;
			fragLifeMin = 0.4f;
			fragLifeMax = 0.8f;
			shieldDamageMultiplier = 1.5f;
			ammoMultiplier = 4f;
		}};
		buckshotLargeCryo = new BuckshotBulletType(6.5f, 30f) {{
			height = width = 30;
			pierceCap = 3;
			frontColor = UAWPal.cryoFront;
			backColor = UAWPal.cryoBack;
			smokeEffect = new MultiEffect(Fx.smokeCloud, Fx.shootBigSmoke2);
			hitEffect = new MultiEffect(Fx.hitBulletBig, UAWFxS.cryoHit, Fx.freezing);
			shootEffect = new MultiEffect(UAWFxD.instShoot(64, frontColor), UAWFxS.shootCryoFlame);
			despawnEffect = UAWFxD.thermalExplosion(frontColor, backColor);
			status = StatusEffects.freezing;
			fragBullets = 8;
			fragBullet = heavyCryoShot;
			fragVelocityMin = 0.3f;
			fragVelocityMax = fragVelocityMin * 1.2f;
			fragLifeMin = 0.4f;
			fragLifeMax = 0.8f;
			shieldDamageMultiplier = 1.5f;
			ammoMultiplier = 4f;
		}};
		buckshotLargePlast = new BuckshotBulletType(6.5f, 30f) {{
			height = width = 30;
			pierceCap = 3;
			frontColor = Pal.plastaniumFront;
			backColor = Pal.plastaniumBack;
			smokeEffect = new MultiEffect(Fx.smokeCloud, Fx.shootBigSmoke2);
			hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.oily);
			shootEffect = new MultiEffect(UAWFxD.instShoot(64, backColor), Fx.shootPyraFlame);
			despawnEffect = UAWFxD.thermalExplosion(frontColor, backColor);
			status = StatusEffects.tarred;
			fragBullets = 8;
			fragBullet = heavyOilShot;
			fragVelocityMin = 0.3f;
			fragVelocityMax = fragVelocityMin * 1.2f;
			fragLifeMin = 0.4f;
			fragLifeMax = 0.8f;
			shieldDamageMultiplier = 2.8f;
			ammoMultiplier = 4f;
		}};
		buckshotLargeEMP = new BuckshotBulletType(6.5f, 30f) {{
			height = width = 30;
			splashDamage = 2.5f * tilesize;
			pierceCap = 2;
			frontColor = Color.white;
			backColor = Pal.lancerLaser;
			smokeEffect = new MultiEffect(Fx.smokeCloud, Fx.shootBigSmoke2);
			hitEffect = new MultiEffect(UAWFxS.hitLaserSpark, Fx.hitLancer);
			shootEffect = new MultiEffect(UAWFxD.instShoot(56, backColor));
			despawnEffect = UAWFxD.empBlast(splashDamageRadius, 3, backColor);
			status = UAWStatusEffects.EMP;
			hitSound = Sounds.plasmaboom;
			statusDuration = 0.3f * 60;
			ammoMultiplier = 6f;
		}};

		mineBasic = new MineBulletType(150, 8 * tilesize, 35 * 60) {{
			hitEffect = UAWFxD.dynamicExplosion(splashDamageRadius);
			frontColor = Color.valueOf("eab678");
			backColor = Color.valueOf("d4816b");
			explodeDelay = 45f;
			explodeRange = 4 * tilesize;
			knockback = 16f;
		}};
		mineIncend = new MineBulletType(125, 10 * tilesize, 35 * 60) {{
			status = StatusEffects.burning;
			puddleAmount = 30;
			puddleLiquid = Liquids.oil;
			makeFire = true;
			frontColor = Color.valueOf("ffaa5f");
			backColor = Color.valueOf("d37f47");
			explodeDelay = 45f;
			explodeRange = 6 * tilesize;
			knockback = 8f;
			hitEffect = UAWFxD.dynamicExplosion2(splashDamageRadius, frontColor, backColor);
		}};
		mineCryo = new MineBulletType(125, 10 * tilesize, 35 * 60) {{
			status = StatusEffects.freezing;
			frontColor = UAWPal.cryoFront;
			backColor = UAWPal.cryoMiddle;
			explodeDelay = 45f;
			explodeRange = 6f * tilesize;
			knockback = 8f;
			hitEffect = UAWFxD.dynamicExplosion2(splashDamageRadius, frontColor, backColor);
		}};
		mineEMP = new MineBulletType(75, 10 * tilesize, 30 * 60) {{
			status = UAWStatusEffects.EMP;
			collidesAir = true;
			statusDuration = 120f;
			frontColor = UAWPal.titaniumBlueFront;
			backColor = UAWPal.titaniumBlueBack;
			explodeDelay = 50f;
			explodeRange = 7f * tilesize;
			lightningColor = frontColor;
			lightning = 15;
			lightningDamage = 5;
			lightningLength = 12;
			lightningLengthRand = 7;
			hitSound = Sounds.plasmaboom;
			hitEffect = UAWFxD.empBlast(splashDamageRadius, 6, frontColor);
		}};

		canisterBasic = new MineCanisterBulletType(mineBasic) {{
			ammoMultiplier = 2f;
		}};
		canisterIncend = new MineCanisterBulletType(mineIncend, 3) {{
			ammoMultiplier = 2f;
			frontColor = Pal.lightishOrange;
			backColor = Pal.lightOrange;
		}};
		canisterCryo = new MineCanisterBulletType(mineCryo, 3) {{
			ammoMultiplier = 2f;
			frontColor = UAWPal.cryoFront;
			backColor = UAWPal.cryoMiddle;
		}};
		canisterEMP = new MineCanisterBulletType(mineEMP, 3) {{
			ammoMultiplier = 2f;
			frontColor = UAWPal.titaniumBlueFront;
			backColor = UAWPal.titaniumBlueBack;
		}};


	}
}
