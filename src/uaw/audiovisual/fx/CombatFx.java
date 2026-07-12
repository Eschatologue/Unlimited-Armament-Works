package uaw.audiovisual.fx;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;

public class CombatFx extends UAWFx {

    /**
     * Draws an expanding ellipse ring
     *
     * @param lifetime  duration in ticks
     * @param width     half-width at full size, in world units
     * @param thickness peak stroke thickness
     * @param col       ring colour
     * @param mode      {@link EllipseFade#STROKE} thins the line; {@link EllipseFade#FADE} fades opacity
     */
    public static Effect ellipseWave(float lifetime, float width, float thickness, Color col, EllipseFade mode) {
        float rad = Math.max(width * 0.375f, width);
        return new Effect(lifetime, e -> {
            color(col);
            switch (mode) {
                case STROKE -> {
                    stroke(thickness * e.fout());
                }
                case FADE -> {
                    alpha(e.fout());
                    stroke(thickness);
                }
            }
            float scale = e.finpow();
            Lines.ellipse(e.x, e.y, rad, (width * 0.375f * scale) / rad, (width * scale) / rad, e.rotation);
        });
    }

    public static Effect ellipseWave(float width, float thickness, Color col, EllipseFade mode) {
        return ellipseWave(20, width, thickness, col, mode);
    }

    public static Effect ellipseWave(float width, float thickness, EllipseFade mode) {
        return ellipseWave(20, width, thickness, Color.gray, mode);
    }


    public static Effect ellipseWave(float width, Color col, EllipseFade mode) {
        return ellipseWave(20, width, width * 0.125f, col, mode);
    }

    public static Effect ellipseWave(float width, EllipseFade mode) {
        return ellipseWave(width, Color.gray, mode);
    }

