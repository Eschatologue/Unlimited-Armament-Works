package UAW.world.blocks.liquid;

import mindustry.content.Blocks;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.*;
import mindustry.world.blocks.distribution.ItemBridge;
import mindustry.world.blocks.liquid.*;

public class PressurizedConduit extends Conduit {
	public Block bridge = Blocks.bridgeConduit;
	public Block junction = Blocks.liquidJunction;

	public PressurizedConduit(String name){
		super(name);
		noSideBlend = true;
		health = 250;
		liquidCapacity = 30f;
		liquidPressure = 1.5f;
		leaks = false;
		placeableLiquid = true;
	}

	@Override
	public void init(){
		super.init();
		if(junctionReplacement == null) junctionReplacement = junction;
		if(bridgeReplacement == null || !(bridgeReplacement instanceof ItemBridge)) bridgeReplacement = bridge;
	}

	@Override
	public boolean blends(Tile tile, int rotation, int otherX, int otherY, int otherRot, Block otherBlock){
		return (otherBlock.outputsLiquid && otherBlock instanceof PressurizedConduit &&blendsArmored(tile, rotation, otherX, otherY, otherRot, otherBlock)) ||
			(lookingAt(tile, rotation, otherX, otherY, otherBlock) && otherBlock.hasLiquids);
	}

	public class PressurizedConduitBuild extends ConduitBuild{
		@Override
		public boolean acceptLiquid(Building source, Liquid liquid){
			return super.acceptLiquid(source, liquid) && (tile == null || source.block instanceof PressurizedConduit ||
				source.tile.absoluteRelativeTo(tile.x, tile.y) == rotation);
		}
	}
}
