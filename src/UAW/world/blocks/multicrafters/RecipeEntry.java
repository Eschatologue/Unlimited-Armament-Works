package UAW.world.blocks.multicrafters;

import arc.struct.Seq;
import mindustry.type.*;
import multicraft.IOEntry;

// TODO
public class RecipeEntry extends IOEntry {

	public RecipeEntry(Seq<ItemStack> items) {
		this(items, Seq.with(), 0f);
	}

	public RecipeEntry(Seq<ItemStack> items, Seq<LiquidStack> fluids) {
		this(items, fluids, 0f);
	}

	public RecipeEntry(Seq<ItemStack> items, Seq<LiquidStack> fluids, float power) {
		this.items = items;
		this.fluids = fluids;
		this.power = power;
	}

	public RecipeEntry() {
	}
}
