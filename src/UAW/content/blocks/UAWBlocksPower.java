package UAW.content.blocks;

import UAW.audiovisual.*;
import UAW.content.UAWLiquids;
import UAW.world.blocks.power.*;
import UAW.world.consumers.ConsumeLiquidFuelExplosive;
import UAW.world.drawer.DrawFilterItemCrafterTop;
import mindustry.content.*;
import mindustry.entities.effect.*;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.draw.*;

import static UAW.Vars.*;
import static mindustry.type.ItemStack.with;

/** Contains Power Blocks or Blocks that produces Power */
public class UAWBlocksPower {
	public static Block placeholder,

	// Phlogiston Production
	industrialBlasterMk1, industrialBlasterMk2, industrialBlasterMk3,

	// Phlogiston to Power
	ignitionGeneratorMk1, ignitionGeneratorMk2, ignitionGeneratorMk3;

	public static void load() {

		// Phlogiston Production
		industrialBlasterMk1 = new PhlogistonBoiler("basic-blaster") {{
			requirements(Category.power, with(
				Items.copper, 85,
				Items.titanium, 35,
				Items.graphite, 35,
				Items.silicon, 35
			));
			size = 2;

			fuelEfficiencyMult = 1f;
			float liqIn = 30 / tick;
			consumeLiquid(Liquids.oil, liqIn);
			outputLiquid = new LiquidStack(UAWLiquids.phlogiston, liqIn * phlogConversionScl);
			liquidCapacity = 90;

			craftEffect = new MultiEffect(
				new RadialEffect(
					new MultiEffect(
						UAWFx.crucibleSmoke(130, UAWPal.phlogistonFront),
						UAWFx.crucibleSmoke(100, UAWPal.phlogistonMid)
					), 4, 90f, 5f)
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
		industrialBlasterMk2 = new PhlogistonBoiler("intermediate-blaster") {{
			requirements(Category.power, with(
				Items.copper, 150,
				Items.lead, 100,
				Items.titanium, 85,
				Items.metaglass, 65,
				Items.silicon, 65,
				Items.graphite, 60
			));
			size = 3;

			fuelEfficiencyMult = 1.25f;

			float liqIn = 120 / tick;
			consumeLiquid(Liquids.oil, liqIn);
			outputLiquid = new LiquidStack(UAWLiquids.phlogiston, liqIn * phlogConversionScl);
			liquidCapacity = industrialBlasterMk1.liquidCapacity * 3;

			craftEffect =
				new RadialEffect(
					new MultiEffect(
						UAWFx.crucibleSmoke(160, UAWPal.phlogistonFront),
						UAWFx.crucibleSmoke(145, UAWPal.phlogistonMid),
						UAWFx.crucibleSmoke(130, Pal.lightishGray)
					), 4, 90, 5f) {{
					rotationOffset = 45;
				}};

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
		industrialBlasterMk3 = new PhlogistonBoiler("advanced-blaster") {{
			requirements(Category.power, with(
				Items.copper, 150,
				Items.lead, 100,
				Items.titanium, 85,
				Items.metaglass, 65,
				Items.silicon, 65,
				Items.graphite, 60
			));
			size = 4;

			fuelEfficiencyMult = 1.50f;

			float liqIn = 360 / tick;
			consumeLiquid(Liquids.oil, liqIn);
			outputLiquid = new LiquidStack(UAWLiquids.phlogiston, liqIn * phlogConversionScl);
			liquidCapacity = industrialBlasterMk2.liquidCapacity * 3;

			craftEffect = new MultiEffect(
				new RadialEffect(
					new MultiEffect(
						UAWFx.crucibleSmoke(160, UAWPal.phlogistonFront),
						UAWFx.crucibleSmoke(145, UAWPal.phlogistonMid),
						UAWFx.crucibleSmoke(130, Pal.lightishGray)
					), 4, 90, 5f) {{
					rotationOffset = 45;
					lengthOffset = 22 * px;
				}},
				new RadialEffect(
					new MultiEffect(
						UAWFx.crucibleSmoke(130, UAWPal.phlogistonFront),
						UAWFx.crucibleSmoke(100, UAWPal.phlogistonMid),
						UAWFx.crucibleSmoke(160, Pal.lightishGray)
					), 4, 90f, 5f) {{
					lengthOffset = 23 * px;
				}}
			);

			squareSprite = false;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidTile(UAWLiquids.phlogiston, 38 * px),
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

		// Phlogiston to Power
		// TODO Sprite this thing & drawBlock stuff
		// TODO Make Bundle
		// TODO Effects & etc
		ignitionGeneratorMk1 = new FilterLiquidGenerator("ignition-generator-mk1") {{
			requirements(Category.power, with(
				Items.copper, 35,
				Items.titanium, 25,
				Items.graphite, 30,
				Items.lead, 40,
				Items.silicon, 30
			));
			size = 2;
			powerProduction = 325 / tick;
			hasLiquids = true;

			generateEffect = Fx.generatespark;

			ambientSound = Sounds.smelter;
			ambientSoundVolume = 0.06f;

			consume(new ConsumeLiquidFuelExplosive(60 / tick));
		}};
	}
}
