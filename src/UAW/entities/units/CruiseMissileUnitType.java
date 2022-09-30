package UAW.entities.units;

import arc.Core;
import arc.graphics.Color;
import arc.util.Nullable;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.type.unit.MissileUnitType;

import static mindustry.Vars.tilesize;

public class CruiseMissileUnitType extends MissileUnitType {
	@Nullable
	public String sprite;

	@Nullable
	public Color exhaustColor;
	public Effect hitEffect = Fx.none;

	public CruiseMissileUnitType(String name) {
		super(name);
		health = 100f;
		drawCell = false;
		outlineColor = Pal.darkerMetal;
		maxRange = 2 * tilesize;
		engineLayer = Layer.effect;
		lowAltitude = true;
		loopSound = Sounds.missileTrail;
		loopSoundVolume = 0.4f;
		fogRadius = 3f;
		deathExplosionEffect = Fx.none;
	}

	@Override
	public void init() {
		super.init();
		if (exhaustColor != null) engineColor = trailColor = exhaustColor;
	}

	@Override
	public void load() {
		super.load();
		if (sprite != null) {
			shadowRegion = region = Core.atlas.find(sprite);
			if (drawCell) cellRegion = Core.atlas.find(sprite + "-cell");
		}
	}

}
