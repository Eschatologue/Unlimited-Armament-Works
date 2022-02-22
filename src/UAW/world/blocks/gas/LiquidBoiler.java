package UAW.world.blocks.gas;

import static UAW.Vars.tick;

public class LiquidBoiler extends GasCrafter {
	public LiquidBoiler(String name) {
		super(name);
		squareSprite = false;
		liquidCapacity = 60f;
		outputsLiquid = false;
		hasItems = true;
		hasLiquids = true;
		hasGasses = true;
		craftTime = 2f * tick;
	}
}
