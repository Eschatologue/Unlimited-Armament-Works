package UAW.type;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Time;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;

public class Rotor {
	public final String name;
	public TextureRegion bladeRegion, bladeOutlineRegion, topRegion, topRegionOutline;

	public float x = 0f;
	public float y = 0f;

	/** Rotor base rotation speed */
	public float rotorSpeed = 12;
	/** Rotor rotation speed when the unit dies */
	public float rotorDeadSpeed = 5f;
	/** The speed of which the rotor will reach its minimum rotation speed */
	public float rotorSlowdownSpeed = 0.004f;
	/** TODO The starting angle of the rotor rotation */
	public float initialRotation = 0f;
	public float layer = Layer.flyingUnitLow + 0.001f;
	public boolean drawRotorTop = true, doubleRotor = false;
	public int bladeCount = 4;

	protected float realRotationSpeed;

	public Rotor(String name) {
		this.name = name;
	}

	public void load() {
		bladeRegion = Core.atlas.find(name);
		bladeOutlineRegion = Core.atlas.find(name + "-outline");
		topRegion = Core.atlas.find(name + "-top");
		topRegionOutline = Core.atlas.find(name + "-top-outline");
	}

	public void update(Unit unit) {
		if (unit.health <= 0 || unit.dead()) {
			realRotationSpeed = Time.time * (Mathf.approachDelta(rotorSpeed,0,rotorSlowdownSpeed));
		} else {
			realRotationSpeed = Time.time * rotorSpeed;
		}

	}

	public void draw(Unit unit) {
		float rx = unit.x + Angles.trnsx(unit.rotation - 90, x, y);
		float ry = unit.y + Angles.trnsy(unit.rotation - 90, x, y);

		for (int i = 0; i < bladeCount; i++) {
			float angle = initialRotation + ((i * 360f / bladeCount + realRotationSpeed % 360));
			Draw.z(layer);
			Draw.rect(bladeOutlineRegion, rx, ry, bladeOutlineRegion.width * Draw.scl, bladeOutlineRegion.height * Draw.scl, angle);
			Draw.mixcol(Color.white, unit.hitTime);
			Draw.rect(bladeRegion, rx, ry, bladeRegion.width * Draw.scl, bladeRegion.height * Draw.scl, angle);
			if (doubleRotor) {
				Draw.rect(bladeOutlineRegion, rx, ry, bladeOutlineRegion.width * Draw.scl * -Mathf.sign(false), bladeOutlineRegion.height * Draw.scl, -angle);
				Draw.mixcol(Color.white, unit.hitTime);
				Draw.rect(bladeRegion, rx, ry, bladeRegion.width * Draw.scl * -Mathf.sign(false), bladeRegion.height * Draw.scl, -angle);
			}
		}
		if (drawRotorTop) {
			Draw.z(layer + 0.001f);
			Draw.rect(topRegionOutline, rx, ry, topRegionOutline.width * Draw.scl, topRegionOutline.height * Draw.scl, unit.rotation - 90);
			Draw.mixcol(Color.white, unit.hitTime);
			Draw.rect(topRegion, rx, ry, topRegion.width * Draw.scl, topRegion.height * Draw.scl, unit.rotation - 90);
		}
	}
}
