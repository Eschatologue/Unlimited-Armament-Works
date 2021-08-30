
package UAW.type.weapon;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.type.Weapon;

public class TurretedWeapon extends Weapon {
    public final TurretedWeapon turretedWeapon;
    // Gonna use this later
    public TextureRegion turret;

    public TurretedWeapon(String name, TurretedWeapon tWeapon) {
        super(name);
        turretedWeapon = tWeapon;
    }
    @Override
    public void load() {
        super.load();
        turret = Core.atlas.find(name + "-turret");
    }
    public void draw(Unit unit, WeaponMount tWeapon){
        super.draw(unit, tWeapon);
        float rotation = unit.rotation - 90,
            weaponRotation = rotation + (rotate ? tWeapon.rotation : 0),
            wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, 0),
            wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, 0);
            Draw.rect(turret,
                    wx, wy, turret.width * Draw.scl, region.height * Draw.scl, weaponRotation);
    }
}

