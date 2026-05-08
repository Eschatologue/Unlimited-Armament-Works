package uaw.content;

import uaw.content.blocks.BlocksDefence;
import uaw.content.blocks.BlocksProduction;
import uaw.content.blocks.BlocksStorage;

public class UAWBlocks {
    public static void load() {
        BlocksStorage.load();
        BlocksProduction.load();
        BlocksDefence.load();
    }
}
