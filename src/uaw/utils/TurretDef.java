package uaw.utils;

import mindustry.world.blocks.defense.turrets.ItemTurret;

import static uaw.Vars.tick;

/**
 * Static helper methods that apply named groups of fields to a turret.
 */
public class TurretDef {

    public static void generalParams(ItemTurret t, int size) {
        generalParams(t, size, size * 75);
    }

    public static void generalParams(ItemTurret t, int size, float scaledHealth) {
        generalParams(t, size, scaledHealth, size * 25);
    }

    /**
     * Used to standardised all UAW turrets
     */
    public static void generalParams(ItemTurret t, int size, float scaledHealth, int maxAmmo) {
        t.size = size;
        t.scaledHealth = size * scaledHealth;
        t.maxAmmo = maxAmmo;
        t.buildTime = size * tick * 1.5f;
        t.squareSprite = false;
    }
}
