package UAW.type.weapon;

import UAW.world.meta.UAWStatValues;
import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.ui.layout.Table;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.type.*;
import mindustry.world.meta.*;

public class RecoilingGunWeapon extends Weapon {
    public TextureRegion turret, turretOutlineRegion, turretCell, weaponIcon;
    public boolean drawTurretCell = false;


    public RecoilingGunWeapon(String name) {
        super(name);
        this.name = name;
    }

    @Override
    public void load() {
        super.load();
        turret = Core.atlas.find(name + "-turret");
        turretOutlineRegion = Core.atlas.find(name + "-turret-outline");
        turretCell = Core.atlas.find(name + "-turret-cell");
        weaponIcon = Core.atlas.find(name + "-turret-icon");
    }

    public Color cellColor(Unit unit) {
        float f = Mathf.clamp(unit.healthf());
        return Tmp.c1.set(Color.black).lerp(unit.team.color, f + Mathf.absin(Time.time, Math.max(f * 5f, 1f), 1f - f));
    }

    @Override
    public void addStats(UnitType u, Table t) {
        if (inaccuracy > 0) {
            t.row();
            t.add("[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int) inaccuracy + " " + StatUnit.degrees.localized());
        }
        t.row();
        t.add("[lightgray]" + Stat.reload.localized() + ": " + (mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / reload * shots, 2) + " " + StatUnit.perSecond.localized());

        UAWStatValues.ammo(ObjectMap.of(u, bullet)).display(t);
    }

    public void draw(Unit unit, WeaponMount mount) {
        float
                rotation = unit.rotation - 90,
                weaponRotation = rotation + (rotate ? mount.rotation : 0),
                recoil = -((mount.reload) / reload * this.recoil),
                wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, recoil),
                wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, recoil),
                tx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsy(weaponRotation, 0, 0),
                ty = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, 0);

        if (shadow > 0) {
            Drawf.shadow(wx, wy, shadow);
        }

        if (outlineRegion.found() && top) {
            Draw.rect(outlineRegion,
                    wx, wy,
                    outlineRegion.width * Draw.scl * -Mathf.sign(flipSprite),
                    region.height * Draw.scl,
                    weaponRotation);
            Draw.rect(turretOutlineRegion,
                    tx, ty,
                    turretOutlineRegion.width * Draw.scl,
                    region.height * Draw.scl,
                    weaponRotation);
        }

        Draw.rect(region,
                wx, wy,
                region.width * Draw.scl * -Mathf.sign(flipSprite),
                region.height * Draw.scl,
                weaponRotation);

        Draw.rect(turret, tx, ty, weaponRotation);
        if (drawTurretCell) {
            Draw.color(cellColor(unit));
            Draw.rect(turretCell, tx, ty, weaponRotation);
            Draw.reset();
        }

        if (heatRegion.found() && mount.heat > 0) {
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

