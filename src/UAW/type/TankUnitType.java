
package UAW.type;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.ObjectSet;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.StatusEffects;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.UnitType;
import mindustry.world.blocks.environment.*;

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
    @Override
    public void update(Unit unit){
        Floor floor = Vars.world.floorWorld(unit.x, unit.y);
        super.update(unit);
        unit.drownTime = Mathf.lerpDelta(Time.delta / floor.drownTime, 0f, 0.03f);
        if(unit.hasEffect(StatusEffects.melting) || unit.hasEffect(StatusEffects.burning) ) {
            unit.reloadMultiplier = 0.5f;
            unit.speedMultiplier = 0.5f;
            unit.healthMultiplier = 0.8f;
        }
    }
}


