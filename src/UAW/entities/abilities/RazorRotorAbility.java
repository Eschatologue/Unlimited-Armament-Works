package UAW.entities.abilities;

import UAW.content.UAWBullets;
import UAW.entities.bullet.RotorBlade;
import UAW.graphics.UAWPal;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.abilities.Ability;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;

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
        this.bullet = new RotorBlade(4f, damage){{
            lifetime = range / speed;
            pierce = true;
            hittable = false;
            despawnEffect = Fx.none;
            hitEffect = Fx.hitBulletSmall;
        }};
    }
    @Override
    public void update(Unit unit){
        float rotationAngle = Time.time + 15;
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
