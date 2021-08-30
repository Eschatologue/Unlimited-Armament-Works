package UAW.entities.bullet;

import mindustry.entities.bullet.BulletType;

public class RotorBlade extends BulletType{

    public RotorBlade(float speed, float damage){
        super(speed, damage);
        homingPower = 4f;
    }

}