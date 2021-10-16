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

public class SplashBulletType extends BulletType {
    public float splashDelay = 25f;
    public int splashAmount = 3;
    public float splashDuration = (splashDelay * splashAmount);
    public Sound applySound = Sounds.shotgun;
    public Color frontColor = Pal.bulletYellow, backColor = Pal.bulletYellowBack;
    public Effect applyEffect, particleEffect;

    public SplashBulletType(float splashDamage, float radius) {
        super(splashDamage, radius);
        this.damage = 0f;
        this.splashDamage = splashDamage;
        this.splashDamageRadius = radius;
        this.lifetime = splashDuration;
        hittable = false;
        hitSize = speed = 0;
        smokeEffect = despawnEffect = hitEffect = Fx.none;
        applyEffect = UAWFxDynamic.circleSplash(splashDamageRadius, splashDelay, frontColor, backColor);
        particleEffect = UAWFxDynamic.statusHit(frontColor, 10f);
        displayAmmoMultiplier = false;
        hittable = false;
        pierceBuilding = pierce = true;
    }

    @Override
    public void update(Bullet b) {
        if (b.timer(1, splashDelay) && splashAmount > 1) {
            Damage.damage(b.team, b.x, b.y, splashDamageRadius, splashDamage, collidesAir, collidesGround);
            applyEffect.at(b.x, b.y);
            applySound.at(b.x, b.y);
            for (int j = 0; j < ((splashAmount) * 15); j++) {
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
