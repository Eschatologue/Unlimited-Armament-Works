package UAW.type;

import UAW.type.weapon.RecoilingGunWeapon;
import UAW.world.meta.UAWStatValues;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.*;
import arc.struct.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.type.*;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.meta.Stat;

// Basically Mech with overrided draw method
public class TankUnitType extends UnitType {
    public float trailIntensity = 0.4f;
    public float trailOffsetX = 0f, trailOffsetY = 0f;
    public float liquidSpeedMultiplier = 1.2f;
    public boolean engineSmoke = false;
    public float engineOffsetX = 0f, engineOffsetY = 0f;

    public TankUnitType(String name) {
        super(name);
        immunities = ObjectSet.with(StatusEffects.disarmed, StatusEffects.slow, StatusEffects.freezing);
        flying = false;
        constructor = MechUnit::create;
        mechStride = mechFrontSway = mechSideSway = 0f;
    }

    // Modifies drawMech
    @Override
    public void drawMech(Mechc mech) {
        Unit unit = (Unit) mech;
        Draw.z(Layer.groundUnit - 0.1f);
        Draw.rect(region, unit.x, unit.y, unit.rotation - 90);
        Draw.reset();
    }

    @Override
    public void update(Unit unit) {
        float engineSmokeIntensity = trailIntensity;
        Floor floor = Vars.world.floorWorld(unit.x, unit.y);
        Color floorColor = floor.mapColor;
        super.update(unit);
        // Weak against fire
        if (unit.hasEffect(StatusEffects.melting) || unit.hasEffect(StatusEffects.burning)) {
            unit.reloadMultiplier = 0.5f;
            unit.speedMultiplier = 0.5f;
            unit.healthMultiplier = 0.8f;
        }
        // Increased Speed in Liquid
        if (floor.isLiquid) {
            unit.speedMultiplier = liquidSpeedMultiplier;
        }
        // Trail Effect
        if (Mathf.chanceDelta(trailIntensity) && !floor.isLiquid && unit.moving()) {
            Fx.unitLand.at(
                    unit.x + Angles.trnsx(unit.rotation - 90, trailOffsetX, trailOffsetY),
                    unit.y + Angles.trnsy(unit.rotation - 90, trailOffsetX, trailOffsetY),
                    unit.hitSize / 6, floorColor);
        }
        if (unit.moving()) {
            engineSmokeIntensity = trailIntensity * 4;
        }
        // Engine Smoke
        if (Mathf.chanceDelta((engineSmokeIntensity)) && engineSmoke) {
            Fx.fireSmoke.at(
                    unit.x + Angles.trnsx(unit.rotation - 90, engineOffsetX, engineOffsetY),
                    unit.y + Angles.trnsy(unit.rotation - 90, engineOffsetX, engineOffsetY));
        }
    }
}


