package UAW.type;

import UAW.ai.types.CopterAI;
import UAW.entities.units.CopterUnitEntity;
import arc.graphics.g2d.Draw;
import arc.struct.Seq;
import mindustry.gen.EntityMapping;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class UAWUnitType extends UnitType {
    public final Seq<Rotor> rotors = new Seq<>();

    public UAWUnitType(String name){
        super(name);
        constructor = EntityMapping.map(this.name);
    }
    public void load(){
        super.load();
        rotors.each(Rotor::load);

    }
    public void draw(Unit unit){
        super.draw(unit);
        if(unit instanceof CopterUnitEntity){
            drawRotor(unit);
            engineSize = 0f;
            rotateSpeed = 6f;
            defaultController = CopterAI::new;
            fallSpeed = 0.007f;
        }
    }
    public void drawRotor(Unit unit){
        applyColor(unit);
        rotors.each(rotor -> rotor.draw(unit));
        Draw.reset();
    }
}
