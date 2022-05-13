package UAW.content.blocks;

import UAW.content.UAWLiquids;
import UAW.world.blocks.power.*;
import UAW.world.drawer.DrawBoilerSmoke;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;

import static mindustry.type.ItemStack.with;

/** Contains Power Blocks or Blocks that produces Power */
public class UAWBlocksPower {
	public static Block placeholder,

	steamTurbine, advancedSteamTurbine,

	steamKettle, steamBoiler, pressureBoiler, geothermalBoiler, solarBoiler;

	public static void load() {

		// Steam to Power
		steamTurbine = new ConsumeGenerator("steam-turbine") {{
			requirements(Category.power, with(
				Items.copper, 65,
				Items.graphite, 40,
				Items.lead, 55,
				Items.silicon, 15
			));
			size = 3;
			squareSprite = false;
			powerProduction = 6f;
			liquidCapacity = 120f;
			drawer = new DrawMulti(
				new DrawDefault(),
				new DrawBlurSpin("-rotator", 8),
				new DrawRegion("-top")

			);
			consumeLiquid(UAWLiquids.steam, 0.5f);
		}};

		advancedSteamTurbine = new ConsumeGenerator("steam-turbine") {{
			requirements(Category.power, with(
				Items.copper, 90,
				Items.titanium, 60,
				Items.lead, 150,
				Items.silicon, 90,
				Items.metaglass, 45
			));
			size = 4;
			squareSprite = false;
			powerProduction = 20f;
			liquidCapacity = 320f;
			drawer = new DrawMulti(
				new DrawDefault(),
				new DrawBlurSpin("-rotator-1", 8),
				new DrawBlurSpin("-rotator-2", -8),
				new DrawRegion("-top")

			);
			consumeLiquid(UAWLiquids.steam, 0.5f);
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
			liquidOutput = new LiquidStack(UAWLiquids.steam, 0.25f * 3);

		}};
		steamBoiler = new LiquidBoiler("steam-boiler") {{
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
			liquidOutput = new LiquidStack(UAWLiquids.steam, 0.5f * 3);
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
			liquidOutput = new LiquidStack(UAWLiquids.steam, 2f * 3);
		}};

		geothermalBoiler = new AttributeLiquidBoiler("geothermal-boiler") {{
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
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidTile(UAWLiquids.steam, 4f),
				new DrawDefault(),
				new DrawRegion("-top")
			);
			attribute = Attribute.heat;
			consumeLiquid(Liquids.water, 0.5f);
			outputLiquid = new LiquidStack(UAWLiquids.steam, 0.5f * 3);
		}};
	}
}
