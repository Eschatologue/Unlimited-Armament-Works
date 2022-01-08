package UAW.type.weapon;

import UAW.world.meta.UAWStatValues;
import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.math.*;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.util.Strings;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.type.*;
import mindustry.world.meta.*;

public class UAWWeapon extends Weapon {
	public float weaponLayer = 0;

	public UAWWeapon(String name) {
		this.name = name;
	}

	public UAWWeapon() {
		this("");
	}

	public void draw(Unit unit, WeaponMount mount) {
		//apply layer offset, roll it back at the end
		float z = Draw.z();
		if (weaponLayer == 0f) {
			Draw.z(z + layerOffset);
		}

		float
			rotation = unit.rotation - 90,
			weaponRotation = rotation + (rotate ? mount.rotation : 0),
			wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -mount.recoil),
			wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -mount.recoil);

		if (shadow > 0) {
			Drawf.shadow(wx, wy, shadow);
		}

		if (top) {
			drawOutline(unit, mount);
		}

		if (weaponLayer != 0){
			Draw.z(weaponLayer);
		}
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

		if (weaponLayer == 0) {
			Draw.z(z);
		}
	}

	@Override
	public void addStats(UnitType u, Table t) {
		if (inaccuracy > 0) {
			t.row();
			t.add("[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int) inaccuracy + " " + StatUnit.degrees.localized());
		}
		t.row();
		t.add("[lightgray]" + Stat.reload.localized() + ": " + (mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / reload * shots, 2) + " " + StatUnit.perSecond.localized());

		UAWStatValues.ammo(ObjectMap.of(u, bullet)).display(t);
	}
}
