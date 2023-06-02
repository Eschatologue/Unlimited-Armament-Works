package UAW.entities;

public class Calc {

	/**
	 * Calculate range based on lifetime. Not 100% accurate but gets you close enough result
	 */
	public static float missileLifetime(float range, float speed, float accel) {
		return missileLifetime(range, speed, accel, 0);
	}

	public static float missileLifetime(float range, float speed, float accel, float lifetimeOffset) {
		return (range - speed) / speed + accel;
	}
}
