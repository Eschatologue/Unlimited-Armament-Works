package UAW.entities.bullet;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.util.Tmp;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.*;
import mindustry.graphics.*;

import static mindustry.Vars.tilesize;

public class CruiseMissileBulletType extends BasicBulletType {
    public CruiseMissileBulletType(float speed, float damage, String sprite) {
        super(speed, damage, sprite);
        layer = Layer.bullet - 1;
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
        trailLength = 36;
        trailWidth = trailLength / 13f;
        trailColor = Pal.bulletYellowBack;
        trailInterval = 0.5f;
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

    @Override
    public void draw(Bullet b) {
        super.draw(b);
        Draw.rect(backRegion, b.x, b.y, b.rotation() - 90);
        Draw.rect(frontRegion, b.x, b.y, b.rotation() - 90);

        Draw.reset();
    }
}
