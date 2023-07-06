package UAW.content;

import UAW.ai.types.DynamicFlyingAI;
import UAW.audiovisual.*;
import UAW.audiovisual.effects.*;
import UAW.entities.abilities.RazorRotorAbility;
import UAW.entities.bullet.*;
import UAW.entities.bullet.presets.StandardBullets;
import UAW.entities.part.MissilePart;
import UAW.entities.units.*;
import UAW.entities.units.entity.*;
import UAW.type.Rotor;
import UAW.type.weapon.*;
import arc.func.Prov;
import arc.graphics.Color;
import arc.math.*;
import arc.math.geom.Rect;
import arc.struct.*;
import arc.struct.ObjectMap.Entry;
import mindustry.ai.types.FlyingAI;
import mindustry.content.*;
import mindustry.entities.abilities.MoveEffectAbility;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.type.weapons.PointDefenseWeapon;
import mindustry.world.meta.BlockFlag;

import static UAW.Vars.px;
import static UAW.Vars.*;
import static UAW.audiovisual.Assets.*;
import static UAW.content.UAWBullets.fragGlassFrag;
import static UAW.entities.Calc.px;
import static UAW.entities.Calc.*;
import static mindustry.Vars.tilesize;

@SuppressWarnings("unchecked")
public class UAWUnitTypes {
	public static UnitType placeholder,


	// Air - Helicopters
	crotchety,
		aglovale, bedivere, calogrenant, dagonet, esclabor,

	// Air - Carriers | Cryocopters
	cantankerous, illustrious, indefatigable, indomitable,

	// Naval - Monitor
	medea, melpomene, medusa, minerva,

	// Naval - Torpedo Boats
	mtb72, mtb96,

	// Tanks - MBT
	cavalier, centurion, caernarvon, challenger,

	// Tanks- Flamer
	cavalierF, centurionF, caernarvonF;


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

	/**
	 * Retrieves the class ID for a certain entity type.
	 * @author GlennFolker
	 */
	public static <T extends Entityc> int classID(Class<T> type) {
		return idMap.get(type, -1);
	}

