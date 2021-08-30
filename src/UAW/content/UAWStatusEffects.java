package UAW.content;

import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;

public class UAWStatusEffects implements ContentList {
    public static StatusEffect
            EMP, riptide, superCorrosive,
            breached;

    @Override
    public void load () {
        EMP = new StatusEffect("EMP") {{
            color = Pal.lancerLaser;
            speedMultiplier = 0.001f;
            reloadMultiplier = 0f;
            effect = Fx.hitFuse;
        }};
        breached = new StatusEffect("breached") {{
            healthMultiplier = 0.55f;
            permanent = true;
            effect = Fx.shieldBreak;
        }};
    }
}