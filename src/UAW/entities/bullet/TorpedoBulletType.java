package UAW.entities.bullet;

import UAW.graphics.*;
import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.environment.Floor;

import static mindustry.Vars.*;
import static mindustry.gen.Nulls.unit;

/**
 * Taken from Sk7725/BetaMindy - NavalBulletType
 * Modified by Eschatologue
 */
public class TorpedoBulletType extends BulletType {
    /** Scaling damage based on enemy hitSize*/
    public float hitSizeDamageScl = 1f;
    /** Maximum enemy hitSize threshold*/
    public float maxEnemyHitSize = 60f;
    /** Drag in non liquid terrain*/
    public float groundDrag = 0.15f;
    /** Drag in non Deep liquid terrain*/
    public float deepDrag = -0.005f;
    public float rippleInterval = 7f;
    public Effect rippleEffect = UAWFxStatic.torpedoRippleTrail;

    public TorpedoBulletType(float speed, float damage){
        super(speed, damage);
        layer = Layer.debris + 0.001f;
        homingPower = 0.035f;
        homingRange = 20 * tilesize;
        hitShake = 24;
        knockback = 8f;
        hitSize = 16f;
        keepVelocity = collidesAir = absorbable = hittable = reflectable = false;
        lightColor = hitColor;
        trailLength = 36;
        trailWidth = 3f;
        trailColor = UAWPal.waterMiddle;
        trailInterval = 0.2f;
        shootEffect = UAWFxStatic.shootWaterFlame;
        trailEffect = UAWFxStatic.torpedoCruiseTrail;
        trailRotation = true;
        hitEffect = new MultiEffect(Fx.flakExplosionBig, UAWFxStatic.torpedoRippleHit);
        despawnEffect = UAWFxStatic.torpedoRippleHit;
        status = StatusEffects.slow;
        statusDuration = 3 * 60;
        hitSoundVolume = 4f;
        hitSound = Sounds.explosionbig;
    }
    @Override
    public void drawTrail(Bullet b){
        if(trailLength > 0 && b.trail != null){
            float z = Draw.z();
            Draw.z(Layer.debris + 0.001f);
            b.trail.draw(trailColor, trailWidth);
            Draw.z(z);
        }
    }

    @Override
    public void update(Bullet b){
        Floor floor = Vars.world.floorWorld(b.x, b.y);
        Color liquidColor = floor.mapColor.equals(Color.black) ? Blocks.water.mapColor : floor.mapColor;
        if(!floor.isLiquid) {
            b.vel.scl(Math.max(1f - groundDrag * Time.delta, 0.01f));
            b.lifetime = b.lifetime / 3;
        }
        else{
            if(Time.time - b.fdata > rippleInterval){
                b.fdata = Time.time;
                rippleEffect.at(b.x, b.y, hitSize / 3f, liquidColor);
            }
        }
        if(floor.isDeep()){
            b.vel.scl(Math.max(1f - deepDrag * Time.delta, 0.01f));
        }
        super.update(b);
    }
    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        if(entity instanceof Healthc h){
            h.damage(b.damage * ((entity.hitSize() * hitSizeDamageScl) / 100));
        }
        if(entity.hitSize() > maxEnemyHitSize) {
            b.damage -= b.damage / ((entity.hitSize() * 3.5f) / 100);
        }
       super.hitEntity(b, entity, health);
    }
}