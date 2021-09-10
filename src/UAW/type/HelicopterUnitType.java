package UAW.type;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import arc.struct.Seq;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.*;
import mindustry.ai.types.*;
import mindustry.graphics.Layer;
import mindustry.graphics.*;
import mindustry.type.UnitType;

import UAW.ai.types.*;

//Possible thanks to iarkn#8872 help
public class HelicopterUnitType extends UnitType {
    public Seq<Rotor> rotors = new Seq<>();

    public HelicopterUnitType(String name) {
        super(name);
        flying = lowAltitude = true;
        constructor = UnitEntity::create;
        engineSize = 0f;
        rotateSpeed = 7f;
        defaultController = CopterAI::new;
        fallSpeed = 0.008f;
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);
        if(unit.health <= 0 || unit.dead()) {
            unit.rotation += Time.delta * (fallSpeed * 1000);
        }
    }
}




