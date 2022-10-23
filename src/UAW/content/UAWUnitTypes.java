package UAW.content;

import UAW.ai.types.DynamicFlyingAI;
import UAW.audiovisual.*;
import UAW.entities.abilities.RazorRotorAbility;
import UAW.entities.bullet.*;
import UAW.entities.bullet.ModdedVanillaBullet.SplashArtilleryBulletType;
import UAW.entities.effects.*;
import UAW.entities.units.*;
import UAW.entities.units.entity.*;
import UAW.type.Rotor;
import UAW.type.weapon.*;
import arc.func.Prov;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Rect;
import arc.struct.*;
import arc.struct.ObjectMap.Entry;
import com.sun.istack.NotNull;
import mindustry.ai.types.FlyingAI;
import mindustry.content.*;
import mindustry.entities.abilities.MoveEffectAbility;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.type.weapons.PointDefenseWeapon;
import mindustry.world.blocks.payloads.PayloadSource;
import mindustry.world.meta.BlockFlag;

import static UAW.Vars.*;
import static UAW.content.UAWBullets.fragGlassFrag;
import static mindustry.Vars.tilesize;

@SuppressWarnings("unchecked")
public class UAWUnitTypes {
	public static UnitType placeholder,


	// Air - Helicopters
	crotchety,
		aglovale, bedivere, calogrenant, dagonet, esclabor,

	// Air - Carriers
	cantankerous, illustrious, indefatigable, indomitable,

	// Naval - Monitor
	arquebus, carronade, falconet, dardanelles,

	// Naval - Torpedo Boats
	megaera, alecto, tisiphone,

	// Tanks - MBT
	cavalier, centurion, caernarvon, challenger,

	// Tanks- Flamer
	cavalierF, centurionF, caernarvonF,

	// Tanks - SPG
	abbot, archer, arbalester, arbiter;

	// Steal from Progressed Material which stole from Endless Rusting which stole from Progressed Materials in the past which stole from BetaMindy
	private static final Entry<Class<? extends Entityc>, Prov<? extends Entityc>>[] types = new Entry[]{
		prov(CopterUnitEntity.class, CopterUnitEntity::new),
		prov(TankUnitEntity.class, TankUnitEntity::new),
	};

	private static final ObjectIntMap<Class<? extends Entityc>> idMap = new ObjectIntMap<>();

	/**
	 * Internal function to flatmap {@code Class -> Prov} into an {@link Entry}.
	 * @author GlennFolker
	 */
	private static <T extends Entityc> Entry<Class<T>, Prov<T>> prov(Class<T> type, Prov<T> prov) {
		Entry<Class<T>, Prov<T>> entry = new Entry<>();
		entry.key = type;
		entry.value = prov;
		return entry;
	}

	/**
	 * Setups all entity IDs and maps them into {@link EntityMapping}.
	 * <p>
	 * Put this inside load()
	 * </p>
	 * @author GlennFolker
	 */
	private static void setupID() {
		for (
			int i = 0,
			j = 0,
			len = EntityMapping.idMap.length;
			i < len;
			i++
		) {
			if (EntityMapping.idMap[i] == null) {
				idMap.put(types[j].key, i);
				EntityMapping.idMap[i] = types[j].value;
				if (++j >= types.length) break;
			}
		}
	}

	private static void setupPayloadSource() {
		registerPayloadSource(HelicopterUnitType.class);
		registerPayloadSource(TankUnitType.class);
	}

	/**
	 * Retrieves the class ID for a certain entity type.
	 * @author GlennFolker
	 */
	public static <T extends Entityc> int classID(Class<T> type) {
		return idMap.get(type, -1);
	}

	public static <T extends UnitType> void registerPayloadSource(@NotNull Class<T> clz) {
		var source = (PayloadSource) Blocks.payloadSource;
		source.config((Class<UnitType>) clz,
			(PayloadSource.PayloadSourceBuild build, UnitType type) -> {
				if (source.canProduce(type) && build.unit != type) {
					build.unit = type;
					build.block = null;
					build.payload = null;
					build.scl = 0f;
				}
			});
	}

