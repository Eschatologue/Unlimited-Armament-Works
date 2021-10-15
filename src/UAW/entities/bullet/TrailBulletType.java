package UAW.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;

public class TrailBulletType extends BasicBulletType {
    /**
     * Draws the Bullet Sprite
     */
    public boolean drawBullet = true;
    /**
     * Trail Length scales based on bullet height
     */
    public float trailLenghtScl = 1;

    public TrailBulletType(float speed, float damage, String bulletSprite) {
        super(speed, damage);
        this.sprite = bulletSprite;
        trailWidth = width / 3.8f;
        trailLength = Mathf.round(height * trailLenghtScl);
        trailRotation = true;
        trailColor = backColor;
    }

    public TrailBulletType(float speed, float damage) {
        this(speed, damage, "bullet");
    }

    public TrailBulletType() {
        this(1f, 1f, "bullet");
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
