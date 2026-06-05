package uaw.audiovisual.fx;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import mindustry.entities.Effect;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;

public class ProductionFx extends UAWFx {

    /**
     * Shorthand: lifetime=160, particleRad=2
     */
    public static Effect crucibleSmoke(Color col) {
        return crucibleSmoke(160f, 2f, col);
    }

    /**
     * Shorthand: particleRad=2
     */
    public static Effect crucibleSmoke(float lifetime, Color col) {
        return crucibleSmoke(lifetime, 2f, col);
    }

    /**
     * Drifting smoke particles, loosely based on {@code Fx.surgeCruciSmoke}
     *
     * @param lifetime how long the effect lasts in ticks
     * @param particleRad radius of each smoke circle at peak size
     * @param col particle colour
     */
    public static Effect crucibleSmoke(float lifetime, float particleRad, Color col) {
        return new Effect(lifetime, e -> {
            color(col);
            alpha(0.45f);

            rand.setSeed(e.id);
            for (int i = 0; i < 3; i++) {
                float len = rand.random(3f, 9f);
                float rot = rand.range(40f) + e.rotation;

                e.scaled(e.lifetime * rand.random(0.3f, 1f), b -> {
                    v.trns(rot, len * b.finpow());
                    Fill.circle(e.x + v.x, e.y + v.y, particleRad * b.fslope() + (particleRad / 10f));
                });
            }
        });
    }

    // endregion
}