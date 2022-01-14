package UAW.type.weapon;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;

/**
 * Inspired from NewestGitHubber / Commandustry missile weapon, modified slightly
 *
 * @Author SMOLKEYS
 */
public class MissileLauncherWeapon extends UAWWeapon {
	public TextureRegion missileRegion, missileOutlineRegion;
	public String missileName = "uaw-w-cruise-missile-basic";
	public float missileOffsetX = 0, missileOffsetY = 0f;

	public MissileLauncherWeapon(String name) {
		this.name = name;
		recoil = 0;
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

	@Override
	public void draw(Unit unit, WeaponMount mount) {
		float z = Draw.z();
		Draw.z(z + layerOffset);
		float rotation = unit.rotation - 90;
		float weaponRotation = rotation + (rotate ? mount.rotation : 0);
		float wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -mount.recoil);
		float wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -mount.recoil);

		super.draw(unit, mount);

		Draw.z(z + layerOffset + 1);
		Draw.alpha(1 - mount.reload / reload);
		Draw.rect(missileRegion,
			wx, wy,
			missileRegion.width * Draw.scl * -Mathf.sign(flipSprite),
			missileRegion.height * Draw.scl,
			weaponRotation);

		Draw.rect(missileOutlineRegion,
			wx, wy,
			missileOutlineRegion.width * Draw.scl * -Mathf.sign(flipSprite),
			missileOutlineRegion.height * Draw.scl,
			weaponRotation);
	}
}
