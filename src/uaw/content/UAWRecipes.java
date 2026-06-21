package uaw.content;

import dev.jojofr.multicrafter.type.IOEntry;
import dev.jojofr.multicrafter.type.Recipe;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.Pal;
import uaw.audiovisual.fx.CombatFx;

import static mindustry.type.ItemStack.with;
import static uaw.Vars.tick;

public class UAWRecipes {

    // region Anthracite
    public static class Anthracite {
        public static final Recipe fromCoal = new Recipe("anthracite",
                new IOEntry().withItems(with(Items.coal, 2)),
                new IOEntry().withItems(with(UAWItems.anthracite, 1, UAWItems.sulphur, 1)),
                2 * tick
        ) {{
            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke, Fx.formsmoke);
            updateEffect = CombatFx.hitSparks(16, Pal.lightFlame, 360);
        }}.isUnlocked();
    }
    // endregion

    // region Graphite
    public static class Graphite {
        public static final Recipe fromAnthracite = new Recipe("graphite",
                new IOEntry().withItems(with(UAWItems.anthracite, 1)),
                new IOEntry().withItems(with(Items.graphite, 3)),
                2 * tick
        ) {{
            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke, Fx.formsmoke);
            updateEffect = Fx.fireHit;
        }}.isUnlocked();
    }
    // endregion
}
