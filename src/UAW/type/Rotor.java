package UAW.type;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import mindustry.graphics.Layer;

public class Rotor {
	public final String name;
	public TextureRegion bladeRegion, bladeOutlineRegion, topRegion, topRegionOutline;
	public float x = 0f;
	public float y = 0f;
	/** Rotor base rotation speed */
	public float rotorSpeed = 12;
	public float layer = Layer.flyingUnitLow + 0.001f;
	public boolean drawRotorTop = true, doubleRotor = false;
	public int bladeCount = 4;

	public Rotor(String name) {
		this.name = name;
	}

	public static class RotorMount {
		public final Rotor rotor;
		public float rotorRotation;

		public RotorMount(Rotor rotor) {
			this.rotor = rotor;
		}
	}

	public void load() {
		bladeRegion = Core.atlas.find(name);
		bladeOutlineRegion = Core.atlas.find(name + "-outline");
		topRegion = Core.atlas.find(name + "-top");
		topRegionOutline = Core.atlas.find(name + "-top-outline");
	}
}
