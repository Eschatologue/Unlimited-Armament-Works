package UAW.world.blocks.liquid;

import UAW.content.UAWBlocks;
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
		junctionReplacement = UAWBlocks.pressurizedLiquidJunction;
		bridgeReplacement = UAWBlocks.pressurizedLiquidBridge;
	}
}

