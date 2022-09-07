package UAW.type.weapon;

import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.math.*;
import mindustry.entities.part.DrawPart;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.type.Weapon;

public class UAWWeapon extends Weapon {
	public float outlineLayerOffset = 0f;

	public UAWWeapon(String name) {
		this.name = name;
	}

	public UAWWeapon() {
		this("");
	}

	@Override
	public void draw(Unit unit, WeaponMount mount) {
		//apply layer offset, roll it back at the end
		float z = Draw.z();
		Draw.z(z + layerOffset);

		float
			rotation = unit.rotation - 90,
			realRecoil = Mathf.pow(mount.recoil, recoilPow) * recoil,
			weaponRotation = rotation + (rotate ? mount.rotation : baseRotation),
			wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -realRecoil),
			wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -realRecoil);

		if (shadow > 0) {
			Drawf.shadow(wx, wy, shadow);
		}
		Draw.reset();
		if (top) {
			Draw.z(z + layerOffset + outlineLayerOffset);
			drawOutline(unit, mount);
			Draw.reset();
		}

		Draw.z(z + layerOffset);

		if (parts.size > 0) {
			DrawPart.params.set(mount.warmup, mount.reload / reload, mount.smoothReload, mount.heat, mount.recoil, mount.charge, wx, wy, weaponRotation + 90);
			DrawPart.params.sideMultiplier = flipSprite ? -1 : 1;

			for (int i = 0; i < parts.size; i++) {
				var part = parts.get(i);
				if (part.under) {
					part.draw(DrawPart.params);
				}
			}
		}

		Draw.xscl = -Mathf.sign(flipSprite);

		if (region.found()) Draw.rect(region, wx, wy, weaponRotation);

		if (cellRegion.found()) {
			Draw.color(unit.type.cellColor(unit));
			Draw.rect(cellRegion, wx, wy, weaponRotation);
			Draw.color();
		}

		if (heatRegion.found() && mount.heat > 0) {
			Draw.color(heatColor, mount.heat);
			Draw.blend(Blending.additive);
			Draw.rect(heatRegion, wx, wy, weaponRotation);
			Draw.blend();
			Draw.color();
		}

		if (parts.size > 0) {
			//TODO does it need an outline?
			for (int i = 0; i < parts.size; i++) {
				var part = parts.get(i);
				if (!part.under) {
					part.draw(DrawPart.params);
				}
			}
		}

		Draw.xscl = 1f;

		Draw.z(z);
	}

}
