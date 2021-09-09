package UAW.type;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.*;
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
    public float rotorOffsetX = 0f;
    public float rotorOffsetY = 0f;
    public float rotorSpeed = 15;

    public int rotorCount = 1;
    public int bladeCount = 3;

    float rotorFallSpeedModifier = 1f;

    public TextureRegion bladeRegion, topRegion, rotorShadowRegion;

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
    public void load(){
        super.load();
        bladeRegion = Core.atlas.find(name + "-blade");
        topRegion = Core.atlas.find(name + "-top");
    }

    @Override
    public void draw(Unit unit) {
        super.draw(unit);
        Draw.z(Layer.flyingUnit + 0.001f);
        drawRotor(unit);
    }

    @Override
    public void update(Unit unit) {
        rotorFallSpeedModifier = 1f;
        super.update(unit);
        if(unit.health <= 0 || unit.dead()) {
            unit.rotation += Time.delta * (fallSpeed * 1000);
            rotorFallSpeedModifier = 0.5f;
        }
    }

    public void drawRotor(Unit unit){
        float rx = unit.x + Angles.trnsx(unit.rotation - 90, rotorOffsetX, rotorOffsetY);
        float ry = unit.y + Angles.trnsy(unit.rotation - 90, rotorOffsetX, rotorOffsetY);

        for(int j = 0; j < bladeCount; j++){
            float angle = ((j * 360f / bladeCount + (Time.time * -(rotorSpeed * rotorFallSpeedModifier))) % 360);
            Draw.rect(bladeRegion, rx, ry, angle);
        }
        Draw.rect(topRegion, rx, ry, unit.rotation - 90);
    }
}




