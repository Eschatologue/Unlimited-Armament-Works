package UAW.content.blocks;

import UAW.audiovisual.*;
import UAW.audiovisual.effects.*;
import UAW.content.*;
import UAW.entities.UAWUnitSorts;
import UAW.entities.bullet.*;
import UAW.world.blocks.defense.SpecificRegenProjector;
import UAW.world.blocks.defense.turrets.UAWItemTurret;
import UAW.world.drawer.DrawPulses;
import arc.graphics.Color;
import arc.math.Interp;
import mindustry.content.*;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.*;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.*;
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
		quadra = new UAWItemTurret("quadra") {{
			requirements(Category.turret, with(
				Items.copper, 115,
				Items.lead, 120,
				Items.titanium, 25,
				Items.graphite, 80
			));
			size = 2;
			scaledHealth = 160;

			reload = 6;
			recoil = 1f;
			recoilTime = 30f;
			maxAmmo = 30;

			range = 20 * tilesize;
			shootCone = 15f;
			inaccuracy = 7.5f;
			rotateSpeed = 10f;

			ammoUseEffect = Fx.casing2Double;
			//shootSound = Sfx.wp_k_gunShootSmall_2;
			shootSound = Sfx.exp_k_metalpipe;

			coolant = consumeCoolant(0.25f);

			shoot = new ShootAlternate() {{
				barrels = 2;
				shots = 2;
				barrelOffset = 5;
				spread = 4f;
				velocityRnd = 0.2f;
			}};

			// Just copy paste from other bullet
			ammo(
				Items.copper, new TrailBulletType(8f, 12) {{
					Color front = Pal.bulletYellow, back = Pal.bulletYellowBack;
					height = 12f;
					width = 5f;
					frontColor = front;
					backColor = back;
					shootEffect = Fx.shootSmall;
					smokeEffect = Fx.shootSmallSmoke;
					hitEffect = UAWFx.sparkHit(9, 3, back);
					despawnEffect = new MultiEffect(
						UAWFx.hitBulletSmall(back)
					);
					trailChance = 0.4f;
					trailEffect = new StatusHitEffect() {{
						life = 13;
						color = front;
						sizeEnd = 0.5f;
						shapeVariant = 2;
					}};
					trailLengthScale = 0.25f;

					pierceCap = 2;

					ammoMultiplier = 3;
				}},
				Items.graphite, new TrailBulletType(7f, 18) {{
					Color front = UAWPal.graphiteFront, back = UAWPal.graphiteMiddle;
					height = 12f;
					width = 7f;
					frontColor = front;
					backColor = back;
					shootEffect = Fx.shootSmall;
					smokeEffect = Fx.shootSmallSmoke;
					despawnEffect = hitEffect = new MultiEffect(
						UAWFx.hitBulletSmall(back)
					);
					trailChance = 0.4f;
					trailEffect = new StatusHitEffect() {{
						life = 13;
						color = front;
						sizeEnd = 0.5f;
						shapeVariant = 2;
					}};
					trailLengthScale = 0.25f;

					reloadMultiplier = 0.6f;
					rangeChange = -4.5f * tilesize;

					status = StatusEffects.slow;
					statusDuration = 2 / tick;

					ammoMultiplier = 4;
				}},
				Items.titanium, new ArmorPiercingBulletType(14f, 8) {{
					Color front = UAWPal.titaniumFront, back = UAWPal.titaniumBack;
					height = 12f;
					width = 5f;
					frontColor = front;
					backColor = back;
					shootEffect = Fx.shootSmall;
					smokeEffect = Fx.shootSmallSmoke;
					despawnEffect = hitEffect = new MultiEffect(
						UAWFx.hitBulletSmall(back)
					);
					trailChance = 0.4f;
					trailEffect = new StatusHitEffect() {{
						life = 13;
						color = front;
						sizeEnd = 0.5f;
						shapeVariant = 2;
					}};
					trailLengthScale = 0.3f;

					armorPierceScl = 0.6f;

					rangeChange = 2 * tilesize;
					ammoMultiplier = 4;
				}},
				Items.pyratite, new TrailBulletType(6f, 12) {{
					Color front = UAWPal.incendFront, back = UAWPal.incendBack;
					height = 12f;
					width = 5f;
					frontColor = front;
					backColor = back;
					shootEffect = Fx.shootSmall;
					smokeEffect = Fx.shootSmallSmoke;
					despawnEffect = hitEffect = new MultiEffect(
						UAWFx.hitBulletSmall(back),
						Fx.fireHit
					);
					trailChance = 0.4f;
					trailEffect = new StatusHitEffect() {{
						life = 13;
						color = front;
						sizeEnd = 0.5f;
						shapeVariant = 1;
					}};
					trailLengthScale = 0.25f;

					status = StatusEffects.burning;
					statusDuration = tick;

					ammoMultiplier = 5;
				}},
				UAWItems.cryogel, new TrailBulletType(6f, 12) {{
					Color front = UAWPal.cryoFront, back = UAWPal.cryoBack;
					height = 12f;
					width = 5f;
					frontColor = front;
					backColor = back;
					shootEffect = Fx.shootSmall;
					smokeEffect = Fx.shootSmallSmoke;
					despawnEffect = hitEffect = new MultiEffect(
						UAWFx.hitBulletSmall(back),
						UAWFx.cryoHit
					);
					trailChance = 0.4f;
					trailEffect = new StatusHitEffect() {{
						life = 13;
						color = front;
						sizeEnd = 0.5f;
						shapeVariant = 1;
					}};
					trailLengthScale = 0.25f;

					status = StatusEffects.freezing;
					statusDuration = tick;

					ammoMultiplier = 5;
				}}
			);
			limitRange();

			squareSprite = false;
			cooldownTime = reload * 0.5f;
			drawer = new DrawTurret(modTurretBase) {{
				parts.addAll(
					new RegionPart("-barrel") {{
						progress = PartProgress.warmup;
						moveY = -4f * px;

					}},
					new RegionPart("-bolt") {{
						progress = PartProgress.recoil;
						moveY = -7f * px;
					}},
					new RegionPart("-body")
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

			reload = 2;
			recoil = 1f;
			recoilTime = 60f;
			maxAmmo = 200;
			minWarmup = 0.95f;
			shootWarmupSpeed = 0.025f;

			range = 35 * tilesize;
			inaccuracy = 7.5f;
			rotateSpeed = 7f;
			targetAir = false;

			shootSound = Sfx.wp_k_gunShootBig_1;

			ammoUseEffect = Fx.casing2Double;

			coolant = consumeCoolant(1.5f);
			coolantMultiplier = 8f;

			shoot = new ShootBarrel() {{
				barrels = new float[]{
					0f, 2f, 0f,
					3f, 1f, 0f,
					-3f, 1f, 0f,
				};
				velocityRnd = 0.2f;
			}};

			ammo(
				Items.graphite, new TrailBulletType(12f, 25) {{
					Color front = Pal.bulletYellow, back = Pal.bulletYellowBack;
					height = 16f;
					width = 7f;
					frontColor = front;
					backColor = back;
					shootEffect = Fx.shootBig2;
					smokeEffect = Fx.shootBigSmoke;
					hitEffect = UAWFx.sparkHit(12f, 15, 6, back);
					despawnEffect = UAWFx.hitBulletSmall(back);
					trailChance = 0.4f;
					trailEffect = new StatusHitEffect() {{
						life = 15;
						color = back;
						sizeEnd = 0.8f;
						shapeVariant = 2;
					}};
					trailLengthScale = 0.25f;

					pierceCap = 3;

					ammoMultiplier = 8;
				}},
				UAWItems.stoutsteel, new TrailBulletType(16f, 12) {{
					Color front = UAWPal.titaniumFront, back = UAWPal.titaniumBack;
					height = 16f;
					width = 7f;
					frontColor = front;
					backColor = back;
					shootEffect = Fx.shootBig2;
					smokeEffect = Fx.shootBigSmoke;
					hitEffect = new MultiEffect(
						Fx.generatespark,
						UAWFx.sparkHit(12f, 15, 6, back)
					);
					despawnEffect = UAWFx.hitBulletSmall(back);
					trailChance = 0.4f;
					trailEffect = new StatusHitEffect() {{
						life = 15;
						color = back;
						sizeEnd = 0.8f;
						shapeVariant = 2;
					}};
					trailLengthScale = 0.25f;

					pierceArmor = true;

					ammoMultiplier = 8;
				}},
				Items.pyratite, new TrailBulletType(10f, 15) {{
					Color front = Pal.lightishOrange, back = Pal.lightOrange;
					height = 16f;
					width = 7f;
					frontColor = front;
					backColor = back;
					shootEffect = Fx.shootBig2;
					smokeEffect = Fx.shootBigSmoke;
					hitEffect = UAWFx.sparkHit(12f, 15, 6, back);
					despawnEffect = UAWFx.hitBulletSmall(back);
					trailChance = 0.4f;
					trailEffect = new StatusHitEffect() {{
						life = 15;
						color = back;
						sizeEnd = 0.8f;
						shapeVariant = 1;
					}};
					trailLengthScale = 0.25f;

					pierceCap = 2;

					status = StatusEffects.melting;
					statusDuration = 2 * tick;

					ammoMultiplier = 8;
				}},
				UAWItems.cryogel, new TrailBulletType(10f, 15) {{
					Color front = UAWPal.cryoFront, back = UAWPal.cryoBack;
					height = 16f;
					width = 7f;
					frontColor = front;
					backColor = back;
					shootEffect = Fx.shootBig2;
					smokeEffect = Fx.shootBigSmoke;
					hitEffect = UAWFx.sparkHit(12f, 15, 6, back);
					despawnEffect = UAWFx.hitBulletSmall(back);
					trailChance = 0.4f;
					trailEffect = new StatusHitEffect() {{
						life = 15;
						color = back;
						sizeEnd = 0.8f;
						shapeVariant = 1;
					}};
					trailLengthScale = 0.25f;

					pierceCap = 2;

					status = UAWStatusEffects.cryoBurn;
					statusDuration = 2 * tick;

					ammoMultiplier = 8;
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
		ashlock = new UAWItemTurret("ashlock") {{
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

			range = 35 * tilesize;
			inaccuracy = 0f;
			rotateSpeed = 5f;
			shake = 3.5f;

			shootSound = Sfx.wp_k_gunShootBig_1;

			ammoUseEffect = Fx.casing3;

			unitSort = UAWUnitSorts.mostHitPoints;

			ammoEjectBack = 9f;
			shootY = 10f;

			ammo(
				Items.graphite, new ArmorPiercingBulletType(8f, 120f) {{
					Color front = UAWPal.graphiteFront, back = UAWPal.graphiteMiddle;
					height = 24f;
					width = 10f;
					hitSize = 6;
					frontColor = front;
					backColor = back;
					shootEffect = new MultiEffect(
						Fx.shootBig2,
						Fx.hitBulletBig
					);
					smokeEffect = new ShootSmokeEffect() {{
						life = 15;
						color = Pal.lightOrange;
						split = true;
						splitAngle = 120f;
						spreadRange = 15;
						spreadCone = 25;
					}};
					hitEffect = UAWFx.hitBulletBigColor;
					despawnEffect = UAWFx.hitBulletSmall(1.25f, back);
					trailChance = 0.7f;
					trailEffect = new MultiEffect(
						Fx.disperseTrail,
						new StatusHitEffect() {{
							life = 13;
							color = front;
							sizeEnd = 0.8f;
							shapeVariant = 2;
						}});
					trailLengthScale = 0.25f;

					rangeChange = -2 * tilesize;

					status = StatusEffects.slow;
					statusDuration = 2 / tick;
					knockback = 1.5f;
					armorPierceScl = 0.45f;

					ammoMultiplier = 2;
				}},
				Items.silicon, new TrailBulletType(10f, 100f) {{
					Color front = Pal.bulletYellow, back = Pal.bulletYellowBack;
					height = 24f;
					width = 10f;
					hitSize = 6;
					frontColor = front;
					backColor = back;
					shootEffect = new MultiEffect(
						Fx.shootBig2,
						Fx.hitBulletBig
					);
					smokeEffect = new ShootSmokeEffect() {{
						life = 15;
						color = Pal.lightOrange;
						split = true;
						splitAngle = 120f;
						spreadRange = 15;
						spreadCone = 25;
					}};
					hitEffect = UAWFx.hitBulletBigColor;
					despawnEffect = UAWFx.hitBulletSmall(1.25f, back);
					trailChance = 0.7f;
					trailEffect = new MultiEffect(
						Fx.disperseTrail,
						new StatusHitEffect() {{
							life = 13;
							color = front;
							sizeEnd = 0.8f;
							shapeVariant = 2;
						}});
					trailLengthScale = 0.25f;

					rangeChange = 5 * tilesize;
					homingPower = 0.35f;

					ammoMultiplier = 2;
				}},
				Items.thorium, new TrailBulletType(12f, 150f) {{
					Color front = Pal.missileYellow, back = Pal.missileYellowBack;
					height = 24f;
					width = 10f;
					hitSize = 6;
					frontColor = front;
					backColor = back;
					shootEffect = new MultiEffect(
						Fx.shootBig2,
						Fx.hitBulletBig
					);
					smokeEffect = new ShootSmokeEffect() {{
						life = 15;
						color = Pal.lightOrange;
						split = true;
						splitAngle = 120f;
						spreadRange = 15;
						spreadCone = 25;
					}};
					hitEffect = UAWFx.hitBulletBigColor;
					despawnEffect = UAWFx.hitBulletSmall(1.25f, back);
					trailChance = 0.7f;
					trailEffect = new MultiEffect(
						Fx.disperseTrail,
						new StatusHitEffect() {{
							life = 13;
							color = front;
							sizeEnd = 0.8f;
							shapeVariant = 2;
						}});
					trailLengthScale = 0.25f;

					pierceArmor = true;

					ammoMultiplier = 2;
				}},
				Items.titanium, new ArmorPiercingBulletType(14f, 90) {{
					Color front = UAWPal.titaniumFront, back = UAWPal.titaniumBack;
					height = 24f;
					width = 10f;
					hitSize = 6;
					frontColor = front;
					backColor = back;
					shootEffect = new MultiEffect(
						Fx.shootBig2,
						Fx.hitBulletBig
					);
					smokeEffect = new ShootSmokeEffect() {{
						life = 15;
						color = Pal.lightOrange;
						split = true;
						splitAngle = 120f;
						spreadRange = 15;
						spreadCone = 25;
					}};
					hitEffect = UAWFx.hitBulletBigColor;
					despawnEffect = UAWFx.hitBulletSmall(1.25f, back);
					trailChance = 0.7f;
					trailEffect = new MultiEffect(
						Fx.disperseTrail,
						new StatusHitEffect() {{
							life = 13;
							color = front;
							sizeEnd = 0.8f;
							shapeVariant = 2;
						}});
					trailLengthScale = 0.25f;

					armorPierceScl = 0.75f;

					ammoMultiplier = 2;
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
			maxAmmo = 36;

			range = 65 * tilesize;
			shootCone = 1f;
			shake = 3f;
			rotateSpeed = 2.5f;

			shootSound = Sfx.wp_k_gunShootBig_2;
			ammoUseEffect = Fx.casing4;

			unitSort = UAWUnitSorts.mostHitPoints;

			ammo(
				Items.surgeAlloy, new TrailBulletType(15f, 400) {{
					height = 24f;
					width = 10f;
					hitSize = 8f;

					frontColor = UAWPal.surgeFront;
					backColor = UAWPal.surgeBack;
					trailColor = hitColor = backColor;

					trailEffect = new MultiEffect(
						Fx.disperseTrail
					);
					shootEffect = new MultiEffect(
						UAWFx.railShoot(35, UAWPal.surgeBack),
						Fx.shootBigColor,
						Fx.hitBulletBig
					);
					hitEffect = new MultiEffect(
						UAWFx.railHit(35, UAWPal.surgeBack),
						Fx.hitMeltdown,
						UAWFx.sparkHit(15f, 16, UAWPal.surgeBack),
						Fx.flakExplosion
					);
					smokeEffect = new ShootSmokeEffect() {{
						life = 30;
						color = Pal.lightOrange;
						split = true;
						splitAngle = 125f;
						spreadRange = 20;
						spreadCone = 30;
					}};

					despawnHit = true;
					trailInterval = 0.05f;
					trailChance = 0.8f;
					pierceCap = 3;
					hitShake = 2.5f;
				}},
				UAWItems.stoutsteel, new TrailBulletType(18f, 350) {{
					height = 24f;
					width = 10f;
					hitSize = 8f;

					frontColor = UAWPal.stoutsteelFront;
					backColor = UAWPal.stoutsteelBack;
					trailColor = hitColor = backColor;

					trailEffect = new MultiEffect(
						Fx.disperseTrail
					);
					shootEffect = new MultiEffect(
						UAWFx.railShoot(35, frontColor),
						Fx.shootBigColor,
						Fx.hitBulletBig
					);
					hitEffect = new MultiEffect(
						UAWFx.railHit(35, frontColor),
						Fx.hitMeltdown,
						UAWFx.sparkHit(15f, 16, frontColor),
						Fx.flakExplosion
					);
					smokeEffect = new ShootSmokeEffect() {{
						life = 30;
						color = Pal.lightOrange;
						split = true;
						splitAngle = 125f;
						spreadRange = 20;
						spreadCone = 30;
					}};

					despawnHit = true;
					trailInterval = 0.05f;
					trailChance = 0.8f;
					pierceCap = 3;
					hitShake = 2.5f;
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
						Fx.shootBigColor,
						Fx.hitBulletBig,
						Fx.hitBulletBig
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
						moveRot = -5;
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
		// TODO
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
							color = backColor.cpy().lerp(frontColor, 0.5f);
							color1 = Pal.lightishGray;
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
							color = frontColor;
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
							color = backColor.cpy().lerp(frontColor, 0.5f);
							color1 = Pal.lightishGray;
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
							color = frontColor;
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
							color = backColor.cpy().lerp(frontColor, 0.5f);
							color1 = Pal.lightishGray;
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
							color = frontColor;
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

			reload = 1.5f * tick;
			recoil = 3;
			recoilTime = recoil * 3;
			maxAmmo = 48;

			range = 100f;
			shake = 2f;
			inaccuracy = 5f;
			rotateSpeed = 4f;
			shootCone = 1f;

			ammoUseEffect = Fx.none;
			shootSound = Sfx.wp_k_shotgunShoot_1;

			unitSort = UAWUnitSorts.leastHitPoints;

			targetAir = false;

			shoot = new ShootSpread() {{
				spread = 2.8f;
				shots = 8;
				velocityRnd = 0.3f;
			}};

			ammo(
				Items.lead, new BuckshotBulletType(7, 8f, 21f) {{
					pierceCap = 3;
					knockback = 5f;
					despawnHit = true;
					hitEffect = Fx.hitBulletColor;
					despawnEffect = Fx.flakExplosion;
					shootEffect = Fx.shootBigColor;
					smokeEffect = Fx.shootSmallSmoke;
					ammoMultiplier = 3;
				}},
				Items.graphite, new BuckshotBulletType(7, 8f, 30f) {{
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
				Items.pyratite, new BuckshotBulletType(7, 6f, 15f) {{
					pierceCap = 3;
					knockback = 4f;
					frontColor = Pal.lightishOrange;
					backColor = Pal.lightOrange;
					shootEffect = Fx.shootSmallColor;
					smokeEffect = Fx.shootSmallSmoke;
					hitEffect = new MultiEffect(Fx.hitBulletColor, Fx.fireHit, Fx.fireSmoke);
					despawnEffect = new MultiEffect(Fx.flakExplosion, Fx.fireHit, Fx.fireSmoke);
					status = StatusEffects.burning;
				}},
				UAWItems.cryogel, new BuckshotBulletType(7, 6f, 12f) {{
					pierceCap = 3;
					knockback = 4f;
					frontColor = UAWPal.cryoFront;
					backColor = UAWPal.cryoBack;
					shootEffect = Fx.shootSmallColor;
					smokeEffect = Fx.shootSmallSmoke;
					hitEffect = new MultiEffect(Fx.hitBulletColor, UAWFx.cryoHit, Fx.fireSmoke);
					despawnEffect = new MultiEffect(Fx.flakExplosion, UAWFx.cryoHit, Fx.fireSmoke);
					status = StatusEffects.freezing;
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
		}};// TODO
		tempest = new ItemTurret("tempest") {{
			float turretRange = 38 * tilesize;
			requirements(Category.turret, with(
				Items.titanium, 200,
				Items.graphite, 150,
				Items.metaglass, 100,
				Items.silicon, 100
			));
			size = 3;
			scaledHealth = 120;

			reload = 50f;
			recoil = 8 * px;
			recoilTime = reload * 4;
			maxAmmo = 120;

			shoot = new ShootMulti(
				new ShootPattern() {{
					shots = 4;
					shotDelay = 15 * 0.25f;
				}},
				new ShootSpread() {{
					spread = 3.2f;
					shots = 3;
					velocityRnd = 0.3f;
				}}
			);

			range = turretRange;
			shake = 0.8f;
			inaccuracy = 6f;
			rotateSpeed = 4f;

			ammoUseEffect = Fx.none;
			shootSound = Sfx.wp_k_gunShoot_4;
			soundPitchMin = 0.7f;
			soundPitchMax = soundPitchMin + 0.2f;

			unitSort = UAWUnitSorts.highest;

			ammo(
				Items.plastanium, new FlakTrailBulletType(8f, 25) {{
					Color front = Pal.plastaniumFront, back = Pal.plastaniumBack;
					height = 15f;
					width = 7f;
					frontColor = front;
					backColor = back;
					shootEffect = Fx.shootBig;
					smokeEffect = new ShootSmokeEffect() {{
						color = Pal.lightOrange;
						split = true;
						splitAngle = 120f;
						smokeSize = 2;
						spreadRange = 30;
						spreadCone = 25;
						amount = 2;
					}};
					hitEffect = Fx.flakExplosion;

					despawnHit = true;
					trailChance = 0.4f;
					trailEffect = new StatusHitEffect() {{
						life = 13;
						color = front;
						sizeEnd = 0.5f;
						shapeVariant = 2;
					}};
					trailLengthScale = 0.25f;

					splashDamage = 30;
					splashDamageRadius = 7.5f * tilesize;
					explodeRange = 6 * tilesize;
					explodeDelay = 0f;
					fragRandomSpread = 90;
					fragBullets = 3;
					fragVelocityMin = 0.7f;
					fragVelocityMax = 1.4f;
					float fragSpeed = 10;

					fragBullet = new TrailBulletType(fragSpeed, 45f) {{
						height = 10;
						width = 4f;
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
						lifetime = (turretRange * 0.25f) / fragSpeed;
						frontColor = Color.white;
						backColor = back;

						pierceCap = 2;

						status = StatusEffects.slow;
						statusDuration = tick * 2;
					}};

					ammoMultiplier = 4;
				}},
				Items.silicon, new FlakTrailBulletType(6f, 15, "missile") {{
					Color front = Pal.missileYellow, back = Pal.missileYellowBack;
					height = 15f;
					width = 7f;
					frontColor = front;
					backColor = back;
					shootEffect = Fx.shootBig;
					smokeEffect = new ShootSmokeEffect() {{
						color = Pal.lightOrange;
						split = true;
						splitAngle = 120f;
						smokeSize = 2;
						spreadRange = 30;
						spreadCone = 25;
						amount = 2;
					}};
					hitEffect = Fx.flakExplosion;

					despawnHit = true;
					trailChance = 0.4f;
					trailEffect = new StatusHitEffect() {{
						life = 13;
						color = front;
						sizeEnd = 0.5f;
						shapeVariant = 2;
					}};
					trailLengthScale = 0.25f;

					splashDamage = 30;
					splashDamageRadius = 7.5f * tilesize;
					explodeRange = 8 * tilesize;
					explodeDelay = 0f;
					fragRandomSpread = 90;
					fragBullets = 4;
					fragVelocityMin = 0.7f;
					fragVelocityMax = 1.4f;
					float fragSpeed = 8;

					fragBullet = new TrailBulletType(fragSpeed, 30f) {{
						height = 10;
						width = 4f;
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
						homingPower = 0.25f;
						lifetime = (turretRange * 0.25f) / fragSpeed;
						frontColor = Color.white;
						backColor = back;
					}};

					ammoMultiplier = 3;
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

			ammoUseEffect = Fx.none;
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
				color = Pal.plastaniumFront;
				amount = 4;
			}};

			consumeItem(UAWItems.sulphur, 1).boost();
			consumePower(1.6f);
			consumeLiquid(UAWLiquids.phlogiston, 0.5f);

			baseColor = UAWLiquids.phlogiston.color;

			drawer = new DrawMulti(
				new DrawDefault(),
				new DrawPulses(false) {{
					square = true;
					pulseRad = (range / 2) * tilesize;
					layer = Layer.effect;
					color = UAWPal.phlogistonFront;
					stroke = 3f;
					timeScl = 400;
				}},
				new DrawGlowRegion("-glow") {{
					color = UAWPal.phlogistonMid;
				}},
				new DrawSoftParticles() {{
					color = UAWPal.phlogistonFront;
					color2 = UAWPal.phlogistonMid;
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
				color = Pal.plastaniumFront;
				amount = 4;
			}};

			consumeItem(UAWItems.sulphur, 2).boost();
			consumePower(2.4f);
			consumeLiquid(UAWLiquids.phlogiston, 1f);

			baseColor = UAWLiquids.phlogiston.color;

			drawer = new DrawMulti(
				new DrawDefault(),
				new DrawPulses(false) {{
					square = true;
					pulseRad = (range / 2) * tilesize;
					layer = Layer.effect;
					color = UAWPal.phlogistonFront;
					stroke = 3f;
					timeScl = 400;
				}},
				new DrawGlowRegion("-glow-top") {{
					color = UAWPal.phlogistonMid;
				}},
				new DrawGlowRegion("-glow-bottom") {{
					color = Pal.turretHeat;
				}},
				new DrawSoftParticles() {{
					color = UAWPal.phlogistonFront;
					color2 = UAWPal.phlogistonMid;
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
