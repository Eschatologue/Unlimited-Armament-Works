package UAW.type.weapon;

import UAW.audiovisual.Sfx;
import mindustry.content.StatusEffects;

public class TorpedoWeapon extends UAWWeapon {
	public TorpedoWeapon(String name) {
		super(name);
		rotate = false;
		shootStatus = StatusEffects.slow;
		shootStatusDuration = reload;
		shootCone = 180;
		inaccuracy = 1f;

		shootSound = Sfx.wp_torp_torpedoLaunchSpring_1;
		shootWarmupSpeed = 0.05f;
		minWarmup = 0.8f;

		shoot.firstShotDelay = 15f;
	}

	public TorpedoWeapon() {
		this("");
	}
}
