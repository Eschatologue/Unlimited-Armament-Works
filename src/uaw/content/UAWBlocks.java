package uaw.content;

import uaw.content.blocks.Power;
import uaw.content.blocks.Production;
import uaw.content.blocks.Storage;
import uaw.content.blocks.Turrets;

public class UAWBlocks {
    public static void load() {
        Power.load();
        Production.load();
        Storage.load();
        Turrets.load();
    }
}
