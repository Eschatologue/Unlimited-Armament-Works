package UAW.content;

import UAW.entities.bullet.*;
import UAW.graphics.*;
import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;

import static mindustry.Vars.tilesize;
import static mindustry.content.Bullets.*;

public class UAWBullets implements ContentList {
    public static BulletType
    // Normal Bullets
    basicPiercing, basicMelt, basicCryo,
    mediumStandard, mediumSurge, mediumPiercing,
    heavyCopper, heavyPiercing, heavySurge, heavyMelt, heavyCryo, heavyHoming,
    // Beam
    basicBeam, heavyBeam,
    // Artillery
    EMPartillery,
    // Missiles
    standardMissile,
    standardCruiseMissile, piercingCruiseMissile, cryoCruiseMissile, incendCruiseMissile, surgeCruiseMissile,
    // Buckshot
    buckshotLead, buckshotIncend, buckshotCryo,
    /// Mines
    mineBasic, mineIncend, mineCryo, mineOil, mineEMP, mineSpore,
    // Unit Weapons
    standardMG,
    // Canister
    canisterBasic, canisterIncend, canisterCryo, canisterOil, canisterEMP, canisterSpore, canisterNuke
    ;

    @Override
    public void load () {
        // region Basic
        basicPiercing = new BasicBulletType(6f, 10f, "bullet") {{
            height = 15f;
            width = 8f;
            lifetime = 120f;
            pierce = true;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
            ammoMultiplier = 2;
            reloadMultiplier = 0.8f;
            trailLength = 10;
            trailWidth = 0.8f;
            trailColor = backColor;
        }};
        basicMelt = new BasicBulletType(3f, 12f, "bullet") {{
            height = 18f;
            width = 12f;
            frontColor = Pal.lightishOrange;
            backColor = Pal.lightOrange;
            status = StatusEffects.melting;
            inaccuracy = 3f;
            lifetime = 90f;
        }};
        basicCryo = new BasicBulletType(3f, 12f, "bullet") {{
            height = 18f;
            width = 12f;
            frontColor = Color.valueOf("C0ECFF");
            backColor = Color.valueOf("87CEEB");
            status = StatusEffects.freezing;
            inaccuracy = 3f;
            lifetime = 90f;
        }};
        // endregion
        // region Heavy
        heavyCopper = new BasicBulletType(6f, 30f, "bullet") {{
            height = 24f;
            width = 12f;
            shootEffect = Fx.shootBig;
            smokeEffect = Fx.shootBigSmoke;
            ammoMultiplier = 3;
        }};
        heavyPiercing = new BasicBulletType(12f, 25f, "bullet") {{
            height = 30f;
            width = 10f;
            shootEffect = Fx.shootBig;
            smokeEffect = Fx.shootBigSmoke;
            ammoMultiplier = 2;
            pierce = true;
            trailLength = 12;
            trailWidth = width / 4;
            trailColor = backColor;
        }};
        heavySurge = new BasicBulletType(4f, 20f, "bullet") {{
            height = 24f;
            width = 11f;
            shootEffect = Fx.shootBig;
            smokeEffect = Fx.shootBigSmoke;
            ammoMultiplier = 3;
            lifetime = 80f;
            lightning = 3;
            lightningLength = 14;
        }};
        heavyMelt = new BasicBulletType(7f, 60, "bullet") {{
            height = 24f;
            width = 14f;
            frontColor = Pal.lightishOrange;
            backColor = Pal.lightOrange;
            status = StatusEffects.melting;
            inaccuracy = 3f;
        }};
        heavyCryo = new BasicBulletType(7f, 60f, "bullet") {{
            height = 24f;
            width = 14f;
            frontColor = Color.valueOf("C0ECFF");
            backColor = Color.valueOf("87CEEB");
            status = StatusEffects.freezing;
            inaccuracy = 3f;
        }};
        heavyHoming = new FlakBulletType(6f, 55f) {{
            height = 24f;
            width = 14f;
            splashDamageRadius = 16;
            splashDamage = 40;
            explodeRange = 8 * 2.5f;
            trailChance = 0.8f;
            trailColor = Color.gray;
            homingPower = 4;
            homingRange = 64;
            homingDelay = 30;
        }};
        // endregion
        // region Beam
        basicBeam = new LaserBoltBulletType(6f, 10f) {{
            height = 14f;
            width = 7f;
            lifetime = 45f;
            frontColor = UAWPal.titaniumBlueFront;
            backColor = UAWPal.titaniumBlueBack;
            hitEffect = Fx.hitLaser;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
        }};
        heavyBeam = new BasicBulletType(2.5f, 50f) {{
            height = 32f;
            width = 15f;
            lifetime = 280f;
            frontColor = UAWPal.titaniumBlueFront;
            backColor = UAWPal.titaniumBlueBack;
            status = StatusEffects.melting;
            hitEffect = Fx.hitLaser;
            shootEffect = Fx.lancerLaserShoot;
            smokeEffect = Fx.lancerLaserShootSmoke;
        }};
        // endregion
        // region Artillery
        EMPartillery = new ArtilleryBulletType(2f, 20f, "shell") {{
            hitEffect = Fx.hitYellowLaser;
            lifetime = 80f;
            width = height = 14f;
            collidesTiles = false;
            ammoMultiplier = 4f;
            splashDamageRadius = 45f;
            splashDamage = 50f;
            lightning = 8;
            lightningLength = 16;
            backColor = Pal.missileYellowBack;
            frontColor = Pal.missileYellow;
            status = UAWStatusEffects.concussion;
            statusDuration = 30f;
        }};
        // endregion
        // region Buckshots
        buckshotLead = new BasicBulletType(4.5f, 10f) {{
            pierceCap = 3;
            lifetime = 30f;
            knockback = 6f;
            despawnEffect = hitEffect = Fx.flakExplosion;
            shootEffect = UAWFxDynamic.instShoot(Pal.bulletYellowBack, 32);
            smokeEffect = Fx.shootSmallSmoke;
            ammoMultiplier = 3;
        }};
        buckshotIncend = new BasicBulletType(4.5f, 8f) {{
            height = width = 12f;
            pierceCap = 3;
            lifetime = 30f;
            knockback = 4f;
            shootEffect = UAWFxDynamic.instShoot(Pal.lightishOrange, 32);
            smokeEffect = Fx.shootSmallSmoke;
            despawnEffect = hitEffect = Fx.blastExplosion;
            frontColor = Pal.lightishOrange;
            backColor = Pal.lightOrange;
            status = StatusEffects.burning;
        }};
        buckshotCryo = new BasicBulletType(4.5f, 8f) {{
            height = width = 12f;
            pierceCap = 3;
            lifetime = 30f;
            knockback = 4f;
            shootEffect = UAWFxDynamic.instShoot(Color.valueOf("87CEEB"), 32);
            smokeEffect = Fx.shootSmallSmoke;
            despawnEffect = hitEffect = UAWFxDynamic.thermalExplosion(UAWPal.cryoFront, UAWPal.cryoMiddle);
            frontColor = UAWPal.cryoFront;
            backColor = UAWPal.cryoBack;
            status = StatusEffects.freezing;
        }};
        // endregion
        // region Cruise Missile
        standardCruiseMissile = new CruiseMissileBulletType(1.8f, 225) {{
            splashDamage = damage;
            splashDamageRadius = 8 * tilesize;
            homingRange = 60 * tilesize;
            homingPower = 0.5f;
            hitEffect = new MultiEffect(UAWFxDynamic.crossBlast(backColor, splashDamageRadius), Fx.blastExplosion, Fx.blockExplosionSmoke);
            status = StatusEffects.blasted;
        }};
        piercingCruiseMissile = new CruiseMissileBulletType(2.2f, 325) {{
            splashDamage = damage;
            splashDamageRadius = 3 * tilesize;
            homingRange = 60 * tilesize;
            hitEffect = new MultiEffect(Fx.hitFuse, Fx.blastExplosion, Fx.smokeCloud, Fx.shootLiquid);
            hitSound = Sounds.plasmaboom;
            fragBullets = 1;
            fragCone = 0.001f;
            fragBullet = new ShrapnelBulletType() {{
                serrations = 1;
                splashDamageRadius = 16;
                damage = 400;
                length = 64;
                width = 12;
                fromColor = Pal.lightOrange;
                toColor = Pal.lightishOrange;
            }};
        }};
        cryoCruiseMissile = new CruiseMissileBulletType(1.8f, 200) {{
            splashDamage = damage;
            splashDamageRadius = 8 * tilesize;
            homingRange = 60 * tilesize;
            frontColor = UAWPal.cryoFront;
            backColor = UAWPal.cryoBack;
            hitEffect = new MultiEffect(UAWFxDynamic.crossBlast(backColor, splashDamageRadius), Fx.blastExplosion, Fx.blockExplosionSmoke);
            status = StatusEffects.freezing;
        }};
        incendCruiseMissile = new CruiseMissileBulletType(1.8f, 200) {{
            splashDamage = damage;
            splashDamageRadius = 8 * tilesize;
            homingRange = 60 * tilesize;
            frontColor = UAWPal.incendFront;
            backColor = UAWPal.incendBack;
            hitEffect = new MultiEffect(UAWFxDynamic.crossBlast(frontColor, splashDamageRadius), Fx.blastExplosion, Fx.blockExplosionSmoke);
            status = StatusEffects.melting;
        }};
        surgeCruiseMissile = new CruiseMissileBulletType(1.8f, 350) {{
            splashDamage = damage;
            splashDamageRadius = 8 * tilesize;
            homingRange = 60 * tilesize;
            frontColor = UAWPal.surgeFront;
            backColor = UAWPal.surgeBack;
            hitEffect = new MultiEffect(UAWFxDynamic.crossBlast(frontColor, splashDamageRadius), Fx.blastExplosion, Fx.blockExplosionSmoke);
            status = StatusEffects.electrified;

            lightning = 8;
            lightningLength = 8;
            lightningDamage = splashDamage / 5;
        }};
        // endregion
        // region Mine
        mineBasic = new MineBulletType(100, 90, 9) {{
            frontColor = Pal.bulletYellow;
            backColor = Pal.bulletYellowBack;
            hitEffect = despawnEffect = new MultiEffect(UAWFxDynamic.crossBlast(backColor, splashDamageRadius), Fx.blockExplosionSmoke);
            status = StatusEffects.blasted;
            fragBullets = 16;
            fragBullet = flakGlassFrag;
        }};
        mineIncend = new MineBulletType(75, 60, 7) {{
            frontColor = UAWPal.incendFront;
            backColor = UAWPal.incendBack;
            hitEffect = despawnEffect = new MultiEffect(UAWFxDynamic.crossBlast(frontColor, splashDamageRadius), Fx.blockExplosionSmoke);
            fragBullets = 16;
            fragAngle = 360;
            status = StatusEffects.burning;
            fragBullet = heavySlagShot;
        }};
        mineCryo = new MineBulletType(75, 60, 7) {{
            frontColor = UAWPal.cryoFront;
            backColor = UAWPal.cryoMiddle;
            hitEffect = despawnEffect = new MultiEffect(UAWFxDynamic.crossBlast(backColor, splashDamageRadius), Fx.blockExplosionSmoke);
            fragBullets = 16;
            fragAngle = 360;
            status = StatusEffects.freezing;
            fragBullet = heavyCryoShot;
        }};
        mineOil = new MineBulletType(75, 60, 7) {{
            frontColor = Pal.plastaniumFront;
            backColor = Pal.plastaniumBack;
            hitEffect = despawnEffect = new MultiEffect(UAWFxDynamic.crossBlast(backColor, splashDamageRadius), Fx.blockExplosionSmoke);
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
            hitEffect = despawnEffect = new MultiEffect(UAWFxDynamic.crossBlast(backColor, splashDamageRadius),Fx.hitYellowLaser, Fx.blockExplosionSmoke);
            blockDetonationRange = 5;
            status = StatusEffects.electrified;
        }};
        mineSpore = new MineBulletType(75, 60, 12) {{
            frontColor = Pal.spore;
            backColor = UAWPal.sporeMiddle;
            status = StatusEffects.sporeSlowed;
            hitEffect = despawnEffect = new MultiEffect(UAWFxDynamic.crossBlast(backColor, splashDamageRadius),Fx.sporeSlowed, Fx.blockExplosionSmoke);
        }};
        // endregion
        // region Unit Bullets
        // endregion
        // region Carriers

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

        // endregion
    }
}
