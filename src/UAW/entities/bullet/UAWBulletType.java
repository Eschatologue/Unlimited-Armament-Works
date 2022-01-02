package UAW.entities.bullet;

import arc.Events;
import arc.util.*;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.world.blocks.defense.Wall;

import static mindustry.Vars.player;

public class UAWBulletType extends BulletType {
	public float armorIgnoreScl = 0f;
	public float shieldDamageMultiplier = 1f;

	public @Nullable BulletType afterShock = null;

	public UAWBulletType(float speed, float damage) {
		this.speed = speed;
		this.damage = damage;
	}

	public UAWBulletType() {
	}

	@Override
	public void hitEntity(Bullet b, Hitboxc entity, float health) {
		float realDamage = b.damage * armorIgnoreScl;
		if (entity instanceof Healthc h) {
			h.damagePierce(realDamage);
			if (armorIgnoreScl < 1) {
				h.damage(b.damage - realDamage);
			} else h.damage(b.damage);
		}
		if (entity instanceof Shieldc h && shieldDamageMultiplier > 1) {
			h.damagePierce(b.damage * shieldDamageMultiplier);
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
}

