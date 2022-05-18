package UAW.content.blocks;

import UAW.content.*;
import UAW.entities.bullet.ModdedVanillaBullet.*;
import UAW.graphics.*;
import UAW.world.blocks.defense.RejuvenationProjector;
import UAW.world.blocks.defense.turrets.UAWItemTurret;
import UAW.world.blocks.defense.walls.ShieldWall;
import arc.graphics.Color;
import mindustry.content.*;
import mindustry.entities.UnitSorts;
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
	ashlock, longsword, deadeye,
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

			shoot = new ShootAlternate() {{
				barrels = 2;
				shots = 2;
				barrelOffset = 5;
				spread = 4f;
			}};

			scaledHealth = 160;
			size = 2;
			squareSprite = false;
			reload = 6f;
			recoil = 0.25f;
			recoilTime = reload * 3;
			range = 20 * tilesize;
			shootCone = 15f;
			ammoUseEffect = Fx.casing2Double;
			inaccuracy = 7.5f;
			rotateSpeed = 10f;
			maxAmmo = 30;

			ammo(
				Items.copper, smallCopper,
				Items.graphite, smallDense,
				Items.pyratite, smallIncendiary,
				UAWItems.cryogel, smallCryo,
				Items.titanium, smallPiercing
			);
			limitRange();

			drawer = new DrawTurret(modTurretBase) {{
				parts.add(new RegionPart("-barrel") {{
					progress = PartProgress.reload;
					mirror = true;
					moveY = -1.5f;
					heatProgress = PartProgress.reload.add(0.25f).min(PartProgress.warmup);

				}});
				parts.add(new RegionPart("-body"));
				parts.add(new RegionPart("-side") {{
					progress = PartProgress.warmup;
					mirror = true;
					moveX = 1.25f;
					moveRot = -10;
				}});
				parts.add(new RegionPart("-back") {{
					progress = PartProgress.reload;
					moveY = -1.25f;
				}});
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
			squareSprite = false;
			scaledHealth = 250;
			maxAmmo = 200;
			reload = 2.5f;
			range = 35 * tilesize;
			rotateSpeed = 7f;
			inaccuracy = 7.5f;
			recoil = 1f;
			recoilTime = 60f;
			shootSound = Sounds.shootBig;
			ammoUseEffect = Fx.casing2Double;
			minWarmup = 0.95f;
			shootWarmupSpeed = 0.5f;

			shoot = new ShootBarrel() {{
				barrels = new float[]{
					0f, 2f, 0f,
					3f, 1f, 0f,
					-3f, 1f, 0f,
				};
			}};

			ammo(
				Items.graphite, mediumStandard,
				UAWItems.titaniumCarbide, mediumPiercing,
				Items.surgeAlloy, mediumSurge,
				Items.pyratite, mediumIncendiary,
				UAWItems.cryogel, mediumCryo
			);
			limitRange(2 * tilesize);

			drawer = new DrawTurret(modTurretBase) {{
				parts.add(new RegionPart("-barrel") {{
					progress = PartProgress.reload.add(0.5f);
					moveY = -1.5f;
//					heatProgress = PartProgress.reload.add(0.25f).min(PartProgress.warmup);
				}});
				parts.add(new RegionPart("-side") {{
					progress = PartProgress.warmup;
					mirror = true;
					moveX = 1.15f;
					moveY = -4f;
					moveRot = 8f;
				}});
				parts.add(new RegionPart("-back") {{
					progress = PartProgress.warmup;
					moveY = -2.5f;
				}});
				parts.add(new RegionPart("-body"));
			}};

		}};

		ashlock = new ItemTurret("ashlock") {{
			requirements(Category.turret, with(
				Items.copper, 150,
				Items.graphite, 100,
				Items.titanium, 50
			));
			scaledHealth = 160;
			size = 2;
			squareSprite = false;
			inaccuracy = 0f;
			reload = 2.25f * tick;
			recoil = 3f;
			range = 30 * tilesize;
			ammoUseEffect = Fx.casing3;
			shootSound = Sounds.shootBig;
			soundPitchMin = 1.5f;
			soundPitchMax = 2f;
			rotateSpeed = 5f;
			shake = 3.5f;
			unitSort = UnitSorts.strongest;
			maxAmmo = 21;
			ammoPerShot = 3;
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
					frontColor = UAWPal.titaniumFront;
					backColor = UAWPal.titaniumBack;
					trailEffect = Fx.disperseTrail;
					trailChance = 0.8f;
					shootEffect = Fx.shootBigColor;
					hitEffect = Fx.hitBulletColor;
					trailColor = hitColor = backColor;
					despawnHit = true;
					pierceArmor = true;
					smokeEffect = Fx.shootBigSmoke;
					ammoMultiplier = 2;
					pierce = true;
				}}
			);
			limitRange();

			drawer = new DrawTurret("armored-") {{
				parts.add(new RegionPart("-barrel") {{
					progress = PartProgress.reload;
					moveY = -9 * px;
				}});
				parts.add(new RegionPart("-breach") {{
					progress = PartProgress.reload;
					moveY = -12 * px;
				}});
				parts.add(new RegionPart("-body"));

			}};
		}};
		longsword = new UAWItemTurret("longsword") {{
			float brange = range = 65 * tilesize;
			requirements(Category.turret, with(
				Items.thorium, 400,
				Items.titanium, 275,
				Items.graphite, 250,
				Items.silicon, 200,
				Items.plastanium, 150
			));
			size = 3;
			health = 150 * size * size;
			maxAmmo = 30;
			ammoPerShot = 6;
			rotateSpeed = 2.5f;
			reload = 3 * tick;
			ammoUseEffect = Fx.casing4;
			recoil = 4f;
			recoilTime = 0.01f;
			shake = 3f;

			shootCone = 1f;
			shootSound = UAWSfx.gunShoot1;
			soundPitchMin = 1.2f;
			soundPitchMax = 1.5f;
			unitSort = (u, x, y) -> -u.health;
			ammo(
				Items.surgeAlloy, new PointBulletType() {{
					damage = 300;
					speed = brange;
					splashDamage = 150;
					splashDamageRadius = 3 * tilesize;
					shootEffect = new MultiEffect(UAWFxD.railShoot(32, Pal.orangeSpark), Fx.blockExplosionSmoke);
					smokeEffect = Fx.smokeCloud;
					trailEffect = UAWFxD.railTrail(12, Pal.orangeSpark);
					hitEffect = despawnEffect = new MultiEffect(UAWFxD.crossBlast(splashDamageRadius, Pal.orangeSpark), Fx.smokeCloud);
					trailSpacing = 20f;
					buildingDamageMultiplier = 0.5f;
					hitShake = 6f;
					ammoMultiplier = 1f;
					status = StatusEffects.electrified;
				}},
				UAWItems.titaniumCarbide, new RailBulletType() {{
					damage = 350f;
					length = range;
					shootEffect = new MultiEffect(UAWFxD.railShoot(36, Pal.orangeSpark), Fx.blockExplosionSmoke);
					hitEffect = new MultiEffect(Fx.railHit, Fx.massiveExplosion);
					pierceEffect = Fx.railHit;
					pointEffect = Fx.railTrail;
					smokeEffect = Fx.smokeCloud;
					pierceCap = 4;
					pierceArmor = true;
					buildingDamageMultiplier = 0.5f;
					ammoMultiplier = 1f;
				}}
			);
		}};
		deadeye = new UAWItemTurret("deadeye") {{
			requirements(Category.turret, with(
				Items.titanium, 1000,
				Items.lead, 1500,
				Items.thorium, 500,
				Items.metaglass, 1200,
				Items.surgeAlloy, 900,
				UAWItems.titaniumCarbide, 800,
				Items.silicon, 1200
			));
			size = 4;
			health = 225 * size * size;
			range = 100 * tilesize;
			maxAmmo = 150;
			ammoPerShot = 50;
			rotateSpeed = 0.625f;
			reload = 15 * tick;
			ammoUseEffect = UAWFxS.casing7;
			recoil = 8f;
			recoilTime = 0.005f;
			shake = 28f;
			shootCone = 1f;
			shootSound = UAWSfx.cannonShootBig1;
			soundPitchMin = 1.5f;
			soundPitchMax = 1.8f;
			unitSort = UnitSorts.strongest;
			cooldownTime = 0.005f;
			ammo(
				UAWItems.titaniumCarbide, new UAWRailBulletType() {{
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
					ammoMultiplier = 1f;
					status = UAWStatusEffects.concussion;
					knockback = 24f;
				}}
			);
			consumePowerCond(15f, TurretBuild::isActive);
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
				UAWItems.titaniumCarbide, 250
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
				Items.pyratite, artilleryLargeAftershockIncend,
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
				Items.lead, buckshotLead,
				Items.graphite, buckshotGraphite,
				Items.pyratite, buckshotIncend,
				UAWItems.cryogel, buckshotCryo
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
				Items.graphite, buckshotMedium,
				Items.pyratite, buckshotMediumIncend,
				UAWItems.cryogel, buckshotMediumCryo,
				UAWItems.titaniumCarbide, buckshotMediumPiercing,
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
				Items.pyratite, buckshotLargeIncend,
				UAWItems.cryogel, buckshotLargeCryo,
				Items.plastanium, buckshotLargePlast,
				Items.surgeAlloy, buckshotLargeEMP
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
