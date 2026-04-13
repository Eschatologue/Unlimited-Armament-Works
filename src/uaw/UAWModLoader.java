package uaw;

import uaw.content.*;
import mindustry.mod.Mod;

public class UAWModLoader extends Mod {

    @Override
    public void loadContent() {

        UAWItems.load();
        UAWLiquids.load();
        UAWBlocks.load();
    }
}
