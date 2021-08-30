package UAW.entities.bullet;

import arc.util.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;

import static mindustry.Vars.*;

public class MineBulletType extends BasicBulletType {
    public float blockDetonationRange = 4.5f;
    public float detonationDelay = 15f;
    public float size = 14;

    // blastRadius are based in block/tile, so 6 blastRadius = 6 tiles/block
    public MineBulletType (float damage, float blastRadius, float duration, String sprite) {
        super(damage, blastRadius, sprite);
        splashDamageRadius = tilesize * blastRadius;
        splashDamage = damage;
        mixColorTo = backColor;
        height = width = size;
        collidesAir = false;
        collidesGround = collidesTiles = true;
        shrinkX = shrinkY = 0f;
        lifetime = duration * 60;
        speed = 3f;
        drag = 0.055f;
        hitShake = 8f;
        hitSound = Sounds.plasmaboom;
        fragVelocityMin = 0.3f;
        fragVelocityMax = fragVelocityMin * 1.2f;
        fragLifeMin = 0.4f;
        fragLifeMax = 0.8f;
    }

    public MineBulletType (float damage, float duration, float blastRadius) {
        this(damage, blastRadius, duration, "uaw-mine");
    }

    @Override
    public void update (Bullet b) {
        float detonationRange = tilesize * blockDetonationRange;
        super.update(b);
        if (b.fdata < 0f) return;
        if (b.timer(2, 6)) {
            Units.nearbyEnemies(b.team, Tmp.r1.setSize(detonationRange * 2f).setCenter(b.x, b.y), unit -> {
                if (b.fdata < 0f || !unit.checkTarget(collidesAir, collidesGround)) return;
                if (unit.within(b, detonationRange)) {
                    b.fdata = -1f;
                    Time.run(detonationDelay, () -> {
                        if (b.fdata < 0) {
                            b.time = b.lifetime;
                        }
                    });
                }
            });
        }
    }

}