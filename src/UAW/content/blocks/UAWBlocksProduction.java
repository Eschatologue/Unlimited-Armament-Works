package UAW.content.blocks;

import UAW.content.*;
import UAW.graphics.*;
import UAW.world.blocks.production.*;
import UAW.world.drawer.DrawBoilerSmoke;
import arc.graphics.Color;
import mindustry.content.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.production.*;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;

import static UAW.Vars.tick;
import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;

/** Contains Production structures, such as factories, drills, pumps, etc */
public class UAWBlocksProduction {
	public static Block placeholder,
	// Drills
	oilDerrick, steamPump, pulsometerPump, steamDrill, advancedSteamDrill, steamThumper,
	// General Crafter
	gelatinizer, carburizingFurnace,
	// Steam Crafter
	steamPress, plastaniumSteamPress,
	// Petroleum Crafter
	petroleumSmelter,
	// Liquid Mixer
	cryofluidDistillery, surgeMixer;

	public static void load() {
		oilDerrick = new Fracker("oil-derrick") {{
			requirements(Category.production, with(
				Items.copper, 200,
				Items.graphite, 150,
				Items.lead, 100,
				Items.silicon, 75,
				Items.metaglass, 75
			));
			size = 4;
			result = Liquids.oil;
			updateEffect = UAWFxD.steamCloud(5, Layer.flyingUnitLow - 1);
			updateEffectChance = 0.05f;
			pumpAmount = 0.5f;
			liquidCapacity = 360f;
			attribute = Attribute.oil;
			baseEfficiency = 0.5f;
			rotateSpeed = -2.5f;

			squareSprite = false;

			consumeLiquid(UAWLiquids.steam, pumpAmount / 2.5f);
		}};
		steamThumper = new ThumperDrill("steam-thumper") {{
			requirements(Category.production, with(
				Items.copper, 55,
				Items.lead, 45,
				Items.graphite, 40,
				Items.silicon, 30
			));
			size = 3;
			squareSprite = false;
			tileRequirement = Blocks.oreCoal;
			drilledItem = UAWItems.anthracite;
			tier = 3;
			itemCapacity = 25;
			drillTime = 550;
			warmupSpeed = 0.001f;
			hasLiquids = true;
			drawRim = true;
			liquidCapacity = 90f;
			drillEffect = new MultiEffect(
				Fx.mineBig,
				Fx.oily
			);
			updateEffect = UAWFxD.steamCloud(4, Layer.flyingUnitLow);

			consumeLiquid(UAWLiquids.steam, 0.5f);
		}};

		steamDrill = new UAWDrill("steam-drill") {{
			requirements(Category.production, with(
				Items.copper, 24,
				Items.graphite, 12
			));
			size = 2;
			squareSprite = false;
			tier = 3;
			itemCapacity = 25;
			drillTime = 300;
			warmupSpeed = 0.001f;
			hasLiquids = false;
			drawRim = true;
			updateEffectChance = 0.03f;
			updateEffect = UAWFxD.steamCloud(3.5f);
			drillEffect = Fx.mineBig;

			consumeLiquid(UAWLiquids.steam, 0.25f);
		}};
		advancedSteamDrill = new UAWDrill("advanced-steam-drill") {{
			requirements(Category.production, with(
				Items.copper, 85,
				Items.silicon, 50,
				Items.titanium, 40,
				Items.graphite, 75
			));
			size = 3;
			squareSprite = false;
			tier = 4;
			itemCapacity = 45;
			drillTime = 180;
			warmupSpeed = 0.0005f;
			hasLiquids = true;
			drawRim = true;
			updateEffectChance = 0.03f;
			updateEffect = UAWFxD.steamCloud(5f);
			drillEffect = Fx.mineBig;
			rotateSpeed = 6f;

			consumeLiquid(UAWLiquids.steam, 1.8f);
		}};

		carburizingFurnace = new AdvancedGenericCrafter("carburizing-furnace") {{
			requirements(Category.crafting, with(
				Items.titanium, 150,
				Items.thorium, 125,
				Items.metaglass, 95,
				Items.silicon, 95,
				Items.graphite, 95
			));
			hasItems = true;
			hasLiquids = false;
			size = 3;
			itemCapacity = 30;
			craftTime = 4f * tick;
			drawer = new DrawArcSmelt() {{
				particles = 32;
				flameRad = 1.2f;
			}};
			craftEffect = UAWFxD.steamCloud(10f, Layer.flyingUnitLow, new Color(UAWPal.steamFront).lerp(Color.gray, 0.15f));
			updateEffect = new MultiEffect(Fx.melting, Fx.burning, Fx.fireSmoke);
			consumePower(3.5f);
			consumeItems(
				new ItemStack(Items.titanium, 6),
				new ItemStack(UAWItems.anthracite, 4)
			);
			outputItem = new ItemStack(
				UAWItems.titaniumCarbide, 2
			);
		}};

		steamPump = new UAWPump("steam-pump") {{
			requirements(Category.liquid, with(
				Items.copper, 80,
				Items.metaglass, 40,
				Items.silicon, 15,
				Items.titanium, 25
			));
			size = 2;
			pumpAmount = 0.2f;
			liquidCapacity = 60f;
			updateEffectChance = 0.02f;
			updateEffect = UAWFxD.steamCloud(4, Layer.flyingUnitLow);

			consumeLiquid(UAWLiquids.steam, 0.25f);
		}};
		pulsometerPump = new UAWPump("pulsometer-pump") {{
			requirements(Category.liquid, with(
				Items.copper, 80,
				Items.metaglass, 90,
				Items.silicon, 30,
				Items.titanium, 40,
				Items.thorium, 35
			));
			size = 3;
			pumpAmount = 0.25f;
			liquidCapacity = 80f;
			updateEffectChance = 0.02f;
			updateEffect = UAWFxD.steamCloud(6f, Layer.flyingUnitLow);

			consumeLiquid(UAWLiquids.steam, 1f);
		}};

		gelatinizer = new GenericCrafter("gelatinizer") {{
			requirements(Category.crafting, with(
				Items.lead, 45,
				Items.graphite, 30,
				Items.thorium, 20
			));
			consumeItems(
				new ItemStack(
					Items.sand, 2
				));
			consumeLiquid(Liquids.cryofluid, 0.25f);
			consumePower(0.5f);
			outputItem = new ItemStack(
				UAWItems.cryogel, 1
			);
			craftTime = 45f;
			hasItems = true;
			hasLiquids = true;
			size = 2;
			drawer = new DrawLiquidRegion();
			craftEffect = Fx.freezing;
			updateEffect = Fx.wet;
		}};
		cryofluidDistillery = new AdvancedGenericCrafter("cryofluid-distillery") {{
			requirements(Category.crafting, with(
				Items.lead, 150,
				Items.silicon, 90,
				Items.titanium, 120
			));
			size = 3;
			squareSprite = false;
			liquidCapacity = 240f;
			outputsLiquid = true;
			hasItems = true;
			hasLiquids = true;
			drawer = new DrawFlame(UAWPal.cryoFront);
			craftTime = 2f * tick;
			updateEffect = new MultiEffect(Fx.wet, UAWFxD.smokeCloud(45, Layer.effect, Color.white));
			updateEffectChance = 0.01f;
			consumeItems(with(
				Items.titanium, 4,
				Items.thorium, 1
			));
			consumeLiquid(Liquids.water, 1.2f);
			outputLiquid = new LiquidStack(Liquids.cryofluid, 60f);
			consumePower(3f);
		}};

		petroleumSmelter = new AdvancedGenericCrafter("petroleum-smelter") {{
			requirements(Category.crafting, with(
				Items.titanium, 125,
				Items.plastanium, 100,
				Items.thorium, 110,
				Items.metaglass, 85,
				Items.silicon, 85,
				Items.graphite, 95
			));
			size = 4;
			hasItems = true;
			hasLiquids = true;
			itemCapacity = 60;
			liquidCapacity = 360f;
			craftTime = 2.5f * tick;
			squareSprite = false;
			drawer = new DrawFlame();
			craftEffect = new MultiEffect(
				UAWFxD.burstSmelt(4 * tilesize, Pal.plastaniumFront, Pal.plastaniumBack),
				Fx.flakExplosionBig
			);
			updateEffect = new MultiEffect(
				UAWFxS.plastHit,
				Fx.burning,
				Fx.fireSmoke
			);
			craftSoundVolume = 1.2f;
			craftShake = 15f;
			consumeItems(
				new ItemStack(Items.sand, 12),
				new ItemStack(Items.lead, 4),
				new ItemStack(UAWItems.anthracite, 3)
			);
			consumeLiquid(Liquids.oil, 3);
			outputItems = with(
				Items.silicon, 13,
				Items.metaglass, 13
			);
		}};

		steamPress = new AdvancedGenericCrafter("steam-press") {{
			requirements(Category.crafting, with(
				Items.titanium, 90,
				Items.silicon, 25,
				Items.lead, 80,
				Items.copper, 100,
				Items.graphite, 50
			));
			size = 3;
			hasItems = true;
			squareSprite = false;
			craftEffect = Fx.pulverizeMedium;
			outputItem = new ItemStack(Items.graphite, 12);
			craftTime = 90f;
			itemCapacity = 40;
			drawer = new DrawBoilerSmoke();

			consumeLiquid(UAWLiquids.steam, 1.8f);
			consumeItem(Items.coal, 5);
		}};
		plastaniumSteamPress = new GenericCrafter("plastanium-steam-press") {{
			requirements(Category.crafting, with(
				Items.silicon, 140,
				Items.coal, 120,
				Items.lead, 220,
				Items.graphite, 120,
				Items.titanium, 160
			));
			size = 3;
			squareSprite = false;
			hasItems = true;
			liquidCapacity = 180f;
			craftTime = 90f;
			itemCapacity = 40;
			hasLiquids = true;
			craftEffect = Fx.formsmoke;
			updateEffect = UAWFxD.steamCloud(7.5f, Layer.flyingUnitLow);
			updateEffectChance = 0.01f;
			outputItem = new ItemStack(Items.plastanium, 6);

			consumeLiquids(
				new LiquidStack(Liquids.oil, 0.75f),
				new LiquidStack(UAWLiquids.steam, 1)
			);
			consumeItem(Items.titanium, 10);
		}};

	}
}
