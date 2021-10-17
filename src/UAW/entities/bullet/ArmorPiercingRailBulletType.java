package UAW.entities.bullet;

import arc.Events;
import arc.util.Tmp;
import mindustry.entities.bullet.RailBulletType;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.world.blocks.defense.Wall;

import static mindustry.Vars.player;

public class ArmorPiercingRailBulletType extends RailBulletType {
    static float furthest = 0;
    public float armorIgnoreScl = 0.5f;
    public float shieldDamageMultiplier;

    void handle(Bullet b, Posc pos, float initialHealth) {
        float sub = Math.max(initialHealth * pierceDamageFactor, 0);

        if (b.damage <= 0) {
            b.fdata = Math.min(b.fdata, b.dst(pos));
            return;
        }

        if (b.damage > 0) {
            pierceEffect.at(pos.getX(), pos.getY(), b.rotation());

            hitEffect.at(pos.getX(), pos.getY());
        }

        //subtract health from each consecutive pierce
        b.damage -= Math.min(b.damage, sub);

        //bullet was stopped, decrease furthest distance
        if (b.damage <= 0f) {
            furthest = Math.min(furthest, b.dst(pos));
        }
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health) {
        float realDamage = b.damage * armorIgnoreScl;
        if (entity instanceof Healthc h) {
            h.damagePierce(realDamage);
            if (armorIgnoreScl < 1) {
                h.damage(b.damage - realDamage);
            }
        }
        if (entity instanceof Shieldc h) {
            h.damagePierce(b.damage * shieldDamageMultiplier);
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
        handle(b, entity, health);
    }
}
