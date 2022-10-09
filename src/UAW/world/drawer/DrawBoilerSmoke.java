package UAW.world.drawer;

import UAW.audiovisual.UAWPal;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Time;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.world.draw.DrawBlock;

/** Modified version of the Draw Arc Smelter, replaced Lines with Circles */
public class DrawBoilerSmoke extends DrawBlock {
	/** The Color of the particle */
	public Color particleColor = UAWPal.steamFront;
	/** The amount of particles generated */
	public int particles = 25;
	/** How big is the particle, calculated in tilesize */
	public float size = 3f;
	/** The alpha or the opacity of the particles */
	public float alpha = 0.55f;
	/** How long does the particle lives, measured in ticks */
	public float lifetime = 140f;
	/** How wide does the particle spreads */
	public float spreadRadius = 7f;

	@Override
	public void draw(Building build) {
		if (build.warmup() > 0f && particleColor.a > 0.001f) {
			float progress = build.warmup();
			float base = (Time.time / lifetime);

			Draw.z(Layer.blockOver);
			Draw.blend(Blending.normal);
			Draw.color(particleColor);

			rand.setSeed(build.id);
			for (int i = 0; i < particles; i++) {
				float fin = (rand.random(1f) + base) % 1f, fout = 1f - fin;
				float angle = rand.random(360f);
				float len = spreadRadius * Interp.pow2Out.apply(fin);

				Draw.alpha(alpha * fout * progress);
				Fill.circle(
					build.x + Angles.trnsx(angle, len),
					build.y + Angles.trnsy(angle, len),
					size * fout * progress
				);
			}
			Draw.blend();
			Draw.reset();
		}
	}
}

