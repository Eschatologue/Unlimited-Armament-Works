package UAW;

import arc.assets.Loadable;

public class Vars implements Loadable {
	// Stuff
	public static final float tick = 60f;
	public static final float px = 0.25f;
	public static final float liquidConsumption = 60;
	public static final String modName = "uaw-";
	public static final String modTurretBase = "armored-";

	// Weapon - Point Defense - PD
	public static final String pointDefense_Red = modName + "point-defense-red";
	public static final String pointDefense_Red_2 = modName + "point-defense-red-2";
	public static final String pointDefense_Purple = modName + "point-defense-purple";

	// Weapon - Artillery - ART / LNCH
	public static final String artillery_small_red = modName + "artillery-small-red";
	public static final String artillery_small_purple = modName + "artillery-small-purple";

	public static final String artillery_medium_red = modName + "artillery-medium-red";

	public static final String artillery_large_purple = modName + "artillery-large-purple";

	// Weapon - Machine Gun - MG
	public static final String machineGun_small_red = modName + "machine-gun-small-red";
	public static final String machineGun_small_purple = modName + "machine-gun-small-purple";

	public static final String machineGun_medium_red = modName + "machine-gun-medium-red";
	public static final String machineGun_medium_red_mirrored = modName + "machine-gun-medium-red-r";
	public static final String machineGun_medium_purple = modName + "machine-gun-medium-purple";

	public static final String machineGun_medium_red_single = modName + "machine-gun-medium-single-red";

	// Weapon - Missile - MSL
	public static final String missile_small_red_1 = modName + "missile-small-red-1";
	public static final String missile_small_red_2 = modName + "missile-small-red-2";

	public static final String missile_medium_red_1 = modName + "missile-medium-red-1";
	public static final String missile_medium_red_2 = modName + "missile-medium-red-2";
	public static final String missile_medium_red_3 = modName + "missile-medium-red-3";

	public static final String missile_large_purple_1 = modName + "missile-large-purple-1";

	public static final String cruiseMissileMount_1 = modName + "cruise-missile-mount-1";
	public static final String cruiseMissileMount_2 = modName + "cruise-missile-mount-2";

	// Cruise Missile Type
	public static final String cruiseMissile_Basic = modName + "cruise-missile-basic";
	public static final String cruiseMissile_Cryo = modName + "cruise-missile-cryo";
	public static final String cruiseMissile_Surge = modName + "cruise-missile-surge";
}
