package UAW.world.blocks.liquid;

import UAW.content.UAWBlocks;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.blocks.liquid.*;

public class PressurizedConduit extends ArmoredConduit {
	public float minimumPower = 0.99f;

	public PressurizedConduit(String name) {
		super(name);
		noSideBlend = true;
		health = 300;
		liquidCapacity = 30f;
		liquidPressure = 1.5f;
		leaks = false;
		placeableLiquid = true;
		outputsPower = consumesPower = hasPower = true;
		consumes.power(0.125f);
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
				&& power.status > minimumPower
				&& (tile == null
				|| source.block instanceof Conduit
				|| (source.relativeTo(tile.x, tile.y) + 2) % 4 != rotation
			);
		}
	}
}