	public static void load() {
		setupID();
		setupPayloadSource();

		// Serpulo

		// region Air - Helicopters
		aglovale = new HelicopterUnitType("aglovale") {{
			float unitRange = 28 * tilesize;
			health = 450;
			hitSize = 18;

			speed = 2.5f;
			accel = 0.04f;
			drag = 0.016f;
			rotateSpeed = 5.5f;

			ammoType = new ItemAmmoType(Items.graphite);

			circleTarget = true;
			lowAltitude = true;
			faceTarget = flying = true;
			range = unitRange;

			fallSpeed = 0.0015f;
			spinningFallSpeed = 4;
			fallSmokeY = -10f;
			engineSize = 0;

			targetFlags = new BlockFlag[]{BlockFlag.turret, BlockFlag.extinguisher, BlockFlag.repair, null};

			constructor = CopterUnitEntity::new;
			aiController = DynamicFlyingAI::new;

			rotors.add(
				new Rotor(name + "-blade") {{
					x = y = 0;
					rotorSpeed = -30f;
					bladeCount = 3;
				}}
			);
			weapons.add(new Weapon(missile_small_red_2) {{
				rotate = false;
				mirror = true;
				shootCone = 90;
				x = 5f;
				y = -3f;
				inaccuracy = 4;
				reload = 25;
				shoot.shots = 2;
				shoot.shotDelay = 5f;
				shootSound = Sounds.missile;
				bullet = new MissileBulletType(6f, 50) {{
					width = 6f;
					height = 12f;
					shrinkY = 0f;
					drag = -0.003f;
					homingRange = 60f;
					keepVelocity = false;
					despawnHit = true;
					splashDamageRadius = 25f;
					splashDamage = 16f;
					lifetime = unitRange / speed;
					backColor = Pal.bulletYellowBack;
					frontColor = Pal.bulletYellow;
					shootEffect = Fx.shootSmallSmoke;
					hitEffect = Fx.blastExplosion;
					trailInterval = 0.4f;
					shootCone = 80;
					ammoMultiplier = 4f;
				}};
			}});
			weapons.add(new Weapon(machineGun_small_red) {{
				layerOffset = -0.01f;
				rotate = false;
				mirror = true;
				shootCone = 90;
				inaccuracy = 3f;
				x = 8f;
				y = 4.5f;
				reload = 4f;
				shootSound = Sfx.gunShoot3;
				ejectEffect = Fx.casing1;
				bullet = new TrailBulletType(6f, 15) {{
					height = 8f;
					width = 5f;
					pierce = true;
					pierceCap = 2;
					buildingDamageMultiplier = 0.4f;
					maxRange = unitRange / 0.8f;
					homingRange = 60f;
					ammoMultiplier = 8f;
				}};
			}});
		}};
		bedivere = new HelicopterUnitType("bedivere") {{
			float unitRange = 32 * tilesize;
			health = 3000;
			hitSize = 30;

			speed = 2.5f;
			accel = 0.08f;
			drag = 0.03f;
			rotateSpeed = 4.5f;

			ammoType = new ItemAmmoType(Items.graphite);

			circleTarget = true;
			lowAltitude = true;
			faceTarget = flying = true;
			range = unitRange;

			fallSpeed = 0.006f;
			spinningFallSpeed = 5f;
			fallSmokeY = -15f;
			engineSize = 0;

			targetFlags = new BlockFlag[]{BlockFlag.turret, BlockFlag.extinguisher, BlockFlag.repair, null};

			aiController = DynamicFlyingAI::new;

			rotors.add(
				new Rotor(name + "-blade") {{
					x = 0;
					y = 4;
					rotorSpeed = -35f;
					rotorBlurSpeedMultiplier = 0.15f;
					bladeCount = 4;
				}}
			);

			weapons.add(new Weapon(machineGun_small_red) {{
				layerOffset = -0.005f;
				rotate = top = false;
				shootCone = 30;
				inaccuracy = 5f;
				alternate = mirror = true;
				x = 7f;
				y = 11f;
				reload = 4;
				recoil = 0f;
				shootSound = Sfx.gunShoot3;
				ejectEffect = Fx.casing1;
				bullet = new TrailBulletType(10f, 24) {{
					trailLengthScale = 1;
					height = 10f;
					width = 5f;
					pierce = true;
					pierceCap = 2;
					buildingDamageMultiplier = 0.4f;
					maxRange = range - 8;
					homingPower = 0.02f;
					lifetime = (unitRange / speed) * 0.8f;
					trailColor = backColor;
					hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.shootSmallSmoke);
					ammoMultiplier = 8f;
				}};
			}});
			weapons.add(new Weapon(artillery_medium_red) {{
				rotate = false;
				alternate = mirror = true;
				top = true;
				shootCone = 15;
				x = 7f;
				y = 0f;
				reload = 60f;
				recoil = 2f;
				shake = 2f;
				ejectEffect = Fx.casing3;
				shootSound = Sounds.shootBig;
				shoot.shots = 4;
				inaccuracy = 6f;
				shoot.shotDelay = 5f;
				bullet = new ArtilleryBulletType(5, 50) {{
					height = 20f;
					width = 15f;
					trailSize = 5;
					despawnHit = true;
					hitColor = UAWPal.surgeMiddle;
					lifetime = (unitRange / speed) * 1.5f;
					status = UAWStatusEffects.EMP;
					statusDuration = 0.5f * tick;
					shootEffect = Fx.shootBigColor;
					smokeEffect = Fx.shootBigSmoke;
					hitEffect = new MultiEffect(Fx.blastExplosion, Fx.flakExplosionBig);
					hitSound = Sounds.boom;
					frontColor = UAWPal.surgeFront;
					backColor = UAWPal.surgeBack;
					splashDamage = 16f;
					splashDamageRadius = 2.5f * tilesize;
					ammoMultiplier = 6f;
				}};
			}});
			weapons.add(new MissileLauncherWeapon() {{
				missileName = cruisemissile_medium_basic;
				layerOffset = -0.01f;
				missileSizeScl = 1f;
				mirror = true;
				alternate = false;
				shootCone = 30;
				x = 8.5f;
				y = 3.5f;
				maxRange = unitRange;
				reload = 2f * 60;
				shootSound = Sfx.missileShootBig1;

				bullet = new BulletType() {{
					hitColor = Pal.lightPyraFlame;
					shootEffect = Fx.shootBigColor;
					smokeEffect = Fx.shootBigSmoke2;
					shake = 1f;
					speed = 0f;
					keepVelocity = false;

					spawnUnit = new CruiseMissileUnitType(name + "missile") {{
						sprite = cruisemissile_medium_basic;
						health = 400;
						speed = 5.5f;
						maxRange = 3 * tilesize;
						lifetime = unitRange * 2f / this.speed;

						trailColor = exhaustColor = Pal.lightOrange;
						engineSize = 10 * px;
						engineOffset = 39f * px;
						rotateSpeed = 6f;
						trailLength = 9;
						missileAccelTime = 35f;
						deathSound = Sounds.largeExplosion;

						weapons.add(new SuicideWeapon() {{
							float splashRad = 6 * tilesize;
							bullet = new ExplosionBulletType(health, splashRad) {{
								status = StatusEffects.burning;
								statusDuration = 5 * tick;
								makeFire = true;
								hitColor = Pal.lightOrange;
								shootEffect = UAWFx.dynamicExplosion(splashRad, hitColor, exhaustColor);
							}};
						}});
						abilities.add(new MoveEffectAbility() {{
							color = Color.grays(0.3f).lerp(Pal.bulletYellow, 0.5f).a(0.4f);
							effect = UAWFx.missileTrailSmoke(65, 5, 14);
							rotation = 180f;
							y = -9f;
							interval = 4f;
						}});
					}};
				}};
			}});

			abilities.add(new RazorRotorAbility(25, 10f, 4.5f * tilesize) {
			});
		}};
		calogrenant = new HelicopterUnitType("calogrenant") {{
			float unitRange = 45 * tilesize;
			health = 7500;
			armor = 12f;
			hitSize = 45;

			speed = 3f;
			accel = 0.03f;
			drag = 0.07f;
			rotateSpeed = 2.7f;

			lowAltitude = true;
			faceTarget = flying = true;
			range = unitRange;

			engineSize = 0;

			targetFlags = new BlockFlag[]{BlockFlag.turret, BlockFlag.extinguisher, BlockFlag.battery, null};

			constructor = CopterUnitEntity::new;
			aiController = FlyingAI::new;

			float rotX = 17;
			float rotY = 5;
			float rotSpeed = 32f;
			rotors.add(
				new Rotor(modName + "short-blade") {{
					x = -rotX;
					y = rotY;
					rotorSpeed = rotSpeed;
					rotorBlurSpeedMultiplier = 0.1f;
					bladeCount = 3;
					doubleRotor = true;
				}},
				new Rotor(modName + "short-blade") {{
					x = rotX;
					y = rotY;
					rotorSpeed = rotSpeed;
					rotorBlurSpeedMultiplier = 0.1f;
					bladeCount = 3;
					doubleRotor = true;
				}}
			);

			weapons.add(new Weapon(machineGun_small_red) {{
				layerOffset = -0.01f;
				rotate = false;
				mirror = true;
				shootCone = 90;
				inaccuracy = 12f;
				x = 20 * px;
				y = 80 * px;
				reload = 2.5f;
				shootSound = Sfx.gunShoot3;
				ejectEffect = Fx.casing1;
				bullet = new BasicBulletType(7f, 12) {{
					height = 12f;
					width = 6f;
					pierce = true;
					pierceCap = 2;
					buildingDamageMultiplier = 0.4f;
					maxRange = range;
					homingRange = 60f;
					lifetime = (unitRange / speed);
					ammoMultiplier = 8f;
				}};
			}});
			weapons.add(new Weapon(machineGun_medium_1_red) {{
				layerOffset = -0.01f;
				rotate = false;
				inaccuracy = 4f;
				mirror = true;
				shootCone = 30f;
				x = 40 * px;
				y = 52 * px;
				reload = 8;
				shootSound = Sounds.shootBig;
				ejectEffect = Fx.casing2;
				bullet = new TrailBulletType(5f, 35) {{
					trailLengthScale = 0.8f;
					height = 16f;
					width = 8f;
					splashDamage = damage;
					splashDamageRadius = 16;
					frontColor = Pal.missileYellow;
					backColor = Pal.missileYellowBack;
					buildingDamageMultiplier = 0.3f;
					lifetime = (unitRange * 0.75f) / speed;
					status = StatusEffects.burning;
					ammoMultiplier = 8f;
					hitEffect = new MultiEffect(Fx.blastExplosion, Fx.fireHit, Fx.blastsmoke);
					shootEffect = Fx.shootBigColor;
					smokeEffect = Fx.shootBigSmoke;
					fragBullets = 3;
					fragBullet = fragGlassFrag;


				}};
			}});
			weapons.add(new MissileLauncherWeapon(cruiseMissileMount_1) {{
				rotate = false;
				baseRotation = -135;
				shootCone = 350;
				missileName = cruisemissile_medium_cryo;
				mirror = true;
				layerOffset = -0.1f;
				x = 40 * px;
				y = -26 * px;
				reload = 5 * tick;
				shootSound = Sfx.missileShootBig1;
				bullet = new BulletType() {{
					hitColor = UAWPal.cryoFront;
					shootEffect = Fx.shootBigColor;
					smokeEffect = Fx.shootBigSmoke2;
					shake = 1f;
					speed = 0f;
					keepVelocity = false;

					spawnUnit = new CruiseMissileUnitType(name + "missile") {{
						sprite = cruisemissile_medium_cryo;
						health = 650;
						speed = 6.5f;
						maxRange = 3 * tilesize;
						lifetime = unitRange * 3.5f / this.speed;
						exhaustColor = UAWPal.cryoBack;

						engineSize = 9 * px;
						engineOffset = 28f * px;
						rotateSpeed = 2.2f;
						trailLength = 12;
						missileAccelTime = 35f;
						deathSound = Sounds.largeExplosion;

						weapons.add(new SuicideWeapon() {{
							float splashRad = 8 * tilesize;
							bullet = new ExplosionBulletType(health, splashRad) {{
								status = StatusEffects.freezing;
								statusDuration = 4 * tick;
								hitColor = UAWPal.cryoBack;
								shootEffect = UAWFx.dynamicExplosion(splashRad, hitColor, exhaustColor);
							}};
						}});
						abilities.add(new MoveEffectAbility() {{
							color = Color.grays(0.3f).lerp(UAWPal.cryoFront, 0.5f).a(0.4f);
							effect = UAWFx.missileTrailSmoke(65, 6, 14);
							rotation = 180f;
							y = -9f;
							interval = 4f;
						}});
					}};
				}};
			}});
		}};
		// endregion Air - Helicopters

