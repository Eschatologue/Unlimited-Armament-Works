package UAW.entities.bullet;

import UAW.graphics.UAWFxDynamic;
import arc.audio.Sound;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.*;
import mindustry.graphics.Pal;

import static mindustry.Vars.*;

public class DamageFieldBulletType extends BasicBulletType {
    public boolean continiousApply = true;
    public Sound applySound = Sounds.shield;
    public float radius = 4 * tilesize;

    public DamageFieldBulletType(float speed, float damage) {
        super(speed,damage);
        splashDamageRadius = radius;
        scaleVelocity = true;
    }

    @Override
    public void hit(Bullet b, float x, float y) {
        Effect applyEffect = UAWFxDynamic.circleApply(Pal.bulletYellow, Pal.bulletYellowBack, radius);
        super.hit(b, x, y);
        if (splashDamageRadius > 0 && !b.absorbed && continiousApply) {
            if (b.timer(1, 15f)) {
                Damage.damage(b.team, x, y, splashDamageRadius, splashDamage * b.damageMultiplier(), collidesAir, collidesGround);
                applySound.at(b.x, b.y);
                applyEffect.at(b.x,b.y);

                if (status != StatusEffects.none) {
                    Damage.status(b.team, x, y, splashDamageRadius, status, statusDuration, collidesAir, collidesGround);
                }
                if (healPercent > 0f) {
                    indexer.eachBlock(b.team, x, y, splashDamageRadius, Building::damaged, other -> {
                        Fx.healBlockFull.at(other.x, other.y, other.block.size, Pal.heal);
                        other.heal(healPercent / 100f * other.maxHealth());
                    });
                }
            }
        }
    }
}
