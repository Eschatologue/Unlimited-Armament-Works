package UAW.entities.bullet;

import UAW.graphics.UAWFxDynamic;
import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.effect.MultiEffect;

import static mindustry.Vars.tilesize;

public class BuckshotBulletType extends BasicBulletType {

    public BuckshotBulletType(float speed, float damage){
        super(speed, damage);
        height = width = 15;
        shrinkX = shrinkY = 0.5f;
        splashDamageRadius = 1.6f * tilesize;
        splashDamage = damage / 1.8f;
        pierceCap = 2;
        knockback = 2;
        trailInterval = 4.5f;
        trailColor = Color.lightGray;
        shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame);
        smokeEffect = Fx.shootBigSmoke2;
        hitEffect = Fx.hitBulletBig;
        despawnEffect = UAWFxDynamic.thermalExplosion(frontColor, backColor);
    }
}
