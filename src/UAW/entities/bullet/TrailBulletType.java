package UAW.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.gen.Bullet;

public class TrailBulletType extends UAWBasicBulletType {
    /**
     * Whenever should the bullet sprite will be drawn
     */
    public boolean drawBullet = true;
    /**
     * How long is the generated trail based on height multiplied by this
     * <p>
     * any value <= 0 to disable, enabled by default
     * </p>
     */
    public float trailLenghtScl = 1f;
    public float trailMult = 0, trailSize = 4f;

    public TrailBulletType(float speed, float damage, String bulletSprite) {
        super(speed, damage);
        this.sprite = bulletSprite;
        height = 7f;
        width = 5f;
        trailRotation = true;
        trailEffect = Fx.artilleryTrail;
    }

    public TrailBulletType(float speed, float damage) {
        this(speed, damage, "bullet");
    }

    public TrailBulletType() {
        this(1f, 1f, "bullet");
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        if (trailLenghtScl > 0) {
            trailWidth = width / 3.4f;
            trailLength = Mathf.round(height * trailLenghtScl);
            trailColor = backColor;
        }
    }

    @Override
    public void draw(Bullet b) {
        if (drawBullet) {
            super.draw(b);
            float height = this.height * ((1f - shrinkY) + shrinkY * b.fout());
            float width = this.width * ((1f - shrinkX) + shrinkX * b.fout());
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
}
