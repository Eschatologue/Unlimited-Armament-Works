package UAW.content;

import UAW.entities.bullet.*;
import UAW.graphics.*;
import UAW.world.blocks.defense.EffectFieldProjector;
import UAW.world.blocks.defense.RejuvenationProjector;
import UAW.world.blocks.defense.turrets.DynamicReloadTurret;
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
import mindustry.world.draw.DrawLiquid;
import mindustry.world.draw.DrawSmelter;

import static UAW.content.UAWBullets.*;
import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;
import static mindustry.content.Bullets.*;

public class UAWBlock implements ContentList {

    public static Block
        // region Turret
        /// MG
        quadra, spitfire,
        /// Sniper
        solo, longsword,
        /// SG
        buckshot, tempest, strikeforce,
        /// VLS
        sparkler, sunspot,
        /// TMDS
        zounderkite,
        // crafters
        gelatinizer, carburizingFurnace, surgeMixer, coalLiquefier,
    // endregion
        // region Defense
        /// Walls
        shieldWall,
        /// Projectors
        statusFieldProjector,
        rejuvinationProjector, rejuvinationDome,
    // endregion
        // region UnitBlocks
        /// Reconstructors
        multiplicativePetroleumReconstructor, exponentialPetroleumReconstructor, tetrativePetroleumReconstructor;
    // endregion

