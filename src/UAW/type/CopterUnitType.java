package UAW.type;

import UAW.ai.types.CopterAI;
import arc.graphics.g2d.Draw;
import arc.math.*;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.gen.*;
import mindustry.type.UnitType;

public class CopterUnitType extends UnitType {
    public final Seq<Rotor> rotors = new Seq<>();
    public float spinningFallSpeed = 0;
    public float fallSmokeX = 0f, fallSmokeY = -5f, fallSmokeChance = 0.5f;

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
        float rx = unit.x + Angles.trnsx(unit.rotation - 90, fallSmokeX, fallSmokeY);
        float ry = unit.y + Angles.trnsy(unit.rotation - 90, fallSmokeX, fallSmokeY);
        super.update(unit);
        if (unit.isFlying() && spinningFallSpeed > 0 || unit.dead() || unit.health < 0) {
            if (unit.dead() || unit.health < 0) {
                unitFallSpin = Time.delta * spinningFallSpeed;
                unit.rotation += unitFallSpin;
                if (Mathf.chanceDelta(fallSmokeChance)) {
                    Fx.fallSmoke.at(rx, ry);
                }
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




