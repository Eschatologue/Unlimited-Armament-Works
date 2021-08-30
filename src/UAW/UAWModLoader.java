package UAW;

import UAW.content.*;
import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

public class UAWModLoader extends Mod{

    public UAWModLoader(){
        Log.info("Loaded UAWModLoader constructor.");

        //listen for game load event
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("frog");
                dialog.cont.add("behold").row();
                //mod sprites are prefixed with the mod name (this mod is called 'example-java-mod' in its config)
                dialog.cont.image(Core.atlas.find("UAW-icon")).pad(20f).row();
                dialog.cont.button("I see", dialog::hide).size(100f, 50f);
                dialog.show();
            });
        });
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
