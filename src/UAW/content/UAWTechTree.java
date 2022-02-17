package UAW.content;

import arc.struct.Seq;
import mindustry.content.*;
import mindustry.content.TechTree.TechNode;
import mindustry.ctype.*;
import mindustry.game.Objectives.*;
import mindustry.type.ItemStack;

import static UAW.content.blocks.UAWBlocks.*;
import static UAW.content.blocks.UAWDefenseBlocks.*;
import static UAW.content.UAWItems.*;
import static UAW.content.UAWLiquids.surgeSolvent;
import static UAW.content.UAWUnitTypes.*;
import static mindustry.content.Blocks.*;
import static mindustry.content.Items.*;

public class UAWTechTree implements ContentList {
	static TechTree.TechNode context = null;

	private static void vanillaNode(UnlockableContent parent, Runnable children) {
		context = TechTree.all.find(t -> t.content == parent);
		children.run();
	}

	private static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objective> objectives, Runnable children) {
		TechNode node = new TechNode(context, content, requirements);
		if (objectives != null) node.objectives = objectives;

		TechNode prev = context;
		context = node;
		children.run();
		context = prev;
	}

	private static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objective> objectives) {
		node(content, requirements, objectives, () -> {
		});
	}

	private static void node(UnlockableContent content, Seq<Objective> objectives) {
		node(content, content.researchRequirements(), objectives, () -> {
		});
	}

	private static void node(UnlockableContent content, ItemStack[] requirements) {
		node(content, requirements, Seq.with(), () -> {
		});
	}

	private static void node(UnlockableContent content, ItemStack[] requirements, Runnable children) {
		node(content, requirements, null, children);
	}

	private static void node(UnlockableContent content, Seq<Objective> objectives, Runnable children) {
		node(content, content.researchRequirements(), objectives, children);
	}

	private static void node(UnlockableContent content, Runnable children) {
		node(content, content.researchRequirements(), children);
	}

	private static void node(UnlockableContent block) {
		node(block, () -> {
		});
	}

	private static void nodeProduce(UnlockableContent content, Seq<Objective> objectives, Runnable children) {
		node(content, content.researchRequirements(), objectives.and(new Produce(content)), children);
	}

	private static void nodeProduce(UnlockableContent content, Seq<Objective> objectives) {
		nodeProduce(content, objectives, () -> {
		});
	}

	private static void nodeProduce(UnlockableContent content, Runnable children) {
		nodeProduce(content, Seq.with(), children);
	}

	private static void nodeProduce(UnlockableContent content) {
		nodeProduce(content, Seq.with(), () -> {
		});
	}

	@Override
	public void load() {

		vanillaNode(pyratite, () ->
			node(cryogel)
		);
		vanillaNode(titanium, () ->
			node(titaniumCarbide,
				Seq.with(
					new Research(thorium),
					new Research(surgeAlloy)
				)
			)
		);

		vanillaNode(surgeAlloy, () ->
			node(surgeSolvent)
		);

		vanillaNode(plastanium, () ->
			node(anthracite,
				Seq.with(
					new SectorComplete(SectorPresets.tarFields),
					new Research(titaniumCarbide)
				)
			)
		);

		vanillaNode(duo, () -> {
				node(quadra, () ->
					node(spitfire,
						Seq.with(
							new Research(cyclone)
						)
					)
				);
				node(solo, () ->
					node(longsword,
						Seq.with(
							new Research(fuse)
						), () ->
							node(deadeye,
								Seq.with(
									new Research(foreshadow)
								)
							)
					)
				);
			}
		);

		vanillaNode(hail, () ->
			node(buckshot, () -> {
					node(tempest,
						Seq.with(
							new Research(cyclone)
						)
					);
					node(strikeforce,
						Seq.with(
							new Research(ripple)
						)
					);
				}
			)
		);

		vanillaNode(ripple, () -> {
				node(zounderkite,
					Seq.with(
						new Produce(Items.blastCompound),
						new Produce(Items.pyratite),
						new Produce(UAWItems.cryogel),
						new Produce(Items.plastanium),
						new Produce(Items.sporePod),
						new Produce(Items.surgeAlloy)
					), () ->
						node(skyhammer,
							Seq.with(
								new Research(spectre),
								new SectorComplete(SectorPresets.impact0078),
								new SectorComplete(SectorPresets.nuclearComplex)
							)
						)
				);
			}
		);

		vanillaNode(phaseWall, () -> {
			node(shieldWall,
				Seq.with(
					new Research(forceProjector),
					new Research(mendProjector)
				)
			);
		});

		vanillaNode(mendProjector, () -> {
				node(statusFieldProjector,
					Seq.with(
						new Produce(Liquids.cryofluid),
						new Produce(Liquids.oil),
						new Produce(Liquids.slag)
					)
				);
				node(rejuvinationProjector,
					Seq.with(
						new Research(overdriveProjector)
					), () ->
						node(rejuvinationDome,
							Seq.with(
								new Research(overdriveDome)
							)
						)
				);
			}
		);

		vanillaNode(blastMixer, () ->
			node(gelatinizer,
				Seq.with(
					new SectorComplete(SectorPresets.frozenForest),
					new Research(cryofluidMixer)
				), () ->
					node(cryofluidDistillery)
			)
		);

		vanillaNode(surgeSmelter, () ->
			node(carburizingFurnace,
				Seq.with(
					new SectorComplete(SectorPresets.windsweptIslands),
					new Research(siliconCrucible),
					new Research(titaniumCarbide)
				)
			)
		);

		vanillaNode(siliconCrucible, () ->
			node(petroleumSmelter,
				Seq.with(
					new Research(surgeSmelter)
				)
			)
		);

		vanillaNode(disassembler, () -> {
			node(surgeMixer,
				Seq.with(
					new Research(cryofluidMixer)
				)
			);
			node(petrochemicalSeperator,
				Seq.with(
					new Research(oilDerrick),
					new Research(anthracite)
				)
			);
		});

		vanillaNode(plastaniumCompressor, () ->
			node(plastaniumForge,
				Seq.with(
					new Research(oilDerrick),
					new Research(surgeSmelter),
					new Research(petrochemicalSeperator)
				)
			)
		);

		vanillaNode(thoriumReactor, () ->
			node(petroleumGenerator,
				Seq.with(
					new Research(oilExtractor),
					new Research(differentialGenerator)
				)
			)
		);

		vanillaNode(oilExtractor, () ->
			node(oilDerrick,
				Seq.with(
					new SectorComplete(SectorPresets.tarFields),
					new SectorComplete(SectorPresets.frozenForest),
					new Research(thermalPump)
				)
			)
		);

		vanillaNode(thermalPump, () ->
			node(rotodynamicPump,
				Seq.with(
					new Research(oilExtractor),
					new Research(titaniumCarbide)
				), () ->
					node(pressurizedConduit, () -> {
							node(pressurizedLiquidRouter);
							node(pressurizedLiquidJunction, () ->
								node(pressurizedLiquidBridge));
						}
					)
			)
		);

		vanillaNode(liquidTank, () ->
			node(liquidCistern)
		);

		vanillaNode(additiveReconstructor, () ->
			node(UAWGroundFactory,
				Seq.with(
					new Research(oilDerrick),
					new Research(plastanium),
					new SectorComplete(SectorPresets.tarFields)
				), () -> {
					node(cavalier, () ->
						node(centurion, () ->
							node(caernarvon)
						)
					);
					node(UAWNavalFactory, () -> {
						node(arquebus, () ->
							node(carronade, () ->
								node(falconet)
							)
						);
						node(seabass, () ->
							node(sharpnose)
						);
						node(UAWAirFactory, () -> {
								node(aglovale, () ->
									node(bedivere, () ->
										node(calogrenant)
									)
								);
								node(corsair);
							}
						);
					});
				})
		);

		vanillaNode(multiplicativeReconstructor, () ->
			node(exponentialPetroleumReconstructor,
				Seq.with(
					new Research(exponentialReconstructor
					)
				), () ->
					node(tetrativePetroleumReconstructor,
						Seq.with(
							new Research(tetrativeReconstructor)
						)
					)
			)
		);

	}
}
