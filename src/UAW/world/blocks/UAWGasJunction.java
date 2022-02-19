package UAW.world.blocks;

import arc.Core;
import arc.func.Func;
import arc.graphics.Color;
import arc.math.Mathf;
import gas.gen.GasBuilding;
import gas.type.Gas;
import gas.world.blocks.gas.GasJunction;
import gas.world.consumers.ConsumeGas;
import mindustry.content.Liquids;
import mindustry.core.UI;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.world.consumers.*;
import mindustry.world.meta.Stat;

public class UAWGasJunction extends GasJunction {
	public UAWGasJunction(String name) {
		super(name);
	}

	@Override
	public void setBars() {
		bars.add("health", entity -> new Bar("stat.health", Pal.health, entity::healthf).blink(Color.white));

		if (hasLiquids) {
			Func<Building, Liquid> current;
			if (consumes.has(ConsumeType.liquid) && consumes.get(ConsumeType.liquid) instanceof ConsumeLiquid) {
				Liquid liquid = consumes.<ConsumeLiquid>get(ConsumeType.liquid).liquid;
				current = entity -> liquid;
			} else {
				current = entity -> entity.liquids == null ? Liquids.water : entity.liquids.current();
			}
			bars.add("liquid", entity -> new Bar(() -> entity.liquids.get(current.get(entity)) <= 0.001f ? Core.bundle.get("bar.liquid") : current.get(entity).localizedName,
				() -> current.get(entity).barColor(), () -> entity == null || entity.liquids == null ? 0f : entity.liquids.get(current.get(entity)) / liquidCapacity));
		}

		if (hasPower && consumes.hasPower()) {
			ConsumePower cons = consumes.getPower();
			boolean buffered = cons.buffered;
			float capacity = cons.capacity;

			bars.add("power", entity -> new Bar(() -> buffered ? Core.bundle.format("bar.poweramount", Float.isNaN(entity.power.status * capacity) ? "<ERROR>" : UI.formatAmount((int) (entity.power.status * capacity))) :
				Core.bundle.get("bar.power"), () -> Pal.powerBar, () -> Mathf.zero(cons.requestedPower(entity)) && entity.power.graph.getPowerProduced() + entity.power.graph.getBatteryStored() > 0f ? 1f : entity.power.status));
		}

		if (hasItems && configurable) {
			bars.add("items", entity -> new Bar(() -> Core.bundle.format("bar.items", entity.items.total()), () -> Pal.items, () -> (float) entity.items.total() / itemCapacity));
		}

		if (unitCapModifier != 0) {
			stats.add(Stat.maxUnits, (unitCapModifier < 0 ? "-" : "+") + Math.abs(unitCapModifier));
		}
		if (hasGasses) {
			Func<GasBuilding, Gas> current;
			if (consumes.hasGas() && consumes.getGas() instanceof ConsumeGas) {
				Gas gas = consumes.<ConsumeGas>getGas().gas;
				current = entity -> gas;
			} else {
				current = entity -> entity.gasses == null ? null : entity.gasses.current();
			}
			bars.<GasBuilding>add("gas", entity -> new Bar(() -> entity.gasses.get(current.get(entity)) <= 0.001f ? Core.bundle.get("bar.gas") : current.get(entity).localizedName,
				() -> current.get(entity).barColor(), () -> entity == null || entity.gasses == null ? 0f : entity.gasses.get(current.get(entity)) / gasCapacity));

		}
	}
}
