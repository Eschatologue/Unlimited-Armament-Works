package UAW.entities.units;

import UAW.audiovisual.UAWFx;
import UAW.type.weapon.SuicideWeapon;
import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.entities.bullet.ExplosionBulletType;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.type.unit.MissileUnitType;

import static mindustry.Vars.tilesize;

public class CruiseMissileUnitType extends MissileUnitType {
	public Color exhaustColor = Pal.bulletYellowBack;

	public CruiseMissileUnitType(String name) {
		super(name);
		health = 150;
		outlineColor = Pal.darkerMetal;
		maxRange = 2 * tilesize;
		engineColor = trailColor = exhaustColor;
		engineLayer = Layer.effect;
		lowAltitude = true;
		loopSound = Sounds.missileTrail;
		loopSoundVolume = 0.6f;
		fogRadius = 3f;
		deathExplosionEffect = Fx.none;

	}
}
