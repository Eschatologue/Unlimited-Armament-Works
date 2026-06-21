package uaw.entities.bullet;

import arc.util.Nullable;
import mindustry.entities.bullet.BasicBulletType;

/**
 * Base bullet type for all UAW projectiles.
 *
 * <p>Extends {@link BasicBulletType} with fields that the custom ammo stat
 * table in {@link uaw.world.blocks.defense.turrets.UAWItemTurret} can read.</p>
 */
public class UAWBulletType extends BasicBulletType {

    /**
     * Short label shown inline with the ammo item name in the stat panel.
     *
     * <p>Example: {@code "APFSDS"} renders as {@code "Thorium // APFSDS"}.</p>
     * <p>Null means no label is shown.</p>
     */
    @Nullable
    public String ammoLabel = null;

    public UAWBulletType(float speed, float damage, String bulletSprite) {
        super(speed, damage, bulletSprite);
    }

    public UAWBulletType(float speed, float damage) {
        super(speed, damage);
    }

    public UAWBulletType() {}
}
