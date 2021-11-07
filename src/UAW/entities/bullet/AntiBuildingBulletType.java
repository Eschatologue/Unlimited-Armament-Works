package UAW.entities.bullet;

import UAW.graphics.UAWFxDynamic;
import UAW.graphics.UAWFxStatic;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;

import static mindustry.Vars.tilesize;
/** Artillery Bullet but with different trail, building damage multiplier, and etc */
public class AntiBuildingBulletType extends ArtilleryBulletType {
    public float size = 32;
    public AntiBuildingBulletType(float speed, float damage, float damageMult,String bulletSprite){
        super(speed, damage, bulletSprite);
        this.buildingDamageMultiplier = damageMult;
        splashDamage = damage * 2.5f;
        splashDamageRadius = 10 * tilesize;
        height = size;
        width = size / 2.1f;
        hitSound = Sounds.explosionbig;
        hitSoundVolume = 3f;
        hitShake = 16;
        knockback = 8;
        frontColor = Pal.sapBullet;
        backColor = Pal.sapBulletBack;
        shootEffect = new MultiEffect(Fx.shootBig2, UAWFxStatic.shootSporeFlame);
        smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.shootLiquid);
        trailLength = Mathf.round(height) + 8;
        trailWidth = width / 4f;
        trailColor = backColor;
        trailMult = 0f;
        status = StatusEffects.melting;
        makeFire = true;
    }
    public AntiBuildingBulletType(float speed, float damage, float damageMult){
        this(speed, damage, damageMult, "shell");
    }

    public AntiBuildingBulletType(){
        this(1f, 1f, 2.5f,"shell");
    }
}
