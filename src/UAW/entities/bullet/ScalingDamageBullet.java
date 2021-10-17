package UAW.entities.bullet;

import arc.Events;
import arc.util.Tmp;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.world.blocks.defense.Wall;

import static mindustry.Vars.player;

/**
 * Bullet with damage scaling based on certain conditions
 *
 * @Author Eschathologue
 */
public class ScalingDamageBullet extends BulletType {
    /**
     * Percentage of how much of the damage ignores armor
     */
    public float armorIgnoreScl = 0f;
    /**
     * Multiplies damage only against shields
     */
    public float shieldDamageScl = 0f;
    /**
     * Multiplies damage only against hitSize
     */
    public float hitSizeDamageScl = 0f;
    /**
     * Maximum hitSize before damage gets reduced
     */
    public float maxEnemyHitSize = 60f;

    public ScalingDamageBullet(float speed, float damage) {
        this.speed = speed;
        this.damage = damage;
    }

    public ScalingDamageBullet() {
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health) {
        float pierceDamage = b.damage * armorIgnoreScl;
        float damageReduction = 0f;
        if (entity.hitSize() > maxEnemyHitSize) {
            damageReduction = ((entity.hitSize() * 10f) / 100);
        }
        if (entity.hitSize() < maxEnemyHitSize / 3) {
            damageReduction = ((entity.hitSize() * 20f) / 100);
        }
        if (entity instanceof Healthc h) {
            if (armorIgnoreScl > 1) {
                h.damage(b.damage);
            }
            h.damagePierce(pierceDamage);
            h.damage(((b.damage * ((entity.hitSize() * hitSizeDamageScl) / 100)) / damageReduction) - pierceDamage);
        }
        if (entity instanceof Shieldc h && shieldDamageScl > 0) {
            h.damagePierce(b.damage * shieldDamageScl);
        }
        if (entity instanceof Unit unit) {
            Tmp.v3.set(unit).sub(b).nor().scl(knockback * 80f);
            if (impact) Tmp.v3.setAngle(b.rotation() + (knockback < 0 ? 180f : 0f));
            unit.impulse(Tmp.v3);
            unit.apply(status, statusDuration);
        }
        if (b.owner instanceof Wall.WallBuild && player != null && b.team == player.team() && entity instanceof Unit unit && unit.dead) {
            Events.fire(EventType.Trigger.phaseDeflectHit);
        }
    }
}
