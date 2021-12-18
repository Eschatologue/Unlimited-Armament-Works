package UAW.world.blocks.liquid;

import UAW.content.UAWBlock;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.*;
import mindustry.world.blocks.liquid.*;

public class PressurizedConduit extends ArmoredConduit {

	public PressurizedConduit(String name) {
		super(name);
		noSideBlend = true;
		health = 300;
		liquidCapacity = 30f;
		liquidPressure = 1.5f;
		leaks = false;
		placeableLiquid = true;
	}

	@Override
	public void init() {
		super.init();
		junctionReplacement = UAWBlock.pressurizedLiquidJunction;
		bridgeReplacement = UAWBlock.pressurizedBridgeConduit;
	}

	@Override
	public boolean blends(Tile tile, int rotation, int otherX, int otherY, int otherRot, Block otherBlock) {
		return (otherBlock.outputsLiquid && blendsArmored(tile, rotation, otherX, otherY, otherRot, otherBlock))
			|| (lookingAt(tile, rotation, otherX, otherY, otherBlock)
			&& otherBlock.hasLiquids
			&& otherBlock instanceof PressurizedConduit
		);
	}

	public class PressurizedConduitBuild extends ArmoredConduitBuild {
		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			return super.acceptLiquid(source, liquid) && (tile == null
				|| !(source.block instanceof Conduit)
				|| source.block instanceof PressurizedConduit
				|| source.tile.absoluteRelativeTo(tile.x, tile.y) == rotation);
		}
	}
}

