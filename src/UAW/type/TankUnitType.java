
package UAW.type;

import UAW.type.weapon.TurretedWeapon;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.struct.Seq;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.MechUnit;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;

public class TankUnitType extends UnitType {
    public final Seq<TurretedWeapon> TurrettedWeapon = new Seq<>();
    public TankUnitType(String name) {
        super(name);
        constructor = MechUnit::create;
        mechSideSway = 0f;
        mechFrontSway = 0f;
        mechStepParticles = true;
        mechStride = 0.5f;
        trailX = 5.5f;
        trailY = -4f;
    }
}

