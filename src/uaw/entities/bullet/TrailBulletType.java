package uaw.entities.bullet;

import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import uaw.audiovisual.fx.ShootingFx;

/**
 * A {@link BasicBulletType} that auto-sizes its trail from the bullet's own dimensions. Used as the base class for most UAW projectiles
 */
public class TrailBulletType extends BasicBulletType {

    /**
     * Trail length = bullet height × this. Set to 0 to disable the trail entirely
     */
    public float trailLengthScl = 0.6f;
    /**
     * Trail width = bullet width x this
     */
    public float trailWidthScl = 0.192f;
    /**
     * Which draw layer the trail sits on
     * <p>A negative value means "just below whatever layer the bullet itself is on", which prevents the trail from drawing over the bullet tip</p>
     */
    public float trailLayer = -1f;
    /**
     * How far below the bullet's own layer the trail is drawn (used when {@link #trailLayer} < 0)
     */
    public float trailLayerOffset = 0.0001f;

    public TrailBulletType(float speed, float damage, String bulletSprite) {
        super(speed, damage);
        sprite = bulletSprite;
        trailEffect = Fx.none; // suppress the default spark trail
        hitSize = width;
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
        if (trailLengthScl > 0) autoTrail();
        // drawSize tells the renderer how far offscreen a bullet can be before it's culled. A long trail needs extra room, hence the generous multiplier
        drawSize = Math.max(drawSize, trailLength * speed * 4.5f);
    }

    /**
     * Derives trail dimensions from the bullet sprite and sets {@link #trailColor} to match {@link #backColor}. Override to customise.
     */
    protected void autoTrail() {
        trailRotation = true;
        trailWidth = width * trailWidthScl;
        trailLength = Mathf.round(height * trailLengthScl);
        trailColor = backColor;
    }

    @Override
    public void drawTrail(Bullet b) {
        if (trailLength <= 0 || b.trail == null) return;

        float z = Draw.z();
        Draw.z(trailLayer > 0 ? trailLayer : z - trailLayerOffset);
        b.trail.draw(trailColor, trailWidth);
        Draw.z(z);
    }

    /**
     * Leaves a fading ghost trail when the bullet expires or hits something.
     */
    @Override
    public void removed(Bullet b) {
        if (trailLength > 0 && b.trail != null && b.trail.size() > 0) {
            ShootingFx.trailFade.at(b.x, b.y, trailWidth, trailColor, b.trail.copy());
        }
    }
}