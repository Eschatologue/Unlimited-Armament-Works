package uaw.world.blocks.production;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.blocks.production.BurstDrill;
import uaw.audiovisual.UAWSfx;

public class ThumperDrill extends BurstDrill {

    public TextureRegion liquidRegion;

    public ThumperDrill(final String name) {
        super(name);
        tier = 3;
        squareSprite = false;

        drillSound = UAWSfx.mineThumperDrill;
        ambientSound = Sounds.none;
        arrows = 0;

        shake = 2f;
        hardnessDrillMultiplier = 0f;
    }

    public class ThumperDrillBuild extends BurstDrill.BurstDrillBuild {

        @Override
        public void draw() {
            final float px = 0.25f;
            Draw.rect(region, x, y);
            drawDefaultCracks();
            float fract = Mathf.clamp(smoothProgress, px, 0.3f);
            Draw.color(Pal.shadow, Pal.shadow.a);
            Draw.rect(topRegion, x - (fract - px) * 40, y - (fract - px) * 40, topRegion.width * fract, topRegion.width * fract);
            Draw.color();
            Draw.z(Layer.blockAdditive);
            Draw.rect(topRegion, x, y, topRegion.width * fract, topRegion.height * fract);
            // Draws the liquid used to power the drill
            if (dominantItem != null && drawMineItem) {
                Draw.color(dominantItem.color);
                Draw.rect(itemRegion, x, y, itemRegion.width * fract, itemRegion.height * fract);
                Draw.color();
            }
        }

    }
}