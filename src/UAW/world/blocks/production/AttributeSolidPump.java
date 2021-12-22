package UAW.world.blocks.production;

import UAW.graphics.UAWFxD;
import arc.Core;
import arc.graphics.g2d.*;
import mindustry.content.Liquids;
import mindustry.graphics.Drawf;
import mindustry.type.*;
import mindustry.world.blocks.production.AttributeCrafter;
import mindustry.world.meta.*;

public class AttributeSolidPump extends AttributeCrafter {
	public Liquid liquidResult = Liquids.oil;
	public TextureRegion region, rotatorRegion, liquidRegion, heatRegion, topRegion;
	public float rotateSpeed = -1.5f;
	public float pumpTime = craftTime = 5.5f;
	public float pumpAmount = 1.5f;

	public AttributeSolidPump(String name) {
		super(name);
		placeableLiquid = true;
		hasPower = true;
		attribute = Attribute.oil;
		outputsLiquid = true;
		outputLiquid = new LiquidStack(liquidResult, pumpAmount);
		warmupSpeed = 0.015f;
		updateEffectChance = 0.3f;
		envEnabled = Env.terrestrial;
		squareSprite = false;
	}

	@Override
	public void load() {
		region = Core.atlas.find(name);
		liquidRegion = Core.atlas.find(name + "-liquidResult");
		rotatorRegion = Core.atlas.find(name + "-rotator");
		heatRegion = Core.atlas.find(name + "-heat");
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
		stats.add(Stat.output, liquidResult, 60f * pumpAmount, true);
		if (attribute != null) {
			stats.add(baseEfficiency > 0.0001f ? Stat.affinities : Stat.tiles, attribute, floating, 1f, baseEfficiency <= 0.001f);
		}
	}

	public class AttributeResourceBuild extends AttributeCrafterBuild {
		public float gather;

		@Override
		public void updateTile() {
			super.updateTile();
			updateEffect = UAWFxD.statusHit(30f, liquidResult.color);
			gather += warmup * edelta();
		}

		@Override
		public void draw() {
			Draw.rect(region, x, y);
			super.drawCracks();
			if (liquidRegion.found()) {
				Drawf.liquid(liquidRegion, x, y, liquids.get(liquidResult) / liquidCapacity, liquidResult.color);
			}
			Drawf.spinSprite(rotatorRegion, x, y, (gather * rotateSpeed));
			Draw.rect(topRegion, x, y);
		}
	}
}
