package UAW.entities.bullet.ModdedVanillaBullet;

import arc.Events;
import arc.util.*;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.world.blocks.defense.Wall;

import static mindustry.Vars.player;

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
		if (entity instanceof Healthc h && entity instanceof Unit unit) {
			float armorPierce = unit.armor * armorIgnoreScl;
			h.damage(b.damage + armorPierce);
		}
		if (entity instanceof Shieldc shield && shieldDamageMultiplier > 1) {
			shield.damage(b.damage * shieldDamageMultiplier);
		}
		if (entity instanceof Unit unit) {
			Tmp.v3.set(unit).sub(b).nor().scl(knockback * 80f);
			if (impact) Tmp.v3.setAngle(b.rotation() + (knockback < 0 ? 180f : 0f));
			unit.impulse(Tmp.v3);
			unit.apply(status, statusDuration);
		}
		//for achievements
		if (b.owner instanceof Wall.WallBuild && player != null && b.team == player.team() && entity instanceof Unit unit && unit.dead) {
			Events.fire(EventType.Trigger.phaseDeflectHit);
		}
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

