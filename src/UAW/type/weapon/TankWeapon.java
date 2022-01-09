package UAW.type.weapon;

import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.math.*;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.*;
import mindustry.graphics.*;

public class TankWeapon extends UAWWeapon {
	public float weaponLayer = Layer.groundUnit;

	public TankWeapon(String name) {
		this.name = name;
	}

	@Override
	public void draw(Unit unit, WeaponMount mount) {
		Mechc mech = unit instanceof Mechc ? (Mechc)unit : null;
		Unit mechUnit = (Unit) mech;
		float rotation = mech.baseRotation() - 90;
		float weaponRotation = rotation + (rotate ? mount.rotation : 0);
		float wx = mechUnit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -mount.recoil);
		float wy = mechUnit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -mount.recoil);

		if (shadow > 0) {
			Drawf.shadow(wx, wy, shadow);
		}

		if (top) {
			drawOutline(unit, mount);
		}

		Draw.z(weaponLayer);
		Draw.rect(region,
			wx, wy,
			region.width * Draw.scl * -Mathf.sign(flipSprite),
			region.height * Draw.scl,
			weaponRotation);

		if (heatRegion.found() && mount.heat > 0) {
			Draw.color(heatColor, mount.heat);
			Draw.blend(Blending.additive);
			Draw.rect(heatRegion,
				wx, wy,
				heatRegion.width * Draw.scl * -Mathf.sign(flipSprite),
				heatRegion.height * Draw.scl,
				weaponRotation);
			Draw.blend();
			Draw.color();
		}

	}
}

