package UAW.world.drawer;

import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.world.blocks.heat.HeatBlock;
import mindustry.world.draw.DrawHeatOutput;

/** DrawHeatOutput but top1 and top2 sprite are generated above the -heat sprite */
public class DrawHeatOutputTop extends DrawHeatOutput {

	@Override
	public void draw(Building build) {
		Draw.z(Layer.blockAdditive + 0.1f);
		Draw.rect(build.rotation > 1 ? top2 : top1, build.x, build.y, build.rotdeg());
		Draw.reset();

		if (build instanceof HeatBlock heater && heater.heat() > 0) {
			Draw.z(Layer.blockAdditive);
			Draw.blend(Blending.additive);
			Draw.color(heatColor, heater.heatFrac() * (heatColor.a * (1f - heatPulse + Mathf.absin(heatPulseScl, heatPulse))));
			if (heat.found()) Draw.rect(heat, build.x, build.y, build.rotdeg());
			Draw.z(Layer.blockAdditive + 0.2f);
			Draw.color(Draw.getColor().mul(glowMult));
			if (glow.found()) Draw.rect(glow, build.x, build.y);
			Draw.blend();
			Draw.color();
		}
	}
}
