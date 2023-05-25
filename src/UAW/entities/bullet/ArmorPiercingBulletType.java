package UAW.entities.bullet;

import arc.Events;
import arc.util.Tmp;
import mindustry.game.EventType;
import mindustry.gen.*;

/** {@link TrailBulletType} that has the capability to ignore damage reduction from armour in a percentile */
public class ArmorPiercingBulletType extends TrailBulletType {
	static final EventType.UnitDamageEvent bulletDamageEvent = new EventType.UnitDamageEvent();

	/**
	 * Refers to how much damage will ignores armour, if the damage is 50 and the {@code armorPierceScl} is 0.5 thats
	 * mean 25 damage will still be dealt despite of armour
	 */
	public float armorPierceScl = -1;

	public ArmorPiercingBulletType(float speed, float damage) {
		super(speed, damage, "bullet");

	}

	@Override
	public void hitEntity(Bullet b, Hitboxc entity, float health) {
		boolean wasDead = entity instanceof Unit u && u.dead;

		if (entity instanceof Healthc h) {
			if (armorPierceScl > 0 && armorPierceScl < 1) {
				float apDmg = b.damage * armorPierceScl;
				h.damagePierce(apDmg + (b.damage - apDmg));
			} else if (pierceArmor || armorPierceScl >= 1) {
				h.damagePierce(b.damage);
			} else {
				h.damage(b.damage);
			}
		}

		if (entity instanceof Unit unit) {
			Tmp.v3.set(unit).sub(b).nor().scl(knockback * 80f);
			if (impact) Tmp.v3.setAngle(b.rotation() + (knockback < 0 ? 180f : 0f));
			unit.impulse(Tmp.v3);
			unit.apply(status, statusDuration);

			Events.fire(bulletDamageEvent.set(unit, b));
		}

		if (!wasDead && entity instanceof Unit unit && unit.dead) {
			Events.fire(new EventType.UnitBulletDestroyEvent(unit, b));
		}

		handlePierce(b, health, entity.x(), entity.y());
	}
}
