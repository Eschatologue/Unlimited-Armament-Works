package uaw.content.blocks;

import arc.graphics.Color;
import mindustry.content.Blocks;
import mindustry.content.Liquids;
import mindustry.world.blocks.production.AttributeCrafter;
import uaw.content.UAWItems;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import uaw.world.blocks.production.ConversionDrill;

import static uaw.Vars.tick;
import static mindustry.type.ItemStack.with;

public class BlocksProduction {

    public static Block
            // Resourcing
            hydroThumper,
    // Production
    calcinator, accretionChamber;

    public static void load() {

        hydroThumper = new ConversionDrill("hydro-thumper") {{
            requirements(Category.production, with(
                    Items.copper, 55,
                    Items.lead, 45,
                    Items.graphite, 40,
                    Items.silicon, 30
            ));
            tier = 3;
            size = 3;
            baseArrowColor = Color.valueOf("989aa4");
            squareSprite = false;
            tileRequirement = Blocks.oreCoal;
            drilledItem = UAWItems.anthracite;
            arrows = 1;
            itemCapacity = 35;
            drillTime = 5 * tick;
            hasLiquids = true;
            liquidCapacity = 90f;
//            drillEffect = new MultiEffect(
//                    UAWFx.mineImpact(4, 15, Pal.bulletYellow),
//                    Fx.mineImpactWave.wrap(Pal.bulletYellowBack, 8 * tilesize),
//                    Fx.drillSteam,
//                    UAWFx.dynamicExplosion(4 * tilesize, Pal.bulletYellow, Pal.bulletYellowBack)
//            );
            consumeLiquid(Liquids.water, 5 / tick);
        }};
        calcinator = new AttributeCrafter("calcinator") {{
            requirements(Category.crafting, with(Items.copper, 125, Items.lead, 50));

            size = 2;
            hasItems = true;
            craftEffect = Fx.pulverizeMedium;

            craftTime = 1 * tick;
            consumeItem(Items.coal, 3);
            outputItem = new ItemStack(UAWItems.anthracite, 1);
        }};

        accretionChamber = new GenericCrafter("accretion-chamber") {{
            requirements(Category.crafting, with(Items.graphite, 75, Items.copper, 50));

            size = 2;
            hasItems = true;
            craftEffect = Fx.pulverizeMedium;

            craftTime = 1 * tick;
            consumeItem(Items.graphite, 2);
            outputItem = new ItemStack(UAWItems.anthracite, 1);
        }};
    }
}
