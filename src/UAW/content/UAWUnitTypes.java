package UAW.content;

import UAW.ai.types.DynamicFlyingAI;
import UAW.entities.abilities.RazorRotorAbility;
import UAW.entities.bullet.*;
import UAW.entities.bullet.ModdedVanillaBullet.*;
import UAW.entities.units.UAWUnitType;
import UAW.entities.units.entity.*;
import UAW.graphics.*;
import UAW.type.Rotor;
import UAW.type.weapon.*;
import arc.func.Prov;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.struct.*;
import arc.struct.ObjectMap.Entry;
import com.sun.istack.NotNull;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.type.weapons.PointDefenseWeapon;
import mindustry.world.blocks.payloads.PayloadSource;
import mindustry.world.meta.BlockFlag;

import static UAW.Vars.*;
import static UAW.content.UAWBullets.fragGlassFrag;
import static arc.graphics.g2d.Draw.color;
import static mindustry.Vars.tilesize;

@SuppressWarnings("unchecked")
public class UAWUnitTypes {
	public static UnitType
		// Helicopters
		aglovale, bedivere, calogrenant,
		corsair, vindicator, superfortress,
		arquebus, carronade, falconet, audacious, arkRoyal,
		seabass, sharpnose, swordfish,
		cavalier, centurion, caernarvon, challenger;

	//Steal from Progressed Material which stole from Endless Rusting which stole from Progressed Materials in the past which stole from BetaMindy
	private static final Entry<Class<? extends Entityc>, Prov<? extends Entityc>>[] types = new Entry[]{
		prov(CopterUnitEntity.class, CopterUnitEntity::new),
		prov(TankUnitEntity.class, TankUnitEntity::new),
		prov(JetUnitEntity.class, JetUnitEntity::new)
	};

	private static final ObjectIntMap<Class<? extends Entityc>> idMap = new ObjectIntMap<>();

