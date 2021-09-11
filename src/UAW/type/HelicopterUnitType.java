package UAW.type;

import UAW.ai.types.CopterAI;
import arc.graphics.g2d.Draw;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;

public class HelicopterUnitType extends UnitType {
    public final Seq<Rotor> rotors = new Seq<>();
    public boolean spinningFall = false;

    public HelicopterUnitType(String name) {
        super(name);
        flying = lowAltitude = true;
        constructor = UnitEntity::create;
        engineSize = 0f;
        rotateSpeed = 6f;
        defaultController = CopterAI::new;
        fallSpeed = 0.008f;
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);
        if(unit.isFlying() && spinningFall) {
            if(unit.health <= 0 || unit.dead()) {
                unit.rotation += Time.delta * (fallSpeed * 1500);
            }
        }
    }
    @Override
    public void draw(Unit unit){
        super.draw(unit);
        drawRotor(unit);
    }

    public void drawRotor(Unit unit){
        applyColor(unit);
        rotors.each(rotor -> rotor.draw(unit));
        Draw.reset();
    }

    @Override
    public void load(){
        super.load();
        rotors.each(Rotor::load);
    }
}




