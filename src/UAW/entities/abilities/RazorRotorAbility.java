package UAW.entities.abilities;

import UAW.entities.bullet.RotorBlade;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.abilities.Ability;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Unit;

import static mindustry.Vars.tilesize;

public class RazorRotorAbility extends Ability{
    public float damage = 35f;
    public float range = 4f * tilesize;
    public float chance = 20f;
    public float minSpeed = 0.8f, maxSpeed = 2.5f;
    public BulletType bullet;

    RazorRotorAbility(){}

    public RazorRotorAbility(float damage, float chance ,float range){
        this.damage = damage;
        this.chance = chance;
        this.range = range;
        this.bullet = new RotorBlade(5f, damage){{
            lifetime = range / speed;
            pierce = true;
            hittable = false;
            despawnEffect = Fx.none;
            hitEffect = Fx.hitBulletSmall;
        }};
    }
    @Override
    public void update(Unit unit){
        float rotationAngle = Time.time + 12;
        float scl = Mathf.clamp((unit.vel().len() - minSpeed) / (maxSpeed - minSpeed));
        if(Mathf.chance(Time.delta * chance * scl)) {
            float x = unit.x + Angles.trnsx(unit.rotation, 0, 0);
            float y = unit.y + Angles.trnsy(unit.rotation, 0, 0);
            for(int j = 0; j < 4; j++){
                float angle = ((j * 360f / 4 + (Time.time * rotationAngle) % 360));
                bullet.create(unit, unit.team, x, y, angle);
            }
        }
    }
}
