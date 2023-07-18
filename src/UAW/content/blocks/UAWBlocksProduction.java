package UAW.content.blocks;

import UAW.audiovisual.*;
import UAW.audiovisual.effects.StatusHitEffect;
import UAW.content.*;
import UAW.world.blocks.production.*;
import UAW.world.drawer.DrawPumpLiquidTile;
import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.content.*;
import mindustry.entities.effect.*;
import mindustry.gen.Sounds;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;
import multicraft.*;

import static UAW.Vars.*;
import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;

/** Contains Production structures, such as factories, drills, pumps, etc */
public class UAWBlocksProduction {
	public static Block placeholder,
	// Drills
	steamBore, advancedSteamBore,

	// Thympers
	steamThumper, blastThumper,

	// Pumps
	phlogistonBore, steamPump, pulsometerPump,

	// General Crafter
	gelatinizer, alloyCrucible, advancedAlloyCrucible,

	// Steam Crafters
	ironcladCompressor,

	// Petroleum Crafter
	blastCruicible,

	// Petroleum
	phlogistonCondenser, phlogistochemicalDistillery,

	// Liquid Mixer
	cryofluidBlender;

	public static void load() {
		// Drills
		steamBore = new Bore("steam-bore") {{
			requirements(Category.production, with(
				Items.copper, 12,
				Items.graphite, 20
			));
			size = 2;

			tier = 3;
			hardnessLimit = 1f;
			drillTime = 100;
			liquidCapacity = 30f;

			updateEffect = UAWFx.cloudPuff(3.5f, UAWPal.steamMid);
			drillEffect = Fx.mineBig;

			hardnessUpperMult = 0.6f;
			drillMultipliers.put(Items.coal, 4.5f);
			drillMultipliers.put(Items.scrap, 0.75f);

			consumeLiquid(UAWLiquids.steam, 0.25f);
		}};
		advancedSteamBore = new Bore("advanced-steam-bore") {{
			requirements(Category.production, with(
				Items.copper, 75,
				Items.graphite, 90,
				Items.silicon, 50,
				Items.titanium, 80
			));
			size = 3;

			tier = 4;
			drillTime = 200;
			warmupSpeed = 0.0005f;
			liquidCapacity = 80;

			updateEffect = UAWFx.cloudPuff(5f, UAWPal.steamMid);
			drillEffect = Fx.mineBig;
			rotateSpeed = 3f;

			hardnessLimit = 3;
			hardnessUpperMult = 0.8f;
			hardnessBelowMult = 0.2f;
			drillMultipliers.put(Items.coal, 4f);
			drillMultipliers.put(Items.titanium, 2.25f);

			consumeLiquid(UAWLiquids.steam, 1.8f);
		}};

		// Thumpers
		steamThumper = new SpecificItemDrill("steam-thumper") {{
			requirements(Category.production, with(
				Items.copper, 55,
				Items.lead, 45,
				Items.graphite, 40,
				Items.silicon, 30
			));
			size = 3;
			baseArrowColor = Color.valueOf("989aa4");
			squareSprite = false;
			tileRequirement = Blocks.oreCoal;
			drilledItem = UAWItems.anthracite;
			tier = 3;
			arrows = 1;
			itemCapacity = 35;
			drillTime = 12 * tick;
			hasLiquids = true;
			liquidCapacity = 90f;
			drillEffect = new MultiEffect(
				UAWFx.mineImpact(4, 15, UAWPal.graphiteFront),
				Fx.mineImpactWave.wrap(UAWPal.graphiteFront, 8 * tilesize),
				Fx.drillSteam,
				UAWFx.dynamicExplosion(4 * tilesize, UAWPal.graphiteFront, UAWPal.graphiteBack)
			);
			consumeLiquid(UAWLiquids.steam, 1f);
		}};

		// Frackers
		phlogistonBore = new ExplodingFracker("phlogiston-bore") {{
			requirements(Category.production, with(
				Items.copper, 150,
				Items.graphite, 250,
				Items.titanium, 115,
				Items.silicon, 85,
				UAWItems.stoutsteel, 75
			));
			size = 3;

			updateEffectChance = 0.05f;
			updateEffect = new MultiEffect(
				new StatusHitEffect() {{
					color = UAWLiquids.phlogiston.color;
					amount = 6;
					spreadBase = 3;
					spreadRad = 6;
					life = 45f;
				}},
				Fx.flakExplosion
			);

			liquidCapacity = 180;
			attribute = Attribute.oil;
			baseEfficiency = 0.5f;
			rotateSpeed = -2.5f;

			hasPower = true;
			squareSprite = false;

			result = UAWLiquids.phlogiston;
			pumpAmount = 30 / tick;

			breakEffect = UAWFx.breakBlockPhlog;

			consumeItem(Items.blastCompound);
			consumeLiquid(UAWLiquids.steam, 120 / tick);
			consumePower(1.5f);
		}};

		// Pumps
		steamPump = new UAWPump("steam-pump") {{
			requirements(Category.liquid, with(
				Items.copper, 60,
				Items.metaglass, 50,
				Items.silicon, 25,
				Items.titanium, 25
			));
			consumeLiquid(UAWLiquids.steam, 15 / tick);

			size = 2;
			pumpAmount = 0.2f;
			liquidCapacity = 60f;

			squareSprite = false;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawPumpLiquidTile() {{
					padding = 22 * px;
				}},
				new DrawDefault()
			);
		}};
		pulsometerPump = new UAWPump("pulsometer-pump") {{
			requirements(Category.liquid, with(
				Items.copper, 70,
				Items.metaglass, 80,
				Items.silicon, 35,
				Items.titanium, 35,
				Items.plastanium, 35
			));
			consumeLiquid(UAWLiquids.steam, 0.5f);
			size = 3;
			pumpAmount = 0.25f;
			liquidCapacity = 180f;

			squareSprite = false;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawPumpLiquidTile() {{
					padding = 35 * px;
				}},
				new DrawDefault()
			);
		}};

		// General Crafters
		gelatinizer = new MultiCrafter("gelatinizer") {{
			requirements(Category.crafting, with(
				Items.lead, 45,
				Items.graphite, 30,
				Items.thorium, 20
			));
			size = 2;

			hasItems = true;
			hasLiquids = true;

			craftEffect = UAWFx.effectHit(0.3f, UAWPal.cryoFront, UAWPal.cryoBack);
			updateEffect = UAWFx.effectHit(0.2f, Color.valueOf("f7cba4"), Color.valueOf("d3ae8d"));
			updateEffectChance = 0.02f;

			menu = detailed;

			resolvedRecipes = Seq.with(
				// Fluid to Gel
				new Recipe() {{
					input = new IOEntry(
						Seq.with(ItemStack.with(Items.sand, 2)),
						Seq.with(LiquidStack.with(Liquids.cryofluid, 0.25f)),
						0.45f
					);
					output = new IOEntry(
						Seq.with(ItemStack.with(UAWItems.cryogel, 1)),
						Seq.with()
					);
					craftTime = 45f;
				}},
				// Gel to fluid
				new Recipe() {{
					input = new IOEntry(
						Seq.with(ItemStack.with(UAWItems.cryogel, 1)),
						Seq.with(),
						0.45f
					);
					output = new IOEntry(
						Seq.with(),
						Seq.with(LiquidStack.with(Liquids.cryofluid, 0.25f))
					);
					craftTime = 45f;
				}}
			);


			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidTile() {{
					padding = 8 * px;
				}},
				new DrawBubbles() {{
					color = UAWPal.cryoFront;
				}},
				new DrawDefault()
			);
		}};
		alloyCrucible = new GenericCrafter("alloy-crucible") {{
			requirements(Category.crafting, with(
				Items.titanium, 150,
				Items.copper, 250,
				Items.metaglass, 95,
				Items.silicon, 95,
				Items.graphite, 95
			));
			size = 3;

			hasItems = true;
			hasLiquids = false;
			itemCapacity = 30;

			craftEffect = UAWFx.cloudPuff(10f, UAWPal.steamMid);
			updateEffect = new MultiEffect(Fx.melting, Fx.burning, Fx.fireSmoke);
			updateEffectChance = 0.06f;
			ambientSound = Sounds.smelter;

			craftTime = 4.5f * tick;
			consumePower(3.5f);
			consumeItems(
				new ItemStack(Items.titanium, 3),
				new ItemStack(Items.silicon, 2),
				new ItemStack(UAWItems.anthracite, 3)
			);
			outputItem = new ItemStack(
				UAWItems.stoutsteel, 3
			);

			squareSprite = false;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawArcSmelt() {{
					particles = 45;
				}},
				new DrawDefault()
			);
		}};
		ironcladCompressor = new MultiCrafter("ironclad-compressor") {{
			requirements(Category.crafting, with(
				Items.titanium, 150,
				Items.copper, 225,
				Items.silicon, 125,
				Items.lead, 225,
				Items.graphite, 120,
				Items.plastanium, 120
			));
			size = 3;
			hasItems = true;
			hasLiquids = true;
			itemCapacity = 72;
			liquidCapacity = 320;

			craftEffect = new RadialEffect() {{
				effect = UAWFx.crucibleSmoke(220, 3, UAWPal.steamMid);
				amount = 4;
				rotationSpacing = 90;
				lengthOffset = 35 * px;
				rotationOffset = 45;
			}};
			changeRecipeEffect = new MultiEffect(
				Fx.massiveExplosion,
				Fx.steam
			);
			ambientSound = Sounds.smelter;

			menu = detailed;
			ignoreLiquidFullness = true;

			resolvedRecipes = Seq.with(
				// Graphite
				new Recipe() {{
					float steamInput = 180f / tick;
					input = new IOEntry(
						Seq.with(ItemStack.with(Items.coal, 18)),
						Seq.with(LiquidStack.with(UAWLiquids.steam, steamInput))
					);
					output = new IOEntry(
						Seq.with(ItemStack.with(Items.graphite, 12)),
						Seq.with(LiquidStack.with(Liquids.water, (steamInput / phlogConversionScl) * steamLoseScl))
					);
					craftTime = 120f;
				}},
				// Plastanium
				new Recipe() {{
					float steamInput = 225f / tick;
					input = new IOEntry(
						Seq.with(ItemStack.with(Items.titanium, 10)),
						Seq.with(LiquidStack.with(
							Liquids.oil, 0.75f,
							UAWLiquids.steam, steamInput
						)),
						2.4f
					);
					output = new IOEntry(
						Seq.with(ItemStack.with(Items.plastanium, 8)),
						Seq.with(LiquidStack.with(Liquids.water, (steamInput / phlogConversionScl) * steamLoseScl))
					);
					craftTime = 90f;
				}},
				// Spore
				new Recipe() {{
					float steamInput = 120 / tick;
					input = new IOEntry(
						Seq.with(ItemStack.with(Items.sporePod, 4)),
						Seq.with(LiquidStack.with(UAWLiquids.steam, steamInput))
					);
					output = new IOEntry(
						Seq.with(),
						Seq.with(LiquidStack.with(
							Liquids.oil, (17.5 * 4) / tick
						))

					);
					craftTime = 90f;
				}}
			);
			squareSprite = false;
			drawer = new DrawRecipe() {{
				defaultDrawer = 3;
				drawers = new DrawBlock[]{
					// Graphite
					new DrawMulti(
						new DrawRegion("-bottom"),
						new DrawDefault(),
						new DrawRegion("-top-graphite")
					),
					// Plastanium
					new DrawMulti(
						new DrawRegion("-bottom"),
						new DrawDefault(),
						new DrawRegion("-top-plast"),
						new DrawFade() {{
							suffix = "-top-plast-top";
						}}
					),
					// Spores
					new DrawMulti(
						new DrawCircles() {{
							color = Color.valueOf("1d201f").a(0.24f);
							strokeMax = 2.5f;
							radius = 30f * px;
							amount = 4;
						}},
						new DrawLiquidTile() {{
							drawLiquid = Liquids.oil;
							padding = 34 * px;
						}},
						new DrawDefault(),
						new DrawRegion("-top-spore")
					)
				};
			}};
		}};

		// Phlogiston
		phlogistonCondenser = new ExplodingCrafter("phlogiston-condenser") {{
			requirements(Category.crafting, with(
				Items.copper, 150,
				Items.titanium, 200,
				Items.metaglass, 85,
				Items.silicon, 125,
				Items.graphite, 100
			));
			size = 3;

			hasItems = true;
			hasLiquids = true;
			ignoreLiquidFullness = false;
			outputsLiquid = true;

			liquidCapacity = 180;
			craftTime = 2 * tick;

			consumeLiquids(LiquidStack.with(
				Liquids.oil, 30 / tick
			));
			consumeItems(ItemStack.with(
				UAWItems.anthracite, 2,
				Items.blastCompound, 2
			));
			outputLiquid = new LiquidStack(UAWLiquids.phlogiston, 30 / tick);
			updateEffect = new MultiEffect(
				new StatusHitEffect() {{
					color = UAWLiquids.phlogiston.color;
					amount = 6;
					spreadBase = 3;
					spreadRad = 6;
					life = 45f;
				}},
				Fx.flakExplosion
			);

			breakEffect = UAWFx.breakBlockPhlog;
			squareSprite = false;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidTile(Liquids.oil) {{
					padding = 5 * px;
					drawLiquidLight = true;
				}},
				new DrawSoftParticles() {{
					color = UAWPal.phlogistonBack;
					color2 = UAWPal.phlogistonFront;
					alpha = 0.5f;
					particleRad = 14f;
					particleSize = 10f;
					particleLife = 120f;
					particles = 27;
				}},
				new DrawDefault(),
				new DrawGlowRegion("-glassglow") {{
					color = UAWPal.phlogistonMid;
				}}
			);
		}};

		// Mixers
		cryofluidBlender = new GenericCrafter("cryofluid-blender") {{
			requirements(Category.crafting, with(
				Items.lead, 150,
				Items.silicon, 90,
				Items.metaglass, 50,
				Items.thorium, 100,
				Items.titanium, 120
			));
			size = 3;
			liquidCapacity = 96f;
			outputsLiquid = true;
			hasItems = true;
			hasLiquids = true;

			updateEffect = new MultiEffect(
				Fx.freezing,
				Fx.freezing,
				Fx.wet
			);

			craftTime = 2f * tick;
			consumeItems(with(
				Items.titanium, 4,
				Items.thorium, 1
			));
			consumeLiquid(Liquids.water, 50 / tick);
			consumePower(3.5f);

			outputLiquid = new LiquidStack(Liquids.cryofluid, 48 / tick);

			squareSprite = false;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawPistons() {{
					sinMag = 5f * px;
					sinScl = 6f;
				}},
				new DrawRegion("-mid"),
				new DrawLiquidTile() {{
					drawLiquid = Liquids.cryofluid;
					padding = 40 * px;
				}},
				new DrawDefault(),
				new DrawGlowRegion() {{
					alpha = 0.6f;
					color = UAWPal.cryoMiddle;
				}}
			);
		}};
	}
}
