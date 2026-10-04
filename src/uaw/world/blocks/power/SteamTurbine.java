package uaw.world.blocks.power;

import mindustry.world.blocks.power.ConsumeGenerator;
import uaw.utils.BlockDef;

/**
 * A {@link ConsumeGenerator} that hands its stored liquid on to neighbouring blocks, the way a Liquid Router does
 */
public class SteamTurbine extends ConsumeGenerator {

    /**
     * Whether steam leaves through the front face only ({@code true}) or through every face ({@code false})
     * <ul>
     *   <li> {@code true}: steam leaves through the front face only. The player picks that face by rotating the block.</li>
     *   <li> {@code false}: steam leaves through every face, like a vanilla Liquid Router.</li>
     * </ul>
     */
    public boolean directionalOutput = true;

    public SteamTurbine(String name) {
        super(name);
        BlockDef.general(this, 3);
        hasLiquids = true;
        rotateDraw = false;
    }

    @Override
    public void init() {
        // Only a rotatable block lets the player choose the output face
        rotate = directionalOutput;
        super.init();
    }

    public class SteamTurbineBuild extends ConsumeGeneratorBuild {

        @Override
        public void updateTile() {
            super.updateTile();

            // The second argument is how gently liquid levels even out between blocks (vanilla default: 2).
            // The third picks the output face relative to rotation: 0 is the front, -1 is every face.
            dumpLiquid(liquids.current(), 2f, directionalOutput ? 0 : -1);
        }
    }
}