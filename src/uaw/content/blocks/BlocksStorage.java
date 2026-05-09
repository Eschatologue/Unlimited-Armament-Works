package uaw.content.blocks;

import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import uaw.world.blocks.storage.DepoNode;
import uaw.world.blocks.storage.DepoSection;

import static mindustry.type.ItemStack.with;

public class BlocksStorage {

    public static Block placeholder,

    // Depo
    depoNode, depoSection;

    public static void load() {
        depoNode = new DepoNode("depo-node"){{
            requirements(Category.effect, BuildVisibility.sandboxOnly, with());
            size = 4;
            itemCapacity = 5000;
            scaledHealth = 50;
        }};

        depoSection = new DepoSection("depo-section"){{
            requirements(Category.effect, BuildVisibility.sandboxOnly, with());
            size = 2;
        }};

    }
}
