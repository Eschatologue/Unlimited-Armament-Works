package UAW.world.blocks.production;

import UAW.graphics.UAWFxD;
import arc.Core;
import arc.graphics.g2d.*;
import mindustry.Vars;
import mindustry.content.Liquids;
import mindustry.graphics.Drawf;
import mindustry.type.*;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.production.AttributeCrafter;
import mindustry.world.meta.*;

public class AttributePump extends AttributeCrafter {
	public TextureRegion region, rotatorRegion, liquidRegion, topRegion;
	public Liquid result = Liquids.oil;
	public float rotateSpeed = -1.5f;
	public float pumpTime = craftTime = 5.5f;
	public float pumpAmount = 1.5f;
	public float deepLiquidBoost = 1.5f;

	public AttributePump(String name) {
		super(name);
		placeableLiquid = true;
		hasPower = true;
		hasLiquids = true;
		hasItems = false;
		attribute = Attribute.oil;
		outputsLiquid = true;
		outputLiquid = new LiquidStack(result, pumpAmount);
		warmupSpeed = 0.015f;
		updateEffectChance = 0.3f;
		envEnabled = Env.terrestrial;
		squareSprite = false;
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
		public float pump;

		@Override
		public void updateTile() {
			Floor floor = Vars.world.floorWorld(this.x, this.y);
			super.updateTile();
			updateEffect = UAWFxD.statusHit(30f, result.color);
			pump += warmup * edelta();
			if (floor.isDeep() && floor.isLiquid) {
				baseEfficiency += baseEfficiency * deepLiquidBoost;
			}
		}

		@Override
		public void draw() {
			Draw.rect(region, x, y);
			super.drawCracks();
			if (liquidRegion.found()) {
				Drawf.liquid(liquidRegion, x, y, liquids.get(result) / liquidCapacity, result.color);
			}
			Drawf.spinSprite(rotatorRegion, x, y, (pump * rotateSpeed));
			Draw.rect(topRegion, x, y);
		}
	}
}
