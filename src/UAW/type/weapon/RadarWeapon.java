package UAW.type.weapon;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Time;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.type.Weapon;

public class RadarWeapon extends Weapon {
	public TextureRegion radarBase, radarTop;

	public RadarWeapon(String name) {
		this.name = name;
		rotate = autoTarget = true;
		mirror = controllable = false;
		layerOffset = 1f;
	}

	@Override
	public void load() {
		super.load();
		radarBase = Core.atlas.find(name + "-base");
		radarTop = Core.atlas.find(name + "-top");
	}

	@Override
	public void drawOutline(Unit unit, WeaponMount mount) {
		float rotation = unit.rotation - 90;
		float spinRotation = rotation * (Time.time * rotateSpeed);
		float wx = unit.x + Angles.trnsx(rotation, x, y);
		float wy = unit.y + Angles.trnsy(rotation, x, y);

		if (outlineRegion.found()) {
			Draw.rect(outlineRegion,
				wx, wy,
				outlineRegion.width * Draw.scl * -Mathf.sign(flipSprite),
				outlineRegion.height * Draw.scl,
				spinRotation);
		}
	}

	@Override
	public void draw(Unit unit, WeaponMount mount) {
		float z = Draw.z();
		Draw.z(z + layerOffset);
		float rotation = unit.rotation - 90;
		float spinRotation = rotation * (Time.time * rotateSpeed);
		float wx = unit.x + Angles.trnsx(rotation, x, y);
		float wy = unit.y + Angles.trnsy(rotation, x, y);

		if (shadow > 0) {
			Drawf.shadow(wx, wy, shadow);
		}
		if (top) {
			drawOutline(unit, mount);
		}
		Draw.rect(
			radarBase, wx, wy,
			region.width * Draw.scl,
			region.height * Draw.scl,
			rotation
		);
		Draw.rect(
			region, wx, wy,
			region.width * Draw.scl,
			region.height * Draw.scl,
			spinRotation
		);
		if (radarTop.found()) {
			Draw.rect(
				radarTop, wx, wy,
				region.width * Draw.scl,
				region.height * Draw.scl,
				rotation
			);
		}
		Draw.z(z);
	}

}
