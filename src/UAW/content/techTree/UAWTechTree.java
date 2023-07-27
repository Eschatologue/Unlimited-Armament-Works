package UAW.content.techTree;

import UAW.content.*;
import arc.struct.Seq;
import mindustry.content.*;
import mindustry.game.Objectives;

import static UAW.content.blocks.UAWBlocksDefence.*;
import static UAW.content.blocks.UAWBlocksLogistic.*;
import static UAW.content.blocks.UAWBlocksPower.*;
import static UAW.content.blocks.UAWBlocksProduction.*;
import static mindustry.content.Blocks.*;
import static mindustry.content.TechTree.*;
import static mindustry.game.Objectives.Research;

public class UAWTechTree {
	static TechTree.TechNode context = null;

	public static void load() {

		nodeRoot(TechTreeContent.uawBegin.name, TechTreeContent.uawBegin, () -> {

			node(UAWLiquids.phlogiston, Seq.with(new Objectives.SectorComplete(SectorPresets.groundZero)), () -> {

				// Phlogiston Logistics
				node(hardenedConduit, () -> {
					node(stoutsteelConduit, () -> {
					});
					node(industrialBlasterMk1, () -> {
						node(industrialBlasterMk2, () -> {
							node(stoutsteelLiquidJunction, () -> {
								node(stoutsteelConduit, () -> {
									node(stoutsteelLiquidRouter, () -> {
										node(stoutsteelLiquidBridge);
									});
									node(stoutsteelConduitPlated);
								});
							});
							node(industrialBlasterMk3);
						});
					});
				});

				// Production
				node(blastBore, () -> {
					node(intermediateBlastBore);
					node(anthraciteThumperMk1, () -> {
					});
				});

				// Crafters
				node(gelatinizer, Seq.with(new Research(cryofluidMixer)), () -> {
					node(ironcladCompressor, Seq.with(new Research(multiPress), new Research(blastBore)), () -> {
						node(alloyCrucible, Seq.with(new Research(industrialBlasterMk2)), () -> {
						});
					});
					node(cryofluidBlender, Seq.with(new Research(cryofluidMixer)), () -> {
					});
				});

				// Resources
				nodeProduce(Liquids.water, () -> {
					nodeProduce(UAWItems.cryogel, () -> {
						nodeProduce(UAWItems.anthracite, () -> {
						});
						nodeProduce(UAWItems.stoutsteel, () -> {
						});
					});
				});

				// Armaments
				node(TechTreeContent.turret, () -> {

					node(TechTreeContent.turretMG, () -> {
						node(quadra, () -> {
							node(spitfire);
						});
					});
					node(TechTreeContent.turretSG, () -> {
						node(buckshot, () -> {
							node(tempest, () -> {
							});
							node(strikeforce, () -> {
							});
						});
					});
					node(TechTreeContent.turretART, () -> {
						node(ashlock, () -> {
							node(longsword, () -> {
								node(deadeye);
							});
							node(zounderkite, () -> {
								node(skyhammer);
							});
						});
					});
					node(TechTreeContent.turretMSL, () -> {

					});

				});
			});

		});

	}
}
