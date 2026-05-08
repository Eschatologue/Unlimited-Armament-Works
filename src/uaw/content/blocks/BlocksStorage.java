package uaw.content.blocks;

import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import uaw.world.blocks.storage.DepoBlock;

import static mindustry.type.ItemStack.with;

public class BlocksStorage {

    public static Block placeholder,

    // Depo
    depoBus;

    public static void load() {
        depoBus = new DepoBlock("depo-bus") {{
            requirements(Category.effect, BuildVisibility.sandboxOnly, with());
            size = 2;
            itemCapacity = 500;
            scaledHealth = 50;
        }};

    }
}
