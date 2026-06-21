package uaw.audiovisual.fx;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;

public class AreaFx extends UAWFx {

    /**
     * General-purpose explosion — a drifting smoke cloud followed by a shockwave ring and radial spark burst
     *
     * <p>All timings, radii, and counts scale with {@code radius}. Pass the same value as the
     * bullet or effect's {@code splashDamageRadius} to get a visually matching explosion.</p>
     *
     * @param radius        explosion radius in world units — use {@code splashDamageRadius} or {@code hitSize} directly
     *                      (originals: {@code reactorExplosion} = {@code 204f}, {@code impactReactorExplosion} = {@code 240f})
     * @param smokeCol      base colour of the drifting smoke cloud
     * @param sparkColFront start colour of the spark streak gradient
     * @param sparkColBack  end colour of the spark streak gradient
     * @param lightCol      point-light colour emitted by smoke particles
     */
    public static Effect explosion(
            float radius,
            Color smokeCol,
            Color sparkColFront,
            Color sparkColBack,
            Color lightCol
    ) {
        float intensity = radius / 30f;
        // Smoke burst count scales with intensity — small explosions get 2, large ones up to 6.
        // Clamped so tiny blasts don't skip smoke entirely and huge ones don't oversaturate.
        int smokeBursts = (int) Mathf.clamp(intensity * 2f, 2f, 6f);

        return new Effect(30, radius * 500f / 240f, b -> {
            // Inner lifetime scales with radius so smoke lingers proportionally to explosion size.
            float baseLifetime = 20f + intensity * 12f;
            b.lifetime = 40f + intensity * 50f;

            color(smokeCol);
            alpha(0.8f);
            for (int i = 0; i < smokeBursts; i++) {
                rand.setSeed(b.id * 2 + i);
                float lenScl = rand.random(0.4f, 1f);
                int fi = i;
                b.scaled(b.lifetime * lenScl, e -> {
                    randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (2.9f * intensity), 14f * intensity, (x, y, in, out) -> {
                        float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                        float rad = fout * ((2f + intensity) * 2.35f);

                        Fill.circle(e.x + x, e.y + y, rad);
                        Drawf.light(e.x + x, e.y + y, rad * 2.5f, lightCol, 0.5f);
                    });
                });
            }

            b.scaled(baseLifetime, e -> {
                color();
                e.scaled(5f + intensity * 2f, i -> {
                    stroke((3.1f + intensity / 5f) * i.fout());
                    Lines.circle(e.x, e.y, (3f + i.fin() * 14f) * intensity);
                    Drawf.light(e.x, e.y, i.fin() * 14f * 2f * intensity, Color.white, 0.9f * e.fout());
                });

                color(sparkColFront, sparkColBack, e.fin());
                stroke(2f * e.fout());

                Draw.z(Layer.effect + 0.001f);
                randLenVectors(e.id + 1, e.finpow() + 0.001f, (int) (8f * intensity), radius, (x, y, in, out) -> {
                    lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + out * 4f * (4f + intensity));
                    Drawf.light(e.x + x, e.y + y, (out * 4f * (3f + intensity)) * 3.5f, Draw.getColor(), 0.8f);
                });
            });
        });
    }

    public static Effect explosion(float radius, Color smokeCol, Color sparkColBack) {
        return explosion(radius, smokeCol, Color.white, sparkColBack, sparkColBack);
    }

    /**
     * Expanding ring with triangle spikes and a filled circle bloom. Based on {@code Fx.scatheExplosion} & {@code Fx.scatheLight}
     *
     * <p>All dimensions and timings derive from {@code radius}. Clip distance scales automatically
     * Drawn in two layers: the bloom sits on {@code Layer.bullet + 2f}, the ring and spikes on the default effect layer.</p>
     *
     * <h4>Scaling anchors</h4>
     * <ul>
     *   <li>Lifetime: {@code 1 tick per world unit} of radius</li>
     *   <li>Clip distance: proportional to original {@code 160f} at {@code radius = 60f}</li>
     *   <li>Spike width/length: proportional to original {@code 40f}/{@code 30f} at {@code radius = 60f}</li>
     * </ul>
     *
     * @param radius maximum ring and bloom radius in world units — use {@code splashDamageRadius} directly
     * @param col    colour applied to both the ring/spikes and the bloom
     */
    public static Effect spikeFlash(float radius, Color col) {
        float lifetime = radius * 0.75f;
        float clipSize = radius * (160f / 60f);
        float strokePeak = radius * (5f / 60f);
        float triWidth = radius * (40f / 60f);
        float triLength = radius * (30f / 60f);
        // Capped at 64 to avoid absurd counts on very large explosions
        int spikeCount = (int) Math.min(radius / 3.75f, 64);

        return new Effect(lifetime, clipSize, e -> {
            float circleRad = 6f + e.finpow() * radius;

            // Bloom — above bullets so it reads as an impact flash
            z(Layer.bullet + 2f);
            color(col);
            alpha(e.foutpow());
            Fill.circle(e.x, e.y, circleRad);

            // Ring and spikes
            z(Layer.effect);
            color(col);
            stroke(e.fout() * strokePeak);
            Lines.circle(e.x, e.y, circleRad);

            rand.setSeed(e.id);
            for (int i = 0; i < spikeCount; i++) {
                float angle = rand.random(360f);
                float lenRand = rand.random(0.5f, 1f);
                Tmp.v1.trns(angle, circleRad);

                for (int s : Mathf.signs) {
                    Drawf.tri(
                            e.x + Tmp.v1.x, e.y + Tmp.v1.y,
                            e.foutpow() * triWidth,
                            e.fout() * triLength * lenRand + 6f,
                            angle + 90f + s * 90f
                    );
                }
            }
        });
    }

    /**
     * Expanding ring with radiating line bursts — the "titan" style hit flash.
     *
     * <p>All dimensions, counts, and timings derive from {@code radius}.
     * Clip distance scales automatically.
     * Pair with {@link #titanSmoke(float, Color)} for the full titan explosion look.</p>
     *
     * <h4>Scaling anchors (at {@code radius = 60f})</h4>
     * <ul>
     *   <li>Lifetime: {@code 30f} ticks</li>
     *   <li>Line count: {@code 16}</li>
     *   <li>Line reach: {@code 70f}</li>
     * </ul>
     *
     * @param radius maximum ring radius in world units — use {@code splashDamageRadius} directly
     * @param col    ring and line colour
     */
    public static Effect titanExplosion(float radius, Color col) {
        float lifetime = radius * (30f / 60f);
        float clipSize = radius * (160f / 60f);
        // Line reach is the furthest distance a line extends from the origin
        float lineReach = radius * (70f / 60f);
        // Line count scales with circumference — more ring = more lines
        int lineCount = (int) Mathf.clamp(radius * (16f / 60f), 6f, 32f);

        return new Effect(lifetime, clipSize, e -> {
            color(col);
            stroke(e.fout() * 3f);
            float circleRad = 6f + e.finpow() * radius;
            Lines.circle(e.x, e.y, circleRad);

            rand.setSeed(e.id);
            for (int i = 0; i < lineCount; i++) {
                float angle = rand.random(360f);
                float lenRand = rand.random(0.5f, 1f);
                // lineAngle(x, y, angle, length, offset) — line starts at offset along the angle and extends by length
                Lines.lineAngle(e.x, e.y, angle, e.foutpow() * 50f * rand.random(0.6f, 1f) + 2f, e.finpow() * lineReach * lenRand + 6f);
            }
        });
    }

    /**
     * Drifting smoke cloud — the "titan" style lingering smoke.
     *
     * <p>Lifetime and spread scale with {@code radius}.
     * Clip distance matches lifetime so distant smoke is never culled early.
     * Pair with {@link #titanExplosion(float, Color)} for the full titan explosion look.</p>
     *
     * @param radius cloud spread radius in world units
     * @param col    smoke particle colour
     */
    public static Effect titanSmoke(float radius, Color col) {
        // intensity drives particle count and spread; anchored so intensity=3 matches the original titanSmoke
        float intensity = radius / 22f;
        float lifetime = radius * (300f / 66f);
        float clipSize = lifetime * 1.25f;
        int smokeBursts = (int) Mathf.clamp(intensity * 2f, 2f, 6f);

        return new Effect(lifetime, clipSize, b -> {
            color(col, 0.7f);
            for (int i = 0; i < smokeBursts; i++) {
                rand.setSeed(b.id * 2 + i);
                float lenScl = rand.random(0.5f, 1f);
                int fi = i;
                b.scaled(b.lifetime * lenScl, e -> {
                    randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (2.9f * intensity), radius, (x, y, in, out) -> {
                        float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                        float rad = fout * ((2f + intensity) * 2.35f);

                        Fill.circle(e.x + x, e.y + y, rad);
                        Drawf.light(e.x + x, e.y + y, rad * 2.5f, col, 0.5f);
                    });
                });
            }
        });
    }
}
