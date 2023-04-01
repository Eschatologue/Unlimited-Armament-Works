package UAW.content;

import UAW.content.blocks.*;

public class UAWBlocks {
	public static void load() {
		UAWBlocksPower.load();
		UAWBlocksDefence.load();
		UAWBlocksLogistic.load();
		UAWBlocksProduction.load();
		UAWBlocksUnits.load();
	}
}

