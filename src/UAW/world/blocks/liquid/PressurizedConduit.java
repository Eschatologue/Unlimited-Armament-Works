package UAW.world.blocks.liquid;

import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.*;
import mindustry.world.blocks.liquid.*;

public class PressurizedConduit extends ArmoredConduit {

	public PressurizedConduit(String name){
		super(name);
		health = 250;
		liquidCapacity = 24f;
		liquidPressure = 1.5f;
		leaks = false;
	}

	@Override
	public boolean blends(Tile tile, int rotation, int otherX, int otherY, int otherRot, Block otherBlock){
		return (otherBlock.outputsLiquid && otherBlock instanceof PressurizedConduit && blendsArmored(tile, rotation, otherX, otherY, otherRot, otherBlock)) ||
			(lookingAt(tile, rotation, otherX, otherY, otherBlock) && otherBlock.hasLiquids);
	}

	public class PressurizedConduitBuild extends ArmoredConduitBuild{
		@Override
		public boolean acceptLiquid(Building source, Liquid liquid){
			return super.acceptLiquid(source, liquid) && (tile == null || source.block instanceof PressurizedConduit ||
				source.tile.absoluteRelativeTo(tile.x, tile.y) == rotation);
		}
	}
}
