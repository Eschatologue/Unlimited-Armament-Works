package uaw.audiovisual.fx;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Interp;
import arc.math.geom.Vec2;
import mindustry.entities.Effect;
import mindustry.graphics.Layer;
import uaw.audiovisual.Perspective;
import uaw.audiovisual.UAWPal;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;
import static mindustry.Vars.tilesize;

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
     * @param lifetime    how long the effect lasts in ticks
     * @param particleRad radius of each smoke circle at peak size
     * @param col         particle colour
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
     * Chimney-style smoke puff using the fake-3D {@link Perspective}
     *
     * @param lifetime   how long the whole puff lasts, in ticks
     * @param riseHeight how high a particle climbs before the effect ends, in world units
     * @param spread     maximum sideways drift per particle, in world units
     * @param col        smoke colour
     */
    public static Effect chimneySmoke(float lifetime, float riseHeight, float spread, Color col) {
        return new Effect(lifetime, e -> {
            rand.setSeed(e.id);

            for (int i = 0; i < 4; i++) {
                // Each particle gets its own drift direction/distance and its own climb schedule,
                // so the puff spreads rather than rising as a single line.
                float driftAngle = rand.random(360f);
                float driftLen = rand.random(spread);
                float riseScl = rand.random(0.6f, 1f);

                e.scaled(e.lifetime * riseScl, b -> {
                    float z = b.fin(Interp.pow2Out) * riseHeight;
                    if (!Perspective.canDraw(z)) return; // nothing would render anyway — skip the maths

                    // finpow() biases drift toward the tail of the particle's life, so it billows
                    // outward more the higher it climbs, rather than drifting at a constant rate.
                    v.trns(driftAngle, driftLen * b.finpow());
                    float wx = e.x + v.x, wy = e.y + v.y;

                    Vec2 pos = Perspective.drawPos(wx, wy, z);
                    float scale = Perspective.scale(wx, wy, z);
                    float fade = Perspective.alpha(wx, wy, z);

                    Draw.z(Layer.blockOver + z * 0.01f);
                    color(col);
                    alpha(0.5f * b.fout() * fade);
                    Fill.circle(pos.x, pos.y, (2f + b.fin() * 3f) * scale);
                });
            }
        });
    }

    /**
     * Spread defaults {@code riseHeight}/3.5f
     */
    public static Effect chimneySmoke(float lifetime, float riseHeight, Color col) {
        return chimneySmoke(lifetime, riseHeight, riseHeight / 3.5f, col);
    }

    public static Effect chimneySmoke(Color col) {
        return chimneySmoke(90, 4 * tilesize, col);
    }

    public static Effect sulphurChimneySmoke() {
        return chimneySmoke(60, 3 * tilesize, UAWPal.refinerySmokeSulph);
    }

    // endregion
}