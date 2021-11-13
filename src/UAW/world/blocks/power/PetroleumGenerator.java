package UAW.world.blocks.power;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.Drawf;
import mindustry.world.blocks.power.ImpactReactor;

import static mindustry.Vars.tilesize;

public class PetroleumGenerator extends ImpactReactor {
	public TextureRegion liquidRegion, heatRegion, topRegion;
	public Effect smokeEffect = new MultiEffect(Fx.melting, Fx.burning, Fx.fireSmoke);

	public PetroleumGenerator(String name) {
		super(name);
		warmupSpeed = 0.05f;
		hasItems = false;
	}

	@Override
	public void load() {
		bottomRegion = Core.atlas.find(name + "-bottom");
		liquidRegion = Core.atlas.find(name + "-liquid");
		topRegion = Core.atlas.find(name + "-top");
	}

	@Override
	public TextureRegion[] icons() {
		return new TextureRegion[]{bottomRegion, topRegion};
	}

	public class PetroleumGeneratorBuild extends ImpactReactorBuild {
		@Override
		public void updateTile() {
			super.updateTile();
			if (warmup >= 0.001) {
				if (Mathf.chance(warmup / 8)) {
					smokeEffect.at(x, y);
				}
			}
		}

		@Override
		public void draw() {
			float r = size * tilesize;
			Draw.rect(bottomRegion, x, y);
			Drawf.liquid(liquidRegion, x, y, liquids.total() / liquidCapacity, liquids.current().color);
			Draw.rect(topRegion, x, y);
			Draw.color(Color.valueOf("ff9b59"));
			Draw.alpha((0.3f + Mathf.absin(Time.time, 2f, 0.05f)) * warmup);
			Draw.rect(heatRegion, x, y, r, r);

			Draw.reset();
		}
	}
}
