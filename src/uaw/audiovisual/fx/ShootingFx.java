package uaw.audiovisual.fx;

import arc.graphics.Color;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Trail;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;
import static mindustry.Vars.state;

public class ShootingFx extends UAWFx {

    public static final Effect hitBulletSmallColor = new Effect(14, e -> {
        color(Color.white, e.color, e.fin());

        e.scaled(7f, s -> {
            stroke(0.5f + s.fout());
            Lines.circle(e.x, e.y, s.fin() * 5f);
        });

        stroke(0.5f + e.fout());

        randLenVectors(e.id, 5, e.fin() * 15f, (x, y) -> {
            float ang = Mathf.angle(x, y);
            lineAngle(e.x + x, e.y + y, ang, e.fout() * 3 + 1f);
        });

        Drawf.light(e.x, e.y, 20f, e.color, 0.6f * e.fout());
    });
    /**
     * Based on {@code Fx.trailFade}. Used for {@link uaw.entities.bullet.TrailBulletType}
     */
    public static final Effect trailFade = new Effect(400f, 400, e -> {
        if (!(e.data instanceof Trail trail)) return;
        //life is how many frames it takes to fade out the trail
        e.lifetime = trail.length * 1.4f;

        if (!state.isPaused()) {
            trail.shorten();
        }
        trail.drawCap(e.color, e.rotation);
        trail.draw(e.color, e.rotation);
    });

    public static Effect hitBulletBig(Color col) {
        return hitBulletBig(col, 13);
    }

    public static Effect hitBulletBig(Color col, float lifetime) {
        return new Effect(lifetime, e -> {
            color(Color.white, col, e.fin());
            stroke(0.5f + e.fout() * 1.5f);

            randLenVectors(e.id, 8, e.finpow() * 30f, e.rotation, 50f, (x, y) -> {
                float ang = Mathf.angle(x, y);
                lineAngle(e.x + x, e.y + y, ang, e.fout() * 4 + 1.5f);
            });
        });
    }

    public static Effect ellipseWave(){
        return ellipseWave(25, 4, 8, 1f, Color.gray);
    }

    /**
     * Draws an expanding ellipse ring that fades out
     *
     * @param lifetime how long the effect lasts, in ticks
     * @param width half-width of the ellipse at full size, in world units
     * @param height half-height of the ellipse at full size, in world units
     * @param thickness peak line stroke thickness (fades to 0 as the effect ends)
     * @param col ring colour
     */
    public static Effect ellipseWave(float lifetime, float width, float height, float thickness, Color col) {
        float rad = Math.max(width, height);
        return new Effect(lifetime, e -> {
            alpha(0.75f);
            color(col);

            stroke(thickness * e.fout());
            float scale = e.finpow();
            Lines.ellipse(e.x, e.y, rad, (width * scale) / rad, (height * scale) / rad, e.rotation);
        });
    }
}
