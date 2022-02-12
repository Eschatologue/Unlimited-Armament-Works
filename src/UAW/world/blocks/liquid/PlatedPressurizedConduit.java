package UAW.world.blocks.liquid;

import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.*;
import mindustry.world.blocks.liquid.Conduit;

public class PlatedPressurizedConduit extends PressurizedConduit {
	public PlatedPressurizedConduit(String name) {
		super(name);
	}

	@Override
	public boolean blends(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock) {
		return (otherblock.outputsLiquid && blendsArmored(tile, rotation, otherx, othery, otherrot, otherblock))
			|| (lookingAt(tile, rotation, otherx, othery, otherblock) && otherblock.hasLiquids)
			&& !(otherblock instanceof Conduit)
			|| otherblock instanceof PressurizedConduit;
	}

	public class PlatedPressurizedConduitBuild extends PressurizedConduitBuild {
		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			return super.acceptLiquid(source, liquid)
				&& (tile == null
				|| source.tile.absoluteRelativeTo(tile.x, tile.y) == rotation
				&& !(source.block instanceof Conduit)
				|| source.block instanceof PressurizedConduit
			);

		}
	}
}