		// region Air - Carriers - Cryocopters
		crotchety = new AirshipUnitType("crotchety") {{
			float unitRange = 30 * tilesize;
			health = 625;
			hitSize = 12;

			speed = 1.7f;
			accel = 0.05f;
			drag = 0.016f;
			rotateSpeed = 5.5f;

			ammoType = new ItemAmmoType(UAWItems.cryogel);

			faceTarget = true;
			range = unitRange;

			targetAir = false;

			fallSpeed = 0.0015f;
			spinningFallSpeed = 4;
			fallSmokeY = -10f;

			targetFlags = new BlockFlag[]{BlockFlag.generator, BlockFlag.turret, BlockFlag.repair, null};

			rotors.add(
				new Rotor(modName + "short-blade-cryo") {{
					topBladeName = "short-blade";
					x = 0;
					y = 4 * px;
					rotorSpeed = -35;
					bladeCount = 3;
					rotorBlurAlphaMultiplier = 1.2f;
					rotorBlurSpeedMultiplier = 0.125f;
				}}
			);
			weapons.add(new Weapon(machineGun_medium_2_cryo) {{
				layerOffset = -0.01f;
				rotate = false;
				mirror = true;
				inaccuracy = 3f;
				x = 27 * px;
				y = 15 * px;
				reload = 60f;
				recoil = 6 * px;
				shootSound = Sfx.gunShoot5;
				ejectEffect = Fx.casing3;
				bullet = new StatusEffectBulletType(UAWStatusEffects.cryoBurn, 3 * tick) {{
					lifetime = unitRange / speed;
					splashDamage = 40;
					splashDamageRadius = 4.5f * tilesize;
					frontColor = UAWPal.cryoFront;
					backColor = UAWPal.cryoBack;
					trailEffect = new MultiEffect(
						Fx.disperseTrail,
						new StatusHitEffect() {{
							color = frontColor;
							sizeMax = 1.25f;
							square = true;
							circle = false;
						}}
					);
					trailChance = 0.5f;
					shootEffect = Fx.shootBigColor;
					trailColor = hitColor = backColor;
					hitEffect = new MultiEffect(
						new ExplosionEffect() {{
							lifetime = 20f;
							waveLife = 15f;
							smokes = 36;
							sparks = 18;
							waveColor = UAWPal.cryoFront;
							sparkColor = UAWPal.cryoMiddle;
							waveRad = splashDamageRadius;
							smokeRad = splashDamageRadius * 1.2f;
						}},
						new SparkEffect() {{
							size = 4;
							lifetime = 60;
							amount = 20;
							spreadRad = splashDamageRadius * 0.8f;
							sparkColor = frontColor;
						}}
					);
					smokeEffect = Fx.shootBigSmoke;
					shake = 2.2f;
				}};
			}});
		}};
		cantankerous = new AirshipUnitType("cantankerous") {{
			float unitRange = 35 * tilesize;
			health = 5000;
			armor = 15f;
			hitSize = 20;

			speed = 1.2f;
			accel = 0.03f;
			drag = 0.07f;
			rotateSpeed = 1f;

			range = unitRange;

			targetGround = false;

			engineSize = 4;
			engineOffset = 58 * px;
			trailLength = 32;
			waveTrailY = -35f * px;
			trailScl = 1.2f;

			float rotX = 41 * px;
			float rotY = -4 * px;
			float rotSpeed = 32f;
			float layerOffset = -0.00009f;
			float rotorScaling = 0.55f;
			rotors.add(
				new Rotor(modName + "short-blade-cryo") {{
					topBladeName = "short-blade";
					x = -rotX;
					y = rotY;
					rotorSpeed = rotSpeed;
					rotorBlurSpeedMultiplier = 0.08f;
					bladeCount = 4;
					rotorLayer = layerOffset;
					rotorSizeScl = rotorTopSizeScl = rotorScaling;
				}},
				new Rotor(modName + "short-blade-cryo") {{
					topBladeName = "short-blade";
					x = rotX;
					y = rotY;
					rotorSpeed = rotSpeed;
					rotorBlurSpeedMultiplier = 0.08f;
					bladeCount = 4;
					rotorLayer = layerOffset;
					rotorSizeScl = rotorTopSizeScl = rotorScaling;
				}}
			);

			weapons.add(new Weapon(name + "-turret") {{
				layerOffset = 0.02f;
				rotate = true;
				rotateSpeed = 1.5f;
				mirror = false;
				shootCone = 90;
				inaccuracy = 6f;
				x = y = 0;
				reload = 3f * tick;
				recoil = 0;
				shootSound = Sfx.cannonShoot1;
				ejectEffect = Fx.casing3;
				shootY = 82 * px;
				shoot = new ShootAlternate() {{
					shots = 2;
					shotDelay = 7.5f;
					spread = 1f;
					barrels = 1;
				}};
				bullet = new StatusEffectBulletType(UAWStatusEffects.cryoBurn, 8 * tick) {{
					lifetime = unitRange / speed;
					// Fires twice, damage is doubled
					splashDamage = 80;
					splashDamageRadius = 6.5f * tilesize;
					frontColor = UAWPal.cryoFront;
					backColor = UAWPal.cryoBack;
					trailEffect = new MultiEffect(
						Fx.disperseTrail,
						new StatusHitEffect() {{
							color = frontColor;
							sizeMax = 2;
							square = true;
							circle = false;
						}}
					);
					trailChance = 0.5f;
					shootEffect = Fx.shootBigColor;
					trailColor = hitColor = backColor;
					hitEffect = new MultiEffect(
						new ExplosionEffect() {{
							lifetime = 20f;
							waveLife = 15f;
							smokes = 39;
							sparks = 25;
							waveColor = UAWPal.cryoFront;
							sparkColor = UAWPal.cryoMiddle;
							waveRad = splashDamageRadius;
							smokeRad = splashDamageRadius * 1.2f;
							smokeSize = 6f;
						}},
						new SparkEffect() {{
							size = 4;
							lifetime = 60;
							amount = 20;
							spreadRad = splashDamageRadius * 0.8f;
							sparkColor = frontColor;
						}}
					);
					smokeEffect = Fx.shootBigSmoke;
					shake = 2.2f;
				}};
				parts.addAll(
					new RegionPart("-gun") {{
						progress = PartProgress.recoil;
						moveY = -13 * px;
						mirror = false;
						layerOffset = 0.01f;
						outlineLayerOffset = -0.025f;
					}}
				);
			}});
			weapons.add(new Weapon(missile_medium_cryo_3) {{
				layerOffset = -0.05f;
				mirror = true;

				x = 24 * px;
				y = -2 * px;

				rotate = false;
				alternate = false;
				rotationLimit = 20;
				baseRotation = -90;
				reload = 4.5f * tick;
				recoil = 0f;
				inaccuracy = 12f;
				shootCone = 180f;

				shootSound = Sounds.missileLaunch;

				maxRange = unitRange;

				shoot = new ShootAlternate() {{
					shots = 2;
					shotDelay = 16f;
					barrels = 1;
				}};

				bullet = new BulletType() {{
					hitColor = UAWPal.cryoFront;
					shootEffect = Fx.shootBigColor;
					smokeEffect = Fx.shootBigSmoke2;
					shake = 1f;
					speed = 0f;
					keepVelocity = false;

					spawnUnit = new CruiseMissileUnitType("cantankerous-missile") {{
						sprite = cruisemissile_small_cryo;
						health = 250;
						speed = 4.5f;
						maxRange = 4 * tilesize;
						lifetime = unitRange * 1.8f / this.speed;

						trailColor = exhaustColor = UAWPal.cryoBack;
						engineSize = 4 * px;
						engineOffset = 12f * px;
						rotateSpeed = 3f;
						trailLength = 9;
						missileAccelTime = 35f;
						deathSound = Sounds.largeExplosion;

						weapons.add(new SuicideWeapon() {{
							float splashRad = 4 * tilesize;
							bullet = new ExplosionBulletType(95, splashRad) {{
								status = UAWStatusEffects.cryoBurn;
								statusDuration = 5 * tick;
								makeFire = true;
								hitColor = UAWPal.cryoMiddle;
								shootEffect = UAWFx.dynamicExplosion(splashRad, hitColor, exhaustColor);
							}};
						}});
						abilities.add(new MoveEffectAbility() {{
							color = Color.grays(0.3f).lerp(UAWPal.cryoMiddle, 0.5f).a(0.4f);
							effect = UAWFx.missileTrailSmoke(45, 3, 14);
							rotation = 180f;
							y = -9f;
							interval = 4f;
						}});
					}};
				}};
			}});
		}};
		// endregion Air - Carriers

