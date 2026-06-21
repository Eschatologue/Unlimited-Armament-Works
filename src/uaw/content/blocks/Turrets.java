package uaw.content.blocks;

import arc.graphics.Color;
import arc.math.Interp;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.FlakBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootBarrel;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.draw.DrawTurret;
import uaw.audiovisual.UAWPal;
import uaw.audiovisual.UAWSfx;
import uaw.audiovisual.fx.AreaFx;
import uaw.audiovisual.fx.CombatFx;
import uaw.content.UAWItems;
import uaw.entities.bullet.UAWBulletType;
import uaw.utils.BulletDef;
import uaw.utils.Calc;
import uaw.utils.TurretDef;
import uaw.world.blocks.defense.turrets.ScalingItemTurret;
import uaw.world.blocks.defense.turrets.UAWItemTurret;

import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;
import static uaw.Vars.px;
import static uaw.Vars.tick;

public class Turrets {

    public static Block placeholder,
    // MG
    quadra, spitfire, saintfire,
    // SN
    solo, longsword, excalibre,
    // SG
    dixtuor, typhoon, tempest;

    static String modBase = "armoured-";

    public static void load() {

        // region MG
        quadra = new ScalingItemTurret("quadra") {{
            requirements(Category.turret, with(

                    Items.titanium, 100,
                    Items.graphite, 50
            ));
            float bH = 4;
            ammo(
                    Items.copper, new BasicBulletType(15, 12) {{
                        BulletDef.size(this, bH);
                        BulletDef.color(this, Pal.copperAmmoFront, Pal.copperAmmoBack);
                        BulletDef.autoTrail(this);
                        ammoMultiplier = 4;
                        pierceCap = 2;

                        hitEffect = despawnEffect = Fx.hitBulletColor.wrap(Pal.copperAmmoBack);
                    }},
                    Items.graphite, new BasicBulletType(12, 20) {{
                        BulletDef.size(this, bH);
                        BulletDef.color(this, Pal.graphiteAmmoFront, Pal.graphiteAmmoBack);
                        BulletDef.autoTrail(this);
                        ammoMultiplier = 6;
                        reloadMultiplier = 0.8f;

                        hitEffect = despawnEffect = Fx.hitBulletColor.wrap(Pal.graphiteAmmoBack);
                    }},
                    Items.silicon, new BasicBulletType(12, 15) {{
                        BulletDef.size(this, bH);
                        BulletDef.color(this, Pal.siliconAmmoFront, Pal.siliconAmmoBack);
                        BulletDef.autoTrail(this);
                        homingPower = 0.3f;
                        reloadMultiplier = 1.5f;
                        ammoMultiplier = 8;

                        hitEffect = despawnEffect = Fx.hitBulletColor.wrap(Pal.siliconAmmoBack);
                    }}
            );
            // General
            TurretDef.general(this, 2);
            rotateSpeed = 10f;
            // Shooting
            range = 20 * tilesize;
            reload = 25f;
            maxReloadScale = 4f;
            recoil = 1f;
            recoilTime = 30f;
            shake = 0.5f;
            shootCone = 15f;
            inaccuracy = 5f;
            shoot = new ShootAlternate() {{
                barrelOffset = 5;
                spread = 5f;
                velocityRnd = 0.2f;
            }};
            limitRange();
            // Effects
            ammoUseEffect = Fx.casing2;
            shootSound = Sounds.shootDuo;

            cooldownTime = reload / 2;
            // Drawer
            recoils = 2;
            drawer = new DrawTurret(modBase) {{
                for (int i = 0; i < 2; i++) {
                    int f = i;
                    parts.add(new RegionPart("-barrel-" + (i == 0 ? "l" : "r")) {{
                        progress = PartProgress.recoil;
                        recoilIndex = f;
                        under = true;
                        moveY = -4 * px;
                    }});
                }
                parts.add(
                        new RegionPart("-body")
                );
            }};
        }};
        spitfire = new ScalingItemTurret("spitfire") {{
            requirements(Category.turret, with(
                    Items.titanium, 550,
                    Items.silicon, 150,
                    Items.plastanium, 100,
                    UAWItems.tisic, 50
            ));
            float bH = 8f;
            ammo(
                    Items.graphite, new BasicBulletType(12, 15) {{
                        BulletDef.size(this, bH);
                        BulletDef.color(this, Pal.graphiteAmmoFront, Pal.graphiteAmmoBack);
                        BulletDef.autoTrail(this);
                        ammoMultiplier = 4;
                        pierceCap = 1;
                        knockback = 3;
                        reloadMultiplier = 0.75f;

                        hitEffect = despawnEffect = Fx.hitBulletColor.wrap(Pal.graphiteAmmoBack);

                        smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.fireSmoke);
                        shootEffect = new MultiEffect(Fx.shootBig, Fx.sparkShoot);
                    }},
                    Items.titanium, new BasicBulletType(20, 12) {{
                        BulletDef.size(this, bH);
                        BulletDef.color(this, UAWPal.titaniumAmmoFront, UAWPal.titaniumAmmoBack);
                        BulletDef.autoTrail(this);
                        ammoMultiplier = 4;
                        pierceCap = 3;

                        hitEffect = despawnEffect = Fx.hitBulletColor.wrap(UAWPal.titaniumAmmoBack);

                        smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.fireSmoke);
                        shootEffect = new MultiEffect(Fx.shootBig, Fx.sparkShoot);
                    }},
                    Items.surgeAlloy, new BasicBulletType(15, 10) {{
                        BulletDef.size(this, bH);
                        BulletDef.color(this, Pal.surgeAmmoFront, Pal.surgeAmmoBack);
                        BulletDef.autoTrail(this);
                        ammoMultiplier = 8;
                        pierceCap = 1;
                        status = StatusEffects.electrified;

                        lightning = 3;
                        lightningAngle = 270;
                        lightningDamage = 1.5f;
                        lightningLength = 3;
                        collidesAir = false;

                        hitEffect = despawnEffect = new MultiEffect(Fx.hitBulletColor.wrap(Pal.surgeAmmoBack), Fx.lightning);

                        smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.fireSmoke);
                        shootEffect = new MultiEffect(Fx.shootBig, Fx.sparkShoot);
                    }}
            );
            // General
            TurretDef.general(this, 3);
            rotateSpeed = 7f;
            targetAir = false;
            // Shooting
            range = 25 * tilesize;
            reload = 15f;
            maxReloadScale = 8f;
            accelTime = 10 * tick;
            decayTime = 5 * tick;
            recoil = 1f;
            recoilTime = 60f;
            shake = 0.75f;
            inaccuracy = 6.5f;
            shoot = new ShootBarrel() {{
                barrels = new float[]{
                        0f, 2f, 0f,
                        3f, 1f, 0f,
                        -3f, 1f, 0f,
                };
                velocityRnd = 0.2f;
            }};
            limitRange(2 * tilesize);
            // Effects
            ammoUseEffect = Fx.casing2;
            shootSound = Sounds.shootCyclone;

