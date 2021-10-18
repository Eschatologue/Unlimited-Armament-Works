package UAW.type;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Time;
import mindustry.gen.Unit;

public class Rotor {
    public final String name;
    public TextureRegion bladeRegion, topRegion;

    public float x = 0f;
    public float y = 0f;
    public float rotationSpeed = 12;

    public int bladeCount = 4;

    public Rotor(String name) {
        this.name = name;
    }

    public void load() {
        bladeRegion = Core.atlas.find(name);
        topRegion = Core.atlas.find(name + "-top");
    }

    public void draw(Unit unit) {
        float RotorSpeedScl = rotationSpeed;
        float rx = unit.x + Angles.trnsx(unit.rotation - 90, x, y);
        float ry = unit.y + Angles.trnsy(unit.rotation - 90, x, y);

        if (unit.health() < 0 || unit.dead) {
            RotorSpeedScl = Mathf.lerpDelta(rotationSpeed, 0f, 0.5f);
        }
        for (int i = 0; i < bladeCount; i++) {
            float angle = ((i * 360f / bladeCount + (Mathf.random(-30, 30)) + (((Time.time * RotorSpeedScl))) % 360));
            Draw.rect(bladeRegion, rx, ry, bladeRegion.width * Draw.scl, bladeRegion.height * Draw.scl, angle);
        }
        Draw.rect(topRegion, rx, ry, topRegion.width * Draw.scl, topRegion.height * Draw.scl, unit.rotation - 90);
    }
}
