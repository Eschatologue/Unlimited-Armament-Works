package uaw.audiovisual;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;

/**
 * Drawing helpers that extend or generalise vanilla {@link mindustry.graphics.Drawf}
 */
public class UAWDrawf {

    /**
     * Draws a rotating sprite that crossfades across its own symmetry seam, avoiding the visual
     * "pop" a plain rotated {@link Draw#rect} produces on sprites with rotational symmetry
     *
     * <p>Adapted from vanilla {@link mindustry.graphics.Drawf#spinSprite}
     *
     * @param region the sprite to draw. Must actually look identical when rotated by {@code period}
     *               degrees, otherwise the crossfade will look like a double-exposure ghost
     * @param x      world x position
     * @param y      world y position
     * @param r      current rotation, in degrees
     * @param period the angle, in degrees, at which the sprite's appearance repeats (e.g. 180f)
     */
    public static void spinSprite(TextureRegion region, float x, float y, float r, float period) {
        float a = Draw.getColorAlpha();
        r = Mathf.mod(r, period);

        Draw.rect(region, x, y, r);
        Draw.alpha(r / period * a);
        Draw.rect(region, x, y, r - period);
        Draw.alpha(a);
    }

    public static void spinSprite(TextureRegion region, float x, float y, float r) {
        spinSprite(region, x, y, r, 90);
    }

    public static void spinSprite180(TextureRegion region, float x, float y, float r) {
        spinSprite(region, x, y, r, 180f);
    }
}