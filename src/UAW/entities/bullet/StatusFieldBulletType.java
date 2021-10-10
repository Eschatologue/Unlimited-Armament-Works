package UAW.entities.bullet;

import UAW.graphics.UAWFxDynamic;
import arc.math.*;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.*;
import mindustry.type.StatusEffect;

@Deprecated
public class StatusFieldBulletType extends BulletType {
    public StatusFieldBulletType(StatusEffect statusEffect, float duration){
        damage = splashDamage = 0f;
        hittable = false;
        lifetime = 0;
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
