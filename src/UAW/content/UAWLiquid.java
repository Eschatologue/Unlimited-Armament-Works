package UAW.content;

import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;

public class UAWLiquid implements ContentList {
    public static Liquid
            surgeSolvent, thoriumAcid;

    @Override
    public void load () {
        surgeSolvent = new Liquid("surge-solvent", Pal.surge) {{
            viscosity = 0.65f;
            barColor = Pal.surge;
            effect = StatusEffects.electrified;
        }};
    }
}
