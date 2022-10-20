package UAW.entities.units.entity;

import UAW.entities.units.HelicopterUnitType;
import mindustry.ai.types.FlyingAI;

public class AirshipUnitType extends HelicopterUnitType {
	public AirshipUnitType(String name) {
		super(name);
		lowAltitude = true;
		flying = true;
		faceTarget = false;
		aiController = FlyingAI::new;
	}
}
