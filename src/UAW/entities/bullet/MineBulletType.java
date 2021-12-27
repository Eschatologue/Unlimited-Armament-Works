package UAW.entities.bullet;

import UAW.graphics.UAWFxD;
import arc.Core;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.*;
import mindustry.graphics.*;

import static mindustry.Vars.tilesize;

public class MineBulletType extends BulletType {
	public Color mineColor = Pal.bulletYellow, detonationColor = Pal.redSpark;
	public TextureRegion mineBase, mineCell, mineLight;
	public String sprite;
	public Sound detonationSound = Sounds.shotgun;
	public float explodeRange = 8 * tilesize;
	public float explodeDelay = 30f;
	public float size = 16;

	public MineBulletType(float damage, float radius, float lifetime, String sprite) {
		this.splashDamage = damage;
		this.splashDamageRadius = radius;
		this.lifetime = lifetime;
		this.sprite = sprite;
		layer = Layer.debris;
		collidesAir = false;
		collidesGround = collidesTiles = true;
		speed = 3f;
		drag = 0.055f;
		hitShake = 8f;
		hitSound = Sounds.plasmaboom;
	}

	public MineBulletType(float damage, float radius, float lifetime) {
		this(damage, radius, lifetime, "uaw-mine");
	}

	public MineBulletType(float damage, float radius) {
		this(damage, radius, 25 * 60, "uaw-mine");
	}

	public MineBulletType() {
		this(550, 64, 25 * 60, "uaw-mine");
	}

	@Override
	public void load() {
		mineBase = Core.atlas.find(sprite);
		mineCell = Core.atlas.find(sprite + "-cell");
		mineLight = Core.atlas.find(sprite + "-light");
	}

	@Override
	public void update(Bullet b) {
		super.update(b);
		if (explodeRange < 0) {
			explodeRange = splashDamageRadius / 4;
		}
		if (b.fdata < 0f) return;
		if (b.timer(2, 6)) {
			Units.nearbyEnemies(b.team, Tmp.r1.setSize(explodeRange * 2f).setCenter(b.x, b.y), unit -> {
					if (b.fdata < 0f || !unit.checkTarget(collidesAir, collidesGround)) return;
					if (unit.within(b, explodeRange)) {
						detonationSound.at(b.x, b.y);
						b.fdata = -1f;
						Time.run(explodeDelay, () -> {
								if (b.fdata < 0) {
									b.time = b.lifetime;
								}
							}
						);
					}
				}
			);
		}
	}

	@Override
	public void draw(Bullet b) {
		Drawf.shadow(b.x, b.y, size * 2);
		Draw.rect(mineBase, b.x, b.y, size, size, b.rotation());

		Draw.color(mineColor);
		Draw.rect(mineCell, b.x, b.y, size, size, b.rotation());

		if (b.fdata() < 0) {
			Draw.color(detonationColor);
			Draw.rect(mineLight, b.x, b.y, size, size, b.rotation());
		}
		Draw.reset();
	}

}