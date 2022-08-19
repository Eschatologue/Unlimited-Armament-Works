package UAW.content.blocks;

import UAW.content.UAWLiquids;
import UAW.world.blocks.power.steam.LiquidBoiler;
import UAW.world.drawer.DrawBoilerSmoke;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.blocks.production.AttributeCrafter;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;

import static UAW.Vars.*;
import static mindustry.type.ItemStack.with;

/** Contains Power Blocks or Blocks that produces Power */
public class UAWBlocksPower {
	public static Block placeholder,

	// Turbines
	steamTurbine, advancedSteamTurbine,

	// Steam Production
	steamKettle, industrialBoiler, pressureBoiler, geothermalBoiler,

	// Heat Generation
	coalBurner, vapourHeater, LPGHeater, geothermalHeater;

	public static void load() {

		// Steam to Power
		steamTurbine = new ConsumeGenerator("steam-turbine") {{
			requirements(Category.power, with(
				Items.copper, 75,
				Items.graphite, 60,
				Items.lead, 95,
				Items.silicon, 35
			));
			size = 3;
			squareSprite = false;
			powerProduction = 7.5f;
			liquidCapacity = 120f;

			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidTile(UAWLiquids.steam, 2f),
				new DrawGlowRegion() {{
					suffix = "-heat-bottom";
				}},
				new DrawDefault(),
				new DrawBlurSpin("-rotator", 8),
				new DrawRegion("-top"),
				new DrawGlowRegion() {{
					suffix = "-heat-top";
				}},
				new DrawBoilerSmoke() {{
					particles = 35;
				}}
			);
			consumeLiquid(UAWLiquids.steam, 0.65f);
		}};
		advancedSteamTurbine = new ConsumeGenerator("advanced-steam-turbine") {{
			requirements(Category.power, with(
				Items.copper, 90,
				Items.titanium, 60,
				Items.lead, 150,
				Items.silicon, 90,
				Items.metaglass, 45
			));
			size = 4;
			squareSprite = false;
			powerProduction = 19.5f;
			liquidCapacity = 320f;

			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidTile(UAWLiquids.steam, 3f),
				new DrawDefault(),
				new DrawBlurSpin("-rotator", 7),
				new DrawPistons() {{
					sinMag = 3f;
					sinScl = 4f;
				}},
				new DrawRegion("-base-mid"),
				new DrawRegion("-top-mid"),
				new DrawLiquidTile(UAWLiquids.steam, 52 * px),
				new DrawRegion("-top"),
				new DrawGlowRegion() {{
					suffix = "-heat-top";
				}},
				new DrawBoilerSmoke() {{
					particles = 45;
					spreadRadius = 8;
				}}
			);

			consumeLiquid(UAWLiquids.steam, 0.65f);
		}};
		steamKettle = new LiquidBoiler("steam-kettle") {{
			requirements(Category.power, with(
				Items.copper, 12,
				Items.lead, 12
			));
			size = 1;
			squareSprite = false;
			drawer = new DrawMulti(
				new DrawDefault(),
				new DrawWarmupRegion(),
				new DrawBoilerSmoke() {{
					size = 1.5f;
					particles = 18;
					lifetime = 150f;
					spreadRadius = 3f;
				}}
			);
			consume(new ConsumeItemFlammable());
			consume(new ConsumeItemExplode());
			consumeLiquid(Liquids.water, 0.25f);
			outputLiquid = new LiquidStack(UAWLiquids.steam, 0.25f * 3);

		}};
		industrialBoiler = new LiquidBoiler("industrial-boiler") {{
			requirements(Category.power, with(
				Items.copper, 35,
				Items.graphite, 25,
				Items.lead, 30
			));
			size = 2;
			squareSprite = false;
			drawer = new DrawMulti(
				new DrawDefault(),
				new DrawWarmupRegion(),
				new DrawBoilerSmoke() {{
					particles = 35;
					lifetime = 160f;
					size = 3f;
					spreadRadius = 5f;
				}}
			);
			consume(new ConsumeItemFlammable());
			consume(new ConsumeItemExplode());
			consumeLiquid(Liquids.water, 0.5f);
			outputLiquid = new LiquidStack(UAWLiquids.steam, 0.5f * 3);
		}};
		pressureBoiler = new LiquidBoiler("pressure-boiler") {{
			requirements(Category.power, with(
				Items.copper, 150,
				Items.lead, 125,
				Items.titanium, 65,
				Items.graphite, 50
			));
			size = 4;
			squareSprite = false;
			itemCapacity = 30;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidTile(UAWLiquids.steam, 5f),
				new DrawDefault(),
				new DrawRegion("-top")
			);
			consume(new ConsumeItemFlammable());
			consumeLiquid(Liquids.water, 2f);
			outputLiquid = new LiquidStack(UAWLiquids.steam, 2f * 3);
		}};
		geothermalBoiler = new AttributeCrafter("geothermal-boiler") {{
			requirements(Category.power, with(
				Items.copper, 65,
				Items.graphite, 55,
				Items.lead, 30,
				Items.metaglass, 30
			));
			size = 3;
			squareSprite = false;
			hasItems = false;
			acceptsItems = false;
			minEfficiency = 0.1f;
			baseEfficiency = 0f;
			boostScale = 0.25f;
			maxBoost = 3f;
			craftTime = 2 * tick;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidTile(UAWLiquids.steam, 4f),
				new DrawDefault(),
				new DrawRegion("-top")
			);
			attribute = Attribute.heat;
			consumeLiquid(Liquids.water, 0.25f);
			outputLiquid = new LiquidStack(UAWLiquids.steam, 0.25f);
		}};

		// Heat Generation
//		coalBurner = new HeatProducer("coal-burner") {{
//			requirements(Category.power, with(
//				Items.copper, 45,
//				Items.lead, 25,
//				Items.graphite, 15
//			));
//
//			size = 2;
//
//			craftTime = 30f;
//			consumeItem(Items.coal, 2);
//			heatOutput = 6f;
//
//			updateEffect = Fx.burning;
//
//			squareSprite = false;
//			drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput(), new DrawFlame());
//
//		}};
//		vapourHeater = new HeatProducer("vapour-heater") {{
//			requirements(Category.power, with(
//				Items.copper, 55,
//				Items.lead, 35,
//				Items.graphite, 20,
//				Items.silicon, 20
//			));
//			researchCostMultiplier = 4f;
//
//			size = 3;
//			rotateDraw = false;
//			regionRotated1 = 1;
//
//			craftTime = 30f;
//			consumeLiquid(UAWLiquids.steam, 0.5f * 3);
//			liquidCapacity = 40f;
//
//			heatOutput = 12f;
//			outputLiquid = new LiquidStack(Liquids.water, 0.5f);
//			ignoreLiquidFullness = false;
//
//			squareSprite = false;
//			drawer = new DrawMulti(
//				new DrawRegion("-bottom"),
//				new DrawLiquidTile(UAWLiquids.steam, 2),
//				new DrawDefault(),
//				new DrawCells(){{
//					color = UAWPal.steamFront;
//					particleColorFrom = UAWPal.steamBack;
//					particleColorTo = UAWPal.steamMid;
//					particles = 35;
//					range = 6f;
//				}},
//				new DrawHeatOutput(),
//				new DrawRegion("-glass")
//			);
//		}};

	}
}
