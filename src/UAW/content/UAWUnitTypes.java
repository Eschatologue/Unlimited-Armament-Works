package UAW.content;

import UAW.entities.abilities.RazorRotorAbility;
import UAW.entities.bullet.*;
import UAW.graphics.*;
import UAW.type.Rotor;
import UAW.type.units.*;
import UAW.type.weapon.*;
import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.type.weapons.PointDefenseWeapon;
import mindustry.world.meta.BlockFlag;

import static mindustry.Vars.tilesize;
import static mindustry.content.Bullets.*;

public class UAWUnitTypes implements ContentList {
	public static UnitType
		aglovale, bedivere, calogrenant, dagonet, esclabor,
		jufeng,
		clurit, kujang, kerambit, cetbang, kiAmuk,
		hatsuharu, shiratsuyu, kagero, shimakaze,
		gardlacz, arkabuz, armata, zemsta;

	@Override
	public void load() {
		aglovale = new CopterUnitType("aglovale") {{
			health = 450;
			hitSize = 18;
			speed = 2.5f;
			accel = 0.04f;
			drag = 0.016f;
			rotateSpeed = 5.5f;
			ammoType = new ItemAmmoType(Items.graphite);
			circleTarget = true;
			commandLimit = 3;

			faceTarget = flying = true;
			range = 28 * tilesize;
			maxRange = range;
			spinningFallSpeed = 4;
			fallSmokeY = -10f;

			onTitleScreen = false;

			rotors.add(
				new Rotor(name + "-blade") {{
					x = y = 0;
					rotorSpeed = -16f;
					bladeCount = 3;
				}}
			);
			weapons.add(
				new Weapon("uaw-missile-small-red") {{
					rotate = false;
					mirror = true;
					shootCone = 90;
					x = 5f;
					y = -3f;
					inaccuracy = 4;
					reload = 12;
					shootSound = Sounds.missile;
					bullet = new MissileBulletType(6f, 35) {{
						width = 6f;
						height = 12f;
						shrinkY = 0f;
						drag = -0.003f;
						homingRange = 60f;
						keepVelocity = false;
						splashDamageRadius = 25f;
						splashDamage = 8f;
						lifetime = range / lifetime;
						backColor = Pal.bulletYellowBack;
						frontColor = Pal.bulletYellow;
						hitEffect = Fx.blastExplosion;
						despawnEffect = Fx.blastExplosion;
						trailColor = Color.gray;
						trailChance = 0.7f;
						shootCone = 90;
						weaveMag = 4;
						weaveScale = 4;
						ammoMultiplier = 4f;
					}};
				}},
				new Weapon("uaw-machine-gun-small-red") {{
					rotate = false;
					mirror = true;
					shootCone = 90;
					inaccuracy = 3f;
					top = false;
					x = 6f;
					y = 2.8f;
					reload = 6f;
					shootSound = Sounds.shoot;
					ejectEffect = Fx.casing1;
					bullet = new BasicBulletType(6f, 20) {{
						height = 18f;
						pierce = true;
						pierceCap = 2;
						buildingDamageMultiplier = 0.4f;
						width = 9f;
						maxRange = range;
						homingRange = 60f;
						lifetime = (range / speed) * 1.2f;
						trailLength = 15;
						trailWidth = 1.6f;
						trailColor = backColor;
						ammoMultiplier = 8f;
					}};
				}}
			);
		}};
		bedivere = new CopterUnitType("bedivere") {{
			health = 5500;
			hitSize = 30;
			speed = 2.5f;
			rotateSpeed = 4.5f;
			accel = 0.08f;
			drag = 0.03f;
			ammoType = new ItemAmmoType(Items.graphite);
			faceTarget = flying = circleTarget = true;
			spinningFallSpeed = 5f;
			fallSmokeY = -15f;
			commandLimit = 3;

			range = 32 * tilesize;
			maxRange = range;

			onTitleScreen = false;

			rotors.add(
				new Rotor(name + "-blade") {{
					x = 0;
					y = 2;
					rotorSpeed = -14f;
					bladeCount = 4;
				}}
			);
			weapons.add(
				new Weapon("uaw-machine-gun-small-red") {{
					minShootVelocity = 0.75f;
					rotate = top = false;
					shootCone = 30;
					inaccuracy = 5f;
					alternate = mirror = true;
					x = 7f;
					y = 10f;
					reload = 4;
					recoil = 0f;
					shootSound = Sounds.shoot;
					ejectEffect = Fx.casing1;
					bullet = new TrailBulletType(10f, 15) {{
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
				}},
				new Weapon("uaw-launcher-medium-red-2") {{
					minShootVelocity = 0.75f;
					rotate = false;
					alternate = mirror = true;
					top = true;
					shootCone = 15;
					x = 7f;
					y = -1.5f;
					reload = 60f;
					recoil = 5f;
					shake = 2f;
					ejectEffect = Fx.casing3;
					shootSound = Sounds.shootBig;
					shots = 5;
					inaccuracy = 6f;
					shotDelay = 5f;
					bullet = new ArtilleryBulletType(5, 50) {{
						height = 20f;
						width = 15f;
						collidesAir = true;
						trailSize = 5;
						lifetime = (range / speed) * 1.5f;
						shootEffect = Fx.shootBig;
						hitEffect = Fx.blastExplosion;
						hitSound = Sounds.boom;
						frontColor = Pal.plastaniumFront;
						backColor = Pal.plastaniumBack;
						splashDamage = 16f;
						splashDamageRadius = 2 * tilesize;
						fragBullets = 5;
						fragBullet = artilleryPlasticFrag;
						ammoMultiplier = 6f;
					}};
				}},
				new Weapon() {{
					rotate = false;
					mirror = true;
					alternate = true;
					shootCone = 30;
					x = 8f;
					y = 0f;
					shots = 2;
					shotDelay = 20;
					maxRange = range;
					reload = 3 * 60;
					shootSound = UAWSfx.cruiseMissileShoot1;
					bullet = new CruiseMissileBulletType(3f, 100) {{
						layer = Layer.flyingUnitLow - 1;
						size = 20;
						homingRange = range * 2;
						homingPower = 0.05f;
						keepVelocity = false;
						splashDamageRadius = 8 * tilesize;
						splashDamage = damage;
						lifetime = (range - 5) / speed;
						shootEffect = UAWFxS.shootPyraFlame;
						hitEffect = despawnEffect = UAWFxD.dynamicExplosion(splashDamageRadius);
						trailEffect = UAWFxS.pyraSmokeTrailUnder;
						status = StatusEffects.burning;
						statusDuration = 4 * 60;
						ammoMultiplier = 2f;
					}};
				}}
			);
			abilities.add(new RazorRotorAbility(25, 10f, 4.5f * tilesize) {
			});
		}};
		calogrenant = new CopterUnitType("calogrenant") {{
			health = 9000;
			armor = 10;
			hitSize = 35;
			speed = 2f;
			rotateSpeed = 4f;
			accel = 0.06f;
			drag = 0.04f;
			ammoType = new ItemAmmoType(Items.thorium);
			faceTarget = flying = true;
			commandLimit = 4;

			range = 45 * tilesize;
			maxRange = range;
			fallSmokeChance = 0;
			targetFlags = new BlockFlag[]{BlockFlag.turret, BlockFlag.battery, BlockFlag.extinguisher, null};

			onTitleScreen = false;

			weapons.add(
				new PointDefenseWeapon("uaw-point-defense-red") {{
					mirror = false;
					x = 0f;
					y = 3f;
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
				}},
				new Weapon("uaw-machine-gun-medium-red") {{
					rotate = false;
					inaccuracy = 6f;
					mirror = true;
					shootCone = 30f;
					x = 8f;
					y = 12f;
					reload = 5;
					shootSound = Sounds.shootBig;
					ejectEffect = Fx.casing2;
					bullet = new TrailBulletType(8f, 25) {{
						height = 16f;
						width = 8f;
						splashDamage = damage;
						splashDamageRadius = 16;
						frontColor = Pal.missileYellow;
						backColor = Pal.missileYellowBack;
						buildingDamageMultiplier = 0.3f;
						maxRange = range - 16;
						lifetime = range / speed;
						status = StatusEffects.burning;
						hitEffect = new MultiEffect(Fx.blastExplosion, Fx.fireHit, Fx.blastsmoke);
						ammoMultiplier = 8f;
					}};
				}},
				new UAWWeapon("uaw-artillery-small-red") {{
					mirror = alternate = true;
					rotate = false;
					x = 11f;
					y = 0f;
					inaccuracy = 0;
					shootCone = 30;
					rotateSpeed = 2.2f;
					reload = 3.5f * 60;
					recoil = 2.2f;
					shootSound = Sounds.railgun;
					shake = 3.5f;
					ejectEffect = UAWFxS.casing3Long;
					bullet = new LaserBulletType() {{
						damage = 350f;
						sideAngle = 20f;
						sideWidth = 1.5f;
						sideLength = 80f;
						width = 12;
						length = range / 1.5f;
						shootEffect = Fx.shockwave;
						colors = new Color[]{Pal.missileYellowBack, Pal.missileYellow, Color.white};
					}};
				}},
				new Weapon() {{
					rotate = false;
					x = y = 0f;
					inaccuracy = 12;
					shootCone = 60f;
					maxRange = range / 1.5f;
					reload = 5 * 60;
					shots = 3;
					shotDelay = 15f;
					shootSound = UAWSfx.cruiseMissileShoot1;
					bullet = new CruiseMissileBulletType(3f, 170) {{
						sprite = "uaw-cruise-missile-cryo";
						layer = Layer.flyingUnitLow - 1;
						size = 45;
						homingRange = range;
						homingPower = 0.05f;
						keepVelocity = false;
						splashDamageRadius = 12 * tilesize;
						splashDamage = damage;
						lifetime = (range - 5) / speed;
						shootEffect = UAWFxS.shootCryoFlame;
						trailColor = UAWPal.cryoFront;
						despawnHit = true;
						hitEffect = UAWFxD.dynamicExplosion2(splashDamageRadius, UAWPal.cryoMiddle, UAWPal.cryoBack);
						trailEffect = UAWFxS.cryoSmokeTrailUnder;
						status = StatusEffects.freezing;
						statusDuration = 4 * 60;
					}};
				}}
			);
			float rotX = 17;
			float rotY = 8;
			float rotSpeed = 13f;
			rotors.add(
				new Rotor("uaw-short-blade") {{
					x = -rotX;
					y = rotY;
					rotorSpeed = -rotSpeed;
					bladeCount = 3;
					drawRotorTop = false;
				}},
				new Rotor("uaw-short-blade") {{
					x = -rotX;
					y = rotY;
					rotorSpeed = rotSpeed;
					bladeCount = 3;
				}},
				new Rotor("uaw-short-blade") {{
					x = rotX;
					y = rotY;
					rotorSpeed = rotSpeed;
					bladeCount = 3;
					drawRotorTop = false;
				}},
				new Rotor("uaw-short-blade") {{
					x = rotX;
					y = rotY;
					rotorSpeed = -rotSpeed;
					bladeCount = 3;
				}}
			);
		}};

		jufeng = new JetUnitType("jufeng") {{
			health = 650;
			hitSize = 20;
			speed = 2.8f;
			accel = 0.1f;
			drag = 0.016f;
			rotateSpeed = 6f;
			ammoType = new ItemAmmoType(Items.blastCompound);
			circleTarget = true;
			commandLimit = 4;

			lowAltitude = false;
			range = 45 * tilesize;

			targetFlags = new BlockFlag[]{BlockFlag.repair, BlockFlag.generator, BlockFlag.extinguisher, null};

			trailX = 6f;
			trailY = -2;
			trailLength = 18;
			trailWidth = 3f;

			weapons.add(
				new Weapon() {{
					minShootVelocity = 0.75f;
					x = 3f;
					reload = 3f * 60;
					shootCone = 90f;
					ejectEffect = Fx.none;
					inaccuracy = 24f;
					shootSound = Sounds.shotgun;
					shots = 2;
					shotDelay = 15f;
					bullet = new UAWArtilleryBulletType(1.8f, 125) {{
						buildingDamageMultiplier = 2.5f;
						lifetime = (range / 3) / speed;
						trailMult = 0.5f;
						width = 10f;
						height = 14f;
						shootEffect = Fx.none;
						smokeEffect = Fx.none;
						splashDamageRadius = 5 * tilesize;
						hitEffect = UAWFxD.dynamicExplosion(splashDamageRadius);
						hitSound = Sounds.explosion;
						status = StatusEffects.blasted;
						statusDuration = 60f;
						makeFire = true;
						homingPower = 0.04f;
						homingRange = 32f;
						fragBullet = fragPlasticFrag;
					}};
				}});
		}};

		clurit = new UnitType("clurit") {{
			health = 750;
			speed = 0.75f;
			accel = 0.2f;
			rotateSpeed = 1.8f;
			drag = 0.05f;
			hitSize = 20;
			armor = 5;
			range = 30 * tilesize;
			maxRange = range;
			rotateShooting = false;
			ammoType = new ItemAmmoType(Items.graphite, 2);

			trailLength = 22;
			trailX = 7f;
			trailY = -9f;
			trailScl = 1.4f;

			constructor = UnitWaterMove::create;

			weapons.add(
				new PointDefenseWeapon("uaw-point-defense-purple") {{
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
				}},
				new Weapon("uaw-machine-gun-small-purple") {{
					rotate = mirror = autoTarget = true;
					controllable = false;
					x = 5f;
					y = 0.4f;
					inaccuracy = 4f;
					reload = 30f;
					shootSound = Sounds.shoot;
					ejectEffect = Fx.casing1;
					shots = 4;
					shotDelay = 5f;
					bullet = flakGlass;
				}},
				new Weapon("uaw-artillery-small-purple") {{
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
					bullet = new UAWArtilleryBulletType(2f, 35) {{
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
				}}
			);
		}};
		kujang = new UnitType("kujang") {{
			health = 7000;
			hitSize = 22;
			speed = 0.8f;
			accel = 0.20f;
			rotateSpeed = 1.5f;
			rotateShooting = false;
			range = 40 * tilesize;
			maxRange = range;
			ammoType = new ItemAmmoType(Items.graphite, 2);

			trailLength = 35;
			trailX = 9f;
			trailY = -15f;
			trailScl = 2f;

			constructor = UnitWaterMove::create;
			commandLimit = 4;

			weapons.add(
				new PointDefenseWeapon("uaw-point-defense-purple") {{
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
				new Weapon("uaw-machine-gun-small-purple") {{
					rotate = mirror = autoTarget = alternate = true;
					controllable = false;
					x = 7f;
					y = 5f;
					inaccuracy = 4f;
					reload = 12f;
					shootSound = Sounds.shoot;
					ejectEffect = Fx.casing2;
					bullet = fragGlass;
				}},
				new Weapon("uaw-missile-large-purple") {{
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
					xRand = 3;
					shots = 6;
					shotDelay = 6.5f;
					velocityRnd = 0.4f;
					ejectEffect = new MultiEffect(Fx.casing3Double, Fx.casing3Double, Fx.casing3Double);
					bullet = new UAWArtilleryBulletType(1.8f, 90) {{
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
						fragBullet = fragPlasticFrag;
					}};
				}}
			);
		}};
		kerambit = new UnitType("kerambit") {{
			health = 16000;
			hitSize = 44;
			speed = 0.65f;
			drag = 0.17f;
			accel = 0.2f;
			rotateSpeed = 1f;
			rotateShooting = false;
			range = 55 * tilesize;
			maxRange = range;
			ammoType = new ItemAmmoType(Items.thorium, 2);

			trailLength = 50;
			trailX = 18f;
			trailY = -15f;
			trailScl = 3.2f;

			constructor = UnitWaterMove::create;
			forceMultiTarget = true;
			weapons.add(
				new PointDefenseWeapon("uaw-point-defense-purple") {{
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
				}},
				new PointDefenseWeapon("uaw-point-defense-purple") {{
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
				}},
				new Weapon("uaw-machine-gun-medium-purple") {{
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
						fragBullet = flakGlass;
					}};
				}},
				new Weapon("uaw-artillery-large-purple") {{
					rotate = true;
					mirror = false;
					rotateSpeed = 1f;
					x = 0f;
					y = -3f;
					targetFlags = new BlockFlag[]{
						BlockFlag.turret,
						BlockFlag.extinguisher,
						BlockFlag.repair,
						BlockFlag.core
					};
					inaccuracy = 10f;
					reload = 5f * 60;
					recoil = 5f;
					shootSound = UAWSfx.artilleryShootHuge;
					shake = 16;
					shootStatusDuration = reload * 1.2f;
					shootStatus = StatusEffects.unmoving;
					ejectEffect = UAWFxS.casing5;
					bullet = new UAWArtilleryBulletType(1.7f, 550) {{
						height = 42;
						width = height / 2f;
						splashDamageRadius = 12 * tilesize;
						buildingDamageMultiplier = 3.5f;
						shieldDamageMultiplier = 4f;
						lifetime = range / speed;
						status = StatusEffects.burning;
						incendChance = 0.8f;
						incendSpread = 16f;
						makeFire = true;
						hitSound = UAWSfx.artilleryExplosionHuge;
						trailMult = 1f;
						hitShake = 15f;
						frontColor = Pal.sapBullet;
						backColor = Pal.sapBulletBack;
						shootEffect = new MultiEffect(
							UAWFxS.shootSporeFlame,
							UAWFxD.instShoot(65, Pal.sapBullet),
							UAWFxD.shootMassiveSmoke(5, 30, backColor)
						);
						smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.shootLiquid);
						despawnHit = true;
						hitEffect = UAWFxD.dynamicExplosion2(splashDamageRadius, frontColor, backColor);
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
				}}
			);
		}};

		hatsuharu = new UnitType("hatsuharu") {{
			health = 650;
			speed = 1.2f;
			accel = 0.2f;
			rotateSpeed = 1.9f;
			drag = 0.05f;
			hitSize = 18;
			range = 35 * tilesize;
			maxRange = range;
			rotateShooting = false;
			ammoType = new ItemAmmoType(Items.graphite, 2);

			trailLength = 20;
			trailX = 6f;
			trailY = -4f;
			trailScl = 1.9f;

			constructor = UnitWaterMove::create;

			weapons.add(
				new Weapon() {{
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
				}},
				new PointDefenseWeapon("uaw-point-defense-red") {{
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
				}}
			);
		}};
		shiratsuyu = new UnitType("shiratsuyu") {{
			health = 5500;
			speed = 1f;
			accel = 0.2f;
			rotateSpeed = 1.8f;
			drag = 0.17f;
			hitSize = 22f;
			armor = 5f;
			rotateShooting = false;
			range = 45 * tilesize;
			maxRange = range;
			ammoType = new ItemAmmoType(Items.graphite, 2);

			trailLength = 25;
			trailX = 8f;
			trailY = -9f;
			trailScl = 2.2f;

			constructor = UnitWaterMove::create;

			weapons.add(
				new Weapon() {{
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
				}},
				new PointDefenseWeapon("uaw-point-defense-red") {{
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
				}},
				new Weapon("uaw-machine-gun-medium-red") {{
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
				}},
				new Weapon("uaw-missile-medium-red-single") {{
					reload = 45f;
					x = 0f;
					y = -8f;
					rotateSpeed = 4f;
					rotate = true;
					mirror = false;
					shots = 2;
					shotDelay = 6f;
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
				}}
			);
		}};

		gardlacz = new TankUnitType("gardlacz") {{
			health = 750;
			armor = 20;
			hitSize = 18;
			speed = 1.3f;
			rotateSpeed = 2.5f;
			ammoType = new ItemAmmoType(Items.graphite);
			targetAir = false;

			accel = 0.05f;
			drag = 0.055f;
			range = 36 * tilesize;
			trailChance = 0.3f;
			drawCell = false;

			turretY = -4.3f;

			weapons.add(
				new PointDefenseWeapon("uaw-point-defense-red") {{
					rotate = autoTarget = true;
					mirror = controllable = false;
					layerOffset = 1;
					x = 2f;
					y = -4.3f;
					reload = 1.5f;
					rotateSpeed = 5.5f;
					recoil = 0.1f;
					targetInterval = 6f;
					targetSwitchInterval = 7.5f;
					ejectEffect = Fx.casing1;

					bullet = new BulletType() {{
						shootEffect = Fx.sparkShoot;
						hitEffect = Fx.pointHit;
						maxRange = range / 2f;
						damage = 8f;
					}};
				}},
				new TankWeapon(name + "-gun") {{
					weaponLayer = Layer.groundUnit;
					targetFlags = new BlockFlag[]{BlockFlag.extinguisher, null};
					top = false;
					mirror = false;
					rotateSpeed = 2.6f;
					x = 0f;
					y = 8f;
					shootY = 16f;
					reload = 1.5f * 60;
					recoil = 4.5f;
					shootSound = UAWSfx.cannonShoot1;
					ejectEffect = UAWFxS.casing2Long;
					shake = 3f;
					bullet = new TrailBulletType(7f, 85) {{
						height = 25f;
						width = 8f;
						lifetime = range / (speed + 3);
						knockback = 4f;
						armorIgnoreScl = 0.4f;
						shieldDamageMultiplier = 1.5f;
						trailColor = backColor;
						shootSound = Sounds.shootBig;
						shootEffect = new MultiEffect(UAWFxD.railShoot(22f, backColor), Fx.shootPyraFlame, Fx.shootSmallSmoke);
						hitEffect = despawnEffect = new MultiEffect(Fx.hitBulletBig, Fx.shootSmallSmoke);
						fragBullets = 4;
						fragLifeMin = 0f;
						fragCone = 30f;
						fragBullet = new BasicBulletType(7f, 6) {{
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
				}}
			);
		}};
		arkabuz = new TankUnitType("arkabuz") {{
			health = 7500;
			armor = 32;
			hitSize = 25;
			speed = 1.2f;
			rotateSpeed = 2f;
			ammoType = new ItemAmmoType(Items.graphite);
			targetAir = false;

			accel = 0.04f;
			drag = 0.08f;
			range = 48 * tilesize;
			trailChance = 0.6f;
			drawCell = false;

			weapons.add(
				new TankWeapon(name + "-gun") {{
					weaponLayer = Layer.groundUnit;
					targetFlags = new BlockFlag[]{BlockFlag.extinguisher, null};
					top = false;
					mirror = false;
					x = 0f;
					y = 17f;
					shootY = 5f;
					reload = 2 * 60;
					recoil = 4.4f;
					shootSound = UAWSfx.cannonShoot1;
					ejectEffect = UAWFxS.casing3Long;
					shake = 6f;
					bullet = new TrailBulletType(8f, 215) {{
						height = 35f;
						width = 10f;
						lifetime = range / (speed + 3);
						pierce = true;
						pierceCap = 2;
						knockback = 6f;
						trailColor = backColor;
						shootSound = Sounds.shootBig;
						armorIgnoreScl = 0.6f;
						shieldDamageMultiplier = 2f;
						shootEffect = new MultiEffect(UAWFxD.railShoot(30f, backColor), Fx.shootPyraFlame, Fx.shootBigSmoke2);
						hitEffect = despawnEffect = new MultiEffect(Fx.hitBulletBig, Fx.shootBigSmoke2);
						fragBullets = 6;
						fragLifeMin = 0f;
						fragCone = 30f;
						status = StatusEffects.melting;
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
				}}
			);
		}};
	}
}
