package UAW.type.weapon;

import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.weapons.PointDefenseWeapon;

public class TankPointDefenseWeapon extends PointDefenseWeapon {
	public float minDamageTarget = 0f, maxDamageTarget = -1;

	public TankPointDefenseWeapon(String name) {
		super(name);
		color = Pal.missileYellow;
		recoil = 0;
		ignoreRotation = true;
		targetInterval = 8f;
		targetSwitchInterval = 3;
		ejectEffect = Fx.casing2;
	}

	@Override
	protected Teamc findTarget(Unit unit, float x, float y, float range, boolean air, boolean ground) {
		return Groups.bullet.intersect(
			x - range,
			y - range,
			range * 2,
			range * 2
		).min(
			b -> b.team != unit.team
				&& b.type().hittable
				&& b.type().damage > minDamageTarget
				&& b.type().damage < (maxDamageTarget < 0 ? Float.MAX_VALUE : maxDamageTarget),
			b -> b.dst2(x, y));
	}

	@Override
	protected boolean checkTarget(Unit unit, Teamc target, float x, float y, float range) {
		return !(target.within(unit, range)
			&& target.team() != unit.team
			&& target instanceof Bullet bullet
			&& bullet.type != null && bullet.type.hittable
			&& bullet.damage > minDamageTarget
			&& bullet.damage < (maxDamageTarget < 0 ? Float.MAX_VALUE : maxDamageTarget)
		);
	}

//	@Override
//	protected void shoot(Unit unit, WeaponMount mount, float shootX, float shootY, float rotation) {
//		if (!(mount.target instanceof Bullet target)) return;
//		super.shoot(unit, mount, shootX, shootY, rotation);
//		Effect.shake(bullet.hitShake, bullet.hitShake, target.x, target.y);
//	}
}