    /**
     * Drifting smoke trail for missiles and fast-moving projectiles. <p>Based on {@code missileTrailSmokeSmall}</p>
     *
     * <p>Spawns overlapping particle bursts, each with a randomised lifespan scaled from
     * {@code lifetime}. Particle size and density both scale with {@code width}</p>
     *
     * @param lifetime how long the effect lasts in ticks (default: {@code 120f})
     * @param width    controls particle size and density; higher = bigger, denser cloud (default: {@code 13f})
     * @param particle controls the amount of particle spawned (default: {@code 3})
     * @param col      smoke colour
     */
    public static Effect missileTrail(float lifetime, float width, int particle, Color col) {
        return new Effect(lifetime, lifetime * (5f / 3f), b -> {
            // width drives the intensity: larger width = bigger, denser particles
            float intensity = width / 10f;

            color(col, 0.7f);
            for (int i = 0; i < particle; i++) {
                rand.setSeed(b.id * 2 + i);
                float lenScl = rand.random(0.5f, 1f);
                int fi = i;
                b.scaled(b.lifetime * lenScl, e -> {
                    randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (2.9f * intensity), 13f * intensity, (x, y, in, out) -> {
                        float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                        float rad = fout * ((2f + intensity) * 2.35f);

                        Fill.circle(e.x + x, e.y + y, rad);
                        Drawf.light(e.x + x, e.y + y, rad * 2.5f, col, 0.5f);
                    });
                });
            }
        }).layer(Layer.groundUnit - 1f);
    }

    public static Effect missileTrail(float width, Color col) {
        return missileTrail(120, width, 3, col);
    }

    public static Effect missileTrail(Color col) {
        return missileTrail(13, col);
    }

    /**
     * Muzzle flash for railgun-type weapons; two opposing triangular flares with an optional expanding ring. <p>Based on {@code railShoot}</p>
     *
     * <p>Flares and ring both scale with {@code size}. Lifetime is derived from {@code size} so larger flashes linger proportionally longer.</p>
     *
     * @param size controls flare length, flare width, and ring radius (original: length {@code 85f}, width {@code 13f}, ring {@code 50f})
     * @param col  flare colour (original: {@code Pal.bulletYellowBack})
     * @param ring whether to draw the expanding {@link Lines#circle} behind the flares
     */
    public static Effect railShoot(float size, Color col, boolean ring) {
        float lifetime = size * 0.28f;
        float ringScale = size * (50f / 85f);

        return new Effect(lifetime, e -> {
            if (ring) {
                e.scaled(lifetime * (10f / 24f), b -> {
                    color(Color.white, Color.lightGray, b.fin());
                    stroke(b.fout() * 3f + 0.2f);
                    Lines.circle(b.x, b.y, b.fin() * ringScale);
                });
            }

            color(col);

            for (int i : Mathf.signs) {
                Drawf.tri(e.x, e.y, (size * (13f / 85f)) * e.fout(), size, e.rotation + 90f * i);
            }
        });
    }

    public static Effect railShoot(float size, Color col) {
        return railShoot(size, col, false);
    }

    public static Effect railShoot(float size) {
        return railShoot(size, Pal.bulletYellowBack);
    }

    /**
     * Coloured bullet-hit flash with an expanding ring and radiating particles
     *
     * <p>Particle shape is selected via {@link HitParticle}. Size scales the spread radius, particle count stays fixed. Lifetime is derived from {@code size}.</p>
     *
     * @param size  controls spread radius and ring scale; also drives lifetime (original: {@code 15f} spread, {@code 14f} lifetime)
     * @param col   hit colour — fades from white to {@code col} over the effect's life
     * @param shape {@link HitParticle#LINES} for line streaks, {@link HitParticle#SQUARES} for filled squares
     */
    public static Effect hitBullet(float size, Color col, HitParticle shape) {
        float lifetime = size * (14f / 15f);
        float ringRadius = size * (5f / 15f);
        float lightRadius = size * (20f / 15f);

        return new Effect(lifetime, e -> {
            color(Color.white, col, e.fin());

            e.scaled(lifetime * 0.5f, s -> {
                stroke(0.5f + s.fout());
                Lines.circle(e.x, e.y, s.fin() * ringRadius);
            });

            stroke(0.5f + e.fout());

            randLenVectors(e.id, 5, e.fin() * size, (x, y) -> {
                float ang = Mathf.angle(x, y);
                switch (shape) {
                    case LINES -> lineAngle(e.x + x, e.y + y, ang, e.fout() * 3f + 1f);
                    case SQUARES -> Fill.square(e.x + x, e.y + y, e.fout() * 3.2f, ang);
                    case CIRCLE -> Fill.circle(e.x + x, e.y + y, e.fout() * 3.2f);
                }
            });

            Drawf.light(e.x, e.y, lightRadius, col, 0.6f * e.fout());
        });
    }

    public static Effect hitBullet(Color col, HitParticle shape) {
        return hitBullet(15, col, shape);
    }

    /**
     * Radial burst of line sparks — covers hitBulletBig, hitLaserBlast, hitEmpSpark, hitLancer, hitMeltdown, etc
     *
     * <p>Lifetime and spread radius both scale with {@code size}
     * Pass {@code spread < 360f} to restrict sparks to a cone around {@code e.rotation}</p>
     *
     * @param size   controls spread radius and line length; also drives lifetime (original spread: {@code 17f}–{@code 30f})
     * @param col    spark colour — fades from white to {@code col}; pass {@code Color.white} for no fade
     * @param spread angular spread in degrees; {@code 360f} = omnidirectional, less = directional cone around {@code e.rotation}
     */
    public static Effect hitSparks(float size, Color col, float spread) {
        float lifetime = size * 0.43f;
        float strokePeak = size * (1.5f / 30f);
        float lineLength = size * (4f / 30f);
        int sparks = (int) Mathf.clamp(size / 1.67f, 4f, 24f);

        return new Effect(lifetime, e -> {
            color(Color.white, col, e.fin());
            stroke(e.fout() * strokePeak + 0.5f);

            if (spread >= 360f) {
                randLenVectors(e.id, sparks, e.finpow() * size, (x, y) -> {
                    lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fout() * lineLength + 1f);
                });
            } else {
                randLenVectors(e.id, sparks, e.finpow() * size, e.rotation, spread, (x, y) -> {
                    lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fout() * lineLength + 1f);
                });
            }
        });
    }

    public static Effect hitBulletBig(Color col) {
        return hitSparks(30f, col, 50f);
    }

    public enum HitParticle {
        LINES,
        SQUARES,
        CIRCLE
    }

    public enum EllipseFade {
        /**
         * Stroke thins as the ring expands.
         */
        STROKE,
        /**
         * Stroke stays constant; opacity fades out.
         */
        FADE
    }
}