            cooldownTime = 2 * tick;
            // Drawer
            recoils = 3;
            drawer = new DrawTurret(modBase) {{
                for (int i = 3; i > 0; i--) {
                    int f = i;
                    parts.add(new RegionPart("-barrel-" + i) {{
                        heatProgress = PartProgress.smoothReload;
                        progress = PartProgress.warmup.add(PartProgress.reload.add(-3.5f));
                        recoilIndex = f - 1;
                        under = true;
                        moveY = 4 * px;
                    }});
                }
                parts.addAll(
                        new RegionPart("-back") {{
                            progress = PartProgress.warmup.min(PartProgress.recoil);
                            moveY = -7 * px;
                        }},
                        new RegionPart("-top")
                );
            }};
        }};
        // endregion MG

        // region SN
        solo = new UAWItemTurret("solo") {{
            requirements(Category.turret, with(
                    Items.titanium, 150,
                    Items.graphite, 50
            ));
            float bH = 14f;
            ammo(
                    Items.graphite, new BasicBulletType(14, 65) {{
                        Color colFront = Pal.graphiteAmmoFront, colBack = Pal.graphiteAmmoBack;
                        BulletDef.size(this, bH);
                        BulletDef.color(this, colFront, colBack);
                        BulletDef.autoTrail(this);

                        pierceCap = 1;
                        knockback = 5;

                        shootEffect = new MultiEffect(
                                Fx.shootBig,
                                CombatFx.ellipseWave(8, CombatFx.EllipseFade.FADE)
                        );

                        smokeEffect = Fx.shootSmokeDisperse;

                        hitEffect = despawnEffect = new MultiEffect(
                                Fx.hitBulletColor.wrap(colBack),
                                CombatFx.hitBulletBig(colBack)
                        );
                        ammoMultiplier = 1.5f;
                    }},
                    Items.titanium, new BasicBulletType(16, 45) {{
                        Color colFront = UAWPal.titaniumAmmoFront, colBack = UAWPal.titaniumAmmoBack;
                        BulletDef.size(this, bH);
                        BulletDef.color(this, colFront, colBack);
                        BulletDef.autoTrail(this);

                        pierceCap = 2;
                        armorMultiplier = 0.75f;

                        shootEffect = new MultiEffect(
                                Fx.shootBig,
                                CombatFx.ellipseWave(8, CombatFx.EllipseFade.FADE)
                        );

                        smokeEffect = Fx.shootSmokeDisperse;

                        trailEffect = Fx.disperseTrail;
                        trailInterval = 1f;

                        hitEffect = despawnEffect = new MultiEffect(
                                Fx.hitBulletColor.wrap(colBack),
                                CombatFx.hitBulletBig(colBack),
                                Fx.hitLancer
                        );
                        ammoMultiplier = 1;
                    }},
                    Items.metaglass, new FlakBulletType(14, 10) {{
                        Color colFront = Pal.glassAmmoFront, colBack = Pal.glassAmmoBack;
                        BulletDef.size(this, bH);
                        BulletDef.color(this, colFront, colBack);
                        BulletDef.autoTrail(this);

                        splashDamage = 35;
                        splashDamageRadius = 4 * tilesize;
                        collidesGround = true;

                        shootEffect = new MultiEffect(
                                Fx.shootBig,
                                CombatFx.ellipseWave(8, CombatFx.EllipseFade.FADE)
                        );

                        smokeEffect = Fx.shootSmokeDisperse;

                        hitEffect = despawnEffect = new MultiEffect(
                                Fx.hitBulletColor.wrap(colBack),
                                Fx.flakExplosion
                        );
                        fragBullets = 8;
                        fragBullet = new BasicBulletType(4, 15, "bullet") {{
                            BulletDef.size(this, 10);
                            BulletDef.color(this, Color.white, Pal.gray);
                            shrinkY = 1f;
                            lifetime = Calc.bulletLifetime(4 * tilesize, 4);
                            despawnEffect = Fx.none;
                        }};
                        ammoMultiplier = 2;
                    }},
                    Items.thorium, new BasicBulletType(14, 90) {{
                        Color colFront = Pal.thoriumAmmoFront, colBack = Pal.thoriumAmmoBack;
                        BulletDef.size(this, bH);
                        BulletDef.color(this, colFront, colBack);
                        BulletDef.autoTrail(this);

                        reloadMultiplier = 0.5f;
                        rangeOverride = 25 * tilesize;

                        pierceCap = 2;
                        armorMultiplier = 0.25f;
                        hitShake = 1.5f;

                        shootEffect = new MultiEffect(
                                Fx.shootBig,
                                CombatFx.ellipseWave(12, CombatFx.EllipseFade.FADE),
                                CombatFx.hitBulletBig(Pal.lightishOrange)
                        );

                        smokeEffect = Fx.shootSmokeDisperse;

                        hitEffect = despawnEffect = new MultiEffect(
                                Fx.hitBulletColor.wrap(colBack),
                                CombatFx.hitBulletBig(colBack)
                        );
                        ammoMultiplier = 1;
                    }}
            );
            // General
            TurretDef.general(this, 2);
            rotateSpeed = 7.5f;
            // Shooting
            range = 30 * tilesize;
            reload = 1.25f * tick;
            shake = 2.5f;
            limitRange();
            ammoPerShot = 2;
            // Effects
            ammoUseEffect = Fx.casing3;
            shootSound = UAWSfx.shootSolo;
            // Drawer
            drawer = new DrawTurret(modBase) {{
                parts.addAll(
                        new RegionPart("-bottom"),
                        new RegionPart("-side") {{
                            mirror = true;
                            progress = PartProgress.recoil.curve(Interp.bounceIn);
                            moveRot = 26.5f;
                            moveX = 2 * px;
                            moveY = -8 * px;
                        }},
                        new RegionPart("-barrel") {{
                            progress = PartProgress.recoil.curve(Interp.pow3).curve(Interp.bounceIn);
                            moveY = -5f * px;
                        }}
                );
            }};
        }};
        longsword = new UAWItemTurret("longsword") {{
            requirements(Category.turret, with(
                    Items.plastanium, 200,
                    Items.graphite, 300,
                    Items.silicon, 100
            ));
            float bH = 20;
            float spdBase = 20;
            ammo(
                    // Thorium: Causes fragments
                    Items.thorium, new UAWBulletType(spdBase, 200) {{
                        ammoLabel = "105 mm FRAG TH";
                        Color colFront = Pal.thoriumAmmoFront, colBack = Pal.thoriumAmmoBack;
                        BulletDef.size(this, bH, BulletDef.BulletSprite.MISSILE_LARGE);
                        BulletDef.color(this, colFront, colBack);
                        BulletDef.autoTrail(this);

                        armorMultiplier = 0.75f;
                        hitShake = 3;

                        trailEffect = new MultiEffect(
                                CombatFx.missileTrail(7, Color.grays(0.6f).lerp(Pal.lightishGray, 0.4f).a(0.3f)),
                                Fx.hitSquaresColor
                        );
                        trailRotation = true;
                        trailInterval = speed / 16;

                        shootEffect = new MultiEffect(
                                CombatFx.hitBulletBig(Pal.lightishOrange),
                                CombatFx.railShoot(36)
                        );

                        smokeEffect = new MultiEffect(
                                CombatFx.ellipseWave(20, 2f, CombatFx.EllipseFade.FADE),
                                CombatFx.hitBullet(18, colBack, CombatFx.HitParticle.SQUARES)
                        );

                        hitEffect = despawnEffect = new MultiEffect(
                                Fx.blastExplosion,
                                Fx.hitBulletColor.wrap(colBack),
                                CombatFx.hitBulletBig(colBack)
                        );
                        fragBullets = 4;
                        fragLifeMin = 0f;
                        fragRandomSpread = 30f;
                        despawnSound = Sounds.explosion;

                        fragBullet = new BasicBulletType(9f, 45) {{
                            BulletDef.size(this, bH * 0.8f, bH * 0.8f);
                            BulletDef.color(this, colFront, colBack);
                            pierce = true;
                            pierceBuilding = true;
                            pierceCap = 3;

                            lifetime = 20f;
                            hitEffect = Fx.flakExplosion;
                            splashDamage = 45;
                            splashDamageRadius = tilesize * 2;
                        }};
                    }},
                    // TiSiC: Armour Piercing
                    UAWItems.tisic, new UAWBulletType(spdBase * 1.5f, 150) {{
                        ammoLabel = "105 mm APCR TSC";
                        Color colFront = UAWPal.titaniumAmmoFront, colBack = UAWPal.titaniumAmmoBack;
                        BulletDef.size(this, bH, BulletDef.BulletSprite.MISSILE_LARGE);
                        BulletDef.color(this, colFront, colBack);
                        BulletDef.autoTrail(this);

                        armorMultiplier = 0.125f;
                        hitShake = 3;

                        trailEffect = new MultiEffect(
                                CombatFx.missileTrail(5, Color.grays(0.6f).lerp(Pal.lightishGray, 0.4f).a(0.3f)),
                                CombatFx.hitBullet(15, colBack, CombatFx.HitParticle.SQUARES)
                        );
                        trailRotation = true;
                        trailInterval = speed / 16;

                        shootEffect = new MultiEffect(
                                CombatFx.hitBulletBig(Pal.lightishOrange),
                                CombatFx.railShoot(36)
                        );

                        smokeEffect = new MultiEffect(
                                CombatFx.ellipseWave(24, 2f, CombatFx.EllipseFade.FADE),
                                Fx.shootSmokeTitan
                        );

                        hitEffect = despawnEffect = new MultiEffect(
                                Fx.hitFuse,
                                Fx.hitBulletColor.wrap(colBack),
                                CombatFx.hitBulletBig(colBack)
                        );
                    }},
                    // Stoutsteel: High Explosive
                    UAWItems.stoutsteel, new UAWBulletType(spdBase * 0.75f, 350) {{
                        ammoLabel = "105 mm HE STS";
                        Color colFront = Color.white, colBack = Pal.redLight;
                        BulletDef.size(this, bH, BulletDef.BulletSprite.MISSILE_LARGE);
                        BulletDef.color(this, colFront, colBack);
                        BulletDef.autoTrail(this);

                        splashDamage = damage * 0.5f;
                        splashDamageRadius = 8 * tilesize;

                        trailEffect = new MultiEffect(
                                CombatFx.missileTrail(5, Color.grays(0.6f).lerp(Pal.lightishGray, 0.4f).a(0.3f)),
                                CombatFx.hitBullet(bH, colBack, CombatFx.HitParticle.SQUARES)
                        );
                        trailRotation = true;
                        trailInterval = speed / 16;

                        shootEffect = new MultiEffect(
                                CombatFx.hitBulletBig(Pal.lightishOrange),
                                CombatFx.railShoot(36)
                        );

                        smokeEffect = new MultiEffect(
                                CombatFx.ellipseWave(24, 2f, CombatFx.EllipseFade.FADE),
                                Fx.shootSmokeTitan
                        );
                        despawnShake = hitShake = 12;
                        hitSound = despawnSound = Sounds.explosionMissile;
                        hitEffect = despawnEffect = new MultiEffect(
                                AreaFx.spikeFlash(splashDamageRadius, colBack),
                                new WaveEffect() {{
                                    lifetime = 10f;
                                    strokeFrom = 4f;
                                    sizeTo = splashDamageRadius * 1.5f;
                                }}
                        );

                    }}
            );
            // General
            TurretDef.general(this, 3);
            rotateSpeed = 5;
            // Shooting
            range = 60 * tilesize;
            reload = 2f * tick;
            shake = 6;
            limitRange();
            shootY = 70 * px;
            // Effects
            ammoUseEffect = Fx.none;
            shootSound = UAWSfx.shootLongsword;
            // Drawer
            drawer = new DrawTurret(modBase) {{
                float barrelMoveY = -6 * px;
                parts.addAll(
                        new RegionPart("-bottom"),
                        new RegionPart("-barrel-1") {{
                            progress = PartProgress.recoil.curve(Interp.pow3);
                            moveY = barrelMoveY;
                        }},
                        new RegionPart("-barrel-2") {{
                            progress = PartProgress.recoil.curve(Interp.pow3).curve(Interp.bounceIn);
                            moveY = barrelMoveY + (-6f * px);
                        }}
                );
            }};
        }};
        // endregion SN

    }
}