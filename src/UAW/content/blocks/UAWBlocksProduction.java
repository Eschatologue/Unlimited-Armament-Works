package UAW.content.blocks;

import UAW.audiovisual.*;
import UAW.content.*;
import UAW.world.blocks.production.*;
import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.content.*;
import mindustry.entities.effect.*;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.*;
import multicraft.*;

import static UAW.Vars.*;
import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;

/** Contains Production structures, such as factories, drills, pumps, etc */
public class UAWBlocksProduction {
	public static Block placeholder,
	// Drills
	blastBore, intermediateBlastBore,

	// Thympers
	/// Anthracite
	anthraciteThumperMk1, anthraciteThumperMk2,

	// General Crafter
	gelatinizer, alloyCrucible, advancedAlloyCrucible,

	// Steam Crafters
	ironcladCompressor,

	// Petroleum Crafter
	blastCruicible,

	// Petroleum
	phlogistochemicalDistillery,

	// Liquid Mixer
	cryofluidBlender;

	public static void load() {
		// Drills
		blastBore = new HardnessBurstDrill("phlog-bore") {{
			requirements(Category.production, with(
				Items.copper, 20,
				Items.metaglass, 10,
				Items.graphite, 20
			));
			size = 2;
			itemCapacity = 40;

			tier = 3;
			h_threshold = 1.5f;
			h_boostMult = 0.225f;
			h_penaltyMult = 1.05f;
			drillTime = 6 * tick;
			liquidCapacity = 50f;

			updateEffect = UAWFx.cloudPuff(3.5f, UAWPal.steamMid);
			drillEffect = new MultiEffect(
				UAWFx.mineImpact(1.75f, 60, 4, UAWPal.phlogistonFront),
				UAWFx.mineImpactWave(1.5f * tilesize, UAWPal.phlogistonFront),
				Fx.drillSteam,
				Fx.flakExplosion
			);

			drillMultipliers.put(Items.coal, 1.25f);
			drillMultipliers.put(Items.scrap, 0.75f);

			consumeLiquid(UAWLiquids.phlogiston, 0.25f);
		}};
		intermediateBlastBore = new HardnessBurstDrill("intermediate-phlog-bore") {{
			requirements(Category.production, with(
				Items.copper, 60,
				Items.graphite, 60,
				Items.silicon, 45,
				Items.plastanium, 35,
				Items.titanium, 70
			));
			size = 3;

			tier = 5;
			drillTime = 4.5f * tick;
			warmupSpeed = 0.0005f;
			liquidCapacity = 80;

			updateEffect = UAWFx.cloudPuff(3.5f, UAWPal.steamMid);
			drillEffect = new MultiEffect(
				UAWFx.mineImpact(3f, 90, 7, UAWPal.phlogistonFront),
				UAWFx.mineImpactWave(2.5f * tilesize, UAWPal.phlogistonFront),
				Fx.drillSteam,
				Fx.plasticExplosion
			);

			h_threshold = 2.625f;
			h_boostMult = 0.525f;
			h_penaltyMult = 1.05f;
			drillMultipliers.put(Items.coal, 2.625f);
			drillMultipliers.put(Items.titanium, 1.15f);

			consumeLiquid(UAWLiquids.phlogiston, 1.5f);
		}};

		// Thumpers
		anthraciteThumperMk1 = new ConversionDrill("anthracite-thumper-mk1") {{
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
			drillTime = 10 * tick;
			hasLiquids = true;
			liquidCapacity = 90f;
			drillEffect = new MultiEffect(
				UAWFx.mineImpact(4, 15, Pal.bulletYellow),
				Fx.mineImpactWave.wrap(Pal.bulletYellowBack, 8 * tilesize),
				Fx.drillSteam,
				UAWFx.dynamicExplosion(4 * tilesize, Pal.bulletYellow, Pal.bulletYellowBack)
			);
			consumeLiquid(UAWLiquids.phlogiston, 30f / tier);
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
				Items.copper, 225,
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
				effect = UAWFx.crucibleSmoke(220, 3, UAWPal.phlogistonFront);
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
					input = new IOEntry(
						Seq.with(ItemStack.with(Items.coal, 18)),
						Seq.with(LiquidStack.with(UAWLiquids.phlogiston, 30 / tick)),
						0.75f
					);
					output = new IOEntry(
						Seq.with(ItemStack.with(Items.graphite, 12)),
						Seq.with()
					);
					craftTime = 3 * tick;
				}},
				// Plastanium
				new Recipe() {{
					input = new IOEntry(
						Seq.with(ItemStack.with(Items.titanium, 10)),
						Seq.with(LiquidStack.with(
							Liquids.oil, 0.75f,
							UAWLiquids.phlogiston, 45 / tick
						)),
						2f
					);
					output = new IOEntry(
						Seq.with(ItemStack.with(Items.plastanium, 8)),
						Seq.with()
					);
					craftTime = 3 * tick;
				}},
				// Spore
				new Recipe() {{
					input = new IOEntry(
						Seq.with(ItemStack.with(Items.sporePod, 4)),
						Seq.with(LiquidStack.with(UAWLiquids.phlogiston, 15f / tick)),
						0.5f
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
