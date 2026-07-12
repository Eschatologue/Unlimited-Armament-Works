package uaw.audiovisual.fx;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import mindustry.entities.Effect;
import mindustry.graphics.Layer;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;

public class    ProductionFx extends UAWFx {

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

    /**
     *
     * @param lifetime how long each puff lasts, in ticks
     * @param height   how far a puff rises before fully dispersing, in world units
     * @param sway     max horizontal wobble at the top of the rise
     * @param col      smoke colour
     */
    public static Effect stackSmoke(float lifetime, float height, float sway, Color col) {
        int puffs = 4;
        return new Effect(lifetime, e -> {
            rand.setSeed(e.id);
            color(col);

            for (int i = 0; i < puffs; i++) {
                int fi = i;
                // Staggers each puff's start so the chimney reads as a continuous stream,
                // not one blob rising and vanishing.
                float delayedLife = e.lifetime * rand.random(0.6f, 1f);

                e.scaled(delayedLife, b -> {
                    float rise = b.finpow(); // 0 at the base, 1 at full height

                    float drawX = e.x + Mathf.sinDeg(rise * 180f + fi * 90f) * sway * rise;
                    float drawY = e.y + rise * height;

                    // Climbing past nearby blocks/turrets is what actually sells the height.
                    Draw.z(Layer.blockOver + rise * 3f);
                    alpha(0.5f * b.fout());
                    Fill.circle(drawX, drawY, 2f + rise * 5f);
                });
            }
        });
    }

    // endregion
}