package uaw.content.blocks;

import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.power.PowerNode;
import uaw.world.blocks.power.PowerPylon;

import static mindustry.type.ItemStack.with;

public class Power {

    public static Block testPylon;

    public static void load(){
        testPylon = new PowerPylon("power-pylon"){{
            requirements(Category.power, with(Items.copper, 2, Items.lead, 6));
            maxNodes = 12;
            laserRange = 12;
            underBullets = true;
            crushFragile = true;
        }};
    }
}