		// region Naval - Monitor
		arquebus = new UnitType("arquebus") {{
			float unitRange = 30 * tilesize;
			health = 750;
			armor = 5;
			hitSize = 20;

			speed = 0.75f;
			accel = 0.2f;
			drag = 0.05f;
			rotateSpeed = 1.8f;

			range = unitRange;
			maxRange = range;
			faceTarget = false;
			ammoType = new ItemAmmoType(Items.graphite, 2);

			trailLength = 22;
			waveTrailX = 7f;
			waveTrailY = -9f;
			trailScl = 1.4f;

			targetFlags = new BlockFlag[]{BlockFlag.turret, BlockFlag.extinguisher, BlockFlag.repair, BlockFlag.core};

			constructor = UnitWaterMove::create;

			weapons.add(new PointDefenseWeapon(pointDefense_Purple) {{
				rotate = autoTarget = true;
				mirror = controllable = false;
				x = 0;
				y = 8f;
				reload = 2.5f;
				recoil = 0.1f;
				targetInterval = 10f;
				targetSwitchInterval = 15f;
				ejectEffect = Fx.casing1;

				bullet = new BulletType() {{
					shootEffect = Fx.sparkShoot;
					hitEffect = Fx.pointHit;
					maxRange = 10f * tilesize;
					damage = 10f;
				}};
			}});
			weapons.add(new Weapon(machineGun_small_purple) {{
				rotate = mirror = autoTarget = true;
				controllable = false;
				x = 5f;
				y = 0.4f;
				inaccuracy = 4f;
				reload = 30f;
				shootSound = Sounds.shoot;
				ejectEffect = Fx.casing1;
				shoot.shots = 4;
				shoot.shotDelay = 5f;
				bullet = new BasicBulletType(3f, 5) {{
					width = 5f;
					height = 12f;
					shrinkY = 1f;
					lifetime = (unitRange * 0.7f) / speed;
					backColor = Pal.gray;
					frontColor = Color.white;
					despawnEffect = Fx.none;
					collidesGround = false;
				}};
			}});
			weapons.add(new Weapon(artillery_small_purple) {{
				mirror = rotate = alternate = true;
				x = 5.5f;
				y = -8f;

				inaccuracy = 8f;
				shootCone = 30;
				rotateSpeed = 2.2f;
				reload = 1.2f * 60;
				recoil = 2.2f;
				shootSound = Sounds.artillery;
				shake = 2.5f;
				shootStatusDuration = reload * 1.2f;
				shootStatus = StatusEffects.slow;
				ejectEffect = Fx.casing3;
				bullet = new SplashArtilleryBulletType(2f, 35, 4 * tilesize) {{
					buildingDamageMultiplier = 2f;
					height = 15;
					width = height / 1.8f;
					lifetime = unitRange / speed;
					trailMult = 0.9f;
					hitShake = 4.5f;
					frontColor = Pal.sapBullet;
					backColor = Pal.sapBulletBack;
					shootEffect = new MultiEffect(
						Fx.shootBig2,
						UAWFx.shootSmoke(15, backColor, false)
					);
					smokeEffect = new MultiEffect(
						Fx.shootBigSmoke2,
						Fx.shootLiquid
					);
					despawnEffect = hitEffect = new MultiEffect(
						UAWFx.dynamicExplosion(splashDamageRadius)
					);
				}};
			}});
		}};
		carronade = new UnitType("carronade") {{
			float unitRange = 40 * tilesize;
			health = 7000;
			armor = 8;
			hitSize = 22;

			speed = 0.8f;
			accel = 0.20f;
			rotateSpeed = 1.5f;

			range = unitRange;
			maxRange = range;
			faceTarget = false;

			ammoType = new ItemAmmoType(Items.graphite, 2);

			trailLength = 35;
			waveTrailX = 9f;
			waveTrailY = -15f;
			trailScl = 2f;

			constructor = UnitWaterMove::create;

			weapons.add(
				new PointDefenseWeapon(pointDefense_Purple) {{
					rotate = autoTarget = true;
					mirror = controllable = false;
					x = 0f;
					y = 10f;
					recoil = 0.5f;
					reload = 3f;
					targetInterval = 10f;
					targetSwitchInterval = 15f;
					ejectEffect = Fx.casing1;

					bullet = new BulletType() {{
						shootEffect = Fx.sparkShoot;
						hitEffect = Fx.pointHit;
						maxRange = 15f * tilesize;
						damage = 10f;
					}};
				}},
				new Weapon(machineGun_small_purple) {{
					rotate = mirror = autoTarget = alternate = true;
					controllable = false;
					x = 7f;
					y = 5f;
					inaccuracy = 4f;
					reload = 12f;
					shootSound = Sounds.shoot;
					ejectEffect = Fx.casing2;
					bullet = new BasicBulletType(3f, 5) {{
						width = 5f;
						height = 12f;
						shrinkY = 1f;
						lifetime = 20f;
						backColor = Pal.gray;
						frontColor = Color.white;
						despawnEffect = Fx.none;
						collidesGround = false;
					}};
				}},
				new Weapon(missile_large_purple_1) {{
					rotate = true;
					mirror = false;
					rotateSpeed = 1f;
					x = 0f;
					y = -7f;
					targetFlags = new BlockFlag[]{BlockFlag.turret, BlockFlag.extinguisher, BlockFlag.repair, BlockFlag.core};
					inaccuracy = 8f;
					reload = 4f * 60;
					recoil = 4;
					shootSound = Sounds.artillery;
					shake = 6;
					shootStatusDuration = reload * 1.2f;
					shootStatus = StatusEffects.unmoving;
					shoot = new ShootAlternate() {{
						shots = 6;
						shotDelay = 6.5f;
						spread = 4f;
						barrels = 3;
					}};
					velocityRnd = 0.4f;
					ejectEffect = Fx.casing3;
					bullet = new SplashArtilleryBulletType(1.8f, 90, 5 * tilesize) {{
						buildingDamageMultiplier = 2f;
						height = 24;
						width = height / 2;
						lifetime = unitRange / speed;
						status = StatusEffects.burning;
						incendChance = 0.8f;
						incendSpread = 16f;
						hitShake = 6f;
						makeFire = true;
						frontColor = Pal.sapBullet;
						backColor = Pal.sapBulletBack;
						trailMult = 1.5f;
						shootEffect = new MultiEffect(
							Fx.shootBig2,
							UAWFx.shootSmoke(15, backColor, false)
						);
						smokeEffect = new MultiEffect(
							Fx.shootBigSmoke2,
							Fx.shootLiquid
						);
						despawnEffect = hitEffect = new MultiEffect(
							UAWFx.dynamicExplosion(splashDamageRadius)
						);
						fragBullets = 4;
						fragBullet = new BasicBulletType(2.5f, 10) {{
							width = 10f;
							height = 12f;
							shrinkY = 1f;
							lifetime = 15f;
							backColor = Pal.plastaniumBack;
							frontColor = Pal.plastaniumFront;
							despawnEffect = Fx.none;
						}};
					}};
				}}
			);
		}};
		falconet = new UnitType("falconet") {{
			float unitRange = 55 * tilesize;
			health = 16000;
			hitSize = 44;
			speed = 0.65f;
			drag = 0.17f;
			accel = 0.2f;
			rotateSpeed = 1f;
			faceTarget = false;
			range = unitRange;
			maxRange = range;
			ammoType = new ItemAmmoType(Items.thorium, 2);

			trailLength = 50;
			waveTrailX = 18f;
			waveTrailY = -15f;
			trailScl = 3.2f;

			constructor = UnitWaterMove::create;
			forceMultiTarget = true;

			weapons.add(new PointDefenseWeapon(pointDefense_Purple) {{
				mirror = true;
				x = 9f;
				y = 12f;
				reload = 6f;
				recoil = 0f;
				targetInterval = 4f;
				targetSwitchInterval = 4f;
				ejectEffect = Fx.casing1;
				bullet = new BulletType() {{
					shootEffect = Fx.sparkShoot;
					smokeEffect = Fx.shootSmallSmoke;
					hitEffect = Fx.pointHit;
					maxRange = 15 * tilesize;
					damage = 15f;
				}};
			}});
			weapons.add(new PointDefenseWeapon(pointDefense_Purple) {{
				mirror = true;
				x = 18f;
				y = -11f;
				reload = 6f;
				recoil = 0f;
				targetInterval = 4f;
				targetSwitchInterval = 4f;
				ejectEffect = Fx.casing1;
				bullet = new BulletType() {{
					shootEffect = Fx.sparkShoot;
					smokeEffect = Fx.shootSmallSmoke;
					hitEffect = Fx.pointHit;
					maxRange = 15 * tilesize;
					damage = 15f;
				}};
			}});
			weapons.add(new Weapon(machineGun_medium_purple) {{
				rotate = mirror = autoTarget = alternate = true;
				controllable = false;
				x = 12f;
				y = 2.5f;
				inaccuracy = 16f;
				reload = 12f;
				shootSound = Sounds.shootBig;
				ejectEffect = Fx.casing2;
				bullet = new FlakBulletType(8f, 0) {{
					splashDamage = 30;
					height = 16f;
					width = 8f;
					homingPower = 0.05f;
					homingRange = 6 * tilesize;
					explodeRange = splashDamageRadius = 3f * tilesize;
					explodeDelay = 10f;
					buildingDamageMultiplier = 0.5f;
					maxRange = unitRange - 16;
					lifetime = (unitRange / speed) / 2;
					trailWidth = width / 3.4f;
					trailLength = Mathf.round(height);
					trailColor = backColor;
					shootEffect = Fx.shootBig;
					smokeEffect = Fx.shootBigSmoke;
					hitEffect = new MultiEffect(
						Fx.blastExplosion,
						Fx.fireHit,
						Fx.blastsmoke,
						Fx.flakExplosionBig
					);
					ammoMultiplier = 8f;
					fragBullets = 4;
					fragLifeMin = 0.3f;
					fragLifeMax = 0.6f;
					fragBullet = new FlakBulletType(4f, 3) {{
						lifetime = 60f;
						ammoMultiplier = 5f;
						shootEffect = Fx.shootSmall;
						reloadMultiplier = 0.8f;
						width = 6f;
						height = 8f;
						hitEffect = Fx.flakExplosion;
						splashDamage = 25f * 1.5f;
						splashDamageRadius = 20f;
						fragBullet = new BasicBulletType(3f, 5, "bullet") {{
							width = 5f;
							height = 12f;
							shrinkY = 1f;
							lifetime = 20f;
							backColor = Pal.gray;
							frontColor = Color.white;
							despawnEffect = Fx.none;
						}};
						fragBullets = 6;
					}};

				}};
			}});
			weapons.add(new UAWWeapon(artillery_large_purple) {{
				layerOffset = 0.2f;
				rotate = true;
				mirror = false;
				rotateSpeed = 1f;
				x = 0f;
				y = -3f;
				shootY = 70 * px;
				targetFlags = new BlockFlag[]{
					BlockFlag.turret,
					BlockFlag.extinguisher,
					BlockFlag.repair,
					BlockFlag.core
				};
				inaccuracy = 10f;
				reload = 5f * 60;
				recoil = 2f;
				shootSound = Sfx.cannonShootBig1;
				shake = 16;
				shootStatusDuration = reload * 1.5f;
				shootStatus = StatusEffects.slow;
				ejectEffect = UAWFx.casing5;

				bullet = new SplashArtilleryBulletType(1.7f, 550, 11 * tilesize) {{
					height = 42;
					width = height / 2f;
					buildingDamageMultiplier = 3.5f;
					lifetime = unitRange / speed;
					status = StatusEffects.burning;
					incendChance = 0.8f;
					incendSpread = 16f;
					makeFire = true;
					hitSound = Sfx.explosionHuge1;
					trailMult = 1f;
					hitShake = 15f;
					frontColor = Pal.sapBullet;
					backColor = Pal.sapBulletBack;
					shootEffect = new MultiEffect(
						UAWFx.shootSmoke(30, backColor, true),
						UAWFx.shootHugeColor
					);
					smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.shootLiquid);
					despawnHit = true;
					hitEffect = UAWFx.dynamicExplosion(splashDamageRadius, frontColor, backColor);
					fragBullets = 1;
					fragBullet = new AftershockBulletType(splashDamage / 2, splashDamageRadius / 1.2f) {{
						splashAmount = 5;
						splashDelay = 60;
						buildingDamageMultiplier = 3.5f;
						lifetime = (splashDelay * splashAmount);
						frontColor = Pal.sapBullet;
						backColor = Pal.sapBulletBack;
						status = StatusEffects.melting;
						statusDuration = 2 * tick;
						particleColor = Pal.suppress;
						particleEffect = new MultiEffect(Fx.sporeSlowed, Fx.hitBulletColor);
						makeFire = true;
						applySound = Sounds.flame2;
					}};
				}};

				parts.add(new RegionPart("-barrel") {{
					progress = PartProgress.recoil;
					moveY = -10 * px;
				}});
			}});
		}};
		// endregion Naval - Monitor

		// region Naval - Torpedo
		megaera = new UnitType("megaera") {{
			float unitRange = 35 * tilesize;
			health = 650;
			speed = 1.2f;
			accel = 0.2f;
			rotateSpeed = 1.9f;
			drag = 0.05f;
			hitSize = 18;
			maxRange = range = unitRange;
			faceTarget = false;
			ammoType = new ItemAmmoType(Items.graphite, 2);

			trailLength = 20;
			waveTrailX = 6f;
			waveTrailY = -4f;
			trailScl = 1.9f;

			constructor = UnitWaterMove::create;

			weapons.add(new Weapon(machineGun_small_red) {{
				rotate = true;
				mirror = true;
				shootCone = 90;
				inaccuracy = 3f;
				x = 28f * px;
				y = -12f * px;
				reload = 5f;
				shootSound = Sfx.gunShoot3;
				ejectEffect = Fx.casing1;
				bullet = new TrailBulletType(6f, 2) {{
					height = 8f;
					width = 5f;
					buildingDamageMultiplier = 0.4f;
					maxRange = unitRange / 0.8f;
					homingRange = 60f;
					ammoMultiplier = 8f;
				}};
			}});
			weapons.add(new Weapon() {{
				rotate = false;
				alternate = mirror = false;
				shootCone = 180;
				x = 0f;
				y = 6f;
				reload = 4f * 60;
				inaccuracy = 1f;
				ammoType = new ItemAmmoType(Items.thorium);
				targetAir = false;

				shootSound = Sfx.torpedoShoot1;

				bullet = new TorpedoBulletType(1.8f, 550) {{
					shootEffect = new MultiEffect(
						UAWFx.shootSmoke,
						Fx.smeltsmoke
					);
					trailLengthScale = 1.5f;
					height = 13;
					width = height / 2;
					lifetime = unitRange / speed;
					homingRange = unitRange / 2;
					hitSizeDamageScl = 3.5f;
					maxEnemyHitSize = 35;
				}};
			}});
			weapons.add(new PointDefenseWeapon(pointDefense_Red) {{
				rotate = autoTarget = true;
				mirror = controllable = false;
				x = 0f;
				y = -7f;
				reload = 4f;
				recoil = 0f;
				targetInterval = 4f;
				targetSwitchInterval = 4f;
				ejectEffect = Fx.casing1;
				bullet = new BulletType() {{
					shootEffect = Fx.sparkShoot;
					smokeEffect = Fx.shootSmallSmoke;
					hitEffect = Fx.pointHit;
					maxRange = unitRange / 3.5f;
					splashDamage = 15f;
				}};
			}});
		}};
		alecto = new UnitType("alecto") {{
			float unitRange = 45 * tilesize;
			health = 4500;
			speed = 1f;
			accel = 0.2f;
			rotateSpeed = 1.8f;
			drag = 0.17f;
			hitSize = 22f;
			armor = 5f;
			faceTarget = false;
			maxRange = range = unitRange;
			ammoType = new ItemAmmoType(Items.graphite, 2);

			trailLength = 25;
			waveTrailX = 8f;
			waveTrailY = -9f;
			trailScl = 2.2f;

			constructor = UnitWaterMove::create;

			weapons.add(new Weapon() {{
				rotate = false;
				alternate = mirror = true;
				shootCone = 180;
				x = 8f;
				y = 6f;
				reload = 3f * 60;
				inaccuracy = 1f;
				targetAir = false;

				shootSound = Sfx.torpedoShoot1;

				bullet = new TorpedoBulletType(1.8f, 650) {{
					shootEffect = new MultiEffect(
						UAWFx.shootSmoke,
						Fx.smeltsmoke
					);
					trailLengthScale = 1.5f;
					lifetime = unitRange / speed;
					homingRange = unitRange;
					maxEnemyHitSize = 45;
					hitSizeDamageScl = 4;
				}};
			}});

			weapons.add(new PointDefenseWeapon(pointDefense_Red) {{
				rotate = autoTarget = true;
				mirror = controllable = false;
				x = 0f;
				y = 9f;
				reload = 3f;
				recoil = 0f;
				targetInterval = 3f;
				targetSwitchInterval = 3f;
				ejectEffect = Fx.casing1;
				bullet = new BulletType() {{
					shootEffect = Fx.sparkShoot;
					smokeEffect = Fx.shootSmallSmoke;
					hitEffect = Fx.pointHit;
					maxRange = unitRange / 3f;
					splashDamage = 15f;
				}};
			}});
			weapons.add(new Weapon(machineGun_medium_1_red) {{
				rotate = true;
				inaccuracy = 3f;
				mirror = true;
				x = 8f;
				y = -5f;
				reload = 7;
				recoil = 1f;
				shootSound = Sounds.shoot;
				ejectEffect = Fx.casing2;
				bullet = new BasicBulletType(7f, 25) {{
					height = 10f;
					width = 6f;
					pierce = true;
					pierceCap = 2;
					buildingDamageMultiplier = 0.3f;
					lifetime = (unitRange / speed) * 0.7f;
					trailLength = 10;
					trailWidth = width / 3;
					trailColor = backColor;
					hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.shootSmallSmoke);
				}};
			}});
			weapons.add(new Weapon(missile_medium_red_2) {{
				reload = 45f;
				x = 0f;
				y = -8f;
				rotateSpeed = 2f;
				rotationLimit = 220;
				rotate = true;
				mirror = false;
				shoot = new ShootAlternate() {{
					shots = 2;
					shotDelay = 6f;
					barrels = 2;
				}};
				inaccuracy = 5f;
				velocityRnd = 0.1f;
				shootSound = Sounds.missile;
				ejectEffect = Fx.none;
				bullet = new MissileBulletType(5f, 60) {{
					height = 12;
					width = height / 2;
					homingRange = 60f;
					keepVelocity = false;
					splashDamageRadius = 4 * tilesize;
					splashDamage = splashDamage * 1.5f;
					lifetime = unitRange / speed * 0.8f;
					trailColor = Color.gray;
					backColor = Pal.bulletYellowBack;
					frontColor = Pal.bulletYellow;
					despawnEffect = hitEffect = new MultiEffect(Fx.blastExplosion, Fx.blastsmoke);
					buildingDamageMultiplier = 0.5f;
					weaveScale = 8f;
					weaveMag = 1f;
				}};
			}});
		}};
		// endregion Naval - Torpedo

		// region Ground - MBT
		cavalier = new TankUnitType("cavalier") {{
			float unitRange = 32 * tilesize;
			health = 950;
			armor = 25;
			hitSize = 13;
			terrainSpeedMultiplier = 1f;
			terrainDragMultiplier = 1.3f;
			speed = 5.5f;
			accel = 0.04f;
			rotateSpeed = 1.5f;
			ammoType = new ItemAmmoType(Items.graphite);

			range = unitRange;

			immunities = ObjectSet.with(
				StatusEffects.disarmed,
				UAWStatusEffects.EMP,
				StatusEffects.freezing
			);

			treadFrames = 6;
			treadPullOffset = 4;
			treadRects = new Rect[]{
				// 0
				new Rect(8 - 96 / 2f, 33 - 144 / 2f, 16, 66)
			};
			weapons.addAll(
				// Main Gun
				new Weapon(name + "-turret") {{
					layerOffset = 0.1f;
					mirror = false;

					x = 0f;
					y = 0f;

					rotate = true;
					rotateSpeed = 3;
					reload = 1.5f * tick;
					recoil = 0;
					shootY = 68f * px;
					shake = 6f;

					soundPitchMin = 1.2f;
					soundPitchMax = soundPitchMin + 0.4f;
					shootSound = Sounds.shootBig;

					ejectEffect = UAWFx.casing2Long;

					bullet = new TrailBulletType(5f, 140) {{
						splashDamage = damage;
						splashDamageRadius = 3 * tilesize;
						frontColor = Pal.missileYellow;
						trailColor = backColor = Pal.missileYellowBack;
						height = 25f;
						width = 8f;
						lifetime = unitRange / speed;
						knockback = 4f;
						despawnHit = true;
						shootEffect = new MultiEffect(
							UAWFx.railShoot(35f, backColor),
							Fx.shootPyraFlame,
							Fx.shootSmallSmoke
						);
						hitEffect = new MultiEffect(
							Fx.blastExplosion,
							Fx.fireHit,
							Fx.blastsmoke
						);
						trailInterval = 1.6f;
						trailChance = 0.3f;
						trailEffect = new MultiEffect(
//							Fx.hitBulletColor,
							Fx.hitFlameSmall
						);
						hitSound = Sounds.explosion;
						fragBullets = 8;
						collidesAir = false;
						fragBullet = fragGlassFrag;
						status = StatusEffects.burning;
					}};

					parts.add(
						new RegionPart("-gun") {{
							progress = PartProgress.recoil;
							under = true;
							moveY = -9 * px;
						}}
					);
				}},
				// Machine Gun
				new Weapon(machineGun_small_red) {{
					mirror = false;

					x = 27 * px;
					y = 24 * px;

					rotate = true;
					rotationLimit = 200;
					ignoreRotation = true;
					rotateSpeed = 3;
					reload = 4.5f;
					recoil = 1f;
					inaccuracy = 12f;

					shootSound = Sfx.gunShoot3;
					ejectEffect = Fx.casing1;

					bullet = new BasicBulletType(6f, 10) {{
						height = 8f;
						width = 5f;
						buildingDamageMultiplier = 0.4f;
						maxRange = unitRange / 0.8f;
						homingRange = 60f;
						ammoMultiplier = 8f;
					}};
				}},
				// Point Defense
				new PointDefenseWeapon(pointDefense_Red_2) {{
					mirror = false;
					ignoreRotation = true;

					x = -27 * px;
					y = -22 * px;

					reload = 15f;
					recoil = 0f;
					targetInterval = 10f;
					targetSwitchInterval = 15f;

					color = Pal.bulletYellowBack;

					bullet = new BulletType() {{
						hitColor = Pal.bulletYellowBack;
						shootEffect = Fx.shootSmallColor;
						hitEffect = Fx.hitBulletColor;
						maxRange = unitRange;
						damage = 100f;
					}};
				}}
			);
		}};
		centurion = new TankUnitType("centurion") {{
			float unitRange = 42 * tilesize;
			health = 7200;
			armor = 25;
			hitSize = 15;
			terrainSpeedMultiplier = 1f;
			terrainDragMultiplier = 1.5f;
			speed = 4f;
			accel = 0.04f;
			rotateSpeed = 1.8f;
			ammoType = new ItemAmmoType(Items.graphite);

			range = unitRange;
			maxRange = unitRange;

			immunities = ObjectSet.with(StatusEffects.disarmed, UAWStatusEffects.EMP, StatusEffects.freezing);

			treadFrames = 9;
			treadPullOffset = 4;
			treadRects = new Rect[]{
				// 0
				new Rect(8 - 128 / 2f, 48 - 240 / 2f, 27, 142)
			};
			weapons.addAll(
				// Main Gun
				new Weapon(name + "-turret") {{
					layerOffset = 0.1f;
					mirror = false;
					x = 0f;
					y = 0f;
					rotate = true;
					rotateSpeed = 1.5f;
					reload = 4.5f * tick;
					recoil = 0;
					shootY = 117f * px;
					shake = 18f;

					shootSound = Sfx.cannonShoot1;
					ejectEffect = UAWFx.casing3Long;
					bullet = new HighVelocityShellBulletType(15f, 355) {{
						shootEffect = new MultiEffect(
							UAWFx.railShoot(45f, UAWPal.graphiteFront),
							Fx.shootTitan,
							UAWFx.shootHugeColor
						);
						smokeEffect = new MultiEffect(
							UAWFx.shootSmoke(25, Pal.lightishOrange.lerp(UAWPal.graphiteFront, 0.45f), true),
							Fx.shootSmokeTitan
						);
						hitEffect = new MultiEffect(
							UAWFx.hitBulletBigColor,
							Fx.colorSparkBig,
							Fx.disperseTrail,
							Fx.hitLancer
						);
						hitShake = shake;
						lifetime = unitRange / speed;
						height = 25;
						trailChance = 0.9f;
						trailInterval = 0.05f;
					}};

					parts.add(
						new RegionPart("-gun") {{
							progress = PartProgress.recoil;
							under = true;
							moveY = -8.5f * px;
						}}
					);
				}},
				// Machine Gun
				new Weapon(machineGun_medium_1_red) {{
					mirror = false;

					x = 31 * px;
					y = 47 * px;

					rotate = true;
					rotationLimit = 170;
					ignoreRotation = true;
					rotateSpeed = 4;
					reload = 9f;
					recoil = 1.2f;
					inaccuracy = 12f;

					shootSound = Sounds.shootBig;
					soundPitchMin = 0.4f;
					soundPitchMax = 0.7f;
					ejectEffect = Fx.casing2;

					bullet = new BasicBulletType(4.5f, 55) {{
						height = 15f;
						width = 7f;
						buildingDamageMultiplier = 0.4f;
						maxRange = unitRange / 0.8f;
						homingRange = 60f;
						ammoMultiplier = 8f;
					}};
				}},
				// ATGM
				new Weapon(missile_medium_red_3) {{
					top = false;
					layerOffset = 0.05f;
					mirror = false;

					x = -41 * px;
					y = -41 * px;

					rotate = true;
					rotationLimit = 20;
					ignoreRotation = true;
					rotateSpeed = 0.5f;
					reload = 8 * tick;
					recoil = 0f;
					inaccuracy = 12f;
					shootCone = 270f / 2f;

					shootSound = Sounds.missileLaunch;
					ejectEffect = Fx.casing1;

					shoot = new ShootAlternate() {{
						shots = 3;
						shotDelay = 12f;
						barrels = 1;
					}};

					bullet = new BulletType() {{
						shootEffect = Fx.shootBig;
						smokeEffect = Fx.shootBigSmoke2;
						shake = 1f;
						speed = 0f;
						keepVelocity = false;

						spawnUnit = new CruiseMissileUnitType(name + "missile") {{
							sprite = cruisemissile_small_red;
							health = 120;
							speed = 4.5f;
							maxRange = 2 * tilesize;
							lifetime = unitRange * 1.5f / this.speed;
							exhaustColor = Pal.bulletYellowBack;

							engineSize = 4 * px;
							engineOffset = 12f * px;
							rotateSpeed = 4f;
							trailLength = 9;
							missileAccelTime = 35f;
							deathSound = Sounds.largeExplosion;

							weapons.add(new SuicideWeapon() {{
								bullet = new ExplosionBulletType(health * 0.85f, 6 * tilesize) {{
									hitColor = Pal.bulletYellow;
									shootEffect = UAWFx.dynamicExplosion(4 * tilesize, hitColor, exhaustColor);
								}};
							}});
							abilities.add(new MoveEffectAbility() {{
								color = Color.grays(0.3f).lerp(Pal.bulletYellowBack, 0.5f).a(0.4f);
								effect = UAWFx.missileTrailSmoke(50, 4, 15);
								rotation = 180f;
								y = -9f;
								interval = 4f;
							}});
						}};
					}};

				}},
				// Point Defense
				new PointDefenseWeapon(pointDefense_Red) {{
					mirror = false;
					ignoreRotation = true;

					x = 40 * px;
					y = -32 * px;

					reload = 30f;
					recoil = 0f;
					targetInterval = 20f;
					targetSwitchInterval = 10f;

					color = Pal.bulletYellowBack;

					bullet = new BulletType() {{
						hitColor = Pal.bulletYellowBack;
						shootEffect = Fx.shootBigColor;
						hitEffect = UAWFx.hitBulletBigColor;
						shootSound = Sounds.artillery;
						maxRange = unitRange;
						damage = 225f;
					}};
				}}
			);
		}};
		caernarvon = new TankUnitType("caernarvon") {{
			float unitRange = 55 * tilesize;
			health = 10500;
			armor = 45;
			hitSize = 18;
			terrainSpeedMultiplier = 1.2f;
			terrainDragMultiplier = 1.8f;
			speed = 4.5f;
			accel = 0.04f;
			rotateSpeed = 1.8f;
			ammoType = new ItemAmmoType(Items.graphite);

			range = unitRange;
			maxRange = unitRange;

			immunities = ObjectSet.with(StatusEffects.disarmed, UAWStatusEffects.EMP, StatusEffects.freezing);

			treadFrames = 9;
			treadPullOffset = 4;
			treadRects = new Rect[]{
				// 0
				new Rect(11 - 144 / 2f, 97 - 355 / 2f, 34, 166)
			};
			weapons.addAll(
				// Main Gun
				new Weapon(name + "-turret") {{
					layerOffset = 0.1f;
					mirror = false;
					x = 0f;
					y = 0f;
					rotate = true;
					rotateSpeed = 0.7f;
					reload = 6.5f * tick;
					recoil = 0;
					shootY = 165f * px;
					shake = 25f;

					shootSound = Sfx.cannonShoot2;
					ejectEffect = UAWFx.casing4Long;
					bullet = new HighVelocityShellBulletType(15f, 600) {{
						frontColor = Pal.bulletYellow;
						backColor = Pal.bulletYellowBack;
						shootEffect = new MultiEffect(
							UAWFx.railShoot(50f, backColor),
							Fx.shootTitan,
							UAWFx.shootHugeColor
						);
						smokeEffect = new MultiEffect(
							UAWFx.shootSmoke(35, Pal.lightishOrange.lerp(frontColor, 0.45f), true),
							Fx.shootSmokeTitan
						);
						hitEffect = new MultiEffect(
							UAWFx.hitBulletBigColor,
							Fx.colorSparkBig,
							Fx.disperseTrail,
							Fx.hitLancer
						);
						sideTrailInterval = 2f;
						hitShake = shake;
						lifetime = unitRange / speed;
						height = 35;
					}};

					parts.add(
						new RegionPart("-gun") {{
							progress = PartProgress.recoil;
							under = true;
							moveY = -12f * px;
						}}
					);
				}},
				// Machine Gun
				new Weapon(machineGun_medium_1_red) {{
					mirror = false;

					x = 30 * px;
					y = 69 * px;

					rotate = true;
					rotationLimit = 210;
					ignoreRotation = true;
					rotateSpeed = 4;
					reload = 5f;
					recoil = 1.2f;
					inaccuracy = 12f;

					shootSound = Sounds.shootBig;
					soundPitchMin = 0.4f;
					soundPitchMax = 0.7f;
					ejectEffect = Fx.casing2;

					bullet = new BasicBulletType(4.5f, 55) {{
						height = 15f;
						width = 7f;
						buildingDamageMultiplier = 0.4f;
						maxRange = unitRange / 0.8f;
						homingRange = 120f;
						ammoMultiplier = 8f;
					}};
				}},
				// ATGM
				new RotatedWeapon(missile_large_red_2) {{
					mirror = false;

					x = 0;
					y = -70 * px;

					baseRotation = -180;
					reload = 15 * tick;
					recoil = 1f;
					inaccuracy = 12f;
					shootCone = 270f / 2f;

					shootSound = Sounds.missileLaunch;
					ejectEffect = Fx.casing1;

					shoot = new ShootAlternate() {{
						shots = 5;
						shotDelay = 12f;
						barrels = 2;
					}};

					bullet = new BulletType() {{
						hitColor = Pal.missileYellow;
						shootEffect = Fx.shootBigColor;
						smokeEffect = Fx.shootBigSmoke2;
						shake = 1f;
						speed = 0f;
						keepVelocity = false;

						spawnUnit = new CruiseMissileUnitType(name + "missile") {{
							sprite = cruisemissile_small_red;
							health = 150;
							speed = 5.5f;
							maxRange = 3 * tilesize;
							lifetime = unitRange * 1.5f / this.speed;
							exhaustColor = Pal.bulletYellowBack;

							engineSize = 6 * px;
							engineOffset = 12f * px;
							rotateSpeed = 4f;
							trailLength = 9;
							missileAccelTime = 35f;
							deathSound = Sounds.largeExplosion;

							weapons.add(new SuicideWeapon() {{
								bullet = new ExplosionBulletType(health * 0.8f, 6 * tilesize) {{
									hitColor = Pal.bulletYellow;
									shootEffect = UAWFx.dynamicExplosion(4 * tilesize, hitColor, exhaustColor);
								}};
							}});
							abilities.add(new MoveEffectAbility() {{
								color = Color.grays(0.3f).lerp(Pal.bulletYellow, 0.5f).a(0.4f);
								effect = UAWFx.missileTrailSmoke(55, 5, 15);
								rotation = 180f;
								y = -9f;
								interval = 4f;
							}});
						}};
					}};

					parts.add(
						new RegionPart("-heat") {{
							heatProgress = PartProgress.heat.add(0.5f);
							drawRegion = false;
						}}
//						new RegionPart("-glow") {{
//							heatProgress = PartProgress.charge;
//							heatColor = UAWPal.drawGlowGold;
//						}}
					);
				}},
				// ART
				new RotatedWeapon(artillery_small_red) {{
					mirror = true;

					x = 45 * px;
					y = -44 * px;

					velocityRnd = 0.45f;
					baseRotation = -32;
					reload = 3.5f * tick;
					recoil = 1f;
					inaccuracy = 12f;
					shootCone = 180;

					shootSound = Sounds.artillery;
					ejectEffect = Fx.casing2;

					shoot = new ShootAlternate() {{
						shots = 4;
						shotDelay = 0.25f;
						barrels = 1;
					}};

					bullet = new SplashArtilleryBulletType(1.8f, 180, 6 * tilesize) {{
						height = 24;
						width = height / 2;
						homingRange = 8 * tilesize;
						lifetime = unitRange / speed;
						incendChance = 0.8f;
						incendSpread = 16f;
						hitShake = 6f;
						trailMult = 1.5f;
						shootEffect = new MultiEffect(
							Fx.shootBig2,
							Fx.shootPyraFlame
						);
						smokeEffect = new MultiEffect(
							Fx.shootBigSmoke2,
							Fx.fireSmoke
						);
						despawnEffect = hitEffect = new MultiEffect(
							UAWFx.dynamicExplosion(splashDamageRadius)
						);
						fragBullets = 4;
						fragBullet = new BasicBulletType(2.5f, 10) {{
							width = 10f;
							height = 12f;
							shrinkY = 1f;
							lifetime = 15f;
							backColor = Pal.plastaniumBack;
							frontColor = Pal.plastaniumFront;
							despawnEffect = Fx.none;
						}};
					}};

				}},
				// Point Defense 1
				new PointDefenseWeapon(pointDefense_Red) {{
					mirror = false;
					ignoreRotation = true;

					x = 50 * px;
					y = 40 * px;

					reload = 30f;
					recoil = 0f;
					targetInterval = 20f;
					targetSwitchInterval = 10f;

					color = Pal.bulletYellowBack;

					bullet = new BulletType() {{
						hitColor = Pal.bulletYellowBack;
						shootEffect = Fx.shootBigColor;
						hitEffect = UAWFx.hitBulletBigColor;
						shootSound = Sounds.artillery;
						maxRange = unitRange;
						damage = 225f;
					}};
				}}
			);
		}};
		// endregion Ground - MBT

	}
}
