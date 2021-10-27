package UAW.type;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.*;
import arc.struct.ObjectSet;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.meta.BlockFlag;

// Basically Mech with overrided draw method
public class TankUnitType extends UnitType {
    public float trailIntensity = 0.4f;
    public float trailOffsetX = 0f, trailOffsetY = 0f;
    public float liquidSpeedMultiplier = 1.2f;
    public float shootingSpeedMultiplier = 1f;
    public boolean useCustomWeaponIcon = false;

    public TankUnitType(String name) {
        super(name);
        immunities = ObjectSet.with(StatusEffects.disarmed, StatusEffects.slow, StatusEffects.freezing);
        flying = false;
        constructor = MechUnit::create;
        targetFlags = new BlockFlag[]{BlockFlag.turret, BlockFlag.repair, BlockFlag.extinguisher};
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

        Floor floor = Vars.world.floorWorld(unit.x, unit.y);
        Color floorColor = floor.mapColor;
        super.update(unit);
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
        if (unit.isShooting) {
            speed = speed * shootingSpeedMultiplier;
        }
    }
}


