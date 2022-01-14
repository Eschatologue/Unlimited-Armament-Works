package UAW.type.weapon;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;

/**
 * Inspired from NewestGitHubber / Commandustry missile weapon, modified slightly
 * @Author SMOLKEYS
 */
public class MissileLauncherWeapon extends UAWWeapon {
	public TextureRegion missileRegion, missileOutlineRegion;
	public String missileName = "uaw-cruise-missile-basic";
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
		missileOutlineRegion = Core.atlas.find(missileName + "outline");
	}

	@Override
	public void draw(Unit unit, WeaponMount mount) {
		float
			rotation = unit.rotation - 90,
			weaponRotation = rotation + (rotate ? mount.rotation : 0),
			mx = unit.x + Angles.trnsx(rotation, x + missileOffsetX, y + missileOffsetY) + Angles.trnsx(weaponRotation, 0, -mount.recoil),
			my = unit.y + Angles.trnsy(rotation, x + missileOffsetX, y + missileOffsetY) + Angles.trnsy(weaponRotation, 0, -mount.recoil);

		super.draw(unit, mount);

		Draw.alpha(1 - mount.reload / reload);
		Draw.rect(missileRegion,
			mx, my,
			missileRegion.width * Draw.scl * -Mathf.sign(flipSprite),
			missileRegion.height * Draw.scl,
			weaponRotation);

		Draw.rect(missileOutlineRegion,
			mx, my,
			missileOutlineRegion.width * Draw.scl * -Mathf.sign(flipSprite),
			missileOutlineRegion.height * Draw.scl,
			weaponRotation);
	}
}
