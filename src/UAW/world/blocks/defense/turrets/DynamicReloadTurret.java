package UAW.world.blocks.defense.turrets;

import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.bullet.BulletType;
import mindustry.graphics.*;

import static mindustry.Vars.tilesize;

// A small modification from Yuria-Shikibe/NewHorizonMod
public class DynamicReloadTurret extends UAWItemTurret {
	public float maxSpeedUpScl = 0.5f;
	public float speedupPerShot = 0.075f;
	public float slowDownReloadTime = 90f;
	public float windUpEffectCircleRad = -1;

	public DynamicReloadTurret(String name) {
		super(name);
	}

	@Override
	public void init() {
		super.init();
		if (windUpEffectCircleRad <= 0) {
			windUpEffectCircleRad = size * tilesize;
		}
	}

	public class DynamicReloadTurretBuild extends ItemTurretBuild {
		public float speedupScl = 1f;
		public float slowDownReload = 1f;

		@Override
		public void updateTile() {
			super.updateTile();
			if (slowDownReload >= 1f) {
				slowDownReload -= Time.delta;
			} else speedupScl = Mathf.lerpDelta(speedupScl, 0f, 0.05f);
		}

		@Override
		protected void updateShooting() {
			if (reload >= reloadCounter) {
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
			if (speedupScl < maxSpeedUpScl) {
				speedupScl += speedupPerShot;
			} else speedupScl = maxSpeedUpScl;
		}

		@Override
		public void drawSelect() {
			Drawf.dashCircle(x, y, range, team.color);

			Draw.z(Layer.effect);
			Draw.color();
			Draw.reset();
		}
	}
}