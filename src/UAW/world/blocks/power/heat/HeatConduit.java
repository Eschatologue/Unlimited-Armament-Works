package UAW.world.blocks.power.heat;

import UAW.world.drawer.DrawHeatOutputTop;
import mindustry.entities.TargetPriority;
import mindustry.world.*;
import mindustry.world.blocks.Autotiler;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.production.HeatCrafter;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

public class HeatConduit extends HeatConductor {
	public boolean hidePowerStat = false;

	public HeatConduit(String name) {
		super(name);
		squareSprite = false;
		update = rotate = true;
		rotateDraw = false;
		solid = false;
		floating = true;
		underBullets = true;
		conveyorPlacement = true;
		noUpdateDisabled = true;
		canOverdrive = false;

		visualMaxHeat = 2f;
		regionRotated1 = 1;
		priority = TargetPriority.transport;
		group = BlockGroup.transportation;

		drawer = new DrawMulti(
			new DrawRegion("-bottom"),
			new DrawHeatOutputTop(),
			new DrawHeatInput("-heat"),
			new DrawDefault()
		);
	}

	@Override
	public void load() {
		super.load();
	}

	@Override
	public void setStats() {
		super.setStats();
		if (hidePowerStat) stats.remove(Stat.powerCapacity);
	}

}
