package UAW.entities.bullet;

import UAW.graphics.UAWFxDynamic;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.math.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.*;
import mindustry.graphics.Pal;

import static mindustry.Vars.indexer;

/**
 * Damages all enemies caught within its area of effect
 */

public class DamageFieldBulletType extends BulletType {
    public float splashDelay = 25f;
    public int splashAmount = 3;
    public boolean shake = false;
    public Sound applySound = Sounds.shotgun;
    public Color frontColor = Pal.bulletYellow, backColor = Pal.bulletYellowBack;
    public Effect applyEffect, particleEffect;

    public DamageFieldBulletType(float splashDamages, float radius) {
        super(splashDamages, radius);
        damage = 0f;
        splashDamage = splashDamages;
        splashDamageRadius = radius;
        hittable = false;
        lifetime = (splashDelay * splashAmount);
        hitSize = speed = 0;
        smokeEffect = despawnEffect = hitEffect = Fx.none;
        applyEffect = UAWFxDynamic.circleSplash(frontColor, backColor, splashDamageRadius);
        particleEffect = UAWFxDynamic.statusHit(frontColor, 10f);
        displayAmmoMultiplier = false;
        hittable = false;
    }

    @Override
    public void update(Bullet b) {
        if (b.timer(1, splashDelay) && splashAmount > 1) {
            Damage.damage(b.team, b.x, b.y, splashDamageRadius, splashDamage, collidesAir, collidesGround);
            applyEffect.at(b.x, b.y);
            applySound.at(b.x, b.y);
            if (shake){
                Effect.shake(hitShake, hitShake, b.x, b.y);
            }
            for (int j = 0; j < ((splashAmount - 1) * 10); j++) {
                particleEffect.at(
                        b.x + Angles.trnsx(Mathf.random(360), Mathf.random(splashDamageRadius)),
                        b.y + Angles.trnsx(Mathf.random(360), Mathf.random(splashDamageRadius))
                );
            }
            if (status != StatusEffects.none) {
                Damage.status(b.team, b.x, b.y, splashDamageRadius, status, statusDuration, collidesAir, collidesGround);
            }
            if (healPercent > 0f) {
                indexer.eachBlock(b.team, b.x, b.y, splashDamageRadius, Building::damaged, other -> {
                    Fx.healBlockFull.at(other.x, other.y, other.block.size, Pal.heal);
                    other.heal(healPercent / 100f * other.maxHealth());
                });
            }
            if (makeFire) {
                indexer.eachBlock(null, b.x, b.y, splashDamageRadius, other -> other.team != b.team, other -> Fires.create(other.tile));
            }
        }
        super.update(b);
    }
}
