package UAW.world.blocks.production;

import UAW.world.consumers.*;
import arc.Core;
import arc.graphics.Color;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.GenericCrafter;

/** Generic Crafter with its efficiency tied to filtered item or liquid used */
public class FilterGenericCrafter extends GenericCrafter {
	/** Whenever the efficiency bar color will follow the item */
	public boolean adaptiveBarColor = true;

	public FilterGenericCrafter(String name) {
		super(name);
		ambientSound = Sounds.smelter;
		ambientSoundVolume = 0.06f;
	}

	@Override
	public void setBars() {
		super.setBars();
		if (hasItems) {
			addBar("efficiency", (FilterGenericCrafterBuild entity) ->
				new Bar(
					() -> Core.bundle.format("bar.uaw-item-efficiency", (int) (entity.itemMultProgress() * 100)),
					() -> adaptiveBarColor ? entity.itemColor() : Pal.lightOrange,
					entity::itemMultProgress
				));
		}
	}

	public class FilterGenericCrafterBuild extends GenericCrafterBuild {
		public boolean useItemFuel = false, useLiquidFuel = false;
		public Color itemColor = Pal.bar;
		public float itemEfficiency;
		public float liquidEfficiency;

		@Override
		public void pickedUp() {
			warmup = 0f;
			itemEfficiency = 0f;
			liquidEfficiency = 0f;
		}

		/** The warmup progress of the crafters, affects {@link #itemMultProgress()} */
		public float warmupProgress() {
			return warmup;
		}

		/**
		 * The Efficiency of the loaded items used as fuel, affects {@link #itemMultProgress()}, also see {@link
		 * ConsumeItemFuelFlammable}
		 */
		public float itemEfficiency() {
			return warmupProgress() > 0 ? itemEfficiency : 0;
		}

		/**
		 * The Efficiency of the loaded items used as fuel, affects {@link #itemMultProgress()}, also see {@link
		 * ConsumeLiquidFuelFlammable}
		 */
		public float liquidEfficiency() {
			return warmupProgress() > 0 ? liquidEfficiency : 0;
		}

		/** The Color of the loaded items, affects {@link #setBars()} */
		public Color itemColor() {
			return warmupProgress() > 0 ? itemColor : Pal.bar;
		}

		/** Item modifier that affects {@link #getProgressIncrease} */
		public float itemMultProgress() {
			return itemEfficiency() * warmupProgress();
		}

		/** Liquid modifier that affects {@link #getProgressIncrease} */
		public float liquidMultProgress() {
			return liquidEfficiency() * warmupProgress();
		}

		@Override
		public float getProgressIncrease(float base) {
			return super.getProgressIncrease(base) * (useItemFuel ? itemMultProgress() : 1) * (useLiquidFuel ? liquidMultProgress() : 1);
		}

	}
}




