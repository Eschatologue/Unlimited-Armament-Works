package UAW.world.blocks.liquid;

import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.*;
import mindustry.world.blocks.distribution.DirectionLiquidBridge;
import mindustry.world.blocks.liquid.*;

public class PrzPlatedConduit extends PrzConduit {
	public PrzPlatedConduit(String name) {
		super(name);
	}

	@Override
	public boolean blends(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock) {
		return (otherblock.outputsLiquid && blendsArmored(tile, rotation, otherx, othery, otherrot, otherblock)) ||
			(lookingAt(tile, rotation, otherx, othery, otherblock) && otherblock.hasLiquids);
	}

	public class PrzPlatedConduitBuild extends PrzConduitBuild {
		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			return super.acceptLiquid(source, liquid) && (tile == null || source.block instanceof Conduit || source.block instanceof DirectionLiquidBridge || source.block instanceof LiquidJunction ||
				source.tile.absoluteRelativeTo(tile.x, tile.y) == rotation);
		}
	}
}
