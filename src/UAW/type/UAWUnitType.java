/*
package UAW.type;

import arc.*;
import arc.audio.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.TextureAtlas.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.ai.types.*;
import mindustry.content.*;
import mindustry.core.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.units.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.graphics.MultiPacker.*;
import mindustry.type.UnitType;
import mindustry.type.ammo.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class UAWUnitType extends UnitType {
    public int rotorCount = 1;
    public int rotorBladeCount = 3;
    public TextureRegion rotorRegion, rotorTopRegion, hullRegion;

    public UAWUnitType(String name){
        super(name);
    }

    @Override
    public void init(){
        super.init();
    }

    @Override
    public void load(){
        super.load();
        rotorRegion = Core.atlas.find(name + "-rotor");
        rotorTopRegion = Core.atlas.find(name + "-rotor-top");
        hullRegion = Core.atlas.find(name);
    }

    @Override
    public void draw(Unit unit){
        super.draw(unit);
        Tankc tank = unit instanceof Tankc ? (Tankc)unit : null;

        float z = unit.elevation > 0.5f ? (lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f);

        Draw.z(z - 0.03f);

        if(tank != null){
            drawTank(tank);
        }
    }
    public void drawTank(Tankc tank){
        Unit unit = (Unit)tank;

        Draw.reset();

        float e = unit.elevation;

        Floor floor = unit.isFlying() ? Blocks.air.asFloor() : unit.floorOn();
        if(floor.isLiquid){
            Draw.color(Color.white, floor.mapColor, 0.5f);
        }
        Draw.mixcol(Color.white, unit.hitTime);
        if(floor.isLiquid){
            Draw.color(Color.white, floor.mapColor, unit.drownTime() * 0.4f);
        }else{
            Draw.color(Color.white);
        }
        Draw.mixcol();
    }
    }
}
*/