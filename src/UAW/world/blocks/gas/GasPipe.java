package UAW.world.blocks.gas;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import gas.world.blocks.gas.GasConduit;

public class GasPipe extends GasConduit {
	public TextureRegion pipeIcon;

	public GasPipe(String name) {
		super(name);
	}

	@Override
	public TextureRegion[] icons() {
		if (pipeIcon.found()) {
			return new TextureRegion[]{pipeIcon, topRegions[0]};
		} else return new TextureRegion[]{Core.atlas.find("conduit-bottom"), topRegions[0]};
	}

	@Override
	public void load() {
		super.load();
		pipeIcon = Core.atlas.find("pipe-bottom");
	}

	public class GasPipeBuild extends GasConduitBuild {

	}
}
