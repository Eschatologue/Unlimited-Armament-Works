package UAW.entities.abilities;

import UAW.entities.bullet.DamageFieldBulletType;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.abilities.Ability;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.*;

import static mindustry.Vars.tilesize;

public class RazorRotorAbility extends Ability {
    public float damage = 35f;
    public float range = 4f * tilesize;
    public float chance = 0.5f;
    public BulletType bullet;

    RazorRotorAbility() {
    }

    public RazorRotorAbility(float damage, float chance, float range) {
        this.damage = damage;
        this.chance = chance;
        this.range = range;
        this.bullet = new BulletType(damage, range) {{
            hitEffect = Fx.hitBulletSmall;
            despawnEffect = Fx.none;
            instantDisappear = true;
            speed = 0f;
            hitSize = range;
        }};
    }

    @Override
    public void update(Unit unit) {
        if(Mathf.chance(Time.delta * chance)){
            bullet.create(unit, unit.team, unit.x, unit.y, 0);
        }
    }
}
