package UAW.world.units;

import UAW.audiovisual.UAWDrawf;
import arc.graphics.g2d.Draw;
import mindustry.graphics.*;
import mindustry.world.blocks.units.UnitFactory;

public class UnitConstructor extends UnitFactory {
	public UnitConstructor(String name) {
		super(name);
	}

	public class UnitConstructorBuild extends UnitFactoryBuild {

		@Override
		public void draw(){
			Draw.rect(region, x, y);
			Draw.rect(outRegion, x, y, rotdeg());

			if(currentPlan != -1){
				UnitPlan plan = plans.get(currentPlan);
				Draw.draw(Layer.blockOver, () -> UAWDrawf.unitConstruct(this, plan.unit, rotdeg() - 90f, progress / plan.time, speedScl, time));
			}

			Draw.z(Layer.blockOver);

			payRotation = rotdeg();
			drawPayload();

			Draw.z(Layer.blockOver + 0.1f);

			Draw.rect(topRegion, x, y);
		}
	}
}
