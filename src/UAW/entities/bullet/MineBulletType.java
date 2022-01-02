package UAW.entities.bullet;

import UAW.content.UAWSfx;
import arc.Core;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.blocks.environment.Floor;

public class MineBulletType extends BulletType {
	/** Light that will appear when mine is triggered */
	public Color detonationColor = Color.red;
	/** Lighter Color */
	public Color frontColor = Pal.bulletYellow;
	/** Darker Color */
	public Color backColor = Pal.bulletYellowBack;
	public TextureRegion mineBase, mineFront, mineBack, mineIndicator, mineOutline;
	public String sprite;
	public Sound detonationSound = UAWSfx.mineDetonate1;
	public Effect triggerEffect = Fx.smeltsmoke;
	/** The distance of unit that will make the mine detonates */
	public float explodeRange = 32;
	/** Time delay of mine detonating when unit steps in its radius */
	public float explodeDelay = 36f;
	/** How big is the mine */
	public float size = 24;
	/**
	 * [TODO]
	 * How long does it takes for the mine to drown, set to < 0 to disable
	 */
	public float mineDrownTime = 30f;

	public MineBulletType(float splashDamage, float splashRadius, float lifetime, String sprite) {
		this.splashDamage = splashDamage;
		this.splashDamageRadius = splashRadius;
		this.lifetime = lifetime;
		this.sprite = sprite;
		damage = 0;
		layer = Layer.debris - 1;
		absorbable = hittable = false;
		despawnHit = true;
		collidesAir = false;
		collideTerrain = true;
		collidesGround = collidesTiles = true;
		speed = 3f;
		drag = 0.055f;
		hitShake = 8f;
		hitSound = Sounds.plasmaboom;
		fragAngle = 360;
	}

	public MineBulletType(float damage, float splashRadius, float lifetime) {
		this(damage, splashRadius, lifetime, "uaw-mine");
	}

	public MineBulletType(float damage, float splashRadius) {
		this(damage, splashRadius, 25 * 60, "uaw-mine");
	}

	public MineBulletType() {
		this(550, 64, 25 * 60, "uaw-mine");
	}

	@Override
	public void load() {
		mineBase = Core.atlas.find(sprite);
		mineFront = Core.atlas.find(sprite + "-front");
		mineBack = Core.atlas.find(sprite + "-back");
		mineIndicator = Core.atlas.find(sprite + "-indicator");
		mineOutline = Core.atlas.find(sprite + "-outline");
	}

	@Override
	public void draw(Bullet b) {
		Drawf.shadow(b.x, b.y, size * 1.7f);
		Draw.rect(mineOutline, b.x, b.y, size, size, b.rotation());
		Draw.rect(mineBase, b.x, b.y, size, size, b.rotation());

		Draw.color(frontColor);
		Draw.rect(mineFront, b.x, b.y, size, size, b.rotation());
		Draw.color(backColor);
		Draw.rect(mineBack, b.x, b.y, size, size, b.rotation());

		Draw.reset();

		if (b.fdata() < 0) {
			Draw.color(detonationColor);
			Draw.rect(mineIndicator, b.x, b.y, size, size, b.rotation());
		}
	}

	@Override
	public void update(Bullet b) {
		Floor floor = Vars.world.floorWorld(b.x, b.y);
		Color liquidColor = floor.mapColor.equals(Color.black) ? Blocks.water.mapColor : floor.mapColor;
		super.update(b);
		// Copied from flakBullet
		if (b.fdata < 0f) return;
		if (b.timer(2, 6)) {
			Units.nearbyEnemies(b.team, Tmp.r1.setSize(explodeRange * 2f).setCenter(b.x, b.y), unit -> {
					if (b.fdata < 0f || !unit.checkTarget(collidesAir, collidesGround)) return;
					if (unit.within(b, explodeRange)) {
						triggerEffect.at(b.x, b.y);
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
}