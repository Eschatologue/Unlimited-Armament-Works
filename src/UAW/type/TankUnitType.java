
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
        mechStride = mechFrontSway = mechSideSway = 0f;
        mechStepShake = 0.3f;
        mechStepParticles = true;
        canDrown = true;
    }
    @Override
    public void draw(Unit unit) {
        if(unit instanceof Mechc){
            drawTank(unit);
        }
        drawOutline(unit);
        drawWeaponOutlines(unit);
        if(engineSize > 0) drawEngine(unit);
        if(drawCell) drawCell(unit);
        drawWeapons(unit);
        if(drawItems) drawItems(unit);
        drawLight(unit);
    }
    public void drawTank(Unit unit){
        applyColor(unit);
        Draw.z(Layer.groundUnit - 0.1f);
        Draw.rect(region, unit.x, unit.y, unit.rotation - 90);
        Draw.reset();
    }
    public void update(Unit unit){
        unit.drownTime -= unit.drownTime / 2f;
        if(unit.hasEffect(StatusEffects.melting) ) {
            drag += drag / -0.5f;
        }
    }
}

