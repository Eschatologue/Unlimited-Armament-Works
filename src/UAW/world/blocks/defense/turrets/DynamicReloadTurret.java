package UAW.world.blocks.defense.turrets;

import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.bullet.BulletType;
import mindustry.world.blocks.defense.turrets.ItemTurret;

// A small modification from Yuria-Shikibe/NewHorizonMod
public class DynamicReloadTurret extends ItemTurret{
	public float maxReloadScl = 0.5f;
	public float speedupPerShot = 0.075f;
	public float slowDownReloadTime = 90f;
	public float inaccuracyModifier = 0f;
	
	public DynamicReloadTurret(String name){
		super(name);
	}
	
	@Override
	public void init(){
		super.init();
	}
	
	public class DynamicReloadTurretBuild extends ItemTurretBuild{
		public float speedupScl = 1f;
		public float slowDownReload = 1f;
		
		@Override
		public void updateTile(){
			super.updateTile();
			if(slowDownReload >= 1f){
				slowDownReload -= Time.delta;
			} else speedupScl = Mathf.lerpDelta(speedupScl, 0f, 0.05f);
		}
		
		@Override
		protected void updateShooting(){
            if(reload >= reloadTime){
                BulletType type = peekAmmo();
                shoot(type);
                reload = 0f;
            }else{
                reload += (1 + speedupScl) * delta() * peekAmmo().reloadMultiplier * baseReloadSpeed();
            };
        }
				
		@Override
		protected void shoot(BulletType type){
			super.shoot(type);
			slowDownReload = slowDownReloadTime;
			if(speedupScl < maxReloadScl){
				speedupScl += speedupPerShot;
			}else speedupScl = maxReloadScl;
		}
		
		@Override
		protected void bullet(BulletType type, float angle){
			super.bullet(type, angle + (speedupScl * inaccuracyModifier));
		}
	}
}
