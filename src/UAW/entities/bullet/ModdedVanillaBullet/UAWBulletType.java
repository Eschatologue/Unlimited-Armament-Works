package UAW.entities.bullet.ModdedVanillaBullet;

import arc.util.*;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.*;

public class UAWBulletType extends BulletType {
	/** Percentage of bullet damage that ignores armor */
	public float armorIgnoreScl = 0f;
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

	@Override
	public void hitEntity(Bullet b, Hitboxc entity, float health) {
		// Armor Ignore
		if (entity instanceof Unit unit && armorIgnoreScl > 0) {
			unit.health -= b.damage * armorIgnoreScl;
		}

		// Shield Damage
		if (entity instanceof Shieldc shield && shieldDamageMultiplier > 1) {
			shield.damage(b.damage * shieldDamageMultiplier);
		}

		super.hitEntity(b, entity, health);
	}

	@Override
	public void update(Bullet b) {
		super.update(b);
		if (b.fdata < 0f) return;
		if (b.timer(2, 6) && explodeRange > 0) {
			Units.nearbyEnemies(b.team, Tmp.r1.setSize(explodeRange * 2f).setCenter(b.x, b.y), unit -> {
				if (b.fdata < 0f || !unit.checkTarget(collidesAir, collidesGround)) return;
				if (unit.within(b, explodeRange)) {
					b.fdata = -1f;
					Time.run(explodeDelay, () -> {
						if (b.fdata < 0) {
							b.time = b.lifetime;
						}
					});
				}
			});
		}
	}
}

