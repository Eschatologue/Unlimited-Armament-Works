package UAW.entities.bullet;

import UAW.content.UAWStatusEffects;
import UAW.graphics.*;
import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.blocks.environment.*;

import static mindustry.Vars.tilesize;

/**
 * Taken from Sk7725/BetaMindy - NavalBulletType
 * Modified by Eschatologue
 */
public class TorpedoBulletType extends BulletType {
    public float groundDrag = 0.15f;
    public float deepDrag = -0.005f;
    public float rippleInterval = 7f;
    public Effect rippleEffect = UAWFxStatic.torpedoRippleTrail;

    public TorpedoBulletType(float speed, float damage){
        super(speed, damage);
        layer = Layer.debris + 0.001f;
        homingPower = 0.035f;
        homingRange = 20 * tilesize;
        hitShake = 16;
        knockback = 8f;
        keepVelocity = collidesAir = absorbable = hittable = false;
        lightColor = hitColor;
        trailLength = 36;
        trailWidth = 2f;
        trailColor = UAWPal.waterMiddle;
        trailInterval = 0.2f;
        shootEffect = UAWFxStatic.shootWaterFlame;
        trailEffect = UAWFxStatic.torpedoCruiseTrail;
        trailRotation = true;
        hitEffect = new MultiEffect(Fx.flakExplosionBig, UAWFxStatic.torpedoRippleHit);
        despawnEffect = UAWFxStatic.torpedoRippleHit;
        status = StatusEffects.slow;
        statusDuration = 3 * 60;
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
}