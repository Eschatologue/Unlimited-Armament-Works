package UAW.type.units;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Time;
import mindustry.gen.*;
import mindustry.graphics.Trail;
import mindustry.type.UnitType;

public class MultiEngineUnitType extends UnitType {
	public int engineCount = 2;
	public float engineOffsetX = 5f, engineOffsetY = 0f;

	public MultiEngineUnitType(String name) {
		super(name);
		this.trailScl = engineSize;
		this.trailX = engineOffsetX;
		this.trailY = engineOffsetY;
		constructor = UnitEntity::create;
		flying = true;
	}

	@Override
	public void drawEngine(Unit unit) {
		if (!unit.isFlying()) return;

		float scale = unit.elevation;
		float offsetX = unit.rotation + (engineOffsetX * 2) * scale;
		float offsetY = unit.rotation + (engineOffsetY * 2) * scale;

		if (unit instanceof Trailc) {
			Trail trail = ((Trailc) unit).trail();
			trail.draw(unit.team.color, (engineSize + Mathf.absin(Time.time, 2f, engineSize / 4f) * scale) * trailScl);
		}
		Draw.color(unit.team.color);

		for (int i = 0; i < engineCount; i++) {
			float angle = unit.rotation + (float) (i * 360 / engineCount);
			Fill.circle(
				unit.x + Angles.trnsx(angle, offsetX, offsetY),
				unit.y + Angles.trnsy(angle, offsetX, offsetY),
				(engineSize + Mathf.absin(Time.time, 2f, engineSize / 4f)) * scale
			);
		}
		Draw.color(Color.white);
		for (int i = 0; i < engineCount; i++) {
			float angle = unit.rotation + (float) (i * 360 / engineCount);
			Fill.circle(
				unit.x + Angles.trnsx(angle, offsetX - 1, offsetY - 1),
				unit.y + Angles.trnsy(angle, offsetX - 1, offsetY - 1),
				(engineSize + Mathf.absin(Time.time, 2f, engineSize / 4f)) / 2f * scale
			);
		}
		Draw.color();
	}
}
