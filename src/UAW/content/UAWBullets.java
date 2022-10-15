package UAW.content;

import UAW.audiovisual.*;
import UAW.entities.bullet.*;
import UAW.entities.bullet.ModdedVanillaBullet.SplashArtilleryBulletType;
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

	// Artillery
	artilleryMediumPlast,
		artilleryLargeAftershockBasic, artilleryLargeAftershockIncendiary, artilleryLargeAftershockCryo,
		artilleryLargeFrag,

	// Mines
	mineBasic, mineIncend, mineCryo, mineAA, mineEMP, mineSpore,

	// Mine Canister
	mineCanisterBasic, mineCanisterIncendiary, mineCanisterCryo, mineCanisterEMP, canisterSpore, canisterNuke;

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

		artilleryMediumPlast = new SplashArtilleryBulletType(1.5f, 350) {{
			height = 35f;
			width = height / 2;
			hitShake = 16f;
			lifetime = 120f;
			splashDamageRadius = 5 * tilesize;
			backColor = Pal.plastaniumBack;
			frontColor = Pal.plastaniumFront;
			hitEffect = UAWFx.massiveExplosion(frontColor, backColor);
		}};

		artilleryLargeAftershockBasic = new SplashArtilleryBulletType(2f, 4500) {{
			height = 45f;
			width = height / 2f;
			splashDamageRadius = 15 * tilesize;
			shootEffect = new MultiEffect(
				UAWFx.railShoot(height * 1.8f, backColor),
				UAWFx.burstCloud(frontColor),
				UAWFx.shootSmoke(width, frontColor)
			);
			hitEffect = UAWFx.dynamicExplosion(splashDamageRadius, frontColor, backColor);
			hitSound = Sfx.explosionHuge1;
			hitSoundVolume = 3f;
			hitShake = 34f;
			trailEffect = Fx.artilleryTrail;
			smokeEffect = Fx.smokeCloud;
			status = StatusEffects.burning;
			fragBullets = 1;
			fragBullet = new AftershockBulletType(1250, splashDamageRadius / 1.2f) {{
				splashAmount = 4;
				splashDelay = 45;
				lifetime = (splashDelay * splashAmount);
				statusDuration = 30f;
				frontColor = Pal.bulletYellow;
				backColor = Pal.bulletYellowBack;
				bottomColor = frontColor;
				hitShake = 28;
				particleEffect = UAWFx.effectHit(0.5f, frontColor, backColor);
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
				UAWFx.railShoot(height * 1.8f, backColor),
				UAWFx.burstCloud(frontColor),
				UAWFx.shootSmoke(width, frontColor)
			);
			hitEffect = UAWFx.dynamicExplosion(splashDamageRadius, frontColor, backColor);
			hitSound = Sfx.explosionHuge1;
			hitSoundVolume = 3f;
			hitShake = 34f;
			trailEffect = Fx.artilleryTrail;
			smokeEffect = Fx.smokeCloud;
			status = StatusEffects.freezing;
			fragBullets = 1;
			fragBullet = new AftershockBulletType(450, splashDamageRadius / 1.2f) {{
				splashAmount = 4;
				splashDelay = 45;
				lifetime = (splashDelay * splashAmount);
				frontColor = UAWPal.cryoFront;
				backColor = UAWPal.cryoBack;
				bottomColor = UAWPal.cryoFront;
				status = StatusEffects.freezing;
				statusDuration = 30f;
				particleEffect = UAWFx.effectHit(0.5f, frontColor, backColor);
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
				UAWFx.railShoot(height * 1.8f, backColor),
				UAWFx.burstCloud(frontColor),
				UAWFx.shootSmoke(width, frontColor)
			);
			hitEffect = UAWFx.dynamicExplosion(splashDamageRadius, frontColor, backColor);
			hitSound = Sfx.explosionHuge1;
			hitSoundVolume = 3f;
			hitShake = 34f;
			trailEffect = Fx.artilleryTrail;
			smokeEffect = Fx.smokeCloud;
			status = StatusEffects.burning;
			fragBullets = 1;
			fragBullet = new AftershockBulletType(450, splashDamageRadius / 1.2f) {{
				splashAmount = 4;
				splashDelay = 45;
				lifetime = (splashDelay * splashAmount);
				frontColor = Pal.lightishOrange;
				backColor = Pal.lightOrange;
				bottomColor = Pal.lightishOrange;
				status = StatusEffects.melting;
				statusDuration = 30f;
				particleEffect = UAWFx.effectHit(0.5f, frontColor, backColor);
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
				UAWFx.railShoot(height * 1.8f, backColor),
				UAWFx.burstCloud(frontColor),
				UAWFx.shootSmoke(height, frontColor)
			);
			hitEffect = new MultiEffect(
				Fx.hitBulletColor,
				UAWFx.crossBomb(width, 45, frontColor)
			);
			hitSoundVolume = 3f;
			hitShake = 34f;
			fragRandomSpread = 90;
			burstRange = 20 * tilesize;
			fragVelocityMin = 0.4f;
			fragVelocityMax = 1.2f;
			fragLifeMin = 0.8f;
			fragLifeMax = 1.4f;
		}};

		mineBasic = new MineBulletType(350, 8 * tilesize, 35 * tick) {{
			hitEffect = UAWFx.dynamicExplosion(splashDamageRadius);
			frontColor = Color.valueOf("eab678");
			backColor = Color.valueOf("d4816b");
			explodeDelay = 45f;
			explodeRange = 4 * tilesize;
			knockback = 16f;
		}};
		mineIncend = new MineBulletType(280, 10 * tilesize, 35 * tick) {{
			status = StatusEffects.burning;
			puddleAmount = 30;
			puddleLiquid = Liquids.oil;
			makeFire = true;
			frontColor = Color.valueOf("ffaa5f");
			backColor = Color.valueOf("d37f47");
			explodeDelay = 45f;
			explodeRange = 6 * tilesize;
			knockback = 8f;
			hitEffect = UAWFx.dynamicExplosion(splashDamageRadius, frontColor, backColor);
		}};
		mineCryo = new MineBulletType(280, 10 * tilesize, 35 * tick) {{
			status = StatusEffects.freezing;
			frontColor = UAWPal.cryoFront;
			backColor = UAWPal.cryoMiddle;
			explodeDelay = 45f;
			explodeRange = 6f * tilesize;
			knockback = 8f;
			hitEffect = UAWFx.dynamicExplosion(splashDamageRadius, frontColor, backColor);
		}};
		mineEMP = new MineBulletType(200, 10 * tilesize, 30 * tick) {{
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
			hitEffect = UAWFx.empExplosion(splashDamageRadius, 6, frontColor);
		}};

		mineCanisterBasic = new CanisterBulletType(mineBasic) {{
			ammoMultiplier = 2f;
		}};
		mineCanisterIncendiary = new CanisterBulletType(mineIncend, 3) {{
			ammoMultiplier = 2f;
			frontColor = Pal.lightishOrange;
			backColor = Pal.lightOrange;
		}};
		mineCanisterCryo = new CanisterBulletType(mineCryo, 3) {{
			ammoMultiplier = 2f;
			frontColor = UAWPal.cryoFront;
			backColor = UAWPal.cryoMiddle;
		}};
		mineCanisterEMP = new CanisterBulletType(mineEMP, 3) {{
			ammoMultiplier = 2f;
			frontColor = UAWPal.titaniumFront;
			backColor = UAWPal.titaniumBack;
		}};


	}
}
