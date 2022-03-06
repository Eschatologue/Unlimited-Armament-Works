package UAW.content.blocks;

import UAW.content.*;
import UAW.graphics.*;
import UAW.world.blocks.gas.GasCrafter;
import UAW.world.blocks.production.*;
import UAW.world.drawer.GasDrawEverything;
import arc.graphics.Color;
import gas.world.blocks.production.*;
import gas.world.consumers.ConsumeGas;
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
	steamPress, plastaniumSteamPress,
	// Petroleum Crafter
	petroleumSmelter,
	// Liquid Mixer
	cryofluidDistillery, surgeMixer;

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
				UAWFxD.steamCloud(5),
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
		steamThumper = new ThumperDrill("steam-thumper") {{
			requirements(Category.production, with(
				Items.copper, 55,
				Items.graphite, 40,
				Items.silicon, 30
			));
			size = 3;
			squareSprite = false;
			oreOverlay = Blocks.oreCoal;
			drilledItem = UAWItems.anthracite;
			tier = 3;
			itemCapacity = 25;
			drillTime = 600;
			warmupSpeed = 0.001f;
			hasLiquids = true;
			drawRim = true;
			gasCapacity = 90f;
			drillEffect = new MultiEffect(
				Fx.mineHuge,
				Fx.oily
			);
			updateEffect = UAWFxD.steamCloud(4);

			consumes.addGas(new ConsumeGas(UAWGas.steam, 0.5f));
			consumes.liquid(Liquids.oil, 0.025f).boost();
		}};

		steamDrill = new UAWGasDrill("steam-drill") {{
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

			consumes.addGas(new ConsumeGas(UAWGas.steam, 0.25f));
			consumes.liquid(Liquids.oil, 0.025f).boost();
		}};
		advancedSteamDrill = new UAWGasDrill("advanced-steam-drill") {{
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

		carburizingFurnace = new GasCrafter("carburizing-furnace") {{
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
			gasCapacity = 180f;
			craftTime = 3.5f * tick;
			drawer = new GasDrawEverything() {{
				drawArcSmelter = true;
				arcParticles = 32;
				arcFlameRad = 1.2f;
			}};
			craftEffect = UAWFxD.steamCloud(12f);
			updateEffect = new MultiEffect(Fx.melting, Fx.burning, Fx.fireSmoke);
			consumes.items(
				new ItemStack(Items.titanium, 4),
				new ItemStack(UAWItems.anthracite, 4)
			);
			consumes.addGas(new ConsumeGas(UAWGas.steam, 4.5f));
			outputItem = new ItemStack(
				UAWItems.titaniumCarbide, 1
			);
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

		steamPress = new GasCrafter("steam-press") {{
			requirements(Category.crafting, with(
				Items.titanium, 90,
				Items.silicon, 25,
				Items.lead, 80,
				Items.copper, 100,
				Items.graphite, 50
			));
			size = 3;
			hasItems = true;
			hasGasses = true;
			craftEffect = Fx.pulverizeMedium;
			outputItem = new ItemStack(Items.graphite, 12);
			craftTime = 90f;
			itemCapacity = 40;
			drawer = new GasDrawEverything(){{
				drawSteam = true;
			}};

			consumes.addGas(new ConsumeGas(UAWGas.steam, 2f));
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
			liquidCapacity = 180f;
			craftTime = 90f;
			itemCapacity = 40;
			hasLiquids = true;
			hasGasses = true;
			craftEffect = Fx.formsmoke;
			updateEffect = UAWFxD.steamCloud(7.5f, Layer.flyingUnitLow);
			updateEffectChance = 0.01f;
			drawer = new GasDrawEverything() {{
				drawSmokeCells = true;
				smokeRecurrance = 10f;
			}};
			outputItem = new ItemStack(Items.plastanium, 6);

			consumes.addGas(new ConsumeGas(UAWGas.steam, 2.25f));
			consumes.liquid(Liquids.oil, 0.75f);
			consumes.item(Items.titanium, 10);
		}};

	}
}
