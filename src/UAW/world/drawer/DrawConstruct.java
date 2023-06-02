package UAW.world.drawer;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.world.draw.DrawBlock;

import static mindustry.Vars.tilesize;

public class DrawConstruct extends DrawBlock {

	/** The color of the drawn line */
	public Color color = Pal.accent;
	/** How fast does the line go back and forth */
	public float timeScl = 55f;
	/** How long is the line | -1 to make it adjusts according to block size */
	public float length = -1f;
	/** The alpha of the line */
	public float alpha = 1f;

	public boolean lineX = true, lineY = false, lineXrev = false, lineYrev = false;

	@Override
	public void draw(Building build) {
		float l;
		if (build.warmup() <= 0.001f) return;
		if (length < 0) {
			l = build.block.size * tilesize - 4f;
		} else l = length;
		Draw.color(color, build.warmup() * alpha);

		if (lineY) Lines.lineAngleCenter(build.x, build.y + Mathf.sin(build.totalProgress(), timeScl, l / 2f), 0, l);
		if (lineX) Lines.lineAngleCenter(build.x + Mathf.sin(build.totalProgress(), timeScl, l / 2f), build.y, 90, l);
		if (lineYrev) Lines.lineAngleCenter(build.x, build.y - Mathf.sin(build.totalProgress(), timeScl, l / 2f), 0, l);
		if (lineXrev) Lines.lineAngleCenter(build.x - Mathf.sin(build.totalProgress(), timeScl, l / 2f), build.y, 90, l);

		Draw.reset();
	}
}
