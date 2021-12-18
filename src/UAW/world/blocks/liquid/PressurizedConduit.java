package UAW.world.blocks.liquid;

import UAW.content.UAWBlock;
import arc.func.Boolf;
import arc.math.Mathf;
import arc.math.geom.*;
import arc.struct.Seq;
import mindustry.content.Blocks;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.*;
import mindustry.world.blocks.distribution.ItemBridge;
import mindustry.world.blocks.liquid.*;

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
	public void init() {
		super.init();
		if (junctionReplacement == null) junctionReplacement = UAWBlock.pressurizedLiquidJunction;
		if (bridgeReplacement == null || !(bridgeReplacement instanceof ItemBridge)) bridgeReplacement = UAWBlock.pressurizedBridgeConduit;
	}

	@Override
	public Block getReplacement(BuildPlan req, Seq<BuildPlan> requests){
		if(junctionReplacement == null) return this;

		Boolf<Point2> cont = p -> requests.contains(o -> o.x == req.x + p.x && o.y == req.y + p.y && o.rotation == req.rotation && (req.block instanceof PressurizedConduit || req.block instanceof LiquidJunction));
		return cont.get(Geometry.d4(req.rotation)) &&
			cont.get(Geometry.d4(req.rotation - 2)) &&
			req.tile() != null &&
			req.tile().block() instanceof Conduit &&
			Mathf.mod(req.build().rotation - req.rotation, 2) == 1 ? junctionReplacement : this;
	}

	@Override
	public boolean blends(Tile tile, int rotation, int otherX, int otherY, int otherRot, Block otherBlock) {
		return otherBlock.outputsLiquid && blendsArmored(tile, rotation, otherX, otherY, otherRot, otherBlock) || lookingAt(tile, rotation, otherX, otherY, otherBlock) && otherBlock.hasLiquids && otherBlock instanceof PressurizedConduit;
	}

	public class PressurizedConduitBuild extends ConduitBuild {
		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			return super.acceptLiquid(source, liquid) && (tile == null || source.block instanceof PressurizedConduit ||
				source.tile.absoluteRelativeTo(tile.x, tile.y) == rotation);
		}
	}
}
