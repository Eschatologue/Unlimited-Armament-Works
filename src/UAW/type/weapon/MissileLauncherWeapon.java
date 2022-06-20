package UAW.type.weapon;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.graphics.MultiPacker;

import static UAW.Vars.cruiseMissileBasic;

/**
 * Inspired from NewestGitHubber / Commandustry missile weapon, modified slightly
 *
 * @Author SMOLKEYS
 */
public class MissileLauncherWeapon extends UAWWeapon {
	public TextureRegion missileRegion, missileOutlineRegion;
	public String missileName = cruiseMissileBasic;
	public float missileLayerOffset = 0;
	public float missileSizeScl = 1.25f;

	public MissileLauncherWeapon(String name) {
		this.name = name;
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
		missileOutlineRegion = Core.atlas.find(missileName + "-outline");
	}

	public void drawMissile(Unit unit, WeaponMount mount) {
		float z = Draw.z();
		float rotation = unit.rotation - 90;
		float mScl = missileSizeScl * Draw.scl;
		float weaponRotation = rotation + (rotate ? mount.rotation : 0);
		float wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -mount.recoil);
		float wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -mount.recoil);

		Draw.z(z + layerOffset + missileLayerOffset);
		Draw.alpha(1 - mount.reload / reload);
		Draw.rect(missileOutlineRegion,
			wx, wy,
			missileOutlineRegion.width * mScl * -Mathf.sign(flipSprite),
			missileOutlineRegion.height * mScl,
			weaponRotation);
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
