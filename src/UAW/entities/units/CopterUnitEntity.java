package UAW.entities.units;

import UAW.type.Rotor;
import arc.graphics.g2d.Draw;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.gen.*;

import static mindustry.gen.Nulls.unit;

public class CopterUnitEntity extends UnitEntity {
	public final Seq<Rotor> rotors = new Seq<>();

	@Override
	public void update() {
		super.update();
		if (isFlying()) {
			if (health <= 0 || unit.dead()) {
				rotation += Time.delta * 6;
			}
		}
	}

	public void draw(Unit unit) {
		super.draw();
		drawRotor(unit);
	}

	public void drawRotor(Unit unit) {
		rotors.each(rotor -> rotor.draw(unit));
		Draw.reset();
	}
}
