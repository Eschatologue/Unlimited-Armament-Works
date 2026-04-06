package UAW.entities.bullet;

import mindustry.type.StatusEffect;

import static UAW.Vars.modName;

public class StatusEffectBulletType extends TrailBulletType {

	public StatusEffectBulletType(String bulletSprite, StatusEffect status, float statusDuration) {
		this.sprite = modName + bulletSprite;
		this.status = status;
		this.statusDuration = statusDuration;
		width = height = 15f;
		speed = 7f;
		trailWidthScale = 0.2f;
		shrinkX = shrinkY = 0f;
		hitSize = (width + height) / 2;
		despawnHit = true;
	}

	public StatusEffectBulletType(StatusEffect status, float statusDuration) {
		this("square-orb", status, statusDuration);
	}
}
