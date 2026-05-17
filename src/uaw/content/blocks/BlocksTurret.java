package uaw.content.blocks;

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
import mindustry.world.draw.DrawTurret;
import uaw.content.UAWItems;
import uaw.entities.bullet.TrailBulletType;
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

            size = 2;
            scaledHealth = 150;

            reload = 25;
            maxReloadScale = 4;
            recoil = 1f;
            recoilTime = 30f;
            maxAmmo = 100;

            range = 20 * tilesize;
            shake = 0.5f;
            shootCone = 15f;
            inaccuracy = 5f;
            rotateSpeed = 10f;

            ammoUseEffect = Fx.casing2Double;
            shootSound = Sounds.shoot;

            shoot = new ShootAlternate() {{
                barrels = 2;
                shots = 2;
                barrelOffset = 5;
                spread = 5f;
                velocityRnd = 0.2f;
            }};

            limitRange();

            squareSprite = false;
            cooldownTime = reload / 2;
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
            ammo(
                    Items.graphite, new TrailBulletType(15, 10) {{
                        height = 8;
                        width = 4;
                        ammoMultiplier = 2.5f;

                        reloadMultiplier = 0.75f;

                        hitEffect = despawnEffect = Fx.hitBulletColor.wrap(Pal.graphiteAmmoBack);
                        hitColor = backColor = trailColor = Pal.graphiteAmmoBack;
                        frontColor = Pal.graphiteAmmoFront;

                        smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.fireSmoke);
                        shootEffect = new MultiEffect(Fx.shootBig, Fx.sparkShoot);
                    }},
                    Items.surgeAlloy, new TrailBulletType(18, 8) {{
                        height = 8;
                        width = 4;
                        ammoMultiplier = 10;
                        pierceCap = 2;

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
            size = 3;
            scaledHealth = 250;

            reload = 15f; // Slower RoF than Cyclone (10f)
            maxReloadScale = 8;
            accelTime = 10 * tick;
            decayTime = 5 * tick;

            recoil = 1f;
            recoilTime = 60f;
            maxAmmo = 250;

            range = 25 * tilesize;
            shake = 0.75f;
            inaccuracy = 7f;
            rotateSpeed = 7f;
            targetAir = false;

            ammoUseEffect = Fx.casing2;
            shootSound = Sounds.shootCyclone;

            coolant = consumeCoolant(1.5f);
            coolantMultiplier = 8f;

            shoot = new ShootBarrel() {{
                barrels = new float[]{
                        0f, 2f, 0f,
                        3f, 1f, 0f,
                        -3f, 1f, 0f,
                };
                velocityRnd = 0.2f;
            }};

            limitRange(2 * tilesize);

            squareSprite = false;
            cooldownTime = 2 * tick;
            drawer = new DrawTurret(modTurretBase) {{
                parts.addAll(
                        new RegionPart("-barrel") {{
                            heatProgress = PartProgress.smoothReload;
                            progress = PartProgress.warmup.add(PartProgress.reload.add(-2.5f));
                            moveY = 4 * px;
                        }},
                        new RegionPart("-back") {{
                            progress = PartProgress.warmup.min(PartProgress.recoil);
                            moveY = -7 * px;
                        }},
                        new RegionPart("-top")
                );
            }};
        }};
    }
}