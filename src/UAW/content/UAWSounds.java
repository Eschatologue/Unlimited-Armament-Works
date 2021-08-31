package UAW.content;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Sound;
import mindustry.Vars;

public class UAWSounds {
    protected static Sound loadSound(String soundName) {
        String name = "sounds/" + soundName;
        String path = Vars.tree.get(name + ".ogg").exists() ? name + ".ogg" : name + ".mp3";

        Sound sound = new Sound();

        AssetDescriptor<?> desc = Core.assets.load(path, Sound.class, new SoundLoader.SoundParameter(sound));
        desc.errored = Throwable::printStackTrace;

        return sound;
    }
    public static Sound
            CannonShot1 = new Sound(),
            CannonShot2 = new Sound(),
            LauncherShot1 = new Sound(),
            MissileLaunch1 = new Sound(),
            ShotgunShotAuto1 = new Sound(),
            SuppressedShot1 = new Sound(),
            TorpedoFire1 = new Sound()
                    ;
    public static void load () {
        if(Vars.headless) return;
        CannonShot1 = loadSound("CannonShot1");
        CannonShot2 = loadSound("CannonShot2");
        LauncherShot1 = loadSound("LauncherShot1");
        MissileLaunch1 = loadSound("MissileLaunch1");
        ShotgunShotAuto1 = loadSound("ShotgunShotAuto1");
        SuppressedShot1 = loadSound("SuppressedShot1");
        TorpedoFire1 = loadSound("TorpedoFire1");
    }
}