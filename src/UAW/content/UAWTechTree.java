package UAW.content;

import arc.struct.Seq;
import mindustry.content.*;
import mindustry.content.TechTree.TechNode;
import mindustry.ctype.*;
import mindustry.game.Objectives.*;
import mindustry.type.ItemStack;

import static UAW.content.UAWBlock.*;
import static UAW.content.UAWItems.*;
import static UAW.content.UAWLiquid.surgeSolvent;
import static UAW.content.UAWUnitTypes.*;
import static mindustry.content.Blocks.*;
import static mindustry.content.Items.*;
import static mindustry.content.UnitTypes.*;

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
		// region Consumables
		/// Cryogel
		vanillaNode(pyratite, () ->
			node(cryogel)
		);
		/// Carbides
		vanillaNode(titanium, () -> node(titaniumCarbide, Seq.with(
			new Research(thorium)
		)));
		/// SurgeSolvent
		vanillaNode(surgeAlloy, () ->
			node(surgeSolvent)
		);
		// endregion
		// region Turrets
		vanillaNode(duo, () -> {
			// Quadra
			node(quadra, () -> node(spitfire, Seq.with(
				new Research(cyclone)
			)));
			// Solo
			node(solo, () -> node(longsword, Seq.with(
				new Research(fuse)
			)));
		});
		vanillaNode(salvo, () -> node(buckshot, () -> {
			// Tempest
			node(tempest, Seq.with(
				new Research(ripple)
			));
			// StrikeForce
			node(strikeforce, Seq.with(
				new Research(ripple)
			));
		}));
		vanillaNode(ripple, () -> {
			node(zounderkite, Seq.with(
				new Produce(Items.blastCompound),
				new Produce(Items.pyratite),
				new Produce(UAWItems.cryogel),
				new Produce(Items.plastanium),
				new Produce(Items.sporePod),
				new Produce(Items.surgeAlloy)
			), () ->
				node(skyhammer, Seq.with(
					new Research(spectre),
					new SectorComplete(SectorPresets.impact0078),
					new SectorComplete(SectorPresets.nuclearComplex)
				)));
		});
		// endregion
		// region Walls
		vanillaNode(phaseWall, () ->
			/// Shield Wall
			node(shieldWall, Seq.with(
				new Research(forceProjector),
				new Research(mendProjector)
			)));
		// endregion
		// region Effects
		vanillaNode(mendProjector, () -> {
			// Status Field
			node(statusFieldProjector, Seq.with(
				new Produce(Liquids.cryofluid),
				new Produce(Liquids.oil),
				new Produce(Liquids.slag)
			));
			// Rejuvinator
			node(rejuvinationProjector, Seq.with(new Research(overdriveProjector)), () ->
				node(rejuvinationDome, Seq.with(new Research(overdriveDome)
				)));
		});
		// endregion
		// region Crafters
		// Gelatinizer
		vanillaNode(blastMixer, () ->
			node(gelatinizer, Seq.with(
				new SectorComplete(SectorPresets.frozenForest),
				new Research(cryofluidMixer)
			)));
		// Carburizing Furnace
		vanillaNode(surgeSmelter, () ->
			node(carburizingFurnace, Seq.with(
				new SectorComplete(SectorPresets.windsweptIslands),
				new Research(siliconCrucible),
				new Research(titaniumCarbide)
			)));
		// Surge Mixer
		vanillaNode(separator, () ->
			node(surgeMixer, Seq.with(
				new Research(cryofluidMixer),
				new Produce(surgeAlloy)
			)));
		// Coal Liquefier
		vanillaNode(oilExtractor, () ->
			node(coalLiquefier, Seq.with(
				new Research(graphitePress),
				new Produce(Liquids.oil)
			)));
		// endregion
		// region Power
		vanillaNode(thermalGenerator, () ->
			node(UAWBlock.combustionGenerator, Seq.with(
				new Research(oilExtractor),
				new Research(differentialGenerator)
			))
		);
		// endregion
		// region Unit Blocks
		// Units Reconstuctors
		vanillaNode(multiplicativeReconstructor, () ->
			node(multiplicativePetroleumReconstructor, () ->
				node(exponentialPetroleumReconstructor, Seq.with(new Research(exponentialReconstructor)), () ->
					node(tetrativePetroleumReconstructor, Seq.with(new Research(tetrativeReconstructor))
					))));
		// endregion
		// Helis
		vanillaNode(zenith, () ->
			node(aglovale, () ->
				node(bedivere, () ->
					node(calogrenant, Seq.with(
						new Research(cryogel),
						new Research(antumbra)
					))
				)));
		// Tanks
		vanillaNode(mace, () ->
			node(gardlacz, () ->
				node(arkabuz)
			));
		// naval
		vanillaNode(minke, () ->
			node(clurit, Seq.with(new Research(atrax)), () ->
				node(kujang, Seq.with(new Research(spiroct)), () ->
					node(kerambit, Seq.with(
						new Research(aegires),
						new Research(forceProjector)
					))
				)));
		vanillaNode(bryde, () ->
			node(hatsuharu, Seq.with(new Research(blastCompound)), () ->
				node(shiratsuyu)
			));

		//  Region
	}
}
