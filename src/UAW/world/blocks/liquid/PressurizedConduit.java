package UAW.world.blocks.liquid;

import UAW.content.UAWBlocks;
import arc.graphics.Color;
import arc.scene.ui.layout.Table;
import arc.util.Log;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.world.blocks.liquid.Conduit;

import static mindustry.Vars.tilesize;

public class PressurizedConduit extends Conduit {
	public float explosionDamage = 45f;
	public boolean armorMode;

	public PressurizedConduit(String name) {
		super(name);
		botColor = Color.valueOf("4a4b53");
		health = 300;
		liquidCapacity = 30f;
		liquidPressure = 2.5f;
		leaks = false;
		placeableLiquid = true;
		configurable = true;
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

		@Override
		public void buildConfiguration(Table table) {
			table.button(Icon.cancel, () -> {
				armorMode = false;
				deselect();
			}).size(40f);
			table.button(Icon.terminal, () -> {
				armorMode = true;
				deselect();
			}).size(40f);
		}

		@Override
		public void update() {
			super.update();
			if (armorMode) {
				Log.info("Armor mode On");
			} else Log.info("Armor mode off");
		}
	}
}

