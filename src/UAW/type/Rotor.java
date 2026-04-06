package UAW.type;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.util.Nullable;
import mindustry.io.JsonIO;

import static UAW.Vars.modName;

public class Rotor {
	public final String name;
	public TextureRegion bladeRegion, bladeBlurRegion, bladeOutlineRegion, topRegion, topRegionOutline;

	public String topSuffix = "-top";

	@Nullable
	public String topBladeName;

	/** Rotor offsets from the unit */
	public float x = 0f, y = 0f;
	/** Rotor Size Scaling */
	public float rotorSizeScl = 1, rotorTopSizeScl = 1;
	/** Rotor base rotation speed */
	public float rotorSpeed = 12;
	/** Minimum Rotation Speed for rotor, the rotor speed wont go below this value, even when dying */
	public float minimumRotorSpeed = 0f;
	/** On what rotorLayer is the Rotor drawn at */
	public float rotorLayer = 0.5f;
	/** How fast does the blur region rotates, multiplied by default rotatespeed */
	public float rotorBlurSpeedMultiplier = 0.25f;
	/** Multiplier for rotor blurs alpha */
	public float rotorBlurAlphaMultiplier = 0.9f;
	/** Whenever to draw the rotor top sprite */
	public boolean drawRotorTop = true;
	/** Duplicates the initial rotor and spins it on the opposite dirrection */
	public boolean doubleRotor = false;
	/** Mirrors the rotor */
	public boolean mirror = false;
	/** How many blades generated on the unit */
	public int bladeCount = 4;

	public Rotor(String name) {
		this.name = name;
	}

	public static class RotorMount {
		public final Rotor rotor;
		public float rotorRotation;
		public float rotorBlurRotation;

		public RotorMount(Rotor rotor) {
			this.rotor = rotor;
		}
	}

	public void load() {
		bladeRegion = Core.atlas.find(name);
		bladeBlurRegion = Core.atlas.find(name + "-blur");
		bladeOutlineRegion = Core.atlas.find(name + "-outline");
		topRegion = Core.atlas.find(topBladeName == null ? name + "-top" : modName + topBladeName + "-top");
		topRegionOutline = Core.atlas.find(topBladeName == null ? name + "-top-outline" : modName + topBladeName + "-top-outline");
	}

	// For mirroring
	public Rotor copy() {
		return JsonIO.copy(this, new Rotor(name));
	}
}
