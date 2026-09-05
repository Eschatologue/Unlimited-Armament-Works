package uaw.world.draw;

import arc.Core;
import mindustry.world.Block;
import mindustry.world.draw.DrawFlame;

public class UAWDrawFlame extends DrawFlame {
    public boolean drawTop = true;
    public String topSuffix = "-top";

    @Override
    public void load(Block block) {
        block.emitLight = true;
        if (drawTop) top = Core.atlas.find(block.name + topSuffix);
        block.lightClipSize = Math.max(block.lightClipSize, (lightRadius + lightSinMag) * 2f * block.size);
    }
}