    @Override
    public void load () {
        float tick = 60;
        // region Turrets
        // MG
        quadra = new DynamicReloadTurret("quadra") {{
            requirements(Category.turret, with(
                    Items.copper, 110,
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
            range = 140;
            shootCone = 15f;
            ammoUseEffect = Fx.casing2Double;
            inaccuracy = 4f;
            rotateSpeed = 10f;
            maxAmmo = 30;

            maxReloadScl = 5f;
            speedupPerShot = 0.1f;
            ammo(
                    Items.copper, Bullets.standardCopper,
                    Items.graphite, Bullets.standardDense,
                    Items.pyratite, UAWBullets.basicMelt,
                    Items.titanium, UAWBullets.basicPiercing
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
            reloadTime = 15f;
            range = 25 * tilesize;
            rotateSpeed = 7f;
            inaccuracy = 15;
            recoilAmount = 3f;
            restitution = 0.05f;
            shootSound = Sounds.shootBig;
            ammoUseEffect = Fx.casing2Double;
            xRand = 3;

            maxReloadScl = 12f;
            speedupPerShot = 0.1f;
            slowDownReloadTime = 90f;
            inaccuracyModifier = 0.4f;

            ammo(
                    Items.graphite, new BasicBulletType(8,20) {{
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
                    UAWItems.titaniumCarbide, new BasicBulletType(12,22) {{
                        height = 30;
                        width = 8;
                        lifetime = range / speed + 8;
                        pierce = true;
                        reloadMultiplier = 1.2f;
                        trailLength = 16;
                        trailWidth = 1.8f;
                        trailColor = backColor;
                        hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.generatespark);
                        despawnEffect = Fx.hitBulletBig;
                        smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.fireSmoke);
                        shootEffect = new MultiEffect(Fx.shootBig2, Fx.hitBulletBig);
                        status = UAWStatusEffects.breached;
                    }},
                    Items.surgeAlloy, new BasicBulletType(8 ,18) {{
                        height = 25;
                        width = 8;
                        lifetime = range / speed;
                        hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.lightning);
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
                    Items.pyratite, new BasicBulletType(7 ,15) {{
                        height = 25;
                        width = 10;
                        pierceCap = 1;
                        lifetime = range / speed;
                        knockback = 1.2f;
                        hitEffect = Fx.hitBulletBig;
                        despawnEffect = Fx.melting;
                        smokeEffect = Fx.shootBigSmoke2;
                        shootEffect = new MultiEffect(Fx.shootPyraFlame,Fx.shootBig2);
                        frontColor = UAWPal.incendFront;
                        backColor = UAWPal.incendBack;
                        status = StatusEffects.melting;
                        trailChance = 0.4f;
                        trailColor = Color.lightGray;
                        fragBullets = 8;
                        fragVelocityMin = 0.3f;
                        fragVelocityMax = fragVelocityMin * 1.2f;
                        fragLifeMin = 0.4f;
                        fragLifeMax = 0.8f;
                        fragBullet = Bullets.slagShot;
                    }},
                    UAWItems.cryogel, new BasicBulletType(7 ,15) {{
                        height = 25;
                        width = 10;
                        pierceCap = 1;
                        lifetime = range / speed;
                        knockback = 1.2f;
                        hitEffect = Fx.hitBulletBig;
                        despawnEffect = Fx.freezing;
                        smokeEffect = Fx.shootBigSmoke2;
                        shootEffect = new MultiEffect(UAWFxStatic.shootCryoFlame,Fx.shootBig2);
                        frontColor = UAWPal.cryoFront;
                        backColor = UAWPal.cryoBack;
                        status = StatusEffects.freezing;
                        trailChance = 0.4f;
                        trailColor = Color.lightGray;
                        fragBullets = 8;
                        fragVelocityMin = 0.3f;
                        fragVelocityMax = fragVelocityMin * 1.2f;
                        fragLifeMin = 0.4f;
                        fragLifeMax = 0.8f;
                        fragBullet = Bullets.cryoShot;
                    }}
            );
        }};

        // SN
        solo = new ItemTurret("solo") {{
            requirements(Category.turret, with(
                    Items.copper, 150,
                    Items.graphite, 100,
                    Items.titanium, 50
            ));
            health = 160 * size;
            size = 2;
            spread = 0f;
            shots = 1;
            reloadTime = 35f;
            shootShake = 3f;
            restitution = 0.05f;
            range = 160;
            shootCone = 1f;
            ammoUseEffect = Fx.casing3;
            shootSound = Sounds.shootBig;
            inaccuracy = 2f;
            rotateSpeed = 5f;
            unitSort = (u, x, y) -> -u.health;
            maxAmmo = 20;
            ammoPerShot = 2;
            ammo(
                    Items.copper, UAWBullets.heavyCopper,
                    Items.graphite, Bullets.standardDenseBig,
                    Items.thorium, Bullets.standardThoriumBig,
                    UAWItems.titaniumCarbide, UAWBullets.heavyPiercing
            );
        }};
        longsword = new ItemTurret("longsword") {{
            float brange = range = 55 * tilesize;
            requirements(Category.turret, with(
                    Items.thorium, 280,
                    UAWItems.titaniumCarbide, 250,
                    Items.graphite, 200,
                    Items.silicon, 180,
                    Items.plastanium, 150
            ));
            size = 3;
            health = 150 * size * size;
            maxAmmo = 30;
            ammoPerShot = 10;
            rotateSpeed = 2.5f;
            reloadTime = 75;
            ammoUseEffect = UAWFxStatic.casing6;
            recoilAmount = 4f;
            restitution = 0.01f;
            shootShake = 3f;

            shootCone = 1f;
            shootSound = Sounds.artillery;
            unitSort = (u, x, y) -> -u.health;
            ammo(
                    Items.surgeAlloy, new PointBulletType() {{
                        damage = 400;
                        speed = brange;
                        splashDamage = 200;
                        splashDamageRadius = 3 * 8;
                        shootEffect = new MultiEffect(UAWFxDynamic.railShoot(Pal.missileYellow, 32), Fx.blockExplosionSmoke);
                        hitEffect = Fx.railHit;
                        smokeEffect = Fx.smokeCloud;
                        trailEffect = Fx.railTrail;
                        hitEffect = despawnEffect = new MultiEffect(UAWFxDynamic.crossBlast(Pal.bulletYellow, splashDamageRadius), Fx.smokeCloud);
                        trailSpacing = 20f;
                        buildingDamageMultiplier = 0.5f;
                        hitShake = 6f;
                        ammoMultiplier = 1f;
                        reloadMultiplier = 1.5f;
                        status = StatusEffects.electrified;
                    }},
                    UAWItems.titaniumCarbide, new RailBulletType() {{
                        damage = 550;
                        length = 450;
                        shootEffect = new MultiEffect(UAWFxDynamic.railShoot(Pal.missileYellow, 32), Fx.blockExplosionSmoke);
                        hitEffect = pierceEffect = new MultiEffect(Fx.railHit, Fx.blockExplosionSmoke);
                        smokeEffect = Fx.smokeCloud;
                        updateEffect = Fx.railTrail;
                        updateEffectSeg = 30f;
                        buildingDamageMultiplier = 0.5f;
                        hitShake = 6f;
                        ammoMultiplier = 1f;
                        status = UAWStatusEffects.breached;
                    }}
            );
        }};

        // SG
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
        tempest = new ItemTurret("tempest") {{
            requirements(Category.turret, with(
                    Items.titanium, 200,
                    Items.graphite, 150,
                    Items.metaglass, 100,
                    Items.silicon, 100
            ));
            health = 120 * size * size;
            size = 3;
            spread = 1.8f;
            shots = 4;
            xRand = 3;
            reloadTime = 7f;
            shootShake = 0.6f;
            restitution = 0.08f;
            range = 30 * tilesize;
            shootCone = 2.3f;
            velocityInaccuracy = 0.2f;
            ammoUseEffect = Fx.casing3;
            shootSound = UAWSounds.ShotgunShotAuto1;
            inaccuracy = 6f;
            rotateSpeed = 4f;
            maxAmmo = 60;
            ammoPerShot = 6;
            ammo(
                    Items.graphite, new BuckshotBulletType(5f, 12f) {{
                        lifetime = range / speed;
                        knockback = 4f;
                    }},
                    Items.pyratite,  new BuckshotBulletType(5f, 8f) {{
                        shootEffect = Fx.shootPyraFlame;
                        smokeEffect = Fx.shootBigSmoke2;
                        hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.burning);
                        despawnEffect = UAWFxDynamic.thermalExplosion(frontColor, backColor);
                        frontColor = Pal.lightishOrange;
                        backColor = Pal.lightOrange;
                        status = StatusEffects.melting;
                        fragBullets = 6;
                        fragBullet = slagShot;
                        fragVelocityMin = 0.3f;
                        fragVelocityMax = fragVelocityMin * 1.2f;
                        fragLifeMin = 0.4f;
                        fragLifeMax = 0.8f;
                    }},
                    UAWItems.cryogel, new BuckshotBulletType(5f, 8f) {{
                        lifetime = range / speed;
                        shootEffect = UAWFxStatic.shootCryoFlame;
                        smokeEffect = Fx.shootBigSmoke2;
                        hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.freezing);
                        despawnEffect = UAWFxDynamic.thermalExplosion(frontColor, backColor);
                        frontColor = UAWPal.cryoFront;
                        backColor = UAWPal.cryoMiddle;
                        status = StatusEffects.freezing;
                        fragBullets = 6;
                        fragBullet = cryoShot;
                        fragVelocityMin = 0.3f;
                        fragVelocityMax = fragVelocityMin * 1.2f;
                        fragLifeMin = 0.4f;
                        fragLifeMax = 0.8f;
                    }},
                    UAWItems.titaniumCarbide, new BuckshotBulletType(6f, 10f) {{
                        pierceCap = 5;
                        lifetime = (range / speed) + 8;
                        knockback = 0f;
                        shootEffect = Fx.shootBig2;
                        smokeEffect = Fx.shootBigSmoke2;
                        trailLength = 9;
                        trailWidth = 1.6f;
                        trailChance = trailInterval = 0;
                        hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.sparkShoot);
                        despawnEffect = UAWFxDynamic.thermalExplosion(frontColor, backColor);
                        status = UAWStatusEffects.breached;
                    }},
                    Items.metaglass, new BuckshotBulletType(5f, 8f) {{
                        splashDamageRadius = 1.8f * tilesize;
                        splashDamage = damage / 2;
                        lifetime = range / speed;
                        fragBullets = 6;
                        fragBullet = new BasicBulletType(3f, 3, "bullet"){{
                            width = 5f;
                            height = 12f;
                            shrinkY = 1f;
                            lifetime = 20f;
                            backColor = Pal.gray;
                            frontColor = Color.white;
                            despawnEffect = Fx.none;
                        }};
                    }}
            );
        }};
        strikeforce = new ItemTurret("strikeforce") {{
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
                    }}
            );
        }};

        // endregion Turret
        // region Mine Deployment
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
            shootSound = UAWSounds.LauncherShot1;
            ammoUseEffect= UAWFxStatic.casingCanister;
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
                    }},
                    Items.copper, new DamageFieldBulletType(50f, 5) {{
                    }}
            );
        }};
        // endregion Mine Deployment
        // region Crafters
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
        // endregion
        // region Defense
        // region Walls
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
        // endregion Walls
        // region Projector
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
                    Liquids.cryofluid, new DamageFieldBulletType(0, range) {{
                        applyEffect = UAWFxDynamic.statusFieldApply(UAWPal.cryoFront, UAWPal.cryoBack, range);
                        status = StatusEffects.freezing;
                        statusDuration = reloadTime * 1.5f;
                        splashAmount = 1;
                    }},
                    Liquids.slag, new DamageFieldBulletType(0, range) {{
                        applyEffect = UAWFxDynamic.statusFieldApply(Pal.lighterOrange, Pal.lightOrange, range);
                        status = StatusEffects.melting;
                        statusDuration = reloadTime * 1.5f;
                        splashAmount = 1;
                    }},
                    Liquids.oil, new DamageFieldBulletType(0, range) {{
                        applyEffect = UAWFxDynamic.statusFieldApply(Pal.plastaniumFront, Pal.plastaniumBack, range);
                        status = StatusEffects.tarred;
                        statusDuration = reloadTime * 1.5f;
                        splashAmount = 1;
                    }},
                    UAWLiquid.surgeSolvent, new DamageFieldBulletType(0, range) {{
                        applyEffect = UAWFxDynamic.statusFieldApply(UAWPal.surgeFront, UAWPal.surgeBack, range);
                        status = StatusEffects.electrified;
                        statusDuration = reloadTime * 1.5f;
                        splashAmount = 1;
                    }}
            );
            consumes.power(2.4f);
        }};
        rejuvinationProjector = new RejuvenationProjector("rejuvination-projector"){{
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
        rejuvinationDome = new RejuvenationProjector("rejuvination-dome"){{
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
        // endregion projector
        // endregion Defense
        // region Units
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
        // endregion Units
    }
}
