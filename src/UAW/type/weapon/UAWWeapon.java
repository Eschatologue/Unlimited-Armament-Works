package UAW.type.weapon;

import UAW.world.meta.UAWStatValues;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.util.Strings;
import mindustry.type.*;
import mindustry.world.meta.*;

public class UAWWeapon extends Weapon {
	public boolean customStat = true;

	public UAWWeapon(String name) {
		super(name);
	}

	@Override
	public void addStats(UnitType u, Table t) {
		if (inaccuracy > 0) {
			t.row();
			t.add("[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int) inaccuracy + " " + StatUnit.degrees.localized());
		}
		if (!alwaysContinuous && reload > 0) {
			t.row();
			t.add("[lightgray]" + Stat.reload.localized() + ": " + (mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / reload * shoot.shots, 2) + " " + StatUnit.perSecond.localized());
		}

		if (customStat) {
			UAWStatValues.ammo(ObjectMap.of(u, bullet)).display(t);
		} else StatValues.ammo(ObjectMap.of(u, bullet)).display(t);
	}
}
