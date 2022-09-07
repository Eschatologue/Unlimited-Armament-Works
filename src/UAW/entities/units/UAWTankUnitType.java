package UAW.entities.units;

import mindustry.gen.TankUnit;
import mindustry.graphics.Pal;
import mindustry.type.unit.TankUnitType;
import mindustry.world.meta.Env;

public class UAWTankUnitType extends TankUnitType {
	public UAWTankUnitType(String name) {
		super(name);
		outlineColor = Pal.darkerMetal;
		envDisabled = Env.space;
		envRequired = Env.terrestrial;

		constructor = TankUnit::create;
	}
}
