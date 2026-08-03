package uaw.content.blocks;

import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.power.PowerNode;
import uaw.world.blocks.power.PowerPylon;
import uaw.world.blocks.power.PowerPylonExtended;

import static mindustry.type.ItemStack.with;

public class Power {

    public static Block pylon;

    public static void load(){
        pylon = new PowerPylonExtended("power-pylon"){{
            requirements(Category.power, with(Items.graphite, 15));
            maxNodes = 14;
            laserRange = 15;
            underBullets = true;
        }};
    }
}
