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
		standardPiercing, standardCryo,
		mediumPiercing, mediumStandard, mediumSurge, mediumIncendiary, mediumCryo,
		heavyCopper, heavyDense, heavyHoming, heavyThorium, heavySurge, heavyPiercing, heavyIncendiary, heavyCryo,
		basicBeam, heavyBeam,
		EMPartillery,
		standardCruiseMissile, piercingCruiseMissile, cryoCruiseMissile, incendCruiseMissile, surgeCruiseMissile,
		buckshotLead, buckshotIncend, buckshotCryo,
		mineBasic, mineIncend, mineCryo, mineOil, mineEMP, mineSpore,
		canisterBasic, canisterIncend, canisterCryo, canisterOil, canisterEMP, canisterSpore, canisterNuke;

	@Override
	public void load() {
		standardPiercing = new TrailBulletType(12f, 10f) {{
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
			shootEffect = new MultiEffect(UAWFxStatic.shootCryoFlame, Fx.shootBig2);
			hitEffect = despawnEffect = new MultiEffect(Fx.hitBulletBig, UAWFxStatic.cryoHit);
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
			ammoMultiplier = 2;
			pierceCap = 2;
		}};
		heavyDense = new TrailBulletType(8f, 90) {{
			hitSize = 5;
			height = 25f;
			width = 15f;
			shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame);
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
			ammoMultiplier = 2;
			pierceCap = 2;
		}};
		heavyThorium = new TrailBulletType(8f, 80) {{
			hitSize = 5;
			height = 30f;
			width = 16f;
			shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame);
			smokeEffect = Fx.shootBigSmoke;
			pierceCap = 2;
			pierceBuilding = true;
			knockback = 0.7f;
		}};
		heavySurge = new TrailBulletType(12f, 25f) {{
			height = 30f;
			width = 10f;
			shootEffect = new MultiEffect(Fx.shootBig2, Fx.hitBulletBig, UAWFxStatic.shootSurgeFlame);
			armorIgnoreScl = 0.6f;
			smokeEffect = Fx.shootBigSmoke;
			ammoMultiplier = 2;
		}};
		heavyPiercing = new TrailBulletType(12f, 25f) {{
			height = 30f;
			width = 10f;
			shootEffect = new MultiEffect(Fx.shootBig2, Fx.hitBulletBig, UAWFxStatic.shootSurgeFlame);
			armorIgnoreScl = 0.6f;
			smokeEffect = Fx.shootBigSmoke;
			ammoMultiplier = 2;
		}};

		mineBasic = new MineBulletType(100, 90, 9) {{
			frontColor = Pal.bulletYellow;
			backColor = Pal.bulletYellowBack;
			hitEffect = despawnEffect = new MultiEffect(UAWFxDynamic.crossBlast(splashDamageRadius, backColor), Fx.blockExplosionSmoke);
			status = StatusEffects.blasted;
			fragBullets = 16;
			fragBullet = flakGlassFrag;
		}};
		mineIncend = new MineBulletType(75, 60, 7) {{
			frontColor = UAWPal.incendFront;
			backColor = UAWPal.incendBack;
			hitEffect = despawnEffect = new MultiEffect(UAWFxDynamic.crossBlast(splashDamageRadius, frontColor), Fx.blockExplosionSmoke);
			fragBullets = 16;
			fragAngle = 360;
			status = StatusEffects.burning;
			fragBullet = heavySlagShot;
		}};
		mineCryo = new MineBulletType(75, 60, 7) {{
			frontColor = UAWPal.cryoFront;
			backColor = UAWPal.cryoMiddle;
			hitEffect = despawnEffect = new MultiEffect(UAWFxDynamic.crossBlast(splashDamageRadius, backColor), Fx.blockExplosionSmoke);
			fragBullets = 16;
			fragAngle = 360;
			status = StatusEffects.freezing;
			fragBullet = heavyCryoShot;
		}};
		mineOil = new MineBulletType(75, 60, 7) {{
			frontColor = Pal.plastaniumFront;
			backColor = Pal.plastaniumBack;
			hitEffect = despawnEffect = new MultiEffect(UAWFxDynamic.crossBlast(splashDamageRadius, backColor), Fx.blockExplosionSmoke);
			fragBullets = 16;
			fragAngle = 360;
			status = StatusEffects.tarred;
			fragBullet = heavyOilShot;
		}};
		mineEMP = new MineBulletType(75, 60, 12) {{
			frontColor = UAWPal.surgeFront;
			backColor = UAWPal.surgeBack;
			lightning = 8;
			lightningDamage = 7;
			lightningLength = 6;
			lightningColor = Pal.lancerLaser;
			hitEffect = despawnEffect = new MultiEffect(UAWFxDynamic.crossBlast(splashDamageRadius, backColor), Fx.hitYellowLaser, Fx.blockExplosionSmoke);
			blockDetonationRange = 5;
			status = StatusEffects.electrified;
		}};
		mineSpore = new MineBulletType(75, 60, 12) {{
			frontColor = Pal.spore;
			backColor = UAWPal.sporeMiddle;
			status = StatusEffects.sporeSlowed;
			hitEffect = despawnEffect = new MultiEffect(UAWFxDynamic.crossBlast(splashDamageRadius, backColor), Fx.sporeSlowed, Fx.blockExplosionSmoke);
		}};

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
			shootEffect = UAWFxStatic.shootPlastFlame;
			lifetime = 160f;
			ammoMultiplier = 3f;
		}};
		canisterEMP = new CanisterBulletType(2f, 30, 3, mineEMP) {{
			frontColor = UAWPal.surgeFront;
			backColor = UAWPal.surgeBack;
			shootEffect = UAWFxStatic.shootSurgeFlame;
			lifetime = 160f;
			ammoMultiplier = 3f;
		}};
		canisterSpore = new CanisterBulletType(2f, 30, 3, mineSpore) {{
			frontColor = Pal.spore;
			backColor = UAWPal.sporeMiddle;
			shootEffect = UAWFxStatic.shootSporeFlame;
			lifetime = 160f;
			ammoMultiplier = 3f;
		}};

	}
}
