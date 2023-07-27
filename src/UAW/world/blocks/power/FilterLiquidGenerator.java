package UAW.world.blocks.power;

import arc.Core;
import arc.graphics.Color;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.power.ConsumeGenerator;

public class FilterLiquidGenerator extends ConsumeGenerator {
	public boolean useFuelColor = true;

	public FilterLiquidGenerator(String name) {
		super(name);
		squareSprite = false;
	}

	@Override
	public void setBars() {
		super.setBars();
		addBar("efficiency", (FilterLiquidGeneratorBuild entity) -> new Bar(
			() -> Core.bundle.format("bar.uaw-liq-efficiency", (int) (entity.liquidEfficiency() * 100)),
			() -> useFuelColor ? entity.getLiquidColor() : Pal.ammo,
			entity::liquidEfficiency
		));
	}

	public class FilterLiquidGeneratorBuild extends ConsumeGeneratorBuild {
		public Color liquidColor = Pal.bar;
		public float liquidEfficiency;
		public float currentLiquidQ = liquids.get(liquids.current()) / block.liquidCapacity;

		public Color getLiquidColor() {
			return currentLiquidQ > 0 ? liquidColor : Pal.bar;
		}

		public float liquidEfficiency() {
			return currentLiquidQ > 0 ? liquidEfficiency : 0;
		}
	}
}
