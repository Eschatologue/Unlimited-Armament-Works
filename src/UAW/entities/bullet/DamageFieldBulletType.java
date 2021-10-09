package UAW.entities.bullet;

import UAW.graphics.UAWFxDynamic;
import arc.audio.Sound;
import arc.util.Time;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.Pal;

import static mindustry.Vars.*;

public class DamageFieldBulletType extends ArtilleryBulletType {
    public boolean continiousApply = true;
    public Sound applySound = Sounds.plasmadrop;
    public float radius = 4 * tilesize;
    public float splashDelay = 30f;
    public int splashAmount = 3;

    public DamageFieldBulletType(float speed, float damage) {
        super(speed, damage);
        splashDamageRadius = radius;
        scaleVelocity = true;
        height = 24f;
        width = height / 2.5f;
    }

    @Override
    public void hit(Bullet b, float x, float y) {
        Effect applyEffect = UAWFxDynamic.circleSplash(frontColor, backColor, splashDamageRadius);
        super.hit(b, x, y);
        // Modifies how splash damage are dealt
        if (splashDamageRadius > 0 && !b.absorbed && continiousApply) {
            // Thx to sh1penfire#0868 & iarkn#8872 for helping me with this
            for (int i = 0; i < splashAmount; i++) {
                Time.runTask(splashDelay * i, () -> {
                    Damage.damage(b.team, x, y, splashDamageRadius, splashDamage * b.damageMultiplier(), collidesAir, collidesGround);
                    applySound.at(b.x, b.y);
                    applyEffect.at(b.x, b.y);

                    if (status != StatusEffects.none) {
                        Damage.status(b.team, x, y, splashDamageRadius, status, statusDuration, collidesAir, collidesGround);
                    }
                    if (healPercent > 0f) {
                        indexer.eachBlock(b.team, x, y, splashDamageRadius, Building::damaged, other -> {
                            Fx.healBlockFull.at(other.x, other.y, other.block.size, Pal.heal);
                            other.heal(healPercent / 100f * other.maxHealth());
                        });
                    }
                });
            }
        }
    }
}
