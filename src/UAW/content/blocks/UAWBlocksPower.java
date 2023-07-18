package UAW.content.blocks;

import UAW.audiovisual.*;
import UAW.content.UAWLiquids;
import UAW.world.blocks.power.PhlogistonBoiler;
import UAW.world.blocks.power.steam.AttributeSteamBoiler;
import UAW.world.drawer.DrawFilterItemCrafterTop;
import mindustry.content.*;
import mindustry.entities.effect.*;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;

import static UAW.Vars.*;
import static mindustry.type.ItemStack.with;

/** Contains Power Blocks or Blocks that produces Power */
public class UAWBlocksPower {
	public static Block placeholder,

	// Turbines
	steamTurbine, advancedSteamTurbine,

	// Phlogiston Production
	industrialBlasterMk1, industrialBlasterMk2, geothermalBoiler,

	// Heat Generation
	coalBurner, vapourHeater, LPGHeater, geothermalHeater;

	public static void load() {

		// Phlogiston Production
		industrialBlasterMk1 = new PhlogistonBoiler("basic-industrial-blaster") {{
			requirements(Category.power, with(
				Items.copper, 85,
				Items.titanium, 35,
				Items.graphite, 35,
				Items.silicon, 35
			));
			size = 2;

			fuelEfficiencyMult = 1.25f;
			float liqIn = 30 / tick;
			consumeLiquid(Liquids.oil, liqIn);
			outputLiquid = new LiquidStack(UAWLiquids.phlogiston, liqIn * phlogConversionScl);
			liquidCapacity = 90;

			craftEffect = new MultiEffect(
				new RadialEffect(UAWFx.crucibleSmoke(145, UAWPal.phlogistonFront), 4, 90f, 5f),
				new RadialEffect(UAWFx.crucibleSmoke(130, UAWPal.phlogistonMid), 4, 90f, 5f)
			);

			squareSprite = false;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidTile(UAWLiquids.phlogiston, 8 * px),
				new DrawCircles() {{
					color = UAWPal.phlogistonMid.cpy().a(0.24f);
					strokeMax = 1.75f;
					radius = 6f;
					amount = 4;
				}},
				new DrawDefault(),
				new DrawGlowRegion() {{
					suffix = "-glow";
					glowScale = 3f;
					color = UAWPal.drawGlowOrange;
				}},
				new DrawFilterItemCrafterTop()
			);
		}};
		industrialBlasterMk2 = new PhlogistonBoiler("intermediate-industrial-blaster") {{
			requirements(Category.power, with(
				Items.copper, 150,
				Items.lead, 100,
				Items.titanium, 85,
				Items.metaglass, 65,
				Items.silicon, 65,
				Items.graphite, 60
			));
			size = 3;

			float liqIn = 120 / tick;
			consumeLiquid(Liquids.oil, liqIn);
			outputLiquid = new LiquidStack(UAWLiquids.phlogiston, liqIn * phlogConversionScl);
			liquidCapacity = industrialBlasterMk1.liquidCapacity * 3;

			craftEffect = new MultiEffect(
				new RadialEffect(UAWFx.crucibleSmoke(160, UAWPal.phlogistonFront), 4, 45f, 5f),
				new RadialEffect(UAWFx.crucibleSmoke(145, UAWPal.phlogistonMid), 4, 45f, 5f)
			);

			squareSprite = false;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidTile(UAWLiquids.phlogiston, 8 * px),
				new DrawCircles() {{
					color = UAWPal.phlogistonMid.cpy().a(0.24f);
					strokeMax = 1.75f;
					radius = 10f;
					amount = 5;
				}},
				new DrawDefault(),
				new DrawGlowRegion() {{
					suffix = "-glow";
					glowScale = 3f;
					color = UAWPal.drawGlowOrange;
				}},
				new DrawFilterItemCrafterTop()
			);
		}};
		geothermalBoiler = new AttributeSteamBoiler("geothermal-boiler") {{
			requirements(Category.power, with(
				Items.copper, 90,
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
			outputLiquid = new LiquidStack(UAWLiquids.steam, steamOutput * phlogConversionScl);
		}};

	}
}
