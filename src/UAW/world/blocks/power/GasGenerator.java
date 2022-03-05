//package UAW.world.blocks.power;
//
//import arc.Core;
//import arc.graphics.g2d.*;
//import gas.world.blocks.power.GasPowerGenerator;
//import mindustry.graphics.Drawf;
//
//public class GasGenerator extends GasPowerGenerator {
//	public TextureRegion rotatorRegion, topRegion;
//	public float rotatorSpeed = 15;
//
//	public GasGenerator(String name) {
//		super(name);
//		hasItems = false;
//		hasLiquids = false;
//		hasGasses = true;
//		gasCapacity = 180f;
//		outputsGas = false;
//	}
//
//	@Override
//	public void load() {
//		super.load();
//		rotatorRegion = Core.atlas.find(name + "-rotator");
//		topRegion = Core.atlas.find(name + "-top");
//	}
//
//	public class GasGeneratorBuild extends GasPowerGenerator.GasGeneratorBuild {
//
//		public void draw() {
//			if (rotatorRegion.found()) {
//				Drawf.spinSprite(rotatorRegion, x, y, rotatorSpeed);
//			}
//			if (topRegion.found()) {
//				Draw.rect(rotatorRegion, x, y);
//			}
//		}
//	}
//}
