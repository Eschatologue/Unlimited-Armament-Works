package UAW.entities.bullet;

import mindustry.content.Fx;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Sounds;
import mindustry.type.StatusEffect;

public class StatusFieldBulletType extends BulletType {
    public StatusFieldBulletType(StatusEffect statusEffect, float duration){
        damage = splashDamage = 0f;
        hittable = false;
        lifetime = 10;
        speed = 0.0005f;
        hitSize = 80;
        status = statusEffect;
        splashDamageRadius = 12 * 8;
        ammoMultiplier = 1.5f;
        displayAmmoMultiplier = false;
        statusDuration = duration;
        hitEffect = Fx.none;
        despawnEffect = smokeEffect = shootEffect = Fx.wet;
        hitSound = Sounds.none;
    }
}
