package UAW.type;

import UAW.world.meta.UAWStatValues;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.*;
import arc.struct.ObjectSet;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.abilities.Ability;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

// Basically Mech with overrided draw method
public class TankUnitType extends UnitType {
    public float trailIntensity = 0.4f;
    public float trailOffsetX = 0f, trailOffsetY = 0f;
    public float liquidSpeedMultiplier = 1.2f;
    public boolean useCustomWeaponIcon = false;

    public TankUnitType(String name) {
        super(name);
        immunities = ObjectSet.with(StatusEffects.disarmed, StatusEffects.slow, StatusEffects.freezing);
        flying = false;
        constructor = MechUnit::create;
        playerTargetFlags = new BlockFlag[]{null};
        targetFlags = new BlockFlag[]{BlockFlag.factory, null};
        mechStride = mechFrontSway = mechSideSway = 0f;
    }
    /*
    // This thing is broken
        @Override
        public void setStats(){
            Unit inst = constructor.get();

            stats.add(Stat.health, health);
            stats.add(Stat.armor, armor);
            stats.add(Stat.speed, speed * 60f / tilesize, StatUnit.tilesSecond);
            stats.add(Stat.size, hitSize / tilesize, StatUnit.blocksSquared);
            stats.add(Stat.itemCapacity, itemCapacity);
            stats.add(Stat.range, (int)(maxRange / tilesize), StatUnit.blocks);
            stats.add(Stat.commandLimit, commandLimit);

            if(abilities.any()){
                var unique = new ObjectSet<String>();

                for(Ability a : abilities){
                    if(unique.add(a.localized())){
                        stats.add(Stat.abilities, a.localized());
                    }
                }
            }

            stats.add(Stat.flying, flying);

            if(!flying){
                stats.add(Stat.canBoost, canBoost);
            }

            if(mineTier >= 1){
                stats.addPercent(Stat.mineSpeed, mineSpeed);
                stats.add(Stat.mineTier, StatValues.blocks(b -> b instanceof Floor f && f.itemDrop != null && f.itemDrop.hardness <= mineTier && (!f.playerUnmineable || Core.settings.getBool("doubletapmine"))));
            }
            if(buildSpeed > 0){
                stats.addPercent(Stat.buildSpeed, buildSpeed);
            }
            if(inst instanceof Payloadc){
                stats.add(Stat.payloadCapacity, (payloadCapacity / (tilesize * tilesize)), StatUnit.blocksSquared);
            }

            if(weapons.any()){
                stats.add(Stat.weapons, UAWStatValues.weapons(this, weapons));
            }
        }
*/
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
    }
}


