package UAW.audiovisual.effects;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import mindustry.entities.Effect;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.randLenVectors;

public class ExplodeEffect extends Effect {
	public Color waveColor = Pal.missileYellow, smokeColor = Color.gray, sparkColor = Pal.missileYellowBack;
	public float waveLife = 6f, waveStroke = 3f, waveRad = 15f, waveRadBase = 2f;
	public float sparkStroke = 1f, sparkRad = 23f, sparkLen = 3f;
	public float smokeLife = 6f, smokeSize = 4f, smokeSizeBase = 0.25f, smokeRad = 23f;
	public int smokes = 5, sparks = 4;

	@Override
	public void init() {
		super.init();
		clip = waveRad * 1.5f;
		lifetime = Math.max(waveLife, smokeLife) * 1.5f;
	}

	@Override
	public void render(EffectContainer e) {
		color(waveColor);

		e.scaled(waveLife, i -> {
			stroke(waveStroke * i.fout());
			Lines.circle(e.x, e.y, waveRadBase + i.fin() * waveRad);
		});

		color(smokeColor);

		if (smokeSize > 0) {
			e.scaled(smokeLife, s -> {
				randLenVectors(e.id, smokes, 2f + smokeRad * s.finpow(), (x, y) -> {
					Fill.circle(e.x + x, e.y + y, s.fout() * smokeSize + smokeSizeBase);
				});
			});
		}

		color(sparkColor);
		stroke(e.fout() * sparkStroke);

		randLenVectors(e.id + 1, sparks, 1f + sparkRad * e.finpow(), (x, y) -> {
			lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * sparkLen);
			Drawf.light(e.x + x, e.y + y, e.fout() * sparkLen * 4f, sparkColor, 0.7f);
		});
	}
}
