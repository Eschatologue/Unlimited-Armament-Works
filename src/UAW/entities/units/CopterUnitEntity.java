package UAW.entities.units;

import UAW.ai.types.CopterAI;
import UAW.type.Rotor;
import arc.graphics.g2d.Draw;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;

import static mindustry.gen.Nulls.unit;

public class CopterUnitEntity extends UnitEntity {

    @Override
    public void update() {
        super.update();
        if(isFlying()) {
            if(health <= 0 || unit.dead()) {
                rotation += Time.delta * 6;
            }
        }
    }
}
