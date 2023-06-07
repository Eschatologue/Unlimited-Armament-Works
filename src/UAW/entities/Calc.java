package UAW.entities;

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
		return range / speed;
	}
}
