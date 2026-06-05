package uaw.utils;

import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.entities.bullet.BasicBulletType;

/**
 * Static helper methods for {@link BasicBulletType}
 */
public class BulletDef {

    public static void size(BasicBulletType b, float height) {
        size(b, height, height * 0.75f);
    }

    /**
     * Sets height, width, and hitSize explicitly.
     * hitSize controls the bullet's collision radius — defaults to half of width.
     */
    public static void size(BasicBulletType b, float height, float width) {
        b.height = height;
        b.width = width;
        b.hitSize = width * 0.6f;
    }

    public static void color(BasicBulletType b, Color frontColor, Color backColor) {
        b.hitColor = b.backColor = b.trailColor = backColor;
        b.frontColor = frontColor;
    }

    /**
     * Derives trail dimensions from the bullet's sprite size and sets trailColor to backColor.
     * Call this after setting height, width, speed, and backColor.
     * <p>Always call this last!</p>
     */
    public static void autoTrail(BasicBulletType b, float lengthScl, float widthScl) {
        b.trailRotation = true;
        b.trailWidth = b.width * widthScl;
        b.trailLength = Mathf.round(b.height * lengthScl);
        b.trailColor = b.backColor;
        b.drawSize = Math.max(b.drawSize, b.trailLength * b.speed * 4.5f);
    }

    /**
     * Uses TrailBulletType's default scales: 0.6 length, 0.192 width.
     * <p>Always call this last!</p>
     */
    public static void autoTrail(BasicBulletType b) {
        autoTrail(b, 0.6f, 0.192f);
    }
}
