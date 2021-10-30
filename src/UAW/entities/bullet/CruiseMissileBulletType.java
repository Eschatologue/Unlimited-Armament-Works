package UAW.entities.bullet;

import UAW.graphics.UAWFxStatic;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.*;
import mindustry.graphics.*;

import static mindustry.Vars.tilesize;

public class CruiseMissileBulletType extends BasicBulletType {
    public float size = 35;
    public CruiseMissileBulletType(float speed, float damage, String sprite) {
        super(speed, damage, sprite);
        height = size;
        width = size / 1.5f;
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
        trailInterval = 0.5f;
        trailEffect = UAWFxStatic.pyraSmokeTrail;
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

    @Override
    public void draw(Bullet b){
        super.draw(b);
        float offset = -90 + (spin != 0 ? Mathf.randomSeed(b.id, 360f) + b.time * spin : 0f);

        Color mix = Tmp.c1.set(mixColorFrom).lerp(mixColorTo, b.fin());

        Draw.mixcol(mix, mix.a);

        Draw.color(backColor);
        Draw.rect(backRegion, b.x, b.y, width, height, b.rotation() + offset);
        Draw.color(frontColor);
        Draw.rect(frontRegion, b.x, b.y, width, height, b.rotation() + offset);

        Draw.reset();
    }
}

