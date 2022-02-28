package UAW.content.blocks;

import UAW.content.*;
import UAW.graphics.*;
import UAW.world.blocks.gas.*;
import UAW.world.blocks.production.*;
import arc.graphics.Color;
import gas.GasStack;
import gas.world.blocks.production.*;
import gas.world.consumers.ConsumeGas;
import gas.world.draw.GasDrawAnimation;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;

import static UAW.Vars.tick;
import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;

/** Contains Production structures, such as factories, drills, pumps, etc */
public class UAWBlocksProduction implements ContentList {
	public static Block placeholder,
	// Drills
	oilDerrick, steamPump, pulsometerPump, steamDrill, advancedSteamDrill, steamThumper,
	// General Crafter
	gelatinizer, carburizingFurnace,
	// Steam Crafter
	steamGraphitePress, plastaniumSteamPress,
	// Petroleum Crafter
	petroleumSmelter, petrochemicalCentrifuge,
	// Liquid Mixer
	cryofluidDistillery, surgeMixer,
	// Gas
	steamKettle, coalBoiler, pressureBoiler, geothermalKettle, solarBoiler;

	@Override
	public void load() {
		oilDerrick = new GasFracker("oil-derrick") {{
			requirements(Category.production, with(
				Items.copper, 200,
				Items.graphite, 150,
				Items.lead, 100,
				Items.silicon, 75,
				Items.metaglass, 75
			));
			size = 4;
			result = Liquids.oil;
			updateEffect = new MultiEffect(
				UAWFxD.steamCloud(8),
				Fx.oily
			);
			updateEffectChance = 0.05f;
			pumpAmount = 0.5f;
			gasCapacity = liquidCapacity = 360f;
			attribute = Attribute.oil;
			baseEfficiency = 0.5f;
			rotateSpeed = -2.5f;

			squareSprite = false;

			consumes.liquid(Liquids.cryofluid, pumpAmount / 2.5f).boost();
			consumes.addGas(new ConsumeGas(UAWGas.steam, 3.5f));
		}};
		petrochemicalCentrifuge = new EffectGasSeperator("petrochemical-centrifuge") {{
			requirements(Category.crafting, with(
				Items.plastanium, 30,
				Items.titanium, 90,
				Items.silicon, 50,
				Items.coal, 180
			));
			results = with(
				UAWItems.anthracite, 2,
				Items.coal, 2
			);
			size = 3;
			craftTime = 0.5f * tick;
			itemCapacity = 30;
			squareSprite = false;
			liquidCapacity = 240f;
			updateEffect = new MultiEffect(Fx.fireHit, UAWFxS.plastHit, Fx.oily);

			consumes.liquid(Liquids.oil, 0.2f);
			consumes.addGas(new ConsumeGas(UAWGas.steam, 0.25f));
		}};

		steamDrill = new GasDrill("steam-drill") {{
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
			updateEffect = UAWFxD.steamCloud(7.5f);
			drillEffect = Fx.mineBig;

			consumes.addGas(new ConsumeGas(UAWGas.steam, 0.25f));
			consumes.liquid(Liquids.oil, 0.025f).boost();
		}};
		advancedSteamDrill = new GasDrill("advanced-steam-drill") {{
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

			consumes.addGas(new ConsumeGas(UAWGas.steam, 2.25f));
			consumes.liquid(Liquids.oil, 0.05f).boost();
		}};

		steamPump = new UAWGasPump("steam-pump") {{
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
			updateEffect = UAWFxD.steamCloud(4);

			consumes.addGas(new ConsumeGas(UAWGas.steam, 0.25f));
		}};
		pulsometerPump = new UAWGasPump("pulsometer-pump") {{
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
			consumes.addGas(new ConsumeGas(UAWGas.steam, 1f));
		}};

		gelatinizer = new GenericCrafter("gelatinizer") {{
			requirements(Category.crafting, with(
				Items.lead, 45,
				Items.graphite, 30,
				Items.thorium, 20
			));
			consumes.items(
				new ItemStack(
					Items.sand, 2
				));
			consumes.liquid(Liquids.cryofluid, 0.25f);
			consumes.power(0.5f);
			outputItem = new ItemStack(
				UAWItems.cryogel, 1
			);
			craftTime = 45f;
			hasItems = true;
			hasLiquids = true;
			size = 2;
			drawer = new DrawLiquid();
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
			drawer = new DrawSmelter(UAWPal.cryoFront);
			craftTime = 2f * tick;
			updateEffect = new MultiEffect(Fx.wet, UAWFxD.smokeCloud(45, Layer.effect, Color.white));
			updateEffectChance = 0.01f;
			consumes.items(with(
				Items.titanium, 4,
				Items.thorium, 1
			));
			consumes.liquid(Liquids.water, 1.2f);
			outputLiquid = new LiquidStack(Liquids.cryofluid, 60f);
			consumes.power(3f);
		}};

		carburizingFurnace = new AdvancedGenericCrafter("carburizing-furnace") {{
			requirements(Category.crafting, with(
				Items.titanium, 150,
				Items.thorium, 125,
				Items.metaglass, 95,
				Items.silicon, 95,
				Items.graphite, 95
			));
			consumes.items(
				new ItemStack(Items.titanium, 4),
				new ItemStack(Items.coal, 8)
			);
			consumes.liquid(Liquids.slag, 1.5f);
			outputItem = new ItemStack(
				UAWItems.titaniumCarbide, 1
			);
			consumes.power(5.5f);
			hasItems = true;
			hasLiquids = true;
			size = 3;
			itemCapacity = 30;
			craftTime = 3.5f * tick;
			drawer = new DrawSmelter();
			craftEffect = new MultiEffect(
				UAWFxD.burstSmelt(3.5f * tilesize, Pal.missileYellow, Pal.missileYellowBack),
				Fx.flakExplosionBig
			);
			updateEffect = new MultiEffect(Fx.melting, Fx.burning, Fx.fireSmoke);
			craftShake = 8;
		}};
		surgeMixer = new GenericCrafter("surge-mixer") {{
			requirements(Category.crafting, with(
				Items.thorium, 30,
				Items.lead, 45,
				Items.silicon, 35,
				Items.metaglass, 20
			));
			size = 3;
			liquidCapacity = 120f;
			outputsLiquid = true;
			hasItems = true;
			hasLiquids = true;
			drawer = new DrawLiquid(true);
			craftTime = 2f * tick;
			updateEffect = new MultiEffect(Fx.shieldBreak, Fx.hitBulletSmall);
			consumes.items(with(
				Items.copper, 1,
				Items.titanium, 1,
				Items.silicon, 1
			));
			consumes.liquid(Liquids.oil, 0.25f);

			outputLiquid = new LiquidStack(UAWLiquids.surgeSolvent, 30f);
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
			drawer = new DrawSmelter();
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
			consumes.items(
				new ItemStack(Items.sand, 12),
				new ItemStack(Items.lead, 4),
				new ItemStack(UAWItems.anthracite, 3)
			);
			consumes.liquid(Liquids.oil, 3);
			outputItems = with(
				Items.silicon, 13,
				Items.metaglass, 13
			);
		}};

		steamGraphitePress = new GasCrafter("steam-graphite-press") {{
			requirements(Category.crafting, with(
				Items.titanium, 90,
				Items.silicon, 25,
				Items.lead, 80,
				Items.copper, 100,
				Items.graphite, 50
			));
			craftEffect = Fx.pulverizeMedium;
			outputItem = new ItemStack(Items.graphite, 12);
			craftTime = 90f;
			itemCapacity = 40;
			size = 3;
			hasItems = true;
			hasGasses = true;
			updateEffect = UAWFxD.steamCloud(9, Layer.flyingUnitLow);
			updateEffectChance = 0.02f;

			consumes.addGas(new ConsumeGas(UAWGas.steam, 3f));
			consumes.item(Items.coal, 5);
		}};
		plastaniumSteamPress = new GasCrafter("plastanium-steam-press") {{
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
			liquidCapacity = 60f;
			craftTime = 90f;
			itemCapacity = 40;
			hasLiquids = true;
			hasGasses = true;
			craftEffect = Fx.formsmoke;
			updateEffect = UAWFxD.steamCloud(9.5f, Layer.flyingUnitLow);
			updateEffectChance = 0.01f;
			drawer = new GasDrawAnimation() {{
				frameCount = 17;
				frameSpeed = (craftTime / frameCount);
			}};
			outputItem = new ItemStack(Items.plastanium, 6);

			consumes.addGas(new ConsumeGas(UAWGas.steam, 3f));
			consumes.liquid(Liquids.oil, 0.75f);
			consumes.item(Items.titanium, 10);
		}};

		// Gasses
		steamKettle = new LiquidBoiler("steam-kettle") {{
			requirements(Category.power, with(
				Items.copper, 12,
				Items.lead, 12
			));
			size = 1;
			squareSprite = false;
			warmupSpeed = 0.01f;
			liquidAmount = 15f;
			gasEffect = UAWFxD.steamCloud(1.5f);
			gasEffectRnd = 0;
			gasEffectWarmupMult = 0.10f;
			consumes.items(new ItemStack(
				Items.coal, 1
			));
			consumes.liquid(liquidInput, liquidAmount / craftTime);
			outputGas = new GasStack(gasResult, liquidAmount * conversionMultiplier);
		}};
		coalBoiler = new LiquidBoiler("coal-boiler") {{
			requirements(Category.power, with(
				Items.copper, 35,
				Items.graphite, 25,
				Items.lead, 30
			));
			size = 2;
			squareSprite = false;
			warmupSpeed = 0.002f;
			gasEffect = UAWFxD.steamCloud(3.5f);
			gasEffectWarmupMult = 0.2f;
			gasEffectRnd = 0.2f;
			liquidAmount = 36;
			consumes.items(new ItemStack(
				Items.coal, 4
			));
			consumes.liquid(liquidInput, liquidAmount / craftTime);
			outputGas = new GasStack(gasResult, liquidAmount * conversionMultiplier);
		}};
		pressureBoiler = new LiquidBoiler("pressure-boiler") {{
			requirements(Category.power, with(
				Items.copper, 150,
				Items.lead, 125,
				Items.titanium, 55,
				Items.graphite, 50
			));
			size = 4;
			squareSprite = false;
			itemCapacity = 30;
			warmupSpeed = 0.0015f;
			gasEffect = UAWFxD.steamCloud(8f);
			gasEffectWarmupMult = 0.15f;
			gasEffectRnd = 0.05f;
			liquidAmount = 180;
			consumes.items(with(
				Items.coal, 4,
				Items.pyratite, 2
			));
			consumes.liquid(liquidInput, liquidAmount / craftTime);
			outputGas = new GasStack(gasResult, liquidAmount * conversionMultiplier);
		}};

	}
}
