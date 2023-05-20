package UAW.world.blocks.liquid;

import UAW.audiovisual.UAWFx;
import UAW.content.blocks.UAWBlocksLogistic;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.type.Liquid;
import mindustry.world.blocks.liquid.Conduit;

import static mindustry.Vars.tilesize;

public class PrzConduit extends Conduit {
	public float explosionDamage = 25f;
	public float maxTemperature = 6f;
	public float maxFlammability = 3f;

	public boolean stoutsteel = true;

	public PrzConduit(String name) {
		super(name);
		botColor = Color.valueOf("4a4b53");
		health = 350;
		liquidCapacity = 30f;
		liquidPressure = 1.2f;
		leaks = false;
		placeableLiquid = true;
		underBullets = true;

		hasPower = true;
		conductivePower = true;

		breakEffect = UAWFx.breakBlockPhlog;
	}

	@Override
	public void init() {
		super.init();
		if (stoutsteel) {
			junctionReplacement = UAWBlocksLogistic.stoutsteelLiquidJunction;
			rotBridgeReplacement = UAWBlocksLogistic.stoutsteelLiquidBridge;
		}
	}

	public class PrzConduitBuild extends ConduitBuild {

		@Override
		public void onDestroyed() {
			super.onDestroyed();
			Sounds.explosion.at(this);
			Damage.damage(x, y, 4 * tilesize, explosionDamage);
			Effect.shake(3f, 3, x, y);
			Fx.plasticExplosion.at(x, y);
		}

		@Override
		public float moveLiquid(Building next, Liquid liquid) {
			if (next == null) return 0;

			next = next.getLiquidDestination(self(), liquid);

			if (next.team == team && next.block.hasLiquids && liquids.get(liquid) > 0f) {
				float ofract = next.liquids.get(liquid) / next.block.liquidCapacity;
				float fract = liquids.get(liquid) / block.liquidCapacity * block.liquidPressure;
				float flow = Math.min(Mathf.clamp((fract - ofract)) * (block.liquidCapacity), liquids.get(liquid));
				flow = Math.min(flow, next.block.liquidCapacity - next.liquids.get(liquid));

				if (flow > 0f && ofract <= fract && next.acceptLiquid(self(), liquid)) {
					next.handleLiquid(self(), liquid, flow);
					liquids.remove(liquid, flow);
					return flow;
				} else if (!next.block.consumesLiquid(liquid) && next.liquids.currentAmount() / next.block.liquidCapacity > 0.1f && fract > 0.1f) {
					float fx = (x + next.x) / 2f, fy = (y + next.y) / 2f;

					Liquid other = next.liquids.current();
					if (other.blockReactive && liquid.blockReactive) {
						if ((other.flammability > maxFlammability && liquid.temperature > maxTemperature) || (liquid.flammability > maxFlammability && other.temperature > maxTemperature)) {
							damageContinuous(1);
							next.damageContinuous(1);
							if (Mathf.chanceDelta(0.1)) {
								Fx.fire.at(fx, fy);
							}
						} else if ((liquid.temperature > maxTemperature && other.temperature < 0.55f) || (other.temperature > maxTemperature && liquid.temperature < 0.55f)) {
							liquids.remove(liquid, Math.min(liquids.get(liquid), 0.7f * Time.delta));
							if (Mathf.chanceDelta(0.2f)) {
								Fx.steam.at(fx, fy);
							}
						}
					}
				}
			}
			return 0;
		}

	}
}


