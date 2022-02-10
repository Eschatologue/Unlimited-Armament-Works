package UAW.content;

import UAW.entities.bullet.*;
import UAW.graphics.*;
import UAW.world.blocks.defense.*;
import UAW.world.blocks.defense.turrets.*;
import UAW.world.blocks.defense.walls.ShieldWall;
import UAW.world.blocks.liquid.*;
import UAW.world.blocks.power.WarmUpGenerator;
import UAW.world.blocks.production.*;
import arc.struct.Seq;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.entities.UnitSorts;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.units.*;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;

import static UAW.content.UAWBullets.*;
import static mindustry.Vars.tilesize;
import static mindustry.content.Bullets.artilleryPlastic;
import static mindustry.type.ItemStack.with;

public class UAWBlocks implements ContentList {
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
	heavylight, trailblazer, terravolt,
	// Liquids
	pressurizedConduit, pressurizedLiquidRouter, pressurizedLiquidJunction, pressurizedLiquidBridge, rotodynamicPump,
	// Drills
	oilDerrick,
	// Crafters
	gelatinizer, carburizingFurnace, surgeMixer, petroleumSmelter, petrochemicalSeperator, plastaniumForge, anthraciteCrystallizer,
	// Power
	petroleumGenerator,
	// Defense
	shieldWall, statusFieldProjector, rejuvinationProjector, rejuvinationDome,
	// Units Factory
	UAWGroundFactory, UAWNavalFactory, UAWAirFactory,
	// Units Reconstructor
	exponentialPetroleumReconstructor, tetrativePetroleumReconstructor;

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

			maxReloadScl = 8f;
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

