package UAW.content;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Sound;
import mindustry.Vars;

public class UAWSfx {
	public static Sound
		artilleryShootHuge = new Sound(),
		artilleryExplosionHuge = new Sound(),
		bigGunShoot1 = new Sound(),
		cannonShoot1 = new Sound(),
		cannonShoot2 = new Sound(),
		cruiseMissileShoot1 = new Sound(),
		launcherShoot1 = new Sound(),
		shotgun_shoot_1 = new Sound(),
		suppressedShoot1 = new Sound(),
		torpedoShoot1 = new Sound();

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
		artilleryShootHuge = loadSound("artillery_shoot_huge");
		artilleryExplosionHuge = loadSound("artillery_explosion_huge");
		bigGunShoot1 = loadSound("bigGun_shoot_1");
		cannonShoot1 = loadSound("cannon_shoot_1");
		cannonShoot2 = loadSound("cannon_shoot_2");
		cruiseMissileShoot1 = loadSound("cruisemissile_shoot_1");
		launcherShoot1 = loadSound("launcher_shoot_1");
		shotgun_shoot_1 = loadSound("shotgun_shoot_1");
		suppressedShoot1 = loadSound("supressed_shoot_1");
		torpedoShoot1 = loadSound("torpedo_shoot_1");
	}
}