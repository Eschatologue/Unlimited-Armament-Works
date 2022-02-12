package UAW.world.blocks.liquid;

import UAW.content.UAWBlocks;
import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.type.Liquid;
import mindustry.world.*;
import mindustry.world.blocks.liquid.Conduit;

import static mindustry.Vars.tilesize;

public class PressurizedConduit extends Conduit {
	public float explosionDamage = 45f;
	boolean armorMode = false;

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

	}
}

