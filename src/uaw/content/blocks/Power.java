package uaw.content.blocks;

import mindustry.content.Items;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlurSpin;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;
import uaw.audiovisual.UAWPal;
import uaw.audiovisual.fx.ProductionFx;
import uaw.content.UAWLiquids;
import uaw.utils.Calc;
import uaw.world.blocks.power.PowerPylonExtended;
import uaw.world.blocks.power.SteamTurbine;

import static mindustry.type.ItemStack.with;

public class Power {

    // Power Transfer
    public static Block pylon;

    // Power Generation
    public static Block steamTurbineMk1;

    public static void load() {
        pylon = new PowerPylonExtended("power-pylon") {{
            requirements(Category.power, with(Items.graphite, 15));
            maxNodes = 14;
            laserRange = 15;
            underBullets = true;
        }};

        steamTurbineMk1 = new SteamTurbine("steam-turbine-mk1") {{
            requirements(Category.power, with(Items.graphite, 50, Items.silicon, 50));
            squareSprite = false;
            powerProduction = 3;

            generateEffect = ProductionFx.chimneySmoke(UAWPal.steam, 0.25f);
            effectChance = 0.5f;

            ambientSound = Sounds.loopSmelter;
            ambientSoundVolume = 0.06f;
            conductivePower = true;

            consumeLiquid(UAWLiquids.steam, Calc.liqPerSec(5));

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawBlurSpin("-rotator", 8f),
                    new DrawDefault(),
                    new DrawRegion("-outlet") {{
                        buildingRotate = true;
                    }}
            );
        }};
    }
}
