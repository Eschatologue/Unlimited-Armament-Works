package UAW.world.blocks.liquid;

import UAW.content.UAWBlocks;
import mindustry.gen.Building;
import mindustry.type.Liquid;
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
		outputsPower = consumesPower = hasPower = true;
		consumes.power(0.25f);
	}

	@Override
	public void init() {
		super.init();
		junctionReplacement = UAWBlocks.pressurizedLiquidJunction;
		bridgeReplacement = UAWBlocks.pressurizedLiquidBridge;
	}

	public class PressurizedConduitBuild extends ConduitBuild {
		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			return super.acceptLiquid(source, liquid)
				&& power.status > 0.99f
				&& (tile == null
				|| source.block instanceof Conduit
				|| source.tile.absoluteRelativeTo(tile.x, tile.y) == rotation);
		}
	}
}

