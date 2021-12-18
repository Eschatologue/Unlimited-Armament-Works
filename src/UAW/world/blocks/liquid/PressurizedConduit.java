package UAW.world.blocks.liquid;

import UAW.content.UAWBlock;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.*;
import mindustry.world.blocks.distribution.ItemBridge;
import mindustry.world.blocks.liquid.Conduit;

public class PressurizedConduit extends Conduit {

	public PressurizedConduit(String name) {
		super(name);
		noSideBlend = true;
		health = 250;
		liquidCapacity = 30f;
		liquidPressure = 1.5f;
		leaks = false;
		placeableLiquid = true;
	}
	@Override
	public boolean blends(Tile tile, int rotation, int otherX, int otherY, int otherRot, Block otherBlock) {
		return
			otherBlock.outputsLiquid
				&& blendsArmored(tile, rotation, otherX, otherY, otherRot, otherBlock)
				|| lookingAt(tile, rotation, otherX, otherY, otherBlock)
				&& otherBlock.hasLiquids;
	}

	public class PressurizedConduitBuild extends ConduitBuild {
		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			return super.acceptLiquid(source, liquid) && (tile == null || source.block instanceof PressurizedConduit ||
				source.tile.absoluteRelativeTo(tile.x, tile.y) == rotation);
		}
	}
}
