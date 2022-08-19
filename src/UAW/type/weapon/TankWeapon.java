//package UAW.type.weapon;
//
//import UAW.entities.units.entity.TankUnitEntity;
//import arc.Core;
//import arc.graphics.Blending;
//import arc.graphics.g2d.*;
//import arc.math.*;
//import mindustry.entities.units.WeaponMount;
//import mindustry.gen.Unit;
//import mindustry.graphics.Drawf;
//
//public class TankWeapon extends UAWWeapon {
//	public TextureRegion gunOutline;
//
//	/** Weapon that attatches to and rotates based on tank hull */
//	public TankWeapon(String name) {
//		this.name = name;
//		top = false;
//		mirror = false;
//		shootCone = 12f;
//		cooldownTime = 40;
//	}
//
//	@Override
//	public void drawOutline(Unit unit, WeaponMount mount) {
//		if (unit instanceof TankUnitEntity tank) {
//			float rotation = unit.rotation - 90;
//			float gunRotation = tank.hullRotation - 90;
//			float weaponRotation = rotation + (rotate ? mount.rotation : 0);
//			float wx = tank.x + Angles.trnsx(gunRotation, x, y) + Angles.trnsx(weaponRotation, 0, -mount.recoil);
//			float wy = tank.y + Angles.trnsy(gunRotation, x, y) + Angles.trnsy(weaponRotation, 0, -mount.recoil);
//			// Draws custom outline region, outlineRegion is reserved for icons
//			if (gunOutline.found()) {
//				Draw.rect(gunOutline,
//					wx, wy,
//					gunOutline.width * Draw.scl * -Mathf.sign(flipSprite),
//					gunOutline.height * Draw.scl,
//					weaponRotation);
//				Draw.rect(region,
//					wx, wy,
//					region.width * Draw.scl * -Mathf.sign(flipSprite),
//					region.height * Draw.scl,
//					weaponRotation);
//				if (heatRegion.found() && mount.heat > 0) {
//					Draw.color(heatColor, mount.heat);
//					Draw.blend(Blending.additive);
//					Draw.rect(heatRegion,
//						wx, wy,
//						heatRegion.width * Draw.scl * -Mathf.sign(flipSprite),
//						heatRegion.height * Draw.scl,
//						weaponRotation);
//					Draw.blend();
//					Draw.color();
//				}
//			}
//		} else {
//			// Prevent crashes if I somehow fuck it up
//			super.draw(unit, mount);
//		}
//	}
//
//	@Override
//	public void draw(Unit unit, WeaponMount mount) {
//		if (unit instanceof TankUnitEntity tank) {
//			float z = Draw.z();
//			Draw.z(z + layerOffset);
//			float rotation = unit.rotation - 90;
//			float gunRotation = tank.hullRotation - 90;
//			float weaponRotation = rotation + (rotate ? mount.rotation : 0);
//			float wx = unit.x + Angles.trnsx(gunRotation, x, y) + Angles.trnsx(weaponRotation, 0, -mount.recoil);
//			float wy = unit.y + Angles.trnsy(gunRotation, x, y) + Angles.trnsy(weaponRotation, 0, -mount.recoil);
//
//			if (shadow > 0) {
//				Drawf.shadow(wx, wy, shadow);
//			}
//
//			if (top) {
//				drawOutline(unit, mount);
//			}
//
//			Draw.rect(region,
//				wx, wy,
//				region.width * Draw.scl * -Mathf.sign(flipSprite),
//				region.height * Draw.scl,
//				weaponRotation);
//			Draw.z(z);
//		} else {
//			super.draw(unit, mount);
//		}
//	}
//
//	@Override
//	public void load() {
//		super.load();
//		outlineRegion = Core.atlas.find(name + "-icon");
//		gunOutline = Core.atlas.find(name + "-outline");
//	}
//}
//
