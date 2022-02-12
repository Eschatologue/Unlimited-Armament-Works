package UAW.world.blocks.liquid;

import UAW.content.UAWBlocks;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.type.Liquid;
import mindustry.world.*;
import mindustry.world.blocks.liquid.Conduit;

import static mindustry.Vars.tilesize;

public class PressurizedConduit extends Conduit {
	public float explosionDamage = 45f;
	public float maxTemperature = 6f;
	public float maxFlammability = 3f;

	public PressurizedConduit(String name) {
		super(name);
		botColor = Color.valueOf("4a4b53");
		health = 350;
		liquidCapacity = 30f;
		liquidPressure = 2.5f;
		leaks = false;
		placeableLiquid = true;
	}

	@Override
	public void init() {
		super.init();
		junctionReplacement = UAWBlocks.pressurizedLiquidJunction;
		bridgeReplacement = UAWBlocks.pressurizedLiquidBridge;
	}

	@Override
	public boolean blends(Tile tile, int rotation, int otherX, int othery, int otherrot, Block otherblock) {
		return otherblock.hasLiquids
			&& (otherblock.outputsLiquid || (lookingAt(tile, rotation, otherX, othery, otherblock)))
			&& lookingAtEither(tile, rotation, otherX, othery, otherrot, otherblock)
			&& !(otherblock instanceof Conduit)
			|| otherblock instanceof PressurizedConduit;
	}

	public class PressurizedConduitBuild extends ConduitBuild {

		@Override
		public void onDestroyed() {
			super.onDestroyed();
			Sounds.explosion.at(this);
			Damage.damage(x, y, 4 * tilesize, explosionDamage);
			Effect.shake(3f, 3, x, y);
			Fx.plasticExplosion.at(x, y);
		}

		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			noSleep();
			return (liquids.current() == liquid || liquids.currentAmount() < 0.2f)
				&& (tile == null
				|| (source.relativeTo(tile.x, tile.y) + 2) % 4 != rotation
				&& !(source.block instanceof Conduit)
				|| source.block instanceof PressurizedConduit
			);
		}

		@Override
		public float moveLiquid(Building next, Liquid liquid) {
			if (next != null) {
				next = next.getLiquidDestination(this, liquid);
				if (next.team == this.team && next.block.hasLiquids && this.liquids.get(liquid) > 0.0F) {
					float ofract = next.liquids.get(liquid) / next.block.liquidCapacity;
					float fract = this.liquids.get(liquid) / this.block.liquidCapacity * this.block.liquidPressure;
					float flow = Math.min(Mathf.clamp(fract - ofract) * this.block.liquidCapacity, this.liquids.get(liquid));
					flow = Math.min(flow, next.block.liquidCapacity - next.liquids.get(liquid));
					if (flow > 0.0F && ofract <= fract && next.acceptLiquid(this, liquid)) {
						next.handleLiquid(this, liquid, flow);
						this.liquids.remove(liquid, flow);
						return flow;
					}

					if (next.liquids.currentAmount() / next.block.liquidCapacity > 0.1F && fract > 0.1F) {
						float fx = (this.x + next.x) / 2.0F;
						float fy = (this.y + next.y) / 2.0F;
						Liquid other = next.liquids.current();
						if (other.flammability > maxFlammability && liquid.temperature > maxTemperature || liquid.flammability > maxFlammability && other.temperature > maxTemperature) {
							this.damage(1.0F * Time.delta);
							next.damage(1.0F * Time.delta);
							if (Mathf.chance(0.1D * (double) Time.delta)) {
								Fx.fire.at(fx, fy);
							}
						} else if (liquid.temperature > 0.7F && other.temperature < 0.55F || other.temperature > 0.7F && liquid.temperature < 0.55F) {
							this.liquids.remove(liquid, Math.min(this.liquids.get(liquid), 0.7F * Time.delta));
							if (Mathf.chance((double) (0.2F * Time.delta))) {
								Fx.steam.at(fx, fy);
							}
						}
					}
				}

			}
			return 0.0F;
		}

	}
}

