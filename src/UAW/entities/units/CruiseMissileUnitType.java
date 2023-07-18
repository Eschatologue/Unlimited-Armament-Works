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
import static UAW.audiovisual.Assets.U_MSL_crsmissile_M_01_red;
import static mindustry.Vars.tilesize;

public class CruiseMissileUnitType extends MissileUnitType {
	@Nullable
	public String sprite;

	@Nullable
	public Color exhaustColor;
	public Effect hitEffect = Fx.none;

	public CruiseMissileUnitType(String name, String spriteName, int presets) {
		super(name + "msl");
		this.sprite = spriteName;
		health = 100f;
		drawCell = false;
		outlineColor = Pal.darkerMetal;
		range = maxRange = 3 * tilesize;
		engineLayer = Layer.effect;
		lowAltitude = true;
		loopSound = Sounds.missileTrail;
		loopSoundVolume = 0.4f;
		fogRadius = 3f;
		deathExplosionEffect = Fx.none;

		switch (presets) {
			default -> {
			}
			case 1 -> {
				engineSize = 5.5f * px;
				engineOffset = 12f * px;
				trailLength = 5;
				deathSound = Sounds.explosionbig;
			}
			case 2 -> {
				engineSize = 12 * px;
				engineOffset = 35f * px;
				trailLength = 9;
				deathSound = Sounds.largeExplosion;
			}
			case 3 -> {
				engineSize = 18 * px;
				engineOffset = 57f * px;
				trailLength = 15;
				deathSound = Sounds.largeExplosion;
			}
		}
	}

	public CruiseMissileUnitType(String name, String spriteName) {
		this(name, spriteName, 0);
	}

	public CruiseMissileUnitType(String name) {
		this(name + "msl", U_MSL_crsmissile_M_01_red);
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
