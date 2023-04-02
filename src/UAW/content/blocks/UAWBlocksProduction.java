package UAW.content.blocks;

import UAW.audiovisual.*;
import UAW.content.*;
import UAW.world.blocks.production.*;
import UAW.world.drawer.*;
import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.content.*;
import mindustry.entities.effect.*;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.production.*;
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
	steamDrill, advancedSteamDrill, steamThumper,

	// Pumps
	oilDerrick, steamPump, pulsometerPump,

	// General Crafter
	gelatinizer, alloyCrucible, advancedAlloyCrucible,

	// Steam Crafters
	ironcladCompressor, steamPress, plastFabricator,

	// Petroleum Crafter
	petroleumCrucible,

	// Petroleum
	petrochemicalRefinery, petroleumDistillery,

	// Liquid Mixer
	cryofluidBlender, surgeMixer;

	public static void load() {
		// Drills
		steamDrill = new UAWDrill("steam-drill") {{
			requirements(Category.production, with(
				Items.copper, 24,
				Items.graphite, 12
			));
			size = 2;
			squareSprite = false;
			tier = 3;
			itemCapacity = 25;
			drillTime = 300;
			warmupSpeed = 0.001f;
			hasLiquids = false;
			drawRim = true;
			updateEffectChance = 0.03f;
			updateEffect = UAWFx.cloudPuff(3.5f, UAWPal.steamMid);
			drillEffect = Fx.mineBig;

			consumeLiquid(UAWLiquids.steam, 0.25f);
			consumeLiquid(Liquids.oil, 0.05f).boost();
		}};
		advancedSteamDrill = new UAWDrill("advanced-steam-drill") {{
			requirements(Category.production, with(
				Items.copper, 85,
				Items.silicon, 50,
				Items.titanium, 40,
				Items.graphite, 75
			));
			size = 3;
			squareSprite = false;
			tier = 4;
			itemCapacity = 45;
			drillTime = 180;
			warmupSpeed = 0.0005f;
			hasLiquids = true;
			drawRim = true;
			updateEffectChance = 0.03f;
			updateEffect = UAWFx.cloudPuff(5f, UAWPal.steamMid);
			drillEffect = Fx.mineBig;
			rotateSpeed = 6f;

			consumeLiquid(UAWLiquids.steam, 1.8f);
			consumeLiquid(Liquids.oil, 0.1f).boost();
		}};

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
		oilDerrick = new Fracker("oil-derrick") {{
			requirements(Category.production, with(
				Items.copper, 225,
				Items.graphite, 300,
				Items.titanium, 230,
				UAWItems.stoutsteel, 100,
				Items.silicon, 120
			));
			size = 4;

			updateEffect = UAWFx.cloudPuff(5, UAWPal.steamMid);
			updateEffectChance = 0.05f;
			liquidCapacity = 360f;
			attribute = Attribute.oil;
			baseEfficiency = 0.25f;
			rotateSpeed = -2.5f;

			hasPower = false;
			squareSprite = false;

			result = Liquids.oil;
			pumpAmount = 135 / tick;
			consumeLiquid(UAWLiquids.steam, 144 / tick);
		}};

		// Pumps
		steamPump = new UAWPump("steam-pump") {{
			requirements(Category.liquid, with(
				Items.copper, 60,
				Items.metaglass, 50,
				Items.silicon, 25,
				Items.titanium, 25
			));
			consumeLiquid(UAWLiquids.steam, 0.25f);

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
				Items.thorium, 125,
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
		petroleumCrucible = new GenericCrafter("petroleum-crucible") {{
			requirements(Category.crafting, with(
				Items.titanium, 100,
				Items.plastanium, 75,
				Items.thorium, 100,
				Items.metaglass, 85,
				Items.silicon, 85,
				Items.graphite, 85
			));
			size = 4;
			hasItems = true;
			hasLiquids = true;
			itemCapacity = 70;
			liquidCapacity = 360f;
			craftTime = 1.25f * tick;

			craftEffect = new RadialEffect() {{
				effect = Fx.surgeCruciSmoke;
				amount = 4;
				rotationSpacing = 90;
				lengthOffset = 60 * px;
				rotationOffset = 45;
			}};
			updateEffect = new MultiEffect(
				UAWFx.plastHit,
				Fx.burning,
				Fx.fireSmoke
			);
			consumeItems(
				new ItemStack(Items.sand, 6),
				new ItemStack(Items.lead, 2),
				new ItemStack(UAWItems.anthracite, 1)
			);
			consumeLiquid(Liquids.oil, 1.5f);
			outputItems = with(
				Items.silicon, 7,
				Items.metaglass, 7
			);

			squareSprite = false;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidTile() {{
					padding = 16 * px;
				}},
				new DrawDefault(),
				new DrawGlowRegion() {{
					glowScale = 12;
					glowIntensity = 0.8f;
					color = UAWPal.drawGlowPink;
				}}
			);
		}};

		// Steam Crafters
		ironcladCompressor = new MultiCrafter("ironclad-compressor") {{
			requirements(Category.crafting, with(
				Items.titanium, 150,
				Items.copper, 325,
				Items.silicon, 145,
				Items.lead, 150,
				Items.graphite, 100,
				Items.plastanium, 100
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

			ignoreLiquidFullness = false;

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
						Seq.with(LiquidStack.with(Liquids.water, (steamInput / steamConversionScl) * steamLoseScl))
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
						4.8f
					);
					output = new IOEntry(
						Seq.with(ItemStack.with(Items.plastanium, 8)),
						Seq.with(LiquidStack.with(Liquids.water, (steamInput / steamConversionScl) * steamLoseScl))
					);
					craftTime = 90f;
				}},
				// Spore
				new Recipe() {{
					float steamInput = 120 / tick;
					input = new IOEntry(
						Seq.with(ItemStack.with(Items.sporePod, 4)),
						Seq.with()
					);
					output = new IOEntry(
						Seq.with(),
						Seq.with(LiquidStack.with(
							Liquids.oil, (18 * 4) / tick,
							Liquids.water, (steamInput / steamConversionScl) * steamLoseScl
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
						new DrawRegion("-top-plast")
					),
					// Spores
					new DrawMulti(
						new DrawRegion("-bottom"),
						new DrawLiquidTile() {{
							drawLiquid = Liquids.water;
							padding = 19 * px;
						}},
						new DrawCircles() {{
							color = Color.valueOf("7090ea").a(0.24f);
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
				UAWFx.cloudPuff(45, Color.white)
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
