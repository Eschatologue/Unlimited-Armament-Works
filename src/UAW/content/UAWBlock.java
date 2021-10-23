package UAW.content;

import UAW.entities.bullet.*;
import UAW.graphics.*;
import UAW.world.blocks.defense.*;
import UAW.world.blocks.defense.turrets.*;
import UAW.world.blocks.defense.walls.ShieldWall;
import UAW.world.blocks.drawer.DrawLiquidInput;
import arc.graphics.Color;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.draw.*;

import static UAW.content.UAWBullets.*;
import static mindustry.Vars.tilesize;
import static mindustry.content.Bullets.*;
import static mindustry.type.ItemStack.with;

public class UAWBlock implements ContentList {
    public static Block

    // Turret
    quadra, spitfire, equalizer,
    solo, longsword, deadeye,
    buckshot, tempest, strikeforce,
    ashlock, zounderkite, skyhammer,
    heavylight, trailblazer, gigavolt,

    // Production
    gelatinizer, carburizingFurnace, surgeMixer, coalLiquefier,

    // Defense
    shieldWall, statusFieldProjector, rejuvinationProjector, rejuvinationDome,

    // UnitBlocks
    multiplicativePetroleumReconstructor, exponentialPetroleumReconstructor, tetrativePetroleumReconstructor;

