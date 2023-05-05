package UAW.content.blocks;

import UAW.audiovisual.*;
import UAW.content.*;
import UAW.entities.UAWUnitSorts;
import UAW.entities.bullet.*;
import UAW.entities.effects.*;
import UAW.world.blocks.defense.SpecificRegenProjector;
import UAW.world.blocks.defense.turrets.UAWItemTurret;
import UAW.world.blocks.defense.walls.ShieldWall;
import UAW.world.drawer.DrawPulses;
import arc.graphics.Color;
import arc.math.Interp;
import mindustry.content.*;
import mindustry.entities.UnitSorts;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.*;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.draw.*;

import static UAW.Vars.*;
import static UAW.content.UAWBullets.*;
import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;

/** Contains defense & support structures, such as Walls, Turrets, and booster */
public class UAWBlocksDefence {
	public static Block placeholder,

	// Tier 2
	quadra, ashlock, buckshot, skeeter,
	// Tier 3
	spitfire, longsword, tempest, strikeforce, zounderkite, redeemer,
	// Tier 4
	deadeye, skyhammer, hellseeker,
	// Tier 5

	// Energy
	heavylight, reticence, trailblazer, sundouser,

	// Wall
	shieldWall, stoutSteelWall, stoutSteelWallLarge,

	// Projectors
	statusFieldProjector, wallHealerProjector, wallHealerDome,

	// Ammunition Crafter
	casingRoller, bulletAssembler, minesAssembler;

	public static void load() {

		//region Serpulo
		//region MG
		quadra = new ItemTurret("quadra") {{
			requirements(Category.turret, with(
				Items.copper, 115,
				Items.lead, 120,
				Items.graphite, 80
			));
			size = 2;
			scaledHealth = 160;

			reload = 6f;
			recoil = 1f;
			recoilTime = reload * 4;
			maxAmmo = 30;

			range = 20 * tilesize;
			shootCone = 15f;
			inaccuracy = 7.5f;
			rotateSpeed = 10f;

			ammoUseEffect = Fx.casing2Double;
			shootSound = Sfx.wp_k_gunShootSmall_2;

			shoot = new ShootAlternate() {{
				barrels = 2;
				shots = 2;
				barrelOffset = 5;
				spread = 4f;
			}};

			ammo(
				Items.copper, new BasicBulletType(5f, 9) {{
					height = 9f;
					width = 7f;
					lifetime = 60f;
					shootEffect = Fx.shootSmall;
					smokeEffect = Fx.shootSmallSmoke;
					ammoMultiplier = 2;
				}},
				Items.graphite, new BasicBulletType(6f, 18) {{
					height = 12f;
					width = 9f;
					hitColor = frontColor = UAWPal.graphiteFront;
					backColor = UAWPal.graphiteBack;
					hitEffect = Fx.hitBulletColor;
					reloadMultiplier = 0.6f;
					ammoMultiplier = 4;
					lifetime = 60f;
				}},
				Items.titanium, new TrailBulletType(10f, 10f) {{
					height = 12f;
					width = 5f;
					frontColor = UAWPal.titaniumFront;
					backColor = UAWPal.titaniumBack;
					pierceArmor = true;
					shootEffect = Fx.shootSmallColor;
					smokeEffect = Fx.shootBigSmoke;
					ammoMultiplier = 3;
					trailInterval = 0.4f;
					trailEffect = Fx.disperseTrail;
					trailLengthScale = 0.6f;
				}},
				Items.pyratite, new BasicBulletType(5f, 15) {{
					height = 12f;
					width = 8f;
					frontColor = Pal.lightishOrange;
					backColor = Pal.lightOrange;
					status = StatusEffects.burning;
					hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.fireHit);
					ammoMultiplier = 5;
					splashDamage = 10f;
					splashDamageRadius = 22f;
					makeFire = true;
					lifetime = 60f;
				}},
				UAWItems.cryogel, new BasicBulletType(5f, 15) {{
					height = 12f;
					width = 8f;
					frontColor = UAWPal.cryoFront;
					hitColor = backColor = UAWPal.cryoBack;
					status = StatusEffects.freezing;
					hitEffect = new MultiEffect(Fx.hitBulletColor, UAWFx.cryoHit);
					ammoMultiplier = 5;
					splashDamage = 10f;
					splashDamageRadius = 22f;
					lifetime = 60f;
				}}
			);
			limitRange();

			squareSprite = false;
			cooldownTime = reload * 0.6f;
			drawer = new DrawTurret(modTurretBase) {{
				parts.addAll(
					new RegionPart("-barrel") {{
						progress = PartProgress.recoil;
						moveY = -5f * px;

					}},
					new RegionPart("-body"),
					new RegionPart("-back") {{
						progress = PartProgress.warmup;
						moveY = -5f * px;
					}}
				);
			}};
		}};
		spitfire = new ItemTurret("spitfire") {{
			requirements(Category.turret, with(
				Items.lead, 350,
				Items.titanium, 280,
				Items.thorium, 250,
				Items.plastanium, 250,
				Items.silicon, 150
			));
			size = 3;
			scaledHealth = 250;

			reload = 2.5f;
			recoil = 1f;
			recoilTime = 60f;
			maxAmmo = 200;
			minWarmup = 0.95f;
			shootWarmupSpeed = 0.025f;

			range = 35 * tilesize;
			inaccuracy = 7.5f;
			rotateSpeed = 7f;
			targetAir = false;

			shootSound = Sounds.shootBig;

			ammoUseEffect = Fx.casing2Double;

			shoot = new ShootBarrel() {{
				barrels = new float[]{
					0f, 2f, 0f,
					3f, 1f, 0f,
					-3f, 1f, 0f,
				};
			}};

			ammo(
				Items.graphite, new BasicBulletType(8, 20) {{
					pierceCap = 2;
					height = 22;
					width = 10;
					knockback = 1.2f;
					hitEffect = Fx.hitBulletBig;
					smokeEffect = Fx.shootBigSmoke2;
					shootEffect = Fx.shootBig2;
					trailChance = 0.4f;
					trailColor = Color.lightGray;

					status = StatusEffects.slow;
					collidesAir = false;
				}},
				UAWItems.stoutsteel, new TrailBulletType(12, 22) {{
					height = 20;
					width = 5;
					pierce = true;
					pierceArmor = true;
					despawnHit = true;
					frontColor = UAWPal.titaniumFront;
					backColor = UAWPal.titaniumBack;
					hitEffect = new MultiEffect(Fx.hitBulletColor, Fx.generatespark);
					smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.fireSmoke);
					shootEffect = Fx.shootBigColor;
					trailEffect = Fx.disperseTrail;
					trailChance = 0.7f;
					collidesAir = false;
				}},
				Items.pyratite, new BasicBulletType(7, 15) {{
					height = 25;
					width = 10;
					knockback = 1.2f;
					hitEffect = Fx.hitBulletBig;
					smokeEffect = Fx.shootBigSmoke2;
					shootEffect = new MultiEffect(Fx.shootPyraFlame, Fx.shootBig2);
					hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.fireHit);
					despawnEffect = Fx.fireHit;
					frontColor = Pal.lightishOrange;
					backColor = Pal.lightOrange;
					status = StatusEffects.burning;
					makeFire = true;
					trailChance = 0.4f;
					trailColor = Color.lightGray;
					collidesAir = false;
				}},
				UAWItems.cryogel, new BasicBulletType(7, 15) {{
					height = 25;
					width = 10;
					knockback = 1.2f;
					hitEffect = Fx.hitBulletBig;
					despawnEffect = Fx.freezing;
					smokeEffect = Fx.shootBigSmoke2;
					shootEffect = new MultiEffect(UAWFx.shootSmoke, Fx.shootBig2);
					hitEffect = new MultiEffect(Fx.hitBulletBig, UAWFx.cryoHit);
					despawnEffect = UAWFx.cryoHit;
					frontColor = UAWPal.cryoFront;
					backColor = UAWPal.cryoBack;
					status = StatusEffects.freezing;
					trailChance = 0.4f;
					trailColor = Color.lightGray;
					collidesAir = false;
				}},
				Items.surgeAlloy, new BasicBulletType(8, 15) {{
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
					collidesAir = false;
				}}
			);
			limitRange(2 * tilesize);

