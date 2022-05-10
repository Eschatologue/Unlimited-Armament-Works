package UAW;

import UAW.content.*;
import UAW.content.blocks.*;
import arc.Events;
import mindustry.game.EventType.FileTreeInitEvent;
import mindustry.mod.Mod;

public class UAWModLoader extends Mod {
	public UAWModLoader() {
		Events.on(FileTreeInitEvent.class, e -> UAWSfx.load());
	}

	public void UAWBlockContent() {
		new UAWBlocksPower().load();
		new UAWBlocksDefense().load();
		new UAWBlocksLogistic().load();
		new UAWBlocksProduction().load();
		new UAWBlocksUnits().load();
	}

	@Override
	public void loadContent() {
		new UAWStatusEffects().load();
		new UAWBullets().load();
		new UAWUnitTypes().load();
		new UAWItems().load();
		new UAWLiquids().load();
		UAWBlockContent();

	}
}
