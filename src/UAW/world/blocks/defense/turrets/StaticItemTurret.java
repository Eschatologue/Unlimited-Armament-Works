package UAW.world.blocks.defense.turrets;

import UAW.graphics.UAWFxDynamic;
import UAW.graphics.UAWFxStatic;
import arc.Core;
import arc.func.Cons;
import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BulletType;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.Turret;


public class StaticItemTurret extends ItemTurret {
    public Cons <StaticItemTurretBuild> heatDrawer = tile -> {
        if(tile.heat <= 0.00001f) return;

        Draw.color(heatColor, tile.heat);
        Draw.blend(Blending.additive);
        Draw.rect(heatRegion, tile.x, tile.y, 90);
        Draw.blend();
        Draw.color();
    };

    // Turret without the rotating part and fires from the center
    public TextureRegion base;
    public StaticItemTurret (String name) {
        super(name);
        shootEffect = UAWFxStatic.crossShoot;
        smokeEffect = Fx.smokeCloud;
        rotate = false;
        shootCone = 360;
    }
    public void load(){
        super.load();
        base = Core.atlas.find(name);
    }
    public class StaticItemTurretBuild extends ItemTurretBuild {

        @Override
        public void draw() {
            Draw.z(Layer.turret - 0.001f);
            Draw.rect(base, x, y);
            if(heatRegion != Core.atlas.find("error")){
                heatDrawer.get(this);
            }
        }

        @Override
        protected void bullet(BulletType type, float angle){
            float lifeScl = type.scaleVelocity ? Mathf.clamp(Mathf.dst(x, y , targetPos.x, targetPos.y) / type.range(), minRange / type.range(), range / type.range()) : 1f;
            type.create(this, team, x, y, angle, 1f + Mathf.range(velocityInaccuracy), lifeScl);
        }

        @Override
        protected void effects(){
            Effect shoot = shootEffect == Fx.none ? peekAmmo().shootEffect : shootEffect;
            Effect smoke = smokeEffect == Fx.none ? peekAmmo().smokeEffect : smokeEffect;

            shoot.at(x, y);
            smoke.at(x, y);
            shootSound.at(x, y, Mathf.random(0.9f, 1.1f));

            if(shootShake > 0){
                Effect.shake(shootShake, shootShake, this);
            }
        }
    }
}
