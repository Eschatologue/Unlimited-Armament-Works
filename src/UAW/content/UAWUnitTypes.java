package UAW.content;

import UAW.entities.abilities.RazorRotorAbility;
import UAW.entities.bullet.*;
import UAW.graphics.*;
import UAW.type.*;
import UAW.type.weapon.RecoilingWeapon;
import arc.graphics.Color;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.type.weapons.PointDefenseWeapon;
import mindustry.world.meta.BlockFlag;

import static mindustry.Vars.tilesize;
import static mindustry.content.Bullets.*;

public class UAWUnitTypes implements ContentList {
    public static UnitType
            aglovale, bedivere, calogrenant, dagonet, esclabor,
            escutcheon,
            clurit, kujang, kerambit, cetbang, kiAmuk,
            hatsuharu, shiratsuyu, kagero, shimakaze,
            gardlacz, arkabuz, armata, twardy;

    @Override
    public void load() {
        aglovale = new CopterUnitType("aglovale") {{
            health = 450;
            hitSize = 18;
            speed = 2.8f;
            accel = 0.04f;
            drag = 0.016f;
            rotateSpeed = 5.5f;
            ammoType = new ItemAmmoType(Items.graphite);
            circleTarget = true;

            faceTarget = flying = true;
            range = 30 * tilesize;
            maxRange = range;
            spinningFall = true;

            itemCapacity = 5;

            rotors.add(
                    new Rotor(name + "-blade") {{
                        x = y = 0;
                        rotationSpeed = -14f;
                        bladeCount = 3;
                    }}
            );
            weapons.add(
                    new Weapon("uaw-missile-small-red") {{
                        rotate = false;
                        mirror = true;
                        shootCone = 90;
                        x = 5f;
                        y = -3f;
                        inaccuracy = 4;
                        reload = 12;
                        shootSound = Sounds.missile;
                        bullet = new MissileBulletType(6f, 35) {{
                            width = 6f;
                            height = 12f;
                            shrinkY = 0f;
                            drag = -0.003f;
                            homingRange = 60f;
                            keepVelocity = false;
                            splashDamageRadius = 25f;
                            splashDamage = 8f;
                            lifetime = range / lifetime + 80;
                            backColor = Pal.bulletYellowBack;
                            frontColor = Pal.bulletYellow;
                            hitEffect = Fx.blastExplosion;
                            despawnEffect = Fx.blastExplosion;
                            trailColor = Color.gray;
                            trailChance = 0.7f;
                            shootCone = 90;
                            weaveMag = 4;
                            weaveScale = 4;
                        }};
                    }},
                    new Weapon("uaw-machine-gun-small-red") {{
                        rotate = false;
                        mirror = true;
                        shootCone = 90;
                        inaccuracy = 3f;
                        top = false;
                        x = 6f;
                        y = 2.8f;
                        reload = 6f;
                        shootSound = Sounds.shoot;
                        ejectEffect = Fx.casing1;
                        bullet = new BasicBulletType(6f, 20) {{
                            height = 18f;
                            pierce = true;
                            pierceCap = 2;
                            buildingDamageMultiplier = 0.4f;
                            width = 9f;
                            maxRange = range;
                            homingRange = 60f;
                            lifetime = (range / speed) * 1.4f;
                            trailLength = 15;
                            trailWidth = 1.6f;
                            trailColor = backColor;
                        }};
                    }}
            );
        }};
        bedivere = new CopterUnitType("bedivere") {{
            health = 4000;
            hitSize = 30;
            speed = 2.5f;
            rotateSpeed = 4.5f;
            accel = 0.08f;
            drag = 0.03f;
            ammoType = new ItemAmmoType(Items.graphite);
            spinningFall = faceTarget = flying = circleTarget = true;

            range = 35 * tilesize;
            maxRange = range;

            itemCapacity = 10;

            rotors.add(
                    new Rotor(name + "-blade") {{
                        x = 0;
                        y = 2;
                        rotationSpeed = -12f;
                        bladeCount = 4;
                    }}
            );
            weapons.add(
                    new Weapon("uaw-machine-gun-small-red") {{
                        rotate = top = false;
                        shootCone = 90;
                        inaccuracy = 3f;
                        alternate = mirror = true;
                        x = 7f;
                        y = 10f;
                        reload = 4;
                        recoil = 0f;
                        shootSound = Sounds.shoot;
                        ejectEffect = Fx.casing1;
                        bullet = new BasicBulletType(8f, 25) {{
                            height = 19f;
                            width = 9f;
                            pierce = true;
                            pierceCap = 2;
                            buildingDamageMultiplier = 0.4f;
                            maxRange = range - 8;
                            homingRange = 60f;
                            lifetime = (range / speed) * 1.5f;
                            trailLength = 15;
                            trailWidth = 1.5f;
                            trailColor = backColor;
                            hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.fireHit);
                            status = StatusEffects.burning;
                        }};
                    }},
                    new Weapon("uaw-missile-medium-red-2") {{
                        rotate = false;
                        alternate = mirror = true;
                        shootCone = 45;
                        top = true;
                        x = 7f;
                        y = -1.5f;
                        inaccuracy = 15;
                        maxRange = range;
                        reload = 80f;
                        recoil = 2f;
                        firstShotDelay = 20f;
                        shootSound = UAWSfx.cruiseMissileShoot1;
                        bullet = new CruiseMissileBulletType(3f, 80) {{
                            homingRange = 120f;
                            homingPower = 0.05f;
                            keepVelocity = false;
                            splashDamageRadius = 8 * tilesize;
                            splashDamage = damage;
                            lifetime = range / speed + 45;
                            shootEffect = UAWFxStatic.shootSurgeFlame;
                            hitEffect = despawnEffect = UAWFxDynamic.dynamicExplosion(splashDamageRadius);
                            status = StatusEffects.slow;
                            statusDuration = 2 * 60;
                        }};
                    }}

            );
            abilities.add(new RazorRotorAbility(35, 0.3f, 4 * tilesize));
        }};
        calogrenant = new CopterUnitType("calogrenant") {{
            float rotX = 17;
            float rotY = 8;
            float rotSpeed = 12f;
            health = 9500;
            hitSize = 32;
            speed = 2f;
            rotateSpeed = 4f;
            accel = 0.06f;
            drag = 0.04f;
            ammoType = new ItemAmmoType(Items.graphite);
            faceTarget = flying = true;

            range = 35 * tilesize;
            maxRange = range;

            itemCapacity = 15;
            weapons.add(
                    new PointDefenseWeapon("uaw-point-defense-red") {{
                        rotate = autoTarget = true;
                        mirror = controllable = false;
                        x = 0f;
                        y = 3f;
                        reload = 2f;
                        recoil = 0f;
                        targetInterval = 3f;
                        targetSwitchInterval = 3f;
                        ejectEffect = Fx.casing1;
                        bullet = new BulletType() {{
                            shootEffect = Fx.sparkShoot;
                            smokeEffect = Fx.shootSmallSmoke;
                            hitEffect = Fx.pointHit;
                            maxRange = range / 2f;
                            damage = 25f;
                        }};
                    }},
                    new Weapon("uaw-machine-gun-medium-red") {{
                        rotate = false;
                        inaccuracy = 3f;
                        mirror = true;
                        x = 8f;
                        y = 12f;
                        reload = 7;
                        recoil = 1f;
                        shootSound = Sounds.shoot;
                        ejectEffect = Fx.casing2;
                        bullet = new BasicBulletType(7f, 25) {{
                            height = 10f;
                            width = 6f;
                            pierce = true;
                            pierceCap = 2;
                            buildingDamageMultiplier = 0.3f;
                            maxRange = range - 16;
                            lifetime = (range / speed) * 0.7f;
                            trailLength = 10;
                            trailWidth = width / 3;
                            trailColor = backColor;
                            hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.shootSmallSmoke);
                        }};
                    }},
                    new Weapon("uaw-artillery-small-red") {{
                        mirror = alternate = true;
                        rotate = false;
                        x = 11f;
                        y = 0f;
                        inaccuracy = 8f;
                        shootCone = 30;
                        rotateSpeed = 2.2f;
                        reload = 1.5f * 60;
                        recoil = 2.2f;
                        shootSound = Sounds.shootBig;
                        shake = 2.5f;
                        ejectEffect = UAWFxStatic.casing3Long;
                        bullet = new UAWPointBulletType() {{
                            damage = 400;
                            speed = range;
                            splashDamage = 200;
                            splashDamageRadius = 3 * 8;
                            shootEffect = new MultiEffect(UAWFxDynamic.railShoot(Pal.bulletYellowBack, 16), Fx.blockExplosionSmoke);
                            smokeEffect = Fx.smokeCloud;
                            trailEffect = UAWFxDynamic.railTrail(Pal.bulletYellowBack);
                            hitEffect = despawnEffect = new MultiEffect(UAWFxDynamic.crossBlast(Pal.bulletYellowBack, splashDamageRadius), UAWFxDynamic.dynamicExplosion(splashDamageRadius));
                            trailSpacing = 20f;
                            shieldDamageMultiplier = 1.5f;
                            buildingDamageMultiplier = 0.5f;
                            hitShake = 6f;
                            ammoMultiplier = 1f;
                        }};
                    }},
                    new Weapon() {{
                        rotate = false;
                        shootCone = 45;
                        top = true;
                        x = y = 0f;
                        inaccuracy = 15;
                        maxRange = range;
                        reload = 80f;
                        recoil = 2f;
                        firstShotDelay = 20f;
                        shootSound = UAWSfx.cruiseMissileShoot1;
                        bullet = new CruiseMissileBulletType(3f, 260) {{
                            layer = Layer.flyingUnitLow - 1;
                            homingRange = 120f;
                            homingPower = 0.05f;
                            keepVelocity = false;
                            splashDamageRadius = 8 * tilesize;
                            splashDamage = damage;
                            lifetime = range / speed + 45;
                            shootEffect = UAWFxStatic.shootSurgeFlame;
                            hitEffect = despawnEffect = UAWFxDynamic.dynamicExplosion(splashDamageRadius);
                            trailEffect = UAWFxStatic.pyraSmokeTrailUnder;
                            status = StatusEffects.slow;
                            statusDuration = 2 * 60;
                        }};
                    }}
            );
            rotors.add(
                    new Rotor("uaw-short-blade") {{
                        x = -rotX;
                        y = rotY;
                        rotationSpeed = -rotSpeed;
                        bladeCount = 3;
                        drawRotorTop = false;
                    }},
                    new Rotor("uaw-short-blade") {{
                        x = -rotX;
                        y = rotY;
                        rotationSpeed = rotSpeed;
                        bladeCount = 3;
                    }},
                    new Rotor("uaw-short-blade") {{
                        x = rotX;
                        y = rotY;
                        rotationSpeed = rotSpeed;
                        bladeCount = 3;
                        drawRotorTop = false;
                    }},
                    new Rotor("uaw-short-blade") {{
                        x = rotX;
                        y = rotY;
                        rotationSpeed = -rotSpeed;
                        bladeCount = 3;
                    }}
            );
        }};

        clurit = new UnitType("clurit") {{
            health = 750;
            speed = 0.75f;
            accel = 0.2f;
            rotateSpeed = 1.8f;
            drag = 0.05f;
            hitSize = 20;
            armor = 5;
            range = 30 * tilesize;
            maxRange = range;
            rotateShooting = false;
            ammoType = new ItemAmmoType(Items.graphite, 2);

            trailLength = 22;
            trailX = 7f;
            trailY = -9f;
            trailScl = 1.4f;

            constructor = UnitWaterMove::create;

            weapons.add(
                    new PointDefenseWeapon("uaw-point-defense-purple") {{
                        rotate = autoTarget = true;
                        mirror = controllable = false;
                        x = 0;
                        y = 8f;
                        reload = 2.5f;
                        recoil = 0.1f;
                        targetInterval = 10f;
                        targetSwitchInterval = 15f;
                        ejectEffect = Fx.casing1;

                        bullet = new BulletType() {{
                            shootEffect = Fx.sparkShoot;
                            hitEffect = Fx.pointHit;
                            maxRange = 10f * tilesize;
                            damage = 10f;
                        }};
                    }},
                    new Weapon("uaw-machine-gun-small-purple") {{
                        rotate = mirror = autoTarget = true;
                        controllable = false;
                        x = 5f;
                        y = 0.4f;
                        inaccuracy = 4f;
                        reload = 10f;
                        shootSound = Sounds.shoot;
                        ejectEffect = Fx.casing2;
                        bullet = standardHoming;
                    }},
                    new Weapon("uaw-artillery-small-purple") {{
                        mirror = rotate = alternate = true;
                        x = 5.5f;
                        y = -8f;
                        targetFlags = new BlockFlag[]{BlockFlag.turret, null};
                        inaccuracy = 8f;
                        shootCone = 30;
                        rotateSpeed = 2.2f;
                        reload = 1.5f * 60;
                        recoil = 2.2f;
                        shootSound = Sounds.artillery;
                        shake = 2.5f;
                        bullet = new AntiBuildingBulletType(2f, 35, 2.5f) {{
                            splashDamageRadius = 6 * tilesize;
                            size = 20;
                            lifetime = range / speed;
                            status = StatusEffects.burning;
                            incendChance = 0.5f;
                            incendSpread = 16f;
                            makeFire = true;

                            frontColor = Pal.sapBullet;
                            backColor = Pal.sapBulletBack;
                            shootEffect = new MultiEffect(Fx.shootBig2, UAWFxStatic.shootSporeFlame);
                            smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.shootLiquid);
                            despawnEffect = hitEffect = new MultiEffect(
                                    UAWFxDynamic.dynamicExplosion(splashDamageRadius)
                            );
                        }};
                    }}
            );
        }};
        kujang = new UnitType("kujang") {{
            health = 7000;
            hitSize = 30;
            speed = 0.65f;
            drag = 0.05f;
            accel = 0.2f;
            rotateSpeed = 1.5f;
            rotateShooting = false;
            range = 40 * tilesize;
            maxRange = range;
            ammoType = new ItemAmmoType(Items.graphite, 2);

            trailLength = 45;
            trailX = 9f;
            trailY = -9f;
            trailScl = 2.8f;

            constructor = UnitWaterMove::create;

            weapons.add(
                    new PointDefenseWeapon("uaw-point-defense-purple") {{
                        mirror = false;
                        targetAir = true;
                        x = 0f;
                        y = 13f;
                        recoil = 0.5f;
                        reload = 3f;
                        targetInterval = 10f;
                        targetSwitchInterval = 15f;
                        ejectEffect = Fx.casing1;

                        bullet = new BulletType() {{
                            shootEffect = Fx.sparkShoot;
                            hitEffect = Fx.pointHit;
                            maxRange = 15f * tilesize;
                            damage = 10f;
                        }};
                    }},
                    new Weapon("uaw-machine-gun-small-purple") {{
                        rotate = mirror = autoTarget = alternate = true;
                        controllable = false;
                        x = 8f;
                        y = 0.4f;
                        inaccuracy = 4f;
                        reload = 12f;
                        shootSound = Sounds.shoot;
                        ejectEffect = Fx.casing2;
                        bullet = fragGlass;
                    }},
                    new Weapon("uaw-artillery-medium-purple") {{
                        rotate = true;
                        mirror = false;
                        rotateSpeed = 1f;
                        x = 0f;
                        y = -12f;
                        targetFlags = new BlockFlag[]{BlockFlag.turret, null};
                        inaccuracy = 8f;
                        reload = 4f * 60;
                        recoil = 3f;
                        shootSound = Sounds.artillery;
                        shootCone = 30f;
                        shake = 5;
                        bullet = new AntiBuildingBulletType(2f, 120, 3.5f) {{
                            splashDamageRadius = 8 * tilesize;
                            size = 38;
                            lifetime = range / speed;
                            status = StatusEffects.burning;
                            incendChance = 0.8f;
                            incendSpread = 16f;
                            makeFire = true;

                            frontColor = Pal.sapBullet;
                            backColor = Pal.sapBulletBack;
                            shootEffect = new MultiEffect(Fx.shootBig2, UAWFxStatic.shootSporeFlame);
                            smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.shootLiquid);
                            despawnEffect = hitEffect = new MultiEffect(
                                    UAWFxDynamic.dynamicExplosion(splashDamageRadius)
                            );
                        }};
                    }}
            );
        }};

        hatsuharu = new UnitType("hatsuharu") {{
            health = 650;
            speed = 1.2f;
            accel = 0.2f;
            rotateSpeed = 1.9f;
            drag = 0.05f;
            hitSize = 18;
            range = 35 * tilesize;
            maxRange = range;
            rotateShooting = false;
            ammoType = new ItemAmmoType(Items.graphite, 2);

            trailLength = 20;
            trailX = 6f;
            trailY = -4f;
            trailScl = 1.9f;

            constructor = UnitWaterMove::create;

            weapons.add(
                    new Weapon() {{
                        maxRange = range;
                        rotate = false;
                        alternate = mirror = false;
                        shootCone = 180;
                        x = 0f;
                        y = 6f;
                        reload = 4f * 60;
                        inaccuracy = 1f;
                        ammoType = new ItemAmmoType(Items.thorium);
                        targetAir = false;

                        shootSound = UAWSfx.torpedoShoot1;

                        bullet = new TorpedoBulletType(1.8f, 450) {{
                            shootEffect = UAWFxStatic.shootWaterFlame;
                            lifetime = range / speed;
                            homingRange = range;
                            hitSizeDamageScl = 3.5f;
                            maxEnemyHitSize = 35;
                        }};
                    }},
                    new PointDefenseWeapon("uaw-point-defense-red") {{
                        rotate = autoTarget = true;
                        mirror = controllable = false;
                        x = 0f;
                        y = -7f;
                        reload = 4f;
                        recoil = 0f;
                        targetInterval = 4f;
                        targetSwitchInterval = 4f;
                        ejectEffect = Fx.casing1;
                        bullet = new BulletType() {{
                            shootEffect = Fx.sparkShoot;
                            smokeEffect = Fx.shootSmallSmoke;
                            hitEffect = Fx.pointHit;
                            maxRange = range / 3.5f;
                            damage = 15f;
                        }};
                    }}
            );
        }};
        shiratsuyu = new UnitType("shiratsuyu") {{
            health = 5500;
            speed = 1f;
            accel = 0.2f;
            rotateSpeed = 1.8f;
            drag = 0.17f;
            hitSize = 22f;
            armor = 5f;
            rotateShooting = false;
            range = 45 * tilesize;
            maxRange = range;
            ammoType = new ItemAmmoType(Items.graphite, 2);

            trailLength = 25;
            trailX = 8f;
            trailY = -9f;
            trailScl = 2.2f;

            constructor = UnitWaterMove::create;

            weapons.add(
                    new Weapon() {{
                        maxRange = range;
                        rotate = false;
                        alternate = mirror = true;
                        shootCone = 180;
                        x = 8f;
                        y = 6f;
                        reload = 3f * 60;
                        inaccuracy = 1f;
                        ammoType = new ItemAmmoType(Items.thorium);
                        targetAir = false;

                        shootSound = UAWSfx.torpedoShoot1;

                        bullet = new TorpedoBulletType(1.8f, 650) {{
                            shootEffect = UAWFxStatic.shootWaterFlame;
                            lifetime = range / speed;
                            homingRange = range;
                            maxEnemyHitSize = 45;
                            hitSizeDamageScl = 4;
                        }};
                    }},
                    new PointDefenseWeapon("uaw-point-defense-red") {{
                        rotate = autoTarget = true;
                        mirror = controllable = false;
                        x = 0f;
                        y = 9f;
                        reload = 3f;
                        recoil = 0f;
                        targetInterval = 3f;
                        targetSwitchInterval = 3f;
                        ejectEffect = Fx.casing1;
                        bullet = new BulletType() {{
                            shootEffect = Fx.sparkShoot;
                            smokeEffect = Fx.shootSmallSmoke;
                            hitEffect = Fx.pointHit;
                            maxRange = range / 3f;
                            damage = 15f;
                        }};
                    }},
                    new Weapon("uaw-machine-gun-medium-red") {{
                        rotate = true;
                        inaccuracy = 3f;
                        mirror = true;
                        x = 8f;
                        y = -5f;
                        reload = 7;
                        recoil = 1f;
                        shootSound = Sounds.shoot;
                        ejectEffect = Fx.casing2;
                        bullet = new BasicBulletType(7f, 25) {{
                            height = 10f;
                            width = 6f;
                            pierce = true;
                            pierceCap = 2;
                            buildingDamageMultiplier = 0.3f;
                            maxRange = range - 16;
                            lifetime = (range / speed) * 0.7f;
                            trailLength = 10;
                            trailWidth = width / 3;
                            trailColor = backColor;
                            hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.shootSmallSmoke);
                        }};
                    }},
                    new Weapon("uaw-missile-medium-red-single") {{
                        reload = 45f;
                        x = 0f;
                        y = -8f;
                        rotateSpeed = 4f;
                        rotate = true;
                        mirror = false;
                        shots = 2;
                        shotDelay = 6f;
                        inaccuracy = 5f;
                        velocityRnd = 0.1f;
                        shootSound = Sounds.missile;
                        ammoType = new ItemAmmoType(Items.thorium);
                        ejectEffect = Fx.none;
                        bullet = new MissileBulletType(5f, 60) {{
                            height = 12;
                            width = height / 2;
                            homingRange = 60f;
                            keepVelocity = false;
                            splashDamageRadius = 4 * tilesize;
                            splashDamage = damage * 1.5f;
                            lifetime = range / speed * 0.8f;
                            trailColor = Color.gray;
                            backColor = Pal.bulletYellowBack;
                            frontColor = Pal.bulletYellow;
                            despawnEffect = hitEffect = new MultiEffect(Fx.blastExplosion, Fx.blastsmoke);
                            buildingDamageMultiplier = 0.5f;
                            weaveScale = 8f;
                            weaveMag = 1f;
                        }};
                    }}
            );
        }};

        gardlacz = new TankUnitType("gardlacz") {{
            health = 750;
            armor = 20;
            hitSize = 18;
            speed = 1.3f;
            rotateSpeed = 2.5f;
            ammoType = new ItemAmmoType(Items.graphite);
            targetAir = false;

            accel = 0.05f;
            drag = 0.055f;
            range = 36 * tilesize;
            rotateShooting = false;
            trailChance = 0.3f;

            weapons.add(
                    new PointDefenseWeapon("uaw-point-defense-red") {{
                        rotate = autoTarget = true;
                        mirror = controllable = false;
                        x = 0f;
                        y = 5f;
                        reload = 1.5f;
                        rotateSpeed = 5.5f;
                        recoil = 0.1f;
                        targetInterval = 6f;
                        targetSwitchInterval = 7.5f;
                        ejectEffect = Fx.casing1;

                        bullet = new BulletType() {{
                            shootEffect = Fx.sparkShoot;
                            hitEffect = Fx.pointHit;
                            maxRange = range / 2f;
                            damage = 8f;
                        }};
                    }},
                    new RecoilingWeapon(name + "-gun") {{
                        targetFlags = new BlockFlag[]{BlockFlag.extinguisher, null};
                        rotate = true;
                        mirror = false;
                        rotateSpeed = 2.6f;
                        x = 0f;
                        y = -4.3f;
                        shootY = 16f;
                        reload = 1.5f * 60;
                        recoil = 4.5f;
                        shootSound = UAWSfx.cannonShoot1;
                        ejectEffect = UAWFxStatic.casing2Long;
                        shake = 3f;
                        bullet = new TrailBulletType(7f, 85) {{
                            height = 25f;
                            width = 8f;
                            lifetime = range / (speed + 3);
                            knockback = 4f;
                            armorIgnoreScl = 0.4f;
                            shieldDamageMultiplier = 1.5f;
                            trailColor = backColor;
                            shootSound = Sounds.shootBig;
                            shootEffect = new MultiEffect(UAWFxDynamic.railShoot(backColor, 22f), Fx.shootPyraFlame, Fx.shootSmallSmoke);
                            hitEffect = despawnEffect = new MultiEffect(Fx.hitBulletBig, Fx.shootSmallSmoke);
                            fragBullets = 4;
                            fragLifeMin = 0f;
                            fragCone = 30f;
                            fragBullet = new BasicBulletType(7f, 6) {{
                                height = width = 8f;
                                pierce = true;
                                pierceBuilding = true;
                                pierceCap = 3;
                                lifetime = 30f;
                                hitEffect = Fx.flakExplosion;
                                splashDamage = damage;
                                splashDamageRadius = 8f;
                                hittable = false;
                            }};
                        }};
                    }}
            );
        }};
        arkabuz = new TankUnitType("arkabuz") {{
            health = 7500;
            armor = 35;
            hitSize = 25;
            speed = 1.2f;
            rotateSpeed = 2f;
            ammoType = new ItemAmmoType(Items.graphite);
            targetAir = false;

            accel = 0.04f;
            drag = 0.08f;
            range = 48 * tilesize;
            rotateShooting = false;
            trailChance = 0.6f;

            weapons.add(
                    new RecoilingWeapon(name + "-gun") {{
                        targetFlags = new BlockFlag[]{BlockFlag.extinguisher, null};
                        rotate = true;
                        mirror = false;
                        rotateSpeed = 2.2f;
                        x = 0f;
                        y = 0f;
                        shootY = 32f;
                        reload = 2 * 60;
                        recoil = 4.4f;
                        shootSound = UAWSfx.cannonShoot1;
                        ejectEffect = UAWFxStatic.casing3Long;
                        shake = 6f;
                        bullet = new TrailBulletType(8f, 215) {{
                            height = 35f;
                            width = 10f;
                            lifetime = range / (speed + 3);
                            pierce = true;
                            pierceCap = 2;
                            knockback = 6f;
                            trailColor = backColor;
                            shootSound = Sounds.shootBig;
                            armorIgnoreScl = 0.6f;
                            shieldDamageMultiplier = 2f;
                            shootEffect = new MultiEffect(UAWFxDynamic.railShoot(backColor, 30f), Fx.shootPyraFlame, Fx.shootBigSmoke2);
                            hitEffect = despawnEffect = new MultiEffect(Fx.hitBulletBig, Fx.shootBigSmoke2);
                            fragBullets = 6;
                            fragLifeMin = 0f;
                            fragCone = 30f;
                            status = StatusEffects.melting;
                            fragBullet = new BasicBulletType(7f, 9) {{
                                buildingDamageMultiplier = 1.5f;
                                height = width = 8f;
                                pierce = true;
                                pierceBuilding = true;
                                pierceCap = 3;
                                lifetime = 30f;
                                hitEffect = Fx.flakExplosion;
                                splashDamage = damage;
                                splashDamageRadius = 8f;
                                hittable = false;
                            }};
                        }};
                    }}
            );
        }};
    }
}
