package uaw.content.vanilla;

import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.UnitSorts;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.LiquidTurret;
import uaw.audiovisual.UAWPal;
import uaw.audiovisual.fx.AreaFx;
import uaw.content.UAWItems;
import uaw.utils.BulletDef;

import static mindustry.Vars.tilesize;

/**
 * Adds extra ammo types to turrets that already exist in the base game
 */
public class VanillaBullets {

    /**
     * Extra distance, in world units, added on top of a turret's range when working out bullet lifetime
     */
    private static final float rangeMargin = 9f;

    public static void load() {
        // region Duo
        BulletType duoCopper = ammoRef(Blocks.duo, Items.copper);
        BulletType duoGraphite = ammoRef(Blocks.duo, Items.graphite);
        BulletType duoSilicon = ammoRef(Blocks.duo, Items.silicon);
        addAmmo(Blocks.duo, UAWItems.ammoBall, new BasicBulletType(duoCopper.speed * 2f, duoGraphite.damage) {{
            BulletDef.size(this, 7);
            BulletDef.color(this, Pal.copperAmmoFront, Pal.copperAmmoBack);
            pierceCap = 2;

            ammoMultiplier = 4;
            rangeChange = 5 * tilesize;
            reloadMultiplier = 1.5f;

            hitEffect = despawnEffect = Fx.hitBulletColor;
            BulletDef.autoTrail(this);
        }});
        addAmmo(Blocks.duo, UAWItems.ammoACG, new BasicBulletType(duoSilicon.speed * 2, duoSilicon.damage * 1.5f) {{
            BulletDef.size(this, 7);
            BulletDef.color(this, Pal.siliconAmmoFront, Pal.siliconAmmoBack);
            pierceCap = 2;
            homingPower = 0.5f;
            hitEffect = despawnEffect = Fx.hitBulletColor;

            ammoMultiplier = 6;
            rangeChange = 5 * tilesize;
            reloadMultiplier = 1.5f;

            BulletDef.autoTrail(this);
        }});

        // endregion Duo

        // region Hail
        BulletType hailGraphite = ammoRef(Blocks.hail, Items.graphite);
        addAmmo(Blocks.hail, UAWItems.shellGP, new ArtilleryBulletType(hailGraphite.speed * 1.5f, hailGraphite.damage * 1.5f) {{
            BulletDef.size(this, 9f);
            BulletDef.color(this, UAWPal.redAmmoFront, UAWPal.redAmmoBack);

            splashDamageRadius = 4 * tilesize;
            splashDamage = damage * 1.5f;

            hitEffect = AreaFx.bulletExplosion(splashDamageRadius, UAWPal.redAmmoFront, UAWPal.redAmmoBack);
            despawnEffect = Fx.hitBulletColor;
            unitSort = UnitSorts.grouped;

            ammoMultiplier = 4;
            rangeChange = 5 * tilesize;

            BulletDef.shellFlightTrail(this);
            BulletDef.autoTrail(this);
        }});
    }

    /**
     * Registers {@code bullet} as ammo for {@code item} on an existing item turret
     * <p>The bullet's lifetime is set automatically so it reaches the turret's full range.</p>
     *
     * @param turret the vanilla turret, for example {@link Blocks#duo}
     * @param item   the item the turret will accept as ammo
     * @param bullet the projectile fired when that item is loaded
     */
    public static void addAmmo(Block turret, Item item, BulletType bullet) {
        if (!(turret instanceof ItemTurret itemTurret)) {
            throw new IllegalArgumentException(turret.name + " is not an ItemTurret, idiot");
        }
        // Bullets default to a short lifetime; without this they vanish before reaching the turret's range.
        bullet.lifetime = (itemTurret.range + bullet.rangeChange + rangeMargin) / bullet.speed;
        itemTurret.ammoTypes.put(item, bullet);
    }

    /**
     * Registers {@code bullet} as ammo for {@code liquid} on an existing liquid turret
     *
     * @param turret the vanilla turret, for example {@link Blocks#wave}
     * @param liquid the liquid the turret will accept as ammo
     * @param bullet the projectile fired when that liquid is loaded
     */
    public static void addAmmo(Block turret, Liquid liquid, BulletType bullet) {
        if (!(turret instanceof LiquidTurret liquidTurret)) {
            throw new IllegalArgumentException(turret.name + " is not a LiquidTurret, idiot");
        }

        liquidTurret.ammoTypes.put(liquid, bullet);
    }

    /**
     * Looks up an existing ammo entry on a turret, so a new bullet can scale off its stats
     *
     * @param turret the turret whose ammo map to read (must be an {@link ItemTurret})
     * @param item   the ammo item whose bullet type to return
     */
    public static BulletType ammoRef(Block turret, Item item) {
        return ((ItemTurret) turret).ammoTypes.get(item);
    }
}