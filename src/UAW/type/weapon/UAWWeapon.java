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

import static mindustry.gen.Groups.unit;

public class UAWWeapon extends Weapon {
	public TextureRegion weaponOutline, weaponBase, weaponBaseOutline;
	public float baseRecoilMultiplier = 0f;
	public boolean customIcon = false;

	public UAWWeapon(String name) {
		this.name = name;
	}

	public UAWWeapon() {
		this("");
	}

	public void drawBase(Unit unit, WeaponMount mount) {
		float rotation = unit.rotation - 90;
		float weaponRotation = rotation + (rotate ? mount.rotation : 0);
		float wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -mount.recoil * baseRecoilMultiplier);
		float wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -mount.recoil * baseRecoilMultiplier);

		if (weaponBase.found()) {
			Draw.rect(weaponBase,
				wx, wy,
				weaponBase.width * Draw.scl * -Mathf.sign(flipSprite),
				weaponBase.height * Draw.scl,
				weaponRotation);
		}
	}

	public void drawBaseOutline(Unit unit, WeaponMount mount) {
		float rotation = unit.rotation - 90;
		float weaponRotation = rotation + (rotate ? mount.rotation : 0);
		float wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -mount.recoil * baseRecoilMultiplier);
		float wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -mount.recoil * baseRecoilMultiplier);

		if (weaponBase.found()) {
			Draw.rect(weaponBaseOutline,
				wx, wy,
				weaponBaseOutline.width * Draw.scl * -Mathf.sign(flipSprite),
				weaponBaseOutline.height * Draw.scl,
				weaponRotation);
		}
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
	public void draw(Unit unit, WeaponMount mount) {
		drawBaseOutline(unit, mount);
		super.draw(unit, mount);
		drawBase(unit, mount);
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
		weaponBase = Core.atlas.find(name + "-base");
		weaponBaseOutline = Core.atlas.find(name + "-base-outline");
		weaponOutline = Core.atlas.find(name + "-outline");
	}
}
