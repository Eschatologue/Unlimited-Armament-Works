package UAW.type.weapon;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;

/**
 * Inspired from NewestGitHubber / Commandustry missile weapon, modified slightly
 *
 * @Author SMOLKEYS
 */
public class MissileLauncherWeapon extends UAWWeapon {
	public TextureRegion missileRegion, missileOutlineRegion;
	public String missileName = "uaw-w-cruise-missile-basic";

	public MissileLauncherWeapon(String name) {
		this.name = name;
		recoil = 0;
		predictTarget = false;
		rotate = false;
		shootCone = 45f;
	}

	public MissileLauncherWeapon() {
		this("");
	}

	@Override
	public void load() {
		super.load();
		missileRegion = Core.atlas.find(missileName);
		missileOutlineRegion = Core.atlas.find(missileName + "-outline");
	}

	public void drawMissile(Unit unit, WeaponMount mount) {
		float z = Draw.z();
		float rotation = unit.rotation - 90;
		float weaponRotation = rotation + (rotate ? mount.rotation : 0);
		float wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -mount.recoil);
		float wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -mount.recoil);

		Draw.z(z + layerOffset + 0.01f);
		Draw.alpha(1 - mount.reload / reload);
		Draw.rect(missileOutlineRegion,
			wx, wy,
			missileOutlineRegion.width * Draw.scl,
			missileOutlineRegion.height * Draw.scl,
			weaponRotation);
		Draw.rect(missileRegion,
			wx, wy,
			missileRegion.width * Draw.scl,
			missileRegion.height * Draw.scl,
			weaponRotation);
	}

	@Override
	public void draw(Unit unit, WeaponMount mount) {
		super.draw(unit, mount);
		drawMissile(unit, mount);
	}
}
