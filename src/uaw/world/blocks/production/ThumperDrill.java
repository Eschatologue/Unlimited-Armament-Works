package uaw.world.blocks.production;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.blocks.production.BurstDrill;
import uaw.audiovisual.UAWSfx;

/**
 * A drill that hammers the ground, only visual changes
 * <p>Used as the base class for {@link ConversionDrill}, which add tile-matching and item-conversion logic on top.</p>
 */
public class ThumperDrill extends BurstDrill {

    public ThumperDrill(final String name) {
        super(name);
        tier = 3;
        squareSprite = false;

        drillSound = UAWSfx.mineThumperDrill;
        ambientSound = Sounds.none;
        arrows = 0;

        shake = 2f;
    }

    public class ThumperDrillBuild extends BurstDrill.BurstDrillBuild {

        @Override
        public void draw() {
            final float px = 0.25f;
            Draw.rect(region, x, y);
            drawDefaultCracks();

            // fract drives both the offset of the shadow and the scale of the drill head.
            // Clamping keeps it in a sensible visual range during the wind-up and return.
            float fract = Mathf.clamp(smoothProgress, px, 0.3f);

            // Shadow: drawn slightly down-left, darkened by Pal.shadow.
            Draw.color(Pal.shadow, Pal.shadow.a);
            Draw.rect(topRegion, x - (fract - px) * 40, y - (fract - px) * 40,
                    topRegion.width * fract, topRegion.width * fract);
            Draw.color();

            // Drill head on the additive layer so it blends with glow effects
            Draw.z(Layer.blockAdditive);
            Draw.rect(topRegion, x, y, topRegion.width * fract, topRegion.height * fract);

            // Tints the centre of the drill head with the colour of the dominant ore
            if (dominantItem != null && drawMineItem) {
                Draw.color(dominantItem.color);
                Draw.rect(itemRegion, x, y, itemRegion.width * fract, itemRegion.height * fract);
                Draw.color();
            }
        }
    }
}