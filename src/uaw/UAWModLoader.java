package uaw;

import arc.Events;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import uaw.audiovisual.Sfx;
import uaw.content.UAWBlocks;
import uaw.content.UAWItems;
import uaw.content.UAWLiquids;

public class UAWModLoader extends Mod {
    public UAWModLoader() {
        Events.on(EventType.FileTreeInitEvent.class, e -> Sfx.load());
    }

    @Override
    public void loadContent() {

        UAWItems.load();
        UAWLiquids.load();
        UAWBlocks.load();
    }
}
