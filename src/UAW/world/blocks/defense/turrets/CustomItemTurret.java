package UAW.world.blocks.defense.turrets;

import UAW.graphics.UAWPal;
import UAW.world.meta.UAWStatValues;
import mindustry.graphics.Drawf;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.meta.Stat;

public class CustomItemTurret extends ItemTurret {
    public CustomItemTurret(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.remove(Stat.ammo);
        stats.add(Stat.ammo, UAWStatValues.ammo(ammoTypes));
    }

    public class CustomItemTurretBuild extends ItemTurretBuild {
        @Override
        public void drawSelect() {
            super.drawSelect();
            if (minRange > 0) {
                Drawf.dashCircle(x, y, minRange, UAWPal.darkPyraBloom);
            }
        }
    }
}
