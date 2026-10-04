package uaw.utils;

import static uaw.Vars.px;
import static uaw.Vars.tick;

public class Calc {

    public static String modName = "uaw-";
    public static String recipe = "rcp-";

    /**
     * Calculate range based on lifetime. Not 100% accurate but gets you close enough result
     * <p>Author: Sh1penfire</p>
     */
    public static float missileLifetime(float range, float speed, float accel) {
        return missileLifetime(range, speed, accel, 1);
    }

    public static float missileLifetime(float range, float speed, float accel, float lifetimeOffset) {
        return ((range - speed) / speed + accel) * lifetimeOffset;
    }

    public static float bulletLifetime(float range, float speed) {
        return bulletLifetime(range, speed, 1);
    }

    public static float bulletLifetime(float range, float speed, float scl) {
        return (range * scl) / speed;
    }

    public static float px(float pixelPos) {
        float offset = 0.5f;
        float result = pixelPos * px;

        if (pixelPos > 0) {
            return result - (offset * px);
        } else if (pixelPos < 0) {
            return result + (offset * px);
        } else {
            return 0;
        }
    }

    // Time measurements
    public static float sec(float amount) {
        return amount * tick;
    }

    // Value Rounding
    public static float round(float amount) {
        return (int) amount;
    }

    /**
     * Converts liquid units per second into the per-tick figure
     */
    public static float liqPerSec(float amount) {
        return amount / tick;
    }

    /**
     * Converts power units per second into the per-tick figure
     */
    public static float powerPerSec(float amount) {
        return amount / tick;
    }

}

