package UAW.type;

import arc.Core;
import arc.graphics.g2d.TextureRegion;

public class Rotor {
	public final String name;
	public TextureRegion bladeRegion, bladeBlurRegion, bladeOutlineRegion, topRegion, topRegionOutline;

	/** Rotor offsets from the unit */
	public float x = 0f, y = 0f;
	/** Rotor base rotation speed */
	public float rotorSpeed = 12;
	/** Minimum Rotation Speed for rotor, the rotor speed wont go below this value, even when dying */
	public float minimumRotorSpeed = 0f;
	/** On what layer is the Rotor drawn at */
	public float layer = 0.5f;
	/** How fast does the blur region rotates, multiplied by default rotatespeed */
	public float rotorBlurSpeedMultiplier = 0.25f;
	/** Multiplier for rotor blurs alpha */
	public float rotorBlurAlphaMultiplier = 0.9f;
	/** Whenever to draw the rotor top sprite */
	public boolean drawRotorTop = true;
	/** Duplicates the initial rotor and spins it on the opposite dirrection */
	public boolean doubleRotor = false;
	/** How many blades generated on the unit */
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
		bladeBlurRegion = Core.atlas.find(name + "-blur");
		bladeOutlineRegion = Core.atlas.find(name + "-outline");
		topRegion = Core.atlas.find(name + "-top");
		topRegionOutline = Core.atlas.find(name + "-top-outline");
	}
}
