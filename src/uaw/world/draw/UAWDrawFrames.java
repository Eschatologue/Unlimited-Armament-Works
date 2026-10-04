package uaw.world.draw;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import mindustry.world.Block;
import mindustry.world.draw.DrawFrames;

/**
 * A {@link DrawFrames}, but reads sprite files starting from frame 1 instead of frame 0
 *
 * <p>Since renumbering Aseprite exports to start at 0 is apparently too convoluted, I just shift the lookup here instead of figuring out the export settings</p>
 */
public class UAWDrawFrames extends DrawFrames {

    public String suffix = "-frame";

    @Override
    public void load(Block block) {
        regions = new TextureRegion[frames];
        for (int i = 0; i < frames; i++) {
            regions[i] = Core.atlas.find(block.name + suffix + (i + 1));
        }
    }
}
