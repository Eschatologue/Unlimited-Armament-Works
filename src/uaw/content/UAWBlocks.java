package uaw.content;

import uaw.content.blocks.BlocksDefence;
import uaw.content.blocks.BlocksProduction;

public class UAWBlocks {
    public static void load() {
        BlocksProduction.load();
        BlocksDefence.load();
    }
}