    @Override
    public void load() {
        float tick = 60;

        quadra = new DynamicReloadTurret("quadra") {{
            requirements(Category.turret, with(
                    Items.copper, 115,
                    Items.lead, 120,
                    Items.graphite, 80
            ));
            health = 160 * size;
            size = 2;
            spread = 4f;
            shots = 2;
            alternate = true;
            reloadTime = 30f;
            restitution = 0.03f;
            range = 20 * tilesize;
            shootCone = 15f;
            ammoUseEffect = Fx.casing2Double;
            inaccuracy = 7.5f;
            rotateSpeed = 10f;
            maxAmmo = 30;

            maxReloadScl = 8f;
            speedupPerShot = 0.15f;
            ammo(
                    Items.copper, new BasicBulletType(4f, 9) {{
                        height = 14f;
                        width = height / 1.8f;
                        lifetime = range / speed;
                        shootEffect = Fx.shootSmall;
                        smokeEffect = Fx.shootSmallSmoke;
                        ammoMultiplier = 3;
                    }},
                    Items.graphite, new BasicBulletType(3.5f, 18) {{
                        height = 18f;
                        width = height / 1.8f;
                        knockback = 0.15f;
                        reloadMultiplier = 0.6f;
                        ammoMultiplier = 4;
                        lifetime = range / speed;
                    }},
                    Items.pyratite, new BasicBulletType(4f, 12f) {{
                        height = 18f;
                        width = height / 1.8f;
                        frontColor = Pal.lightishOrange;
                        backColor = Pal.lightOrange;
                        hitEffect = despawnEffect = new MultiEffect(Fx.hitBulletSmall, Fx.fireHit);
                        status = StatusEffects.burning;
                        inaccuracy = 3f;
                        lifetime = range / speed;
                        ammoMultiplier = 4f;
                    }},
                    Items.titanium, new TrailBulletType(12f, 10f) {{
                        height = 30f;
                        width = 10f;
                        armorIgnoreScl = 0.25f;
                        shieldDamageMultiplier = 1.5f;
                        shootEffect = Fx.shootBig;
                        smokeEffect = Fx.shootBigSmoke;
                        ammoMultiplier = 3;
                        lifetime = range / speed;
                    }}
            );
        }};
        spitfire = new DynamicReloadTurret("spitfire") {{
            requirements(Category.turret, with(
                    Items.lead, 350,
                    Items.titanium, 280,
                    Items.thorium, 250,
                    Items.plastanium, 250,
                    Items.silicon, 150
            ));
            size = 3;
            health = 150 * size * size;
            maxAmmo = 200;
            reloadTime = 30f;
            range = 30 * tilesize;
            rotateSpeed = 7f;
            inaccuracy = 7.5f;
            recoilAmount = 3f;
            restitution = 0.05f;
            shootSound = Sounds.shootBig;
            ammoUseEffect = Fx.casing2Double;
            xRand = 3;

            maxReloadScl = 16f;
            speedupPerShot = 0.1f;
            slowDownReloadTime = 90f;

            ammo(
                    Items.graphite, new BasicBulletType(8, 20) {{
                        lifetime = range / speed;
                        pierceCap = 1;
                        height = 25;
                        width = 12;
                        knockback = 1.2f;
                        hitEffect = Fx.hitBulletBig;
                        smokeEffect = Fx.shootBigSmoke2;
                        shootEffect = Fx.shootBig2;
                        trailChance = 0.4f;
                        trailColor = Color.lightGray;

                        status = StatusEffects.slow;
                    }},
                    UAWItems.titaniumCarbide, new TrailBulletType(12, 22) {{
                        height = 30;
                        width = 8;
                        lifetime = range / speed + 8;
                        pierce = true;
                        reloadMultiplier = 1.2f;
                        armorIgnoreScl = 0.5f;
                        hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.generatespark);
                        despawnEffect = Fx.hitBulletBig;
                        smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.fireSmoke);
                        shootEffect = new MultiEffect(Fx.shootBig2, Fx.hitBulletBig);
                    }},
                    Items.surgeAlloy, new BasicBulletType(8, 18) {{
                        height = 25;
                        width = 8;
                        lifetime = range / speed;
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
                    }},
                    Items.pyratite, new BasicBulletType(7, 15) {{
                        height = 25;
                        width = 10;
                        pierceCap = 1;
                        lifetime = range / speed;
                        knockback = 1.2f;
                        hitEffect = Fx.hitBulletBig;
                        smokeEffect = Fx.shootBigSmoke2;
                        shootEffect = new MultiEffect(Fx.shootPyraFlame, Fx.shootBig2);
                        hitEffect = despawnEffect = new MultiEffect(Fx.hitBulletBig, Fx.fireHit);
                        frontColor = UAWPal.incendFront;
                        backColor = UAWPal.incendBack;
                        status = StatusEffects.melting;
                        trailChance = 0.4f;
                        trailColor = Color.lightGray;
                    }},
                    UAWItems.cryogel, new BasicBulletType(7, 15) {{
                        height = 25;
                        width = 10;
                        pierceCap = 1;
                        lifetime = range / speed;
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
                    }}
            );
        }};

        solo = new CustomItemTurret("solo") {{
            requirements(Category.turret, with(
                    Items.copper, 150,
                    Items.graphite, 100,
                    Items.titanium, 50
            ));
            health = 160 * size;
            size = 2;
            spread = 0f;
            reloadTime = 35f;
            shootShake = 3f;
            restitution = 0.05f;
            range = 25 * tilesize;
            ammoUseEffect = Fx.casing3;
            shootSound = Sounds.shootBig;
            inaccuracy = 1.5f;
            rotateSpeed = 5f;
            unitSort = (u, x, y) -> -u.health;
            maxAmmo = 20;
            ammoPerShot = 2;
            ammo(
                    Items.copper, new TrailBulletType(10f, 55f) {{
                        hitSize = 5;
                        height = 30f;
                        width = 10f;
                        shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame);
                        smokeEffect = Fx.shootBigSmoke;
                        ammoMultiplier = 2;
                        pierceCap = 2;
                        lifetime = range / speed;
                    }},
                    Items.graphite, new TrailBulletType(8f, 90) {{
                        hitSize = 5;
                        height = 25f;
                        width = 15f;
                        shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame);
                        smokeEffect = Fx.shootBigSmoke;
                        reloadMultiplier = 0.5f;
                        ammoMultiplier = 2;
                        knockback = 1.2f;
                        lifetime = range / speed;
                    }},
                    Items.silicon, new TrailBulletType(10f, 55f) {{
                        hitSize = 5;
                        height = 30f;
                        width = 10f;
                        homingPower = 0.16f;
                        shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame);
                        smokeEffect = Fx.shootBigSmoke;
                        ammoMultiplier = 2;
                        pierceCap = 2;
                        lifetime = range / speed;
                    }},
                    Items.thorium, new TrailBulletType(8f, 80) {{
                        hitSize = 5;
                        height = 30f;
                        width = 16f;
                        shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame);
                        smokeEffect = Fx.shootBigSmoke;
                        pierceCap = 2;
                        pierceBuilding = true;
                        knockback = 0.7f;
                        lifetime = range / speed;
                    }},
                    Items.titanium, new TrailBulletType(12f, 25f) {{
                        height = 30f;
                        width = 10f;
                        shootEffect = new MultiEffect(Fx.shootBig2, Fx.hitBulletBig, UAWFxStatic.shootSurgeFlame);
                        armorIgnoreScl = 0.6f;
                        smokeEffect = Fx.shootBigSmoke;
                        ammoMultiplier = 2;
                        lifetime = range + (5 * tilesize) / speed;
                    }}
            );
        }};
        longsword = new CustomItemTurret("longsword") {{
            float brange = range = 55 * tilesize;
            requirements(Category.turret, with(
                    Items.thorium, 280,
                    UAWItems.titaniumCarbide, 150,
                    Items.graphite, 200,
                    Items.silicon, 180,
                    Items.plastanium, 150
            ));
            size = 3;
            health = 150 * size * size;
            maxAmmo = 30;
            ammoPerShot = 10;
            rotateSpeed = 2.5f;
            reloadTime = 60;
            ammoUseEffect = UAWFxStatic.casing6;
            recoilAmount = 4f;
            restitution = 0.01f;
            shootShake = 3f;

            shootCone = 1f;
            shootSound = Sounds.artillery;
            unitSort = (u, x, y) -> -u.health;
            ammo(
                    Items.surgeAlloy, new StandardPointBulletType() {{
                        damage = 400;
                        speed = brange;
                        splashDamage = 200;
                        splashDamageRadius = 3 * 8;
                        shootEffect = new MultiEffect(UAWFxDynamic.railShoot(Pal.missileYellow, 32), Fx.blockExplosionSmoke);
                        smokeEffect = Fx.smokeCloud;
                        trailEffect = Fx.railTrail;
                        hitEffect = despawnEffect = new MultiEffect(UAWFxDynamic.crossBlast(Pal.bulletYellow, splashDamageRadius), Fx.smokeCloud);
                        trailSpacing = 20f;
                        shieldDamageMultiplier = 1.5f;
                        buildingDamageMultiplier = 0.5f;
                        hitShake = 6f;
                        ammoMultiplier = 1f;
                        status = StatusEffects.electrified;
                    }},
                    UAWItems.titaniumCarbide, new StandardRailBulletType() {{
                        damage = 400;
                        length = 450;
                        shootEffect = new MultiEffect(UAWFxDynamic.railShoot(Pal.missileYellow, 32), Fx.blockExplosionSmoke);
                        hitEffect = pierceEffect = new MultiEffect(Fx.railHit, Fx.blockExplosionSmoke);
                        smokeEffect = Fx.smokeCloud;
                        updateEffect = Fx.railTrail;
                        updateEffectSeg = 30f;
                        armorIgnoreScl = buildingDamageMultiplier = 0.5f;
                        hitShake = 6f;
                        ammoMultiplier = 1f;
                    }}
            );
        }};

        zounderkite = new ItemTurret("zounderkite") {{
            requirements(Category.turret, with(
                    Items.lead, 300,
                    Items.titanium, 280,
                    Items.graphite, 325,
                    Items.silicon, 250,
                    Items.plastanium, 125
            ));
            range = 32 * tilesize;
            minRange = range / 4;
            reloadTime = 3 * tick;
            size = 3;
            shootSound = UAWSfx.launcherShoot1;
            ammoUseEffect = UAWFxStatic.casingCanister;
            ammoPerShot = 15;
            acceptCoolant = false;
            ammo(
                    Items.graphite, new CanisterBulletType(2f, 30, 4, mineBasic) {{
                        lifetime = range / speed;
                        ammoMultiplier = 4f;
                    }},
                    Items.pyratite, new CanisterBulletType(2f, 30, 3, mineIncend) {{
                        frontColor = UAWPal.incendFront;
                        backColor = UAWPal.incendBack;
                        shootEffect = Fx.shootPyraFlame;
                        lifetime = 160f;
                        ammoMultiplier = 3f;
                    }},
                    UAWItems.cryogel, new CanisterBulletType(2f, 30, 3, mineCryo) {{
                        frontColor = UAWPal.cryoFront;
                        backColor = UAWPal.cryoMiddle;
                        lifetime = range / speed;
                        ammoMultiplier = 3f;
                    }},
                    Items.plastanium, new CanisterBulletType(2f, 30, 3, mineOil) {{
                        frontColor = Pal.plastaniumFront;
                        backColor = Pal.plastaniumBack;
                        shootEffect = UAWFxStatic.shootPlastFlame;
                        lifetime = range / speed;
                        ammoMultiplier = 3f;
                    }},
                    Items.sporePod, new CanisterBulletType(2f, 30, 3, mineSpore) {{
                        frontColor = Pal.spore;
                        backColor = UAWPal.sporeMiddle;
                        shootEffect = UAWFxStatic.shootSporeFlame;
                        lifetime = range / speed;
                        ammoMultiplier = 3f;
                    }},
                    Items.surgeAlloy, new CanisterBulletType(2f, 30, 3, mineEMP) {{
                        frontColor = UAWPal.surgeFront;
                        backColor = UAWPal.surgeBack;
                        shootEffect = UAWFxStatic.shootSurgeFlame;
                        lifetime = range / speed;
                        ammoMultiplier = 3f;
                    }}
            );
        }};
        skyhammer = new ItemTurret("skyhammer") {{
            requirements(Category.turret, with(
                    Items.copper, 1000,
                    Items.lead, 600,
                    Items.titanium, 550,
                    Items.graphite, 500,
                    Items.surgeAlloy, 350,
                    Items.plastanium, 300,
                    Items.thorium, 250,
                    UAWItems.titaniumCarbide, 225));
            size = 4;
            health = 100 * size * size;
            targetAir = false;
            inaccuracy = 4f;
            rotateSpeed = 1f;
            unitSort = (unit, x, y) -> unit.hitSize;
            reloadTime = 10 * tick;
            ammoEjectBack = 5f;
            ammoUseEffect = UAWFxStatic.casing7;
            ammoPerShot = 20;
            itemCapacity = 60;
            velocityInaccuracy = 0.2f;
            restitution = 0.02f;
            recoilAmount = 6f;
            shootShake = 48f;
            range = 50 * tilesize;
            minRange = range / 12;

            shootSound = UAWSfx.artilleryShootHuge;
            ammo(
                    Items.pyratite, new ArtilleryBulletType(2f, 4000) {{
                        height = 45;
                        width = height / 2f;
                        splashDamage = damage;
                        splashDamageRadius = 14 * tilesize;
                        lifetime = range / speed;
                        frontColor = Pal.lightishOrange;
                        backColor = Pal.lightOrange;
                        shootEffect = new MultiEffect(UAWFxDynamic.railShoot(Pal.lightOrange, height + (width * 1.5f)), Fx.impactcloud, Fx.nuclearShockwave);
                        hitEffect = UAWFxDynamic.hugeExplosion(splashDamageRadius, frontColor);
                        hitSound = UAWSfx.artilleryExplosionHuge;
                        hitSoundVolume = 3f;
                        hitShake = 34f;
                        makeFire = true;
                        trailMult = 1.5f;
                        trailSize = width / 2.3f;
                        trailEffect = Fx.artilleryTrail;
                        smokeEffect = Fx.smokeCloud;
                        status = StatusEffects.burning;
                        fragBullets = 1;
                        fragBullet = new SplashBulletType(splashDamage / 4, splashDamageRadius / 1.2f) {{
                            splashAmount = 4;
                            splashDelay = 45;
                            lifetime = (splashDelay * splashAmount);
                            frontColor = Pal.lightishOrange;
                            backColor = Pal.lightOrange;
                            status = StatusEffects.melting;
                            statusDuration = 30f;
                            particleEffect = Fx.burning;
                            makeFire = true;
                            applySound = Sounds.flame2;
                        }};
                    }},
                    UAWItems.cryogel, new ArtilleryBulletType(2f, 4000) {{
                        height = 45;
                        width = height / 2f;
                        splashDamage = damage;
                        splashDamageRadius = 14 * tilesize;
                        lifetime = range / speed;
                        frontColor = UAWPal.cryoFront;
                        backColor = UAWPal.cryoBack;
                        shootEffect = new MultiEffect(UAWFxDynamic.railShoot(UAWPal.cryoBackBloom, height + (width * 1.5f)), Fx.impactcloud, Fx.nuclearShockwave);
                        hitEffect = UAWFxDynamic.hugeExplosion(splashDamageRadius, frontColor);
                        hitSound = UAWSfx.artilleryExplosionHuge;
                        hitSoundVolume = 3f;
                        hitShake = 34f;
                        trailMult = 1.5f;
                        trailSize = width / 2.3f;
                        trailEffect = Fx.artilleryTrail;
                        smokeEffect = Fx.smokeCloud;
                        status = StatusEffects.freezing;
                        fragBullets = 1;
                        fragBullet = new SplashBulletType(splashDamage / 4, splashDamageRadius / 1.2f) {{
                            splashAmount = 4;
                            splashDelay = 45;
                            lifetime = (splashDelay * splashAmount);
                            frontColor = UAWPal.cryoFront;
                            backColor = UAWPal.cryoBack;
                            status = StatusEffects.freezing;
                            statusDuration = 30f;
                            particleEffect = Fx.freezing;
                            makeFire = true;
                            applySound = Sounds.plasmaboom;
                        }};
                    }},
                    Items.plastanium, new ArtilleryBulletType(2f, 3500) {{
                        height = 45;
                        width = height / 2f;
                        splashDamage = damage;
                        splashDamageRadius = 14 * tilesize;
                        lifetime = range / speed;
                        frontColor = Pal.plastaniumFront;
                        backColor = Pal.plastaniumBack;
                        shootEffect = new MultiEffect(UAWFxDynamic.railShoot(UAWPal.plastBackBloom, height + width), Fx.impactcloud, Fx.nuclearShockwave);
                        hitEffect = UAWFxDynamic.hugeExplosion(splashDamageRadius, frontColor);
                        hitSound = UAWSfx.artilleryExplosionHuge;
                        hitSoundVolume = 3f;
                        hitShake = 34f;
                        trailMult = 1.5f;
                        trailSize = width / 2.3f;
                        trailEffect = Fx.artilleryTrail;
                        smokeEffect = Fx.smokeCloud;
                        fragBullets = 20;
                        fragBullet = new ArtilleryBulletType(2f, 1250) {{
                            hitEffect = Fx.plasticExplosion;
                            knockback = 1f;
                            lifetime = 50f;
                            width = height = 28f;
                            collidesTiles = false;
                            splashDamageRadius = 35f * 0.75f;
                            splashDamage = 45f;
                            fragBullet = artilleryPlastic;
                            fragBullets = 4;
                            backColor = Pal.plastaniumBack;
                            frontColor = Pal.plastaniumFront;
                        }};
                    }},
                    Items.thorium, new ArtilleryBulletType(2f, 5500) {{
                        height = 48;
                        width = height / 2f;
                        splashDamage = damage;
                        splashDamageRadius = 14 * tilesize;
                        lifetime = range / speed;
                        shootEffect = new MultiEffect(UAWFxDynamic.railShoot(UAWPal.surgeBackBloom, height + width), Fx.impactcloud, Fx.nuclearShockwave);
                        hitEffect = UAWFxDynamic.hugeExplosion(splashDamageRadius, frontColor);
                        hitSound = UAWSfx.artilleryExplosionHuge;
                        hitSoundVolume = 3f;
                        hitShake = 34f;
                        trailMult = 1.5f;
                        trailSize = width / 2.3f;
                        trailEffect = Fx.artilleryTrail;
                        smokeEffect = Fx.smokeCloud;
                        fragBullets = 1;
                        fragBullet = new SplashBulletType(splashDamage / 4, splashDamageRadius / 1.2f) {{
                            splashAmount = 5;
                            splashDelay = 45;
                            lifetime = (splashDelay * splashAmount);
                            frontColor = Pal.bulletYellow;
                            backColor = Pal.bulletYellowBack;
                            hitShake = 28;
                            status = UAWStatusEffects.concussion;
                            statusDuration = 30f;
                            particleEffect = Fx.fireSmoke;
                            applySound = Sounds.explosionbig;
                        }};
                    }}
            );
        }};

        buckshot = new ItemTurret("buckshot") {{
            requirements(Category.turret, with(
                    Items.copper, 120,
                    Items.lead, 200,
                    Items.graphite, 100
            ));
            health = 160 * size;
            size = 2;
            spread = 1.5f;
            shots = 10;
            reloadTime = 45f;
            shootShake = 2f;
            restitution = 0.05f;
            shootCone = 0.5f;
            velocityInaccuracy = 0.3f;
            ammoUseEffect = Fx.casing3;
            shootSound = Sounds.artillery;
            inaccuracy = 5f;
            rotateSpeed = 4f;
            maxAmmo = 48;
            ammoPerShot = 12;
            range = 100f;
            targetAir = false;
            ammo(
                    Items.lead, UAWBullets.buckshotLead,
                    Items.pyratite, UAWBullets.buckshotIncend,
                    UAWItems.cryogel, UAWBullets.buckshotCryo
            );
        }};
        tempest = new CustomItemTurret("tempest") {{
            requirements(Category.turret, with(
                    Items.titanium, 200,
                    Items.graphite, 150,
                    Items.metaglass, 100,
                    Items.silicon, 100
            ));
            health = 120 * size * size;
            size = 3;
            spread = 2f;
            shots = 4;
            xRand = 3;
            reloadTime = 7f;
            shootShake = 0.6f;
            restitution = 0.08f;
            range = 30 * tilesize;
            shootCone = 2.3f;
            velocityInaccuracy = 0.2f;
            ammoUseEffect = Fx.casing3;
            shootSound = UAWSfx.shotgun_shoot_1;
            inaccuracy = 6f;
            rotateSpeed = 4f;
            maxAmmo = 60;
            ammoPerShot = 6;
            ammo(
                    Items.graphite, new BuckshotBulletType(5f, 12f) {{
                        lifetime = range / speed;
                        knockback = 4f;
                        despawnEffect = hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.burning, Fx.coalSmeltsmoke);
                        shieldDamageMultiplier = 2.5f;
                    }},
                    Items.pyratite, new BuckshotBulletType(5f, 8f) {{
                        shootEffect = Fx.shootPyraFlame;
                        smokeEffect = Fx.shootBigSmoke2;
                        frontColor = Pal.lightishOrange;
                        backColor = Pal.lightOrange;
                        despawnEffect = hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.burning, Fx.fireHit);
                        status = StatusEffects.burning;
                        shieldDamageMultiplier = 1.4f;
                    }},
                    UAWItems.cryogel, new BuckshotBulletType(5f, 8f) {{
                        lifetime = range / speed;
                        shootEffect = UAWFxStatic.shootCryoFlame;
                        smokeEffect = Fx.shootBigSmoke2;
                        frontColor = UAWPal.cryoFront;
                        backColor = UAWPal.cryoMiddle;
                        despawnEffect = hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.freezing, UAWFxStatic.cryoHit);
                        status = StatusEffects.freezing;
                        shieldDamageMultiplier = 1.4f;
                    }},
                    UAWItems.titaniumCarbide, new BuckshotBulletType(6f, 10f) {{
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
                    }},
                    Items.metaglass, new BuckshotBulletType(5f, 8f) {{
                        splashDamageRadius = 1.8f * tilesize;
                        splashDamage = damage / 2;
                        lifetime = range / speed;
                        fragBullets = 6;
                        fragBullet = fragGlass;
                    }}
            );
        }};
        strikeforce = new CustomItemTurret("strikeforce") {{
            requirements(Category.turret, with(
                    Items.titanium, 350,
                    Items.graphite, 300,
                    Items.metaglass, 200,
                    Items.silicon, 200,
                    Items.surgeAlloy, 100
            ));
            health = 250 * size * size;
            size = 3;
            spread = 2.3f;
            shots = 24;
            recoilAmount = 4f;
            reloadTime = 95f;
            shootShake = 8f;
            restitution = 0.08f;
            range = 175f;
            shootCone = 3f;
            velocityInaccuracy = 0.3f;
            ammoUseEffect = Fx.casing4;
            shootSound = Sounds.artillery;
            inaccuracy = 8f;
            rotateSpeed = 3f;
            maxAmmo = 128;
            ammoPerShot = 32;
            ammo(
                    Items.pyratite, new BuckshotBulletType(6.5f, 30f) {{
                        height = width = 30;
                        pierceCap = 3;
                        lifetime = range / speed;
                        frontColor = Pal.lightishOrange;
                        backColor = Pal.lightOrange;
                        smokeEffect = new MultiEffect(Fx.smokeCloud, Fx.shootBigSmoke2);
                        hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.melting);
                        shootEffect = new MultiEffect(UAWFxDynamic.instShoot(backColor, 64), Fx.shootPyraFlame);
                        despawnEffect = UAWFxDynamic.thermalExplosion(frontColor, backColor);
                        status = StatusEffects.melting;
                        fragBullets = 8;
                        fragBullet = heavySlagShot;
                        fragVelocityMin = 0.3f;
                        fragVelocityMax = fragVelocityMin * 1.2f;
                        fragLifeMin = 0.4f;
                        fragLifeMax = 0.8f;
                        shieldDamageMultiplier = 1.5f;
                    }},
                    UAWItems.cryogel, new BuckshotBulletType(6.5f, 30f) {{
                        height = width = 30;
                        pierceCap = 3;
                        lifetime = range / speed;
                        frontColor = UAWPal.cryoFront;
                        backColor = UAWPal.cryoBack;
                        smokeEffect = new MultiEffect(Fx.smokeCloud, Fx.shootBigSmoke2);
                        hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.freezing);
                        shootEffect = new MultiEffect(UAWFxDynamic.instShoot(backColor, 64), UAWFxStatic.shootCryoFlame);
                        despawnEffect = UAWFxDynamic.thermalExplosion(frontColor, backColor);
                        status = StatusEffects.freezing;
                        fragBullets = 8;
                        fragBullet = heavyCryoShot;
                        fragVelocityMin = 0.3f;
                        fragVelocityMax = fragVelocityMin * 1.2f;
                        fragLifeMin = 0.4f;
                        fragLifeMax = 0.8f;
                        shieldDamageMultiplier = 1.5f;
                    }},
                    Items.plastanium, new BuckshotBulletType(6.5f, 30f) {{
                        height = width = 30;
                        pierceCap = 3;
                        lifetime = range / speed;
                        frontColor = Pal.plastaniumFront;
                        backColor = Pal.plastaniumBack;
                        smokeEffect = new MultiEffect(Fx.smokeCloud, Fx.shootBigSmoke2);
                        hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.oily);
                        shootEffect = new MultiEffect(UAWFxDynamic.instShoot(backColor, 64), Fx.shootPyraFlame);
                        despawnEffect = UAWFxDynamic.thermalExplosion(frontColor, backColor);
                        status = StatusEffects.tarred;
                        fragBullets = 8;
                        fragBullet = heavyOilShot;
                        fragVelocityMin = 0.3f;
                        fragVelocityMax = fragVelocityMin * 1.2f;
                        fragLifeMin = 0.4f;
                        fragLifeMax = 0.8f;
                        shieldDamageMultiplier = 2.8f;
                    }},
                    Items.surgeAlloy, new BuckshotBulletType(6.5f, 30f) {{
                        height = width = 30;
                        pierceCap = 3;
                        lifetime = range / speed;
                        frontColor = UAWPal.surgeFront;
                        backColor = UAWPal.surgeBack;
                        smokeEffect = new MultiEffect(Fx.smokeCloud, Fx.shootBigSmoke2);
                        hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.freezing);
                        shootEffect = new MultiEffect(UAWFxDynamic.instShoot(backColor, 64), UAWFxStatic.shootSurgeFlame);
                        despawnEffect = UAWFxDynamic.thermalExplosion(frontColor, backColor);
                        status = StatusEffects.electrified;

                        lightningDamage = 8;
                        lightning = 3;
                        lightningLength = 8;
                        shieldDamageMultiplier = 3f;
                    }}
            );
        }};

        gelatinizer = new GenericCrafter("gelatinizer") {{
            requirements(Category.crafting, with(
                    Items.lead, 45,
                    Items.graphite, 30,
                    Items.thorium, 20
            ));
            consumes.items(
                    new ItemStack(
                            Items.sand, 2
                    ));
            consumes.liquid(Liquids.cryofluid, 0.2f);
            consumes.power(0.5f);
            outputItem = new ItemStack(
                    UAWItems.cryogel, 1
            );
            craftTime = 60f;
            hasItems = true;
            hasLiquids = true;
            size = 2;
            drawer = new DrawLiquidInput();
            craftEffect = Fx.freezing;
            updateEffect = Fx.wet;
        }};
        carburizingFurnace = new GenericCrafter("carburizing-furnace") {{
            requirements(Category.crafting, with(
                    Items.titanium, 150,
                    Items.thorium, 125,
                    Items.metaglass, 95,
                    Items.silicon, 95,
                    Items.graphite, 95
            ));
            consumes.items(
                    new ItemStack(Items.titanium, 6),
                    new ItemStack(Items.coal, 12)
            );
            consumes.liquid(Liquids.slag, 0.75f);
            outputItem = new ItemStack(
                    UAWItems.titaniumCarbide, 1
            );
            consumes.power(5.5f);
            hasItems = true;
            hasLiquids = true;
            size = 3;
            itemCapacity = 36;
            craftTime = 4.5f * tick;
            drawer = new DrawSmelter();
            craftEffect = UAWFxDynamic.burstSmelt(23f, 10f, 21f, 90, Color.valueOf("FFB499"), Color.valueOf("FFE4CC"));
            updateEffect = new MultiEffect(Fx.melting, Fx.burning, Fx.fireSmoke);
        }};
        surgeMixer = new GenericCrafter("surge-mixer") {{
            requirements(Category.crafting, with(
                    Items.thorium, 60,
                    Items.lead, 55,
                    Items.silicon, 45,
                    Items.metaglass, 30
            ));
            consumes.items(
                    new ItemStack(Items.surgeAlloy, 2),
                    new ItemStack(Items.thorium, 3)
            );
            consumes.liquid(Liquids.slag, 0.5f);
            consumes.power(2f);
            outputLiquid = new LiquidStack(UAWLiquid.surgeSolvent, 30f);
            size = 3;
            liquidCapacity = 120f;
            outputsLiquid = true;
            hasItems = true;
            hasLiquids = true;
            drawer = new DrawLiquid();
            craftTime = 2.5f * tick;
            updateEffect = Fx.shieldBreak;
        }};
        coalLiquefier = new GenericCrafter("coal-liquefier") {{
            requirements(Category.crafting, with(
                    Items.lead, 50,
                    Items.titanium, 50,
                    Items.silicon, 35,
                    Items.metaglass, 30
            ));
            consumes.items(
                    new ItemStack(Items.coal, 8),
                    new ItemStack(Items.thorium, 2)
            );
            consumes.liquid(Liquids.slag, 0.85f);
            consumes.power(1.5f);
            outputLiquid = new LiquidStack(Liquids.oil, 30f);
            size = 3;
            liquidCapacity = 120f;
            outputsLiquid = true;
            hasItems = true;
            hasLiquids = true;
            drawer = new DrawLiquid();
            craftTime = 1.5f * tick;
            updateEffect = Fx.steam;
        }};

        shieldWall = new ShieldWall("force-wall") {{
            requirements(Category.defense, with(
                    Items.phaseFabric, 6,
                    Items.silicon, 4
            ));
            size = 1;
            health = 250;
            shieldHealth = 8500f;
            cooldown = 2f;
            cooldownBrokenShield = 1f;
            researchCostMultiplier = 2f;

            consumes.power(0.5f);
        }};

        statusFieldProjector = new EffectFieldProjector("status-field-projector") {{
            requirements(Category.effect, with(
                    Items.lead, 100,
                    Items.titanium, 75,
                    Items.metaglass, 75,
                    Items.silicon, 75
            ));
            size = 2;
            reloadTime = 1.2f * 60;
            range = 24 * tilesize;
            ammoUseEffect = Fx.doorcloselarge;
            ammo(
                    Liquids.cryofluid, new SplashBulletType(0, range) {{
                        shootEffect = UAWFxDynamic.statusFieldApply(UAWPal.cryoFront, UAWPal.cryoBack, range);
                        smokeEffect = UAWFxDynamic.statusHit(UAWPal.cryoMiddle, 30);
                        status = StatusEffects.freezing;
                        frontColor = UAWPal.cryoFront;
                        backColor = UAWPal.cryoBack;
                        statusDuration = reloadTime * 1.5f;
                        splashAmount = 1;
                    }},
                    Liquids.slag, new SplashBulletType(0, range) {{
                        shootEffect = UAWFxDynamic.statusFieldApply(Pal.lighterOrange, Pal.lightOrange, range);
                        smokeEffect = UAWFxDynamic.statusHit(Pal.orangeSpark, 30);
                        status = StatusEffects.melting;
                        frontColor = Pal.lighterOrange;
                        backColor = Pal.lightOrange;
                        statusDuration = reloadTime * 1.5f;
                        splashAmount = 1;
                    }},
                    Liquids.oil, new SplashBulletType(0, range) {{
                        shootEffect = UAWFxDynamic.statusFieldApply(Pal.plastaniumFront, UAWPal.plastDarker, range);
                        smokeEffect = UAWFxDynamic.statusHit(Pal.plastanium, 30);
                        status = StatusEffects.tarred;
                        statusDuration = reloadTime * 1.5f;
                        splashAmount = 1;
                    }},
                    UAWLiquid.surgeSolvent, new SplashBulletType(0, range) {{
                        shootEffect = UAWFxDynamic.statusFieldApply(Pal.missileYellow, Pal.missileYellowBack, range);
                        smokeEffect = UAWFxDynamic.statusHit(Pal.surge, 30);
                        status = StatusEffects.electrified;
                        frontColor = Pal.missileYellow;
                        backColor = Pal.missileYellowBack;
                        statusDuration = reloadTime * 1.5f;
                        splashAmount = 1;
                    }}
            );
            consumes.power(2.4f);
        }};
        rejuvinationProjector = new RejuvenationProjector("rejuvination-projector") {{
            requirements(Category.effect, with(
                    Items.lead, 100,
                    Items.titanium, 35,
                    Items.silicon, 15,
                    Items.metaglass, 15
            ));
            consumes.power(1.6f);
            size = 2;
            reload = 15f;
            range = 10 * tilesize;
            healPercent = 0.8f;
            health = 60 * size * size;
            boostMultiplier = 2.5f;
        }};
        rejuvinationDome = new RejuvenationProjector("rejuvination-dome") {{
            requirements(Category.effect, with(
                    Items.lead, 200,
                    Items.titanium, 100,
                    Items.silicon, 25,
                    Items.metaglass, 25
            ));
            consumes.power(3f);
            size = 3;
            reload = 15f;
            range = 25 * tilesize;
            healPercent = 1.2f;
            health = 60 * size * size;
            useTime = 4 * tick;
            consumes.items(with(Items.phaseFabric, 1, Items.thorium, 2));
            boostMultiplier = 4.5f;
        }};

        multiplicativePetroleumReconstructor = new Reconstructor("multiplicative-petroleum-reconstructor") {{
            requirements(Category.units, with(
                    Items.lead, 500,
                    Items.silicon, 150,
                    Items.metaglass, 150,
                    Items.titanium, 950
            ));

            size = 5;
            consumes.power(4.5f);
            consumes.items(with(Items.silicon, 90, Items.titanium, 130, Items.plastanium, 60));
            consumes.liquid(Liquids.oil, 1f);

            constructTime = 25 * tick;
            liquidCapacity = 120f;
            floating = true;

            upgrades.addAll(
                    new UnitType[]{UnitTypes.horizon, UAWUnitTypes.aglovale},
                    new UnitType[]{UnitTypes.mace, UAWUnitTypes.gardlacz},
                    new UnitType[]{UnitTypes.minke, UAWUnitTypes.clurit},
                    new UnitType[]{UnitTypes.bryde, UAWUnitTypes.hatsuharu}
            );
        }};
        exponentialPetroleumReconstructor = new Reconstructor("exponential-petroleum-reconstructor") {{
            requirements(Category.units, with(
                    Items.lead, 1000,
                    Items.titanium, 2000,
                    Items.thorium, 550,
                    Items.plastanium, 500,
                    UAWItems.titaniumCarbide, 700
            ));

            size = 7;
            consumes.power(7f);
            consumes.items(with(Items.silicon, 225, Items.metaglass, 225, UAWItems.titaniumCarbide, 250, Items.plastanium, 550));
            consumes.liquid(Liquids.oil, 1.5f);

            constructTime = 80 * tick;
            liquidCapacity = 240f;
            floating = true;

            upgrades.addAll(
                    new UnitType[]{UAWUnitTypes.aglovale, UAWUnitTypes.bedivere},
                    new UnitType[]{UAWUnitTypes.clurit, UAWUnitTypes.kujang},
                    new UnitType[]{UAWUnitTypes.gardlacz, UAWUnitTypes.arkabuz},
                    new UnitType[]{UAWUnitTypes.hatsuharu, UAWUnitTypes.shiratsuyu}
            );
        }};

    }
}
