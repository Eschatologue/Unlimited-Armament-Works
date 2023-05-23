package UAW;

import UAW.audiovisual.Sfx;
import UAW.content.*;
import UAW.content.techTree.*;
import arc.Events;
import mindustry.game.EventType.FileTreeInitEvent;
import mindustry.mod.Mod;

public class UAWModLoader extends Mod {
	public UAWModLoader() {
		Events.on(FileTreeInitEvent.class, e -> Sfx.load());
	}

	@Override
	public void loadContent() {
		UAWStatusEffects.load();
		TechTreeContent.load();
		UAWBullets.load();
		UAWUnitTypes.load();
		UAWItems.load();
		UAWLiquids.load();
		UAWBlocks.load();
		UAWTechTree.load();

	}
}
