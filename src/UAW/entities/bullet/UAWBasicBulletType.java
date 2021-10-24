package UAW.entities.bullet;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.gen.Bullet;
import mindustry.graphics.Pal;

public class UAWBasicBulletType extends UAWBulletType {
    public Color backColor = Pal.bulletYellowBack, frontColor = Pal.bulletYellow;
    public Color mixColorFrom = new Color(1f, 1f, 1f, 0f), mixColorTo = new Color(1f, 1f, 1f, 0f);
    public float width = 5f, height = 7f;
    public float shrinkX = 0f, shrinkY = 0.5f;
    public float spin = 0;
    public String sprite;

    public TextureRegion backRegion;
    public TextureRegion frontRegion;

    public UAWBasicBulletType(float speed, float damage, String bulletSprite) {
        super(speed, damage);
        this.sprite = bulletSprite;
    }

    public UAWBasicBulletType(float speed, float damage) {
        this(speed, damage, "bullet");
    }

    /**
     * For mods.
     */
    public UAWBasicBulletType() {
        this(1f, 1f, "bullet");
    }

    @Override
    public void load() {
        backRegion = Core.atlas.find(sprite + "-back");
        frontRegion = Core.atlas.find(sprite);
    }

    @Override
    public void draw(Bullet b) {
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
