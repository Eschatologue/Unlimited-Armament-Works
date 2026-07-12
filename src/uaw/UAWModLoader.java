package uaw;

import arc.Events;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import uaw.audiovisual.UAWSfx;
import uaw.content.UAWBlocks;
import uaw.content.UAWItems;
import uaw.content.UAWLiquids;
import uaw.content.UAWRecipes;

public class UAWModLoader extends Mod {
    public UAWModLoader() {
        Events.on(EventType.FileTreeInitEvent.class, e -> UAWSfx.load());
    }

    @Override
    public void loadContent() {
        UAWItems.load();
        UAWLiquids.load();
        UAWRecipes.load();
        UAWBlocks.load();
    }
}
