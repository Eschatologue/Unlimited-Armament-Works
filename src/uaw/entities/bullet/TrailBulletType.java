package uaw.entities.bullet;

import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;

/**
 * A {@link BasicBulletType} that auto-sizes its trail from the bullet's own dimensions. Used as the base class for most UAW projectiles
 */
@Deprecated
public class TrailBulletType extends BasicBulletType {

    public float trailLengthScl = 0.6f;
    public float trailWidthScl = 0.192f;

    public TrailBulletType(float speed, float damage, String bulletSprite) {
        super(speed, damage);
        sprite = bulletSprite;
        trailEffect = Fx.none; // suppress the default spark trail

    }

    public TrailBulletType(float speed, float damage) {
        this(speed, damage, "bullet");
    }

    public TrailBulletType() {
        this(0f, 0f, "bullet");
    }

    @Override
    public void init() {
        super.init();
        if (trailLengthScl > 0) {
            trailRotation = true;
            trailWidth = width * trailWidthScl;
            trailLength = Mathf.round(height * trailLengthScl);
            trailColor = backColor;
        }
        // drawSize tells the renderer how far offscreen a bullet can be before it's culled. A long trail needs extra room, hence the generous multiplier
        drawSize = Math.max(drawSize, trailLength * speed * 4.5f);
    }
}