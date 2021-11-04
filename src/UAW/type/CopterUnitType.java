package UAW.type;

import UAW.ai.types.CopterAI;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.*;
import arc.util.Time;
import mindustry.gen.*;
import mindustry.type.*;

public class CopterUnitType extends UnitType {
    public final Seq<Rotor> rotors = new Seq<>();
    public boolean spinningFall = false;
    public float spinningFallSpeed = 0;

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
        if (unit.isFlying() && spinningFallSpeed > 0) {
            if (unit.dead() || unit.health < 0) {
                unitFallSpin = Time.delta * spinningFallSpeed;
                unit.rotation += unitFallSpin;
            }
        }
        if (unit.dead() || unit.health < 0) {
            for(Rotor rotor : rotors){
                rotor.rotorSpeed = Mathf.lerpDelta(rotor.rotorSpeed, 0, spinningFallSpeed);
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




