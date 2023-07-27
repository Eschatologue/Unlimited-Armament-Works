package UAW.content.blocks;

import UAW.content.*;
import UAW.world.units.UnitConstructor;
import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.units.Reconstructor;

import static UAW.Vars.tick;
import static mindustry.type.ItemStack.with;

/** Contains Unit related Blocks such as factories, reconstructors, etc */
public class UAWBlocksUnits {
	public static Block placeholder,
	// Units Factory
	airGroundFactory, navalFactory,
	// Units Reconstructor
	unitReassemblyFacility1, unitReassemblyFacility2;

	public static void load() {
		airGroundFactory = new UnitConstructor("air-ground-factory") {{
			requirements(Category.units, with(
				Items.lead, 350,
				Items.silicon, 120,
				Items.metaglass, 120,
				Items.titanium, 450,
				UAWItems.stoutsteel, 50
			));
			size = 5;
			liquidCapacity = 240f;
			plans = Seq.with(
				new UnitPlan(UAWUnitTypes.crotchety, 35f * tick, with(
					Items.silicon, 75,
					Items.titanium, 90,
					Items.graphite, 75,
					Items.copper, 100,
					UAWItems.cryogel, 35
				)),
				new UnitPlan(UAWUnitTypes.aglovale, 50f * tick, with(
					Items.silicon, 90,
					Items.metaglass, 80,
					Items.titanium, 100,
					Items.graphite, 125,
					Items.copper, 125
				)),
				new UnitPlan(UAWUnitTypes.cavalier, 45f * tick, with(
					Items.silicon, 85,
					Items.titanium, 90,
					Items.graphite, 180,
					Items.copper, 165
				))
			);
			consumeLiquid(UAWLiquids.phlogiston, 60 / tick);
		}};
		navalFactory = new UnitConstructor("naval-factory") {{
			requirements(Category.units, with(
				Items.lead, 350,
				Items.silicon, 150,
				Items.metaglass, 150,
				Items.titanium, 425,
				UAWItems.stoutsteel, 100
			));
			size = 5;
			floating = true;
			requiresWater = true;
			liquidCapacity = 240f;
			plans = Seq.with(
				new UnitPlan(UAWUnitTypes.medea, 45f * tick, with(
					Items.silicon, 65,
					Items.metaglass, 60,
					Items.titanium, 100,
					Items.lead, 120
				)),
				new UnitPlan(UAWUnitTypes.mtb72, 55f * tick, with(
					Items.silicon, 65,
					Items.metaglass, 50,
					Items.titanium, 100,
					Items.blastCompound, 30,
					Items.lead, 120
				))
			);
			consumeLiquid(UAWLiquids.phlogiston, 60 / tick);
		}};

		unitReassemblyFacility1 = new Reconstructor("unit-reassembly-mk1") {{
			requirements(Category.units, with(
				Items.lead, 1000,
				Items.titanium, 2000,
				Items.thorium, 550,
				Items.plastanium, 500,
				UAWItems.stoutsteel, 550
			));

			size = 7;
			consumePower(7f);
			consumeItems(with(
				Items.silicon, 425,
				Items.metaglass, 325,
				UAWItems.stoutsteel, 250,
				Items.plastanium, 225
			));
			consumeLiquid(UAWLiquids.phlogiston, 60 / tick);

			constructTime = 80 * tick;
			liquidCapacity = 240f;
			placeableLiquid = true;

			upgrades.addAll(
				new UnitType[]{UAWUnitTypes.crotchety, UAWUnitTypes.cantankerous},
				new UnitType[]{UAWUnitTypes.aglovale, UAWUnitTypes.bedivere},
				new UnitType[]{UAWUnitTypes.medea, UAWUnitTypes.melpomene},
				new UnitType[]{UAWUnitTypes.cavalier, UAWUnitTypes.centurion},
				new UnitType[]{UAWUnitTypes.mtb72, UAWUnitTypes.mtb96}

			);
		}};
		unitReassemblyFacility2 = new Reconstructor("unit-reassembly-mk2") {{
			requirements(Category.units, with(
				Items.lead, 2500,
				Items.copper, 3250,
				Items.titanium, 2250,
				Items.silicon, 1325,
				Items.metaglass, 500,
				Items.plastanium, 600,
				UAWItems.stoutsteel, 650,
				Items.surgeAlloy, 600
			));

			size = 9;
			consumePower(20f);
			consumeItems(with(
				Items.silicon, 450,
				Items.metaglass, 450,
				Items.plastanium, 300,
				Items.surgeAlloy, 350,
				UAWItems.stoutsteel, 350
			));
			consumeLiquid(UAWLiquids.phlogiston, 90 / tick);

			constructTime = 225 * tick;
			liquidCapacity = 480f;
			placeableLiquid = true;

			upgrades.addAll(
				new UnitType[]{UAWUnitTypes.bedivere, UAWUnitTypes.calogrenant},
//				new UnitType[]{UAWUnitTypes.melpomene, UAWUnitTypes.minerva},
				new UnitType[]{UAWUnitTypes.centurion, UAWUnitTypes.caernarvon}
			);
		}};
	}
}
