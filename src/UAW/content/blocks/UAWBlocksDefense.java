package UAW.content.blocks;

import UAW.content.*;
import UAW.entities.bullet.ModdedVanillaBullet.UAWRailBulletType;
import UAW.entities.bullet.SplashBulletType;
import UAW.graphics.*;
import UAW.world.blocks.defense.*;
import UAW.world.blocks.defense.turrets.*;
import UAW.world.blocks.defense.walls.ShieldWall;
import arc.graphics.Color;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.entities.UnitSorts;
import mindustry.entities.bullet.PointBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.type.Category;
import mindustry.world.Block;

import static UAW.Vars.tick;
import static UAW.content.UAWBullets.*;
import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;

/** Contains defense & support structures, such as Walls, Turrets, and booster */
public class UAWBlocksDefense implements ContentList {
	public static Block placeholder,
	// Gatling
	quadra, spitfire, equalizer,
	// Sniper/Railgun
	solo, longsword, deadeye,
	// Shotcannon
	buckshot, tempest, strikeforce,
	// Artillery
	ashlock, zounderkite, skyhammer,
	// Energy
	heavylight, silence, trailblazer, sundouser,

	// Defense
	shieldWall, statusFieldProjector, rejuvinationProjector, rejuvinationDome;

	@Override
	public void load() {
		quadra = new DynamicReloadTurret("quadra") {{
			requirements(Category.turret, with(
				Items.copper, 115,
				Items.lead, 120,
				Items.graphite, 80
			));
			health = 160 * size;
			size = 2;
			squareSprite = true;
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

			maxSpeedUpScl = 8f;
			speedupPerShot = 0.15f;
			ammo(
				Items.copper, smallCopper,
				Items.graphite, smallDense,
				Items.pyratite, smallIncendiary,
				UAWItems.cryogel, smallCryo,
				Items.titanium, smallPiercing
			);
			limitRange();
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
			range = 35 * tilesize;
			rotateSpeed = 7f;
			inaccuracy = 7.5f;
			recoilAmount = 3f;
			restitution = 0.05f;
			shootSound = Sounds.shootBig;
			ammoUseEffect = Fx.casing2Double;
			xRand = 3;

			maxSpeedUpScl = 16f;
			speedupPerShot = 0.1f;
			slowDownReloadTime = 90f;
			ammo(
				Items.graphite, mediumStandard,
				UAWItems.titaniumCarbide, mediumPiercing,
				Items.surgeAlloy, mediumSurge,
				Items.pyratite, mediumIncendiary,
				UAWItems.cryogel, mediumCryo
			);
			limitRange(2 * tilesize);
		}};

		solo = new UAWItemTurret("solo") {{
			requirements(Category.turret, with(
				Items.copper, 150,
				Items.graphite, 100,
				Items.titanium, 50
			));
			health = 160 * size;
			size = 2;
			squareSprite = true;
			spread = 0f;
			reloadTime = 0.8f * tick;
			shootShake = 3f;
			restitution = 0.05f;
			range = 30 * tilesize;
			ammoUseEffect = Fx.casing3;
			shootSound = Sounds.shootBig;
			inaccuracy = 1.5f;
			rotateSpeed = 5f;
			unitSort = (u, x, y) -> -u.health;
			maxAmmo = 20;
			ammoPerShot = 2;
			ammo(
				Items.copper, heavyCopper,
				Items.graphite, heavyDense,
				Items.silicon, heavyHoming,
				Items.thorium, heavyThorium,
				Items.titanium, heavyPiercing
			);
			limitRange();
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
			reloadTime = 3 * tick;
			ammoUseEffect = Fx.casing4;
			recoilAmount = 4f;
			restitution = 0.01f;
			shootShake = 3f;

			shootCone = 1f;
			shootSound = UAWSfx.gunShoot1;
			minShootPitch = 1.2f;
			maxShootPitch = 1.5f;
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
				UAWItems.titaniumCarbide, new UAWRailBulletType() {{
					damage = 350f;
					length = range;
					shootEffect = new MultiEffect(UAWFxD.railShoot(36, Pal.orangeSpark), Fx.blockExplosionSmoke);
					hitEffect = new MultiEffect(Fx.railHit, Fx.massiveExplosion);
					pierceEffect = Fx.railHit;
					updateEffect = Fx.railTrail;
					smokeEffect = Fx.smokeCloud;
					pierceCap = 4;
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
			reloadTime = 15 * tick;
			ammoUseEffect = UAWFxS.casing7;
			recoilAmount = 8f;
			restitution = 0.005f;
			shootShake = 28f;
			shootCone = 1f;
			shootSound = UAWSfx.cannonShootBig1;
			minShootPitch = 1.5f;
			maxShootPitch = 1.8f;
			unitSort = UnitSorts.strongest;
			cooldown = 0.005f;
			ammo(
				UAWItems.titaniumCarbide, new UAWRailBulletType() {{
					damage = 10000;
					shootEffect = new MultiEffect(
						UAWFxD.railShoot(64, Pal.bulletYellowBack),
						UAWFxD.smokeCloud(1.5f * tick, Layer.effect, Color.darkGray),
						Fx.blastExplosion,
						Fx.nuclearShockwave
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
					updateEffect = new MultiEffect(
						UAWFxD.railTrail(18, Pal.bulletYellowBack),
						Fx.fireSmoke
					);
					trailEffect = UAWFxD.railTrail(15, Pal.bulletYellowBack);
					pierceDamageFactor = 0.8f;
					updateEffectSeg = 30f;
					buildingDamageMultiplier = 0.1f;
					hitShake = 25f;
					ammoMultiplier = 1f;
					status = UAWStatusEffects.concussion;
					knockback = 24f;
				}}
			);
			consumes.powerCond(15f, TurretBuild::isActive);
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
			reloadTime = 6 * tick;
			shootSound = UAWSfx.launcherShoot1;
			minShootPitch = 1.5f;
			maxShootPitch = 2f;
			ammoUseEffect = UAWFxS.casingCanister;
			ammoPerShot = 15;
			acceptCoolant = false;
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
			reloadTime = 10 * tick;
			ammoEjectBack = 5f;
			ammoUseEffect = UAWFxS.casing7;
			ammoPerShot = 20;
			itemCapacity = 60;
			velocityInaccuracy = 0.2f;
			restitution = 0.02f;
			recoilAmount = 6f;
			shootShake = 48f;
			range = 65 * tilesize;
			minRange = range / 4.5f;
			cooldown = 0.002f;

			shootSound = UAWSfx.cannonShootBig2;
			minShootPitch = 1.5f;
			maxShootPitch = 1.8f;
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
			spread = 2.8f;
			shots = 10;
			reloadTime = 1 * tick;
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
			spread = 2.6f;
			shots = 4;
			xRand = 3;
			reloadTime = 8f;
			shootShake = 0.8f;
			restitution = 0.08f;
			range = 28 * tilesize;
			shootCone = 2.3f;
			velocityInaccuracy = 0.2f;
			ammoUseEffect = Fx.casing3;
			shootSound = UAWSfx.gunShoot4;
			inaccuracy = 6f;
			rotateSpeed = 4f;
			maxAmmo = 60;
			ammoPerShot = 5;
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
			health = 250 * size * size;
			spread = 3.5f;
			shots = 20;
			recoilAmount = 4f;
			reloadTime = 95f;
			shootShake = 8f;
			restitution = 0.08f;
			range = 175f;
			shootCone = 3f;
			velocityInaccuracy = 0.3f;
			ammoUseEffect = Fx.casing4;
			shootSound = Sounds.artillery;
			minShootPitch = 1.8f;
			maxShootPitch = 2f;
			inaccuracy = 8f;
			rotateSpeed = 3f;
			maxAmmo = 120;
			ammoPerShot = 30;
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
					smokeEffect = UAWFxS.cryoHit;
					status = StatusEffects.freezing;
					frontColor = UAWPal.cryoFront;
					backColor = UAWPal.cryoBack;
					statusDuration = reloadTime * 1.5f;
					splashAmount = 1;
					shootEffect = UAWFxD.circleSplash(range, reloadTime / 2, frontColor, backColor, backColor, 6);
				}},
				Liquids.slag, new SplashBulletType(0, range) {{
					smokeEffect = Fx.fireHit;
					status = StatusEffects.melting;
					frontColor = Pal.lighterOrange;
					backColor = Pal.lightOrange;
					statusDuration = reloadTime * 1.5f;
					splashAmount = 1;
					shootEffect = UAWFxD.circleSplash(range, reloadTime / 2, frontColor, backColor, backColor, 6);
				}},
				Liquids.oil, new SplashBulletType(0, range) {{
					frontColor = Pal.plastaniumFront;
					backColor = Pal.plastaniumBack;
					smokeEffect = UAWFxS.plastHit;
					status = StatusEffects.tarred;
					statusDuration = reloadTime * 1.5f;
					splashAmount = 1;
					shootEffect = UAWFxD.circleSplash(range, reloadTime / 2, frontColor, backColor, backColor, 6);
				}},
				UAWLiquids.surgeSolvent, new SplashBulletType(0, range) {{
					smokeEffect = UAWFxS.surgeHit;
					status = StatusEffects.electrified;
					frontColor = Pal.missileYellow;
					backColor = Pal.missileYellowBack;
					statusDuration = reloadTime * 1.5f;
					splashAmount = 1;
					shootEffect = UAWFxD.circleSplash(range, reloadTime / 2, frontColor, backColor, backColor, 6);
				}}
			);
			consumes.power(2.4f);
		}};
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
			consumes.power(1.6f);
			consumes.liquid(Liquids.oil, 0.5f);
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
			consumes.power(3f);
			consumes.liquid(Liquids.oil, 1f);
		}};
	}
}
