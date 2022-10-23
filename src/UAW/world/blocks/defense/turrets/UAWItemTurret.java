package UAW.world.blocks.defense.turrets;

import UAW.world.meta.UAWStatValues;
import arc.graphics.Color;
import mindustry.graphics.*;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.meta.Stat;

import static mindustry.Vars.tilesize;

/**
 * Modified version of the vanilla item turret
 * <p>
 * Displays minimum range when selected
 * </p>
 */
public class UAWItemTurret extends ItemTurret {
	public Color minRangeColor = Pal.lightishOrange;

	public boolean showMinRange = true;

	public UAWItemTurret(String name) {
		super(name);
		squareSprite = false;
	}

	public void minRangeDraw(float x, float y, int type) {
		if (minRange > 0 && showMinRange) {
			if (type == 1) Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, minRange, minRangeColor);
			if (type == 2) Drawf.dashCircle(x, y, minRange, minRangeColor);
		}

	}

	@Override
	public void setStats() {
		super.setStats();
		stats.remove(Stat.ammo);
		stats.add(Stat.ammo, UAWStatValues.ammo(ammoTypes));
	}

	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		super.drawPlace(x, y, rotation, valid);
		minRangeDraw(x, y, 1);
	}

	public class UAWItemTurretBuild extends ItemTurretBuild {
		@Override
		public void drawSelect() {
			super.drawSelect();
			minRangeDraw(x, y, 2);
		}
	}
}
