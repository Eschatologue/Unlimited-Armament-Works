package UAW.audiovisual.effects;

import UAW.audiovisual.UAWPal;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.util.Tmp;
import mindustry.entities.Effect;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;

public class CrossbombEffect extends Effect {
	public Color color = UAWPal.phlogistonFront.cpy(), bottomColor = UAWPal.phlogistonBack;
	public float waveSize = 65, waveSizeBase = 4f;
	public float waveStroke = 2;

	public int crossCount = 4;
	public float crossWidth = 6f, crossLength = 100f;
	public float crossRotOffset = 0;
	public boolean drawBottom = true;

	@Override
	public void render(EffectContainer e) {

		color(color);
		stroke(e.fout() * waveStroke);
		float circleRad = waveSizeBase + e.finpow() * waveSize;
		Lines.circle(e.x, e.y, circleRad);

		color(color);
		float crossRot = 360f / crossCount;
		for (int i = 0; i < crossCount; i++) {
			Drawf.tri(e.x, e.y, crossWidth, crossLength * e.fout(), i * crossRot + crossRotOffset);
		}

		color(color.cpy().lerp(Color.white, 0.5f));
		for (int i = 0; i < crossCount; i++) {
			Drawf.tri(e.x, e.y, crossWidth * 0.5f, crossLength * 0.35f * e.fout(), i * crossRot + crossRotOffset);
		}

		Drawf.light(e.x, e.y, circleRad * 1.6f, color, e.fout());

		if (drawBottom) {
			float lightcirc = waveSizeBase + e.finpow() * waveSize;
			Draw.z(Layer.debris);
			Fill.light(e.x, e.y, Lines.circleVertices(lightcirc / 2), lightcirc, Color.white.cpy().a(0f), Tmp.c4.set(bottomColor).cpy().a(e.fout()));
			Draw.reset();
			Drawf.light(e.x, e.y, lightcirc * 1.6f, bottomColor.cpy(), e.fout());
		}
	}
}
