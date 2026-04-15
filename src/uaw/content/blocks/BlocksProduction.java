package uaw.content.blocks;

import arc.graphics.Color;
import mindustry.content.Blocks;
import mindustry.content.Liquids;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.Pal;
import mindustry.world.blocks.production.GenericCrafter;
import uaw.content.UAWItems;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import uaw.world.blocks.production.ConversionDrill;

import static uaw.Vars.tick;
import static mindustry.type.ItemStack.with;

public class BlocksProduction {

    public static Block
            // Resourcing
            hydroThumper, acidThumper,
    // Production
    calcinator, accretionChamber;

    public static void load() {

        hydroThumper = new ConversionDrill("hydro-thumper") {{
            requirements(Category.production, with(
                    Items.copper, 125,
                    Items.lead, 50,
                    Items.graphite, 50
            ));

            size = 3;
            tier = 4;

            arrows = 1;
            baseArrowColor = Color.valueOf("989aa4");

            squareSprite = false;

            drillTime = 3 * tick;
            tileRequirement = Blocks.oreCoal;
            drilledItem = UAWItems.anthracite;
            drillEffect = new MultiEffect(
                    Fx.mineImpact,
                    Fx.hitLiquid.wrap(Pal.water),
                    Fx.mineImpactWave,
                    Fx.drillSteam
            );
            consumeLiquid(Liquids.water, 12 / tick);
        }};

        calcinator = new GenericCrafter("calcinator") {{
            requirements(Category.crafting, with(Items.copper, 125, Items.lead, 50));

            size = 2;
            squareSprite = false;
            hasItems = true;
            craftEffect = new MultiEffect(Fx.coalSmeltsmoke, Fx.smeltsmoke);

            consumeItems(with(Items.coal, 2));
            outputItems = ItemStack.with(UAWItems.anthracite, 2, UAWItems.sulphur, 1);
            craftTime = 2 * tick;
        }};

//        accretionChamber = new GenericCrafter("accretion-chamber") {{
//            requirements(Category.crafting, with(Items.graphite, 75, Items.copper, 50));
//
//            size = 2;
//            hasItems = true;
//            craftEffect = Fx.pulverizeMedium;
//
//            craftTime = 1 * tick;
//            consumeItem(Items.graphite, 2);
//            outputItem = new ItemStack(UAWItems.anthracite, 1);
//        }};
    }
}
