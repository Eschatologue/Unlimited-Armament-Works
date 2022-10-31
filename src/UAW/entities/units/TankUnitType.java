package UAW.entities.units;

import UAW.entities.units.entity.TankUnitEntity;
import arc.Core;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.world.meta.Env;

public class TankUnitType extends mindustry.type.unit.TankUnitType {
	public TextureRegion treadOutlineRegion;

	public boolean overlayOutline = true;

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

	@Override
	public <T extends Unit & Tankc> void drawTank(T unit) {
		super.drawTank(unit);
		if (overlayOutline) Draw.rect(treadOutlineRegion, unit.x, unit.y, unit.rotation - 90);
	}

	@Override
	public void load() {
		super.load();
		if (overlayOutline) treadOutlineRegion = Core.atlas.find(name + "-treads-out");
	}
}
