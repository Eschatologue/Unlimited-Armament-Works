package UAW.content.blocks;

import UAW.content.*;
import UAW.graphics.*;
import UAW.world.blocks.liquid.*;
import UAW.world.blocks.power.WarmUpGenerator;
import UAW.world.blocks.production.*;
import arc.graphics.Color;
import arc.struct.Seq;
import gas.world.blocks.gas.GasConduit;
import gas.world.blocks.sandbox.*;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.units.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static UAW.Vars.tick;
import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;

/** Contains General use blocks */
public class UAWBlocks implements ContentList {
	public static Block placeholder,
	// Liquids
	pressurizedConduit, platedPressurizedConduit, pressurizedLiquidRouter, pressurizedLiquidJunction, pressurizedLiquidBridge, liquidCistern,
	// Drills
	oilDerrick, rotodynamicPump,
	// Crafters
	gelatinizer, cryofluidDistillery, carburizingFurnace, surgeMixer, petroleumSmelter, petrochemicalSeperator, plastaniumForge,
	// Power
	petroleumGenerator,
	// Units Factory
	UAWGroundFactory, UAWNavalFactory, UAWAirFactory,
	// Units Reconstructor
	exponentialPetroleumReconstructor, tetrativePetroleumReconstructor,

	// Gasses
	gasSource, gasVoid, gasPipe, gasRouter, gasDistributor;

