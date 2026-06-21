package uaw.world.blocks.defense.turrets;

import arc.struct.OrderedMap;
import mindustry.entities.bullet.BulletType;
import mindustry.type.Item;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.meta.Stat;
import uaw.world.meta.UAWAmmoListValue;

/** Base class for all UAW item turrets. */
public class UAWItemTurret extends ItemTurret {

    public UAWItemTurret(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.remove(Stat.ammo);
        stats.add(Stat.ammo, new UAWAmmoListValue((OrderedMap<Item, BulletType>) ammoTypes, name));
    }
}