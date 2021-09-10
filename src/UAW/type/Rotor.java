package UAW.type;

import arc.*;
import arc.audio.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class Rotor {
    public String name = "";
    public TextureRegion bladeRegion;
    public TextureRegion topRegion;
    public int bladeCount = 4;
    public float rotationSpeed = 12;
    public float x = 0f, y = 0f;

    public Rotor(String name){
        this.name = name;
    }
    public Rotor(){
        this("");
    }

    public void load(){
        bladeRegion = Core.atlas.find(name);
        topRegion = Core.atlas.find(name + "-top");
    }

    public void draw(Unit unit){
        float rx = unit.x + Angles.trnsx(unit.rotation - 90, x, y);
        float ry = unit.y + Angles.trnsy(unit.rotation - 90, x, y);

        for(int i = 0; i < bladeCount; i++){
            float angle = ((i * 360f / bladeCount + (Time.time * rotationSpeed)) % 360);
            Draw.rect(bladeRegion, rx, ry, angle);
        }
        Draw.rect(topRegion, rx, ry, unit.rotation - 90);
    }
}
