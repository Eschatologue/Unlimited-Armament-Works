package UAW.content;

import UAW.entities.bullet.*;
import UAW.entities.bullet.ModdedVanillaBullet.*;
import UAW.graphics.*;
import arc.graphics.Color;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;

import static UAW.Vars.tick;
import static mindustry.Vars.tilesize;

public class UAWBullets {
	public static BulletType placeholder,

	/**
	 * Basic > Dense > Piercing > Fragmentation > Clustering > Incendiary > Cryo > Oil > Surge
	 */

	// Vanilla
	fragGlassFrag, heavySlagShot, heavyCryoShot, heavyOilShot,

	// Standard - Basic
	smallBasic, smallDense, smallPiercing, smallIncendiary, smallCryo,

	// Standard - Medium
	mediumBasic, mediumDense, mediumPiercing, mediumIncendiary, mediumCryo, mediumSurge,

	// Standard - Large
	largeBasic, largeDense, largePiercing, largeIncendiary, largeCryo, largeSurge,

	// Buckshot - Small
	buckshotSmallBasic, buckshotSmallDense, buckshotSmallIncendiary, buckshotSmallCryo,

	// Buckshot - Medium
	buckshotMediumBasic, buckshotMediumDense, buckshotMediumPiercing, buckshotMediumFrag, buckshotMediumIncendiary, buckshotMediumCryo,

	// Buckshot - Large
	buckshotLargeBasic, buckshotLargeDense, buckshotLargeFrag, buckshotLargeOil, buckshotLargeIncendiary, buckshotLargeCryo, buckshotLargeSurge,

	// Artillery
	artilleryMediumPlast,
		artilleryLargeAftershockBasic, artilleryLargeAftershockIncendiary, artilleryLargeAftershockCryo,
		artilleryLargeFrag,

	// Mines
	mineBasic, mineIncend, mineCryo, mineAA, mineEMP, mineSpore,

	// Mine Canister
	canisterBasic, canisterIncend, canisterCryo, canisterEMP, canisterSpore, canisterNuke;

