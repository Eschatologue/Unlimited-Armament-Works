package UAW.content;

import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;

public class UAWStatusEffects implements ContentList {
    public static StatusEffect
            concussion, riptide, flooding,
            breached;

    @Override
    public void load () {
        concussion = new StatusEffect("concussion") {{
            color = Pal.lightishGray;
            reloadMultiplier = speedMultiplier = 0.4f;
        }};
        breached = new StatusEffect("breached") {{
            healthMultiplier = 0.55f;
            permanent = true;
            effect = Fx.shieldBreak;
        }};
    }
}