			squareSprite = false;
			cooldownTime = 2 * tick;
			drawer = new DrawTurret(modTurretBase) {{
				parts.addAll(
					new RegionPart("-barrel") {{
						heatProgress = PartProgress.heat.add(PartProgress.heat);
						progress = PartProgress.warmup.add(PartProgress.reload.add(-2.5f));
						moveY = 4 * px;
					}},
					new RegionPart("-side") {{
						progress = PartProgress.warmup.blend(PartProgress.heat, 0.45f);
						mirror = true;
						moveX = 2f * px;
						moveY = -4 * px;
						moveRot = -25f;
					}},
					new RegionPart("-blade") {{
						progress = PartProgress.warmup;
						mirror = true;
						moveX = -4 * px;
						moveY = 8 * px;
					}},
					new RegionPart("-back") {{
						progress = PartProgress.warmup;
						moveY = -2.5f;
					}},
					new RegionPart("-top")
				);
			}};
		}};
		//endregion MG

		//region Sniper
		ashlock = new ItemTurret("ashlock") {{
			requirements(Category.turret, with(
				Items.copper, 150,
				Items.graphite, 100,
				Items.titanium, 50
			));
			size = 2;
			scaledHealth = 250;

			reload = 1.75f * tick;
			recoil = 3f;
			ammoPerShot = 3;
			maxAmmo = 30;

			range = 30 * tilesize;
			inaccuracy = 0f;
			rotateSpeed = 5f;
			shake = 3.5f;

			shootSound = Sfx.wp_k_gunShootBig_1;
			soundPitchMin = 1.5f;
			soundPitchMax = 2f;

			ammoUseEffect = Fx.casing3;

			unitSort = UAWUnitSorts.mostHitPoints;

			ammoEjectBack = 9f;
			shootY = 10f;

			ammo(
				Items.graphite, new TrailBulletType(10f, 90f) {{
					hitSize = 6;
					height = 24f;
					width = 10f;
					frontColor = UAWPal.graphiteFront;
					backColor = UAWPal.graphiteMiddle;
					trailEffect = Fx.disperseTrail;
					trailChance = 0.8f;
					shootEffect = Fx.shootBigColor.wrap(frontColor);
					hitEffect = Fx.hitBulletColor.wrap(frontColor);
					trailColor = hitColor = backColor;
					despawnHit = true;
					smokeEffect = Fx.shootBigSmoke;
					reloadMultiplier = 0.5f;
					ammoMultiplier = 2;
					knockback = 1.2f;
				}},
				Items.silicon, new TrailBulletType(8f, 80f) {{
					hitSize = 5;
					height = 30f;
					width = 10f;
					homingPower = 0.2f;
					trailEffect = Fx.disperseTrail;
					trailChance = 0.8f;
					shootEffect = Fx.shootBigColor;
					hitEffect = Fx.hitBulletColor;
					trailColor = hitColor = backColor;
					despawnHit = true;
					ammoMultiplier = 2;
					pierceCap = 2;
				}},
				Items.thorium, new TrailBulletType(10f, 120f) {{
					hitSize = 5;
					height = 30f;
					width = 12f;
					frontColor = Pal.missileYellow;
					backColor = Pal.missileYellowBack;
					trailEffect = Fx.disperseTrail;
					trailChance = 0.8f;
					shootEffect = Fx.shootBigColor;
					hitEffect = Fx.hitBulletColor;
					trailColor = hitColor = backColor;
					despawnHit = true;
					knockback = 0.7f;
				}},
				Items.titanium, new TrailBulletType(11f, 70f) {{
					hitSize = 6f;
					height = 20f;
					width = 8f;
					despawnHit = true;
					pierceArmor = true;
					frontColor = UAWPal.titaniumFront;
					backColor = UAWPal.titaniumBack;
					trailEffect = Fx.disperseTrail;
					trailChance = 0.8f;
					shootEffect = Fx.shootBigColor;
					hitEffect = Fx.hitBulletColor;
					trailColor = hitColor = backColor;
					smokeEffect = Fx.shootBigSmoke;
					ammoMultiplier = 2;
					pierce = true;
				}}
			);
			limitRange();

			squareSprite = false;
			cooldownTime = reload * 0.5f;
			drawer = new DrawTurret(modTurretBase) {{
				parts.addAll(
					new RegionPart("-barrel") {{
						progress = PartProgress.recoil;
						moveY = -9 * px;
					}},
					new RegionPart("-breach") {{
						progress = PartProgress.reload;
						moveY = -12 * px;
						heatProgress = PartProgress.reload.add(1.5f);
					}},
					new RegionPart("-body")
				);
			}};
		}};
		longsword = new ItemTurret("longsword") {{
			requirements(Category.turret, with(
				Items.thorium, 400,
				Items.titanium, 275,
				Items.graphite, 250,
				Items.silicon, 200,
				Items.plastanium, 150
			));
			size = 3;
			scaledHealth = 250;

			reload = 2 * tick;
			recoil = 3f;
			ammoPerShot = 6;
			maxAmmo = 30;

			range = 65 * tilesize;
			shootCone = 1f;
			shake = 3f;
			rotateSpeed = 2.5f;

			shootSound = Sfx.wp_k_gunShootBig_2;
			ammoUseEffect = Fx.casing4;

			unitSort = UAWUnitSorts.mostHitPoints;

			ammo(
				Items.surgeAlloy, new TrailBulletType(15f, 400) {{
					hitSize = 8f;
					height = 28f;
					width = 12f;
					despawnHit = true;
					frontColor = UAWPal.surgeFront;
					backColor = UAWPal.surgeBack;
					trailEffect = new MultiEffect(
						Fx.disperseTrail,
						Fx.disperseTrail,
						Fx.disperseTrail
					);
					trailInterval = 0.05f;
					trailChance = 0.8f;
					shootEffect = new MultiEffect(
						UAWFx.railShoot(35, UAWPal.surgeBack),
						Fx.shootBigColor
					);
					hitEffect = new MultiEffect(
						UAWFx.railHit(35, UAWPal.surgeBack),
						Fx.hitBulletColor
					);
					trailColor = hitColor = backColor;
					smokeEffect = Fx.shootBigSmoke;
					pierceCap = 3;
				}},
				UAWItems.stoutsteel, new TrailBulletType(18f, 350) {{
					hitSize = 8f;
					height = 28f;
					width = 12f;
					despawnHit = true;
					pierceArmor = true;
					frontColor = UAWPal.graphiteFront;
					backColor = UAWPal.graphiteMiddle;
					trailEffect = new MultiEffect(
						Fx.disperseTrail,
						Fx.disperseTrail,
						Fx.disperseTrail
					);
					trailInterval = 0.05f;
					trailChance = 0.8f;
					shootEffect = new MultiEffect(
						UAWFx.railShoot(85, backColor),
						Fx.shootBigColor
					);
					hitEffect = new MultiEffect(
						UAWFx.railHit(40, hitColor),
						UAWFx.hitBulletBigColor
					);
					trailColor = hitColor = backColor;
					smokeEffect = Fx.shootBigSmoke;
					pierce = true;
				}}
			);
			limitRange();

			squareSprite = false;
			drawer = new DrawTurret(modTurretBase) {{
				parts.addAll(
					new RegionPart("-body"),
					new RegionPart("-barrel") {{
						progress = PartProgress.recoil.curve(Interp.pow2In);
						moveY = -9 * px;
						heatProgress = PartProgress.recoil;
					}}
				);
			}};
		}};
		deadeye = new ItemTurret("deadeye") {{
			requirements(Category.turret, with(
				Items.titanium, 1000,
				Items.lead, 1250,
				Items.thorium, 600,
				Items.metaglass, 900,
				Items.surgeAlloy, 900,
				UAWItems.stoutsteel, 800,
				Items.silicon, 1200
			));
			size = 4;
			scaledHealth = 250;

			reload = 10 * tick;
			recoil = 15;
			recoilTime = recoil * 2;
			ammoPerShot = 50;
			maxAmmo = 150;

			range = 125 * tilesize;
			shake = 28f;
			rotateSpeed = 0.625f;
			minWarmup = 0.95f;
			shootWarmupSpeed = 0.015f;

			shootSound = Sfx.wp_k_cannonShoot_2;

			ammoUseEffect = UAWFx.casing6;

			unitSort = UAWUnitSorts.mostHitPoints;
			ammo(
				UAWItems.stoutsteel, new HighVelocityShellBulletType(25f, 12500) {{
					height = 30f;
					trailChance = 0.8f;
					sideTrailInterval = 0.05f;
					frontColor = Pal.bulletYellow;
					backColor = Pal.bulletYellowBack;
					shootEffect = new MultiEffect(
						UAWFx.instShoot(95, backColor),
						Fx.shootBigColor
					);
					smokeEffect = new MultiEffect(
						Fx.shootBigSmoke,
						Fx.smokeCloud,
						UAWFx.shootSmokeMissile(backColor)
					);
					hitEffect = new MultiEffect(
						UAWFx.instHit(25, frontColor, backColor),
						Fx.blastsmoke,
						Fx.colorSparkBig,
						Fx.hitMeltdown,
						UAWFx.hitBulletBigColor
					);
					trailEffect = new MultiEffect(
						Fx.disperseTrail,
						Fx.hitMeltdown
					);
					sideTrail = UAWFx.sideTrail(43);
					hitShake = 28f;
					knockback = 24f;
					pierce = true;

					status = StatusEffects.slow;
				}}
			);
			consumePowerCond(15f, TurretBuild::isActive);

			squareSprite = false;
			cooldownTime = reload * 0.8f;
			drawer = new DrawTurret(modTurretBase) {{
				parts.addAll(
					new RegionPart("-barrel") {{
						progress = PartProgress.reload.add(-0.5f);
						mirror = true;
						moveX = 5 * px;
						moveY = -10f * px;
					}},
					new RegionPart("-side") {{
						progress = PartProgress.warmup.blend(PartProgress.reload, 0.3f);
						mirror = true;
						moveX = 4 * px;
						moveY = -8f * px;
						moveRot = -20f;
					}},
					new RegionPart("-body")
				);
			}};
		}};
		//endregion Sniper

		//region Artillery
		zounderkite = new UAWItemTurret("zounderkite") {{
			requirements(Category.turret, with(
				Items.lead, 300,
				Items.titanium, 280,
				Items.graphite, 325,
				Items.silicon, 250,
				Items.plastanium, 125
			));
			ammo(
				Items.blastCompound, mineCanisterBasic,
				UAWItems.cryogel, mineCanisterCryo,
				Items.pyratite, mineCanisterIncendiary,
				Items.surgeAlloy, mineCanisterEMP
			);
			size = 3;
			scaledHealth = 150;

			reload = 6 * tick;
			recoil = 4;
			recoilTime = recoil * 8;
			ammoPerShot = 15;
			maxAmmo = 45;
			canOverdrive = false;

			range = 50 * tilesize;
			minRange = range / 4;
			minWarmup = 0.95f;
			shootWarmupSpeed = 0.02f;

			shootSound = Sfx.wp_lnch_springShoot_2;

			ammoUseEffect = UAWFx.casingCanister;

			cooldownTime = reload * 0.8f;
			drawer = new DrawTurret(modTurretBase) {{
				parts.addAll(
					new RegionPart("-barrel") {{
						progress = PartProgress.recoil;
						moveY = -6 * px;
					}},
					new RegionPart("-piston") {{
						progress = PartProgress.warmup.blend(PartProgress.recoil, -0.3f);
						mirror = true;
						moveX = 10 * px;
						moveY = 12 * px;
					}},
					new RegionPart("-side") {{
						progress = PartProgress.warmup.blend(PartProgress.reload, -0.3f);
						mirror = true;
						moveRot = -45;
						moveX = 21 * px;
						moveY = 9 * px;
					}},
					new RegionPart("-body")
				);
			}};
			limitRange();
		}};
		skyhammer = new UAWItemTurret("skyhammer") {{
			requirements(Category.turret, with(
				Items.copper, 1000,
				Items.lead, 650,
				Items.graphite, 500,
				Items.plastanium, 325,
				Items.silicon, 325,
				UAWItems.stoutsteel, 250
			));
			size = 4;
			scaledHealth = 250;

			reload = 12 * tick;
			recoil = 15 * px;
			ammoPerShot = 20;
			maxAmmo = ammoPerShot * 3;

			range = 65 * tilesize;
			inaccuracy = 4f;
			rotateSpeed = 1f;
			shake = 48f;
			minRange = range / 4.5f;
			minWarmup = 0.95f;
			shootWarmupSpeed = 0.01f;
			velocityRnd = 0.3f;

			targetAir = false;
			unitSort = UAWUnitSorts.biggest;

			shootSound = Sfx.wp_k_cannonShoot_1;
			ammoUseEffect = UAWFx.casing7;

			ammo(
				Items.pyratite, new SplashArtilleryBulletType(2.5f, 3500) {{
					height = 40;
					width = 18;
					splashDamageRadius = 14 * tilesize;
					frontColor = UAWPal.incendFront;
					backColor = UAWPal.incendBack;
					shootEffect = new MultiEffect(
						UAWFx.railShoot(height * 1.8f, backColor),
						UAWFx.burstCloud(backColor),
						UAWFx.shootSmoke(width, frontColor)
					);
					hitEffect = UAWFx.dynamicExplosion(splashDamageRadius, frontColor, backColor);
					hitSound = Sfx.exp_n_impactHuge_1;
					hitSoundVolume = 3f;
					hitShake = 34f;
					generateTrail = true;
					trailInterval = 7.5f;
					trailEffect = new MultiEffect(
						Fx.artilleryTrail,
						new StatusHitEffect() {{
							amount = 8;
							life = 30f;
							spreadRad = 9f;
							sizeStart = 0.4f;
							sizeEnd = 2;
							color1 = backColor.lerp(frontColor, 0.5f);
							color2 = Pal.lightishGray;
						}}
					);
					smokeEffect = Fx.smokeCloud;
					status = StatusEffects.burning;
					aftershock = new AftershockBulletType(450, splashDamageRadius * 0.75f) {{
						splashAmount = 4;
						splashDelay = 45;
						frontColor = UAWPal.incendFront;
						backColor = UAWPal.incendBack;
						status = StatusEffects.burning;
						statusDuration = 60f;
						particleEffect = new StatusHitEffect() {{
							color1 = frontColor;
							life = 40;
							amount = 4;
							sizeEnd = 2f;

						}};
						makeFire = true;
						applySound = Sounds.fire;
					}};
				}},
				UAWItems.cryogel, new SplashArtilleryBulletType(2.5f, 3500) {{
					height = 40;
					width = 18;
					splashDamageRadius = 14 * tilesize;
					frontColor = UAWPal.cryoFront;
					backColor = UAWPal.cryoBack;
					shootEffect = new MultiEffect(
						UAWFx.railShoot(height * 1.8f, backColor),
						UAWFx.burstCloud(backColor),
						UAWFx.shootSmoke(width, frontColor)
					);
					hitEffect = UAWFx.dynamicExplosion(splashDamageRadius, frontColor, backColor);
					hitSound = Sfx.exp_n_impactHuge_1;
					hitSoundVolume = 3f;
					hitShake = 34f;
					generateTrail = true;
					trailInterval = 7.5f;
					trailEffect = new MultiEffect(
						Fx.artilleryTrail,
						new StatusHitEffect() {{
							amount = 8;
							life = 30f;
							spreadRad = 9f;
							sizeStart = 0.4f;
							sizeEnd = 2;
							color1 = backColor.lerp(frontColor, 0.5f);
							color2 = Pal.lightishGray;
						}}
					);
					smokeEffect = Fx.smokeCloud;
					status = UAWStatusEffects.cryoBurn;
					aftershock = new AftershockBulletType(450, splashDamageRadius * 0.75f) {{
						splashAmount = 4;
						splashDelay = 45;
						frontColor = UAWPal.cryoFront;
						backColor = UAWPal.cryoBack;
						status = UAWStatusEffects.cryoBurn;
						statusDuration = 60f;
						particleEffect = new StatusHitEffect() {{
							color1 = frontColor;
							life = 40;
							amount = 4;
							sizeEnd = 2f;

						}};
						makeFire = true;
						applySound = Sounds.fire;
					}};
				}},
				Items.plastanium, artilleryLargeFrag,
				Items.thorium, new SplashArtilleryBulletType(2.5f, 5500) {{
					height = 40;
					width = 18;
					splashDamageRadius = 14 * tilesize;
					frontColor = Pal.missileYellow;
					backColor = Pal.missileYellowBack;
					shootEffect = new MultiEffect(
						UAWFx.railShoot(height * 1.8f, backColor),
						UAWFx.burstCloud(backColor),
						UAWFx.shootSmoke(width, frontColor)
					);
					hitEffect = UAWFx.dynamicExplosion(splashDamageRadius, frontColor, backColor);
					hitSound = Sfx.exp_n_impactHuge_1;
					hitSoundVolume = 3f;
					hitShake = 34f;
					generateTrail = true;
					trailInterval = 7.5f;
					trailEffect = new MultiEffect(
						Fx.artilleryTrail,
						new StatusHitEffect() {{
							amount = 8;
							life = 30f;
							spreadRad = 9f;
							sizeStart = 0.4f;
							sizeEnd = 2;
							color1 = backColor.lerp(frontColor, 0.5f);
							color2 = Pal.lightishGray;
						}}
					);
					smokeEffect = Fx.smokeCloud;
					status = UAWStatusEffects.concussion;
					aftershock = new AftershockBulletType(450, splashDamageRadius * 0.8f) {{
						splashAmount = 4;
						splashDelay = 45;
						lifetime = (splashDelay * splashAmount);
						frontColor = Pal.missileYellow;
						backColor = Pal.missileYellowBack;
						status = UAWStatusEffects.concussion;
						statusDuration = 30f;
						particleEffect = new StatusHitEffect() {{
							shapeVariant = 1;
							color1 = frontColor;
							life = 40;
							amount = 4;
							sizeEnd = 2f;

						}};
						makeFire = true;
						applySound = Sounds.fire;
					}};
				}}
			);
			limitRange();

			cooldownTime = reload * 0.8f;
			drawer = new DrawTurret(modTurretBase) {{
				parts.addAll(
					new RegionPart("-side-front") {{
						progress = PartProgress.warmup.delay(0.25f).blend(PartProgress.reload, 0.45f);
						mirror = true;
						moveRot = -50;
						moveX = 25 * px;
						moveY = 22 * px;
					}},
					new RegionPart("-side-bottom") {{
						progress = PartProgress.warmup.blend(PartProgress.reload, 0.5f);
						mirror = true;
						moveX = 7 * px;
						moveY = -7 * px;
					}},
					new RegionPart("-barrel-front") {{
						progress = PartProgress.recoil.curve(Interp.sine);
						moveY = -5 * px;
					}},
					new RegionPart("-barrel-back") {{
						progress = PartProgress.recoil;
						moveY = (-5 * px) + (-8 * px);
					}}
				);
			}};
		}};
		//endregion Artillery

		//region Shotcannon
		buckshot = new ItemTurret("buckshot") {{
			requirements(Category.turret, with(
				Items.copper, 120,
				Items.lead, 200,
				Items.graphite, 100
			));
			size = 2;
			scaledHealth = 150;

			reload = 1 * tick;
			recoil = 3;
			recoilTime = recoil * 3;
			maxAmmo = 48;

			range = 100f;
			shake = 2f;
			inaccuracy = 5f;
			rotateSpeed = 4f;
			shootCone = 0.5f;

			ammoUseEffect = Fx.casing3;
			shootSound = Sounds.artillery;

			unitSort = UnitSorts.closest;

			targetAir = false;

			shoot = new ShootSpread() {{
				spread = 2.8f;
				shots = 8;
				velocityRnd = 0.3f;
			}};

			ammo(
				Items.lead, new BuckshotBulletType(7, 4.5f, 12f) {{
					pierceCap = 3;
					knockback = 5f;
					despawnHit = true;
					hitEffect = Fx.hitBulletColor;
					despawnEffect = Fx.flakExplosion;
					shootEffect = Fx.shootBigColor;
					smokeEffect = Fx.shootSmallSmoke;
					ammoMultiplier = 3;
				}},
				Items.graphite, new BuckshotBulletType(7, 3f, 16f) {{
					pierceCap = 4;
					knockback = 8f;
					reloadMultiplier = 0.5f;
					frontColor = UAWPal.graphiteFront;
					backColor = UAWPal.graphiteBack;
					hitEffect = Fx.hitBulletColor;
					despawnEffect = Fx.flakExplosion;
					shootEffect = Fx.shootBigColor;
					smokeEffect = Fx.shootSmallSmoke;
					ammoMultiplier = 3;
				}},
				Items.pyratite, new BuckshotBulletType(7, 4.5f, 9f) {{
					pierceCap = 3;
					knockback = 4f;
					frontColor = Pal.lightishOrange;
					backColor = Pal.lightOrange;
					shootEffect = Fx.shootSmallColor;
					smokeEffect = Fx.shootSmallSmoke;
					hitEffect = new MultiEffect(Fx.hitBulletColor, Fx.fireHit, Fx.fireSmoke);
					despawnEffect = new MultiEffect(Fx.flakExplosion, Fx.fireHit, Fx.fireSmoke);
					status = StatusEffects.burning;
				}}
			);
			limitRange();

			squareSprite = false;
			drawer = new DrawTurret(modTurretBase) {{
				parts.addAll(
					new RegionPart("-barrel") {{
						progress = PartProgress.recoil;
						moveY = -9 * px;
					}},
					new RegionPart("-side") {{
						progress = PartProgress.reload;
						mirror = true;
						moveX = 6 * px;
						moveY = -moveX;
					}},
					new RegionPart("-body")
				);
			}};
		}};
		tempest = new ItemTurret("tempest") {{
			requirements(Category.turret, with(
				Items.titanium, 200,
				Items.graphite, 150,
				Items.metaglass, 100,
				Items.silicon, 100
			));
			size = 3;
			scaledHealth = 120;

			reload = 45f;
			recoil = 8 * px;
			recoilTime = reload * 4;
			maxAmmo = 120;

			shoot.shots = 4;
			shoot.shotDelay = 15 * 0.25f;

			range = 38 * tilesize;
			shake = 0.8f;
			inaccuracy = 6f;
			rotateSpeed = 4f;

			ammoUseEffect = Fx.casing3;
			shootSound = Sfx.wp_k_gunShoot_4;
			soundPitchMin = 0.7f;
			soundPitchMax = soundPitchMin + 0.2f;

			unitSort = UAWUnitSorts.highest;

			ammo(
				Items.plastanium, new FlakTrailBulletType(8f, 30) {{
					Color front = Pal.plastaniumFront, back = Pal.plastaniumBack;
					height = 18f;
					width = 9f;
					splashDamageRadius = 2f * tilesize;
					splashDamage = 10f;
					shootEffect = Fx.shootBigColor;
					smokeEffect = new MultiEffect(
						new ShootSmokeEffect() {{
							color = back;
							split = true;
							splitAngle = 100f;
							spreadRange = 30;
							spreadCone = 35;
						}}
					);
					frontColor = front;
					hitColor = backColor = trailColor = back;
					hitEffect = despawnEffect = Fx.blastExplosion;

					trailChance = 0.4f;
					trailEffect = new StatusHitEffect() {{
						life = 7.5f;
						color1 = back;
						shapeVariant = 2;
					}};

					fragRandomSpread = 9f;
					fragSpread = 12.5f;
					fragBullets = 4;
					fragVelocityMin = 0.4f;
					fragVelocityMax = 1.2f;

					explodeRange = 8 * tilesize;
					explodeDelay = 1f;

					despawnSound = Sounds.dullExplosion;
					collidesGround = true;

					fragBullet = new BuckshotBulletType(7.5f, 5f, 25) {{
						lifetime = 20f;
						trailChance = 0.25f;
						frontColor = front;
						hitColor = backColor = back;
						hitEffect = despawnEffect = UAWFx.blastExplosion(front, back);
						splashDamageRadius = 1.5f * tilesize;
						splashDamage = 8f;
					}};
				}},
				UAWItems.stoutsteel, new TrailBulletType(12, 45) {{
					height = 18f;
					width = 9f;
					pierceArmor = true;
					frontColor = UAWPal.graphiteFront;
					backColor = UAWPal.graphiteMiddle;
					trailEffect = new MultiEffect(
						Fx.disperseTrail,
						new StatusHitEffect() {{
							life = 30;
							color1 = backColor;
							shapeVariant = 2;
						}});
					trailInterval = 0.05f;
					trailChance = 0.4f;
					trailColor = hitColor = backColor;
					shootEffect = Fx.shootBigColor;
					smokeEffect = new MultiEffect(
						new ShootSmokeEffect() {{
							color = Pal.lightOrange;
							split = true;
							splitAngle = 120f;
							smokeSize = 2;
							spreadRange = 30;
							spreadCone = 25;
						}}
					);
					hitEffect = UAWFx.hitBulletBigColor;
					pierce = true;
					rangeChange = 8 * tilesize;
				}},
				Items.metaglass, new FlakTrailBulletType(7f, 22f) {{
					Color front = Pal.missileYellow, back = Pal.missileYellowBack;
					height = 18f;
					width = 9f;
					splashDamageRadius = 2f * tilesize;
					splashDamage = 10f;
					shootEffect = Fx.shootBigColor;
					smokeEffect = new MultiEffect(
						new ShootSmokeEffect() {{
							life = 30;
							color = Pal.lightOrange;
							split = true;
							splitAngle = 100f;
							spreadRange = 30;
							spreadCone = 35;
						}}
					);
					frontColor = front;
					hitColor = backColor = trailColor = back;
					hitEffect = despawnEffect = Fx.blastExplosion;

					trailChance = 0.5f;
					trailEffect = new StatusHitEffect() {{
						life = 15;
						color1 = back;
						shapeVariant = 2;
					}};

					fragRandomSpread = 9f;
					fragSpread = 40;
					fragBullets = 16;
					fragVelocityMin = 0.2f;
					fragVelocityMax = 1.4f;

					explodeRange = 3 * tilesize;
					explodeDelay = 1f;

					despawnSound = Sounds.dullExplosion;
					collidesGround = true;

					fragBullet = fragGlassFrag;
				}},
				Items.blastCompound, new TrailBulletType(7f, 35f) {{
					Color front = Pal.missileYellow, back = Pal.missileYellowBack;
					height = 18f;
					width = 9f;
					splashDamageRadius = 3f * tilesize;
					splashDamage = 30f;
					shootEffect = Fx.shootBigColor;
					smokeEffect = new MultiEffect(
						new ShootSmokeEffect() {{
							life = 30;
							color = Pal.lightOrange;
							split = true;
							splitAngle = 100f;
							spreadRange = 30;
							spreadCone = 35;
						}}
					);
					frontColor = front;
					hitColor = backColor = trailColor = back;
					hitEffect = despawnEffect = UAWFx.dynamicExplosion(splashDamageRadius);

					trailChance = 0.5f;
					trailEffect = new StatusHitEffect() {{
						life = 15;
						color1 = back;
						shapeVariant = 2;
					}};

					despawnSound = Sounds.dullExplosion;
					collidesGround = true;

					reloadMultiplier = 0.8f;
				}}
			);
			limitRange();

			squareSprite = false;
			drawer = new DrawTurret(modTurretBase) {{
				parts.addAll(
					new RegionPart("-barrel") {{
						progress = PartProgress.smoothReload;
						moveY = -7 * px;
					}},
					new RegionPart("-back") {{
						progress = PartProgress.smoothReload.blend(PartProgress.heat, 0.25f);
						mirror = true;
						moveX = 4 * px;
						moveY = -4 * px;
					}},
					new RegionPart("-front") {{
						progress = PartProgress.warmup.blend(PartProgress.heat, 0.25f);
						mirror = true;
						moveX = 11 * px;
						moveY = 4 * px;
					}},
					new RegionPart("-body")
				);
			}};
		}};
		strikeforce = new ItemTurret("strikeforce") {{
			requirements(Category.turret, with(
				Items.titanium, 350,
				Items.graphite, 300,
				Items.metaglass, 200,
				Items.silicon, 200,
				Items.surgeAlloy, 100
			));
			size = 3;
			scaledHealth = 350;

			reload = 90f;
			recoil = 4f;
			recoilTime = recoil * 8;
			ammoPerShot = 4;
			maxAmmo = 360;

			range = 175f;
			shake = 8f;
			inaccuracy = 8f;
			rotateSpeed = 3f;

			ammoUseEffect = Fx.casing4;
			shootSound = Sfx.wp_k_shotgunShoot_2;

			targetAir = false;

			shoot = new ShootSpread() {{
				shootY = 67 * px;
				spread = 3.2f;
				shots = 20;
				velocityRnd = 0.4f;
			}};

			ammo(
				Items.pyratite, new BuckshotBulletType(18, 6f, 60f) {{
					pierceCap = 3;
					frontColor = Pal.lightishOrange;
					backColor = Pal.lightOrange;
					smokeEffect = new MultiEffect(
						UAWFx.shootSmoke(bulletSize(), backColor, true),
						Fx.shootBigSmoke2
					);
					hitEffect = new MultiEffect(
						UAWFx.hitBulletBigColor,
						Fx.fireHit,
						Fx.blastsmoke
					);
					shootEffect = UAWFx.shootHugeColor;
					despawnEffect = UAWFx.blastExplosion(frontColor, backColor);
					status = StatusEffects.burning;
					ammoMultiplier = 4f;
					knockback = 8;
					collidesAir = false;
				}},
				UAWItems.cryogel, new BuckshotBulletType(18, 6f, 60f) {{
					pierceCap = 3;
					frontColor = UAWPal.cryoFront;
					backColor = UAWPal.cryoBack;
					smokeEffect = new MultiEffect(
						UAWFx.shootSmoke(bulletSize(), backColor, true),
						Fx.shootBigSmoke2
					);
					hitEffect = new MultiEffect(
						UAWFx.hitBulletBigColor,
						UAWFx.cryoHit,
						Fx.blastsmoke
					);
					shootEffect = UAWFx.shootHugeColor;
					despawnEffect = UAWFx.blastExplosion(frontColor, backColor);
					status = StatusEffects.freezing;
					ammoMultiplier = 4f;
					knockback = 8;
					collidesAir = false;
				}},
				Items.plastanium, new BuckshotBulletType(18, 6f, 60f) {{
					pierceCap = 3;
					frontColor = Pal.plastaniumFront;
					backColor = Pal.plastaniumBack;
					smokeEffect = new MultiEffect(
						UAWFx.shootSmoke(bulletSize(), backColor, true),
						Fx.shootBigSmoke2
					);
					hitEffect = new MultiEffect(
						UAWFx.hitBulletBigColor,
						UAWFx.plastHit,
						Fx.blastsmoke
					);
					shootEffect = UAWFx.shootHugeColor;
					despawnEffect = UAWFx.blastExplosion(frontColor, backColor);
					status = StatusEffects.tarred;
					ammoMultiplier = 4f;
					knockback = 8;
					collidesAir = false;
				}},
				Items.surgeAlloy, new BuckshotBulletType(18, 6f, 60f) {{
					splashDamage = 2.5f * tilesize;
					splashDamageRadius = 2.5f * tilesize;
					pierceCap = 2;
					frontColor = Color.white;
					backColor = Pal.lancerLaser;
					smokeEffect = new MultiEffect(
						UAWFx.shootSmoke(bulletSize(), backColor, true),
						Fx.shootBigSmoke2
					);
					hitEffect = new MultiEffect(
						UAWFx.hitBulletBigColor,
						Fx.blastsmoke
					);
					shootEffect = UAWFx.shootHugeColor;
					despawnEffect = UAWFx.empExplosion(splashDamageRadius, 3, backColor);
					status = UAWStatusEffects.EMP;
					hitSound = Sounds.plasmaboom;
					statusDuration = 0.3f * 60;
					ammoMultiplier = 6f;
					collidesAir = false;
				}}
			);
			limitRange();

			squareSprite = false;
			drawer = new DrawTurret(modTurretBase) {{
				parts.addAll(
					new RegionPart("-body"),
					new RegionPart("-barrel") {{
						progress = PartProgress.recoil.curve(Interp.pow2In).add(PartProgress.reload);
						heatProgress = PartProgress.heat.add(PartProgress.heat.add(PartProgress.recoil));
						moveY = -11 * px;
					}}
				);
			}};
		}};
		//endregion Shotcannon

		// Walls
		int wallHealthMultiplier = 4;

		shieldWall = new ShieldWall("force-wall") {{
			requirements(Category.defense, with(
				Items.phaseFabric, 6,
				Items.silicon, 4
			));
			size = 1;
			health = 250;
			shieldHealth = health * 20;
			cooldown = 2f;
			cooldownBrokenShield = 1f;
			researchCostMultiplier = 2f;

			consumePower(0.5f);
		}};

		stoutSteelWall = new Wall("stoutsteel-wall") {{
			requirements(Category.defense, with(
				UAWItems.stoutsteel, 8
			));
			size = 2;
			health = 300 * wallHealthMultiplier;
			chanceDeflect = 5f;
			crushDamageMultiplier = 1f;
			armor = Math.round(health / 80f);
		}};
		stoutSteelWallLarge = new Wall("stoutsteel-wall-large") {{
			size = 3;
			requirements(Category.defense, with(
				UAWItems.stoutsteel, 12
			));
			health = 300 * wallHealthMultiplier * 3;
			chanceDeflect = 6f;
			crushDamageMultiplier = 0.8f;
			armor = Math.round(health / 80f);
		}};

		// Projectors
		wallHealerProjector = new SpecificRegenProjector("wall-healer-projector") {{
			requirements(Category.effect, with(
				Items.lead, 150,
				Items.titanium, 55,
				Items.silicon, 35,
				Items.metaglass, 35
			));
			size = 2;
			range = 11;
			healPercent = 3.5f / tick;
			health = 50 * (int) (Math.pow(size, 3));
			effect = new StatusHitEffect() {{
				color1 = Pal.plastaniumFront;
				amount = 4;
			}};

			consumeItem(UAWItems.sulphur, 1).boost();
			consumePower(1.6f);
			consumeLiquid(UAWLiquids.petroleum, 0.5f);

			baseColor = UAWLiquids.petroleum.color;

			drawer = new DrawMulti(
				new DrawDefault(),
				new DrawPulses(false) {{
					square = true;
					pulseRad = (range / 2) * tilesize;
					layer = Layer.effect;
					color = UAWPal.lpgFront;
					stroke = 3f;
					timeScl = 400;
				}},
				new DrawGlowRegion("-glow") {{
					color = UAWPal.lpgMid;
				}},
				new DrawSoftParticles() {{
					color = UAWPal.lpgFront;
					color2 = UAWPal.lpgMid;
					alpha = 0.55f;
					particleRad = 9f;
					particleSize = 8f;
					particleLife = 130f;
					particles = 20;
				}}
			);
		}};
		wallHealerDome = new SpecificRegenProjector("wall-healer-dome") {{
			requirements(Category.effect, with(
				Items.lead, 150,
				Items.titanium, 55,
				Items.silicon, 35,
				Items.metaglass, 35
			));
			size = 3;
			range = 21;
			healPercent = 5.5f / tick;
			otherBlockHealMult = 0.4f;
			health = 50 * (int) (Math.pow(size, 3));
			effect = new StatusHitEffect() {{
				color1 = Pal.plastaniumFront;
				amount = 4;
			}};

			consumeItem(UAWItems.sulphur, 2).boost();
			consumePower(2.4f);
			consumeLiquid(UAWLiquids.petroleum, 1f);

			baseColor = UAWLiquids.petroleum.color;

			drawer = new DrawMulti(
				new DrawDefault(),
				new DrawPulses(false) {{
					square = true;
					pulseRad = (range / 2) * tilesize;
					layer = Layer.effect;
					color = UAWPal.lpgFront;
					stroke = 3f;
					timeScl = 400;
				}},
				new DrawGlowRegion("-glow-top") {{
					color = UAWPal.lpgMid;
				}},
				new DrawGlowRegion("-glow-bottom") {{
					color = Pal.turretHeat;
				}},
				new DrawSoftParticles() {{
					color = UAWPal.lpgFront;
					color2 = UAWPal.lpgMid;
					alpha = 0.55f;
					particleRad = 12f;
					particleSize = 10f;
					particleLife = 160f;
					particles = 30;
				}}
			);
		}};

//		minesAssembler = new MultiCrafter("blast-furnace") {{
//			requirements(Category.crafting, with(
//				Items.lead, 45,
//				Items.graphite, 30,
//				Items.thorium, 20
//			));
//			size = 3;
//
//			hasItems = true;
//			hasLiquids = true;
//			menu = simple;
//			resolvedRecipes = Seq.with(
//				new Recipe() {{
//					input = new IOEntry(
//						Seq.with(with(Items.silicon, 4)),
//						Seq.with()
//					);
//					output = new IOEntry(
//						Seq.with(with(Items.metaglass, 4)),
//						Seq.with()
//					);
//					craftTime = 2 * tick;
//				}},
//				new Recipe() {{
//					input = new IOEntry(
//						Seq.with(with(Items.metaglass, 4)),
//						Seq.with(),
//						85f / tick
//					);
//					output = new IOEntry(
//						Seq.with(with(Items.surgeAlloy, 4)),
//						Seq.with()
//					);
//					craftTime = 4 * tick;
//				}}
//			);
//		}};
		//endregion Serpulo
	}
}
