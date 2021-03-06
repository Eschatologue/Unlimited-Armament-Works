package UAW.content.blocks;

import UAW.content.*;
import arc.struct.Seq;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.units.*;

import static UAW.Vars.tick;
import static mindustry.type.ItemStack.with;

/** Contains Unit related Blocks such as factories, reconstructors, etc */
public class UAWBlocksUnits implements ContentList {
	public static Block placeholder,
	// Units Factory
	UAWGroundFactory, UAWNavalFactory, UAWAirFactory,
	// Units Reconstructor
	exponentialPetroleumReconstructor, tetrativePetroleumReconstructor;

	@Override
	public void load() {
		UAWGroundFactory = new UnitFactory("uaw-ground-factory") {{
			requirements(Category.units, with(
				Items.lead, 550,
				Items.silicon, 90,
				Items.metaglass, 90,
				Items.titanium, 250,
				Items.plastanium, 100
			));
			size = 5;
			consumes.power(3.5f);
			consumes.liquid(Liquids.oil, 1f);
			liquidCapacity = 120f;
			plans = Seq.with(
				new UnitPlan(UAWUnitTypes.cavalier, 35f * tick, with(
					Items.silicon, 85,
					Items.titanium, 90,
					Items.lead, 150,
					Items.copper, 165
				))
			);
		}};
		UAWNavalFactory = new UnitFactory("uaw-naval-factory") {{
			requirements(Category.units, with(
				Items.lead, 550,
				Items.silicon, 60,
				Items.metaglass, 100,
				Items.titanium, 250,
				Items.plastanium, 100
			));
			floating = true;
			size = 5;
			consumes.power(3.5f);
			consumes.liquid(Liquids.oil, 1f);
			liquidCapacity = 120f;
			plans = Seq.with(
				new UnitPlan(UAWUnitTypes.arquebus, 45f * tick, with(
					Items.silicon, 65,
					Items.metaglass, 60,
					Items.titanium, 100,
					Items.lead, 120
				)),
				new UnitPlan(UAWUnitTypes.seabass, 55f * tick, with(
					Items.silicon, 55,
					Items.metaglass, 50,
					Items.titanium, 100,
					Items.blastCompound, 30,
					Items.lead, 120
				))
			);
		}};
		UAWAirFactory = new UnitFactory("uaw-air-factory") {{
			requirements(Category.units, with(
				Items.lead, 550,
				Items.silicon, 80,
				Items.metaglass, 80,
				Items.titanium, 250,
				Items.plastanium, 100
			));
			size = 5;
			consumes.power(3.5f);
			consumes.liquid(Liquids.oil, 1f);
			liquidCapacity = 120f;
			plans = Seq.with(
				new UnitPlan(UAWUnitTypes.aglovale, 35f * tick, with(
					Items.silicon, 100,
					Items.titanium, 125,
					Items.plastanium, 75,
					Items.lead, 150
				)),
				new UnitPlan(UAWUnitTypes.corsair, 30f * tick, with(
					Items.silicon, 85,
					Items.titanium, 90,
					Items.lead, 150,
					Items.plastanium, 75,
					Items.blastCompound, 35
				))
			);
		}};

		exponentialPetroleumReconstructor = new Reconstructor("exponential-petroleum-reconstructor") {{
			requirements(Category.units, with(
				Items.lead, 1000,
				Items.titanium, 2000,
				Items.thorium, 550,
				Items.plastanium, 500,
				UAWItems.titaniumCarbide, 550
			));

			size = 7;
			consumes.power(7f);
			consumes.items(with(
				Items.silicon, 425,
				Items.metaglass, 325,
				UAWItems.titaniumCarbide, 250,
				Items.plastanium, 225
			));
			consumes.liquid(Liquids.oil, 1.5f);

			constructTime = 80 * tick;
			liquidCapacity = 240f;
			placeableLiquid = true;

			upgrades.addAll(
				new UnitType[]{UAWUnitTypes.aglovale, UAWUnitTypes.bedivere},
				new UnitType[]{UAWUnitTypes.arquebus, UAWUnitTypes.carronade},
				new UnitType[]{UAWUnitTypes.cavalier, UAWUnitTypes.centurion},
				new UnitType[]{UAWUnitTypes.seabass, UAWUnitTypes.sharpnose}
//				new UnitType[]{UAWUnitTypes.corsair, UAWUnitTypes.vindicator}
			);
		}};
		tetrativePetroleumReconstructor = new Reconstructor("tetrative-petroleum-reconstructor") {{
			requirements(Category.units, with(
				Items.lead, 3500,
				Items.titanium, 2500,
				Items.silicon, 1250,
				Items.metaglass, 500,
				Items.plastanium, 600,
				UAWItems.titaniumCarbide, 600,
				Items.surgeAlloy, 700
			));

			size = 9;
			consumes.power(20f);
			consumes.items(with(
				Items.silicon, 550,
				Items.metaglass, 450,
				Items.plastanium, 300,
				Items.surgeAlloy, 350,
				UAWItems.titaniumCarbide, 350
			));
			consumes.liquid(Liquids.oil, 3.5f);

			constructTime = 225 * tick;
			liquidCapacity = 480f;
			placeableLiquid = true;

			upgrades.addAll(
				new UnitType[]{UAWUnitTypes.bedivere, UAWUnitTypes.calogrenant},
				new UnitType[]{UAWUnitTypes.carronade, UAWUnitTypes.falconet},
				new UnitType[]{UAWUnitTypes.centurion, UAWUnitTypes.caernarvon}
//			new UnitType[]{UAWUnitTypes.sharpnose, UAWUnitTypes.sturgeon}
//			new UnitType[]{UAWUnitTypes.vindicator, UAWUnitTypes.superfortress}
			);
		}};
	}
}
