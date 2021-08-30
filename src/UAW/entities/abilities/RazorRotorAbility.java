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
    // Rotor damage
    public float damage = 35f;
    // Damage Range
    public float range = 4f * tilesize;
    //Chance of firing every tick. Set >= 1 to always fire lightning every tick at max speed.
    public float chance = 20f;
    // Speeds for when to start firing and when to stop getting faster
    public float minSpeed = 0.8f, maxSpeed = 2.5f;
    // Bullet type that is fired. Can be null
    public BulletType bullet;
/*
    public float layer = Layer.bullet - 0.001f, blinkScl = 20f;
    public float sectorRad = 0.14f, rotateSpeed = 0.5f;
    public int sectors = 4;
    public Color color = UAWPal.titaniumBlueMiddle;
*/
    RazorRotorAbility(){}

    public RazorRotorAbility(float damage, float chance ,float range){
        this.damage = damage;
        this.chance = chance;
        this.range = range;
        this.bullet = new RotorBlade(4f, damage){{
            lifetime = range / speed;
            pierce = true;
            despawnEffect = Fx.none;
            hitEffect = Fx.hitBulletSmall;
        }};
    }
/*
    @Override
    public void draw(Unit unit){
        float scl = Mathf.clamp((unit.vel().len() - minSpeed) / (maxSpeed - minSpeed));
        float rx = Tmp.v1.x, ry = Tmp.v1.y;
        super.draw(unit);
        Draw.z(layer);
        Draw.color(color);
        Lines.stroke((0.7f + Mathf.absin(blinkScl, 0.7f)), color);
        Lines.stroke(Lines.getStroke() * scl);
        if(scl > 0){
            for(int i = 0; i < sectors; i++){
                float rot = unit.rotation + i * 360f/sectors + Time.time * rotateSpeed;
                Lines.swirl(rx, ry, range, sectorRad, rot);
            }
        }
        Drawf.light(rx, ry, range, color, scl * 0.8f);
        Draw.reset();
    }
*/
    @Override
    public void update(Unit unit){
        float rotationAngle = Time.time + 15;
        float scl = Mathf.clamp((unit.vel().len() - minSpeed) / (maxSpeed - minSpeed));
        if(Mathf.chance(Time.delta * chance * scl)) {
            float x = unit.x + Angles.trnsx(unit.rotation, 0, 0);
            float y = unit.y + Angles.trnsy(unit.rotation, 0, 0);
            bullet.create(unit, unit.team, x, y, rotationAngle);
        }
    }
}
