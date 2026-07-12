package uaw.utils;

import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.entities.bullet.BasicBulletType;

/**
 * Static helper methods for {@link BasicBulletType}
 */
public class BulletDef {

    /**
     * Sets height, width, and hitSize from explicit dimensions
     * <p>hitSize controls the bullet's collision radius - set to 60% of width</p>
     */
    public static void size(BasicBulletType b, float height, float width) {
        b.height = height;
        b.width = width;
        b.hitSize = width * 0.6f;
    }

    /**
     * Sets height, width, and hitSize from a {@link BulletSprite} preset.
     * <p>Also applies the preset's sprite name.</p>
     */
    public static void size(BasicBulletType b, float height, BulletSprite sprite) {
        b.sprite = sprite.spriteName;
        size(b, height, height * sprite.widthRatio);
    }

    /**
     * Sizes the bullet using the {@link BulletSprite#DEFAULT} preset.
     */
    public static void size(BasicBulletType b, float height) {
        size(b, height, BulletSprite.DEFAULT);
    }

    public static void color(BasicBulletType b, Color frontColor, Color backColor) {
        b.hitColor = b.backColor = b.trailColor = backColor;
        b.frontColor = frontColor;
    }

    /**
     * Derives trail dimensions from the bullet's sprite size and sets trailColor to backColor.
     * Call this after setting height, width, speed, and backColor.
     * <p>Always call this last!</p>
     *
     * @param b         the boolet
     * @param lengthScl trail length multiplier applied to {@code b.height}
     * @param widthScl  trail width multiplier applied to {@code b.width}
     */
    public static void autoTrail(BasicBulletType b, float lengthScl, float widthScl) {
        b.trailWidth = b.width * widthScl;
        b.trailLength = Mathf.round(b.height * lengthScl);
        b.trailColor = b.backColor;
        b.drawSize = Math.max(b.drawSize, b.trailLength * b.speed * 4.5f);
    }

    /**
     * Finds the {@link BulletSprite} matching {@code b.sprite}, or {@link BulletSprite#DEFAULT} if none match
     */
    private static BulletSprite spriteOf(BasicBulletType b) {
        for (BulletSprite s : BulletSprite.values()) {
            if (s.spriteName.equals(b.sprite)) return s;
        }
        return BulletSprite.DEFAULT;
    }

    /**
     * Derives trail dimensions using the scale defaults baked into a {@link BulletSprite} preset
     * <p>Always call this last!</p>
     */
    public static void autoTrail(BasicBulletType b, BulletSprite sprite) {
        autoTrail(b, sprite.trailLengthScl, sprite.trailWidthScl);
    }

    public static void autoTrail(BasicBulletType b, float lengthScl, BulletSprite sprite) {
        autoTrail(b, lengthScl, sprite.trailWidthScl);
    }

    /**
     * Derives trail dimensions using the scale defaults of whichever {@link BulletSprite} was applied to this bullet
     */
    public static void autoTrail(BasicBulletType b) {
        autoTrail(b, spriteOf(b));
    }

    /**
     * Same as {@link #autoTrail(BasicBulletType)} but with a manual lengthScl override
     */
    public static void autoTrail(BasicBulletType b, float lengthScl) {
        autoTrail(b, lengthScl, spriteOf(b).trailWidthScl);
    }


    /**
     * Aspect-ratio and trail-scale profiles for common bullet sprites
     * <p>Each variant encodes the width-to-height ratio and default trail scales for its sprite,
     * so {@link BulletDef#size} and {@link BulletDef#autoTrail} produce consistent results without manual tuning</p>
     */
    public enum BulletSprite {
        DEFAULT("bullet", 0.75f, 0.6f, 0.192f),
        MISSILE_LARGE("missile-large", 0.52f, 0.6f, 0.326f);

        /**
         * Sprite name assigned to the bullet
         */
        public final String spriteName;
        /**
         * Width as a fraction of the bullet's height
         */
        public final float widthRatio;
        /**
         * Default trail length multiplier, applied to {@code b.height}
         */
        public final float trailLengthScl;
        /**
         * Default trail width multiplier, applied to {@code b.width}
         */
        public final float trailWidthScl;

        BulletSprite(String spriteName, float widthRatio, float trailLengthScl, float trailWidthScl) {
            this.spriteName = spriteName;
            this.widthRatio = widthRatio;
            this.trailLengthScl = trailLengthScl;
            this.trailWidthScl = trailWidthScl;
        }
    }
}