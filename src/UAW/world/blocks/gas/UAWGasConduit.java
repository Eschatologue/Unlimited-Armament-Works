package UAW.world.blocks.gas;

import UAW.content.blocks.UAWBlocksLogistic;
import gas.world.blocks.gas.*;
import mindustry.Vars;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.ItemBridge;

/** To fix issue with bridge replacement */
public class UAWGasConduit extends GasConduit {
	public Block junction, bridge;
	public UAWGasConduit(String name) {
		super(name);
	}
	@Override
	public void init() {
		super.init();
		junctionReplacement = junction;
		bridgeReplacement = bridge;
	}
}
