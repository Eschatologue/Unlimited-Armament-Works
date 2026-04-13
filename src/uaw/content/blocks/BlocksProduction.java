package uaw.content.blocks;

import arc.graphics.Color;
import mindustry.content.Blocks;
import mindustry.content.Liquids;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.Pal;
import mindustry.world.blocks.production.AttributeCrafter;
import uaw.content.UAWItems;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import uaw.world.blocks.production.ConversionDrill;

import static mindustry.Vars.tilesize;
import static uaw.Vars.modName;
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
                    Items.copper, 150,
                    Items.lead, 60,
                    Items.graphite, 50
            ));
            size = 3;
            tier = 3;

            arrows = 1;
            baseArrowColor = Color.valueOf("989aa4");

            squareSprite = false;

            drillTime = 5 * tick;
            tileRequirement = Blocks.oreCoal;
            drilledItem = UAWItems.anthracite;
            drillEffect = new MultiEffect(
                    Fx.mineImpact,
                    Fx.mineImpactWave.wrap(Pal.bulletYellowBack),
                    Fx.drillSteam
            );
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
