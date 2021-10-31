package UAW.type;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Time;
import mindustry.gen.Unit;

public class Rotor {
    public final String name;
    public TextureRegion bladeRegion, bladeOutlineRegion, topRegion, topRegionOutline;

    public float x = 0f;
    public float y = 0f;
    public float rotationSpeed = 12;
    public float rotorDeathSpeedScl = 2f;
    public boolean drawRotorTop = true;

    public int bladeCount = 4;

    public Rotor(String name) {
        this.name = name;
    }

    public void load() {
        bladeRegion = Core.atlas.find(name);
        bladeOutlineRegion = Core.atlas.find(name + "-outline");
        topRegion = Core.atlas.find(name + "-top");
        topRegionOutline = Core.atlas.find(name + "-top-outline");
    }

    public void draw(Unit unit) {
        float rotorSpeedScl = 1;
        float rx = unit.x + Angles.trnsx(unit.rotation - 90, x, y);
        float ry = unit.y + Angles.trnsy(unit.rotation - 90, x, y);

        if (unit.health() < 0 || unit.dead) {
            rotorSpeedScl = Mathf.approachDelta(1,0.4f, unit.type().fallSpeed);
        }
        for (int i = 0; i < bladeCount; i++) {
            float angle = ((i * 360f / bladeCount + (((Time.time * (rotationSpeed * rotorSpeedScl)))) % 360));
            Draw.rect(bladeOutlineRegion, rx, ry, bladeOutlineRegion.width * Draw.scl, bladeOutlineRegion.height * Draw.scl, angle);
            Draw.mixcol(Color.white, unit.hitTime);
            Draw.rect(bladeRegion, rx, ry, bladeRegion.width * Draw.scl, bladeRegion.height * Draw.scl, angle);
        }
        if (drawRotorTop) {
            Draw.rect(topRegionOutline, rx, ry, topRegionOutline.width * Draw.scl, topRegionOutline.height * Draw.scl, unit.rotation - 90);
            Draw.mixcol(Color.white, unit.hitTime);
            Draw.rect(topRegion, rx, ry, topRegion.width * Draw.scl, topRegion.height * Draw.scl, unit.rotation - 90);
        }
    }
}
