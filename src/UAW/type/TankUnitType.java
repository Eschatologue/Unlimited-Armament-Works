
package UAW.type;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.ObjectSet;
import arc.util.Tmp;
import mindustry.content.StatusEffects;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.UnitType;

public class TankUnitType extends UnitType {
    public TankUnitType(String name) {
        super(name);
        immunities = ObjectSet.with(StatusEffects.disarmed, StatusEffects.slow, StatusEffects.freezing);
        flying = false;
        constructor = MechUnit::create;
        mechFrontSway = mechSideSway = 0f;
        mechStepParticles = true;
        canDrown = true;
    }
    @Override
    public void drawMech(Mechc mech){
        Unit unit = (Unit)mech;
        Draw.z(Layer.groundUnit - 0.1f);
        Draw.rect(region, unit.x, unit.y, unit.rotation - 90);
        Draw.reset();
    }

    public void update(Unit unit){
        unit.drownTime += (unit.drownTime / -2f);
        if(unit.hasEffect(StatusEffects.melting) ) {
            drag += drag / -0.5f;
        }
    }
}

