package uaw.utils;

import static uaw.Vars.px;
import static uaw.Vars.tick;

public class Calc {

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

    // Items & Liquid measurements
    public static float liquidUnit(float amount){
        return amount / tick;
    }


    public static float percentageDifference(float from, float to) {
        return (Math.abs(from - to) / ((from + to) / 2) * 100);
    }

    public static float percentageChange(float from, float to) {
        return (from - to) / Math.abs(from) * 100;
    }

}