	public static void load() {

		// region Vanilla
		fragGlassFrag = new BasicBulletType(3f, 5, "bullet") {{
			width = 5f;
			height = 12f;
			shrinkY = 1f;
			lifetime = 20f;
			backColor = Pal.gray;
			frontColor = Color.white;
			despawnEffect = Fx.none;
		}};
		heavySlagShot = new LiquidBulletType(Liquids.slag) {{
			lifetime = 49f;
			speed = 4f;
			knockback = 1.3f;
			puddleSize = 8f;
			orbSize = 4f;
			damage = 4.75f;
			drag = 0.001f;
			ammoMultiplier = 0.4f;
			statusDuration = 60f * 4f;
		}};
		heavyCryoShot = new LiquidBulletType(Liquids.cryofluid) {{
			lifetime = 49f;
			speed = 4f;
			knockback = 1.3f;
			puddleSize = 8f;
			orbSize = 4f;
			drag = 0.001f;
			ammoMultiplier = 0.4f;
			statusDuration = 60f * 4f;
			damage = 0.2f;
		}};
		heavyOilShot = new LiquidBulletType(Liquids.oil) {{
			lifetime = 49f;
			speed = 4f;
			knockback = 1.3f;
			puddleSize = 8f;
			orbSize = 4f;
			drag = 0.001f;
			ammoMultiplier = 0.4f;
			statusDuration = 60f * 4f;
			damage = 0.2f;
		}};
		// endregion Vanilla

		buckshotSmallBasic = new BuckshotBulletType(4.5f, 12f) {{
			pierceCap = 3;
			lifetime = 30f;
			knockback = 5f;
			despawnHit = true;
			hitEffect = Fx.flakExplosion;
			shootEffect = UAWFxD.instShoot(32, Pal.bulletYellowBack);
			smokeEffect = Fx.shootSmallSmoke;
			ammoMultiplier = 3;
		}};
		buckshotSmallDense = new BuckshotBulletType(3f, 16f) {{
			pierceCap = 4;
			lifetime = 30f;
			knockback = 8f;
			reloadMultiplier = 0.5f;
			despawnHit = true;
			hitEffect = Fx.flakExplosion;
			shootEffect = UAWFxD.instShoot(32, Pal.bulletYellowBack);
			smokeEffect = Fx.shootSmallSmoke;
			ammoMultiplier = 3;
		}};
		buckshotSmallIncendiary = new BuckshotBulletType(4.5f, 9f) {{
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
		buckshotSmallCryo = new BuckshotBulletType(4.5f, 9f) {{
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

		buckshotMediumBasic = new BuckshotBulletType(7f, 12f) {{
			knockback = 4f;
			despawnEffect = hitEffect = new MultiEffect(Fx.hitBulletSmall);
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
			pierceArmor = true;
		}};
		buckshotMediumFrag = new BuckshotBulletType(7f, 9f) {{
			splashDamageRadius = 1.8f * tilesize;
			splashDamage = damage / 2;
			fragBullets = 6;
			fragBullet = fragGlassFrag;
		}};
		buckshotMediumIncendiary = new BuckshotBulletType(7f, 9f) {{
			shootEffect = Fx.shootPyraFlame;
			smokeEffect = Fx.shootBigSmoke2;
			frontColor = Pal.lightishOrange;
			backColor = Pal.lightOrange;
			despawnEffect = hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.burning, Fx.fireHit);
			status = StatusEffects.burning;
		}};
		buckshotMediumCryo = new BuckshotBulletType(7f, 9f) {{
			shootEffect = UAWFxS.shootCryoFlame;
			smokeEffect = Fx.shootBigSmoke2;
			frontColor = UAWPal.cryoFront;
			backColor = UAWPal.cryoMiddle;
			despawnEffect = hitEffect = new MultiEffect(
				UAWFxD.hitBulletBig(UAWPal.cryoMiddle),
				Fx.freezing,
				UAWFxS.cryoHit
			);
			status = StatusEffects.freezing;
		}};

		buckshotLargeIncendiary = new BuckshotBulletType(25, 6.5f, 30f) {{
			pierceCap = 3;
			frontColor = Pal.lightishOrange;
			backColor = Pal.lightOrange;
			smokeEffect = new MultiEffect(Fx.smokeCloud, Fx.shootBigSmoke2);
			hitEffect = new MultiEffect(Fx.blastExplosion, Fx.fireHit, Fx.blastsmoke);
			shootEffect = new MultiEffect(UAWFxD.instShoot(64, frontColor), Fx.shootPyraFlame);
			despawnEffect = UAWFxD.blastExplosion(frontColor, backColor);
			status = StatusEffects.burning;
			fragBullets = 6;
			fragBullet = heavySlagShot;
			fragVelocityMin = 0.3f;
			fragVelocityMax = fragVelocityMin * 1.2f;
			fragLifeMin = 0.2f;
			fragLifeMax = 0.5f;
			ammoMultiplier = 4f;
		}};
		buckshotLargeCryo = new BuckshotBulletType(25, 6.5f, 30f) {{
			pierceCap = 3;
			frontColor = UAWPal.cryoFront;
			backColor = UAWPal.cryoBack;
			smokeEffect = new MultiEffect(Fx.smokeCloud, Fx.shootBigSmoke2);
			hitEffect = new MultiEffect(Fx.hitBulletBig, UAWFxS.cryoHit, Fx.freezing);
			shootEffect = new MultiEffect(UAWFxD.instShoot(64, frontColor), UAWFxS.shootCryoFlame);
			despawnEffect = UAWFxD.blastExplosion(frontColor, backColor);
			status = StatusEffects.freezing;
			fragBullets = 6;
			fragBullet = heavyCryoShot;
			fragVelocityMin = 0.3f;
			fragVelocityMax = fragVelocityMin * 1.2f;
			fragLifeMin = 0.2f;
			fragLifeMax = 0.5f;
			ammoMultiplier = 4f;
		}};
		buckshotLargeOil = new BuckshotBulletType(25, 6.5f, 30f) {{
			pierceCap = 3;
			frontColor = Pal.plastaniumFront;
			backColor = Pal.plastaniumBack;
			smokeEffect = new MultiEffect(Fx.smokeCloud, Fx.shootBigSmoke2);
			hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.oily);
			shootEffect = new MultiEffect(UAWFxD.instShoot(64, backColor), Fx.shootPyraFlame);
			despawnEffect = UAWFxD.blastExplosion(frontColor, backColor);
			status = StatusEffects.tarred;
			fragBullets = 8;
			fragBullet = heavyOilShot;
			fragVelocityMin = 0.3f;
			fragVelocityMax = fragVelocityMin * 1.2f;
			fragLifeMin = 0.2f;
			fragLifeMax = 0.5f;
			ammoMultiplier = 4f;
		}};
		buckshotLargeSurge = new BuckshotBulletType(25, 6.5f, 30f) {{
			splashDamage = 2.5f * tilesize;
			pierceCap = 2;
			frontColor = Color.white;
			backColor = Pal.lancerLaser;
			smokeEffect = new MultiEffect(Fx.smokeCloud, Fx.shootBigSmoke2);
			hitEffect = new MultiEffect(UAWFxS.hitLaserSpark, Fx.hitLancer);
			shootEffect = new MultiEffect(UAWFxD.instShoot(56, backColor));
			despawnEffect = UAWFxD.empExplosion(splashDamageRadius, 3, backColor);
			status = UAWStatusEffects.EMP;
			hitSound = Sounds.plasmaboom;
			statusDuration = 0.3f * 60;
			ammoMultiplier = 6f;
		}};

		artilleryMediumPlast = new SplashArtilleryBulletType(1.5f, 350) {{
			height = 35f;
			width = height / 2;
			hitShake = 16f;
			lifetime = 120f;
			splashDamageRadius = 5 * tilesize;
			backColor = Pal.plastaniumBack;
			frontColor = Pal.plastaniumFront;
			hitEffect = UAWFxD.massiveExplosion(frontColor, backColor);
		}};

		artilleryLargeAftershockBasic = new SplashArtilleryBulletType(2f, 4500) {{
			height = 45f;
			width = height / 2f;
			splashDamageRadius = 15 * tilesize;
			shootEffect = new MultiEffect(
				UAWFxD.railShoot(width, backColor),
				UAWFxD.impactCloud(frontColor),
				UAWFxD.shootBigSmoke(width, frontColor)
			);
			hitEffect = UAWFxD.dynamicExplosion(splashDamageRadius, frontColor, backColor);
			hitSound = UAWSfx.explosionHuge1;
			hitSoundVolume = 3f;
			hitShake = 34f;
			trailEffect = Fx.artilleryTrail;
			smokeEffect = Fx.smokeCloud;
			status = StatusEffects.burning;
			fragBullets = 1;
			fragBullet = new SplashBulletType(1250, splashDamageRadius / 1.2f) {{
				splashAmount = 4;
				splashDelay = 45;
				lifetime = (splashDelay * splashAmount);
				statusDuration = 30f;
				frontColor = Pal.bulletYellow;
				backColor = Pal.bulletYellowBack;
				bottomColor = frontColor;
				hitShake = 28;
				particleEffect = UAWFxD.effectHit(0.5f, frontColor, backColor);
				makeFire = true;
				applySound = Sounds.explosionbig;
			}};
		}};
		artilleryLargeAftershockCryo = new SplashArtilleryBulletType(2f, 2500) {{
			height = 45f;
			width = height / 2;
			splashDamageRadius = 14 * tilesize;
			frontColor = UAWPal.cryoFront;
			backColor = UAWPal.cryoBack;
			shootEffect = new MultiEffect(
				UAWFxD.railShoot(height, backColor),
				UAWFxD.impactCloud(frontColor),
				UAWFxD.shootBigSmoke(width, frontColor)
			);
			hitEffect = UAWFxD.dynamicExplosion(splashDamageRadius, frontColor, backColor);
			hitSound = UAWSfx.explosionHuge1;
			hitSoundVolume = 3f;
			hitShake = 34f;
			trailEffect = Fx.artilleryTrail;
			smokeEffect = Fx.smokeCloud;
			status = StatusEffects.freezing;
			fragBullets = 1;
			fragBullet = new SplashBulletType(450, splashDamageRadius / 1.2f) {{
				splashAmount = 4;
				splashDelay = 45;
				lifetime = (splashDelay * splashAmount);
				frontColor = UAWPal.cryoFront;
				backColor = UAWPal.cryoBack;
				bottomColor = UAWPal.cryoFront;
				status = StatusEffects.freezing;
				statusDuration = 30f;
				particleEffect = UAWFxD.effectHit(0.5f, frontColor, backColor);
				makeFire = true;
				applySound = Sounds.plasmaboom;
			}};
		}};
		artilleryLargeAftershockIncendiary = new SplashArtilleryBulletType(2f, 2500) {{
			height = 45;
			width = height / 2;
			splashDamageRadius = 14 * tilesize;
			frontColor = Pal.lightishOrange;
			backColor = Pal.lightOrange;
			shootEffect = new MultiEffect(
				UAWFxD.railShoot(width, backColor),
				UAWFxD.impactCloud(frontColor),
				UAWFxD.shootBigSmoke(width, frontColor)
			);
			hitEffect = UAWFxD.dynamicExplosion(splashDamageRadius, frontColor, backColor);
			hitSound = UAWSfx.explosionHuge1;
			hitSoundVolume = 3f;
			hitShake = 34f;
			trailEffect = Fx.artilleryTrail;
			smokeEffect = Fx.smokeCloud;
			status = StatusEffects.burning;
			fragBullets = 1;
			fragBullet = new SplashBulletType(450, splashDamageRadius / 1.2f) {{
				splashAmount = 4;
				splashDelay = 45;
				lifetime = (splashDelay * splashAmount);
				frontColor = Pal.lightishOrange;
				backColor = Pal.lightOrange;
				bottomColor = Pal.lightishOrange;
				status = StatusEffects.melting;
				statusDuration = 30f;
				particleEffect = UAWFxD.effectHit(0.5f, frontColor, backColor);
				makeFire = true;
				applySound = Sounds.plasmaboom;
			}};
		}};
		artilleryLargeFrag = new CanisterBulletType(artilleryMediumPlast, 36) {{
			height = 45;
			width = height / 2;
			backColor = Pal.plastaniumBack;
			frontColor = Pal.plastaniumFront;
			shootEffect = new MultiEffect(
				UAWFxD.railShoot(height, backColor),
				UAWFxD.impactCloud(frontColor),
				UAWFxD.shootBigSmoke(height, frontColor)
			);
			hitEffect = new MultiEffect(
				UAWFxD.hitBulletBig(frontColor),
				UAWFxD.crossBlast(width, frontColor)
			);
			hitSoundVolume = 3f;
			hitShake = 34f;
			fragAngle = 60;
			burstRange = 20 * tilesize;
			fragVelocityMin = 0.4f;
			fragVelocityMax = 1.2f;
			fragLifeMin = 0.8f;
			fragLifeMax = 1.4f;
		}};

		mineBasic = new MineBulletType(150, 8 * tilesize, 35 * tick) {{
			hitEffect = UAWFxD.dynamicExplosion(splashDamageRadius);
			frontColor = Color.valueOf("eab678");
			backColor = Color.valueOf("d4816b");
			explodeDelay = 45f;
			explodeRange = 4 * tilesize;
			knockback = 16f;
		}};
		mineIncend = new MineBulletType(125, 10 * tilesize, 35 * tick) {{
			status = StatusEffects.burning;
			puddleAmount = 30;
			puddleLiquid = Liquids.oil;
			makeFire = true;
			frontColor = Color.valueOf("ffaa5f");
			backColor = Color.valueOf("d37f47");
			explodeDelay = 45f;
			explodeRange = 6 * tilesize;
			knockback = 8f;
			hitEffect = UAWFxD.dynamicExplosion(splashDamageRadius, frontColor, backColor);
		}};
		mineCryo = new MineBulletType(125, 10 * tilesize, 35 * tick) {{
			status = StatusEffects.freezing;
			frontColor = UAWPal.cryoFront;
			backColor = UAWPal.cryoMiddle;
			explodeDelay = 45f;
			explodeRange = 6f * tilesize;
			knockback = 8f;
			hitEffect = UAWFxD.dynamicExplosion(splashDamageRadius, frontColor, backColor);
		}};
		mineEMP = new MineBulletType(75, 10 * tilesize, 30 * tick) {{
			status = UAWStatusEffects.EMP;
			collidesAir = true;
			statusDuration = 120f;
			frontColor = UAWPal.titaniumFront;
			backColor = UAWPal.titaniumBack;
			explodeDelay = 50f;
			explodeRange = 7f * tilesize;
			lightningColor = frontColor;
			lightning = 15;
			lightningDamage = 5;
			lightningLength = 12;
			lightningLengthRand = 7;
			hitSound = Sounds.plasmaboom;
			hitEffect = UAWFxD.empExplosion(splashDamageRadius, 6, frontColor);
		}};

		canisterBasic = new CanisterBulletType(mineBasic) {{
			ammoMultiplier = 2f;

		}};
		canisterIncend = new CanisterBulletType(mineIncend, 3) {{
			ammoMultiplier = 2f;
			frontColor = Pal.lightishOrange;
			backColor = Pal.lightOrange;
		}};
		canisterCryo = new CanisterBulletType(mineCryo, 3) {{
			ammoMultiplier = 2f;
			frontColor = UAWPal.cryoFront;
			backColor = UAWPal.cryoMiddle;
		}};
		canisterEMP = new CanisterBulletType(mineEMP, 3) {{
			ammoMultiplier = 2f;
			frontColor = UAWPal.titaniumFront;
			backColor = UAWPal.titaniumBack;
		}};


	}
}
