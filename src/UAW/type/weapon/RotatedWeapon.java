package UAW.type.weapon;

import mindustry.type.Weapon;

public class RotatedWeapon extends Weapon {
	public RotatedWeapon(String name) {
		this.name = name;
		top = true;
		rotate = false;
		ignoreRotation = true;
		shootCone = 360;
	}
}
