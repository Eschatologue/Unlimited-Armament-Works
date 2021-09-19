
package UAW.type;

import UAW.type.weapon.TurretedWeapon;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Tmp;
import mindustry.content.Blocks;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.MechUnit;
import mindustry.gen.Mechc;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import mindustry.world.blocks.environment.Floor;

public class TankUnitType extends UnitType {
    public TankUnitType(String name) {
        super(name);
        flying = false;
        constructor = MechUnit::create;
        mechFrontSway = mechSideSway = 0f;
        mechStride = 0.5f;
    }
}

