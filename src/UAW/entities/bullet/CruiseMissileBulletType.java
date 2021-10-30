package UAW.entities.bullet;

import arc.Core;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;

import static mindustry.Vars.tilesize;

public class CruiseMissileBulletType extends BasicBulletType {
    public CruiseMissileBulletType(float speed, float damage, String sprite) {
        super(speed, damage, sprite);
        shrinkX = shrinkY = 0;
        drag = -0.015f;
        homingRange = 30 * tilesize;
        splashDamage = damage;
        splashDamageRadius = 12 * tilesize;
        homingPower = 0.15f;
        hitShake = 12f;
        hitSize = 1.5f * 8;
        hitSoundVolume = 4f;
        hitSound = Sounds.explosionbig;
        backColor = Pal.missileYellowBack;
        frontColor = Pal.missileYellow;
        trailLength = 35;
        trailWidth = trailLength / 14.6f;
        trailColor = Pal.bulletYellowBack;
        trailInterval = 0.5f;
        trailRotation = true;
    }

    public CruiseMissileBulletType(float speed, float damage) {
        this(speed, damage, "uaw-cruise-missile");

    }

    public CruiseMissileBulletType() {
        this(1.8f, 225);
    }

    @Override
    public void load() {
        super.load();
        backRegion = Core.atlas.find(sprite + "-outline");
    }
}
