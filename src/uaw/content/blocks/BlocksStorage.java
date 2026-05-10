package uaw.content.blocks;

import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import uaw.world.blocks.storage.DepotPort;
import uaw.world.blocks.storage.DepotSection;

import static mindustry.type.ItemStack.with;

public class BlocksStorage {

    public static Block placeholder,

    // Depo
    depotPort, depotSection;

    public static void load() {
        depotPort = new DepotPort("depot-port"){{
            requirements(Category.effect, BuildVisibility.sandboxOnly, with());
            itemCapacity = 6000;
            scaledHealth = 50;
        }};

        depotSection = new DepotSection("depot-section"){{
            requirements(Category.effect, BuildVisibility.sandboxOnly, with());
        }};

    }
}
