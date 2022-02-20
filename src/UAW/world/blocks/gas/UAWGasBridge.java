package UAW.world.blocks.gas;

import gas.world.blocks.gas.GasBridge;
import mindustry.gen.Building;

public class UAWGasBridge extends GasBridge {
	public UAWGasBridge(String name) {
		super(name);
	}

	public class UAWGasBridgeBuild extends GasBridgeBuild {
		@Override
		public void updateTransport(Building other) {
			moved |= moveGas(other, gasses.current()) > 0.05f;
		}
	}
}
