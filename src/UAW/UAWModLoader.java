package UAW;

import UAW.content.*;
import arc.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;

public class UAWModLoader extends Mod{
    public UAWModLoader(){
        Events.on(FileTreeInitEvent.class, e -> UAWSfx.load());
    }
    @Override
    public void loadContent(){
        new UAWStatusEffects().load();
        new UAWBullets().load();
        new UAWUnitTypes().load();
        new UAWItems().load();
        new UAWLiquid().load();
        new UAWBlock().load();
        new UAWTechTree().load();
    }
}
