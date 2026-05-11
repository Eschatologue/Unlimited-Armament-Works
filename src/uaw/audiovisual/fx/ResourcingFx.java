package uaw.audiovisual.fx;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import mindustry.entities.Effect;
import mindustry.graphics.Layer;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;
import static arc.math.Mathf.rand;
import static mindustry.content.Fx.v;
import static uaw.Vars.tick;

public class ResourcingFx extends UAWFx{

    public static final Effect thumperImpactWave = new Effect(tick, e -> {
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
    });

    public static final Effect thumpParticles = new Effect(4 * tick, e -> {
        float length = 3f + e.finpow() * 20f;
        rand.setSeed(e.id);

        for (int i = 0; i < 13; i++) {
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
}
