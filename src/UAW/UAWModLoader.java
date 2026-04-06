package UAW;

import UAW.content.*;
import mindustry.mod.Mod;

public class UAWModLoader extends Mod {

    @Override
    public void loadContent() {

        UAWBlocks.load();
    }
}
