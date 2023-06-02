package UAW.entities.units;

import arc.Core;
import arc.graphics.Color;
import arc.util.Nullable;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.type.unit.MissileUnitType;

import static UAW.Vars.*;
import static mindustry.Vars.tilesize;

public class CruiseMissileUnitType extends MissileUnitType {
	@Nullable
	public String sprite;

	@Nullable
	public Color exhaustColor;
	public Effect hitEffect = Fx.none;

	public int presets = 0;

	public CruiseMissileUnitType(String name, String spriteName) {
		super(name + "msl");
		this.sprite = spriteName;
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
		physics = true;
	}

	public CruiseMissileUnitType(String name) {
		this(name + "msl", cruisemissile_medium_basic);
	}

	@Override
	public void init() {
		super.init();
		if (exhaustColor != null) engineColor = trailColor = exhaustColor;

		switch (presets) {
			case 0 -> {
			}
			case 1 -> {
				engineSize = 4 * px;
				engineOffset = 12f * px;
				trailLength = 9;
				deathSound = Sounds.largeExplosion;
			}
			case 2 -> {
				engineSize = 9 * px;
				engineOffset = 28f * px;
				trailLength = 12;
				deathSound = Sounds.largeExplosion;
			}
		}
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
