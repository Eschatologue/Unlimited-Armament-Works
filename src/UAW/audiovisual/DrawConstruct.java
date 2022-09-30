package UAW.audiovisual;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Building;
import mindustry.graphics.*;

import static mindustry.Vars.tilesize;

public class DrawConstruct {

	public static void construct(Building t, UnlockableContent content, float rotation, float progress, float alpha, float time) {
		construct(t, content.fullIcon, rotation, progress, alpha, time);
	}

	public static void construct(float x, float y, TextureRegion region, float rotation, float progress, float alpha, float time) {
		construct(x, y, region, UAWPal.graphiteFront, rotation, progress, alpha, time);
	}

	public static void construct(float x, float y, TextureRegion region, Color color, float rotation, float progress, float alpha, float time) {
		Shaders.build.region = region;
		Shaders.build.progress = progress;
		Shaders.build.color.set(color);
		Shaders.build.color.a = alpha;
		Shaders.build.time = -time / 20f;

		Draw.shader(Shaders.build);
		Draw.rect(region, x, y, rotation);
		Draw.shader();

		Draw.reset();
	}

	public static void construct(Building t, TextureRegion region, float rotation, float progress, float alpha, float time) {
		construct(t, region, UAWPal.graphiteFront, rotation, progress, alpha, time);
	}

	public static void construct(Building t, TextureRegion region, Color color, float rotation, float progress, float alpha, float time) {
		construct(t, region, color, rotation, progress, alpha, time, t.block.size * tilesize - 4f);
	}

	public static void construct(Building t, TextureRegion region, Color color, float rotation, float progress, float alpha, float time, float size) {
		float timeScl = 55f;
		Shaders.build.region = region;
		Shaders.build.progress = progress;
		Shaders.build.color.set(color);
		Shaders.build.color.a = alpha;
		Shaders.build.time = -time / 20f;

		Draw.shader(Shaders.build);
		Draw.rect(region, t.x, t.y, rotation);
		Draw.shader();

		Draw.color(UAWPal.graphiteFront);
		Draw.alpha(alpha);

		Lines.lineAngleCenter(t.x, t.y + Mathf.sin(time, timeScl, size / 2f), 0, size);
		Lines.lineAngleCenter(t.x + Mathf.sin(time, timeScl, size / 2f), t.y, 90, size);
		Lines.lineAngleCenter(t.x, t.y - Mathf.sin(time, timeScl, size / 2f), 0, size);
		Lines.lineAngleCenter(t.x - Mathf.sin(time, timeScl, size / 2f), t.y, 90, size);

		Draw.reset();
	}
}
