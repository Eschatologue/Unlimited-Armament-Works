package UAW.world.drawer;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;


public class DrawVentHeat extends DrawBlock {
	public TextureRegion region;
	public String suffix = "-heat";
	public Color heatColor = Color.valueOf("ff5512");

	public DrawVentHeat() {
	}

	public DrawVentHeat(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public void draw(Building build) {
		Draw.color(heatColor);
		Draw.alpha(build.warmup() * 0.6f * (1f - 0.3f + Mathf.absin(Time.time, 3f, 0.3f)));
		Draw.blend(Blending.additive);
		Draw.rect(region, build.x, build.y);
		Draw.blend();
		Draw.color();
		Draw.reset();
	}

	@Override
	public void load(Block block) {
		region = Core.atlas.find(block.name + suffix);
	}
}
