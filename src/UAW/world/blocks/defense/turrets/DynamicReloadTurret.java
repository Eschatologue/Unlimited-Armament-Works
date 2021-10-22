package UAW.world.blocks.defense.turrets;

import UAW.graphics.UAWPal;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.bullet.BulletType;
import mindustry.graphics.*;

// A small modification from Yuria-Shikibe/NewHorizonMod
public class DynamicReloadTurret extends CustomStatItemTurret {
    public float maxReloadScl = 0.5f;
    public float speedupPerShot = 0.075f;
    public float slowDownReloadTime = 90f;
    public float inaccuracyModifier = 0f;

    public DynamicReloadTurret(String name) {
        super(name);
    }

    public class DynamicReloadTurretBuild extends ItemTurretBuild {
        public float speedupScl = 1f;
        public float slowDownReload = 1f;

        @Override
        public void updateTile() {
            super.updateTile();
            if (slowDownReload >= 1f) {
                slowDownReload -= Time.delta;
            } else
                speedupScl = Mathf.lerpDelta(speedupScl, 0f, 0.05f);
        }

        @Override
        protected void updateShooting() {
            if (reload >= reloadTime) {
                BulletType type = peekAmmo();
                shoot(type);
                reload = 0f;
            } else {
                reload += (1 + speedupScl) * delta() * peekAmmo().reloadMultiplier * baseReloadSpeed();
            }
        }

        @Override
        protected void shoot(BulletType type) {
            super.shoot(type);
            slowDownReload = slowDownReloadTime;
            if (speedupScl < maxReloadScl) {
                speedupScl += speedupPerShot;
            } else speedupScl = maxReloadScl;
        }

        @Override
        public void drawSelect() {
            super.drawSelect();
            Draw.z(Layer.effect);
            Draw.color(UAWPal.cryoBackBloom, UAWPal.surgeBackBloom, UAWPal.darkPyraBloom, speedupScl / maxReloadScl);
            Lines.stroke(speedupScl / maxReloadScl);
            Lines.polySeg(200, 0, (int) (200 * speedupScl / maxReloadScl), x, y, range / 10, rotation);
            Draw.color();
        }

        @Override
        protected void bullet(BulletType type, float angle) {
            super.bullet(type, angle + (speedupScl * inaccuracyModifier));
        }
    }
}