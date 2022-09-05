package UAW.world.drawer;

import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.blocks.liquid.LiquidBlock;
import mindustry.world.blocks.production.Pump;
import mindustry.world.draw.DrawLiquidTile;

public class DrawPumpLiquidTile extends DrawLiquidTile {

	@Override
	public void draw(Building build) {
		if (!(build instanceof Pump.PumpBuild pump) || pump.liquidDrop == null) return;

		Liquid drawn = drawLiquid != null ? drawLiquid : pump.liquidDrop;
		LiquidBlock.drawTiledFrames(build.block.size, build.x, build.y, padding, drawn, build.liquids.get(drawn) / build.block.liquidCapacity * alpha);
	}
}
