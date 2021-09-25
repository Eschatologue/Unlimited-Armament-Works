
package UAW.type;

import arc.graphics.g2d.Draw;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;

public class TankUnitType extends UnitType {
    public TankUnitType(String name) {
        super(name);
        flying = false;
        constructor = MechUnit::create;
        mechFrontSway = mechSideSway = 0f;
        mechStride = 0f;
        canDrown = true;
    }

    @Override
    public void draw(Unit unit) {
        super.draw(unit);
        if(unit instanceof Mechc){
            drawTank(unit);
        }
    }
    public void drawTank(Unit unit){
        applyColor(unit);
        Draw.z(Layer.groundUnit - 0.1f);
        Draw.rect(region, unit.x, unit.y, unit.rotation - 90);
        Draw.reset();
    }

}

