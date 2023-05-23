package UAW.content.techTree;

import UAW.content.*;
import arc.struct.Seq;
import mindustry.content.*;
import mindustry.game.Objectives;

import static UAW.content.blocks.UAWBlocksDefence.*;
import static UAW.content.blocks.UAWBlocksPower.*;
import static UAW.content.blocks.UAWBlocksProduction.*;
import static mindustry.content.Blocks.*;
import static mindustry.content.TechTree.*;
import static mindustry.game.Objectives.Research;

public class UAWTechTree {
	static TechTree.TechNode context = null;

	public static void load() {

		nodeRoot(TechTreeContent.uawBegin.name, TechTreeContent.uawBegin, () -> {

			// Steam
			node(UAWLiquids.steam, Seq.with(new Objectives.SectorComplete(SectorPresets.groundZero)), () -> {

				// Power
				node(steamKettle, Seq.with(new Research(mechanicalPump)), () -> {
					node(industrialBoiler, () -> {
						node(pressureBoiler);
						node(geothermalBoiler);
					});
					node(steamTurbine, Seq.with(new Research(industrialBoiler)), () -> {
						node(advancedSteamTurbine);
					});
				});

				// Production
				node(steamBore, () -> {
					node(advancedSteamBore);
					node(steamThumper, () -> {
					});
					node(steamPump, Seq.with(new Research(rotaryPump)), () -> {
						node(pulsometerPump, () -> {
						});
					});
				});

				// Crafters
				node(gelatinizer, Seq.with(new Research(cryofluidMixer)), () -> {
					node(ironcladCompressor, Seq.with(new Research(multiPress), new Research(steamBore)), () -> {
						node(alloyCrucible, Seq.with(new Research(pressureBoiler)), () -> {
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

			// Phlogiston
			node(UAWLiquids.phlogiston, Seq.with(new Research(UAWItems.anthracite)), () -> {
				node(phlogistonCondenser, Seq.with(), () -> {
				});
			});

		});

	}
}
