package UAW.audiovisual;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Sound;
import mindustry.Vars;

public class Sfx {
	public static Sound placeholder,

	// Explosion
	exp_k_metalpipe = new Sound(),
		exp_n_impactHuge_1 = new Sound(),

	// Cannons
	wp_k_cannonShoot_1 = new Sound(),
		wp_k_cannonShoot_2 = new Sound(),

	// Gun
	wp_k_gunShootSmall_1 = new Sound(),
		wp_k_gunShootSmall_2 = new Sound(),

	wp_k_gunShoot_1 = new Sound(),
		wp_k_gunShoot_2 = new Sound(),
		wp_k_gunShoot_3 = new Sound(),
		wp_k_gunShoot_4 = new Sound(),
		wp_k_gunShoot_5 = new Sound(),
		wp_k_gunShoot_6 = new Sound(),
		wp_k_gunShoot_7 = new Sound(),

	wp_k_gunShootBig_1 = new Sound(),
		wp_k_gunShootBig_2 = new Sound(),

	// Shotgun
	/** Sounds like a muffled slug */
	wp_k_shotgunShoot_1 = new Sound(),
		wp_k_shotgunShoot_2 = new Sound(),
		wp_k_shotgunShoot_3 = new Sound(),

	// Launchers
	wp_lnch_springShoot_1 = new Sound(),
		wp_lnch_springShoot_2 = new Sound(),
		wp_lnch_springShoot_3 = new Sound(),

	wp_lnch_energyShoot_1 = new Sound(),

	// Mines
	wp_mine_detonateSound_1 = new Sound(),

	// Missiles
	wp_msl_missileLaunch_1_big = new Sound(),
		wp_msl_missileLaunch_2_big = new Sound(),

	// Torpedoes
	wp_torp_torpedoLaunch_1 = new Sound(),
		wp_torp_torpedoLaunchSpring_1 = new Sound();


	protected static Sound loadSound(String fileName) {
		String name = "sounds/" + fileName;
		String path = Vars.tree.get(name + ".ogg").exists() ? name + ".ogg" : name + ".mp3";

		Sound sound = new Sound();

		AssetDescriptor<?> desc = Core.assets.load(path, Sound.class, new SoundLoader.SoundParameter(sound));
		desc.errored = Throwable::printStackTrace;

		return sound;
	}

	public static void load() {
		if (Vars.headless) return;

		exp_k_metalpipe = loadSound("exp_k_metalpipe");
		exp_n_impactHuge_1 = loadSound("exp_n_impactHuge_1");

		wp_k_cannonShoot_1 = loadSound("wp_k_cannonShoot_1");
		wp_k_cannonShoot_2 = loadSound("wp_k_cannonShoot_2");

		wp_k_gunShootSmall_1 = loadSound("wp_k_gunShootSmall_1");
		wp_k_gunShootSmall_2 = loadSound("wp_k_gunShootSmall_2");

		wp_k_gunShoot_1 = loadSound("wp_k_gunShoot_1");
		wp_k_gunShoot_2 = loadSound("wp_k_gunShoot_2");
		wp_k_gunShoot_3 = loadSound("wp_k_gunShoot_3");
		wp_k_gunShoot_4 = loadSound("wp_k_gunShoot_4");
		wp_k_gunShoot_5 = loadSound("wp_k_gunShoot_5");
		wp_k_gunShoot_6 = loadSound("wp_k_gunShoot_6");
		wp_k_gunShoot_7 = loadSound("wp_k_gunShoot_7");

		wp_k_gunShootBig_1 = loadSound("wp_k_gunShootBig_1");
		wp_k_gunShootBig_2 = loadSound("wp_k_gunShootBig_2");

		wp_k_shotgunShoot_1 = loadSound("wp_k_shotgunShoot_1");
		wp_k_shotgunShoot_2 = loadSound("wp_k_shotgunShoot_2");
		wp_k_shotgunShoot_3 = loadSound("wp_k_shotgunShoot_3");

		wp_lnch_springShoot_1 = loadSound("wp_lnch_springShoot_1");
		wp_lnch_springShoot_2 = loadSound("wp_lnch_springShoot_2");
		wp_lnch_springShoot_3 = loadSound("wp_lnch_springShoot_3");

		wp_lnch_energyShoot_1 = loadSound("wp_lnch_energyShoot_1");

		wp_mine_detonateSound_1 = loadSound("wp_mine_detonateSound_1");

		wp_msl_missileLaunch_1_big = loadSound("wp_msl_missileLaunch_1_big");
		wp_msl_missileLaunch_2_big = loadSound("wp_msl_missileLaunch_2_big");

		wp_torp_torpedoLaunch_1 = loadSound("wp_torp_torpedoLaunch_1");
		wp_torp_torpedoLaunchSpring_1 = loadSound("wp_torp_torpedoLaunchSpring_1");
	}


}