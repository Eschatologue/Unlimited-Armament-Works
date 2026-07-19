package mindustry.ui;

import arc.graphics.g2d.*;
import arc.scene.ui.Image;

/** Shim for MultiCrafterLib compatibility - ItemImage was removed in v159. */
public class ItemImage extends Image {

    public ItemImage(TextureRegion region, int amount) {
        super(region);
    }
}
