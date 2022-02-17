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
		gunShoot1 = new Sound(),
		gunShoot2 = new Sound(),
		gunShoot3 = new Sound(),
		gunShoot4 = new Sound(),
		gunShoot5 = new Sound(),
		cruiseMissileShoot1 = new Sound(),
		launcherShoot1 = new Sound(),
		mineDetonate1 = new Sound(),
		shotgunShoot1 = new Sound(),
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
		gunShoot1 = loadSound("gun_shoot_1");
		gunShoot2 = loadSound("gun_shoot_2");
		gunShoot3 = loadSound("gun_shoot_3");
		gunShoot4 = loadSound("gun_shoot_4");
		gunShoot5 = loadSound("gun_shoot_5");
		cruiseMissileShoot1 = loadSound("cruisemissile_shoot_1");
		launcherShoot1 = loadSound("launcher_shoot_1");
		mineDetonate1 = loadSound("mine_detonate_1");
		shotgunShoot1 = loadSound("shotgun_shoot_1");
		suppressedShoot1 = loadSound("supressed_shoot_1");
		torpedoShoot1 = loadSound("torpedo_shoot_1");
	}
}