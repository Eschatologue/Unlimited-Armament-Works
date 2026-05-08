package uaw.audiovisual;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.graphics.Layer;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;
import static arc.math.Mathf.rand;
import static mindustry.content.Fx.v;
import static uaw.Vars.tick;

public class UAWFx {

    public static final Effect placeholder = new Effect(0, 0f, e -> {
    }),

    thumperImpactWave = new Effect(tick, e -> {
        color(e.color);

        stroke(e.fout() * 1.5f);

        randLenVectors(e.id, 12, 4f + e.finpow() * e.rotation, (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fout() * 5 + 1f);
        });

        Draw.z(Layer.blockUnder);
        Draw.alpha(0.25f);
        e.scaled(30f, b -> {
            stroke(5f * b.fout());
            Lines.circle(e.x, e.y, b.finpow() * 28f);
        });
    }),

    thumpParticles = new Effect(4 * tick, e -> {

        float length = 3f + e.finpow() * 20f;
        rand.setSeed(e.id);
        for(int i = 0; i < 13; i++){
            v.trns(rand.random(360f), rand.random(length));
            float sizer = rand.random(1.3f, 3.7f);

            e.scaled(e.lifetime * rand.random(0.5f, 1f), b -> {
                color(Color.gray, b.fslope() * 0.93f);
                Draw.z(Layer.blockAfterCracks);
                Draw.alpha(0.5f);
                Fill.circle(e.x + v.x, e.y + v.y, sizer + b.fslope() * 1.2f);
            });
        }
    });

    public static Effect crucibleSmoke(Color color) {
        return crucibleSmoke(160, color);
    }

    public static Effect crucibleSmoke(float lifetime, Color color) {
        return crucibleSmoke(lifetime, 2, color);
    }

    /**
     * {@link Fx#surgeCruciSmoke}
     * @param lifetime
     * 	How long does the effect lasts | 160
     * @param particleRad
     * 	Particle Size | 2
     * @param color
     * 	particle color
     */
    public static Effect crucibleSmoke(float lifetime, float particleRad, Color color) {
        return new Effect(lifetime, e -> {
            color(color);
            alpha(0.45f);

            rand.setSeed(e.id);
            for (int i = 0; i < 3; i++) {
                float len = rand.random(3f, 9f);
                float rot = rand.range(40f) + e.rotation;

                e.scaled(e.lifetime * rand.random(0.3f, 1f), b -> {
                    v.trns(rot, len * b.finpow());
                    Fill.circle(e.x + v.x, e.y + v.y, particleRad * b.fslope() + (particleRad / 10));
                });
            }
        });
    }

}
