package UAW.entities.bullet;

import UAW.graphics.UAWFxDynamic;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.math.*;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.*;
import mindustry.graphics.Pal;

import static mindustry.Vars.tilesize;

/**
 * Damages all enemies caught within its area of effect, should be used like fragBullets
 */

public class DamageFieldBulletType extends BulletType {
    /**
     * How big is the area, already multiplied with tilesize
     */
    public float blockRadius = 4;
    /**
     * Interval per splash
     */
    public float splashDelay = 45f;
    /**
     * How many times it splashes
     */
    public int splashAmount = 3;
    public Sound applySound = Sounds.shotgun;
    public Color frontColor = Pal.bulletYellow, backColor = Pal.bulletYellowBack;

    public DamageFieldBulletType(float damage) {
        splashDamage = damage;
        splashDamageRadius = (blockRadius * tilesize);
        hittable = false;
        lifetime = 60f;
        speed = 0;
        despawnEffect = Fx.none;
    }

    @Override
    public void update(Bullet b) {
        Effect applyEffect = UAWFxDynamic.circleSplash(frontColor, backColor, splashDamageRadius);
        Effect particleEffect = UAWFxDynamic.statusHit(frontColor, 10f);
        if (b.timer(1, splashDelay)) {
                Damage.damage(b.team, b.x, b.y, splashDamageRadius, splashDamage);
                applyEffect.at(b.x, b.y);
                applySound.at(b.x, b.y);
                for (int j = 0; j < (splashAmount * 5); j++) {
                    particleEffect.at(
                            b.x + Angles.trnsx(Mathf.random(360), Mathf.random(splashDamageRadius)),
                            b.y + Angles.trnsx(Mathf.random(360), Mathf.random(splashDamageRadius))
                    );
                }
            }
    }
}
