package UAW.entities.bullet;

import arc.graphics.Color;
import mindustry.entities.bullet.BasicBulletType;

import static UAW.Vars.modName;

public class BuckshotBulletType extends BasicBulletType {

	public BuckshotBulletType(float size, float speed, float damage) {
		super(speed, damage);
		this.height = size;
		this.width = size;
		this.hitSize = size * 1.5f;
		this.damage = damage;
		spin = (float) Math.floor((Math.random() * 25f) - 25f);
		sprite = modName + "buckshot";
		hitColor = backColor;
		shrinkX = shrinkY = 0.5f;
		pierceCap = 2;
		knockback = 2;
		trailInterval = 4.5f;
		trailChance = 0.5f;
		trailColor = Color.lightGray;

		collidesTiles = true;
	}

	public BuckshotBulletType(float speed, float damage) {
		this(15, speed, damage);
	}

	public float bulletSize() {
		return (height + width) / 2;
	}

	@Override
	public void init() {
		super.init();
	}
}