	@Override
	public void load() {

		pressurizedConduit = new PressurizedConduit("pressurized-conduit") {{
			requirements(Category.liquid, with(
				Items.titanium, 3,
				Items.metaglass, 2,
				Items.plastanium, 3
			));
			health = 500;
			baseExplosiveness = 8f;
		}};
		platedPressurizedConduit = new PlatedPressurizedConduit("plated-pressurized-conduit") {{
			requirements(Category.liquid, with(
				Items.titanium, 3,
				Items.metaglass, 2,
				Items.plastanium, 3,
				Items.thorium, 2
			));
			health = 750;
			baseExplosiveness = 8f;
		}};
		pressurizedLiquidRouter = new LiquidRouter("pressurized-liquid-router") {{
			requirements(Category.liquid, with(
				UAWItems.titaniumCarbide, 3,
				Items.plastanium, 2,
				Items.metaglass, 2
			));
			health = 500;
			baseExplosiveness = 8f;
			liquidCapacity = 60f;
			liquidPressure = 2f;
			placeableLiquid = true;
		}};
		pressurizedLiquidJunction = new LiquidJunction("pressurized-liquid-junction") {{
			requirements(Category.liquid, with(
				UAWItems.titaniumCarbide, 3,
				Items.plastanium, 2,
				Items.metaglass, 2
			));
			health = 500;
			liquidCapacity = 60f;
			liquidPressure = 1.5f;
			placeableLiquid = true;
			baseExplosiveness = 8f;
		}};
		pressurizedLiquidBridge = new LiquidBridge("pressurized-liquid-bridge") {{
			requirements(Category.liquid, with(
				UAWItems.titaniumCarbide, 3,
				Items.plastanium, 2,
				Items.metaglass, 2
			));
			health = 500;
			liquidCapacity = 60f;
			liquidPressure = 1.5f;
			range = 6;
			arrowPeriod = 0.9f;
			arrowTimeScl = 2.75f;
			baseExplosiveness = 8f;
		}};

		liquidCistern = new LiquidRouter("liquid-cistern") {{
			requirements(Category.liquid, with(
				Items.titanium, 90,
				Items.plastanium, 50,
				Items.metaglass, 45,
				Items.silicon, 45
			));
			size = 3;
			squareSprite = false;
			liquidCapacity = 3600f;
			health = 900;
			buildCostMultiplier = 1.5f;
		}};

		oilDerrick = new Fracker("oil-derrick") {{
			requirements(Category.production, with(
				Items.titanium, 150,
				Items.plastanium, 125,
				Items.thorium, 115,
				Items.metaglass, 85,
				Items.silicon, 85
			));
			size = 4;
			result = Liquids.oil;
			updateEffect = new MultiEffect(
				UAWFxD.smokeCloud(40, Layer.effect, Color.gray),
				Fx.oily
			);
			updateEffectChance = 0.1f;
			pumpAmount = 1.5f;
			liquidCapacity = 360f;
			attribute = Attribute.oil;
			baseEfficiency = 0.5f;
			rotateSpeed = -2.5f;

			squareSprite = false;

			consumes.liquid(Liquids.cryofluid, pumpAmount / 2.5f);
			consumes.power(3.5f);
		}};
		rotodynamicPump = new RotatingLiquidPump("rotodynamic-pump") {{
			requirements(Category.liquid, with(
				Items.lead, 150,
				Items.metaglass, 60,
				Items.silicon, 60,
				Items.plastanium, 60,
				UAWItems.titaniumCarbide, 40,
				Items.thorium, 70
			));
			size = 3;
			pumpAmount = 0.5f;
			liquidCapacity = 540f;
			rotateSpeed = -3f;

			consumes.item(UAWItems.cryogel, 4);
			consumes.power(2f);
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

		plastaniumForge = new AdvancedGenericCrafter("plastanium-forge") {{
			requirements(Category.crafting, with(
				Items.lead, 225,
				Items.silicon, 160,
				Items.graphite, 160,
				Items.titanium, 300,
				Items.plastanium, 250
			));
			size = 3;
			health = 500 * size;
			squareSprite = false;
			hasItems = true;
			itemCapacity = 48;
			liquidCapacity = 280f;
			craftTime = 5.5f * tick;
			outputItem = new ItemStack(Items.plastanium, 14);
			hasPower = hasLiquids = true;
			craftEffect = new MultiEffect(
				UAWFxD.burstSmelt(2.5f * tilesize, Pal.plastaniumFront, Pal.plastaniumBack),
				Fx.plasticExplosion
			);
			craftShake = 6f;
			updateEffect = new MultiEffect(Fx.plasticburn, Fx.burning, Fx.fireSmoke);
			drawer = new DrawSmelter();

			consumes.liquid(Liquids.oil, 2.5f);
			consumes.items(
				new ItemStack(Items.titanium, 7),
				new ItemStack(UAWItems.anthracite, 2)
			);
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
		petrochemicalSeperator = new EffectSeparator("petrochemical-seperator") {{
			requirements(Category.crafting, with(
				Items.lead, 120,
				Items.titanium, 100,
				Items.plastanium, 80,
				Items.silicon, 80
			));
			results = with(
				UAWItems.anthracite, 2,
				Items.coal, 2,
				Items.coal, 2
			);
			size = 3;
			craftTime = 2.5f * tick;
			itemCapacity = 30;
			squareSprite = false;
			liquidCapacity = 240f;
			updateEffect = new MultiEffect(Fx.fireHit, UAWFxS.plastHit, Fx.oily);

			consumes.liquid(Liquids.oil, 1.5f);
			consumes.item(Items.sand, 3);
		}};

		petroleumGenerator = new WarmUpGenerator("petroleum-generator") {{
			requirements(Category.power, with(
				Items.copper, 200,
				Items.lead, 200,
				Items.titanium, 150,
				Items.plastanium, 100,
				Items.silicon, 120,
				Items.metaglass, 120
			));
			size = 4;
			health = 300 * size;
			hasLiquids = true;
			hasItems = false;
			liquidCapacity = 1200f;
			ambientSound = Sounds.machine;
			ambientSoundVolume = 0.05f;

			powerProduction = 45f;
			consumes.liquid(Liquids.oil, 3f);
		}};

		UAWGroundFactory = new UnitFactory("uaw-ground-factory") {{
			requirements(Category.units, with(
				Items.lead, 550,
				Items.silicon, 90,
				Items.metaglass, 90,
				Items.titanium, 250,
				Items.plastanium, 100
			));
			size = 5;
			consumes.power(3.5f);
			consumes.liquid(Liquids.oil, 1f);
			liquidCapacity = 120f;
			plans = Seq.with(
				new UnitPlan(UAWUnitTypes.cavalier, 35f * tick, with(
					Items.silicon, 85,
					Items.titanium, 90,
					Items.lead, 150,
					Items.copper, 165
				))
			);
		}};
		UAWNavalFactory = new UnitFactory("uaw-naval-factory") {{
			requirements(Category.units, with(
				Items.lead, 550,
				Items.silicon, 60,
				Items.metaglass, 100,
				Items.titanium, 250,
				Items.plastanium, 100
			));
			floating = true;
			size = 5;
			consumes.power(3.5f);
			consumes.liquid(Liquids.oil, 1f);
			liquidCapacity = 120f;
			plans = Seq.with(
				new UnitPlan(UAWUnitTypes.arquebus, 45f * tick, with(
					Items.silicon, 65,
					Items.metaglass, 60,
					Items.titanium, 100,
					Items.lead, 120
				)),
				new UnitPlan(UAWUnitTypes.seabass, 55f * tick, with(
					Items.silicon, 55,
					Items.metaglass, 50,
					Items.titanium, 100,
					Items.blastCompound, 30,
					Items.lead, 120
				))
			);
		}};
		UAWAirFactory = new UnitFactory("uaw-air-factory") {{
			requirements(Category.units, with(
				Items.lead, 550,
				Items.silicon, 80,
				Items.metaglass, 80,
				Items.titanium, 250,
				Items.plastanium, 100
			));
			size = 5;
			consumes.power(3.5f);
			consumes.liquid(Liquids.oil, 1f);
			liquidCapacity = 120f;
			plans = Seq.with(
				new UnitPlan(UAWUnitTypes.aglovale, 35f * tick, with(
					Items.silicon, 100,
					Items.titanium, 125,
					Items.plastanium, 75,
					Items.lead, 150
				)),
				new UnitPlan(UAWUnitTypes.corsair, 30f * tick, with(
					Items.silicon, 85,
					Items.titanium, 90,
					Items.lead, 150,
					Items.plastanium, 75,
					Items.blastCompound, 35
				))
			);
		}};

		exponentialPetroleumReconstructor = new Reconstructor("exponential-petroleum-reconstructor") {{
			requirements(Category.units, with(
				Items.lead, 1000,
				Items.titanium, 2000,
				Items.thorium, 550,
				Items.plastanium, 500,
				UAWItems.titaniumCarbide, 550
			));

			size = 7;
			consumes.power(7f);
			consumes.items(with(
				Items.silicon, 425,
				Items.metaglass, 325,
				UAWItems.titaniumCarbide, 250,
				Items.plastanium, 225
			));
			consumes.liquid(Liquids.oil, 1.5f);

			constructTime = 80 * tick;
			liquidCapacity = 240f;
			placeableLiquid = true;

			upgrades.addAll(
				new UnitType[]{UAWUnitTypes.aglovale, UAWUnitTypes.bedivere},
				new UnitType[]{UAWUnitTypes.arquebus, UAWUnitTypes.carronade},
				new UnitType[]{UAWUnitTypes.cavalier, UAWUnitTypes.centurion},
				new UnitType[]{UAWUnitTypes.seabass, UAWUnitTypes.sharpnose}
//				new UnitType[]{UAWUnitTypes.corsair, UAWUnitTypes.vindicator}
			);
		}};
		tetrativePetroleumReconstructor = new Reconstructor("tetrative-petroleum-reconstructor") {{
			requirements(Category.units, with(
				Items.lead, 3500,
				Items.titanium, 2500,
				Items.silicon, 1250,
				Items.metaglass, 500,
				Items.plastanium, 600,
				UAWItems.titaniumCarbide, 600,
				Items.surgeAlloy, 700
			));

			size = 9;
			consumes.power(20f);
			consumes.items(with(
				Items.silicon, 550,
				Items.metaglass, 450,
				Items.plastanium, 300,
				Items.surgeAlloy, 350,
				UAWItems.titaniumCarbide, 350
			));
			consumes.liquid(Liquids.oil, 3.5f);

			constructTime = 225 * tick;
			liquidCapacity = 480f;
			placeableLiquid = true;

			upgrades.addAll(
				new UnitType[]{UAWUnitTypes.bedivere, UAWUnitTypes.calogrenant},
				new UnitType[]{UAWUnitTypes.carronade, UAWUnitTypes.falconet},
				new UnitType[]{UAWUnitTypes.centurion, UAWUnitTypes.caernarvon}
//			new UnitType[]{UAWUnitTypes.sharpnose, UAWUnitTypes.sturgeon}
//			new UnitType[]{UAWUnitTypes.vindicator, UAWUnitTypes.superfortress}
			);
		}};

		gasSource = new GasSource("gas-source") {{
			requirements(Category.liquid, BuildVisibility.sandboxOnly, with());
			alwaysUnlocked = true;
		}};
		gasVoid = new GasVoid("gas-void") {{
			requirements(Category.liquid, BuildVisibility.sandboxOnly, with());
			alwaysUnlocked = true;
		}};

		gasPipe = new GasConduit("gas-pipe") {{
			size = 1;
		}};
	}
}
