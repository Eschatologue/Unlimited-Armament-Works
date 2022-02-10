package UAW.world.blocks.liquid;

import UAW.content.UAWBlocks;
import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.gen.Sounds;
import mindustry.world.blocks.liquid.Conduit;

import static mindustry.Vars.tilesize;

public class PressurizedConduit extends Conduit {
	public float explosionDamage = 45f;

	public PressurizedConduit(String name) {
		super(name);
		botColor = Color.valueOf("4a4b53");
		health = 300;
		liquidCapacity = 30f;
		liquidPressure = 2f;
		leaks = false;
		placeableLiquid = true;
	}

	@Override
	public void init() {
		super.init();
		junctionReplacement = UAWBlocks.pressurizedLiquidJunction;
		bridgeReplacement = UAWBlocks.pressurizedLiquidBridge;
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
	}
}

