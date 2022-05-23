//package UAW.content;
//
//import arc.struct.Seq;
//import mindustry.content.*;
//import mindustry.content.TechTree.TechNode;
//import mindustry.ctype.*;
//import mindustry.game.Objectives.*;
//import mindustry.type.ItemStack;
//
//import static UAW.content.UAWItems.*;
//import static UAW.content.UAWLiquids.surgeSolvent;
//import static UAW.content.UAWUnitTypes.*;
//import static UAW.content.blocks.UAWBlocksDefense.*;
//import static UAW.content.blocks.UAWBlocksLogistic.*;
//import static UAW.content.blocks.UAWBlocksPower.*;
//import static UAW.content.blocks.UAWBlocksProduction.*;
//import static UAW.content.blocks.UAWBlocksUnits.*;
//import static mindustry.content.Blocks.*;
//import static mindustry.content.Items.*;
//
//public class UAWTechTree implements ContentList {
//	static TechTree.TechNode context = null;
//
//	private static void vanillaNode(UnlockableContent parent, Runnable children) {
//		context = TechTree.all.find(t -> t.content == parent);
//		children.run();
//	}
//
//	private static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objective> objectives, Runnable children) {
//		TechNode node = new TechNode(context, content, requirements);
//		if (objectives != null) node.objectives = objectives;
//
//		TechNode prev = context;
//		context = node;
//		children.run();
//		context = prev;
//	}
//
//	private static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objective> objectives) {
//		node(content, requirements, objectives, () -> {
//		});
//	}
//
//	private static void node(UnlockableContent content, Seq<Objective> objectives) {
//		node(content, content.researchRequirements(), objectives, () -> {
//		});
//	}
//
//	private static void node(UnlockableContent content, ItemStack[] requirements) {
//		node(content, requirements, Seq.with(), () -> {
//		});
//	}
//
//	private static void node(UnlockableContent content, ItemStack[] requirements, Runnable children) {
//		node(content, requirements, null, children);
//	}
//
//	private static void node(UnlockableContent content, Seq<Objective> objectives, Runnable children) {
//		node(content, content.researchRequirements(), objectives, children);
//	}
//
//	private static void node(UnlockableContent content, Runnable children) {
//		node(content, content.researchRequirements(), children);
//	}
//
//	private static void node(UnlockableContent block) {
//		node(block, () -> {
//		});
//	}
//
//	private static void nodeProduce(UnlockableContent content, Seq<Objective> objectives, Runnable children) {
//		node(content, content.researchRequirements(), objectives.and(new Produce(content)), children);
//	}
//
//	private static void nodeProduce(UnlockableContent content, Seq<Objective> objectives) {
//		nodeProduce(content, objectives, () -> {
//		});
//	}
//
//	private static void nodeProduce(UnlockableContent content, Runnable children) {
//		nodeProduce(content, Seq.with(), children);
//	}
//
//	private static void nodeProduce(UnlockableContent content) {
//		nodeProduce(content, Seq.with(), () -> {
//		});
//	}
//
//	@Override
//	public void load() {
//		// region Consumeables
//		vanillaNode(pyratite, () ->
//			// Cryogel
//			nodeProduce(cryogel)
//		);
//		vanillaNode(titanium, () ->
//			// Titanium Carbide
//			nodeProduce(compositeAlloy)
//		);
//
//		vanillaNode(surgeAlloy, () ->
//			// Surge Solvent
//			node(surgeSolvent)
//		);
//
//		vanillaNode(plastanium, () ->
//			// Anthracite
//			nodeProduce(anthracite)
//		);
//
//		vanillaNode(water, () ->
//			nodeProduce(UAWLiquids.steam)
//		);
//		// endregion consumeables
//		// region Turrets
//		vanillaNode(duo, () -> {
//				// Gatling Tree
//				node(quadra, () ->
//					node(spitfire,
//						Seq.with(
//							new Research(cyclone)
//						)
//					)
//				);
//				// Sniper Tree
//				node(ashlock, () ->
//					node(longbow,
//						Seq.with(
//							new Research(fuse)
//						), () ->
//							node(deadeye,
//								Seq.with(
//									new Research(foreshadow)
//								)
//							)
//					)
//				);
//			}
//		);
//
//		vanillaNode(hail, () ->
//			// Shotcannon Tree
//			node(buckshot, () -> {
//					node(tempest,
//						Seq.with(
//							new Research(cyclone)
//						)
//					);
//					node(strikeforce,
//						Seq.with(
//							new Research(ripple)
//						)
//					);
//				}
//			)
//		);
//
//		vanillaNode(ripple, () -> {
//				// Artillery tree
//				node(zounderkite,
//					Seq.with(
//						new Produce(Items.blastCompound),
//						new Produce(Items.pyratite),
//						new Produce(UAWItems.cryogel),
//						new Produce(Items.plastanium),
//						new Produce(Items.sporePod),
//						new Produce(Items.surgeAlloy)
//					), () ->
//						node(skyhammer,
//							Seq.with(
//								new Research(spectre),
//								new SectorComplete(SectorPresets.impact0078),
//								new SectorComplete(SectorPresets.nuclearComplex)
//							)
//						)
//				);
//			}
//		);
//		// endregion Turrets
//		// region Walls
//		vanillaNode(phaseWall, () -> {
//			// Special Walls
//			node(shieldWall,
//				Seq.with(
//					new Research(forceProjector),
//					new Research(mendProjector)
//				)
//			);
//		});
//		// endregion walls
//		// region Projectors
//		vanillaNode(mendProjector, () -> {
//				// Status Field Projector
//				node(statusFieldProjector,
//					Seq.with(
//						new Produce(Liquids.cryofluid),
//						new Produce(Liquids.oil),
//						new Produce(Liquids.slag)
//					)
//				);
//				// Rejuvination Projector
//				node(rejuvinationProjector,
//					Seq.with(
//						new Research(overdriveProjector)
//					), () ->
//						node(rejuvinationDome,
//							Seq.with(
//								new Research(overdriveDome)
//							)
//						)
//				);
//			}
//		);
//		// endregion Projectors
//		// region Crafters
//		vanillaNode(blastMixer, () ->
//			// Gelatinizer
//			node(gelatinizer,
//				Seq.with(
//					new SectorComplete(SectorPresets.frozenForest),
//					new Research(cryofluidMixer)
//				), () ->
//					node(cryofluidDistillery)
//			)
//		);
//
//		vanillaNode(siliconCrucible, () ->
//			// Petroleum Smelter
//			node(petroleumSmelter,
//				Seq.with(
//					new Research(surgeSmelter)
//				)
//			)
//		);
//
//		vanillaNode(disassembler, () -> {
//			// Surge Mixer
//			node(surgeMixer,
//				Seq.with(
//					new Research(cryofluidMixer)
//				)
//			);
//		});
//
//		//endregion Crafters
//
//		vanillaNode(mechanicalPump, () ->
//			// Steam Kettle | the start of steam system
//			node(steamKettle, () -> {
//				node(gasPipe, () -> {
//					node(gasJunction, () -> {
//						node(gasRouter, () -> {
//							node(gasBridge);
//							node(reinforcedGasPipe, () ->
//								node(platedGasPipe)
//							);
//						});
//						node(industrialBoiler, () -> {
//							node(pressureBoiler,
//								Seq.with(
//									new Research(platedGasPipe),
//									new Research(steamThumper)
//								)
//							);
//							node(geothermalBoiler);
//							node(steamPump, () ->
//								node(pulsometerPump, () ->
//									node(oilDerrick,
//										Seq.with(
//											new Research(pressureBoiler),
//											new Research(carburizingFurnace)
//										)
//									)
//								)
//							);
//						});
//						// Steam Turbine
//						node(steamTurbine, () -> {
//								node(advancedSteamTurbine);
//							}
//						);
//					});
//					// Steam Drills
//					node(steamDrill, () -> {
//						node(steamPress, () ->
//							node(plastaniumSteamPress)
//						);
//						node(advancedSteamDrill);
//						// Steam Thumper
//						node(steamThumper, () ->
//							node(carburizingFurnace,
//								Seq.with(
//									new Produce(anthracite)
//								)
//							)
//						);
//					});
//				});
//			})
//		);
//		vanillaNode(thermalPump, () ->
//			// Conduits
//			node(pressurizedConduit, () -> {
//					node(pressurizedLiquidRouter);
//					node(pressurizedLiquidJunction, () ->
//						node(pressurizedLiquidBridge, () ->
//							node(platedPressurizedConduit)
//						)
//					);
//				}
//			)
//		);
//
//		vanillaNode(liquidTank, () ->
//			// Cistern
//			node(liquidCistern)
//		);
//
//		// Unit Factory & Units
//		vanillaNode(additiveReconstructor, () ->
//			node(UAWGroundFactory,
//				Seq.with(
//					new Research(oilDerrick),
//					new Research(pressureBoiler),
//					new Research(plastanium)
//				), () -> {
//					node(cavalier, () ->
//						node(centurion, () ->
//							node(caernarvon)
//						)
//					);
//					node(UAWNavalFactory, () -> {
//						node(arquebus, () ->
//							node(carronade, () ->
//								node(falconet)
//							)
//						);
//						node(seabass, () ->
//							node(sharpnose)
//						);
//						node(UAWAirFactory, () -> {
//								node(aglovale, () ->
//									node(bedivere, () ->
//										node(calogrenant)
//									)
//								);
//								node(corsair);
//							}
//						);
//					});
//				})
//		);
//
//		// Unit Reconstructor
//		vanillaNode(multiplicativeReconstructor, () ->
//			node(exponentialPetroleumReconstructor,
//				Seq.with(
//					new Research(exponentialReconstructor
//					)
//				), () ->
//					node(tetrativePetroleumReconstructor,
//						Seq.with(
//							new Research(tetrativeReconstructor)
//						)
//					)
//			)
//		);
//
//	}
//}
