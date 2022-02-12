package UAW.world.blocks.liquid;

import UAW.content.UAWBlocks;
import arc.graphics.Color;
import arc.scene.ui.layout.Table;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.type.Liquid;
import mindustry.world.blocks.liquid.Conduit;

import static mindustry.Vars.tilesize;

public class PressurizedConduit extends Conduit {
	public float explosionDamage = 45f;


	public PressurizedConduit(String name) {
		super(name);
		botColor = Color.valueOf("4a4b53");
		health = 350;
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
		public boolean armorMode = false;

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
				armorMode = true;
				deselect();
			}).size(50f);
			table.button(Icon.upload, () -> {
				armorMode = false;
				deselect();
			}).size(50f);
		}

		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			if (armorMode) {
				return super.acceptLiquid(source, liquid) && (tile == null || source.block instanceof Conduit ||
					source.tile.absoluteRelativeTo(tile.x, tile.y) == rotation);
			} else {
				noSleep();
				return (liquids.current() == liquid || liquids.currentAmount() < 0.2f)
					&& (tile == null || (source.relativeTo(tile.x, tile.y) + 2) % 4 != rotation);
			}
		}
	}
}

