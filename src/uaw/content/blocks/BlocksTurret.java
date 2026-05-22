package uaw.content.blocks;

import arc.math.Interp;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootBarrel;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.draw.DrawTurret;
import uaw.audiovisual.UAWPal;
import uaw.audiovisual.UAWSfx;
import uaw.audiovisual.fx.ShootingFx;
import uaw.content.UAWItems;
import uaw.entities.bullet.TrailBulletType;
import uaw.utils.TurretDef;
import uaw.world.blocks.defense.turrets.ScalingItemTurret;

import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;
import static uaw.Vars.px;
import static uaw.Vars.tick;

public class BlocksTurret {

    public static Block placeholder,
    // MG
    quadra, spitfire, saintfire,
    // SN
    solo, longsword, deadeye;

    static String modTurretBase = "armoured-";

    public static void load() {

        // region MG

        quadra = new ScalingItemTurret("quadra") {{
            requirements(Category.turret, with(
                    Items.copper, 150,
                    Items.titanium, 50,
                    Items.graphite, 25
            ));
            ammo(
                    Items.copper, new TrailBulletType(15, 9) {{
                        height = 4f;
                        width = 3f;
                        ammoMultiplier = 4;
                        pierceCap = 2;

                        hitEffect = despawnEffect = Fx.hitBulletColor.wrap(Pal.copperAmmoBack);
                        hitColor = backColor = trailColor = Pal.copperAmmoBack;
                        frontColor = Pal.copperAmmoFront;
                    }},
                    Items.graphite, new TrailBulletType(12, 18) {{
                        height = 4f;
                        width = 3f;
                        ammoMultiplier = 6;
                        reloadMultiplier = 0.8f;

                        hitEffect = despawnEffect = Fx.hitBulletColor.wrap(Pal.graphiteAmmoBack);
                        hitColor = backColor = trailColor = Pal.graphiteAmmoBack;
                        frontColor = Pal.graphiteAmmoFront;
                    }},
                    Items.silicon, new TrailBulletType(12, 12) {{
                        height = 4f;
                        width = 3f;
                        homingPower = 0.3f;
                        reloadMultiplier = 1.5f;
                        ammoMultiplier = 8;

                        hitEffect = despawnEffect = Fx.hitBulletColor.wrap(Pal.siliconAmmoBack);
                        hitColor = backColor = trailColor = Pal.siliconAmmoBack;
                        frontColor = Pal.siliconAmmoFront;
                    }}
            );
            // General
            TurretDef.generalParams(this, 2);
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
                barrels = 2;
                shots = 2;
                barrelOffset = 5;
                spread = 5f;
                velocityRnd = 0.2f;
            }};
            limitRange();
            // Effects
            ammoUseEffect = Fx.casing2Double;
            shootSound = Sounds.shoot;

            cooldownTime = reload / 2;
            // Drawer
            drawer = new DrawTurret(modTurretBase) {{
                parts.addAll(
                        new RegionPart("-barrel") {{
                            progress = PartProgress.warmup;
                            moveY = -4.0f * px;
                        }},
                        new RegionPart("-bolt") {{
                            progress = PartProgress.recoil;
                            moveY = -7.0f * px;
                        }},
                        new RegionPart("-body")
                );
            }};
        }};

        spitfire = new ScalingItemTurret("spitfire") {{
            requirements(Category.turret, with(
                    Items.titanium, 350,
                    Items.silicon, 100,
                    Items.plastanium, 100,
                    UAWItems.tisic, 50
            ));
            float bHeight = 8f;
            float bWidth = bHeight / 2;
            ammo(
                    Items.graphite, new TrailBulletType(12, 12) {{
                        height = bHeight;
                        width = bWidth;
                        ammoMultiplier = 4;
                        pierceCap = 1;
                        knockback = 2;
                        reloadMultiplier = 0.75f;

                        hitEffect = despawnEffect = Fx.hitBulletColor.wrap(Pal.graphiteAmmoBack);
                        hitColor = backColor = trailColor = Pal.graphiteAmmoBack;
                        frontColor = Pal.graphiteAmmoFront;

                        smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.fireSmoke);
                        shootEffect = new MultiEffect(Fx.shootBig, Fx.sparkShoot);
                    }},
                    Items.titanium, new TrailBulletType(20, 10) {{
                        height = bHeight;
                        width = bWidth;
                        ammoMultiplier = 4;
                        pierceCap = 3;

                        hitEffect = despawnEffect = Fx.hitBulletColor.wrap(UAWPal.titaniumBack);
                        hitColor = backColor = trailColor = UAWPal.titaniumBack;
                        frontColor = UAWPal.titaniumFront;

                        smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.fireSmoke);
                        shootEffect = new MultiEffect(Fx.shootBig, Fx.sparkShoot);
                    }},
                    Items.surgeAlloy, new TrailBulletType(15, 8) {{
                        height = bHeight;
                        width = bWidth;
                        ammoMultiplier = 8;
                        pierceCap = 1;
                        status = StatusEffects.electrified;
                        lightning = 3;
                        lightningAngle = 270;
                        lightningDamage = 1.5f;
                        lightningLength = 7;
                        collidesAir = false;

                        hitEffect = despawnEffect = new MultiEffect(Fx.hitBulletColor.wrap(Pal.surgeAmmoBack), Fx.lightning);
                        hitColor = backColor = trailColor = Pal.surgeAmmoBack;
                        frontColor = Pal.surgeAmmoFront;

                        smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.fireSmoke);
                        shootEffect = new MultiEffect(Fx.shootBig, Fx.sparkShoot);
                    }}
            );
            // General
            TurretDef.generalParams(this, 3);
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
            drawer = new DrawTurret(modTurretBase) {{
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
        solo = new ItemTurret("solo") {{
            requirements(Category.turret, with(
                    Items.copper, 150,
                    Items.graphite, 50
            ));
            float bH = 14;
            float bW = 8;
            ammo(
                    Items.graphite, new TrailBulletType(15, 55) {{
                        height = bH;
                        width = bW;
                        pierce = true;
                        pierceCap = 2;
                        knockback = 3;

                        shootEffect = new MultiEffect(
                                Fx.shootBig,
                                ShootingFx.ellipseWave()
                        );
                        smokeEffect = Fx.shootSmokeDisperse;
                        hitEffect = despawnEffect = new MultiEffect(
                                Fx.hitBulletColor.wrap(Pal.graphiteAmmoBack),
                                ShootingFx.hitBulletBig(Pal.graphiteAmmoBack)
                        );
                        hitColor = backColor = trailColor = Pal.graphiteAmmoBack;
                        frontColor = Pal.graphiteAmmoFront;
                    }}
            );
            // General
            TurretDef.generalParams(this, 2);
            rotateSpeed = 7.5f;
            // Shooting
            range = 35 * tilesize;
            reload = 1.25f * tick;
            shake = 2.5f;
            limitRange();
            // Effects
            ammoUseEffect = Fx.casing3;
            shootSound = UAWSfx.shootSolo;
            // Drawer
            drawer = new DrawTurret(modTurretBase) {{
                float mX = 2 * px;
                float mY = -8 * px;
                float mRot = -26.5f;
                parts.addAll(
                        new RegionPart("-bottom"),
                        new RegionPart("-side-r") {{
                            progress = PartProgress.recoil.curve(Interp.bounceIn);
                            moveRot = -mRot;
                            moveX = mX;
                            moveY = mY;
                        }},
                        new RegionPart("-side-l") {{
                            progress = PartProgress.recoil.curve(Interp.bounceIn);
                            moveRot = mRot;
                            moveX = -mX;
                            moveY = mY;
                        }},
                        new RegionPart("-barrel") {{
                            progress = PartProgress.recoil.curve(Interp.pow3).curve(Interp.bounceIn);
                            moveY = -5f * px;
                        }}
                );
            }};
        }};
        // endregion SN

    }
}