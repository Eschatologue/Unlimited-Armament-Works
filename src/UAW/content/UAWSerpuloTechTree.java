package UAW.content;


import UAW.content.blocks.UAWBlocksUnits;
import arc.struct.Seq;
import mindustry.content.*;

import static UAW.Vars.modName;
import static UAW.content.UAWItems.*;
import static UAW.content.UAWUnitTypes.*;
import static UAW.content.blocks.UAWBlocksDefense.*;
import static UAW.content.blocks.UAWBlocksLogistic.*;
import static UAW.content.blocks.UAWBlocksPower.*;
import static UAW.content.blocks.UAWBlocksProduction.*;
import static UAW.content.blocks.UAWBlocksUnits.*;
import static mindustry.content.Blocks.*;
import static mindustry.content.TechTree.*;
import static mindustry.game.Objectives.*;

public class UAWSerpuloTechTree {
	static TechTree.TechNode context = null;

	public static void load() {
		nodeRoot(modName, UAWLiquids.steam, () -> {

			// Items
			nodeProduce(Liquids.oil, () -> {
				nodeProduce(anthracite, () -> {
					nodeProduce(stoutsteel, () -> {
					});
				});

				nodeProduce(Liquids.cryofluid, () -> {
					nodeProduce(cryogel, () -> {
					});
				});

			});

			// Power etc
			node(steamKettle, Seq.with(new Research(mechanicalPump)), () -> {
				node(industrialBoiler, () -> {
					node(pressureBoiler);
					node(geothermalBoiler);
					node(steamTurbine, () -> {
						node(advancedSteamTurbine);
					});
				});

				node(steamDrill, () -> {
					node(advancedSteamDrill);
					node(steamPress, Seq.with(new Research(multiPress)), () -> {
					});
					node(steamThumper, () -> {
						node(alloyCrucible, () -> {
						});
					});
				});

				node(steamPump, Seq.with(new Research(rotaryPump)), () -> {
					node(pulsometerPump, () -> {
						node(pressurizedConduit, Seq.with(new Research(platedConduit), new Produce(stoutsteel)), () -> {
							node(platedPressurizedConduit);
							node(pressurizedLiquidRouter, () ->
								node(pressurizedLiquidJunction, () ->
									node(pressurizedLiquidBridge)
								));
						});
					});

					node(gelatinizer, Seq.with(new Research(cryofluidMixer)), () -> {
						node(cryofluidBlender);
					});

				});
			});

			// Turrets
			node(buckshot, () -> {
				node(quadra, () -> {
					node(spitfire);
				});

				node(ashlock, () -> {
					node(longbow, () -> {
						node(deadeye);
					});
				});

				node(zounderkite, Seq.with(new Research(quadra), new Research(ashlock), new Research(salvo)), () -> {
					node(stoutSteelWall, () -> {
						node(stoutSteelWallLarge);
					});
					node(skyhammer, Seq.with(new Research(ripple)), () -> {
					});
				});
				node(tempest);
				node(strikeforce);
			});

			// Units
			node(airGroundFactory, () -> {
				node(crotchety, () -> {
					node(cantankerous, Seq.with(new Research(gelatinizer)), () -> {
					});
				});
				node(aglovale, () -> {
					node(bedivere, () -> {
						node(calogrenant, () -> {
						});
					});
				});

				node(cavalier, Seq.with(new Research(crotchety)), () -> {
					node(centurion, () -> {
						node(caernarvon);
					});
				});

				node(UAWBlocksUnits.navalFactory, () -> {
					node(megaera, () -> {
						node(alecto);
					});
					node(arquebus, () -> {
						node(carronade, () -> {
							node(falconet);
						});
					});
				});

				node(exponentialPetroleumReconstructor, () -> {
					node(tetrativePetroleumReconstructor);
				});
			});

		});
	}
}
