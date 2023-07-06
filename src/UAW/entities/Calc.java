package UAW.entities;

import static UAW.Vars.px;

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

}
