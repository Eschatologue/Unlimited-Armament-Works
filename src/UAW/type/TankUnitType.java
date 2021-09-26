
package UAW.type;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.ObjectSet;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.UnitType;
import mindustry.world.blocks.environment.*;

// Basically Mech with overrided draw method
public class TankUnitType extends UnitType {
    public float trailIntensity = 0.4f;
    public TankUnitType(String name) {
        super(name);
        immunities = ObjectSet.with(StatusEffects.disarmed, StatusEffects.slow, StatusEffects.freezing);
        flying = false;
        constructor = MechUnit::create;
        mechStride = mechFrontSway = mechSideSway = 0f;
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
        Color floorColor = floor.mapColor;
        super.update(unit);
        // Weak against fire
        if(unit.hasEffect(StatusEffects.melting) || unit.hasEffect(StatusEffects.burning) ) {
            unit.reloadMultiplier = 0.5f;
            unit.speedMultiplier = 0.5f;
            unit.healthMultiplier = 0.8f;
        }
        // Trail Effect
        if(Mathf.chanceDelta(trailIntensity) && !floor.isLiquid && unit.moving()){
            Fx.unitLand.at(unit.x , unit.y, unit.hitSize / 14, floorColor);
        }
    }
}


