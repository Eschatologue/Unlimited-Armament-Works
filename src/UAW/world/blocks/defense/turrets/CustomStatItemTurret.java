package UAW.world.blocks.defense.turrets;

import UAW.world.meta.UAWStatValues;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.meta.Stat;

public class CustomStatItemTurret extends ItemTurret {
    public CustomStatItemTurret(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.remove(Stat.ammo);
        stats.add(Stat.ammo, UAWStatValues.ammo(ammoTypes));
    }
}
