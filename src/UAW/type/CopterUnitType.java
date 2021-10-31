package UAW.type;

import UAW.ai.types.CopterAI;
import arc.graphics.g2d.Draw;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.gen.*;
import mindustry.type.UnitType;

public class CopterUnitType extends UnitType {
    public final Seq<Rotor> rotors = new Seq<>();
    public boolean spinningFall = false;

    public CopterUnitType(String name) {
        super(name);
        flying = lowAltitude = true;
        constructor = UnitEntity::create;
        engineSize = 0f;
        rotateSpeed = 6f;
        defaultController = CopterAI::new;
        fallSpeed = 0.007f;
    }

    @Override
    public void update(Unit unit) {
        float unitFallSpin;
        super.update(unit);
        if (unit.isFlying() && spinningFall) {
            if (unit.health <= 0 || unit.dead()) {
                unitFallSpin = Time.delta * (fallSpeed * 1200);
                unit.rotation += unitFallSpin;
            }
        }
    }

    @Override
    public void draw(Unit unit) {
        super.draw(unit);
        drawRotor(unit);
    }

    public void drawRotor(Unit unit) {
        applyColor(unit);
        rotors.each(rotor -> rotor.draw(unit));
        Draw.reset();
    }

    @Override
    public void load() {
        super.load();
        rotors.each(Rotor::load);
    }
}