			maxReloadScl = 16f;
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
			reloadTime = 35f;
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
				UAWItems.titaniumCarbide, 175,
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
			shootSound = Sounds.artillery;
			unitSort = (u, x, y) -> -u.health;
			ammo(
				Items.surgeAlloy, new PointBulletType() {{
					damage = 300;
					speed = brange;
					splashDamage = 150;
					splashDamageRadius = 3 * 8;
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
			shootShake = 25f;
			shootCone = 1f;
			shootSound = UAWSfx.bigGunShoot1;
			unitSort = UnitSorts.strongest;
			cooldown = 1.5f;
			ammo(
				UAWItems.titaniumCarbide, new UAWRailBulletType() {{
					damage = 10000;
					shootEffect = new MultiEffect(
						UAWFxD.railShoot(64, Pal.bulletYellowBack),
						UAWFxD.effectCloud(Pal.bulletYellow),
						Fx.blastExplosion,
						Fx.nuclearShockwave
					);
					hitEffect = new MultiEffect(
						UAWFxD.railHit(16, Pal.bulletYellow),
						Fx.blastExplosion,
						Fx.flakExplosionBig
					);
					smokeEffect = new MultiEffect(
						Fx.smokeCloud,
						Fx.blastsmoke
					);
					length = range;
					updateEffect = new MultiEffect(
						UAWFxD.railTrail(15, Pal.bulletYellowBack),
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
			acceptCoolant = false;

			shootSound = UAWSfx.artilleryShootHuge;
			ammo(
				Items.pyratite, new ArtilleryBulletType(2f, 2500) {{
					height = 45;
					width = height / 2f;
					splashDamage = damage;
					splashDamageRadius = 14 * tilesize;
					lifetime = range / speed;
					frontColor = Pal.lightishOrange;
					backColor = Pal.lightOrange;
					shootEffect = new MultiEffect(
						UAWFxD.railShoot(height + (width * 1.5f), Pal.lightOrange),
						UAWFxD.effectCloud(frontColor),
						UAWFxD.shootMassiveSmoke(5, 30, backColor),
						Fx.nuclearShockwave
					);
					hitEffect = UAWFxD.dynamicExplosion2(splashDamageRadius, frontColor, backColor);
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
				UAWItems.cryogel, new ArtilleryBulletType(2f, 2500) {{
					height = 45;
					width = height / 2f;
					splashDamage = damage;
					splashDamageRadius = 14 * tilesize;
					lifetime = range / speed;
					frontColor = UAWPal.cryoFront;
					backColor = UAWPal.cryoBack;
					shootEffect = new MultiEffect(
						UAWFxD.railShoot(height + (width * 1.5f), UAWPal.cryoBackBloom),
						UAWFxD.effectCloud(frontColor),
						UAWFxD.shootMassiveSmoke(5, 30, frontColor),
						Fx.nuclearShockwave
					);
					hitEffect = UAWFxD.dynamicExplosion2(splashDamageRadius, frontColor, backColor);
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
					shootEffect = new MultiEffect(
						UAWFxD.railShoot(height + (width * 1.5f), UAWPal.plastBackBloom),
						UAWFxD.effectCloud(frontColor),
						UAWFxD.shootMassiveSmoke(5, 30, frontColor),
						Fx.nuclearShockwave
					);
					hitEffect = UAWFxD.dynamicExplosion2(splashDamageRadius, frontColor, backColor);
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
				Items.thorium, new ArtilleryBulletType(2f, 4250) {{
					height = 48;
					width = height / 2f;
					splashDamage = damage;
					splashDamageRadius = 14 * tilesize;
					lifetime = range / speed;
					shootEffect = new MultiEffect(
						UAWFxD.railShoot(height + width, UAWPal.surgeBackBloom),
						UAWFxD.effectCloud(frontColor),
						UAWFxD.shootMassiveSmoke(5, 30, frontColor),
						Fx.nuclearShockwave
					);
					hitEffect = UAWFxD.dynamicExplosion2(splashDamageRadius, frontColor, backColor);
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

		buckshot = new UAWItemTurret("buckshot") {{
			requirements(Category.turret, with(
				Items.copper, 120,
				Items.lead, 200,
				Items.graphite, 100
			));
			health = 160 * size;
			size = 2;
			squareSprite = true;
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
				Items.lead, buckshotLead,
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
			shootSound = UAWSfx.shotgunShoot1;
			inaccuracy = 6f;
			rotateSpeed = 4f;
			maxAmmo = 60;
			ammoPerShot = 6;
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
			health = 250 * size * size;
			size = 3;
			spread = 2.3f;
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

		pressurizedConduit = new PressurizedConduit("pressurized-conduit") {{
			requirements(Category.liquid, with(
				UAWItems.titaniumCarbide, 3,
				Items.metaglass, 2,
				Items.plastanium, 2
			));
			health = 500;
			baseExplosiveness = 8f;
		}};
		pressurizedLiquidRouter = new LiquidRouter("pressurized-liquid-router") {{
			requirements(Category.liquid, with(
				UAWItems.titaniumCarbide, 3,
				Items.plastanium, 2,
				Items.metaglass, 2
			));
			health = 500;
			baseExplosiveness = 8f;
			liquidCapacity = 60f;
			liquidPressure = 2f;
			placeableLiquid = true;
		}};
		pressurizedLiquidJunction = new LiquidJunction("pressurized-liquid-junction") {{
			requirements(Category.liquid, with(
				UAWItems.titaniumCarbide, 3,
				Items.plastanium, 2,
				Items.metaglass, 2
			));
			health = 500;
			liquidCapacity = 60f;
			liquidPressure = 1.5f;
			placeableLiquid = true;
			consumesPower = outputsPower = hasPower = true;
			consumes.powerBuffered(250);
			baseExplosiveness = 8f;
		}};
		pressurizedLiquidBridge = new LiquidBridge("pressurized-liquid-bridge") {{
			requirements(Category.liquid, with(
				UAWItems.titaniumCarbide, 3,
				Items.plastanium, 2,
				Items.metaglass, 2
			));
			health = 500;
			liquidCapacity = 60f;
			liquidPressure = 1.5f;
			range = 6;
			arrowPeriod = 0.9f;
			arrowTimeScl = 2.75f;
			baseExplosiveness = 8f;
		}};

		rotodynamicPump = new RotatingLiquidPump("rotodynamic-pump") {{
			requirements(Category.liquid, with(
				Items.lead, 150,
				Items.metaglass, 60,
				Items.silicon, 60,
				Items.plastanium, 60,
				UAWItems.titaniumCarbide, 40,
				Items.thorium, 70
			));
			size = 3;
			pumpAmount = 0.40f;
			liquidCapacity = 540f;
			rotateSpeed = -3f;

			consumes.item(UAWItems.cryogel, 1);
		}};
		oilDerrick = new Fracker("oil-derrick") {{
			requirements(Category.production, with(
				Items.titanium, 180,
				Items.plastanium, 125,
				Items.metaglass, 85,
				Items.silicon, 65,
				UAWItems.titaniumCarbide, 85,
				Items.surgeAlloy, 45
			));
			size = 4;
			result = Liquids.oil;
			updateEffect = new MultiEffect(
				Fx.pulverize,
				Fx.hitLancer
			);
			updateEffectChance = 0.05f;
			pumpAmount = 1.2f;
			liquidCapacity = 360f;
			attribute = Attribute.oil;
			baseEfficiency = 0.55f;
			itemUseTime = 120f;
			rotateSpeed = -2.5f;

			squareSprite = false;
			floating = true;

			consumes.liquid(Liquids.cryofluid, 0.25f);
			consumes.power(3.5f);
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
			consumes.liquid(Liquids.cryofluid, 0.25f);
			consumes.power(0.5f);
			outputItem = new ItemStack(
				UAWItems.cryogel, 1
			);
			craftTime = 45f;
			hasItems = true;
			hasLiquids = true;
			size = 2;
			drawer = new DrawLiquid();
			craftEffect = Fx.freezing;
			updateEffect = Fx.wet;
		}};
		carburizingFurnace = new AdvancedGenericCrafter("carburizing-furnace") {{
			requirements(Category.crafting, with(
				Items.titanium, 150,
				Items.thorium, 125,
				Items.metaglass, 95,
				Items.silicon, 95,
				Items.graphite, 95
			));
			consumes.items(
				new ItemStack(Items.titanium, 4),
				new ItemStack(Items.coal, 8)
			);
			consumes.liquid(Liquids.slag, 1.5f);
			outputItem = new ItemStack(
				UAWItems.titaniumCarbide, 1
			);
			consumes.power(5.5f);
			hasItems = true;
			hasLiquids = true;
			size = 3;
			itemCapacity = 30;
			craftTime = 3.5f * tick;
			drawer = new DrawSmelter();
			craftEffect = new MultiEffect(
				UAWFxD.burstSmelt(3.5f * tilesize, Pal.missileYellow, Pal.missileYellowBack),
				Fx.flakExplosionBig
			);
			updateEffect = new MultiEffect(Fx.melting, Fx.burning, Fx.fireSmoke);
			craftShake = 8;
		}};
		surgeMixer = new GenericCrafter("surge-mixer") {{
			requirements(Category.crafting, with(
				Items.thorium, 30,
				Items.lead, 45,
				Items.silicon, 35,
				Items.metaglass, 20
			));
			consumes.items(with(
				Items.copper, 1,
				Items.titanium, 1,
				Items.silicon, 1));
			consumes.liquid(Liquids.oil, 0.25f);
			consumes.power(2f);
			outputLiquid = new LiquidStack(UAWLiquids.surgeSolvent, 30f);
			size = 3;
			liquidCapacity = 120f;
			outputsLiquid = true;
			hasItems = true;
			hasLiquids = true;
			drawer = new DrawLiquid();
			craftTime = 2f * tick;
			updateEffect = Fx.shieldBreak;
		}};
		plastaniumForge = new AdvancedGenericCrafter("plastanium-forge") {{
			requirements(Category.crafting, with(
				Items.lead, 325,
				Items.silicon, 160,
				Items.graphite, 160,
				Items.titanium, 300,
				Items.plastanium, 250
			));
			size = 3;
			health = 500 * size;
			squareSprite = false;
			hasItems = true;
			itemCapacity = 48;
			liquidCapacity = 360f;
			craftTime = 5.5f * tick;
			outputItem = new ItemStack(Items.plastanium, 14);
			hasPower = hasLiquids = true;
			craftEffect = new MultiEffect(
				UAWFxD.burstSmelt(2.5f * tilesize, Pal.plastaniumFront, Pal.plastaniumBack),
				Fx.plasticExplosion
			);
			craftShake = 6f;
			updateEffect = new MultiEffect(Fx.plasticburn, Fx.burning, Fx.fireSmoke);
			drawer = new DrawSmelter();

			consumes.liquid(Liquids.oil, 2.5f);
			consumes.power(4f);
			consumes.items(
				new ItemStack(Items.titanium, 7),
				new ItemStack(UAWItems.anthracite, 2)
			);
		}};

		petroleumSmelter = new AdvancedGenericCrafter("petroleum-smelter") {{
			requirements(Category.crafting, with(
				Items.titanium, 125,
				Items.plastanium, 100,
				Items.thorium, 110,
				Items.metaglass, 85,
				Items.silicon, 85,
				Items.graphite, 95
			));
			size = 4;
			hasItems = true;
			hasLiquids = true;
			itemCapacity = 60;
			liquidCapacity = 360f;
			craftTime = 2.5f * tick;
			squareSprite = false;
			drawer = new DrawSmelter();
			craftEffect = new MultiEffect(
				UAWFxD.burstSmelt(4 * tilesize, Pal.plastaniumFront, Pal.plastaniumBack),
				Fx.flakExplosionBig
			);
			updateEffect = new MultiEffect(
				UAWFxS.plastHit,
				Fx.burning,
				Fx.fireSmoke
			);
			craftSoundVolume = 1.2f;
			craftShake = 15f;
			consumes.items(
				new ItemStack(Items.sand, 12),
				new ItemStack(Items.lead, 4),
				new ItemStack(UAWItems.anthracite, 3)
			);
			consumes.liquid(Liquids.oil, 1.25f);
			consumes.power(4.5f);
			outputItems = with(
				Items.silicon, 13,
				Items.metaglass, 13
			);
		}};
		petrochemicalSeperator = new EffectSeparator("petrochemical-seperator") {{
			requirements(Category.crafting, with(
				Items.lead, 120,
				Items.titanium, 100,
				Items.plastanium, 80,
				Items.silicon, 80
			));
			results = with(
				UAWItems.anthracite, 2,
				Items.coal, 2,
				Items.coal, 2
			);
			size = 3;
			craftTime = 2.5f * tick;
			itemCapacity = 30;
			squareSprite = false;
			liquidCapacity = 240f;
			updateEffect = new MultiEffect(Fx.fireHit, UAWFxS.plastHit, Fx.oily);

			consumes.power(2f);
			consumes.liquid(Liquids.oil, 0.5f);
			consumes.item(Items.sand, 2);
		}};
		anthraciteCrystallizer = new AttributeCrafter("anthracite-crystallizer") {{
			requirements(Category.crafting, with(
				Items.lead, 250,
				Items.titanium, 150,
				Items.plastanium, 120,
				Items.silicon, 105)
			);
			consumes.items(
				new ItemStack(Items.coal, 4),
				new ItemStack(Items.thorium, 1)
			);
			consumes.liquid(Liquids.oil, 1.5f);
			outputItems = with(
				UAWItems.anthracite, 4
			);
			size = 3;
			drawer = new DrawLiquid();
			hasItems = true;
			hasLiquids = true;
			itemCapacity = 30;
			craftTime = 2.5f * tick;
			squareSprite = false;
			craftEffect = new MultiEffect(Fx.melting, Fx.burning, Fx.fireHit);
			updateEffect = new MultiEffect(Fx.burning, Fx.fireSmoke, Fx.steam);
			attribute = Attribute.heat;
			boostScale = 0.11f;
			maxBoost = 3f;
			baseEfficiency = 0.5f;
		}};

		petroleumGenerator = new WarmUpGenerator("petroleum-generator") {{
			requirements(Category.power, with(
				Items.copper, 225,
				Items.titanium, 150,
				Items.plastanium, 100,
				Items.silicon, 155,
				Items.metaglass, 120
			));
			size = 4;
			health = 300 * size;
			hasLiquids = true;
			hasItems = false;
			liquidCapacity = 480f;
			ambientSound = Sounds.machine;
			ambientSoundVolume = 0.05f;

			powerProduction = 45f;
			consumes.liquid(Liquids.oil, 2.5f);
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
					shootEffect = UAWFxD.statusFieldApply(UAWPal.cryoFront, UAWPal.cryoBack, range);
					smokeEffect = UAWFxS.cryoHit;
					status = StatusEffects.freezing;
					frontColor = UAWPal.cryoFront;
					backColor = UAWPal.cryoBack;
					statusDuration = reloadTime * 1.5f;
					splashAmount = 1;
				}},
				Liquids.slag, new SplashBulletType(0, range) {{
					shootEffect = UAWFxD.statusFieldApply(Pal.lighterOrange, Pal.lightOrange, range);
					smokeEffect = Fx.fireHit;
					status = StatusEffects.melting;
					frontColor = Pal.lighterOrange;
					backColor = Pal.lightOrange;
					statusDuration = reloadTime * 1.5f;
					splashAmount = 1;
				}},
				Liquids.oil, new SplashBulletType(0, range) {{
					shootEffect = UAWFxD.statusFieldApply(Pal.plastaniumFront, UAWPal.plastDarker, range);
					smokeEffect = UAWFxS.plastHit;
					status = StatusEffects.tarred;
					statusDuration = reloadTime * 1.5f;
					splashAmount = 1;
				}},
				UAWLiquids.surgeSolvent, new SplashBulletType(0, range) {{
					shootEffect = UAWFxD.statusFieldApply(Pal.missileYellow, Pal.missileYellowBack, range);
					smokeEffect = UAWFxS.surgeHit;
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
			consumes.liquid(UAWLiquids.surgeSolvent, 0.5f);
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
			consumes.liquid(UAWLiquids.surgeSolvent, 1f);
		}};

		UAWGroundFactory = new UnitFactory("uaw-ground-factory") {{
			requirements(Category.units, with(
				Items.lead, 550,
				Items.silicon, 90,
				Items.metaglass, 90,
				Items.titanium, 250,
				Items.plastanium, 100
			));
			size = 5;
			consumes.power(3.5f);
			consumes.liquid(Liquids.oil, 1f);
			liquidCapacity = 120f;
			plans = Seq.with(
				new UnitPlan(UAWUnitTypes.gardlacz, 35f * tick, with(
					Items.silicon, 85,
					Items.titanium, 90,
					Items.lead, 150,
					Items.copper, 165
				))
			);
		}};
		UAWNavalFactory = new UnitFactory("uaw-naval-factory") {{
			requirements(Category.units, with(
				Items.lead, 550,
				Items.silicon, 60,
				Items.metaglass, 100,
				Items.titanium, 250,
				Items.plastanium, 100
			));
			floating = true;
			size = 5;
			consumes.power(3.5f);
			consumes.liquid(Liquids.oil, 1f);
			liquidCapacity = 120f;
			plans = Seq.with(
				new UnitPlan(UAWUnitTypes.clurit, 45f * tick, with(
					Items.silicon, 65,
					Items.metaglass, 60,
					Items.titanium, 100,
					Items.lead, 120
				)),
				new UnitPlan(UAWUnitTypes.hatsuharu, 55f * tick, with(
					Items.silicon, 55,
					Items.metaglass, 50,
					Items.titanium, 100,
					Items.blastCompound, 30,
					Items.lead, 120
				))
			);
		}};
		UAWAirFactory = new UnitFactory("uaw-air-factory") {{
			requirements(Category.units, with(
				Items.lead, 550,
				Items.silicon, 80,
				Items.metaglass, 80,
				Items.titanium, 250,
				Items.plastanium, 100
			));
			size = 5;
			consumes.power(3.5f);
			consumes.liquid(Liquids.oil, 1f);
			liquidCapacity = 120f;
			plans = Seq.with(
				new UnitPlan(UAWUnitTypes.aglovale, 35f * tick, with(
					Items.silicon, 100,
					Items.titanium, 125,
					Items.plastanium, 75,
					Items.lead, 150
				)),
				new UnitPlan(UAWUnitTypes.corsair, 30f * tick, with(
					Items.silicon, 85,
					Items.titanium, 90,
					Items.lead, 150,
					Items.plastanium, 75,
					Items.blastCompound, 35
				))
			);
		}};

		exponentialPetroleumReconstructor = new Reconstructor("exponential-petroleum-reconstructor") {{
			requirements(Category.units, with(
				Items.lead, 1000,
				Items.titanium, 2000,
				Items.thorium, 550,
				Items.plastanium, 500,
				UAWItems.titaniumCarbide, 550
			));

			size = 7;
			consumes.power(7f);
			consumes.items(with(
				Items.silicon, 425,
				Items.metaglass, 325,
				UAWItems.titaniumCarbide, 250,
				Items.plastanium, 225
			));
			consumes.liquid(Liquids.oil, 1.5f);

			constructTime = 80 * tick;
			liquidCapacity = 240f;
			placeableLiquid = true;

			upgrades.addAll(
				new UnitType[]{UAWUnitTypes.aglovale, UAWUnitTypes.bedivere},
				new UnitType[]{UAWUnitTypes.clurit, UAWUnitTypes.kujang},
				new UnitType[]{UAWUnitTypes.gardlacz, UAWUnitTypes.arkabuz},
				new UnitType[]{UAWUnitTypes.hatsuharu, UAWUnitTypes.shiratsuyu}
//				new UnitType[]{UAWUnitTypes.corsair, UAWUnitTypes.vindicator}
			);
		}};
		tetrativePetroleumReconstructor = new Reconstructor("tetrative-petroleum-reconstructor") {{
			requirements(Category.units, with(
				Items.lead, 3500,
				Items.titanium, 2500,
				Items.silicon, 1250,
				Items.metaglass, 500,
				Items.plastanium, 600,
				UAWItems.titaniumCarbide, 600,
				Items.surgeAlloy, 700
			));

			size = 9;
			consumes.power(20f);
			consumes.items(with(
				Items.silicon, 550,
				Items.metaglass, 450,
				Items.plastanium, 300,
				Items.surgeAlloy, 350,
				UAWItems.titaniumCarbide, 350
			));
			consumes.liquid(Liquids.oil, 3.5f);

			constructTime = 225 * tick;
			liquidCapacity = 480f;
			placeableLiquid = true;

			upgrades.addAll(
				new UnitType[]{UAWUnitTypes.bedivere, UAWUnitTypes.calogrenant},
				new UnitType[]{UAWUnitTypes.kujang, UAWUnitTypes.kerambit},
				new UnitType[]{UAWUnitTypes.arkabuz, UAWUnitTypes.armata}
//			new UnitType[]{UAWUnitTypes.shiratsuyu, UAWUnitTypes.kagero}
//			new UnitType[]{UAWUnitTypes.vindicator, UAWUnitTypes.superfortress}
			);
		}};

	}
}
