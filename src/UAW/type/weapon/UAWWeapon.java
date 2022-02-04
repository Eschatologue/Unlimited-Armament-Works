package UAW.type.weapon;

import UAW.world.meta.UAWStatValues;
import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.util.Strings;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.type.*;
import mindustry.world.meta.*;

public class UAWWeapon extends Weapon {
	public boolean customIcon = false;
	public TextureRegion weaponOutline;

	public UAWWeapon(String name) {
		this.name = name;
	}

	public UAWWeapon() {
		this("");
	}

	@Override
	public void drawOutline(Unit unit, WeaponMount mount) {
		float
			rotation = unit.rotation - 90,
			weaponRotation = rotation + (rotate ? mount.rotation : 0),
			wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -mount.recoil),
			wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -mount.recoil);
		if (outlineRegion.found() && !customIcon) {
			Draw.rect(outlineRegion,
				wx, wy,
				outlineRegion.width * Draw.scl * -Mathf.sign(flipSprite),
				outlineRegion.height * Draw.scl,
				weaponRotation);
		} else if (weaponOutline.found() && customIcon) {
			Draw.rect(weaponOutline,
				wx, wy,
				weaponOutline.width * Draw.scl * -Mathf.sign(flipSprite),
				weaponOutline.height * Draw.scl,
				weaponRotation);
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

	@Override
	public void load() {
		if (!customIcon) {
			super.load();
		} else {
			super.load();
			outlineRegion = Core.atlas.find(name + "-icon");
		}
		weaponOutline = Core.atlas.find(name + "-outline");
	}
}
