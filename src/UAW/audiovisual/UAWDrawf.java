package UAW.audiovisual;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Building;
import mindustry.graphics.Shaders;

import static mindustry.Vars.tilesize;

/** Based on {@link mindustry.graphics.Drawf} */
public class UAWDrawf {

	public static void unitConstruct(Building t, UnlockableContent content, float rotation, float progress, float alpha, float time) {
		unitConstruct(t, content.fullIcon, rotation, progress, alpha, time);
	}

	public static void unitConstruct(float x, float y, TextureRegion region, float rotation, float progress, float alpha, float time) {
		unitConstruct(x, y, region, UAWPal.graphiteFront, rotation, progress, alpha, time);
	}

	public static void unitConstruct(float x, float y, TextureRegion region, Color color, float rotation, float progress, float alpha, float time) {
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

	public static void unitConstruct(Building t, TextureRegion region, float rotation, float progress, float alpha, float time) {
		unitConstruct(t, region, UAWPal.phlogistonMid, rotation, progress, alpha, time);
	}

	public static void unitConstruct(Building t, TextureRegion region, Color color, float rotation, float progress, float alpha, float time) {
		unitConstruct(t, region, color, rotation, progress, alpha, time, t.block.size * tilesize - 8f);
	}

	public static void unitConstruct(Building t, TextureRegion region, Color color, float rotation, float progress, float alpha, float time, float size) {
		float timeScl = 55f;
		Shaders.build.region = region;
		Shaders.build.progress = progress;
		Shaders.build.color.set(color);
		Shaders.build.color.a = alpha;
		Shaders.build.time = -time / 20f;

		Draw.shader(Shaders.build);
		Draw.rect(region, t.x, t.y, rotation);
		Draw.shader();

		Draw.color(UAWPal.phlogistonMid);
		Draw.alpha(alpha);

		Lines.lineAngleCenter(t.x, t.y + Mathf.sin(time, timeScl, size / 2f), 0, size);
		Lines.lineAngleCenter(t.x + Mathf.sin(time, timeScl, size / 2f), t.y, 90, size);
		Lines.lineAngleCenter(t.x, t.y - Mathf.sin(time, timeScl, size / 2f), 0, size);
		Lines.lineAngleCenter(t.x - Mathf.sin(time, timeScl, size / 2f), t.y, 90, size);

		Draw.reset();
	}
}
