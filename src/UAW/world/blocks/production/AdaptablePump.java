package UAW.world.blocks.production;

import UAW.graphics.UAWFxD;
import arc.Core;
import arc.graphics.g2d.*;
import arc.util.Time;
import mindustry.content.Liquids;
import mindustry.graphics.Drawf;
import mindustry.type.*;
import mindustry.world.blocks.production.AttributeCrafter;
import mindustry.world.meta.*;

public class AdaptablePump extends AttributeCrafter {
	public TextureRegion region, rotatorRegion, liquidRegion, topRegion;
	public Liquid result = Liquids.oil;
	public float rotateSpeed = 1.5f;
	public float pumpTime = craftTime = 5.5f;
	public float pumpAmount = 1.5f;

	public AdaptablePump(String name) {
		super(name);
		placeableLiquid = true;
		hasPower = true;
		hasLiquids = true;
		hasItems = false;
		attribute = Attribute.oil;
		outputLiquid = new LiquidStack(result, pumpAmount);
		warmupSpeed = 0.5f;
		squareSprite = false;
		updateEffectChance = 0.5f;
	}

	@Override
	public void load() {
		region = Core.atlas.find(name);
		liquidRegion = Core.atlas.find(name + "-liquid");
		rotatorRegion = Core.atlas.find(name + "-rotator");
		topRegion = Core.atlas.find(name + "-top");
	}

	@Override
	public TextureRegion[] icons() {
		return new TextureRegion[]{region, rotatorRegion, topRegion};
	}

	@Override
	public void setStats() {
		super.setStats();

		stats.remove(Stat.output);
		stats.add(Stat.output, result, 60f * pumpAmount, true);
		if (attribute != null) {
			stats.add(baseEfficiency > 0.0001f ? Stat.affinities : Stat.tiles, attribute, floating, 1f, baseEfficiency <= 0.001f);
		}
	}

	public class AdaptablePumpBuild extends AttributeCrafterBuild {
		@Override
		public void updateTile() {
			super.updateTile();
			updateEffect = UAWFxD.statusHit(15f, result.color);
		}

		@Override
		public void draw() {
			Draw.rect(region, x, y);
			super.drawCracks();
			if (liquidRegion.found()) {
				Drawf.liquid(liquidRegion, x, y, liquids.get(result) / liquidCapacity, result.color);
			}
			Draw.rect(rotatorRegion, x, y, Time.time * (warmup * rotateSpeed));
			Draw.rect(topRegion, x, y);
		}
	}
}
