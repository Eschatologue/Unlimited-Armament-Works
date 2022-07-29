package UAW.world.blocks.power;

import UAW.world.drawer.DrawHeatOutputTop;
import mindustry.entities.TargetPriority;
import mindustry.world.blocks.heat.HeatConductor;
import mindustry.world.draw.*;
import mindustry.world.meta.BlockGroup;

/** TODO */
public class HeatConduit extends HeatConductor {

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

		regionRotated1 = 1;
		priority = TargetPriority.transport;
		group = BlockGroup.transportation;

		drawer = new DrawMulti(
			new DrawRegion("-bottom"),
			new DrawHeatOutputTop(),
			new DrawHeatInput("-heat"),
			new DrawDefault(),
			new DrawRegion("-glass")
		);
	}

	@Override
	public void load() {
		super.load();
	}

}
