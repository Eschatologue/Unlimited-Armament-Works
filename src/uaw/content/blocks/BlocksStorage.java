package uaw.content.blocks;

import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import uaw.content.UAWItems;
import uaw.world.blocks.storage.DepotPort;
import uaw.world.blocks.storage.DepotSection;

import static mindustry.type.ItemStack.with;
import static uaw.Vars.tick;

public class BlocksStorage {

    public static Block placeholder,

    // Depot
    depotPort, depotSection;

    public static void load() {
        depotPort = new DepotPort("depot-port"){{
            requirements(Category.distribution, with(Items.graphite, 500, Items.silicon, 500, UAWItems.tisic, 250));
            itemCapacity = 5000;
            health = 2500;
            buildTime = 15 * tick;
        }};

        depotSection = new DepotSection("depot-section"){{
            requirements(Category.distribution, with(Items.graphite, 150, Items.silicon, 50, UAWItems.tisic, 25));
            health = Blocks.vault.health;
            buildTime = 2.5f * tick;
        }};

    }
}
