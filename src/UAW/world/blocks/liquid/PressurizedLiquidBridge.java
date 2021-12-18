package UAW.world.blocks.liquid;

import arc.Core;
import arc.graphics.g2d.*;
import mindustry.graphics.Drawf;
import mindustry.world.blocks.liquid.LiquidBridge;

public class PressurizedLiquidBridge extends LiquidBridge {
	public TextureRegion bottomRegion, liquidRegion, topRegion;

	public PressurizedLiquidBridge(String name) {
		super(name);
		range = 6;
	}

	@Override
	public TextureRegion[] icons() {
		return new TextureRegion[]{bottomRegion, topRegion};
	}

	@Override
	public void load() {
		bottomRegion = Core.atlas.find(name + "-bottom");
		liquidRegion = Core.atlas.find(name + "-liquid");
		topRegion = Core.atlas.find(name + "-top");
	}

	public class PressurizedLiquidBridgeBuild extends LiquidBridgeBuild {
		@Override
		public void draw() {
			super.draw();
			Draw.rect(bottomRegion, x, y);
			if (liquids.total() > 0.001f) {
				Drawf.liquid(liquidRegion, x, y, liquids.total() / liquidCapacity, liquids.current().color);
			}
			Draw.rect(topRegion, x, y);
		}
	}
}
