package uaw.audiovisual;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Sound;
import mindustry.Vars;

import java.lang.reflect.Field;

public class UAWSfx {

    public static Sound
            // Resourcing
            mineThumperDrill,
    // Shoot
    shootBig1, shootSolo;

    /**
     * Scans and loads every 'Sound' field automatically
     */
    public static void load() {
        if (Vars.headless) return;

        for (Field field : UAWSfx.class.getFields()) {
            if (field.getType() == Sound.class) {
                try {
                    String fileName = field.getName();
                    Sound loadedSound = loadSound(fileName);
                    field.set(null, loadedSound);

                } catch (Exception e) {
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



