package UAW.content.blocks;

import UAW.audiovisual.UAWPal;
import UAW.content.UAWLiquids;
import UAW.world.blocks.power.steam.*;
import UAW.world.drawer.DrawBoilerSmoke;
import mindustry.content.*;
import mindustry.gen.Sounds;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.power.ConsumeGenerator;
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
	steamKettle, industrialBoiler, pressureBoiler, blastBoiler, geothermalBoiler,

	// Heat Generation
	coalBurner, vapourHeater, LPGHeater, geothermalHeater;

	public static void load() {

		// Steam to Power
		steamTurbine = new ConsumeGenerator("steam-turbine") {{
			requirements(Category.power, with(
				Items.copper, 50,
				Items.graphite, 40,
				Items.lead, 60,
				Items.silicon, 45
			));
			size = 3;
			squareSprite = false;
			powerProduction = 8.5f;
			liquidCapacity = 90f;

			generateEffect = Fx.steam;
			effectChance = 0.1f;

			ambientSound = Sounds.smelter;
			ambientSoundVolume = 0.06f;

			drawer = new DrawMulti(
				new DrawDefault(),
				new DrawBlurSpin("-rotator", 6)
			);
			float steamInput = 45 / tick;
			consumeLiquid(UAWLiquids.steam, steamInput);
			outputLiquid = new LiquidStack(Liquids.water, (steamInput / steamConversionScl) * 0.8f);
		}};
		advancedSteamTurbine = new ConsumeGenerator("advanced-steam-turbine") {{
			requirements(Category.power, with(
				Items.copper, 250,
				Items.lead, 200,
				Items.silicon, 125,
				Items.graphite, 100,
				Items.plastanium, 95,
				Items.metaglass, 50
			));
			size = 4;
			squareSprite = false;
			powerProduction = 13.5f;
			liquidCapacity = 270f;

			generateEffect = Fx.steam;
			effectChance = 0.1f;

			ambientSound = Sounds.smelter;
			ambientSoundVolume = 0.06f;

			drawer = new DrawMulti(
				new DrawDefault(),
				new DrawBlurSpin("-rotator", 7)
			);
			float steamInput = 135 / tick;
			consumeLiquid(UAWLiquids.steam, steamInput);
			outputLiquid = new LiquidStack(Liquids.water, (steamInput / steamConversionScl) * 0.8f);
		}};

		// Steam Production
		steamKettle = new SteamBoiler("steam-kettle") {{
			requirements(Category.power, with(
				Items.copper, 12,
				Items.lead, 12
			));
			size = 1;

			float steamOutput = 15 / tick;
			consumeLiquid(Liquids.water, steamOutput);
			outputLiquid = new LiquidStack(UAWLiquids.steam, steamOutput * steamConversionScl);
			liquidCapacity = 180f;

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

		}};
		industrialBoiler = new SteamBoiler("industrial-boiler") {{
			requirements(Category.power, with(
				Items.copper, 55,
				Items.graphite, 35,
				Items.silicon, 35,
				Items.lead, 35
			));
			size = 2;

			float steamOutput = 30 / tick;
			consumeLiquid(Liquids.water, steamOutput);
			outputLiquid = new LiquidStack(UAWLiquids.steam, steamOutput * steamConversionScl);
			liquidCapacity = steamKettle.liquidCapacity * 2;

			squareSprite = false;
			drawer = new DrawMulti(
				new DrawDefault(),
				new DrawWarmupRegion() {{
					sinMag = 0.4f;
					sinScl = sinMag * 15f;
				}},
				new DrawBoilerSmoke() {{
					particles = 28;
					lifetime = 160f;
					size = 2.5f;
					spreadRadius = 5f;
				}}
			);
		}};
		pressureBoiler = new SteamBoiler("pressure-boiler") {{
			requirements(Category.power, with(
				Items.copper, 125,
				Items.lead, 100,
				Items.titanium, 85,
				Items.metaglass, 65,
				Items.silicon, 65,
				Items.graphite, 60
			));
			size = 4;

			float steamOutput = 120 / tick;
			consumeLiquid(Liquids.water, steamOutput);
			outputLiquid = new LiquidStack(UAWLiquids.steam, steamOutput * steamConversionScl);
			liquidCapacity = steamKettle.liquidCapacity * 4;

			squareSprite = false;
			drawer = new DrawMulti(
				new DrawDefault(),
				new DrawGlowRegion() {{
					glowScale = 3f;
					color = UAWPal.drawGlowOrange;
				}},
				new DrawBoilerSmoke() {{
					particles = 40;
					lifetime = 210f;
					size = 5f;
					spreadRadius = 14f;
				}}
			);
		}};
		geothermalBoiler = new AttributeSteamBoiler("geothermal-boiler") {{
			requirements(Category.power, with(
				Items.copper, 70,
				Items.graphite, 45,
				Items.silicon, 40,
				Items.lead, 50,
				Items.metaglass, 30
			));
			size = 3;
			updateEffect = Fx.steam;
			drawer = new DrawMulti(
				new DrawDefault(),
				new DrawGlowRegion() {{
					glowScale = 3f;
					color = UAWPal.drawGlowOrange;
				}}
			);
			attribute = Attribute.heat;
			float steamOutput = 90 / tick;
			consumeLiquid(Liquids.water, steamOutput);
			outputLiquid = new LiquidStack(UAWLiquids.steam, steamOutput * steamConversionScl);
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
//			drawer = new DrawMulti(
//				new DrawDefault(),
//				new DrawHeatOutput(),
//				new DrawFlame()
//			);
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
