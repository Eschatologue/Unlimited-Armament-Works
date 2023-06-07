package UAW.type.weapon;

import UAW.audiovisual.Assets;
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
@Deprecated
public class MissileLauncherWeapon extends UAWWeapon {
	public TextureRegion missileRegion;
	public String missileName = Assets.U_MSL_crsmissile_M_01_red;
	public float missileLayerOffset = 0;
	public float missileSizeScl = 1f;

	public MissileLauncherWeapon(String name) {
		super(name);
		recoil = 0;
		predictTarget = false;
		ignoreRotation = true;
		rotate = false;
		shootCone = 55f;
	}

	public MissileLauncherWeapon() {
		this("");
	}

	@Override
	public void load() {
		super.load();
		missileRegion = Core.atlas.find(missileName);
	}

	public void drawMissile(Unit unit, WeaponMount mount) {
		float mScl = missileSizeScl * Draw.scl;

		float z = Draw.z();

		float
			rotation = unit.rotation - 90,
			realRecoil = Mathf.pow(mount.recoil, recoilPow) * recoil,
			weaponRotation = rotation + (rotate ? mount.rotation : baseRotation),
			wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -realRecoil),
			wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -realRecoil);

		Draw.z(z + layerOffset + missileLayerOffset);
		Draw.alpha(1 - mount.reload / reload);
		Draw.rect(missileRegion,
			wx, wy,
			missileRegion.width * mScl * -Mathf.sign(flipSprite),
			missileRegion.height * mScl,
			weaponRotation);
		Draw.reset();
	}

	@Override
	public void draw(Unit unit, WeaponMount mount) {
		super.draw(unit, mount);
		drawMissile(unit, mount);
	}
}