	public static void load() {
		setupID();

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
					rotorTopSizeScl = 0.8f;
				}}
			);

			float mgRange = unitRange * 0.60f;
			weapons.add(
				// Wings
				new Weapon(U_WP_machinegun_S_01_red) {{
					rotate = true;
					rotationLimit = 0.1f;
					layerOffset = -0.01f;
					shootCone = 60;
					inaccuracy = 3f;
					x = 31f * px;
					y = 12 * px;
					reload = 4f;
					shootSound = Sfx.wp_k_gunShootSmall_2;
					ejectEffect = Fx.casing1;
					bullet = new StandardBullets(5f, 6.5f, 0) {{
						pierceCap = 2;
						buildingDamageMultiplier = 0.4f;
						lifetime = bulletLifetime(mgRange, this.speed);
						homingRange = 60f;
						ammoMultiplier = 8f;
					}};
				}},
				new Weapon(U_WP_missile_S_01_red) {{
					rotate = false;
					shootCone = 90;
					inaccuracy = 3f;
					x = 24 * px;
					y = -14 * px;
					reload = 1.5f * tick;
					shootSound = Sounds.missileSmall;
					recoil = 0.75f;

					shoot = new ShootPattern() {{
						shots = 4;
						shotDelay = 15 * 0.25f;
					}};
					bullet = new TrailBulletType(4f, 16) {{
						sprite = "missile";
						splashDamageRadius = 4 * tilesize;
						splashDamage = this.damage * 1.5f;
						buildingDamageMultiplier = 0.5f;

						height = width = 9f;
						frontColor = Pal.missileYellow;
						backColor = Pal.missileYellowBack;
						shrinkY = 0f;

						lifetime = unitRange * 1.25f / this.speed;

						shootEffect = Fx.shootSmall;
						smokeEffect = Fx.shootSmallSmoke;
						hitEffect = Fx.blastExplosion;
						despawnEffect = Fx.blastExplosion;

						homingRange = unitRange * 0.5f;
						homingPower = 0.05f;
						homingDelay = 0.25f * tick;

						trailLengthScale = 1.2f;
						trailWidthScale = 0.2f;
						weaveMag = 3;
						weaveScale = 9.5f;

						status = StatusEffects.blasted;
						statusDuration = 60f;
					}};
				}}
			);
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

			weapons.add(new Weapon(U_WP_machinegun_S_01_red) {{
				layerOffset = -0.005f;
				rotate = top = false;
				shootCone = 30;
				inaccuracy = 5f;
				alternate = mirror = true;
				x = px(29);
				y = px(44);
				reload = 2.5f;
				recoil = 0f;
				shootSound = Sfx.wp_k_gunShootSmall_1;
				ejectEffect = Fx.casing1;

				bullet = new StandardBullets(10f, 22, 1) {{
					pierceCap = 4;
					buildingDamageMultiplier = 0.4f;
					lifetime = bulletLifetime(unitRange, this.speed, 0.8f);
					ammoMultiplier = 8f;
				}};
			}});
			weapons.add(new Weapon(U_WP_artillery_S_02_red) {{
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
			weapons.add(new Weapon() {{
				layerOffset = -0.01f;
				mirror = true;
				alternate = false;
				shootCone = 30;
				x = px(33);
				y = px(14);

				minWarmup = 0.9f;
				shootWarmupSpeed = 0.05f;

				reload = 2f * tick;
				shootSound = Sfx.wp_msl_missileLaunch_1_big;

				bullet = new BulletType() {{
					hitColor = Pal.lightPyraFlame;
					shootEffect = Fx.shootBigColor;
					smokeEffect = Fx.shootBigSmoke2;
					shake = 1f;
					speed = 0f;
					keepVelocity = false;

					spawnUnit = new CruiseMissileUnitType(name, U_MSL_crsmissile_M_01_red, 2) {{
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
							color = UAWPal.missileSmoke;
							effect = UAWFx.missileTrailSmoke(65, 5, 14);
							rotation = 180f;
							y = -9f;
							interval = 4f;
						}});
					}};
				}};

				parts.add(
					new MissilePart(U_MSL_crsmissile_M_01_red) {{
						moves.add(new PartMove(PartProgress.warmup, px(5), 0, 0));
					}}
				);
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
			rotateSpeed = 2.5f;

			lowAltitude = true;
			faceTarget = flying = true;
			range = unitRange;

			engineSize = 0;

			targetFlags = new BlockFlag[]{BlockFlag.turret, BlockFlag.extinguisher, BlockFlag.battery, null};

			constructor = CopterUnitEntity::new;
			aiController = FlyingAI::new;

			float rotX = px(73);
			float rotY = px(0);
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

			weapons.add(new Weapon(U_WP_machinegun_S_01_red) {{
				layerOffset = -0.01f;
				rotate = true;
				rotationLimit = 0.5f;
				mirror = true;
				shootCone = 90;
				inaccuracy = 0.5f;
				x = px(20);
				y = px(80);
				reload = 1.5f;
				shootSound = Sfx.wp_k_gunShoot_2;
				ejectEffect = Fx.casing1;
				bullet = new StandardBullets(7f, 12, 0) {{
					lifetime = bulletLifetime(unitRange, this.speed, 0.75f);
					ammoMultiplier = 8f;
				}};
			}});
			weapons.add(new Weapon(U_WP_machinegun_M_01_red) {{
				layerOffset = -0.01f;
				rotate = true;
				rotationLimit = 0.1f;
				inaccuracy = 4f;
				mirror = true;
				shootCone = 30f;
				x = px(41);
				y = px(51);
				reload = 7.5f;
				shootSound = Sounds.shootBig;
				ejectEffect = Fx.casing2;

				float bulletDamage = 70;
				bullet = new StandardBullets(6, bulletDamage, 2) {{
					Color front = Pal.missileYellow, back = Pal.missileYellowBack;
					splashDamage = damage;
					splashDamageRadius = 16;
					frontColor = Pal.missileYellow;
					backColor = Pal.missileYellowBack;
					buildingDamageMultiplier = 0.3f;
					lifetime = bulletLifetime(unitRange, this.speed);
					status = StatusEffects.melting;
					ammoMultiplier = 8f;
					hitEffect = new MultiEffect(Fx.blastExplosion, Fx.fireHit, Fx.blastsmoke);
					shootEffect = Fx.shootBigColor;
					smokeEffect = Fx.shootBigSmoke;

					fragOnHit = true;
					fragRandomSpread = 90f;
					fragBullets = 5;
					fragVelocityMin = 0.7f;
					fragVelocityMax = 1.4f;
					float fragSpeed = this.speed * 2;

					fragBullet = new TrailBulletType(fragSpeed, bulletDamage / fragBullets) {{
						height = 10;
						width = 4f;
						pierceCap = 4;
						pierce = true;
						pierceBuilding = true;
						trailEffect = new StatusHitEffect() {{
							life = 13;
							color = front;
							sizeEnd = 0.5f;
							shapeVariant = 2;
						}};
						trailLengthScale = 0.25f;
						hitEffect = UAWFx.sparkHit(7, 2, back);
						despawnEffect = new MultiEffect(
							UAWFx.hitBulletSmall(back)
						);
						lifetime = bulletLifetime(unitRange, fragSpeed, 0.2f);
						frontColor = front;
						backColor = back;

						pierceCap = 2;
					}};

				}};
			}});
			weapons.add(new Weapon(U_WP_missile_M_02_red) {{
				rotate = false;
				shootCone = 90;
				mirror = true;
				alternate = false;
				layerOffset = 0.01f;
				x = px(26);
				y = px(-25);
				reload = 5 * tick;
				shootSound = Sfx.wp_msl_missileLaunch_2_big;
				shoot.shots = 6;
				shoot.shotDelay = 2.5f;
				inaccuracy = 90f;

				bullet = new BulletType() {{
					shootEffect =
						new MultiEffect(
							Fx.shootBig,
							Fx.shootPyraFlame,
							Fx.hitBulletBig
						);
					smokeEffect = new MultiEffect(
						Fx.shootBigSmoke2,
						Fx.fireSmoke
					);
					shake = 5f;
					speed = 0f;
					keepVelocity = false;

					spawnUnit = new CruiseMissileUnitType(name, U_MSL_crsmissile_S_01_red, 1) {{
						Color front = Pal.missileYellow, back = Pal.missileYellowBack;
						health = 120;
						speed = 5.5f;
						exhaustColor = back;
						rotateSpeed = 2.5f;

						missileAccelTime = 35f;
						lifetime = missileLifetime(unitRange * 1.25f, this.speed, this.missileAccelTime);
						deathSound = Sounds.largeExplosion;
						range = maxRange = tilesize;

						weapons.add(new SuicideWeapon() {{
							bullet = new ShrapnelBulletType() {{
								shootEffect = smokeEffect = new MultiEffect(Fx.shootTitan, Fx.shootBigSmoke);
								killShooter = true;
								fromColor = front;
								toColor = back;
								length = (unitRange * 0.25f) * 0.5f;
								damage = 85f;
								width = 27f;

								serrationWidth = 6f;
								serrationSpacing = 12f;
							}};
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
			weapons.add(new Weapon(U_WP_machinegun_M_02_cryo) {{
				layerOffset = -0.01f;
				rotate = false;
				mirror = true;
				inaccuracy = 3f;
				x = 27 * px;
				y = 15 * px;
				reload = 60f;
				recoil = 6 * px;
				shootSound = Sfx.wp_lnch_springShoot_2;
				ejectEffect = Fx.casing3;
				bullet = new StatusEffectBulletType(UAWStatusEffects.cryoBurn, 3 * tick) {{
					lifetime = bulletLifetime(unitRange, this.speed);
					splashDamage = 40;
					splashDamageRadius = 4.5f * tilesize;
					frontColor = UAWPal.cryoFront;
					backColor = UAWPal.cryoBack;
					trailEffect = new MultiEffect(
						Fx.disperseTrail,
						new StatusHitEffect() {{
							life = 15f;
							color = frontColor;
							sizeEnd = 1.25f;
							shapeVariant = 2;
						}}
					);
					trailChance = 0.5f;
					shootEffect = Fx.shootBigColor;
					trailColor = hitColor = backColor;
					hitEffect = new MultiEffect(
						new ExplosionEffect() {{
							lifetime = 20f;
							waveLife = 15f;
							smokes = 32;
							sparks = 15;
							waveColor = UAWPal.cryoFront;
							sparkColor = UAWPal.cryoMiddle;
							waveRad = splashDamageRadius;
							smokeRad = splashDamageRadius * 0.8f;
						}},
						new SparkEffect() {{
							size = 4;
							life = 45;
							amount = 20;
							spreadRad = splashDamageRadius * 0.8f;
							color = frontColor;
						}}
					);
					smokeEffect = Fx.shootBigSmoke;
					shake = 2.2f;
				}};
			}});
		}};
		cantankerous = new AirshipUnitType("cantankerous") {{
			float unitRange = 35 * tilesize;
			health = 5250;
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
			float rotorScaling = 0.6f;
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
				shootSound = Sfx.wp_lnch_springShoot_2;
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
							sizeEnd = 2;
							shapeVariant = 2;
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
							life = 60;
							amount = 20;
							spreadRad = splashDamageRadius * 0.8f;
							color = frontColor;
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
			weapons.add(new Weapon(U_WP_missile_M_03_cryo) {{
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

					spawnUnit = new CruiseMissileUnitType(name, U_MSL_crsmissile_S_01_cryo, 1) {{
						health = 250;
						speed = 4.5f;
						maxRange = 4 * tilesize;
						lifetime = unitRange * 1.8f / this.speed;

						trailColor = exhaustColor = UAWPal.cryoBack;
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
							color = Color.grays(0.3f).cpy().lerp(UAWPal.cryoMiddle, 0.5f).a(0.4f);
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
		medea = new UnitType("medea") {{
			float unitRange = 35 * tilesize;
			health = 750;
			armor = 5;
			hitSize = 20;

			speed = 0.6f;
			accel = 0.2f;
			drag = 0.05f;
			rotateSpeed = 2.5f;

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

			weapons.add(new Weapon(name + "-weapon") {{
				layerOffset = 0.1f;
				rotate = true;
				mirror = false;
				x = 0;
				y = -20 * px;
				inaccuracy = 0.5f;
				reload = 3.5f * tick;

				rotateSpeed = 2.5f;
				rotationLimit = 270;
				shootY = 61 * px;
				minWarmup = 0.9f;
				shootWarmupSpeed = 0.05f;

				ejectEffect = Fx.casing3;
				shootSound = Sfx.wp_k_gunShoot_7;

				bullet = new TrailBulletType(13f, 80) {{
					Color front = Color.white, back = UAWPal.phlogistonMid;
					height = 13f * 1.2f;
					width = 7.5f * 1.2f;
					frontColor = front;
					backColor = back;
					recoil = 0.25f;
					lifetime = unitRange / this.speed;

					shootEffect = new MultiEffect(
						Fx.shootBig2,
						Fx.shootTitan
					);
					smokeEffect = Fx.shootBigSmoke;
					hitEffect = new MultiEffect(
						UAWFx.sparkHit(12f, 15, 6, back),
						Fx.flakExplosion
					);
					despawnEffect = UAWFx.hitBulletSmall(back);

					trailColor = back;
					trailInterval = 1.5f;
					trailChance = -1f;
					trailEffect = new MultiEffect(
						new StatusHitEffect() {{
							life = 25;
							amount = 6;
							color = back;
							color1 = Pal.gray.cpy().a(0.35f);
							sizeStart = 0.5f;
							sizeEnd = 1.25f;
							shapeVariant = 1;
						}}
					);

					splashDamage = this.damage * 0.6f;
					splashDamageRadius = 4 * tilesize;
					ammoMultiplier = 8;

					shake = 2.25f;
					hitShake = shake * 1.5f;

					fragBullets = 7;
					fragVelocityMin = 0.4f;
					fragVelocityMax = 0.8f;
					fragLifeMin = 0.5f;
					fragLifeMax = 1.2f;
					fragRandomSpread = 45f;

					fragBullet = new BasicBulletType(4.5f, 20) {{
						frontColor = front;
						backColor = back;

						pierce = true;
						pierceBuilding = true;
						pierceCap = 4;
						keepVelocity = false;

						hitSize = 7.5f;
						lifetime = (6 * tilesize) / this.speed;
						hitEffect = Fx.flakExplosion;

						splashDamage = this.damage * 0.8f;
						splashDamageRadius = 2f * tilesize;
						buildingDamageMultiplier = 3f;
					}};
				}};

				parts.add(
					new RegionPart("-bottom"),
					new RegionPart("-sides") {{
						float mX = 4 * px, mY = -6 * px, rot = 35;
						progress = PartProgress.warmup.curve(Interp.sineOut);
						mirror = true;
						moveRot = rot;
						moveX = mX;
						moveY = mY;

						moves.add(
							new PartMove(PartProgress.smoothReload.curve(Interp.sineIn), -(mX * 0.25f), -(mY * 0.25f), -(rot * 0.5f)),
							new PartMove(PartProgress.recoil.curve(Interp.smooth), -mX / 3, -mY / 3, -(rot * 0.25f))
						);
					}},
					new RegionPart("-gun") {{
						progress = PartProgress.recoil;
						moveY = -9 * px;
					}}
				);
			}});

		}};
		melpomene = new UnitType("melpomene") {{
			float unitRange = 45 * tilesize;
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
			targetFlags = new BlockFlag[]{BlockFlag.turret, BlockFlag.extinguisher, BlockFlag.repair, BlockFlag.core};

			Color front = Color.white.cpy(), back = UAWPal.phlogistonMid.cpy();
			weapons.add(new Weapon(U_WP_crsmissile_L_01_phlog) {{
				layerOffset = 0.1f;
				rotate = true;
				rotateSpeed = 1.2f;
				rotationLimit = 280;
				shootCone = 12f;
				mirror = false;
				recoil = 0;
				x = 0;
				y = -30 * px;
				shootY = -10 * px;
				inaccuracy = 0f;
				reload = 8 * tick;
				shootSound = Sfx.wp_msl_missileLaunch_2_big;

				minWarmup = 0.9f;
				shootWarmupSpeed = 0.025f;

				targetAir = false;

				bullet = new BulletType() {{
					shootEffect = Fx.shootBigColor;
					smokeEffect = Fx.shootBigSmoke2;
					shake = 1f;
					speed = 0f;
					keepVelocity = false;
					collidesAir = false;

					spawnUnit = new CruiseMissileUnitType(name, U_MSL_crsmissile_L_01_phlog, 3) {{
						Color baseColor = UAWPal.phlogistonFront;
						float missileDamage = 125;
						health = missileDamage * 0.8f;
						speed = 7f;
						rotateSpeed = 1.2f;

						maxRange = 2 * tilesize;

						exhaustColor = baseColor;

						lowAltitude = true;
						targetAir = false;

						missileAccelTime = 2 * tick;
						deathSound = Sounds.largeExplosion;

						lifetime = missileLifetime(unitRange, this.speed, missileAccelTime);

						parts.add(new FlarePart() {{
							progress = PartProgress.life.slope().curve(Interp.pow2In);
							sides = 5;
							color1 = baseColor;
							radius = 0f;
							radiusTo = 65f;
							stroke = 6f;
							rotation = 360f / sides / 2f;
							y = px(-57);
							followRotation = true;
						}});

						weapons.add(new SuicideWeapon() {{
							float splashRad = 7f * tilesize;
							shake = 45f;
							bullet = new ExplosionBulletType(missileDamage, splashRad) {{
								buildingDamageMultiplier = 6.8f;
								splashDamagePierce = true;
								knockback = 24f;

								hitColor = baseColor;

								makeFire = true;
								shootEffect = new MultiEffect(
									Fx.massiveExplosion,
									new ScatheExplosionEffect() {{
										size = splashRad * 0.8f;
										slashAmount = 24;
										color = UAWPal.phlogistonFront;
										color1 = UAWPal.phlogistonBack;
									}},
									new CrossbombEffect() {{
										lifetime = 1.5f * tick;
										waveSizeBase = waveSize = 0;
										crossLength = splashRad * 2f;
										crossRotOffset = 45;
										crossWidth = 8;
										color = baseColor;
										drawBottom = false;
									}},
									new WaveEffect() {{
										lifetime = 10f;
										strokeFrom = 4f;
										sizeTo = splashRad * 2;
									}}
								);

								puddles = 16;
								puddleAmount = 35;
								puddleRange = splashRad / 2;
								puddleLiquid = Liquids.oil;

								fragLifeMin = 0.2f;
								fragLifeMax = 1.2f;
								fragBullets = 14;
								fragBullet = new ArtilleryBulletType(1.5f, 15) {{
									splashDamageRadius = splashRad / 2;
									splashDamage = 40f;

									width = height = 18f;
									spin = 4.5f;
									buildingDamageMultiplier = 3f;
									drag = 0.02f;
									hitEffect = Fx.massiveExplosion;
									despawnEffect = Fx.scatheSlash;
									lifetime = splashRad / this.speed;
									collidesTiles = false;
									lightColor = backColor = trailColor = hitColor = UAWPal.phlogistonMid;
									frontColor = Color.white;
									smokeEffect = Fx.shootBigSmoke2;
									despawnShake = 7f;
									lightRadius = 30f;
									lightOpacity = 0.5f;

									puddles = 4;
									puddleAmount = 18;
									puddleRange = splashRad / 3;
									puddleLiquid = Liquids.slag;
								}};

							}};
						}});
						abilities.add(new MoveEffectAbility() {{
							color = UAWPal.missileSmoke;
							effect = UAWFx.missileTrailSmoke(80, 6, 18);
							rotation = 180f;
							y = -9f;
							interval = 4f;
						}});
					}};
				}};

				parts.add(
					new MissilePart("-missile") {{
						layerOffset = 0.01f;
						moves.add(new PartMove(PartProgress.warmup, 0f, -10f * px, 0));
					}},
					new RegionPart("-top") {{
						progress = PartProgress.warmup.curve(Interp.smooth);
						moveX = -8 * px;
						moveY = -33 * px;
						moveRot = 45;
						mirror = true;
					}}
				);
			}});
			weapons.add(
				new Weapon(U_WP_missile_S_01_phlog) {{
					x = 18 * px;
					y = 37 * px;

					rotate = true;
					rotateSpeed = 12;
					reload = 1 * tick;
					alternate = true;


					shootSound = Sfx.wp_k_shotgunShoot_3;
					shoot = new ShootSpread() {{
						spread = 2.8f;
						shots = 6;
						velocityRnd = 0.35f;
					}};

					bullet = new TrailBulletType(7, 12f) {{
						height = 15;
						width = 8f;

						trailEffect = new StatusHitEffect() {{
							life = 13;
							color = front;
							sizeEnd = 0.5f;
							shapeVariant = 2;
						}};
						trailLengthScale = 0.25f;
						trailChance = 0.4f;

						shootEffect = Fx.shootSmall;
						smokeEffect = new ShootSmokeEffect() {{
							color = Pal.lightOrange;
							split = true;
							splitAngle = 90f;
							smokeSize = 1.25f;
							spreadRange = 15;
							spreadCone = 12.5f;
							amount = 2;
						}};
						hitEffect = UAWFx.sparkHit(7, 2, back);
						despawnEffect = new MultiEffect(
							UAWFx.hitBulletSmall(back)
						);
						lifetime = (unitRange * 0.25f) / this.speed;
						frontColor = front;
						backColor = back;

						pierceCap = 2;

						status = StatusEffects.slow;
						statusDuration = tick * 2;
					}};
				}},
				new Weapon(U_WP_missile_S_01_phlog) {{
					x = 43 * px;
					y = -42 * px;

					rotate = true;
					rotateSpeed = 12;
					reload = 1.2f * tick;
					alternate = true;

					shootSound = Sfx.wp_k_shotgunShoot_3;
					shoot = new ShootSpread() {{
						spread = 2.8f;
						shots = 6;
						velocityRnd = 0.35f;
					}};

					bullet = new TrailBulletType(6, 12.5f) {{
						height = 15;
						width = 8f;

						trailEffect = new StatusHitEffect() {{
							life = 13;
							color = front;
							sizeEnd = 0.5f;
							shapeVariant = 2;
						}};
						trailLengthScale = 0.25f;
						trailChance = 0.4f;

						shootEffect = Fx.shootSmall;
						smokeEffect = new ShootSmokeEffect() {{
							color = Pal.lightOrange;
							split = true;
							splitAngle = 90f;
							smokeSize = 1.25f;
							spreadRange = 15;
							spreadCone = 12.5f;
							amount = 2;
						}};
						hitEffect = UAWFx.sparkHit(7, 2, back);
						despawnEffect = new MultiEffect(
							UAWFx.hitBulletSmall(back)
						);
						lifetime = (unitRange * 0.25f) / this.speed;
						frontColor = front;
						backColor = back;

						pierceCap = 2;

						status = StatusEffects.slow;
						statusDuration = tick * 2;
					}};
				}}
			);

		}};

		// TODO need an idea instead of artillery go boom
		minerva = new UnitType("minerva") {{
			float unitRange = 55 * tilesize;
			hidden = true;
			health = 16000;
			hitSize = 44;
			speed = 0.6f;
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

			weapons.add(new PointDefenseWeapon(U_WP_pointdefense_01_purple) {{
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
			weapons.add(new PointDefenseWeapon(U_WP_pointdefense_01_purple) {{
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
			weapons.add(new Weapon(U_WP_machinegun_M_01_purple) {{
				rotate = mirror = autoTarget = alternate = true;
				controllable = false;
				x = 12f;
				y = 2.5f;
				inaccuracy = 16f;
				reload = 15f;
				shootSound = Sounds.shootBig;
				ejectEffect = Fx.casing2;
				bullet = new FlakBulletType(8f, 15) {{
					splashDamage = 30;
					height = 14f;
					width = 7f;
					homingPower = 0.05f;
					homingRange = 6 * tilesize;
					explodeRange = splashDamageRadius = 3f * tilesize;
					explodeDelay = 10f;
					buildingDamageMultiplier = 0.5f;
					maxRange = unitRange - 16;
					lifetime = (unitRange * 0.45f) / speed;
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
							backColor = Pal.gray.cpy();
							frontColor = Color.white;
							despawnEffect = Fx.none;
						}};
						fragBullets = 6;
					}};

				}};
			}});
			weapons.add(new UAWWeapon(U_WP_artillery_L_01_purple) {{
				layerOffset = 0.2f;
				rotate = true;
				mirror = false;
				rotateSpeed = 0.8f;
				rotationLimit = 120;
				x = 0f;
				y = -39f * px;
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
				shootSound = Sfx.wp_k_cannonShoot_1;
				shake = 16;
				shootStatusDuration = reload * 1.5f;
				shootStatus = StatusEffects.slow;
				ejectEffect = UAWFx.casing5;

				bullet = new SplashArtilleryBulletType(2.25f, 500, 12 * tilesize) {{
					height = 38;
					width = height / 2f;
					buildingDamageMultiplier = 3.5f;
					lifetime = unitRange / speed;
					status = StatusEffects.burning;
					incendChance = 0.8f;
					incendSpread = 16f;
					makeFire = true;
					hitSound = Sfx.exp_n_impactHuge_1;
					trailInterval = 18;
					trailChance = -1;
					trailEffect = new MultiEffect(
						Fx.artilleryTrail,
						new SmokePuffEffect() {{
							amount = 25;
							life = 65;
						}}
					);
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
					trailLength = 21 * 2;
					trailWidth = 7;
					trailInterp = v -> Math.max(Mathf.slope(v), 0.8f);
					trailColor = backColor;
					aftershock = new AftershockBulletType(splashDamage / 2, splashDamageRadius / 1.2f) {{
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
				float barrelMoveY = -10f;
				parts.add(
					new RegionPart("-body"),
					new RegionPart("-barrel-front") {{
						progress = PartProgress.recoil;
						moveY = (barrelMoveY - 7) * px;
					}},
					new RegionPart("-barrel-back") {{
						progress = PartProgress.recoil;
						moveY = barrelMoveY * px;
					}}
				);
			}});
		}};
		// endregion Naval - Monitor

		// region Naval - Torpedo
		mtb72 = new UnitType("mtb72") {{
			float unitRange = 35 * tilesize;
			health = 650;
			speed = 1f;
			accel = 0.2f;
			rotateSpeed = 4.25f;
			drag = 0.05f;
			hitSize = 18;
			maxRange = range = unitRange;
			faceTarget = false;

			trailLength = 28;
			waveTrailX = 32f * px;
			waveTrailY = -32f * px;
			trailScl = 2.5f;

			constructor = UnitWaterMove::create;

			weapons.add(new Weapon(U_WP_machinegun_M_01_red) {{
				rotate = true;
				rotateSpeed = 12;
				rotationLimit = 270;
				inaccuracy = 3f;
				x = 22f * px;
				y = -12f * px;
				reload = 5f;
				shootSound = Sfx.wp_k_gunShootSmall_2;
				ejectEffect = Fx.casing1;
				bullet = new StandardBullets(6f, 6, 0) {{
					maxRange = unitRange * 0.75f;
				}};
			}});
			weapons.add(new PointDefenseWeapon(U_WP_pointdefense_01_red) {{
				rotate = autoTarget = true;
				mirror = controllable = false;
				x = 0f;
				y = -44f * px;
				reload = 4f;
				recoil = 0f;
				targetInterval = 4f;
				targetSwitchInterval = 4f;
				ejectEffect = Fx.casing1;
				bullet = new BulletType() {{
					shootEffect = Fx.sparkShoot;
					smokeEffect = Fx.shootSmallSmoke;
					hitEffect = Fx.pointHit;
					maxRange = unitRange / 2f;
					splashDamage = 15f;
				}};
			}});

			weapons.add(new TorpedoWeapon() {{
				layerOffset = -0.01f;
				x = 31 * px;
				y = -21f * px;
				reload = 3.5f * tick;

				bullet = new TorpedoBulletType(1.8f, 650) {{
					shootEffect = new MultiEffect(
						UAWFx.shootSmoke,
						Fx.smeltsmoke
					);
					trailLengthScale = 1.5f;
					height = 13;
					width = height / 2;
					lifetime = unitRange / speed;
					homingPower = 0.05f;
					homingDelay = 15f;
					homingRange = unitRange * 0.8f;

				}};

				shootX = 12 * px;
				parts.add(
					new RegionPart(U_WP_artillery_S_02_red) {{
						progress = PartProgress.warmup.curve(Interp.bounceOut);
						moveX = 10 * px;
						shadow = 0;
						outline = false;

						moves.add(new PartMove(PartProgress.recoil, 0f, 0f, -15f));
					}}
				);
			}});
		}};
		mtb96 = new UnitType("mtb96") {{
			float unitRange = 45 * tilesize;
			health = 3250;
			speed = 1f;
			accel = 0.2f;
			rotateSpeed = 3f;
			drag = 0.17f;
			hitSize = 22f;
			armor = 5f;
			faceTarget = false;
			maxRange = range = unitRange;
			ammoType = new ItemAmmoType(Items.graphite, 2);

			trailLength = 35;
			waveTrailX = 40 * px;
			waveTrailY = -36f * px;
			trailScl = 2.7f;

			constructor = UnitWaterMove::create;

			weapons.add(new TorpedoWeapon(U_WP_missile_M_01_red) {{
				layerOffset = -0.01f;
				alternate = mirror = true;
				baseRotation = -45f;
				x = 35f * px;
				y = -10f * px;
				reload = 3f * 60;

				shootWarmupSpeed = 0.05f;
				minWarmup = 0.8f;

				shootSound = Sfx.wp_torp_torpedoLaunchSpring_1;

				shoot.firstShotDelay = 15f;

				bullet = new TorpedoBulletType(1.8f, 650) {{
					shootEffect = new MultiEffect(
						UAWFx.shootSmoke,
						Fx.smeltsmoke
					);
					trailLengthScale = 1.5f;
					lifetime = unitRange / speed;
					homingRange = unitRange;
				}};

				parts.add(
					new RegionPart() {{
						progress = PartProgress.warmup.curve(Interp.bounceOut);
						moveX = 25 * px;
						shadow = 0;
					}}
				);
			}});

			weapons.add(new PointDefenseWeapon(U_WP_pointdefense_01_red) {{
				rotate = autoTarget = true;
				mirror = controllable = false;
				x = 0f;
				y = 53f * px;
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
			weapons.add(new Weapon(U_WP_machinegun_M_01_red) {{
				rotate = true;
				mirror = true;
				rotateSpeed = 12;
				rotationLimit = 270;
				inaccuracy = 3f;
				x = 37f * px;
				y = -15f * px;
				reload = 5;
				recoil = 1f;
				shootSound = Sfx.wp_k_gunShootSmall_2;
				ejectEffect = Fx.casing2;
				bullet = new StandardBullets(6f, 16, 1) {{
					maxRange = unitRange * 0.8f;

					pierceCap = 2;
				}};
			}});
			weapons.add(new Weapon(U_WP_missile_M_02_red) {{
				reload = 45f;
				x = 0f;
				y = -38f * px;
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
					shootSound = Sfx.wp_k_gunShoot_6;

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
				new Weapon(U_WP_machinegun_S_01_red) {{
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

					shootSound = Sounds.shoot;
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
				new TankPointDefenseWeapon(U_WP_pointdefense_02_red) {{
					mirror = false;

					x = -27 * px;
					y = -22 * px;

					reload = 2 * tick;

					minDamageTarget = 90;

					ejectEffect = Fx.casing2;

					bullet = new BulletType() {{
						hitShake = 2f;
						hitColor = Pal.missileYellow;
						shootEffect = Fx.shootBigColor;
						hitEffect = new MultiEffect(
							UAWFx.hitBulletBigColor,
							new StatusHitEffect() {{
								life = 15f;
								amount = 4;
								color = hitColor;
								shapeVariant = 2;
							}}
						);
						maxRange = unitRange / 3f;
						damage = 150f;
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

					shootSound = Sfx.wp_k_gunShoot_7;
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
				new Weapon(U_WP_machinegun_M_01_red) {{
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

					bullet = new BasicBulletType(4.5f, 45) {{
						lifetime = ((unitRange * 0.8f) / speed);
						height = 15f;
						width = 7f;
						buildingDamageMultiplier = 0.4f;
						homingRange = 60f;
						ammoMultiplier = 8f;
						shootEffect = Fx.shootBigColor;
						smokeEffect = Fx.shootBigSmoke;
						hitEffect = UAWFx.hitBulletBigColor;
					}};
				}},
				// ATGM
				new Weapon(U_WP_missile_M_03_red) {{
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

						spawnUnit = new CruiseMissileUnitType(name, U_MSL_crsmissile_S_01_red, 1) {{
							health = 120;
							speed = 4.5f;
							maxRange = 2 * tilesize;
							lifetime = unitRange * 1.5f / this.speed;
							exhaustColor = Pal.bulletYellowBack;

							missileAccelTime = 35f;
							deathSound = Sounds.largeExplosion;

							weapons.add(new SuicideWeapon() {{
								bullet = new ExplosionBulletType(health * 0.85f, 6 * tilesize) {{
									hitColor = Pal.bulletYellow;
									shootEffect = UAWFx.dynamicExplosion(4 * tilesize, hitColor, exhaustColor);
								}};
							}});
							abilities.add(new MoveEffectAbility() {{
								color = UAWPal.missileSmoke;
								effect = UAWFx.missileTrailSmoke(50, 4, 15);
								rotation = 180f;
								y = -9f;
								interval = 4f;
							}});
						}};
					}};

				}},
				// Point Defense
				new TankPointDefenseWeapon(U_WP_pointdefense_01_red) {{
					mirror = false;

					x = 40 * px;
					y = -32 * px;

					reload = 5 * tilesize;

					color = Pal.missileYellow;

					minDamageTarget = 275;

					bullet = new BulletType() {{
						hitShake = 2f;
						hitColor = Pal.missileYellow;
						shootEffect = Fx.shootBigColor;
						hitEffect = new MultiEffect(
							UAWFx.hitBulletBigColor,
							new StatusHitEffect() {{
								life = 15f;
								amount = 6;
								color = hitColor;
								shapeVariant = 2;
							}}
						);
						maxRange = unitRange / 3f;
						damage = 360;
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
			treadPullOffset = 8;
			treadRects = new Rect[]{
				// 0
				new Rect(11 - 144 / 2f, 97 - 355 / 2f, 35, 166)
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

					shootSound = Sfx.wp_k_shotgunShoot_2;
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
				new Weapon(U_WP_machinegun_M_01_red) {{
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
				new RotatedWeapon(U_WP_missile_L_01_red) {{
					mirror = false;

					x = 0;
					y = -70 * px;

					baseRotation = -180;
					reload = 15 * tick;
					recoil = 1f;
					inaccuracy = 12f;
					shootCone = 360;

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

						spawnUnit = new CruiseMissileUnitType(name, U_MSL_crsmissile_S_01_red, 1) {{
							health = 150;
							speed = 5.5f;
							maxRange = 3 * tilesize;
							lifetime = unitRange * 1.5f / this.speed;
							exhaustColor = Pal.bulletYellowBack;

							missileAccelTime = 35f;
							deathSound = Sounds.largeExplosion;

							weapons.add(new SuicideWeapon() {{
								bullet = new ExplosionBulletType(health * 0.8f, 6 * tilesize) {{
									hitColor = Pal.bulletYellow;
									shootEffect = UAWFx.dynamicExplosion(4 * tilesize, hitColor, exhaustColor);
								}};
							}});
							abilities.add(new MoveEffectAbility() {{
								color = UAWPal.missileSmoke;
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
				new RotatedWeapon(U_WP_artillery_S_01_red) {{
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
						trailInterval = 30f;
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
				new TankPointDefenseWeapon(U_WP_pointdefense_01_red) {{
					mirror = false;

					x = 50 * px;
					y = 40 * px;

					reload = 8 * tilesize;

					minDamageTarget = 350;

					bullet = new BulletType() {{
						hitShake = 3f;
						hitColor = Pal.missileYellow;
						shootEffect = Fx.shootBigColor;
						hitEffect = new MultiEffect(
							UAWFx.hitBulletBigColor,
							new StatusHitEffect() {{
								life = 15f;
								amount = 12;
								color = hitColor;
								shapeVariant = 2;
							}}
						);
						maxRange = unitRange / 2f;
						damage = 650f;
					}};
				}}
			);
		}};
		// endregion Ground - MBT

	}
}
