
package UAW.type.weapon;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.type.Weapon;

public class TurretedWeapon extends Weapon {
    public TextureRegion turret, turretOutlineRegion;

    public TurretedWeapon(String name){
        super(name);
        this.name = name;
    }
    @Override
    public void load() {
        super.load();
        turret = Core.atlas.find(name + "-turret");
        turretOutlineRegion = Core.atlas.find(name + "-turret-outline");
    }

    public void draw(Unit unit, WeaponMount mount){
        float
                rotation = unit.rotation - 90,
                weaponRotation  = rotation + (rotate ? mount.rotation : 0),
                recoil = -((mount.reload) / reload * this.recoil),
                wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, recoil),
                wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, recoil),
                tx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsy(weaponRotation, 0, 0),
                ty = unit.y + Angles.trnsy(rotation, x, y)+ Angles.trnsy(weaponRotation, 0, 0);

        if(shadow > 0){
            Drawf.shadow(wx, wy, shadow);
        }

        if(outlineRegion.found() && top){
            Draw.rect(outlineRegion,
                    wx, wy,
                    outlineRegion.width * Draw.scl * -Mathf.sign(flipSprite),
                    region.height * Draw.scl,
                    weaponRotation);
            Draw.rect(turretOutlineRegion,
                    tx, ty, weaponRotation);
        }

        Draw.rect(region,
                wx, wy,
                region.width * Draw.scl * -Mathf.sign(flipSprite),
                region.height * Draw.scl,
                weaponRotation);

        Draw.rect(turret, tx, ty, weaponRotation);

        if(heatRegion.found() && mount.heat > 0){
            Draw.color(heatColor, mount.heat);
            Draw.blend(Blending.additive);
            Draw.rect(heatRegion,
                    wx, wy,
                    heatRegion.width * Draw.scl * -Mathf.sign(flipSprite),
                    heatRegion.height * Draw.scl,
                    weaponRotation);
            Draw.blend();
            Draw.color();
        }

    }
}

