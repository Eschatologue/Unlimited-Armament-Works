package UAW.content.blocks;

import UAW.content.*;
import UAW.entities.UAWUnitSorts;
import UAW.entities.bullet.ModdedVanillaBullet.*;
import UAW.graphics.*;
import UAW.world.blocks.defense.RejuvenationProjector;
import UAW.world.blocks.defense.turrets.UAWItemTurret;
import UAW.world.blocks.defense.walls.ShieldWall;
import arc.graphics.Color;
import arc.math.Interp;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.*;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.draw.DrawTurret;

import static UAW.Vars.*;
import static UAW.content.UAWBullets.*;
import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;

/** Contains defense & support structures, such as Walls, Turrets, and booster */
public class UAWBlocksDefense {
	public static Block placeholder,
	// Gatling
	quadra, spitfire, equalizer,
	// Sniper/Railgun
	ashlock, longbow, deadeye,
	// Shotcannon
	buckshot, tempest, strikeforce,
	// Artillery
	zounderkite, skyhammer,
	// Energy
	heavylight, silence, trailblazer, sundouser,

	// Defense
	shieldWall, statusFieldProjector, rejuvinationProjector, rejuvinationDome;

	public static void load() {
		quadra = new ItemTurret("quadra") {{
			requirements(Category.turret, with(
				Items.copper, 115,
				Items.lead, 120,
				Items.graphite, 80
			));
			size = 2;
			scaledHealth = 160;

			reload = 6f;
			recoil = 0.25f;
			recoilTime = reload * 3;
			maxAmmo = 30;

			range = 20 * tilesize;
			shootCone = 15f;
			inaccuracy = 7.5f;
			rotateSpeed = 10f;

			ammoUseEffect = Fx.casing2Double;

			shoot = new ShootAlternate() {{
				barrels = 2;
				shots = 2;
				barrelOffset = 5;
				spread = 4f;
			}};

			ammo(
				Items.copper, new BasicBulletType(5f, 9) {{
					width = 7f;
					height = 9f;
					lifetime = 60f;
					shootEffect = Fx.shootSmall;
					smokeEffect = Fx.shootSmallSmoke;
					ammoMultiplier = 2;
				}},
				Items.graphite, new BasicBulletType(6f, 18) {{
					width = 9f;
					height = 12f;
					reloadMultiplier = 0.6f;
					ammoMultiplier = 4;
					lifetime = 60f;
				}},
				Items.titanium, new TrailBulletType(10f, 10f) {{
					height = 15f;
					width = 5f;
					pierceArmor = true;
					shootEffect = Fx.shootBig;
					smokeEffect = Fx.shootBigSmoke;
					ammoMultiplier = 3;
					trailLenghtScl = 1f;
				}},
				Items.pyratite, new BasicBulletType(5f, 16) {{
					width = 10f;
					height = 12f;
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
				UAWItems.cryogel, new BasicBulletType(5f, 16) {{
					width = 10f;
					height = 12f;
					frontColor = UAWPal.cryoFront;
					backColor = UAWPal.cryoBack;
					status = StatusEffects.freezing;
					hitEffect = new MultiEffect(Fx.hitBulletSmall, UAWFxS.cryoHit);
					ammoMultiplier = 5;
					splashDamage = 10f;
					splashDamageRadius = 22f;
					lifetime = 60f;
				}}
			);
			limitRange();

			squareSprite = false;
			drawer = new DrawTurret(modTurretBase) {{
				parts.addAll(
					new RegionPart("-barrel") {{
						progress = PartProgress.reload;
						moveY = -5f * px;
						heatProgress = PartProgress.reload;

					}},
					new RegionPart("-body"),
					new RegionPart("-back") {{
						progress = PartProgress.reload;
						moveY = -6f * px;
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
				}},
				UAWItems.compositeAlloy, new TrailBulletType(12, 22) {{
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
				}},
				UAWItems.cryogel, new BasicBulletType(7, 15) {{
					height = 25;
					width = 10;
					knockback = 1.2f;
					hitEffect = Fx.hitBulletBig;
					despawnEffect = Fx.freezing;
					smokeEffect = Fx.shootBigSmoke2;
					shootEffect = new MultiEffect(UAWFxS.shootCryoFlame, Fx.shootBig2);
					hitEffect = new MultiEffect(Fx.hitBulletBig, UAWFxS.cryoHit);
					despawnEffect = UAWFxS.cryoHit;
					frontColor = UAWPal.cryoFront;
					backColor = UAWPal.cryoBack;
					status = StatusEffects.freezing;
					trailChance = 0.4f;
					trailColor = Color.lightGray;
				}}
			);
			limitRange(2 * tilesize);

			squareSprite = false;
			drawer = new DrawTurret(modTurretBase) {{
				parts.addAll(
					new RegionPart("-barrel") {{
						progress = PartProgress.warmup.add(PartProgress.reload.add(-2.5f));
						moveY = 4 * px;
						heatProgress = PartProgress.reload;
					}},
					new RegionPart("-side") {{
						progress = PartProgress.warmup;
						mirror = true;
						moveX = 2f * px;
						moveY = -16 * px;
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

		ashlock = new ItemTurret("ashlock") {{
			requirements(Category.turret, with(
				Items.copper, 150,
				Items.graphite, 100,
				Items.titanium, 50
			));
			size = 2;
			scaledHealth = 160;

			reload = 1.75f * tick;
			recoil = 3f;
			ammoPerShot = 3;
			maxAmmo = 30;

			range = 30 * tilesize;
			inaccuracy = 0f;
			rotateSpeed = 5f;
			shake = 3.5f;

			shootSound = UAWSfx.cannonShoot1;
			soundPitchMin = 1.8f;
			soundPitchMax = 2.5f;

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
					shootEffect = Fx.shootBigColor;
					hitEffect = Fx.hitBulletColor;
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
			drawer = new DrawTurret(modTurretBase) {{
				parts.addAll(
					new RegionPart("-barrel") {{
						progress = PartProgress.reload;
						moveY = -9 * px;
						heatProgress = PartProgress.reload;
					}},
					new RegionPart("-breach") {{
						progress = PartProgress.reload;
						moveY = -12 * px;
						heatProgress = PartProgress.reload.add(0.5f);
					}},
					new RegionPart("-body")
				);
			}};
		}};
		longbow = new ItemTurret("longbow") {{
			requirements(Category.turret, with(
				Items.thorium, 400,
				Items.titanium, 275,
				Items.graphite, 250,
				Items.silicon, 200,
				Items.plastanium, 150
			));
			size = 3;
			scaledHealth = 350;

			reload = 3 * tick;
			recoil = 3f;
			ammoPerShot = 6;
			maxAmmo = 30;

			range = 65 * tilesize;
			shootCone = 1f;
			shake = 3f;
			rotateSpeed = 2.5f;

			shootSound = UAWSfx.cannonShoot2;
			soundPitchMin = 2.2f;
			soundPitchMax = 2.8f;

			ammoUseEffect = Fx.casing4;

			unitSort = UAWUnitSorts.mostHitPoints;

			ammo(
				Items.surgeAlloy, new TrailBulletType(18f, 400) {{
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
						UAWFxD.railShoot(width * 3f, backColor),
						Fx.shootBigColor
					);
					hitEffect = Fx.hitBulletColor;
					trailColor = hitColor = backColor;
					smokeEffect = Fx.shootBigSmoke;
					pierceCap = 3;
				}},
				UAWItems.compositeAlloy, new TrailBulletType(22f, 365) {{
					hitSize = 8f;
					height = 28f;
					width = 12f;
					despawnHit = true;
					pierceArmor = true;
					frontColor = UAWPal.compAlloyFront;
					backColor = UAWPal.compAlloyMid;
					trailEffect = new MultiEffect(
						Fx.disperseTrail,
						Fx.disperseTrail,
						Fx.disperseTrail
					);
					trailInterval = 0.05f;
					trailChance = 0.8f;
					shootEffect = new MultiEffect(
						UAWFxD.railShoot(width * 3, backColor),
						Fx.shootBigColor
					);
					hitEffect = Fx.hitBulletColor;
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
				UAWItems.compositeAlloy, 800,
				Items.silicon, 1200
			));
			size = 4;
			scaledHealth = 550;

			reload = 12 * tick;
			recoil = 12;
			recoilTime = recoil * 2;
			ammoPerShot = 50;
			maxAmmo = 150;

			range = 100 * tilesize;
			shake = 28f;
			rotateSpeed = 0.625f;
			minWarmup = 0.95f;
			shootWarmupSpeed = 0.015f;

			shootSound = UAWSfx.cannonShootBig1;
			soundPitchMin = 2.8f;
			soundPitchMax = 3.6f;

			ammoUseEffect = UAWFxS.casing7;

			unitSort = UAWUnitSorts.mostHitPoints;
			ammo(
				UAWItems.compositeAlloy, new RailBulletType() {{
					damage = 10000;
					shootEffect = new MultiEffect(
						UAWFxD.railShoot(64, Pal.bulletYellowBack),
						UAWFxD.smokeCloud(1.5f * tick, Layer.effect, Color.darkGray),
						Fx.blastExplosion
					);
					hitEffect = new MultiEffect(
						UAWFxD.railHit(18, Pal.bulletYellow),
						Fx.blastExplosion,
						Fx.flakExplosionBig
					);
					smokeEffect = new MultiEffect(
						Fx.smokeCloud,
						Fx.blastsmoke
					);
					length = range;
					pointEffect = new MultiEffect(
						UAWFxD.railTrail(18, Pal.bulletYellowBack),
						Fx.fireSmoke
					);
					trailEffect = UAWFxD.railTrail(15, Pal.bulletYellowBack);
					pierceDamageFactor = 0.8f;
					pointEffectSpace = 30f;
					buildingDamageMultiplier = 0.1f;
					hitShake = 25f;
					status = UAWStatusEffects.concussion;
					knockback = 24f;

					pierceArmor = true;
					pierce = true;
				}}
			);
			consumePowerCond(15f, TurretBuild::isActive);

			drawer = new DrawTurret(modTurretBase) {{
				parts.addAll(
					new RegionPart("-front") {{
						heatProgress = PartProgress.reload;
						progress = PartProgress.reload.add(-0.5f);
						mirror = true;
						moveX = 10 * px;
						moveY = -10f * px;
					}},
					new RegionPart("-side") {{
						progress = PartProgress.warmup.blend(PartProgress.reload, 0.3f);
						mirror = true;
						moveX = 4 * px;
						moveY = -8f * px;
						moveRot = -25f;
					}},
					new RegionPart("-body")
				);
			}};
		}};

		zounderkite = new UAWItemTurret("zounderkite") {{
			requirements(Category.turret, with(
				Items.lead, 300,
				Items.titanium, 280,
				Items.graphite, 325,
				Items.silicon, 250,
				Items.plastanium, 125
			));
			ammo(
				Items.graphite, canisterBasic,
				UAWItems.cryogel, canisterCryo,
				Items.pyratite, canisterIncend,
				Items.surgeAlloy, canisterEMP
			);
			size = 3;
			squareSprite = false;
			range = 45 * tilesize;
			reload = 6 * tick;
			shootSound = UAWSfx.launcherShoot1;
			soundPitchMin = 1.5f;
			soundPitchMax = 2f;
			ammoUseEffect = UAWFxS.casingCanister;
			ammoPerShot = 15;
			canOverdrive = false;
			limitRange();
		}};

		skyhammer = new UAWItemTurret("skyhammer") {{
			requirements(Category.turret, with(
				Items.copper, 1000,
				Items.lead, 650,
				Items.graphite, 500,
				Items.plastanium, 325,
				Items.silicon, 325,
				UAWItems.compositeAlloy, 250
			));
			size = 4;
			health = 100 * size * size;
			targetAir = false;
			inaccuracy = 4f;
			rotateSpeed = 1f;
			unitSort = (unit, x, y) -> unit.hitSize;
			reload = 10 * tick;
			ammoEjectBack = 5f;
			ammoUseEffect = UAWFxS.casing7;
			ammoPerShot = 20;
			itemCapacity = 60;
			velocityRnd = 0.2f;
			recoilTime = 0.02f;
			recoil = 6f;
			shake = 48f;
			range = 65 * tilesize;
			minRange = range / 4.5f;
			cooldownTime = 0.002f;

			shootSound = UAWSfx.cannonShootBig2;
			soundPitchMin = 1.5f;
			soundPitchMax = 1.8f;
			ammo(
				Items.pyratite, artilleryLargeAftershockIncendiary,
				UAWItems.cryogel, artilleryLargeAftershockCryo,
				Items.plastanium, artilleryLargeFrag,
				Items.thorium, artilleryLargeAftershockBasic
			);
		}};

		buckshot = new UAWItemTurret("buckshot") {{
			requirements(Category.turret, with(
				Items.copper, 120,
				Items.lead, 200,
				Items.graphite, 100
			));
			size = 2;
			health = 150 * size;
			squareSprite = true;
			reload = 1 * tick;
			shake = 2f;
			recoilTime = 0.05f;
			shootCone = 0.5f;
			ammoUseEffect = Fx.casing3;
			shootSound = Sounds.artillery;
			inaccuracy = 5f;
			rotateSpeed = 4f;
			maxAmmo = 48;
			ammoPerShot = 12;
			range = 100f;
			targetAir = false;
			shoot = new ShootSpread() {{
				spread = 2.8f;
				shots = 10;
				velocityRnd = 0.3f;
			}};
			ammo(
				Items.lead, buckshotSmallBasic,
				Items.graphite, buckshotSmallDense,
				Items.pyratite, buckshotSmallIncendiary,
				UAWItems.cryogel, buckshotSmallCryo
			);
			limitRange();
		}};
		tempest = new UAWItemTurret("tempest") {{
			requirements(Category.turret, with(
				Items.titanium, 200,
				Items.graphite, 150,
				Items.metaglass, 100,
				Items.silicon, 100
			));
			size = 3;
			health = 120 * size * size;
			reload = 8f;
			shake = 0.8f;
			recoilTime = 0.08f;
			range = 28 * tilesize;
			shootCone = 2.3f;
			ammoUseEffect = Fx.casing3;
			shootSound = UAWSfx.gunShoot4;
			inaccuracy = 6f;
			rotateSpeed = 4f;
			maxAmmo = 60;
			ammoPerShot = 5;
			shoot = new ShootMulti(
				new ShootSpread() {{
					spread = 2.6f;
					shots = 4;
					velocityRnd = 0.2f;
				}},
				new ShootBarrel() {{
					barrels = new float[]{
						0f, 1f, 0f,
						3f, 0f, 0f,
						-3f, 0f, 0f,
					};
				}}
			);
			ammo(
				Items.graphite, buckshotMediumBasic,
				Items.pyratite, buckshotMediumIncendiary,
				UAWItems.cryogel, buckshotMediumCryo,
				UAWItems.compositeAlloy, buckshotMediumPiercing,
				Items.metaglass, buckshotMediumFrag
			);
			limitRange();
		}};
		strikeforce = new UAWItemTurret("strikeforce") {{
			requirements(Category.turret, with(
				Items.titanium, 350,
				Items.graphite, 300,
				Items.metaglass, 200,
				Items.silicon, 200,
				Items.surgeAlloy, 100
			));
			size = 3;
			scaledHealth = 350;
			recoil = 4f;
			reload = 95f;
			shake = 8f;
			recoilTime = 0.08f;
			range = 175f;
			shootCone = 3f;
			ammoUseEffect = Fx.casing4;
			shootSound = Sounds.artillery;
			soundPitchMin = 1.8f;
			soundPitchMax = 2f;
			inaccuracy = 8f;
			rotateSpeed = 3f;
			maxAmmo = 120;
			ammoPerShot = 30;
			shoot = new ShootSpread() {{
				spread = 3.5f;
				shots = 20;
				velocityRnd = 0.3f;
			}};
			ammo(
				Items.pyratite, buckshotLargeIncendiary,
				UAWItems.cryogel, buckshotLargeCryo,
				Items.plastanium, buckshotLargeOil,
				Items.surgeAlloy, buckshotLargeSurge
			);
			limitRange(0.9f);
		}};

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

//		statusFieldProjector = new EffectFieldProjector("status-field-projector") {{
//			requirements(Category.effect, with(
//				Items.lead, 100,
//				Items.titanium, 75,
//				Items.metaglass, 75,
//				Items.silicon, 75
//			));
//			size = 2;
//			reload = 1.2f * 60;
//			range = 24 * tilesize;
//			ammoUseEffect = Fx.doorcloselarge;
//			ammo(
//				Liquids.cryofluid, new SplashBulletType(0, range) {{
//					smokeEffect = UAWFxS.cryoHit;
//					status = StatusEffects.freezing;
//					frontColor = UAWPal.cryoFront;
//					backColor = UAWPal.cryoBack;
//					statusDuration = reload * 1.5f;
//					splashAmount = 1;
//					shootEffect = UAWFxD.circleSplash(range, reload / 2, frontColor, backColor, backColor, 6);
//				}},
//				Liquids.slag, new SplashBulletType(0, range) {{
//					smokeEffect = Fx.fireHit;
//					status = StatusEffects.melting;
//					frontColor = Pal.lighterOrange;
//					backColor = Pal.lightOrange;
//					statusDuration = reload * 1.5f;
//					splashAmount = 1;
//					shootEffect = UAWFxD.circleSplash(range, reload / 2, frontColor, backColor, backColor, 6);
//				}},
//				Liquids.oil, new SplashBulletType(0, range) {{
//					frontColor = Pal.plastaniumFront;
//					backColor = Pal.plastaniumBack;
//					smokeEffect = UAWFxS.plastHit;
//					status = StatusEffects.tarred;
//					statusDuration = reload * 1.5f;
//					splashAmount = 1;
//					shootEffect = UAWFxD.circleSplash(range, reload / 2, frontColor, backColor, backColor, 6);
//				}},
//				UAWLiquids.surgeSolvent, new SplashBulletType(0, range) {{
//					smokeEffect = UAWFxS.surgeHit;
//					status = StatusEffects.electrified;
//					frontColor = Pal.missileYellow;
//					backColor = Pal.missileYellowBack;
//					statusDuration = reload * 1.5f;
//					splashAmount = 1;
//					shootEffect = UAWFxD.circleSplash(range, reload / 2, frontColor, backColor, backColor, 6);
//				}}
//			);
//			consumePower(2.4f);
//		}};
		rejuvinationProjector = new RejuvenationProjector("rejuvination-projector") {{
			requirements(Category.effect, with(
				Items.lead, 150,
				Items.titanium, 55,
				Items.silicon, 35,
				Items.metaglass, 35
			));
			size = 2;
			reload = 15f;
			range = 10 * tilesize;
			healPercent = 0.8f;
			health = 60 * size * size;
			boostMultiplier = 3f;
			boostDuration = 5 * tick;
			consumePower(1.6f);
			consumeLiquid(Liquids.oil, 0.5f);
		}};
		rejuvinationDome = new RejuvenationProjector("rejuvination-dome") {{
			requirements(Category.effect, with(
				Items.lead, 250,
				Items.titanium, 100,
				Items.silicon, 55,
				Items.metaglass, 55
			));
			size = 3;
			reload = 15f;
			range = 25 * tilesize;
			healPercent = 1.2f;
			health = 75 * size * size;
			boostMultiplier = 6f;
			consumePower(3f);
			consumeLiquid(Liquids.oil, 1f);
		}};
	}
}