	/**
	 * Internal function to flatmap {@code Class -> Prov} into an {@link Entry}.
	 *
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
	 *
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
		registerPayloadSource(UAWUnitType.class);
	}

	/**
	 * Retrieves the class ID for a certain entity type.
	 *
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

		aglovale = new UAWUnitType("aglovale") {{
			health = 500;
			hitSize = 18;
			speed = 2.5f;
			accel = 0.04f;
			drag = 0.016f;
			rotateSpeed = 5.5f;
			ammoType = new ItemAmmoType(Items.graphite);
			circleTarget = true;
			lowAltitude = true;

			faceTarget = flying = true;
			range = 28 * tilesize;
			maxRange = range;
			fallSpeed = 0.003f;
			spinningFallSpeed = 4;
			fallSmokeY = -10f;
			engineSize = 0;

			constructor = CopterUnitEntity::new;
			aiController = DynamicFlyingAI::new;

			rotors.add(
				new Rotor(name + "-blade") {{
					x = y = 0;
					rotorSpeed = -15f;
					bladeCount = 3;
				}}
			);
			weapons.add(new Weapon(missileSmallRed2) {{
				rotate = false;
				mirror = true;
				shootCone = 90;
				x = 5f;
				y = -3f;
				inaccuracy = 4;
				reload = 15;
				shoot.shots = 2;
				shoot.shotDelay = 5f;
				shootSound = Sounds.missile;
				bullet = new MissileBulletType(6f, 45) {{
					width = 6f;
					height = 12f;
					shrinkY = 0f;
					drag = -0.003f;
					homingRange = 60f;
					keepVelocity = false;
					despawnHit = true;
					splashDamageRadius = 25f;
					splashDamage = 16f;
					lifetime = range / speed;
					backColor = Pal.bulletYellowBack;
					frontColor = Pal.bulletYellow;
					shootEffect = Fx.shootSmallSmoke;
					hitEffect = Fx.blastExplosion;
					trailChance = 0.9f;
					shootCone = 90;
					weaveMag = 5;
					weaveScale = 5;
					ammoMultiplier = 4f;
				}};
			}});
			weapons.add(new Weapon(machineGunSmallRed) {{
				layerOffset = -0.01f;
				rotate = false;
				mirror = true;
				shootCone = 90;
				inaccuracy = 3f;
				x = 8f;
				y = 3.5f;
				reload = 5f;
				shootSound = Sounds.shoot;
				ejectEffect = Fx.casing1;
				bullet = new TrailBulletType(6f, 23) {{
					trailLenghtScl = 1;
					height = 12f;
					width = 6f;
					pierce = true;
					pierceCap = 2;
					buildingDamageMultiplier = 0.4f;
					maxRange = range / 0.8f;
					homingRange = 60f;
					ammoMultiplier = 8f;
				}};
			}});
		}};
		bedivere = new UAWUnitType("bedivere") {{
			health = 3500;
			hitSize = 30;
			speed = 2.5f;
			rotateSpeed = 4.5f;
			accel = 0.08f;
			drag = 0.03f;
			ammoType = new ItemAmmoType(Items.graphite);
			faceTarget = flying = circleTarget = true;
			fallSpeed = 0.006f;
			spinningFallSpeed = 5f;
			fallSmokeY = -15f;
			lowAltitude = true;
			engineSize = 0;

			range = 32 * tilesize;
			maxRange = range;

			constructor = CopterUnitEntity::new;
			aiController = DynamicFlyingAI::new;

			rotors.add(
				new Rotor(name + "-blade") {{
					x = 0;
					y = 4;
					rotorSpeed = -12f;
					bladeCount = 4;
				}}
			);

			weapons.add(new Weapon(machineGunSmallRed) {{
				layerOffset = -0.005f;
				minShootVelocity = 0.75f;
				rotate = top = false;
				shootCone = 30;
				inaccuracy = 5f;
				alternate = mirror = true;
				x = 7f;
				y = 11f;
				reload = 4;
				recoil = 0f;
				shootSound = Sounds.shoot;
				ejectEffect = Fx.casing1;
				bullet = new TrailBulletType(10f, 20) {{
					trailLenghtScl = 1;
					height = 10f;
					width = 6f;
					pierce = true;
					pierceCap = 2;
					buildingDamageMultiplier = 0.4f;
					maxRange = range - 8;
					homingPower = 0.02f;
					lifetime = (range / speed) * 0.8f;
					trailColor = backColor;
					hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.shootSmallSmoke);
					ammoMultiplier = 8f;
				}};
			}});
			weapons.add(new Weapon(artilleryMediumRed) {{
				minShootVelocity = 0.75f;
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
					lifetime = (range / speed) * 1.5f;
					shootEffect = Fx.shootBig;
					hitEffect = new MultiEffect(Fx.blastExplosion, Fx.flakExplosionBig);
					hitSound = Sounds.boom;
					frontColor = Pal.plastaniumFront;
					backColor = Pal.plastaniumBack;
					splashDamage = 16f;
					splashDamageRadius = 2 * tilesize;
					ammoMultiplier = 6f;
				}};
			}});
			weapons.add(new MissileLauncherWeapon() {{
				layerOffset = -0.01f;
				missileSizeScl = 1f;
				rotate = false;
				mirror = true;
				alternate = false;
				shootCone = 30;
				x = 8.5f;
				y = 3.5f;
				maxRange = range;
				reload = 2f * 60;
				shootSound = UAWSfx.missileShootBig1;
				bullet = new CruiseMissileBulletType(3f, 450) {{
					layer = Layer.flyingUnitLow - 1;
					sizeScl = 1.3f;
					homingRange = range * 2;
					homingPower = 0.05f;
					keepVelocity = false;
					splashDamageRadius = 8 * tilesize;
					lifetime = (range - 5) / speed;
					shootEffect = UAWFxS.shootPyraFlame;
					hitEffect = UAWFxD.dynamicExplosion(splashDamageRadius);
					trailColor = Pal.lightPyraFlame;
					status = StatusEffects.burning;
					statusDuration = 4 * 60;
					ammoMultiplier = 2f;
					collidesGround = false;
				}};
			}});

			abilities.add(new RazorRotorAbility(25, 10f, 4.5f * tilesize) {
			});
		}};
		calogrenant = new UAWUnitType("calogrenant") {{
			health = 7500;
			armor = 10f;
			speed = 3f;
			drag = 0.07f;
			accel = 0.03f;
			flying = true;
			hitSize = 35f;
			range = 40 * tilesize;
			lowAltitude = true;
			rotateSpeed = 2.7f;
			engineSize = 0;

			constructor = CopterUnitEntity::new;
			aiController = DynamicFlyingAI::new;

			weapons.add(new PointDefenseWeapon(pointDefenseRed) {{
				mirror = true;
				x = 23.5f;
				y = 4f;
				reload = 3f;
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
			weapons.add(new Weapon(machineGunSmallRed) {{
				layerOffset = -0.01f;
				rotate = false;
				mirror = true;
				shootCone = 90;
				inaccuracy = 12f;
				x = 4.75f;
				y = 20.75f;
				reload = 2f;
				shootSound = Sounds.shoot;
				ejectEffect = Fx.casing1;
				bullet = new BasicBulletType(7f, 15) {{
					height = 12f;
					width = 6f;
					pierce = true;
					pierceCap = 2;
					buildingDamageMultiplier = 0.4f;
					maxRange = range;
					homingRange = 60f;
					lifetime = (range / speed);
					ammoMultiplier = 8f;
				}};
			}});
			weapons.add(new Weapon(machineGunMediumRed) {{
				layerOffset = -0.01f;
				rotate = false;
				inaccuracy = 4f;
				mirror = true;
				shootCone = 30f;
				x = 10.5f;
				y = 12.5f;
				reload = 8;
				shootSound = Sounds.shootBig;
				ejectEffect = Fx.casing2;
				bullet = new TrailBulletType(6f, 45) {{
					trailLenghtScl = 1;
					height = 16f;
					width = 8f;
					splashDamage = damage;
					splashDamageRadius = 16;
					frontColor = Pal.missileYellow;
					backColor = Pal.missileYellowBack;
					buildingDamageMultiplier = 0.3f;
					lifetime = (range * 0.75f) / speed;
					status = StatusEffects.burning;
					hitEffect = new MultiEffect(Fx.blastExplosion, Fx.fireHit, Fx.blastsmoke);
					ammoMultiplier = 8f;
					fragBullets = 3;
					fragBullet = fragGlassFrag;
				}};
			}});
			weapons.add(new MissileLauncherWeapon(cruiseMissileMount1) {{
				missileName = cruiseMissileCryo;
				rotate = false;
				mirror = true;
				layerOffset = 0.1f;
				x = 10.25f;
				y = -3.5f;
				reload = 5 * tick;
				shootSound = UAWSfx.missileShootBig1;
				bullet = new CruiseMissileBulletType(3f, 700) {{
					sprite = cruiseMissileCryo;
					homingRange = range;
					homingPower = 0.05f;
					keepVelocity = false;
					splashDamageRadius = 12 * tilesize;
					lifetime = range / speed;
					shootEffect = UAWFxS.shootCryoFlame;
					trailColor = UAWPal.cryoFront;
					despawnHit = true;
					hitEffect = UAWFxD.dynamicExplosion(splashDamageRadius, UAWPal.cryoMiddle, UAWPal.cryoBack);
					trailColor = UAWPal.cryoMiddle;
					status = StatusEffects.freezing;
					statusDuration = 4 * 60;
				}};
			}});

			float rotX = 17;
			float rotY = 5;
			float rotSpeed = 13f;
			rotors.add(
				new Rotor(modName + "short-blade") {{
					x = -rotX;
					y = rotY;
					rotorSpeed = rotSpeed;
					bladeCount = 3;
					doubleRotor = true;
				}},
				new Rotor(modName + "short-blade") {{
					x = rotX;
					y = rotY;
					rotorSpeed = rotSpeed;
					bladeCount = 3;
					doubleRotor = true;
				}}
			);
		}};

		corsair = new UAWUnitType("corsair") {{
			health = 650;
			hitSize = 20;
			speed = 3.2f;
			accel = 0.1f;
			drag = 0.015f;
			rotateSpeed = 7f;
			ammoType = new ItemAmmoType(Items.blastCompound);
			circleTarget = true;

			flying = true;
			lowAltitude = false;
			faceTarget = false;
			range = 45 * tilesize;

			targetFlags = new BlockFlag[]{BlockFlag.repair, BlockFlag.turret, BlockFlag.generator, BlockFlag.extinguisher, null};

			waveTrailX = 6f;
			waveTrailY = -1;
			trailLength = 10;
			trailScl = 1.75f;

			engineSize = 3f;
			engineOffset = 3f;
			setEnginesMirror(
				new UnitEngine(38 / 4f, -46 / 4f, 3f, 315f)
			);

			constructor = JetUnitEntity::new;
			aiController = DynamicFlyingAI::new;

			weapons.add(
				new Weapon() {{
					minShootVelocity = 0.55f;
					x = 3f;
					reload = 3f * 60;
					shootCone = 45f;
					ejectEffect = Fx.none;
					inaccuracy = 24f;
					shootSound = Sounds.shotgun;
					shoot.shots = 2;
					shoot.shotDelay = 15f;
					bullet = new SplashArtilleryBulletType(5f, 125) {{
						buildingDamageMultiplier = 2.5f;
						lifetime = range / speed;
						trailMult = 0.5f;
						height = 25f;
						width = 25f;
						shootEffect = Fx.none;
						smokeEffect = Fx.none;
						hitShake = 5f;
						splashDamageRadius = 6 * tilesize;
						hitEffect = UAWFxD.dynamicExplosion(splashDamageRadius);
						hitSound = Sounds.explosion;
						status = StatusEffects.blasted;
						statusDuration = 60f;
						homingPower = 0.1f;
						homingRange = 8f * tilesize;
						fragBullet = fragGlassFrag;
						drag = 0.001f;

						keepVelocity = false;
					}};
				}},
				new Weapon() {{
					rotate = false;
					mirror = false;
					shootCone = 90;
					inaccuracy = 3f;
					x = 0f;
					y = 0f;
					reload = 6f;
					shootSound = Sounds.shoot;
					ejectEffect = Fx.casing1;
					bullet = new TrailBulletType(6f, 15) {{
						height = 10f;
						width = 5f;
						buildingDamageMultiplier = 0.4f;
						lifetime = (range / speed);
						ammoMultiplier = 8f;
						collidesGround = false;
						trailLenghtScl = 1;
					}};
				}}
			);
		}};

		arquebus = new UnitType("arquebus") {{
			health = 750;
			speed = 0.75f;
			accel = 0.2f;
			rotateSpeed = 1.8f;
			drag = 0.05f;
			hitSize = 20;
			armor = 5;
			range = 30 * tilesize;
			maxRange = range;
			faceTarget = false;
			ammoType = new ItemAmmoType(Items.graphite, 2);

			trailLength = 22;
			waveTrailX = 7f;
			waveTrailY = -9f;
			trailScl = 1.4f;

			constructor = UnitWaterMove::create;

			weapons.add(new PointDefenseWeapon(pointDefensePurple) {{
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
			weapons.add(new Weapon(machineGunSmallPurple) {{
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
					lifetime = 20f;
					backColor = Pal.gray;
					frontColor = Color.white;
					despawnEffect = Fx.none;
					collidesGround = false;
				}};
			}});
			weapons.add(new Weapon(artillerySmallPurple) {{
				mirror = rotate = alternate = true;
				x = 5.5f;
				y = -8f;
				targetFlags = new BlockFlag[]{BlockFlag.turret, BlockFlag.extinguisher, BlockFlag.repair, BlockFlag.core};
				inaccuracy = 8f;
				shootCone = 30;
				rotateSpeed = 2.2f;
				reload = 1.2f * 60;
				recoil = 2.2f;
				shootSound = Sounds.artillery;
				shake = 2.5f;
				shootStatusDuration = reload * 1.2f;
				shootStatus = StatusEffects.unmoving;
				ejectEffect = Fx.casing3;
				bullet = new SplashArtilleryBulletType(2f, 35) {{
					splashDamageRadius = 4 * tilesize;
					buildingDamageMultiplier = 2f;
					height = 15;
					width = height / 1.8f;
					lifetime = range / speed;
					trailMult = 0.9f;
					hitShake = 4.5f;

					frontColor = Pal.sapBullet;
					backColor = Pal.sapBulletBack;
					shootEffect = new MultiEffect(Fx.shootBig2, UAWFxS.shootSporeFlame);
					smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.shootLiquid);
					despawnEffect = hitEffect = new MultiEffect(
						UAWFxD.dynamicExplosion(splashDamageRadius)
					);
				}};
			}});
		}};
		carronade = new UnitType("carronade") {{
			health = 7000;
			hitSize = 22;
			speed = 0.8f;
			accel = 0.20f;
			rotateSpeed = 1.5f;
			faceTarget = false;
			range = 40 * tilesize;
			maxRange = range;
			ammoType = new ItemAmmoType(Items.graphite, 2);

			trailLength = 35;
			waveTrailX = 9f;
			waveTrailY = -15f;
			trailScl = 2f;

			constructor = UnitWaterMove::create;

			weapons.add(
				new PointDefenseWeapon(pointDefensePurple) {{
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
				new Weapon(machineGunSmallPurple) {{
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
				new Weapon(missileLargePurple1) {{
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
					ejectEffect = new MultiEffect(Fx.casing3Double, Fx.casing3Double, Fx.casing3Double);
					bullet = new SplashArtilleryBulletType(1.8f, 90) {{
						splashDamageRadius = 5 * tilesize;
						buildingDamageMultiplier = 2f;
						height = 24;
						width = height / 2;
						lifetime = range / speed;
						status = StatusEffects.burning;
						incendChance = 0.8f;
						incendSpread = 16f;
						hitShake = 6f;
						makeFire = true;
						frontColor = Pal.sapBullet;
						backColor = Pal.sapBulletBack;
						trailMult = 1.5f;
						shootEffect = new MultiEffect(Fx.shootBig2, UAWFxS.shootSporeFlame);
						smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.shootLiquid);
						despawnEffect = hitEffect = new MultiEffect(
							UAWFxD.dynamicExplosion(splashDamageRadius)
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
			health = 16000;
			hitSize = 44;
			speed = 0.65f;
			drag = 0.17f;
			accel = 0.2f;
			rotateSpeed = 1f;
			faceTarget = false;
			range = 55 * tilesize;
			maxRange = range;
			ammoType = new ItemAmmoType(Items.thorium, 2);

			trailLength = 50;
			waveTrailX = 18f;
			waveTrailY = -15f;
			trailScl = 3.2f;

			constructor = UnitWaterMove::create;
			forceMultiTarget = true;

			weapons.add(new PointDefenseWeapon(pointDefensePurple) {{
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
			weapons.add(new PointDefenseWeapon(pointDefensePurple) {{
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
			weapons.add(new Weapon(machineGunMediumPurple) {{
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
					maxRange = range - 16;
					lifetime = (range / speed) / 2;
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
			weapons.add(new UAWWeapon(artilleryLargePurple) {{
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
				recoil = 5f;
				shootSound = UAWSfx.cannonShootBig1;
				shake = 16;
				shootStatusDuration = reload * 1.2f;
				shootStatus = StatusEffects.unmoving;
				ejectEffect = UAWFxS.casing5;

				customIcon = true;
				bullet = new SplashArtilleryBulletType(1.7f, 550) {{
					height = 42;
					width = height / 2f;
					splashDamageRadius = 12 * tilesize;
					buildingDamageMultiplier = 3.5f;
					lifetime = range / speed;
					status = StatusEffects.burning;
					incendChance = 0.8f;
					incendSpread = 16f;
					makeFire = true;
					hitSound = UAWSfx.explosionHuge1;
					trailMult = 1f;
					hitShake = 15f;
					frontColor = Pal.sapBullet;
					backColor = Pal.sapBulletBack;
					shootEffect = new MultiEffect(
						UAWFxS.shootSporeFlame,
						UAWFxD.instShoot(65, Pal.sapBullet),
						UAWFxD.shootBigSmoke(5, backColor)
					);
					smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.shootLiquid);
					despawnHit = true;
					hitEffect = UAWFxD.dynamicExplosion(splashDamageRadius, frontColor, backColor);
					fragBullets = 1;
					fragBullet = new SplashBulletType(splashDamage / 2, splashDamageRadius / 1.2f) {{
						splashAmount = 5;
						splashDelay = 60;
						buildingDamageMultiplier = 3.5f;
						lifetime = (splashDelay * splashAmount);
						frontColor = Pal.sapBullet;
						backColor = Pal.sapBulletBack;
						status = StatusEffects.melting;
						statusDuration = 30f;
						particleEffect = new MultiEffect(Fx.sporeSlowed, Fx.fire);
						makeFire = true;
						applySound = Sounds.flame2;
					}};
				}};
			}});
		}};

		seabass = new UnitType("seabass") {{
			health = 650;
			speed = 1.2f;
			accel = 0.2f;
			rotateSpeed = 1.9f;
			drag = 0.05f;
			hitSize = 18;
			range = 35 * tilesize;
			maxRange = range;
			faceTarget = false;
			ammoType = new ItemAmmoType(Items.graphite, 2);

			trailLength = 20;
			waveTrailX = 6f;
			waveTrailY = -4f;
			trailScl = 1.9f;

			constructor = UnitWaterMove::create;

			weapons.add(new Weapon() {{
				maxRange = range;
				rotate = false;
				alternate = mirror = false;
				shootCone = 180;
				x = 0f;
				y = 6f;
				reload = 4f * 60;
				inaccuracy = 1f;
				ammoType = new ItemAmmoType(Items.thorium);
				targetAir = false;

				shootSound = UAWSfx.torpedoShoot1;

				bullet = new TorpedoBulletType(1.8f, 450) {{
					shootEffect = UAWFxS.shootWaterFlame;
					lifetime = range / speed;
					homingRange = range;
					hitSizeDamageScl = 3.5f;
					maxEnemyHitSize = 35;
				}};
			}});
			weapons.add(new PointDefenseWeapon(pointDefenseRed) {{
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
					maxRange = range / 3.5f;
					damage = 15f;
				}};
			}});
		}};
		sharpnose = new UnitType("sharpnose") {{
			health = 5500;
			speed = 1f;
			accel = 0.2f;
			rotateSpeed = 1.8f;
			drag = 0.17f;
			hitSize = 22f;
			armor = 5f;
			faceTarget = false;
			range = 45 * tilesize;
			maxRange = range;
			ammoType = new ItemAmmoType(Items.graphite, 2);

			trailLength = 25;
			waveTrailX = 8f;
			waveTrailY = -9f;
			trailScl = 2.2f;

			constructor = UnitWaterMove::create;

			weapons.add(new Weapon() {{
				maxRange = range;
				rotate = false;
				alternate = mirror = true;
				shootCone = 180;
				x = 8f;
				y = 6f;
				reload = 3f * 60;
				inaccuracy = 1f;
				ammoType = new ItemAmmoType(Items.thorium);
				targetAir = false;

				shootSound = UAWSfx.torpedoShoot1;

				bullet = new TorpedoBulletType(1.8f, 650) {{
					shootEffect = UAWFxS.shootWaterFlame;
					lifetime = range / speed;
					homingRange = range;
					maxEnemyHitSize = 45;
					hitSizeDamageScl = 4;
				}};
			}});

			weapons.add(new PointDefenseWeapon(pointDefenseRed) {{
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
					maxRange = range / 3f;
					damage = 15f;
				}};
			}});
			weapons.add(new Weapon(machineGunMediumRed) {{
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
					maxRange = range - 16;
					lifetime = (range / speed) * 0.7f;
					trailLength = 10;
					trailWidth = width / 3;
					trailColor = backColor;
					hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.shootSmallSmoke);
				}};
			}});
			weapons.add(new Weapon(missileMediumRed2) {{
				reload = 45f;
				x = 0f;
				y = -8f;
				rotateSpeed = 4f;
				rotate = true;
				mirror = false;
				shoot.shots = 2;
				shoot.shotDelay = 6f;
				inaccuracy = 5f;
				velocityRnd = 0.1f;
				shootSound = Sounds.missile;
				ammoType = new ItemAmmoType(Items.thorium);
				ejectEffect = Fx.none;
				bullet = new MissileBulletType(5f, 60) {{
					height = 12;
					width = height / 2;
					homingRange = 60f;
					keepVelocity = false;
					splashDamageRadius = 4 * tilesize;
					splashDamage = damage * 1.5f;
					lifetime = range / speed * 0.8f;
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

		cavalier = new UAWUnitType("cavalier") {{
			health = 750;
			armor = 18;
			hitSize = 16;
			speed = 1.5f;
			accel = 0.04f;
			drag = 0.04f;
			rotateSpeed = 2f;
			baseRotateSpeed = rotateSpeed * 2.5f;
			ammoType = new ItemAmmoType(Items.graphite);
			singleTarget = true;

			range = 30 * tilesize;
			groundTrailInterval = 0.95f;
			groundTrailSpacing = 2.6f;

			terrainSpeedMultiplier = 1.2f;

			immunities = ObjectSet.with(StatusEffects.disarmed, UAWStatusEffects.EMP, StatusEffects.freezing);
			constructor = TankUnitEntity::new;

			weapons.add(new Weapon(pointDefenseRed) {{
				ignoreRotation = true;
				mirror = false;
				reload = 5f;
				recoil = 0f;
				x = 3f;
				y = -2f;
				rotate = true;
				ejectEffect = Fx.casing1;
				inaccuracy = 12f;
				bullet = new BasicBulletType(4, 12) {{
					height = 10;
					width = 7f;
					lifetime = (range * 0.5f) / speed;
					maxRange = range * 0.5f;
					shootEffect = Fx.shootBigSmoke;
				}};
			}});
			weapons.add(new TankWeapon(name + "-gun") {{
								layerOffset = -0.1f;
								targetFlags = new BlockFlag[]{BlockFlag.extinguisher, null};
								x = 0f;
								y = 0f;
								shootY = 16f;
								reload = 1.5f * 60;
								recoil = 4.5f;
								shootSound = Sounds.shootBig;
								ejectEffect = UAWFxS.casing2Long;
								shake = 6f;
								soundPitchMin = 1.2f;
								soundPitchMax = 1.4f;
								bullet = new TrailBulletType(5f, 125) {{
									trailLenghtScl = 1;
									splashDamage = damage;
									splashDamageRadius = 3 * tilesize;
									frontColor = Pal.missileYellow;
									backColor = Pal.missileYellowBack;
									height = 25f;
									width = 8f;
									lifetime = range / speed;
									knockback = 4f;
									despawnHit = true;
									shootEffect = new MultiEffect(UAWFxD.railShoot(22f, backColor), Fx.shootPyraFlame, Fx.shootSmallSmoke);
									hitEffect = new MultiEffect(Fx.blastExplosion, Fx.fireHit, Fx.blastsmoke);
									fragBullets = 8;
									collidesAir = false;
									fragBullet = fragGlassFrag;
								}};
							}}
			);
		}};
		centurion = new UAWUnitType("centurion") {{
			health = 7250;
			armor = 30;
			hitSize = 25;
			speed = 1f;
			accel = 0.04f;
			drag = 0.04f;
			rotateSpeed = 1.3f;
			baseRotateSpeed = rotateSpeed * 2.2f;
			ammoType = new ItemAmmoType(Items.graphite);
			singleTarget = true;

			range = 40 * tilesize;
			maxRange = range;
			drawCell = false;

			groundTrailSpacing = 5;
			groundTrailY = -7;
			groundTrailSize = 1.3f;
			groundTrailInterval = 0.6f;

			terrainSpeedMultiplier = 1.4f;

			immunities = ObjectSet.with(StatusEffects.disarmed, UAWStatusEffects.EMP, StatusEffects.freezing);
			constructor = TankUnitEntity::new;

			weapons.add(new Weapon(machineGunMediumRedMirrored) {{
				ignoreRotation = true;
				layerOffset = 1.5f;
				rotate = true;
				mirror = false;
				reload = 5f;
				recoil = 0.5f;
				recoilTime = 2f;
				shootCone = 10f;
				x = -5f;
				y = -2.5f;
				ejectEffect = Fx.casing2;
				predictTarget = false;
				inaccuracy = 10f;
				bullet = new BasicBulletType(4, 15) {{
					height = 14;
					width = 9f;
					lifetime = (range * 0.5f) / speed;
					maxRange = range * 0.5f;
					shootEffect = Fx.shootBigSmoke;
				}};
			}});
			weapons.add(new MissileLauncherWeapon(cruiseMissileMount1) {{
				rotate = top = mirror = false;
				layerOffset = 1;
				x = 11.5f;
				y = -0f;
				reload = 8 * 60f;
				shootSound = UAWSfx.missileShootBig1;
				bullet = new CruiseMissileBulletType(3f, 450) {{
					homingRange = range;
					homingPower = 0.04f;
					splashDamageRadius = 8 * tilesize;
					lifetime = (range - 5) / speed;
					shootEffect = new MultiEffect(
						UAWFxS.shootPyraFlame,
						Fx.sparkShoot
					);
					hitEffect = UAWFxD.dynamicExplosion(splashDamageRadius);
					trailColor = Pal.lightPyraFlame;
					status = StatusEffects.blasted;
					statusDuration = 4 * 60;
					ammoMultiplier = 2f;
				}};
			}});
			weapons.add(new TankWeapon(name + "-gun") {{
				layerOffset = -1.5f;
				x = 0f;
				y = 0f;
				shootY = 26f;
				reload = 2 * 60;
				recoil = 2.5f;
				shootSound = UAWSfx.cannonShoot1;
				ejectEffect = UAWFxS.casing3Long;
				shake = 12f;
				soundPitchMin = 1.4f;
				soundPitchMax = 1.8f;
				bullet = new TrailBulletType(10f, 350) {{
					trailLenghtScl = 1;
					height = 35f;
					width = 10f;
					homingPower = 0.0015f;
					homingRange = 8 * tilesize;
					lifetime = range / speed;
					pierce = true;
					pierceCap = 3;
					knockback = 5f;
					trailColor = backColor;
					pierceArmor = true;

					shootEffect = new MultiEffect(UAWFxD.railShoot(30f, backColor), Fx.shootPyraFlame, UAWFxS.muzzleBreakShootSmoke);
					hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.shootBigSmoke2);
					fragBullets = 5;
					fragLifeMin = 0f;
					fragRandomSpread = 30f;
					collidesAir = false;
					despawnHit = true;
					fragBullet = new BasicBulletType(7f, 9) {{
						buildingDamageMultiplier = 1.5f;
						height = width = 8f;
						pierce = true;
						pierceBuilding = true;
						pierceCap = 3;
						lifetime = 30f;
						hitEffect = Fx.flakExplosion;
						splashDamage = damage;
						splashDamageRadius = 8f;
						hittable = false;
					}};
				}};
			}});
		}};
		caernarvon = new UAWUnitType("caernarvon") {{
			health = 12500;
			armor = 45;
			hitSize = 32;
			speed = 1.2f;
			accel = 0.04f;
			drag = 0.04f;
			rotateSpeed = 1.5f;
			baseRotateSpeed = rotateSpeed * 2.2f;
			ammoType = new ItemAmmoType(UAWItems.compositeAlloy);

			range = 50 * tilesize;
			maxRange = range;
			drawCell = false;

			groundTrailSpacing = 10;
			groundTrailY = -15.5f;
			groundTrailSize = 0.9f;
			groundTrailInterval = 0.7f;

			terrainSpeedMultiplier = 1.6f;

			immunities = ObjectSet.with(StatusEffects.disarmed, UAWStatusEffects.EMP, StatusEffects.freezing);
			constructor = TankUnitEntity::new;

			weapons.add(new Weapon(machineGunMediumRed) {{
				ignoreRotation = true;
				layerOffset = 1.5f;
				rotate = true;
				mirror = false;
				reload = 4f;
				recoil = 0.5f;
				recoilTime = 2f;
				shootCone = 10f;
				x = 7.5f;
				y = 3.25f;
				ejectEffect = Fx.casing2;
				shootSound = Sounds.shoot;
				predictTarget = false;
				inaccuracy = 5f;
				bullet = new BasicBulletType(6, 40) {{
					height = 15;
					width = 10f;
					lifetime = (range * 0.6f) / speed;
					maxRange = range * 0.6f;
					shootEffect = Fx.shootBig2;
					smokeEffect = Fx.shootBigSmoke2;
				}};
			}});
			weapons.add(new PointDefenseWeapon(pointDefenseRed) {{
				ignoreRotation = true;
				mirror = false;
				x = -6f;
				y = 3.5f;
				reload = 4.5f;
				targetInterval = 5f;
				targetSwitchInterval = 7.5f;
				color = Pal.bulletYellow;

				bullet = new BulletType() {{
					shootEffect = Fx.sparkShoot;
					hitEffect = Fx.pointHit;
					maxRange = 100f;
					damage = 35f;
				}};
			}});
			weapons.add(new MissileLauncherWeapon(cruiseMissileMount2) {{
				mirror = false;
				rotate = true;
				layerOffset = 0.1f;
				x = 0f;
				y = -4.75f;
				reload = 7 * 60f;
				shootSound = UAWSfx.missileShootBig1;
				rotateSpeed = 1.2f;
				bullet = new CruiseMissileBulletType(3f, 600) {{
					homingRange = range;
					homingPower = 0.04f;
					splashDamageRadius = 8 * tilesize;
					lifetime = (range - 5) / speed;
					shootEffect = new MultiEffect(
						UAWFxS.shootPyraFlame,
						Fx.sparkShoot
					);
					hitEffect = UAWFxD.dynamicExplosion(splashDamageRadius);
					status = StatusEffects.blasted;
					statusDuration = 4 * 60;
					ammoMultiplier = 2f;
				}};
			}});
			weapons.add(new TankWeapon(name + "-gun") {{
				layerOffset = -1.5f;
				x = 0f;
				y = 0f;
				recoil = 7f;
				shootY = 163f * px;
				reload = 3 * tick;
				shootSound = UAWSfx.cannonShoot2;
				ejectEffect = UAWFxS.casing4Long;
				shake = 18f;
				soundPitchMin = 1.8f;
				soundPitchMax = 2.2f;
				bullet = new TrailBulletType(10f, 550) {{
					trailLenghtScl = 1;
					height = 38f;
					width = 12f;
					homingPower = 0.0015f;
					homingRange = 8 * tilesize;
					lifetime = range / speed;
					pierceCap = 5;
					trailColor = backColor;
					pierceArmor = true;
					absorbable = false;
					buildingDamageMultiplier = 0.2f;
					shootEffect = new MultiEffect(UAWFxD.railShoot(40f, backColor), Fx.shootPyraFlame, UAWFxS.muzzleBreakShootSmoke);
					hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.shootBigSmoke2, Fx.railHit);
					hitSound = Sounds.shootBig;
					collidesAir = false;
					despawnHit = true;
					trailInterval = 2f;
					trailEffect = new Effect(20f, e -> {
						color(backColor);
						for (int s : Mathf.signs) {
							Drawf.tri(e.x, e.y, 5.5f, 45f * e.fslope(), e.rotation + 135f * s);
						}
					});
				}};
			}});
		}};
	}
}
