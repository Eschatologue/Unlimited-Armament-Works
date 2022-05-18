package UAW.entities.bullet.ModdedVanillaBullet;

import arc.util.*;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.*;

public class UAWBulletType extends BulletType {
	/** Percentage of bullet damage that damages shield */
	public float shieldDamageMultiplier = 1f;
	/** FlakBullets explode range, 0 to disable */
	public float explodeRange = 0f;
	public float explodeDelay = 5f;

	public UAWBulletType(float speed, float damage) {
		this.speed = speed;
		this.damage = damage;
	}

	public UAWBulletType() {
	}


}

