package uaw.audiovisual;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Sound;
import mindustry.Vars;

import java.lang.reflect.Field;

public class Sfx {

    public static Sound
            mineThumperDrill;

    /** Scans and loads every 'Sound' field automatically */
    public static void load() {
        // Don't try to load sounds if the game is running without a window (server mode)
        if (Vars.headless) return;

        for (Field field : Sfx.class.getFields()) {
            if (field.getType() == Sound.class) {
                try {
                    String fileName = field.getName();
                    Sound loadedSound = loadSound(fileName);
                    field.set(null, loadedSound);

                } catch (Exception e) {
                    // If a file is missing or a name is wrong, print error
                    System.err.println("Failed to load sound: " + field.getName());
                    e.printStackTrace();
                }
            }
        }
    }

    private static Sound loadSound(String soundName) {
        String name = "sounds/" + soundName;
        String path = Vars.tree.get(name + ".ogg").exists() ? name + ".ogg" : name + ".mp3";
        Sound sound = new Sound();
        AssetDescriptor<?> desc = Core.assets.load(path, Sound.class, new SoundLoader.SoundParameter(sound));
        desc.errored = Throwable::printStackTrace;

        return sound;
    }
}

//public class Sfx {
//    public static Sound
//            mineThumperDrill = new Sound(),
//            shootSmall1 = new Sound();
//
//    public static void load() {
//        mineThumperDrill = loadSound("mine_thumper_drill");
//        shootSmall1 = loadSound("placeholder");
//    }
//
//    private static Sound loadSound(String soundName) {
//        if (!Vars.headless) {
//            String name = "sounds/" + soundName;
//            String path = Vars.tree.get(name + ".ogg").exists() ? name + ".ogg" : name + ".mp3";
//
//            Sound sound = new Sound();
//
//            AssetDescriptor<?> desc = Core.assets.load(path, Sound.class, new SoundLoader.SoundParameter(sound));
//            desc.errored = Throwable::printStackTrace;
//
//            return sound;
//
//        } else {
//            return new Sound();
//        }
//    }
//}


