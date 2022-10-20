package UAW.entities.units;

import UAW.entities.units.entity.TankUnitEntity;
import mindustry.graphics.Pal;
import mindustry.world.meta.Env;

public class TankUnitType extends mindustry.type.unit.TankUnitType {

	public float terrainSpeedMultiplier = 1;
	public float terrainDragMultiplier = 1f;

	public TankUnitType(String name) {
		super(name);
		outlineColor = Pal.darkerMetal;
		squareShape = false;
		envDisabled = Env.space;
		envRequired = Env.terrestrial;

		constructor = TankUnitEntity::new;
	}
}
