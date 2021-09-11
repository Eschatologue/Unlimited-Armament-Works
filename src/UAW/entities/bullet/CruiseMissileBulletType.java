package UAW.entities.bullet;

import UAW.graphics.UAWFxStatic;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;

import static mindustry.Vars.tilesize;

public class CruiseMissileBulletType extends BasicBulletType {
    public float size = 35;

    public CruiseMissileBulletType(float speed, float damage, String sprite) {
        super(speed, damage, sprite);
        height = size;
        width = size / 1.5f;
        shrinkX = shrinkY = 0;
        drag = -0.015f;
        homingRange = 30 * tilesize;
        splashDamage = damage;
        splashDamageRadius = 12 * tilesize;
        lifetime = 6 * 60;
        homingPower = 0.15f;
        knockback = 8;
        hitShake = 12f;
        hitSize = 14;
        hitSoundVolume = 4f;
        hitSound = Sounds.explosionbig;
        backColor = Pal.missileYellowBack;
        frontColor = Pal.missileYellow;
        trailLength = 35;
        trailWidth = size / 14.6f;
        trailColor = Pal.lightPyraFlame;
        trailInterval = 0.8f;
        trailEffect = UAWFxStatic.cruiseMissileTrail;
        trailRotation = true;
    }

    public CruiseMissileBulletType(float speed, float damage) {
        this(speed, damage,"uaw-cruise-missile");

    }
    public CruiseMissileBulletType(){
        this(1.8f, 225);
    }

}
